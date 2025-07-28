package br.com.orderhub.estoque_service.adapter.controller;

import br.com.orderhub.estoque_service.adapter.controller.EstoqueApiController;
import br.com.orderhub.estoque_service.adapter.dto.EstoqueApiRequestDto;
import br.com.orderhub.estoque_service.adapter.dto.EstoqueApiResponseDto;
import br.com.orderhub.estoque_service.adapter.mapper.EstoqueApiDtoMapper;
import br.com.orderhub.core.controller.EstoqueController;
import br.com.orderhub.core.domain.entities.Estoque;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EstoqueApiControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EstoqueController estoqueController;

    @Mock
    private EstoqueApiDtoMapper mapper;

    @InjectMocks
    private EstoqueApiController controller;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void deveBaixarEstoqueComSucesso() throws Exception {
        EstoqueApiRequestDto request = new EstoqueApiRequestDto("SKU123", 2);
        LocalDateTime agora = LocalDateTime.now();
        Estoque estoque = new Estoque("SKU123", 8, agora, agora);
        EstoqueApiResponseDto response = new EstoqueApiResponseDto("SKU123", 8, agora, agora);

        when(mapper.toDomain(any())).thenReturn(estoque);
        when(estoqueController.baixar("SKU123", 2)).thenReturn(estoque);
        when(mapper.toResponse(estoque)).thenReturn(response);

        mockMvc.perform(post("/api/estoques/baixar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void deveReporEstoqueComSucesso() throws Exception {
        EstoqueApiRequestDto request = new EstoqueApiRequestDto("SKU456", 5);
        LocalDateTime agora = LocalDateTime.now();
        Estoque estoque = new Estoque("SKU456", 10, agora, agora);
        EstoqueApiResponseDto response = new EstoqueApiResponseDto("SKU456", 10, agora, agora);

        when(mapper.toDomain(request)).thenReturn(estoque);
        when(estoqueController.repor("SKU456", 5)).thenReturn(estoque);
        when(mapper.toResponse(estoque)).thenReturn(response);

        mockMvc.perform(post("/api/estoques/repor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void deveBuscarPorSkuComSucesso() throws Exception {
        String sku = "SKU789";
        LocalDateTime agora = LocalDateTime.now();
        Estoque estoque = new Estoque(sku, 12, agora, agora);
        EstoqueApiResponseDto response = new EstoqueApiResponseDto(sku, 12, agora, agora);

        when(estoqueController.consultarPorSku(sku)).thenReturn(estoque);
        when(mapper.toResponse(estoque)).thenReturn(response);

        mockMvc.perform(get("/api/estoques/" + sku))
                .andExpect(status().isOk());
    }
}
