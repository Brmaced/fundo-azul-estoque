package br.com.fundoazul.estoque.dto;

import java.math.BigDecimal;

import br.com.fundoazul.estoque.model.TipoProduto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Classe que representa a requisição para criar ou atualizar um produto no sistema de estoque
public record ProdutoRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres")
        String nome,

        @NotNull(message = "A categoria é obrigatória")
        Long categoriaId,

        @NotNull(message = "O tipo é obrigatório")
        TipoProduto tipo,

        @NotNull(message = "O preço de custo é obrigatório")
        @DecimalMin(value = "0.00", message = "O preço de custo não pode ser negativo")
        BigDecimal precoCusto,

        @NotNull(message = "O preço de venda é obrigatório")
        @DecimalMin(value = "0.01", message = "O preço de venda deve ser maior que zero")
        BigDecimal precoVenda,

        @Min(value = 0, message = "O estoque mínimo não pode ser negativo")
        int estoqueMinimo) {
}