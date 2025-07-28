package br.com.orderhub.estoque_service.adapter.gateway;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.interfaces.IEstoqueGateway;
import br.com.orderhub.estoque_service.adapter.mapper.EstoqueEntityMapper;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EstoqueRepositoryJpaGatewayImpl implements IEstoqueGateway {

    private final EstoqueRepository estoqueRepository;
    private final EstoqueEntityMapper mapper;

    @Override
    public Optional<Estoque> buscarPorSku(String sku) {
        return estoqueRepository.findBySku(sku)
                .map(mapper::toDomain);
    }

    @Override
    public Estoque criar(Estoque estoque) {
        var entity = estoqueRepository.save(mapper.toEntity(estoque));
        return mapper.toDomain(entity);
    }

    @Override
    public Estoque atualizar(Estoque estoque) {
        var entity = estoqueRepository.save(mapper.toEntity(estoque));
        return mapper.toDomain(entity);
    }
}
