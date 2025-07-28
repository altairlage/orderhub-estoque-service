package br.com.orderhub.estoque_service.adapter.gateway;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.estoque_service.adapter.mapper.EstoqueEntityMapper;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueEntity;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstoqueRepositoryJpaGatewayImplTest {

    @Test
    void deveRetornarEstoqueQuandoEncontradoPorSku() {
        EstoqueRepository repository = mock(EstoqueRepository.class);
        EstoqueEntityMapper mapper = new EstoqueEntityMapper();
        EstoqueRepositoryJpaGatewayImpl gateway = new EstoqueRepositoryJpaGatewayImpl(repository, mapper);

        EstoqueEntity entity = new EstoqueEntity("SKU123", 10, null, null);
        when(repository.findBySku("SKU123")).thenReturn(Optional.of(entity));

        Optional<Estoque> result = gateway.buscarPorSku("SKU123");

        assertTrue(result.isPresent());
        assertEquals("SKU123", result.get().getSku());
        assertEquals(10, result.get().getQuantidadeDisponivel());
    }

    @Test
    void deveRetornarVazioQuandoNaoEncontrado() {
        EstoqueRepository repository = mock(EstoqueRepository.class);
        EstoqueEntityMapper mapper = new EstoqueEntityMapper();
        EstoqueRepositoryJpaGatewayImpl gateway = new EstoqueRepositoryJpaGatewayImpl(repository, mapper);

        when(repository.findBySku("SKU999")).thenReturn(Optional.empty());

        Optional<Estoque> result = gateway.buscarPorSku("SKU999");

        assertFalse(result.isPresent());
    }
}
