package com.packageIxia.sistemaControleEscala.daos.projeto;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.packageIxia.sistemaControleEscala.models.projeto.Projeto;

@Repository
public interface ProjetoDao extends CrudRepository<Projeto, Long>  {

	Iterable<Projeto> findAllByExcluido(boolean excluido);

	boolean existsByGerenteId(long id);

	List<Projeto> findAllByGerenteId(long id);

}
