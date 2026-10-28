package br.com.fundoazul.estoque.dto;

import java.math.BigDecimal;

import br.com.fundoazul.estoque.model.Produto;
import br.com.fundoazul.estoque.model.TipoProduto;

// Classe de resposta para representar os dados de um produto de forma simplificada
public record ProdutoResponse(
        Long id,
        String nome,
        String categoria,
        TipoProduto tipo,
        BigDecimal precoCusto,
        BigDecimal precoVenda,
        int quantidadeAtual,
        int estoqueMinimo,
        boolean ativo) {

    public static ProdutoResponse de(Produto p) {
        return new ProdutoResponse(
                p.getId(),
                p.getNome(),
                p.getCategoria().getNome(),
                p.getTipo(),
                p.getPrecoCusto(),
                p.getPrecoVenda(),
                p.getQuantidadeAtual(),
                p.getEstoqueMinimo(),
                p.isAtivo());
    }
}