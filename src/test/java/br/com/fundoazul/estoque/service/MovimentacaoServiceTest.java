package br.com.fundoazul.estoque.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fundoazul.estoque.dto.MovimentacaoRequest;
import br.com.fundoazul.estoque.dto.MovimentacaoResponse;
import br.com.fundoazul.estoque.exception.RecursoNaoEncontradoException;
import br.com.fundoazul.estoque.exception.RegraDeNegocioException;
import br.com.fundoazul.estoque.model.MovimentacaoEstoque;
import br.com.fundoazul.estoque.model.Produto;
import br.com.fundoazul.estoque.model.TipoMovimentacao;
import br.com.fundoazul.estoque.repository.MovimentacaoEstoqueRepository;
import br.com.fundoazul.estoque.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class MovimentacaoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private MovimentacaoEstoqueRepository movimentacaoRepository;

    @InjectMocks
    private MovimentacaoService service;

    @Test
    void registraEntradaEAtualizaOEstoque() {
        Produto produto = new Produto();
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(movimentacaoRepository.save(any(MovimentacaoEstoque.class)))
                .thenAnswer(invocacao -> invocacao.getArgument(0));

        MovimentacaoResponse resposta = service.registrar(1L,
                new MovimentacaoRequest(TipoMovimentacao.ENTRADA, 10, "compra inicial"));

        assertThat(produto.getQuantidadeAtual()).isEqualTo(10);
        assertThat(resposta.tipo()).isEqualTo(TipoMovimentacao.ENTRADA);
        assertThat(resposta.quantidade()).isEqualTo(10);
        verify(movimentacaoRepository).save(any(MovimentacaoEstoque.class));
    }

    @Test
    void recusaMovimentacaoDoTipoVenda() {
        assertThatThrownBy(() -> service.registrar(1L,
                new MovimentacaoRequest(TipoMovimentacao.VENDA, 1, null)))
                .isInstanceOf(RegraDeNegocioException.class);

        verifyNoInteractions(movimentacaoRepository);
    }

    @Test
    void lancaErroQuandoOProdutoNaoExiste() {
        when(produtoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.registrar(99L,
                new MovimentacaoRequest(TipoMovimentacao.ENTRADA, 1, null)))
                .isInstanceOf(RecursoNaoEncontradoException.class);

        verify(movimentacaoRepository, never()).save(any());
    }

    @Test
    void saidaSemEstoqueNaoGravaMovimentacao() {
        Produto produto = new Produto();
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        assertThatThrownBy(() -> service.registrar(1L,
                new MovimentacaoRequest(TipoMovimentacao.PERDA, 1, null)))
                .isInstanceOf(RegraDeNegocioException.class);

        verify(movimentacaoRepository, never()).save(any());
    }
}