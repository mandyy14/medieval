package com.fiap.medieval.service;

import com.fiap.medieval.dto.personagem.*;
import com.fiap.medieval.exception.ResourceNotFoundException;
import com.fiap.medieval.model.Personagem;
import com.fiap.medieval.repository.PersonagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonagemService {

    @Autowired
    private PersonagemRepository repository;

    public List<PersonagemRespostaDTO> listar(int page, int size) {
        return repository.findAll(PageRequest.of(page, size))
                .stream()
                .map(p -> new PersonagemRespostaDTO(p.getId(), p.getNome(), p.getClasse(), p.getNivel(), p.getMoedas()))
                .collect(Collectors.toList());
    }

    public PersonagemRespostaDTO buscarPorId(Long id) {
        var p = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personagem não encontrado"));
        return new PersonagemRespostaDTO(p.getId(), p.getNome(), p.getClasse(), p.getNivel(), p.getMoedas());
    }

    public PersonagemRespostaDTO criar(PersonagemCriacaoDTO dto) {
        var personagem = new Personagem();
        personagem.setNome(dto.getNome());
        personagem.setClasse(dto.getClasse());
        personagem.setNivel(dto.getNivel());
        personagem.setMoedas(dto.getMoedas());

        var salvo = repository.save(personagem);
        return new PersonagemRespostaDTO(salvo.getId(), salvo.getNome(), salvo.getClasse(), salvo.getNivel(), salvo.getMoedas());
    }

    public PersonagemRespostaDTO atualizar(Long id, PersonagemAtualizacaoDTO dto) {
        var personagem = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personagem não encontrado"));

        personagem.setNome(dto.getNome());
        personagem.setClasse(dto.getClasse());
        personagem.setNivel(dto.getNivel());
        personagem.setMoedas(dto.getMoedas());

        var salvo = repository.save(personagem);
        return new PersonagemRespostaDTO(salvo.getId(), salvo.getNome(), salvo.getClasse(), salvo.getNivel(), salvo.getMoedas());
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Personagem não encontrado");
        }
        repository.deleteById(id);
    }
}
