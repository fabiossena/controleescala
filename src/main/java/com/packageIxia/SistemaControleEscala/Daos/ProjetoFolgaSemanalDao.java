package com.packageIxia.SistemaControleEscala.Daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoFolgaSemanal;

@Repository
public interface ProjetoFolgaSemanalDao extends CrudRepository<ProjetoFolgaSemanal, Long> {

	Iterable<ProjetoFolgaSemanal> findAllByProjetoEscalaPrestadorId(long projetoEscalaPrestadorId);

	void deleteByProjetoEscalaPrestadorId(long projetoEscalaPrestadorId);

}
