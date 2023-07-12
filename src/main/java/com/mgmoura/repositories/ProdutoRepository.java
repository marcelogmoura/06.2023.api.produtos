package com.mgmoura.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mgmoura.entities.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	@Query("SELECT p FROM Produto p ORDER BY p.nome")
	List<Produto> findAll();

	@Query("SELECT COUNT(m) FROM Movimentacao m WHERE m.produto.idProduto=:idProduto")
	Integer countByMovimentacao(@Param("idProduto") Integer idProduto);


}
