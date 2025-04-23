package com.fiap.medieval.repository;

import com.fiap.medieval.model.Personagem;
import com.fiap.medieval.model.enums.ClassePersonagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonagemRepository extends JpaRepository<Personagem, Long> {
    List<Personagem> findByNomeContainingIgnoreCase(String nome);
    List<Personagem> findByClasse(ClassePersonagem classe);
}
