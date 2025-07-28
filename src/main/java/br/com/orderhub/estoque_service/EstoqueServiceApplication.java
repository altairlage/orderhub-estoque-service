package br.com.orderhub.estoque_service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.orderhub.estoque_service.adapter.persistence")
@EnableJpaRepositories("com.orderhub.estoque_service.adapter.persistence")
@EnableRabbit
public class EstoqueServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EstoqueServiceApplication.class, args);
    }
}