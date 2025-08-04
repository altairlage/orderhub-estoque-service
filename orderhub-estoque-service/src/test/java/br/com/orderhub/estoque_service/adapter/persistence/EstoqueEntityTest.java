package br.com.orderhub.estoque_service.adapter.persistence;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EstoqueEntityTest {

    @Test
    void deveConstruirEntidadeComIdEQuantidade() {
        EstoqueEntity entity = new EstoqueEntity(1L, 100);

        assertEquals(1L, entity.getIdProduto());
        assertEquals(100, entity.getQuantidadeDisponivel());
        assertNull(entity.getCriadoEm());
        assertNull(entity.getAtualizadoEm());
    }

    @Test
    void deveExecutarPrePersistComSucesso() {
        EstoqueEntity entity = new EstoqueEntity();
        entity.prePersist();

        assertNotNull(entity.getCriadoEm());
        assertNotNull(entity.getAtualizadoEm());
        assertEquals(entity.getCriadoEm(), entity.getAtualizadoEm());
    }

    @Test
    void deveExecutarPreUpdateComSucesso() {
        EstoqueEntity entity = new EstoqueEntity();
        entity.setCriadoEm(LocalDateTime.now().minusDays(1));
        entity.setAtualizadoEm(LocalDateTime.now().minusDays(1));

        entity.preUpdate();

        assertNotNull(entity.getAtualizadoEm());
        assertTrue(entity.getAtualizadoEm().isAfter(entity.getCriadoEm()));
    }

    @Test
    void deveUsarBuilderCorretamente() {
        EstoqueEntity entity = EstoqueEntity.builder()
                .idProduto(2L)
                .quantidadeDisponivel(50)
                .build();

        assertEquals(2L, entity.getIdProduto());
        assertEquals(50, entity.getQuantidadeDisponivel());
    }
}