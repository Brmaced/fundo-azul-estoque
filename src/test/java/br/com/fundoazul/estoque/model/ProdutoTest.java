package br.com.fundoazul.estoque.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import br.com.fundoazul.estoque.exception.RegraDeNegocioException;


class ProdutoTest {

    @Test
    void entradaAumentaOEstoque() {
        Produto produto = new Produto();

        produto.movimentar(TipoMovimentacao.ENTRADA, 10);

        assertThat(produto.getQuantidadeAtual()).isEqualTo(10);
    }

    @Test
    void perdaDiminuiOEstoque() {
        Produto produto = new Produto();
        produto.movimentar(TipoMovimentacao.ENTRADA, 10);

        produto.movimentar(TipoMovimentacao.PERDA, 3);

        assertThat(produto.getQuantidadeAtual()).isEqualTo(7);
    }

    @Test
    void permiteZerarOEstoque() {
        Produto produto = new Produto();
        produto.movimentar(TipoMovimentacao.ENTRADA, 5);

        produto.movimentar(TipoMovimentacao.AJUSTE_NEGATIVO, 5);

        assertThat(produto.getQuantidadeAtual()).isZero();
    }

    @Test
    void saidaMaiorQueOEstoqueLancaExcecaoENaoAlteraOSaldo() {
        Produto produto = new Produto();
        produto.movimentar(TipoMovimentacao.ENTRADA, 5);

        assertThatThrownBy(() -> produto.movimentar(TipoMovimentacao.PERDA, 6))
                .isInstanceOf(RegraDeNegocioException.class)
                .hasMessageContaining("Estoque insuficiente");

        assertThat(produto.getQuantidadeAtual()).isEqualTo(5);
    }

    @Test
    void quantidadeZeroOuNegativaLancaExcecao() {
        Produto produto = new Produto();

        assertThatThrownBy(() -> produto.movimentar(TipoMovimentacao.ENTRADA, 0))
                .isInstanceOf(RegraDeNegocioException.class);
        assertThatThrownBy(() -> produto.movimentar(TipoMovimentacao.ENTRADA, -2))
                .isInstanceOf(RegraDeNegocioException.class);
    }
}
