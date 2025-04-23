package com.fiap.medieval.controller;

import com.fiap.medieval.dto.item.ItemAtualizacaoDTO;
import com.fiap.medieval.dto.item.ItemCriacaoDTO;
import com.fiap.medieval.dto.item.ItemRespostaDTO;
import com.fiap.medieval.model.enums.ItemRaridade;
import com.fiap.medieval.model.enums.ItemTipo;
import com.fiap.medieval.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("/api/itens") // Define o path base para os endpoints de item
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // POST /api/itens - Criar um novo item
    @PostMapping
    public ResponseEntity<ItemRespostaDTO> criarItem(@Valid @RequestBody ItemCriacaoDTO itemDTO) {
        ItemRespostaDTO novoItem = itemService.criarItem(itemDTO);
        // Retorna status 201 Created com a URI do novo recurso no cabeçalho Location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoItem.getId())
                .toUri();
        return ResponseEntity.created(location).body(novoItem);
    }

    // GET /api/itens/{id} - Buscar item por ID
    @GetMapping("/{id}")
    public ResponseEntity<ItemRespostaDTO> buscarItemPorId(@PathVariable Long id) {
        ItemRespostaDTO item = itemService.buscarItemPorId(id);
        return ResponseEntity.ok(item);
    }

    // GET /api/itens - Listar todos os itens com paginação
    // Parâmetros de paginação: ?page=0&size=10&sort=nome,asc
    @GetMapping
    public ResponseEntity<Page<ItemRespostaDTO>> listarTodosItens(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<ItemRespostaDTO> itensPage = itemService.listarTodosItens(pageable);
        return ResponseEntity.ok(itensPage);
    }

    // PUT /api/itens/{id} - Atualizar um item existente
    @PutMapping("/{id}")
    public ResponseEntity<ItemRespostaDTO> atualizarItem(@PathVariable Long id, @Valid @RequestBody ItemAtualizacaoDTO itemDTO) {
        ItemRespostaDTO itemAtualizado = itemService.atualizarItem(id, itemDTO);
        return ResponseEntity.ok(itemAtualizado);
    }

    // DELETE /api/itens/{id} - Deletar um item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        itemService.deletarItem(id);
        return ResponseEntity.noContent().build(); // Retorna status 204 No Content
    }

    // --- Endpoints de Filtro ---

    // GET /api/itens/buscar/nome?nome=Espada
    @GetMapping("/buscar/nome")
    public ResponseEntity<Page<ItemRespostaDTO>> buscarItensPorNomeParcial(
            @RequestParam String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<ItemRespostaDTO> itensPage = itemService.buscarItensPorNomeParcial(nome, pageable);
        return ResponseEntity.ok(itensPage);
    }

    // GET /api/itens/buscar/tipo?tipo=ARMA
    @GetMapping("/buscar/tipo")
    public ResponseEntity<Page<ItemRespostaDTO>> buscarItensPorTipo(
            @RequestParam ItemTipo tipo,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<ItemRespostaDTO> itensPage = itemService.buscarItensPorTipo(tipo, pageable);
        return ResponseEntity.ok(itensPage);
    }

    // GET /api/itens/buscar/preco?min=10.50&max=100.00
    @GetMapping("/buscar/preco")
    public ResponseEntity<Page<ItemRespostaDTO>> buscarItensPorPreco(
            @RequestParam(required = false) BigDecimal min, // required = false para permitir busca só por max ou só por min (ajuste no service se necessário)
            @RequestParam(required = false) BigDecimal max,
            @PageableDefault(size = 10, sort = "preco") Pageable pageable) {
        Page<ItemRespostaDTO> itensPage = itemService.buscarItensPorPreco(min, max, pageable);
        return ResponseEntity.ok(itensPage);
    }

    // GET /api/itens/buscar/raridade?raridade=LENDARIO
    @GetMapping("/buscar/raridade")
    public ResponseEntity<Page<ItemRespostaDTO>> buscarItensPorRaridade(
            @RequestParam ItemRaridade raridade,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<ItemRespostaDTO> itensPage = itemService.buscarItensPorRaridade(raridade, pageable);
        return ResponseEntity.ok(itensPage);
    }


    // --- Endpoint "Carry Method" ---

    // GET /api/itens/personagem/{personagemId} - Buscar itens por ID do dono
    // Alternativa de path: /api/personagens/{personagemId}/itens (mais RESTful, mas requer ajuste na estrutura ou no controller de Personagem)
    @GetMapping("/personagem/{personagemId}")
    public ResponseEntity<Page<ItemRespostaDTO>> buscarItensPorDono(
            @PathVariable Long personagemId,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<ItemRespostaDTO> itensPage = itemService.buscarItensPorDono(personagemId, pageable);
        return ResponseEntity.ok(itensPage);
    }
}
