package com.packageIxia.SistemaControleEscala.Daos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.packageIxia.SistemaControleEscala.Models.Projeto.Projeto;

public interface ProjetoDao extends CrudRepository<Projeto, Long>  {

	Iterable<Projeto> findAllByExcluido(boolean excluido);

	boolean existsByGerenteId(long id);

	List<Projeto> findAllByGerenteId(long id);

}
