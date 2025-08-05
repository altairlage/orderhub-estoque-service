package br.com.orderhub.estoque_service.adapter.controller;

import br.com.orderhub.core.controller.EstoqueController;
import br.com.orderhub.core.dto.estoques.AtualizarEstoqueDTO;
import br.com.orderhub.core.dto.estoques.EstoqueDTO;
import br.com.orderhub.estoque_service.adapter.dto.EstoqueApiRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EstoqueApiControllerTest {

    private EstoqueController mockController;
    private EstoqueApiController apiController;

    @BeforeEach
    void setup() {
        mockController = mock(EstoqueController.class);
        apiController = new EstoqueApiController(mockController);
    }

    @Test
    void deveReporEstoqueComSucesso() {
        EstoqueApiRequestDto requestDto = new EstoqueApiRequestDto(10);
        EstoqueDTO expectedResponse = new EstoqueDTO(1L, 20);

        when(mockController.reporEstoque(any())).thenReturn(expectedResponse);

        ResponseEntity<EstoqueDTO> response = apiController.reporEstoque(1L, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void deveBaixarEstoqueComSucesso() {
        EstoqueApiRequestDto requestDto = new EstoqueApiRequestDto(5);
        EstoqueDTO expectedResponse = new EstoqueDTO(1L, 15);

        when(mockController.baixarEstoque(any())).thenReturn(expectedResponse);

        ResponseEntity<EstoqueDTO> response = apiController.baixarEstoque(1L, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());

        verify(mockController).baixarEstoque(new AtualizarEstoqueDTO(1L, 5));
    }

    @Test
    void deveConsultarEstoquePorIdComSucesso() {
        EstoqueDTO expectedResponse = new EstoqueDTO(1L, 30);

        when(mockController.consultarEstoquePorIdProduto(1L)).thenReturn(expectedResponse);

        ResponseEntity<EstoqueDTO> response = apiController.consultarEstoquePorIdProduto(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());

        verify(mockController).consultarEstoquePorIdProduto(1L);
    }

    @Test
    void deveAdicionarProdutoNoEstoqueComSucesso() {
        AtualizarEstoqueDTO dto = new AtualizarEstoqueDTO(2L, 50);
        EstoqueDTO expectedResponse = new EstoqueDTO(2L, 50);

        when(mockController.adicionarProdutoNoEstoque(dto)).thenReturn(expectedResponse);

        ResponseEntity<EstoqueDTO> response = apiController.adicionarProdutoNoEstoque(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());

        verify(mockController).adicionarProdutoNoEstoque(dto);
    }

    @Test
    void deveRemoverProdutoDoEstoqueComSucesso() {
        apiController.removerProdutoNoEstoque(3L);

        verify(mockController).removerProdutoNoEstoque(3L);
    }
}