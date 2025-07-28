package br.com.orderhub.estoque_service.adapter.messaging.listener;

import br.com.orderhub.core.domain.usecases.estoques.BaixarEstoque;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PedidoCriadoListenerTest {

    @Test
    void deveExecutarBaixarEstoqueQuandoReceberEvento() {
        BaixarEstoque baixarEstoque = mock(BaixarEstoque.class);
        PedidoCriadoListener listener = new PedidoCriadoListener(baixarEstoque);
        PedidoCriadoEvent event = new PedidoCriadoEvent();
        event.setSku("SKU999");
        event.setQuantidade(3);

        listener.handle(event);

        verify(baixarEstoque, times(1)).executar("SKU999", 3);
    }
}
