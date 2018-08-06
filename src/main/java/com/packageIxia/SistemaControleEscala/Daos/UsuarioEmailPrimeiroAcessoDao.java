package com.packageIxia.SistemaControleEscala.Daos;

import org.springframework.data.repository.CrudRepository;

import com.packageIxia.SistemaControleEscala.Models.UsuarioEmailPrimeiroAcesso;

public interface UsuarioEmailPrimeiroAcessoDao extends CrudRepository<UsuarioEmailPrimeiroAcesso, Long> {
	public boolean existsByMatriculaAndEmail(String matricula, String email);
	public UsuarioEmailPrimeiroAcesso findByMatricula(String matricula);
	public boolean existsByEmail(String email);
	public boolean existsByMatricula(String matricula);
}
