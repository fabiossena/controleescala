package com.packageIxia.sistemaControleEscala.daos.referencias;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.packageIxia.sistemaControleEscala.models.referencias.MotivoAusencia;

@Repository
public interface MotivoAusenciaDao extends CrudRepository<MotivoAusencia, Long> {

	Iterable<MotivoAusencia> findAllByTipo(int tipo);

}
