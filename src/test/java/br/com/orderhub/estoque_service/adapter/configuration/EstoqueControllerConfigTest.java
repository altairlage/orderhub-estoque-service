package br.com.orderhub.estoque_service.adapter.configuration;

import br.com.orderhub.core.controller.EstoqueController;
import br.com.orderhub.core.domain.usecases.estoques.BaixarEstoque;
import br.com.orderhub.core.domain.usecases.estoques.ConsultarEstoquePorSku;
import br.com.orderhub.core.domain.usecases.estoques.ReporEstoque;
import br.com.orderhub.core.interfaces.IEstoqueGateway;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class EstoqueControllerConfigTest {

    @Test
    void deveInstanciarEstoqueController() {
        EstoqueControllerConfig config = new EstoqueControllerConfig();

        var controller = config.estoqueController(
                mock(BaixarEstoque.class),
                mock(ReporEstoque.class),
                mock(ConsultarEstoquePorSku.class));

        assertNotNull(controller);
    }

    @Test
    void deveInstanciarBaixarEstoqueUseCase() {
        EstoqueControllerConfig config = new EstoqueControllerConfig();
        var useCase = config.baixarEstoqueUseCase(mock(IEstoqueGateway.class));
        assertNotNull(useCase);
    }

    @Test
    void deveInstanciarReporEstoqueUseCase() {
        EstoqueControllerConfig config = new EstoqueControllerConfig();
        var useCase = config.reporEstoqueUseCase(mock(IEstoqueGateway.class));
        assertNotNull(useCase);
    }

    @Test
    void deveInstanciarConsultarEstoquePorSkuUseCase() {
        EstoqueControllerConfig config = new EstoqueControllerConfig();
        var useCase = config.consultarEstoquePorSkuUseCase(mock(IEstoqueGateway.class));
        assertNotNull(useCase);
    }

}
