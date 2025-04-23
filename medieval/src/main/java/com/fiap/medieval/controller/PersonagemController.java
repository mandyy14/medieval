package com.fiap.medieval.controller;

import com.fiap.medieval.dto.personagem.*;
import com.fiap.medieval.model.enums.ClassePersonagem;
import com.fiap.medieval.service.PersonagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personagens")
public class PersonagemController {

    @Autowired
    private PersonagemService service;

    @GetMapping
    public List<PersonagemRespostaDTO> listar(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return service.listar(page, size);
    }

    @GetMapping("/{id}")
    public PersonagemRespostaDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public PersonagemRespostaDTO criar(@RequestBody @Valid PersonagemCriacaoDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    public PersonagemRespostaDTO atualizar(@PathVariable Long id,
                                           @RequestBody @Valid PersonagemAtualizacaoDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
