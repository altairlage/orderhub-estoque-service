package br.com.orderhub.estoque_service.adapter.messaging.listener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoCriadoEvent {
    private String sku;
    private int quantidade;
}
