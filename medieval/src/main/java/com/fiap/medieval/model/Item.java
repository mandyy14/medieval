package com.fiap.medieval.model;

import com.fiap.medieval.model.enums.ItemRaridade;
import com.fiap.medieval.model.enums.ItemTipo;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "itens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do item é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "Tipo do item é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemTipo tipo;

    @NotNull(message = "Raridade do item é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemRaridade raridade;

    @NotNull(message = "Preço do item é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser positivo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personagem_id", referencedColumnName = "id")
    private Personagem dono;
}
