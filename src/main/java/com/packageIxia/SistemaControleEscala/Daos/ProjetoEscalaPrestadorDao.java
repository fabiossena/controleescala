package com.packageIxia.SistemaControleEscala.Daos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscalaPrestador;

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
}
