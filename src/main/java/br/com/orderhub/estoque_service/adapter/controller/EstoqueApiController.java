package br.com.orderhub.estoque_service.adapter.controller;

import br.com.orderhub.core.controller.EstoqueController;
import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.estoque_service.adapter.dto.EstoqueApiRequestDto;
import br.com.orderhub.estoque_service.adapter.dto.EstoqueApiResponseDto;
import br.com.orderhub.estoque_service.adapter.mapper.EstoqueApiDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estoques")
@RequiredArgsConstructor
public class EstoqueApiController {

    private final EstoqueApiDtoMapper mapper;
    private final EstoqueController estoqueController;

    @PostMapping("/repor")
    public ResponseEntity<EstoqueApiResponseDto> reporEstoque(@Valid @RequestBody EstoqueApiRequestDto dto) {
        Estoque atualizado = estoqueController.repor(dto.sku(), dto.quantidadeDisponivel());
        return ResponseEntity.ok(mapper.toResponse(atualizado));
    }

    @PostMapping("/baixar")
    public ResponseEntity<EstoqueApiResponseDto> baixarEstoque(@Valid @RequestBody EstoqueApiRequestDto dto) {
        Estoque atualizado = estoqueController.baixar(dto.sku(), dto.quantidadeDisponivel());
        return ResponseEntity.ok(mapper.toResponse(atualizado));
    }

    @GetMapping("/{sku}")
    public ResponseEntity<EstoqueApiResponseDto> buscarPorSku(@PathVariable("sku") String sku) {
        Estoque estoque = estoqueController.consultarPorSku(sku);
        return ResponseEntity.ok(mapper.toResponse(estoque));
    }
}
