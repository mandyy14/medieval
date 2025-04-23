package com.fiap.medieval.dto.item;

import com.fiap.medieval.model.enums.ItemRaridade;
import com.fiap.medieval.model.enums.ItemTipo;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemRespostaDTO {
    private Long id;
    private String nome;
    private ItemTipo tipo;
    private ItemRaridade raridade;
    private BigDecimal preco;
}
