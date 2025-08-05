package br.com.orderhub.estoque_service.adapter.mapper;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EstoqueEntityMapperTest {

    private final EstoqueEntityMapper mapper = new EstoqueEntityMapper();

    @Test
    void deveConverterDeEntityParaDomainCorretamente() {
        EstoqueEntity entity = new EstoqueEntity(1L, 50);

        Estoque domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getIdProduto());
        assertEquals(50, domain.getQuantidadeDisponivel());
    }

    @Test
    void deveConverterDeDomainParaEntityCorretamente() {
        Estoque domain = new Estoque(2L, 30);

        EstoqueEntity entity = mapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals(2L, entity.getIdProduto());
        assertEquals(30, entity.getQuantidadeDisponivel());
    }
}