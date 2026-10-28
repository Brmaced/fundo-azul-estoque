package br.com.fundoazul.estoque.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fundoazul.estoque.dto.ProdutoRequest;
import br.com.fundoazul.estoque.dto.ProdutoResponse;
import br.com.fundoazul.estoque.exception.RecursoNaoEncontradoException;
import br.com.fundoazul.estoque.model.Categoria;
import br.com.fundoazul.estoque.model.Produto;
import br.com.fundoazul.estoque.repository.CategoriaRepository;
import br.com.fundoazul.estoque.repository.ProdutoRepository;

// Serviço responsável por gerenciar operações relacionadas a produtos, incluindo criação, atualização, listagem e desativação.

@Service 
public class ProdutoService {
    
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional 
    public ProdutoResponse criar(ProdutoRequest req) {
        Produto produto = new Produto();
        preencher(produto, req);
        return ProdutoResponse.de(produtoRepository.save(produto));
    }

    @Transactional 
    public ProdutoResponse atualizar (Long id, ProdutoRequest reg){
    
        Produto produto = obter(id);
        preencher(produto, reg);
        return ProdutoResponse.de(produto);
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponse> listar() {
        return produtoRepository.findAll().stream().map(ProdutoResponse::de).toList();
    }


    @Transactional(readOnly = true)
    public ProdutoResponse buscar(Long id) {
        return ProdutoResponse.de(obter(id));
    }

    @Transactional
    public void desativar(Long id) {
        obter(id).setAtivo(false);
    }

    private Produto obter(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + id));
    }

    private void preencher(Produto produto, ProdutoRequest req) {
        Categoria categoria = categoriaRepository.findById(req.categoriaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Categoria não encontrada: " + req.categoriaId()));
        produto.setNome(req.nome());
        produto.setCategoria(categoria);
        produto.setTipo(req.tipo());
        produto.setPrecoCusto(req.precoCusto());
        produto.setPrecoVenda(req.precoVenda());
        produto.setEstoqueMinimo(req.estoqueMinimo());
    }
}