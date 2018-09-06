package com.packageIxia.SistemaControleEscala.Daos;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.OrderBy;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.packageIxia.SistemaControleEscala.Models.Projeto.HoraTrabalhada;

@Repository
public interface HoraTrabalhadaDao extends CrudRepository<HoraTrabalhada, Long> {
	
	@OrderBy("ht.dataHoraInicio")
	List<HoraTrabalhada> findAllByHoraAprovacaoId(Long id);

	@OrderBy("ht.dataHoraInicio")
	@Query("select ht from HoraTrabalhada ht left join HoraAprovacao ha on ha.id=hora_aprovacao_id where prestador_id=:prestadorId")
	List<HoraTrabalhada> findByPrestadorId(@Param("prestadorId")Long id);

	@Query("select h from HoraTrabalhada h left join HoraAprovacao ha on ha.id=hora_aprovacao_id where prestador_id = :prestadorId and (h.dataHoraInicio between :dataHoraInicio and :dataHoraFim or  h.dataHoraFim between :dataHoraInicio and :dataHoraFim "
			+ "or :dataHoraInicio between h.dataHoraInicio and h.dataHoraFim or :dataHoraFim between h.dataHoraInicio and h.dataHoraFim)")
	List<HoraTrabalhada> findAnyBetweenDataInicioAndFim(@Param("prestadorId")long prestadorId, @Param("dataHoraInicio")LocalDateTime dataHoraInicio, @Param("dataHoraFim")LocalDateTime dataHoraFim);

	@Query("select h from HoraTrabalhada h left join HoraAprovacao ha on ha.id=hora_aprovacao_id where prestador_id = :prestadorId and (:dataHoraInicio between h.dataHoraInicio and h.dataHoraFim and :dataHoraFim between h.dataHoraInicio and h.dataHoraFim)")
	List<HoraTrabalhada> findBetweenDataInicioAndFim(@Param("prestadorId")long prestadorId, @Param("dataHoraInicio")LocalDateTime dataHoraInicio, @Param("dataHoraFim")LocalDateTime dataHoraFim);

//	@Transactional
//	@Modifying
//	@Query("update HoraTrabalhada set excluid usuario_exclusao_id = :usuarioExclusaoId, dataHoraExlusao = now() where id = :id")
//	void updateDelete(@Param("id")long id,  @Param("usuarioExclusaoId")long usuarioExclusaoId);

	@Transactional
	@Modifying
	@Query("update HoraTrabalhada set responsavel_aprovacao_id = :responsavelId, aprovadoResponsavel = :aprovadoResponsavel, motivoRecusa=:motivoRecusa where id = :id")
	void updateAprova(@Param("id")long id, @Param("responsavelId")long responsavelId, @Param("aprovadoResponsavel")int aprovadoResponsavel, @Param("motivoRecusa")String motivoRecusa);

	@Query("from HoraTrabalhada h where projeto_escala_id=:id and year(h.dataHoraInicio)=:ano and month(h.dataHoraInicio)=:mes")
	List<HoraTrabalhada> findByProjetoEscalaIdAndDate(@Param("id")long id, @Param("ano")int ano, @Param("mes")int mes);

//	@Query("update h set responsavel_aprovacao_id = :responsavelId, h.aprovadoResponsavel=:aprovadoResponsavel from HoraTrabalhada h inner join ProjetoEscala pe on pe.id=h.projetoEscalaId inner join Projeto p on p.id=pe.projetoId where h.id = :aprovacaoId and (p.GerenteId = :responsavelId or pe.MonitorId = :responsavelId)" )
//	void updateAprovaTodos(@Param("aprovacaoId")long aprovacaoId, @Param("responsavelId")long responsavelId, @Param("aprovadoResponsavel")boolean aprovadoResponsavel);
}
