package br.com.orderhub.estoque_service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Testes da Classe Principal da Aplicação")
class EstoqueServiceApplicationTest {

    @Test
    @DisplayName("Deve carregar o contexto da aplicação")
    void contextLoads() {
        // Este teste verifica se o contexto do Spring Boot sobe sem erros.
        // É um teste de integração básico, mas eficaz para a cobertura.
    }

    @Test
    @DisplayName("Deve executar o método main sem lançar exceção")
    void main() {
        // Este teste chama o método main diretamente para garantir sua cobertura.
        EstoqueServiceApplication.main(new String[]{});
    }
}