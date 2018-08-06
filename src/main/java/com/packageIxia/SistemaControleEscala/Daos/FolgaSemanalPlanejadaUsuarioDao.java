package com.packageIxia.SistemaControleEscala.Daos;

import org.springframework.data.repository.CrudRepository;

import com.packageIxia.SistemaControleEscala.Models.Usuario.FolgaSemanalPlanejadaUsuario;

public interface FolgaSemanalPlanejadaUsuarioDao extends CrudRepository<FolgaSemanalPlanejadaUsuario, Long> {

	  public Iterable<FolgaSemanalPlanejadaUsuario> findAllByUsuarioId(long usuarioId);
}
