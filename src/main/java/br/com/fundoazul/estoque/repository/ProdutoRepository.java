package br.com.fundoazul.estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fundoazul.estoque.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {}