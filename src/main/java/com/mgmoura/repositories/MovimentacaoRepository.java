package com.mgmoura.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mgmoura.entities.Movimentacao;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Integer>{
	

	@Query("SELECT m FROM Movimentacao m " +
			"JOIN FETCH m.produto p " +
			"WHERE m.dataMovimentacao >= :dataInicial " +
			"AND m.dataMovimentacao <= :dataFinal " +
			"ORDER BY m.dataMovimentacao DESC")
	List<Movimentacao> findByDatas(
			@Param("dataInicial") Date dataMin,
			@Param("dataFinal") Date dataMax);

}
