package br.com.orderhub.estoque_service.adapter.controller;

import br.com.orderhub.core.controller.EstoqueController;
import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.dto.estoques.ItemEstoqueDTO;
import br.com.orderhub.estoque_service.adapter.dto.EstoqueApiRequestDto;
import br.com.orderhub.estoque_service.adapter.dto.EstoqueApiResponseDto;
import br.com.orderhub.estoque_service.adapter.mapper.EstoqueApiDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoques")
@RequiredArgsConstructor
public class EstoqueApiController {

    private final EstoqueApiDtoMapper mapper;
    private final EstoqueController estoqueController;

    /**
     * Endpoint para repor o estoque de um item específico.
     * @param id O ID do estoque (geralmente o mesmo ID do produto).
     * @param dto O corpo da requisição contendo a quantidade a ser adicionada.
     * @return O estado atualizado do estoque.
     */
    @PostMapping("/{id}/repor")
    public ResponseEntity<EstoqueApiResponseDto> reporEstoque(@PathVariable("id") Long id, @Valid @RequestBody EstoqueApiRequestDto dto) {
        Estoque atualizado = estoqueController.repor(id, dto.quantidade());
        return ResponseEntity.ok(mapper.toResponse(atualizado));
    }

    /**
     * Endpoint para dar baixa no estoque de um item específico.
     * @param id O ID do estoque.
     * @param dto O corpo da requisição contendo a quantidade a ser removida.
     * @return O estado atualizado do estoque.
     */
    @PostMapping("/{id}/baixar")
    public ResponseEntity<EstoqueApiResponseDto> baixarEstoque(@PathVariable("id") Long id, @Valid @RequestBody EstoqueApiRequestDto dto) {
        Estoque atualizado = estoqueController.baixar(id, dto.quantidade());
        return ResponseEntity.ok(mapper.toResponse(atualizado));
    }

    /**
     * Endpoint para consultar o estoque de um item pelo seu ID.
     * @param id O ID do estoque.
     * @return O estado atual do estoque.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EstoqueApiResponseDto> buscarPorId(@PathVariable("id") Long id) {
        Estoque estoque = estoqueController.consultarPorId(id);
        return ResponseEntity.ok(mapper.toResponse(estoque));
    }
    
    /**
     * Endpoint para dar baixa em múltiplos itens de forma atômica.
     */
    @PostMapping("/baixar-multiplos")
    public ResponseEntity<Void> baixarMultiplos(@Valid @RequestBody List<ItemEstoqueDTO> itens) {
        estoqueController.baixarEstoqueMultiplo(itens);
        return ResponseEntity.noContent().build();
    }
}