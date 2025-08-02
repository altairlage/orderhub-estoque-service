package br.com.orderhub.estoque_service.adapter.mapper;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Testes Unitários do EstoqueEntityMapper")
class EstoqueEntityMapperTest {

    private EstoqueEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new EstoqueEntityMapper();
    }

    @Test
    @DisplayName("Deve mapear de Entidade de Persistência para Entidade de Domínio")
    void deveMapearEntityParaDomain() {
        // Arrange
        EstoqueEntity entity = EstoqueEntity.builder()
                .id(1L)
                .quantidadeDisponivel(100)
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();

        // Act
        Estoque domain = mapper.toDomain(entity);

        // Assert
        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getQuantidadeDisponivel(), domain.getQuantidadeDisponivel());
        assertEquals(entity.getCriadoEm(), domain.getCriadoEm());
        assertEquals(entity.getAtualizadoEm(), domain.getAtualizadoEm());
    }

    @Test
    @DisplayName("Deve mapear de Entidade de Domínio para Entidade de Persistência")
    void deveMapearDomainParaEntity() {
        // Arrange
        Estoque domain = Estoque.builder()
                .id(2L)
                .quantidadeDisponivel(50)
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();

        // Act
        EstoqueEntity entity = mapper.toEntity(domain);

        // Assert
        assertNotNull(entity);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getQuantidadeDisponivel(), entity.getQuantidadeDisponivel());
        assertEquals(domain.getCriadoEm(), entity.getCriadoEm());
        assertEquals(domain.getAtualizadoEm(), entity.getAtualizadoEm());
    }
}