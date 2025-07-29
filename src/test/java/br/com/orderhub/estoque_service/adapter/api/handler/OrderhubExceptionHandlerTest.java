package br.com.orderhub.estoque_service.adapter.api.handler;

import br.com.orderhub.core.exceptions.EstoqueInsuficienteException;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.exceptions.OrderhubException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class OrderhubExceptionHandlerTest {

    private OrderhubExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new OrderhubExceptionHandler();
    }

    @Test
    @DisplayName("Deve retornar 404 para EstoqueNaoEncontradoException")
    void deveRetornar404ParaEstoqueNaoEncontradoException() {
        OrderhubException ex = new EstoqueNaoEncontradoException("Estoque não encontrado");

        ResponseEntity<String> response = handler.handleOrderhubException(ex);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Estoque não encontrado", response.getBody());
    }

    @Test
    @DisplayName("Deve retornar 409 para EstoqueInsuficienteException")
    void deveRetornar409ParaEstoqueInsuficienteException() {
        OrderhubException ex = new EstoqueInsuficienteException("Estoque insuficiente");

        ResponseEntity<String> response = handler.handleOrderhubException(ex);

        assertEquals(409, response.getStatusCodeValue());
        assertEquals("Estoque insuficiente", response.getBody());
    }

    @Test
    @DisplayName("Deve retornar 400 para outra OrderhubException")
    void deveRetornar400ParaOutrasOrderhubExceptions() {
        OrderhubException ex = new OrderhubException("Erro genérico");

        ResponseEntity<String> response = handler.handleOrderhubException(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Erro genérico", response.getBody());
    }

    @Test
    @DisplayName("Deve retornar 400 para IllegalArgumentException")
    void deveRetornar400ParaIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Parâmetro inválido");

        ResponseEntity<String> response = handler.handleIllegalArgumentException(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Parâmetro inválido", response.getBody());
    }

    @Test
    @DisplayName("Deve retornar 500 para Exception genérica")
    void deveRetornar500ParaExceptionGenerica() {
        Exception ex = new Exception("Falha geral");

        ResponseEntity<String> response = handler.handleGeneralError(ex);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Erro interno: Falha geral", response.getBody());
    }
}
