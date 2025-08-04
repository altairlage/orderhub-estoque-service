package br.com.orderhub.estoque_service.adapter.controller;

import br.com.orderhub.estoque_service.adapter.dto.EstoqueApiRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {"/db_clean.sql", "/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/db_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@DisplayName("Testes de Integração para EstoqueApiController")
public class EstoqueApiControllerIT {

    @LocalServerPort
    private int port;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/estoques";
    }

    @Test
    @DisplayName("Deve buscar estoque por ID com sucesso")
    void deveBuscarEstoquePorIdComSucesso() {
        given()
            .when()
                .get("/{id}", 101L)
            .then()
                .statusCode(200)
                .body("idProduto", equalTo(101)) // ATUALIZADO
                .body("quantidadeDisponivel", equalTo(50));
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar estoque com ID inexistente")
    void deveRetornarNotFoundParaIdInexistente() {
        given()
            .when()
                .get("/{id}", 999L)
            .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Deve repor estoque com sucesso")
    void deveReporEstoqueComSucesso() throws Exception {
        EstoqueApiRequestDto requestDto = new EstoqueApiRequestDto(30);

        given()
            .contentType(ContentType.JSON)
            .body(objectMapper.writeValueAsString(requestDto))
        .when()
            .post("/{id}/repor", 101L)
        .then()
            .statusCode(200)
            .body("idProduto", equalTo(101)) // ATUALIZADO
            .body("quantidadeDisponivel", equalTo(80));
    }

    @Test
    @DisplayName("Deve baixar estoque com sucesso")
    void deveBaixarEstoqueComSucesso() throws Exception {
        EstoqueApiRequestDto requestDto = new EstoqueApiRequestDto(10);

        given()
            .contentType(ContentType.JSON)
            .body(objectMapper.writeValueAsString(requestDto))
        .when()
            .post("/{id}/baixar", 101L)
        .then()
            .statusCode(200)
            .body("idProduto", equalTo(101)) // ATUALIZADO
            .body("quantidadeDisponivel", equalTo(40));
    }

    @Test
    @DisplayName("Deve retornar 409 ao tentar baixar estoque insuficiente")
    void deveRetornarConflitoAoBaixarEstoqueInsuficiente() throws Exception {
        EstoqueApiRequestDto requestDto = new EstoqueApiRequestDto(20);

        given()
            .contentType(ContentType.JSON)
            .body(objectMapper.writeValueAsString(requestDto))
        .when()
            .post("/{id}/baixar", 102L)
        .then()
            .statusCode(409);
    }
}