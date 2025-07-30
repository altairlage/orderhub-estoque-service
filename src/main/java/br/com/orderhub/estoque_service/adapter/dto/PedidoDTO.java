package br.com.orderhub.estoque_service.adapter.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoDTO(
    @NotNull(message = "ID do pedido é obrigatório")
    Long id,

    @NotEmpty(message = "O pedido deve conter pelo menos um item")
    List<@Valid ItemDTO> itens
) {}
