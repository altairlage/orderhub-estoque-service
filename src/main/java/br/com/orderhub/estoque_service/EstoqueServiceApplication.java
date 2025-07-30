package br.com.orderhub.estoque_service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Classe principal da aplicação de Estoque.
 * <p>
 * A anotação @SpringBootApplication já inclui a varredura de componentes,
 * repositórios e entidades em seus subpacotes, tornando @EntityScan e
 * @EnableJpaRepositories desnecessários.
 * <p>
 * @EnableJpaAuditing é necessário para popular automaticamente os campos
 * de data de criação/atualização nas entidades.
 * <p>
 * @EnableRabbit ativa o suporte para mensageria com RabbitMQ.
 */
@SpringBootApplication
@EnableJpaAuditing // NECESSÁRIO para preenchimento automático de datas (@CreatedDate, etc)
public class EstoqueServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstoqueServiceApplication.class, args);
    }
}