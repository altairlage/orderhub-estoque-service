package br.com.orderhub.estoque_service.adapter.mapper;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EstoqueEntityMapperTest {

    private final EstoqueEntityMapper mapper = new EstoqueEntityMapper();

    @Test
    @DisplayName("Deve mapear Estoque para EstoqueEntity")
    void deveMapearDominioParaEntity() {
        Estoque estoque = new Estoque("SKU123", 10, LocalDateTime.now().minusDays(1), LocalDateTime.now());

        EstoqueEntity entity = mapper.toEntity(estoque);

        assertEquals("SKU123", entity.getSku());
        assertEquals(10, entity.getQuantidadeDisponivel());
        assertEquals(estoque.getCriadoEm(), entity.getCriadoEm());
        assertEquals(estoque.getAtualizadoEm(), entity.getAtualizadoEm());
    }

    @Test
    @DisplayName("Deve mapear EstoqueEntity para Estoque")
    void deveMapearEntityParaDominio() {
        EstoqueEntity entity = new EstoqueEntity();
        entity.setSku("SKU999");
        entity.setQuantidadeDisponivel(5);
        entity.setCriadoEm(LocalDateTime.now().minusDays(2));
        entity.setAtualizadoEm(LocalDateTime.now());

        Estoque estoque = mapper.toDomain(entity);

        assertEquals("SKU999", estoque.getSku());
        assertEquals(5, estoque.getQuantidadeDisponivel());
        assertEquals(entity.getCriadoEm(), estoque.getCriadoEm());
        assertEquals(entity.getAtualizadoEm(), estoque.getAtualizadoEm());
    }
}
