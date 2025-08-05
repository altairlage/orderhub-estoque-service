package br.com.orderhub.estoque_service.adapter.gateway;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.interfaces.IEstoqueGateway;
import br.com.orderhub.estoque_service.adapter.mapper.EstoqueEntityMapper;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueEntity;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EstoqueRepositoryJpaGatewayImpl implements IEstoqueGateway {

    private final EstoqueRepository estoqueRepository;
    private final EstoqueEntityMapper mapper;

    public EstoqueRepositoryJpaGatewayImpl(EstoqueRepository estoqueRepository, EstoqueEntityMapper mapper) {
        this.estoqueRepository = estoqueRepository;
        this.mapper = mapper;
    }

    public Estoque salvar(Estoque estoque) {
        EstoqueEntity entity = mapper.toEntity(estoque);
        return mapper.toDomain(estoqueRepository.save(entity));
    }

    @Override
    public Estoque adicionarProdutoNoEstoque(Estoque estoque) {
        if (estoque.getIdProduto() == null || estoque.getIdProduto() <= 0) {
           return null;
        }

        return salvar(estoque);
    }

    @Override
    public void removerProdutoNoEstoque(Long idProduto) {
        estoqueRepository.deleteById(idProduto);
    }

    @Override
    public Estoque consultarEstoquePorIdProduto(Long idProduto) {
        if (idProduto == null || idProduto <= 0) {
            return null;
        }

        Optional<EstoqueEntity> optional = estoqueRepository.findById(idProduto);

        return optional.map(mapper::toDomain).orElse(null);

    }

    @Override
    public Estoque baixarEstoque(Estoque estoque) {
        if (estoque.getIdProduto() == null || estoque.getIdProduto() <= 0) {
            return null;
        }

        Optional<EstoqueEntity> optional = estoqueRepository.findById(estoque.getIdProduto());

        if (optional.isEmpty()) {
            return null;
        }
        EstoqueEntity entity = optional.get();

        if (entity.getQuantidadeDisponivel() < estoque.getQuantidadeDisponivel()){
            return null;
        }

        entity.setQuantidadeDisponivel(entity.getQuantidadeDisponivel() - estoque.getQuantidadeDisponivel());

        return salvar(mapper.toDomain(entity));
    }

    @Override
    public Estoque reporEstoque(Estoque estoque) {
        if (estoque.getIdProduto() == null || estoque.getIdProduto() <= 0) {
            return null;
        }

        EstoqueEntity entity = estoqueRepository.findById(estoque.getIdProduto()).orElse(null);

        if (entity == null) {
            return null;
        }

        entity.setQuantidadeDisponivel(entity.getQuantidadeDisponivel() + estoque.getQuantidadeDisponivel());

        return salvar(mapper.toDomain(entity));
    }
}