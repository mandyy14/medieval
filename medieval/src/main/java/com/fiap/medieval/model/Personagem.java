package com.fiap.medieval.model;

import com.fiap.medieval.model.enums.ClassePersonagem;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Enumerated(EnumType.STRING)
    private ClassePersonagem classe;

    @Min(1)
    @Max(99)
    private int nivel;

    @Min(0)
    private double moedas;
}
