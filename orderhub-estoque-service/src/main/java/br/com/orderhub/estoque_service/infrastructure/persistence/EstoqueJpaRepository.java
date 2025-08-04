package br.com.orderhub.estoque_service.infrastructure.persistence;

import br.com.orderhub.estoque_service.adapter.persistence.EstoqueEntity; // Importar a entidade correta
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueJpaRepository extends JpaRepository<EstoqueEntity, Long> {

}