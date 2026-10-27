package br.com.fundoazul.estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fundoazul.estoque.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}