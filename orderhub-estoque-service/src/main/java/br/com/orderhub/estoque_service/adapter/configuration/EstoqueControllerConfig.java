package br.com.orderhub.estoque_service.adapter.configuration;

import br.com.orderhub.core.controller.EstoqueController;
import br.com.orderhub.core.interfaces.IEstoqueGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EstoqueControllerConfig {

    /**
     * Provê uma instância única do EstoqueController do core.
     * O controller do core agora recebe apenas a implementação do Gateway como dependência,
     * pois ele é responsável por orquestrar seus próprios casos de uso.
     * Isso centraliza a lógica de negócio no módulo core e limpa esta configuração.
     */
    @Bean
    public EstoqueController estoqueController(IEstoqueGateway estoqueGateway) {
        // O construtor do EstoqueController no core agora espera apenas o IEstoqueGateway.
        return new EstoqueController(estoqueGateway);
    }
}