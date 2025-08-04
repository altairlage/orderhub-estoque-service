package br.com.orderhub.estoque_service.adapter.gateway;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.estoque_service.adapter.mapper.EstoqueEntityMapper;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueEntity;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EstoqueRepositoryJpaGatewayImplTest {

    private EstoqueRepository estoqueRepository;
    private EstoqueEntityMapper mapper;
    private EstoqueRepositoryJpaGatewayImpl gateway;

    @BeforeEach
    void setup() {
        estoqueRepository = mock(EstoqueRepository.class);
        mapper = mock(EstoqueEntityMapper.class);
        gateway = new EstoqueRepositoryJpaGatewayImpl(estoqueRepository, mapper);
    }

    @Test
    void deveSalvarEstoqueComSucesso() {
        Estoque estoque = new Estoque(1L, 10);
        EstoqueEntity entity = new EstoqueEntity(1L, 10);

        when(mapper.toEntity(estoque)).thenReturn(entity);
        when(estoqueRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(estoque);

        Estoque result = gateway.salvar(estoque);

        assertEquals(estoque, result);
        verify(estoqueRepository).save(entity);
    }

    @Test
    void deveAdicionarProdutoNoEstoqueQuandoIdValido() {
        Estoque estoque = new Estoque(2L, 20);
        EstoqueEntity entity = new EstoqueEntity(2L, 20);

        when(mapper.toEntity(estoque)).thenReturn(entity);
        when(estoqueRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(estoque);

        Estoque result = gateway.adicionarProdutoNoEstoque(estoque);

        assertEquals(estoque, result);
    }

    @Test
    void deveRemoverProdutoDoEstoque() {
        gateway.removerProdutoNoEstoque(3L);

        verify(estoqueRepository).deleteById(3L);
    }

    @Test
    void deveConsultarEstoquePorIdValido() {
        EstoqueEntity entity = new EstoqueEntity(4L, 15);
        Estoque estoque = new Estoque(4L, 15);

        when(estoqueRepository.findById(4L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(estoque);

        Estoque result = gateway.consultarEstoquePorIdProduto(4L);

        assertEquals(estoque, result);
    }

    @Test
    void naoDeveConsultarEstoqueComIdInvalido() {
        Estoque result = gateway.consultarEstoquePorIdProduto(0L);

        assertNull(result);
        verifyNoInteractions(estoqueRepository);
    }

    @Test
    void deveBaixarEstoqueComSucesso() {
        Estoque request = new Estoque(101L, 10);
        EstoqueEntity entity = new EstoqueEntity(101L, 50);
        EstoqueEntity atualizado = new EstoqueEntity(101L, 40);
        Estoque esperado = new Estoque(101L, 40);

        when(estoqueRepository.findById(101L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(atualizado)).thenReturn(esperado);
        when(mapper.toDomain(any())).thenReturn(esperado);
        when(mapper.toEntity(any())).thenReturn(entity);
        when(estoqueRepository.save(any())).thenReturn(atualizado);

        Estoque result = gateway.baixarEstoque(request);

        assertNotNull(result);
        assertEquals(40, result.getQuantidadeDisponivel());
    }

    @Test
    void naoDeveBaixarEstoqueSeQuantidadeInsuficiente() {
        Estoque estoque = new Estoque(6L, 20);
        EstoqueEntity entity = new EstoqueEntity(6L, 10);

        when(estoqueRepository.findById(6L)).thenReturn(Optional.of(entity));

        Estoque result = gateway.baixarEstoque(estoque);

        assertNull(result);
    }
}