package com.packageIxia.SistemaControleEscala.Daos.Referencias;

import org.springframework.data.repository.CrudRepository;

import com.packageIxia.SistemaControleEscala.Models.Referencias.MotivoAusencia;

public interface MotivoAusenciaDao extends CrudRepository<MotivoAusencia, Long> {

	Iterable<MotivoAusencia> findAllByTipo(int tipo);

}
