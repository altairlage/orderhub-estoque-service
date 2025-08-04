package br.com.orderhub.estoque_service.adapter.api.handler;

import br.com.orderhub.core.exceptions.OrderhubException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderhubExceptionHandlerTest {
    private final OrderhubExceptionHandler handler = new OrderhubExceptionHandler();

    @Test
    void deveRetornarNotFoundParaEstoqueNaoEncontradoException() {
        OrderhubException ex = new br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException("Produto não encontrado");

        ResponseEntity<String> response = handler.handleOrderhubException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Produto não encontrado", response.getBody());
    }

    @Test
    void deveRetornarConflictParaEstoqueInsuficienteException() {
        OrderhubException ex = new br.com.orderhub.core.exceptions.EstoqueInsuficienteException("Estoque insuficiente");

        ResponseEntity<String> response = handler.handleOrderhubException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Estoque insuficiente", response.getBody());
    }

    @Test
    void deveRetornarConflictParaProdutoJaCadastradoException() {
        OrderhubException ex = new br.com.orderhub.core.exceptions.ProdutoJaCadastradoNoEstoqueException("Produto já existe");

        ResponseEntity<String> response = handler.handleOrderhubException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Produto já existe", response.getBody());
    }

    @Test
    void deveRetornarBadRequestParaOrderhubExceptionGenerica() {
        OrderhubException ex = new OrderhubException("Erro genérico");

        ResponseEntity<String> response = handler.handleOrderhubException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro genérico", response.getBody());
    }

    @Test
    void deveRetornarBadRequestParaIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Argumento inválido");

        ResponseEntity<String> response = handler.handleIllegalArgumentException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Argumento inválido", response.getBody());
    }

    @Test
    void deveRetornarInternalServerErrorParaExceptionGenerica() {
        Exception ex = new Exception("Falha interna");

        ResponseEntity<String> response = handler.handleGeneralError(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro interno: Falha interna", response.getBody());
    }

    @Test
    void deveRetornarBadRequestParaErroDeValidacao() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError error1 = new FieldError("obj", "nome", "não pode ser vazio");
        FieldError error2 = new FieldError("obj", "email", "formato inválido");

        when(bindingResult.getFieldErrors()).thenReturn(List.of(error1, error2));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro de validação: nome: não pode ser vazio, email: formato inválido", response.getBody());
    }
}
