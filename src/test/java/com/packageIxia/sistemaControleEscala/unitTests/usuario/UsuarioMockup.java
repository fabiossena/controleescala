package com.packageIxia.sistemaControleEscala.unitTests.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.packageIxia.sistemaControleEscala.daos.usuario.UsuarioDao;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

public class UsuarioMockup implements UsuarioDao {

	List<Usuario> entities = new ArrayList<Usuario>();
	@Override
	public <S extends Usuario> S save(S entity) {
		entity.setId(entities.size()+1);
		this.entities.add(entity);
		return entity;
	}

	@Override
	public <S extends Usuario> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Usuario> findById(Long id) {
		return this.entities.stream().filter(x->x.getId()==id).findFirst();
	}

	@Override
	public boolean existsById(Long id) {
		return this.entities.stream().anyMatch(x->x.getId()==id);
	}

	@Override
	public Iterable<Usuario> findAll() {
		return this.entities;
	}

	@Override
	public Iterable<Usuario> findAllById(Iterable<Long> ids) {
		return this.entities.stream().filter(x->Utilities.toList(ids).contains(x.getId())).collect(Collectors.toList());
	}

	@Override
	public long count() {
		return this.entities.size();
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Usuario entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Usuario> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario findByMatricula(String matricula) {
		if (this.entities.stream().anyMatch(x->x.getMatricula()==matricula)) {
			return this.entities.stream().filter(x->x.getMatricula()==matricula).findFirst().orElseGet(null);
		}
		else {
			return null;
		}
	}

	@Override
	public boolean existsByMatricula(String matricula) {
		return this.entities.stream().anyMatch(x->x.getMatricula()==matricula);
	}

	@Override
	public boolean existsByEmail(String email) {
		return this.entities.stream().anyMatch(x->x.getEmail()==email);
	}

	@Override
	public Usuario findByEmail(String email) {
		if (this.entities.stream().anyMatch(x->x.getEmail()==email)) {
			return this.entities.stream().filter(x->x.getEmail()==email).findFirst().orElseGet(null);
		}
		else {
			return null;
		}
	}

	@Override
	public Usuario findByCpf(String cpf) {
		if (this.entities.stream().anyMatch(x->x.getCpf()==cpf)) {
			return this.entities.stream().filter(x->x.getCpf()==cpf).findFirst().orElseGet(null);
		}
		else {
			return null;
		}
	}

	@Override
	public Iterable<Usuario> findAllByExcluido(boolean excluido) {
		return this.entities.stream().filter(x->x.isExcluido() == excluido).collect(Collectors.toList());
	}

	@Override
	public Iterable<Usuario> findAllByFuncaoId(int id) {
		return this.entities.stream().filter(x->x.getFuncao().getId()==id).collect(Collectors.toList());
	}

	@Override
	public List<Usuario> findAllByPerfilAcessoIdAndExcluido(int perfilAcessoId, boolean excluido) {
		return this.entities.stream().filter(x->x.getFuncao().getPerfilAcessoId()==perfilAcessoId && x.isExcluido() == excluido).collect(Collectors.toList());
	}

	@Override
	public List<Usuario> findAllByPerfilAcessoIdAndExcluido(int[] ids, boolean excluido) {
		// TODO Auto-generated method stub
		return null;
	}
}
