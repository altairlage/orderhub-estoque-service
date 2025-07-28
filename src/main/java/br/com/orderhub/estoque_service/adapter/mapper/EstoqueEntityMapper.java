package br.com.orderhub.estoque_service.adapter.mapper;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueEntity;

import org.springframework.stereotype.Component;

@Component
public class EstoqueEntityMapper {

    public Estoque toDomain(EstoqueEntity entity) {
        return Estoque.builder()
                .sku(entity.getSku())
                .quantidadeDisponivel(entity.getQuantidadeDisponivel())
                .criadoEm(entity.getCriadoEm())
                .atualizadoEm(entity.getAtualizadoEm())
                .build();
    }

    public EstoqueEntity toEntity(Estoque estoque) {
        return EstoqueEntity.builder()
                .sku(estoque.getSku())
                .quantidadeDisponivel(estoque.getQuantidadeDisponivel())
                .criadoEm(estoque.getCriadoEm())
                .atualizadoEm(estoque.getAtualizadoEm())
                .build();
    }
}
