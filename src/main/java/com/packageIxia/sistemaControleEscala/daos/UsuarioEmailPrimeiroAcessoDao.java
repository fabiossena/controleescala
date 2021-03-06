package com.packageIxia.sistemaControleEscala.daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.packageIxia.sistemaControleEscala.models.UsuarioEmailPrimeiroAcesso;

@Repository
public interface UsuarioEmailPrimeiroAcessoDao extends CrudRepository<UsuarioEmailPrimeiroAcesso, Long> {
	public boolean existsByMatriculaAndEmail(String matricula, String email);
	public UsuarioEmailPrimeiroAcesso findByMatricula(String matricula);
	public boolean existsByEmail(String email);
	public boolean existsByMatricula(String matricula);
}
