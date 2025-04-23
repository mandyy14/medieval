package com.fiap.medieval.dto.personagem;

import com.fiap.medieval.model.enums.ClassePersonagem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PersonagemRespostaDTO {
    private Long id;
    private String nome;
    private ClassePersonagem classe;
    private int nivel;
    private double moedas;
}
