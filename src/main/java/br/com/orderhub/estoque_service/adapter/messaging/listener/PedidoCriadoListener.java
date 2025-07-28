package br.com.orderhub.estoque_service.adapter.messaging.listener;

import br.com.orderhub.core.domain.usecases.estoques.BaixarEstoque;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoCriadoListener {

    private final BaixarEstoque baixarEstoque;

    @RabbitListener(queues = "pedido.criado")
    public void handle(PedidoCriadoEvent event) {
        baixarEstoque.executar(event.getSku(), event.getQuantidade());
    }
}
