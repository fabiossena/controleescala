package com.packageIxia.SistemaControleEscala.Daos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.packageIxia.SistemaControleEscala.Models.Projeto.AusenciaReposicao;

public interface AusenciaReposicaoDao extends CrudRepository<AusenciaReposicao, Long> {

	List<AusenciaReposicao> findAllByUsuarioTrocaId(long id);

	List<AusenciaReposicao> findAllByUsuarioAprovacaoId(long id);

	List<AusenciaReposicao> findAllByGerenciaAprovacaoId(long id);

	List<AusenciaReposicao> findAllByProjetoEscalaTrocaId(long escalaId);

}
