package br.com.orderhub.estoque_service.adapter.configuration;

import br.com.orderhub.core.controller.EstoqueController;
import br.com.orderhub.core.interfaces.IEstoqueGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários da Configuração do Controller de Estoque")
class EstoqueControllerConfigTest {

    @Mock
    private IEstoqueGateway estoqueGateway; // Mock da dependência necessária

    @InjectMocks
    private EstoqueControllerConfig estoqueControllerConfig; 

    @Test
    @DisplayName("Deve criar o bean do EstoqueController com sucesso")
    void deveCriarBeanEstoqueController() {
        // Act
        EstoqueController controller = estoqueControllerConfig.estoqueController(estoqueGateway);

        // Assert
        assertNotNull(controller, "O bean do EstoqueController não deve ser nulo");
    }
}