package com.packageIxia.SistemaControleEscala.Daos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscala;

public interface ProjetoEscalaDao extends CrudRepository<ProjetoEscala, Long>  {

	Iterable<ProjetoEscala> findAllByProjetoId(long projetoId);
	
	Iterable<ProjetoEscala> findAllByProjetoIdAndExcluido(long projetoId, boolean excluido);

	boolean existsByProjetoId(long id);

	boolean existsByMonitorId(long id);

	List<ProjetoEscala> findAllByMonitorId(long monitorId);
}
