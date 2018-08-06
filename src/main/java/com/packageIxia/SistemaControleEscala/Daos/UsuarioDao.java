package com.packageIxia.SistemaControleEscala.Daos;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.packageIxia.SistemaControleEscala.Models.Usuario.Usuario;


public interface UsuarioDao extends CrudRepository<Usuario, Long> {
	
	public Usuario findByMatricula(String matricula);
	public boolean existsByMatricula(String matricula);
	public boolean existsByEmail(String email);
	public Usuario findByEmail(String email);
	public List<Usuario> findAllByFuncaoIdAndExcluido(int funcaoI, boolean excluido);
	public Usuario findByCpf(String cpf);
	public Iterable<Usuario> findAllByExcluido(boolean excluido);
	public Iterable<Usuario> findAllByFuncaoId(int id);

}
