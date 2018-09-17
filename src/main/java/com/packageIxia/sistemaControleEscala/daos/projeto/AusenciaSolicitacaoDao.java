package com.packageIxia.sistemaControleEscala.daos.projeto;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.packageIxia.sistemaControleEscala.models.projeto.AusenciaSolicitacao;

@Repository
public interface AusenciaSolicitacaoDao extends CrudRepository<AusenciaSolicitacao, Long> {

	List<AusenciaSolicitacao> findAllByUsuarioId(long id);

	List<AusenciaSolicitacao> findAllByUsuarioAprovacaoId(long id);

	List<AusenciaSolicitacao> findAllByGerenciaAprovacaoId(long id);

	List<AusenciaSolicitacao> findAllByProjetoEscalaId(long escalaId);

	boolean existsByUsuarioId(long prestadorId);

//	@Query("from AusenciaSolicitacao a inner join AusenciaReposicao b on a.id=b.ausenciaSolicitacaoId WHERE year(a.dataInicio)=:year and month(a.dataInicio)=:month and projeto_escala_id in (:projetoEscalaIds)")
//	List<AusenciaSolicitacao> findAllByProjetoIdAndMonth(@Param("year")int year, @Param("month")int month, @Param("projetoEscalaIds")List<Long> projetoEscalaIds);
//
//	@Query("from AusenciaSolicitacao a inner join AusenciaReposicao b on a.id=b.ausenciaSolicitacaoId WHERE year(a.dataInicio)=:year and month(a.dataInicio)=:month and projeto_escala_id=:projetoEscalaId")
//	List<AusenciaSolicitacao> findAllByProjetoEscalaIdAndMonth(@Param("year")int year, @Param("month")int month, @Param("projetoEscalaId")long projetoEscalaId);

}
