package br.com.orderhub.estoque_service.adapter.mapper;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.estoque_service.adapter.dto.EstoqueApiResponseDto;
import org.springframework.stereotype.Component;

@Component
public class EstoqueApiDtoMapper {
    public EstoqueApiResponseDto toResponse(Estoque estoque) {
        return new EstoqueApiResponseDto(
                estoque.getId(), 
                estoque.getQuantidadeDisponivel(),
                estoque.getCriadoEm(),
                estoque.getAtualizadoEm()
        );
    }
}
