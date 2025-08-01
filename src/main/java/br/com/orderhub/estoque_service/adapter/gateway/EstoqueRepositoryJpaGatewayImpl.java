package br.com.orderhub.estoque_service.adapter.gateway;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.interfaces.IEstoqueGateway;
import br.com.orderhub.estoque_service.adapter.mapper.EstoqueEntityMapper;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List; // Importar
import java.util.Optional;
import java.util.stream.Collectors; // Importar

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

    // MÉTODO NOVO - IMPLEMENTAÇÃO DA EXIGÊNCIA DO CORE
    @Override
    public List<Estoque> buscarPorSkus(List<String> skus) {
        return estoqueRepository.findAllById(skus).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    // MÉTODO CONSOLIDADO - IMPLEMENTA A EXIGÊNCIA DO CORE
    // O método save do JPARepository já lida com a criação (se não tem ID) e atualização (se tem ID).
    @Override
    public void salvar(Estoque estoque) {
        estoqueRepository.save(mapper.toEntity(estoque));
    }
}