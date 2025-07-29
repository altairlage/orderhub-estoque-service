package br.com.orderhub.estoque_service.adapter.gateway;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.estoque_service.adapter.mapper.EstoqueEntityMapper;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueEntity;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstoqueRepositoryJpaGatewayImplTest {

    private EstoqueRepository repository;
    private EstoqueEntityMapper mapper;
    private EstoqueRepositoryJpaGatewayImpl gateway;

    @BeforeEach
    void setUp() {
        repository = mock(EstoqueRepository.class);
        mapper = new EstoqueEntityMapper();
        gateway = new EstoqueRepositoryJpaGatewayImpl(repository, mapper);
    }

    @Test
    void deveRetornarEstoqueQuandoEncontradoPorSku() {
        EstoqueEntity entity = new EstoqueEntity("SKU123", 10, LocalDateTime.now(), LocalDateTime.now());
        when(repository.findBySku("SKU123")).thenReturn(Optional.of(entity));

        Optional<Estoque> result = gateway.buscarPorSku("SKU123");

        assertTrue(result.isPresent());
        assertEquals("SKU123", result.get().getSku());
        assertEquals(10, result.get().getQuantidadeDisponivel());
    }

    @Test
    void deveRetornarVazioQuandoNaoEncontrado() {
        when(repository.findBySku("SKU999")).thenReturn(Optional.empty());

        Optional<Estoque> result = gateway.buscarPorSku("SKU999");

        assertFalse(result.isPresent());
    }

    @Test
    void deveCriarEstoque() {
        Estoque estoque = Estoque.builder()
                .sku("SKU001")
                .quantidadeDisponivel(5)
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();

        EstoqueEntity entity = mapper.toEntity(estoque);

        when(repository.save(any())).thenReturn(entity);

        Estoque result = gateway.criar(estoque);

        assertNotNull(result);
        assertEquals(estoque.getSku(), result.getSku());
        assertEquals(estoque.getQuantidadeDisponivel(), result.getQuantidadeDisponivel());

        verify(repository).save(any());
    }

    @Test
    void deveAtualizarEstoque() {
        Estoque estoque = Estoque.builder()
                .sku("SKU002")
                .quantidadeDisponivel(15)
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();

        EstoqueEntity entity = mapper.toEntity(estoque);

        when(repository.save(any())).thenReturn(entity);

        Estoque result = gateway.atualizar(estoque);

        assertNotNull(result);
        assertEquals(estoque.getSku(), result.getSku());
        assertEquals(estoque.getQuantidadeDisponivel(), result.getQuantidadeDisponivel());

        verify(repository).save(any());
    }
}
