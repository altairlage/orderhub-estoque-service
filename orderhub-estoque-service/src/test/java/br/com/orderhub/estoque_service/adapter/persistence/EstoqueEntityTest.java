package br.com.orderhub.estoque_service.adapter.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários da Entidade EstoqueEntity")
class EstoqueEntityTest {

    @Test
    @DisplayName("Deve definir as datas de criação e atualização ao persistir")
    void deveDefinirDatasAoPersistir() throws InterruptedException {
        // Arrange
        EstoqueEntity estoque = new EstoqueEntity();

        // Act
        estoque.prePersist();
        LocalDateTime tempoAposPrePersist = LocalDateTime.now();


        // Assert
        assertNotNull(estoque.getCriadoEm(), "A data de criação não deve ser nula");
        assertNotNull(estoque.getAtualizadoEm(), "A data de atualização não deve ser nula");
        assertEquals(estoque.getCriadoEm(), estoque.getAtualizadoEm(), "As datas de criação e atualização devem ser iguais na inserção");
        
        // Garante que a data foi gerada recentemente
        assertTrue(estoque.getCriadoEm().isBefore(tempoAposPrePersist.plusSeconds(1)));
    }

    @Test
    @DisplayName("Deve atualizar a data de atualização ao dar update")
    void deveAtualizarDataAoDarUpdate() throws InterruptedException {
        // Arrange
        EstoqueEntity estoque = new EstoqueEntity();
        
        // Simula a persistência inicial
        estoque.prePersist();
        LocalDateTime dataCriacao = estoque.getCriadoEm();

        // Garante um intervalo de tempo para a atualização ser diferente
        Thread.sleep(10); 

        // Act
        estoque.preUpdate();
        LocalDateTime dataAtualizacao = estoque.getAtualizadoEm();

        // Assert
        assertNotNull(dataAtualizacao, "A data de atualização não deve ser nula");
        assertEquals(dataCriacao, estoque.getCriadoEm(), "A data de criação não deve ser alterada no update");
        assertNotEquals(dataCriacao, dataAtualizacao, "A data de atualização deve ser diferente da data de criação");
        assertTrue(dataAtualizacao.isAfter(dataCriacao), "A data de atualização deve ser posterior à data de criação");
    }

    @Test
    @DisplayName("Deve testar os construtores e o builder gerados pelo Lombok")
    void deveTestarConstrutoresELombok() {
        // Arrange
        LocalDateTime agora = LocalDateTime.now();

        // Act: Testando o AllArgsConstructor e Setters
        EstoqueEntity estoque1 = new EstoqueEntity(1L, 100, agora, agora);
        estoque1.setId(2L); // Testa o setter
        estoque1.setQuantidadeDisponivel(200);

        // Act: Testando o Builder
        EstoqueEntity estoque2 = EstoqueEntity.builder()
                .id(3L)
                .quantidadeDisponivel(300)
                .criadoEm(agora)
                .atualizadoEm(agora)
                .build();
        
        String toString = estoque2.toString(); // Apenas para cobertura do método toString() se existir

        // Assert
        assertNotNull(estoque1);
        assertNotNull(estoque2);
        assertEquals(2L, estoque1.getId());
        assertEquals(200, estoque1.getQuantidadeDisponivel());
        assertEquals(3, estoque2.getId());
    }
}