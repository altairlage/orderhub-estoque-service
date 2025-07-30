package br.com.orderhub.estoque_service.adapter.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BaixaMultiplaRequestDto(
    @NotBlank(message = "SKU é obrigatório.")
    String sku,

    @NotNull(message = "Quantidade é obrigatória.")
    @Min(value = 1, message = "Quantidade deve ser maior que zero.")
    Integer quantidade
) {}
