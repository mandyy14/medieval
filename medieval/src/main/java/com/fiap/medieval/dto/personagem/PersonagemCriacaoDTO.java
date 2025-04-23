package com.fiap.medieval.dto.personagem;

import com.fiap.medieval.model.enums.ClassePersonagem;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonagemCriacaoDTO {

    @NotBlank
    private String nome;

    @NotNull
    private ClassePersonagem classe;

    @Min(1)
    @Max(99)
    private int nivel;

    @Min(0)
    private double moedas;
}
