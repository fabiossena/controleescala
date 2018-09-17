package com.packageIxia.sistemaControleEscala.daos.referencias;

import org.springframework.data.repository.CrudRepository;

import com.packageIxia.sistemaControleEscala.models.referencias.MotivoAusencia;

public interface MotivoAusenciaDao extends CrudRepository<MotivoAusencia, Long> {

	Iterable<MotivoAusencia> findAllByTipo(int tipo);

}
