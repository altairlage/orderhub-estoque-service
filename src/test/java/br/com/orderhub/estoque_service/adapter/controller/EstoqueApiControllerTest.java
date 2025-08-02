package br.com.orderhub.estoque_service.adapter.controller;

import br.com.orderhub.core.controller.EstoqueController;
import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
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

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .setControllerAdvice(new OrderhubExceptionHandler()) // Adiciona o handler de exceções
                .build();
    }

    @Test
    @DisplayName("Deve buscar estoque por ID com sucesso")
    void deveBuscarPorIdComSucesso() throws Exception {
        // Arrange
        Long estoqueId = 1L;
        Estoque estoque = Estoque.builder().id(estoqueId).quantidadeDisponivel(100).build();
        EstoqueApiResponseDto responseDto = new EstoqueApiResponseDto(estoqueId, 100, LocalDateTime.now(), LocalDateTime.now());

        when(estoqueController.consultarPorId(estoqueId)).thenReturn(estoque);
        when(mapper.toResponse(estoque)).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(get("/api/estoques/{id}", estoqueId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(estoqueId))
                .andExpect(jsonPath("$.quantidadeDisponivel").value(100));
    }

    @Test
    @DisplayName("Deve retornar 404 Not Found ao buscar ID de estoque inexistente")
    void deveRetornarNotFoundAoBuscarIdInexistente() throws Exception {
        // Arrange
        Long estoqueId = 99L;
        when(estoqueController.consultarPorId(estoqueId)).thenThrow(new EstoqueNaoEncontradoException("Estoque não encontrado"));

        // Act & Assert
        mockMvc.perform(get("/api/estoques/{id}", estoqueId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve repor estoque com sucesso")
    void deveReporEstoqueComSucesso() throws Exception {
        // Arrange
        Long estoqueId = 1L;
        int quantidadeParaRepor = 50;
        EstoqueApiRequestDto requestDto = new EstoqueApiRequestDto(quantidadeParaRepor);
        Estoque estoqueAtualizado = Estoque.builder().id(estoqueId).quantidadeDisponivel(150).build();
        EstoqueApiResponseDto responseDto = new EstoqueApiResponseDto(estoqueId, 150, null, null);

        when(estoqueController.repor(anyLong(), anyInt())).thenReturn(estoqueAtualizado);
        when(mapper.toResponse(estoqueAtualizado)).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post("/api/estoques/{id}/repor", estoqueId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(estoqueId))
                .andExpect(jsonPath("$.quantidadeDisponivel").value(150));
    }

    @Test
    @DisplayName("Deve baixar estoque com sucesso")
    void deveBaixarEstoqueComSucesso() throws Exception {
        // Arrange
        Long estoqueId = 1L;
        int quantidadeParaBaixar = 20;
        EstoqueApiRequestDto requestDto = new EstoqueApiRequestDto(quantidadeParaBaixar);
        Estoque estoqueAtualizado = Estoque.builder().id(estoqueId).quantidadeDisponivel(80).build();
        EstoqueApiResponseDto responseDto = new EstoqueApiResponseDto(estoqueId, 80, null, null);

        when(estoqueController.baixar(anyLong(), anyInt())).thenReturn(estoqueAtualizado);
        when(mapper.toResponse(estoqueAtualizado)).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post("/api/estoques/{id}/baixar", estoqueId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(estoqueId))
                .andExpect(jsonPath("$.quantidadeDisponivel").value(80));
    }
}