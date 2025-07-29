package br.com.orderhub.estoque_service.adapter.persistence;

import br.com.orderhub.estoque_service.adapter.persistence.EstoqueEntity;
import br.com.orderhub.estoque_service.adapter.persistence.EstoqueRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EstoqueRepositoryTest {

    @Autowired
    private EstoqueRepository repository;

    @Test
    @DisplayName("Deve salvar e recuperar um estoque")
    void deveSalvarERecuperarEstoque() {
        // given
        EstoqueEntity entity = EstoqueEntity.builder()
                .sku("TEST-001")
                .quantidadeDisponivel(100)
                .build();

        // when
        repository.save(entity);
        Optional<EstoqueEntity> result = repository.findBySku("TEST-001");

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getQuantidadeDisponivel()).isEqualTo(100);
        assertThat(result.get().getCriadoEm()).isNotNull();
        assertThat(result.get().getAtualizadoEm()).isNotNull();
    }

    @Test
    @DisplayName("Deve atualizar estoque corretamente")
    void deveAtualizarEstoque() {
        // given
        EstoqueEntity entity = EstoqueEntity.builder()
                .sku("TEST-002")
                .quantidadeDisponivel(10)
                .build();

        repository.save(entity);

        // when
        entity.setQuantidadeDisponivel(99);
        repository.save(entity);

        // then
        Optional<EstoqueEntity> result = repository.findBySku("TEST-002");
        assertThat(result).isPresent();
        assertThat(result.get().getQuantidadeDisponivel()).isEqualTo(99);
    }
}
