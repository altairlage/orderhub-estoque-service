package br.com.orderhub.estoque_service.adapter.mapper;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.estoque_service.adapter.dto.EstoqueApiRequestDto;
import br.com.orderhub.estoque_service.adapter.dto.EstoqueApiResponseDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EstoqueApiDtoMapperTest {

    private final EstoqueApiDtoMapper mapper = new EstoqueApiDtoMapper();

    @Test
    void deveConverterParaDomain() {
        EstoqueApiRequestDto dto = new EstoqueApiRequestDto("SKU123", 10);
        Estoque estoque = mapper.toDomain(dto);

        assertEquals("SKU123", estoque.getSku());
        assertEquals(10, estoque.getQuantidadeDisponivel());
    }

    @Test
    void deveConverterParaResponse() {
        LocalDateTime agora = LocalDateTime.now();
        Estoque estoque = new Estoque("SKU456", 20, agora, agora);
        EstoqueApiResponseDto response = mapper.toResponse(estoque);

        assertEquals("SKU456", response.sku());
        assertEquals(20, response.quantidadeDisponivel());
        assertEquals(agora, response.criadoEm());
        assertEquals(agora, response.atualizadoEm());
    }
}
