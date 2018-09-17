package com.packageIxia.sistemaControleEscala.daos.usuario;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.packageIxia.sistemaControleEscala.models.usuario.FolgaSemanalPlanejadaUsuario;

@Repository
public interface FolgaSemanalPlanejadaUsuarioDao extends CrudRepository<FolgaSemanalPlanejadaUsuario, Long> {

	  public Iterable<FolgaSemanalPlanejadaUsuario> findAllByUsuarioId(long usuarioId);
}
