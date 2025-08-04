package br.com.orderhub.estoque_service.adapter.controller;

import br.com.orderhub.core.controller.EstoqueController;
import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.exceptions.EstoqueInsuficienteException;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.exceptions.OrderhubException;
import br.com.orderhub.estoque_service.adapter.api.handler.OrderhubExceptionHandler;
import br.com.orderhub.estoque_service.adapter.dto.EstoqueApiRequestDto;
import br.com.orderhub.estoque_service.adapter.dto.EstoqueApiResponseDto;
import br.com.orderhub.estoque_service.adapter.mapper.EstoqueApiDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários do EstoqueApiController")
class EstoqueApiControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private EstoqueController estoqueController;

    @Mock
    private EstoqueApiDtoMapper mapper;

    @InjectMocks
    private EstoqueApiController estoqueApiController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(estoqueApiController)
                .setControllerAdvice(new OrderhubExceptionHandler())
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }

    @Test
    @DisplayName("Deve buscar estoque por ID com sucesso")
    void deveBuscarPorIdComSucesso() throws Exception {
        Long estoqueId = 1L;
        Estoque estoque = Estoque.builder().id(estoqueId).quantidadeDisponivel(100).build();
        // O DTO de resposta agora usa idProduto, mas o construtor é posicional
        EstoqueApiResponseDto responseDto = new EstoqueApiResponseDto(estoqueId, 100, LocalDateTime.now(), LocalDateTime.now());

        when(estoqueController.consultarPorId(estoqueId)).thenReturn(estoque);
        when(mapper.toResponse(estoque)).thenReturn(responseDto);

        mockMvc.perform(get("/api/estoques/{id}", estoqueId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProduto").value(estoqueId)) // ATUALIZADO
                .andExpect(jsonPath("$.quantidadeDisponivel").value(100));
    }

    @Test
    @DisplayName("Deve retornar 404 Not Found ao buscar ID de estoque inexistente")
    void deveRetornarNotFoundAoBuscarIdInexistente() throws Exception {
        Long estoqueId = 99L;
        when(estoqueController.consultarPorId(estoqueId)).thenThrow(new EstoqueNaoEncontradoException("Estoque não encontrado"));

        mockMvc.perform(get("/api/estoques/{id}", estoqueId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve repor estoque com sucesso")
    void deveReporEstoqueComSucesso() throws Exception {
        Long estoqueId = 1L;
        EstoqueApiRequestDto requestDto = new EstoqueApiRequestDto(50);
        Estoque estoqueAtualizado = Estoque.builder().id(estoqueId).quantidadeDisponivel(150).build();
        EstoqueApiResponseDto responseDto = new EstoqueApiResponseDto(estoqueId, 150, null, null);

        when(estoqueController.repor(anyLong(), anyInt())).thenReturn(estoqueAtualizado);
        when(mapper.toResponse(estoqueAtualizado)).thenReturn(responseDto);

        mockMvc.perform(post("/api/estoques/{id}/repor", estoqueId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProduto").value(estoqueId)) // ATUALIZADO
                .andExpect(jsonPath("$.quantidadeDisponivel").value(150));
    }

    @Test
    @DisplayName("Deve baixar estoque com sucesso")
    void deveBaixarEstoqueComSucesso() throws Exception {
        Long estoqueId = 1L;
        EstoqueApiRequestDto requestDto = new EstoqueApiRequestDto(20);
        Estoque estoqueAtualizado = Estoque.builder().id(estoqueId).quantidadeDisponivel(80).build();
        EstoqueApiResponseDto responseDto = new EstoqueApiResponseDto(estoqueId, 80, null, null);

        when(estoqueController.baixar(anyLong(), anyInt())).thenReturn(estoqueAtualizado);
        when(mapper.toResponse(estoqueAtualizado)).thenReturn(responseDto);

        mockMvc.perform(post("/api/estoques/{id}/baixar", estoqueId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProduto").value(estoqueId)) // ATUALIZADO
                .andExpect(jsonPath("$.quantidadeDisponivel").value(80));
    }

    // TESTE PARA /baixar-por-pedido FOI REMOVIDO

    @Test
    @DisplayName("Deve baixar múltiplos itens de estoque com sucesso")
    void deveBaixarMultiplosComSucesso() throws Exception {
        String jsonContent = "[{\"produto\": {\"id\": 1}, \"quantidade\": 10}]";
        doNothing().when(estoqueController).baixarEstoqueMultiplo(any());

        mockMvc.perform(post("/api/estoques/baixar-multiplos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request para quantidade inválida ao repor estoque")
    void deveRetornarBadRequestParaQuantidadeInvalidaAoRepor() throws Exception {
        Long estoqueId = 1L;
        EstoqueApiRequestDto requestDto = new EstoqueApiRequestDto(0);

        mockMvc.perform(post("/api/estoques/{id}/repor", estoqueId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 409 Conflict ao tentar baixar estoque insuficiente")
    void deveRetornarConflictAoTentarBaixarEstoqueInsuficiente() throws Exception {
        Long estoqueId = 1L;
        EstoqueApiRequestDto requestDto = new EstoqueApiRequestDto(100);

        when(estoqueController.baixar(estoqueId, 100)).thenThrow(new EstoqueInsuficienteException("Estoque insuficiente"));

        mockMvc.perform(post("/api/estoques/{id}/baixar", estoqueId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Deve tratar OrderhubException genérica com status 400")
    void deveTratarOrderhubExceptionGenerica() throws Exception {
        Long estoqueId = 1L;
        when(estoqueController.consultarPorId(estoqueId))
                .thenThrow(new OrderhubException("Erro genérico de negócio") {});

        mockMvc.perform(get("/api/estoques/{id}", estoqueId))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erro genérico de negócio"));
    }

    @Test
    @DisplayName("Deve tratar IllegalArgumentException com status 400")
    void deveTratarIllegalArgumentException() throws Exception {
        Long estoqueId = 1L;
        when(estoqueController.consultarPorId(estoqueId))
                .thenThrow(new IllegalArgumentException("Argumento inválido"));

        mockMvc.perform(get("/api/estoques/{id}", estoqueId))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Argumento inválido"));
    }

    @Test
    @DisplayName("Deve tratar erro genérico com status 500")
    void deveTratarErroGenerico() throws Exception {
        Long estoqueId = 1L;
        when(estoqueController.consultarPorId(estoqueId))
                .thenThrow(new RuntimeException("Erro inesperado no sistema"));

        mockMvc.perform(get("/api/estoques/{id}", estoqueId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Erro interno: Erro inesperado no sistema"));
    }
}