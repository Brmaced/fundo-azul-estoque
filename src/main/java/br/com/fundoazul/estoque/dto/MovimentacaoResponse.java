package br.com.fundoazul.estoque.dto;

import java.time.LocalDateTime;

import br.com.fundoazul.estoque.model.MovimentacaoEstoque;
import br.com.fundoazul.estoque.model.TipoMovimentacao;

public record MovimentacaoResponse(
        Long id,
        Long produtoId,
        TipoMovimentacao tipo,
        int quantidade,
        LocalDateTime dataHora,
        Long usuarioId,
        String observacao) {

    public static MovimentacaoResponse de(MovimentacaoEstoque m) {
        return new MovimentacaoResponse(
                m.getId(),
                m.getProduto().getId(),
                m.getTipo(),
                m.getQuantidade(),
                m.getDataHora(),
                m.getUsuarioId(),
                m.getObservacao());
    }
}