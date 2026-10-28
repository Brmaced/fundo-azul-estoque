package br.com.fundoazul.estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fundoazul.estoque.model.Categoria;

// Interface de repositório para a entidade Categoria, permitindo operações CRUD e consultas personalizadas
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}