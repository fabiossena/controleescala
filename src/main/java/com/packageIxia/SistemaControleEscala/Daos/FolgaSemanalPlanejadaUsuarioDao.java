package com.packageIxia.SistemaControleEscala.Daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.packageIxia.SistemaControleEscala.Models.Usuario.FolgaSemanalPlanejadaUsuario;

@Repository
public interface FolgaSemanalPlanejadaUsuarioDao extends CrudRepository<FolgaSemanalPlanejadaUsuario, Long> {

	  public Iterable<FolgaSemanalPlanejadaUsuario> findAllByUsuarioId(long usuarioId);
}
