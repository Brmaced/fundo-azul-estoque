package br.com.fundoazul.estoque.dto;

import br.com.fundoazul.estoque.model.TipoMovimentacao;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MovimentacaoRequest(
    @NotNull (message = "O tipo da movimentação é obrigatório")
    TipoMovimentacao tipo,

    @Min (value = 1, message = "A quantidade deve ser maior que zero")
    int quantidade,

    @Size (max = 200, message = "A observação deve ter no maximo 200 caracteres")
    String observacao) {

}
