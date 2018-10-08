package com.packageIxia.sistemaControleEscala.daos.referencias;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.packageIxia.sistemaControleEscala.models.referencias.Pais;

@Repository
public interface PaisDao extends CrudRepository<Pais, Long> {

}
