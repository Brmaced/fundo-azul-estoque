package br.com.fundoazul.estoque.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fundoazul.estoque.model.MovimentacaoEstoque;

public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {
    List<MovimentacaoEstoque> findByProdutoId(Long produtoId);

}
