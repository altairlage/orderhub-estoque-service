package br.com.orderhub.estoque_service.adapter.mapper;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueEntity;

import org.springframework.stereotype.Component;

@Component
public class EstoqueEntityMapper {

    public Estoque toDomain(EstoqueEntity entity) {
        return Estoque.builder()
                .id(entity.getId())
                .quantidadeDisponivel(entity.getQuantidadeDisponivel())
                .criadoEm(entity.getCriadoEm())
                .atualizadoEm(entity.getAtualizadoEm())
                .build();
    }

    public EstoqueEntity toEntity(Estoque estoque) {
        return EstoqueEntity.builder()
                .id(estoque.getId()) 
                .quantidadeDisponivel(estoque.getQuantidadeDisponivel())
                .criadoEm(estoque.getCriadoEm())
                .atualizadoEm(estoque.getAtualizadoEm())
                .build();
    }
}