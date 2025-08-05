package br.com.orderhub.estoque_service.adapter.mapper;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueEntity;

import org.springframework.stereotype.Component;

@Component
public class EstoqueEntityMapper {

    public Estoque toDomain(EstoqueEntity entity) {
        return new Estoque(entity.getIdProduto(), entity.getQuantidadeDisponivel());
    }

    public EstoqueEntity toEntity(Estoque estoque) {
        return new EstoqueEntity(estoque.getIdProduto(), estoque.getQuantidadeDisponivel());
    }
}