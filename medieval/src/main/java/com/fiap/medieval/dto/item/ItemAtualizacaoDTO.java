package com.fiap.medieval.dto.item;

import com.fiap.medieval.model.enums.ItemRaridade;
import com.fiap.medieval.model.enums.ItemTipo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ItemAtualizacaoDTO {

    @NotBlank(message = "Nome do item é obrigatório")
    private String nome;

    @NotNull(message = "Tipo do item é obrigatório")
    private ItemTipo tipo;

    @NotNull(message = "Raridade do item é obrigatória")
    private ItemRaridade raridade;

    @NotNull(message = "Preço do item é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser positivo")
    private BigDecimal preco;

    private Long personagemId;
}
