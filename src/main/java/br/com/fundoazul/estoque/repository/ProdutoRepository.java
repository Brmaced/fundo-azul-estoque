package br.com.fundoazul.estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fundoazul.estoque.model.Produto;

// Interface de repositório para a entidade Produto, permitindo operações CRUD e consultas personalizadas
public interface ProdutoRepository extends JpaRepository<Produto, Long> {}