package com.packageIxia.SistemaControleEscala.Daos;

import org.springframework.data.repository.CrudRepository;

import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoFolgaSemanal;

public interface ProjetoFolgaSemanalDao extends CrudRepository<ProjetoFolgaSemanal, Long> {

	Iterable<ProjetoFolgaSemanal> findAllByProjetoEscalaPrestadorId(long projetoEscalaPrestadorId);

	void deleteByProjetoEscalaPrestadorId(long projetoEscalaPrestadorId);

}
