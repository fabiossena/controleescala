package com.packageIxia.sistemaControleEscala.daos.projeto;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.packageIxia.sistemaControleEscala.models.projeto.HoraAprovacao;

@Repository
public interface HoraAprovacaoDao extends CrudRepository<HoraAprovacao, Long> {

	List<HoraAprovacao> findByPrestadorId(long id);

	@Query("from HoraAprovacao h where year(h.data) = :ano and month(h.data) = :mes")
	List<HoraAprovacao> findAllByDate(@Param("ano")int ano, @Param("mes")int mes);

	@Query("from HoraAprovacao h where year(h.data) = :ano and month(h.data) = :mes and prestador_id=:prestadorId")
	HoraAprovacao findAllByDateAndPrestadorId(@Param("ano")int ano, @Param("mes")int mes, @Param("prestadorId")long prestadorId);

	@Transactional
	@Modifying
	@Query("update HoraAprovacao h set h.aceitePrestador = 0,  h.motivoRecusaPrestador=:observacao, h.totalHoras=:totalHoras, h.totalValor=:totalValor where h.id = :id")
	int updateAprovacaoReset(@Param("id")long id, @Param("observacao")String observacao, @Param("totalHoras")double totalHoras, @Param("totalValor")double totalValor);

	@Transactional
	@Modifying
	@Query("update HoraAprovacao h set aceiteAprovador = :aprova, aprovador_id = :aprovadorId, h.motivoRecusaAprovador = :observacao where h.id = :id")
	int updateAprovacaoResponsavel(@Param("aprova")int aprova, @Param("id")long id, @Param("aprovadorId")long aprovadorId, @Param("observacao")String observacao);

	@Transactional
	@Modifying
	@Query("update HoraAprovacao h set h.aceitePrestador = :aprova, prestador_id = :aprovadorId, h.motivoRecusaPrestador = :observacao where h.id = :id")
	int updateAprovacaoPrestador(@Param("aprova")int aprova, @Param("id")long id, @Param("aprovadorId")long aprovadorId, @Param("observacao")String observacao);

	@Transactional
	@Modifying
	@Query("update HoraAprovacao h set h.arquivoNota = :arquivoNota where h.id = :id")
	void updateNota(@Param("id")long id, @Param("arquivoNota")String arquivoNota);
	
	@Transactional
	@Modifying
	@Query("update HoraAprovacao h set h.aceiteAprovador = 3 where h.id in (:ids)")
	void updateCsvGerado(@Param("ids")List<Long> ids);

	boolean existsByPrestadorId(long prestadorId);

}
