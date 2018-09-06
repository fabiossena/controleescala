package com.packageIxia.SistemaControleEscala.Daos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscala;

@Repository
public interface ProjetoEscalaDao extends CrudRepository<ProjetoEscala, Long>  {

	List<ProjetoEscala> findAllByProjetoId(long projetoId);
	
	List<ProjetoEscala> findAllByProjetoIdAndExcluido(long projetoId, boolean excluido);

	boolean existsByProjetoId(long id);

	boolean existsByMonitorId(long id);

	List<ProjetoEscala> findAllByMonitorId(long monitorId);

	List<ProjetoEscala> findAllByProjetoId(Iterable<Long> projs);
}
