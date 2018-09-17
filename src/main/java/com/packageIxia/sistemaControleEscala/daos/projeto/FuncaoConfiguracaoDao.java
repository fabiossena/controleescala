package com.packageIxia.sistemaControleEscala.daos.projeto;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.packageIxia.sistemaControleEscala.models.projeto.FuncaoConfiguracao;

@Repository
public interface FuncaoConfiguracaoDao extends CrudRepository<FuncaoConfiguracao, Long> {

}
