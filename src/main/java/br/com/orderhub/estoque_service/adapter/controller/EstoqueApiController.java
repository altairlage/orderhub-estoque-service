package br.com.orderhub.estoque_service.adapter.controller;

import java.util.List;
import java.util.stream.Collectors;

import br.com.orderhub.core.controller.EstoqueController;
import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.dto.estoques.ItemEstoqueDTO; // MUDANÇA: Usar DTO do core
import br.com.orderhub.core.dto.pedidos.PedidoDTO;
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

    // Endpoints individuais (/repor, /baixar, /{id}) permanecem corretos.
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

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueApiResponseDto> buscarPorId(@PathVariable("id") String id) {
        Estoque estoque = estoqueController.consultarPorSku(id); // id é o SKU
        return ResponseEntity.ok(mapper.toResponse(estoque));
    }

    /**
     * Endpoint para dar baixa no estoque a partir de um pedido.
     * Agora, essa chamada delega para a lógica atômica no core.
     */
    @PostMapping("/baixar-por-pedido")
    public ResponseEntity<Void> baixarEstoquePorPedido(@Valid @RequestBody PedidoDTO dto) {
        estoqueController.baixarEstoquePorPedido(dto);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Endpoint para dar baixa em múltiplos itens de forma atômica.
     * MUDANÇA: O corpo da requisição agora espera o DTO do core e chama o método atômico.
     */
    @PostMapping("/baixar-multiplos")
    public ResponseEntity<Void> baixarMultiplos(@Valid @RequestBody List<ItemEstoqueDTO> itens) {
        estoqueController.baixarEstoqueMultiplo(itens);
        return ResponseEntity.noContent().build();
    }
}