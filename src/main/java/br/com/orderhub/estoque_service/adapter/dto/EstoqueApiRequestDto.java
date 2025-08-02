package br.com.orderhub.estoque_service.adapter.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para requisições de baixa ou reposição de estoque.
 * O ID do estoque é passado pela URL, então o corpo da requisição
 * precisa apenas da quantidade.
 */
public record EstoqueApiRequestDto(
        @NotNull(message = "A quantidade é obrigatória.")
        @Min(value = 1, message = "A quantidade deve ser de no mínimo 1.")
        Integer quantidade
) {}
