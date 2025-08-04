package br.com.orderhub.estoque_service.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.orderhub.estoque_service.adapter.persistence.EstoqueEntity;

@Repository
public interface EstoqueRepository extends JpaRepository<EstoqueEntity, Long> {


}