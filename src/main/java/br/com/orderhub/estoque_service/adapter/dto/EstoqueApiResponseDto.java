package br.com.orderhub.estoque_service.adapter.dto;

import java.time.LocalDateTime;

public record EstoqueApiResponseDto(
        Long idProduto,
        Integer quantidadeDisponivel,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {}
