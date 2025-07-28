package br.com.orderhub.estoque_service.adapter.configuration;

import br.com.orderhub.core.controller.EstoqueController;
import br.com.orderhub.core.domain.usecases.estoques.BaixarEstoque;
import br.com.orderhub.core.domain.usecases.estoques.ReporEstoque;
import br.com.orderhub.core.domain.usecases.estoques.ConsultarEstoquePorSku;
import br.com.orderhub.core.interfaces.IEstoqueGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EstoqueControllerConfig {

    @Bean
    public BaixarEstoque baixarEstoqueUseCase(IEstoqueGateway estoqueGateway) {
        return new BaixarEstoque(estoqueGateway);
    }

    @Bean
    public ReporEstoque reporEstoqueUseCase(IEstoqueGateway estoqueGateway) {
        return new ReporEstoque(estoqueGateway);
    }

    @Bean
    public ConsultarEstoquePorSku consultarEstoquePorSkuUseCase(IEstoqueGateway estoqueGateway) {
        return new ConsultarEstoquePorSku(estoqueGateway);
    }

    @Bean
    public EstoqueController estoqueController(BaixarEstoque baixarEstoque,
                                               ReporEstoque reporEstoque,
                                               ConsultarEstoquePorSku consultarEstoquePorSku) {
        return new EstoqueController(baixarEstoque, reporEstoque, consultarEstoquePorSku);
    }
}