package br.com.orderhub.estoque_service.adapter.configuration;

import br.com.orderhub.core.controller.EstoqueController;
import br.com.orderhub.core.interfaces.IEstoqueGateway;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class EstoqueControllerConfigTest {

    @Test
    void deveCriarBeanEstoqueControllerComGatewayMockado() {
        IEstoqueGateway mockGateway = mock(IEstoqueGateway.class);
        EstoqueControllerConfig config = new EstoqueControllerConfig();

        EstoqueController controller = config.estoqueController(mockGateway);

        assertNotNull(controller);
    }
}