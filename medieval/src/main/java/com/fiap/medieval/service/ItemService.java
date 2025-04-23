package com.fiap.medieval.service;

import com.fiap.medieval.dto.item.ItemAtualizacaoDTO;
import com.fiap.medieval.dto.item.ItemCriacaoDTO;
import com.fiap.medieval.dto.item.ItemRespostaDTO;
import com.fiap.medieval.exception.ResourceNotFoundException;
import com.fiap.medieval.model.Item;
import com.fiap.medieval.model.Personagem;
import com.fiap.medieval.model.enums.ItemRaridade;
import com.fiap.medieval.model.enums.ItemTipo;
import com.fiap.medieval.repository.ItemRepository;
import com.fiap.medieval.repository.PersonagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final PersonagemRepository personagemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, PersonagemRepository personagemRepository) {
        this.itemRepository = itemRepository;
        this.personagemRepository = personagemRepository;
    }

    @Transactional
    public ItemRespostaDTO criarItem(ItemCriacaoDTO itemDTO) {
        Item item = new Item();
        item.setNome(itemDTO.getNome());
        item.setTipo(itemDTO.getTipo());
        item.setRaridade(itemDTO.getRaridade());
        item.setPreco(itemDTO.getPreco());

        if (itemDTO.getPersonagemId() != null) {
            Personagem dono = personagemRepository.findById(itemDTO.getPersonagemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Personagem não encontrado com id: " + itemDTO.getPersonagemId()));
            item.setDono(dono);
        }

        Item itemSalvo = itemRepository.save(item);
        return mapEntityToRespostaDTO(itemSalvo);
    }

    @Transactional(readOnly = true)
    public ItemRespostaDTO buscarItemPorId(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com id: " + id));
        return mapEntityToRespostaDTO(item);
    }

    @Transactional(readOnly = true)
    public Page<ItemRespostaDTO> listarTodosItens(Pageable pageable) {
        Page<Item> itensPage = itemRepository.findAll(pageable);
        return itensPage.map(this::mapEntityToRespostaDTO);
    }

    @Transactional
    public ItemRespostaDTO atualizarItem(Long id, ItemAtualizacaoDTO itemDTO) {
        Item itemExistente = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com id: " + id));

        itemExistente.setNome(itemDTO.getNome());
        itemExistente.setTipo(itemDTO.getTipo());
        itemExistente.setRaridade(itemDTO.getRaridade());
        itemExistente.setPreco(itemDTO.getPreco());

        Personagem novoDono = null;
        if (itemDTO.getPersonagemId() != null) {
            novoDono = personagemRepository.findById(itemDTO.getPersonagemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Personagem não encontrado com id: " + itemDTO.getPersonagemId()));
        }
        itemExistente.setDono(novoDono);

        Item itemAtualizado = itemRepository.save(itemExistente);
        return mapEntityToRespostaDTO(itemAtualizado);
    }

    @Transactional
    public void deletarItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item não encontrado com id: " + id);
        }
        itemRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<ItemRespostaDTO> buscarItensPorNomeParcial(String nome, Pageable pageable) {
        Page<Item> itensPage = itemRepository.findByNomeContainingIgnoreCase(nome, pageable);
        return itensPage.map(this::mapEntityToRespostaDTO);
    }

    @Transactional(readOnly = true)
    public Page<ItemRespostaDTO> buscarItensPorTipo(ItemTipo tipo, Pageable pageable) {
        Page<Item> itensPage = itemRepository.findByTipo(tipo, pageable);
        return itensPage.map(this::mapEntityToRespostaDTO);
    }

    @Transactional(readOnly = true)
    public Page<ItemRespostaDTO> buscarItensPorPreco(BigDecimal precoMin, BigDecimal precoMax, Pageable pageable) {
        if (precoMin == null) precoMin = BigDecimal.ZERO;
        if (precoMax == null) precoMax = BigDecimal.valueOf(Double.MAX_VALUE);
        if (precoMin.compareTo(precoMax) > 0) {
            throw new IllegalArgumentException("Preço mínimo não pode ser maior que o preço máximo.");
        }

        Page<Item> itensPage = itemRepository.findByPrecoBetween(precoMin, precoMax, pageable);
        return itensPage.map(this::mapEntityToRespostaDTO);
    }

    @Transactional(readOnly = true)
    public Page<ItemRespostaDTO> buscarItensPorRaridade(ItemRaridade raridade, Pageable pageable) {
        Page<Item> itensPage = itemRepository.findByRaridade(raridade, pageable);
        return itensPage.map(this::mapEntityToRespostaDTO);
    }


    @Transactional(readOnly = true)
    public Page<ItemRespostaDTO> buscarItensPorDono(Long personagemId, Pageable pageable) {
        if (!personagemRepository.existsById(personagemId)) {
             throw new ResourceNotFoundException("Personagem não encontrado com id: " + personagemId);
        }
        Page<Item> itensPage = itemRepository.findByDonoId(personagemId, pageable);
        return itensPage.map(this::mapEntityToRespostaDTO);
    }

    private ItemRespostaDTO mapEntityToRespostaDTO(Item item) {
        ItemRespostaDTO dto = new ItemRespostaDTO();
        dto.setId(item.getId());
        dto.setNome(item.getNome());
        dto.setTipo(item.getTipo());
        dto.setRaridade(item.getRaridade());
        dto.setPreco(item.getPreco());

        return dto;
    }
}
