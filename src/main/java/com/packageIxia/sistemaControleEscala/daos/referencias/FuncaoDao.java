package com.packageIxia.sistemaControleEscala.daos.referencias;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.packageIxia.sistemaControleEscala.models.referencias.Funcao;

@Repository
public interface FuncaoDao extends CrudRepository<Funcao, Long> {
	List<Funcao> findByPerfilAcessoId(int perfilAcessoId);
}
