package br.com.orderhub.estoque_service.adapter.gateway;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.interfaces.IEstoqueGateway;
import br.com.orderhub.estoque_service.adapter.mapper.EstoqueEntityMapper;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueEntity;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EstoqueRepositoryJpaGatewayImpl implements IEstoqueGateway {

    private final EstoqueRepository estoqueRepository;
    private final EstoqueEntityMapper mapper;

    /**
     * Implementa o método da interface para buscar um estoque pelo seu ID.
     */
    @Override
    public Optional<Estoque> buscarPorId(Long id) {
        return estoqueRepository.findById(id)
                .map(mapper::toDomain);
    }

    /**
     * Implementa o método da interface para buscar uma lista de estoques por seus IDs.
     */
    @Override
    public List<Estoque> buscarPorIds(List<Long> ids) {
        return estoqueRepository.findAllById(ids).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Salva ou atualiza uma entidade de estoque. O método save do JpaRepository
     * lida com a criação (se o ID for nulo) e a atualização (se o ID existir).
     */
    @Override
    public void salvar(Estoque estoque) {
        EstoqueEntity entity = mapper.toEntity(estoque);
        estoqueRepository.save(entity);
    }
}