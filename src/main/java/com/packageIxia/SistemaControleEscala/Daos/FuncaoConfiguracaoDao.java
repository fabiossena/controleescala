package com.packageIxia.SistemaControleEscala.Daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.packageIxia.SistemaControleEscala.Models.Projeto.FuncaoConfiguracao;

@Repository
public interface FuncaoConfiguracaoDao extends CrudRepository<FuncaoConfiguracao, Long> {

}
