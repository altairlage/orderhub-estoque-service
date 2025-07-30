package br.com.orderhub.estoque_service.adapter.controller;

import java.util.List;
import java.util.stream.Collectors;

import br.com.orderhub.core.controller.EstoqueController;
import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.dto.pedidos.PedidoDTO;
import br.com.orderhub.estoque_service.adapter.dto.BaixaMultiplaRequestDto;
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
        Estoque atualizado = estoqueController.repor(dto.id(), dto.quantidadeDisponivel());
        return ResponseEntity.ok(mapper.toResponse(atualizado));
    }

    @PostMapping("/baixar")
    public ResponseEntity<EstoqueApiResponseDto> baixarEstoque(@Valid @RequestBody EstoqueApiRequestDto dto) {
        Estoque atualizado = estoqueController.baixar(dto.id(), dto.quantidadeDisponivel());
        return ResponseEntity.ok(mapper.toResponse(atualizado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueApiResponseDto> buscarPorId(@PathVariable("id") String id) {
        Estoque estoque = estoqueController.consultarPorSku(id); // Considerando id como sku
        return ResponseEntity.ok(mapper.toResponse(estoque));
    }

    @PostMapping("/baixar-por-pedido")
    public ResponseEntity<Void> baixarEstoquePorPedido(@Valid @RequestBody PedidoDTO dto) {
        estoqueController.baixarEstoquePorPedido(dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/baixar-multiplos")
    public ResponseEntity<List<EstoqueApiResponseDto>> baixarMultiplos(
            @Valid @RequestBody List<BaixaMultiplaRequestDto> itens) {
        List<EstoqueApiResponseDto> resposta = itens.stream()
                .map(dto -> {
                    Estoque atualizado = estoqueController.baixar(dto.id(), dto.quantidade());
                    return mapper.toResponse(atualizado);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(resposta);
    }
}
