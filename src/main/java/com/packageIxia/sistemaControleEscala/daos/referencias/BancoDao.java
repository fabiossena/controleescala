package com.packageIxia.sistemaControleEscala.daos.referencias;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.packageIxia.sistemaControleEscala.models.referencias.Banco;

@Repository
public interface BancoDao extends CrudRepository<Banco, Long> {

}
