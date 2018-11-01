package com.packageIxia.sistemaControleEscala.daos.projeto;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscalaPrestador;

@Repository
public interface ProjetoEscalaPrestadorDao extends CrudRepository<ProjetoEscalaPrestador, Long>  {

	Iterable<ProjetoEscalaPrestador> findAllByProjetoEscalaId(long projetoEscalaId);

	List<ProjetoEscalaPrestador> findAllByPrestadorId(long prestadorId);

	Iterable<ProjetoEscalaPrestador> findAllByPrestadorIdAndExcluido(long prestadorId, boolean excluido);

	boolean existsByPrestadorId(long prestadorId);

	boolean existsByProjetoEscalaId(long projetoEscalaId);

	List<ProjetoEscalaPrestador> findAllByProjetoId(long id);

	List<ProjetoEscalaPrestador> findAllByPrestadorIdAndProjetoId(long prestadorId, long projetoId);

	List<ProjetoEscalaPrestador> findAllByProjetoEscalaIdAndExcluido(long projetoEscalaId, boolean excluido);

	ProjetoEscalaPrestador findByIdAndExcluido(long id, boolean excluido);

	List<ProjetoEscalaPrestador> findAllByPrestadorIdAndProjetoIdAndExcluido(long prestadorId, long projetoId, boolean excluido);

	List<ProjetoEscalaPrestador> findAllByPrestadorIdAndExcluidoAndAtivo(long prestadorId, boolean excluido, boolean ativo);

	ProjetoEscalaPrestador findByProjetoEscalaIdAndPrestadorIdAndExcluido(long projetoEscalaId, long prestadorId, boolean excluido);

	List<ProjetoEscalaPrestador> findAllByRamalIntegracaoRobo(String ramalRobo);

	List<ProjetoEscalaPrestador> findAllByIdAndPrestadorIdAndExcluido(long id, long prestadorId, boolean excluido);
	
	@Transactional
	@Modifying
	@Query("update ProjetoEscalaPrestador set aceito = :aceito, motivo_recusa = :motivo where id = :id")
	void aceiteRecusa(@Param("id")long id, @Param("aceito")int aceito, @Param("motivo")String motivo);
}
