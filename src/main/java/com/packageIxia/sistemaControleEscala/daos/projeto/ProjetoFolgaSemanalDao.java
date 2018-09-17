package com.packageIxia.sistemaControleEscala.daos.projeto;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoFolgaSemanal;

@Repository
public interface ProjetoFolgaSemanalDao extends CrudRepository<ProjetoFolgaSemanal, Long> {

	Iterable<ProjetoFolgaSemanal> findAllByProjetoEscalaPrestadorId(long projetoEscalaPrestadorId);

	void deleteByProjetoEscalaPrestadorId(long projetoEscalaPrestadorId);

}
