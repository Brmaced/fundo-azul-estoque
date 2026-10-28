package br.com.fundoazul.estoque.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fundoazul.estoque.dto.MovimentacaoRequest;
import br.com.fundoazul.estoque.dto.MovimentacaoResponse;
import br.com.fundoazul.estoque.exception.RecursoNaoEncontradoException;
import br.com.fundoazul.estoque.exception.RegraDeNegocioException;
import br.com.fundoazul.estoque.model.MovimentacaoEstoque;
import br.com.fundoazul.estoque.model.Produto;
import br.com.fundoazul.estoque.model.TipoMovimentacao;
import br.com.fundoazul.estoque.repository.MovimentacaoEstoqueRepository;
import br.com.fundoazul.estoque.repository.ProdutoRepository;


@Service
public class MovimentacaoService {

    
    private static final Long USUARIO_SISTEMA_ID = 1L;

    private final ProdutoRepository produtoRepository;
    private final MovimentacaoEstoqueRepository movimentacaoRepository;

    public MovimentacaoService(ProdutoRepository produtoRepository, MovimentacaoEstoqueRepository movimentacaoRepository) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @Transactional
    public MovimentacaoResponse registrar(Long produtoId, MovimentacaoRequest req) {
        if (req.tipo() == TipoMovimentacao.VENDA) {
            throw new RegraDeNegocioException("Movimentações do tipo VENDA são geradas pelas vendas");
        }
        Produto produto = obterProduto(produtoId);
        produto.movimentar(req.tipo(), req.quantidade());

        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque(
                produto, req.tipo(), req.quantidade(), USUARIO_SISTEMA_ID, req.observacao());
        return MovimentacaoResponse.de(movimentacaoRepository.save(movimentacao));
    }

    @Transactional(readOnly = true)
    public List<MovimentacaoResponse> listar(Long produtoId) {
        if (!produtoRepository.existsById(produtoId)) {
            throw new RecursoNaoEncontradoException("Produto não encontrado: " + produtoId);
        }
        return movimentacaoRepository.findByProdutoId(produtoId).stream().map(MovimentacaoResponse::de).toList();
    }

    private Produto obterProduto(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + id));
    }
}

