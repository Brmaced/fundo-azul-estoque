package br.com.fundoazul.estoque.model;

public enum TipoMovimentacao {
    ENTRADA(true),
    VENDA(false),
    PERDA(false),
    AJUSTE_POSITIVO(true),
    AJUSTE_NEGATIVO(false);

    private final boolean aumentaEstoque;

    TipoMovimentacao(boolean aumentaEstoque) {
        this.aumentaEstoque = aumentaEstoque;
    }

    public boolean aumentaEstoque() {
        return aumentaEstoque;
    }
}
