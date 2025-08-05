package br.com.orderhub.estoque_service.adapter.controller;

import br.com.orderhub.core.controller.EstoqueController;
import br.com.orderhub.core.dto.estoques.AtualizarEstoqueDTO;
import br.com.orderhub.core.dto.estoques.EstoqueDTO;
import br.com.orderhub.estoque_service.adapter.dto.EstoqueApiRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/estoques")
@RequiredArgsConstructor
public class EstoqueApiController {
    private final EstoqueController estoqueController;

    /**
     * Endpoint para repor o estoque de um item específico.
     * @param idProduto O ID do estoque (geralmente o mesmo ID do produto).
     * @param dto O corpo da requisição contendo a quantidade a ser adicionada.
     * @return O estado atualizado do estoque.
     */
    @PostMapping("/{idProduto}/repor")
    public ResponseEntity<EstoqueDTO> reporEstoque(@PathVariable("idProduto") Long idProduto, @Valid @RequestBody EstoqueApiRequestDto dto) {
        AtualizarEstoqueDTO atualizarEstoqueDTO = new AtualizarEstoqueDTO(idProduto, dto.quantidade());
        return ResponseEntity.ok(estoqueController.reporEstoque(atualizarEstoqueDTO));
    }

    /**
     * Endpoint para dar baixa no estoque de um item específico.
     * @param idProduto O ID do estoque.
     * @param dto O corpo da requisição contendo a quantidade a ser removida.
     * @return O estado atualizado do estoque.
     */
    @PostMapping("/{idProduto}/baixar")
    public ResponseEntity<EstoqueDTO> baixarEstoque(@PathVariable("idProduto") Long idProduto, @Valid @RequestBody EstoqueApiRequestDto dto) {
        AtualizarEstoqueDTO atualizarEstoqueDTO = new AtualizarEstoqueDTO(idProduto, dto.quantidade());
        return ResponseEntity.ok(estoqueController.baixarEstoque(atualizarEstoqueDTO));
    }

    /**
     * Endpoint para consultar a disponibilidade no estoque de um item pelo ID do produto.
     * @param idProduto O ID do estoque.
     * @return O estado atual do estoque.
     */
    @GetMapping("/{idProduto}")
    public ResponseEntity<EstoqueDTO> consultarEstoquePorIdProduto(@PathVariable("idProduto") Long idProduto) {
        return ResponseEntity.ok(estoqueController.consultarEstoquePorIdProduto(idProduto));
    }

    @PostMapping("/adicionar")
    public ResponseEntity<EstoqueDTO> adicionarProdutoNoEstoque(@RequestBody AtualizarEstoqueDTO dto) {
        return ResponseEntity.ok(estoqueController.adicionarProdutoNoEstoque(dto));
    }

    @DeleteMapping("/{idProduto}/remover")
    public void removerProdutoNoEstoque(@PathVariable("idProduto") Long idProduto) {
        estoqueController.removerProdutoNoEstoque(idProduto);
    }
}