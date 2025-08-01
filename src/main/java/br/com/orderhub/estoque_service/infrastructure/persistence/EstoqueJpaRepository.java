package br.com.orderhub.estoque_service.infrastructure.persistence;

import br.com.orderhub.core.domain.entities.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueJpaRepository extends JpaRepository<Estoque, String> {
    Optional<Estoque> findBySku(String sku);
}
