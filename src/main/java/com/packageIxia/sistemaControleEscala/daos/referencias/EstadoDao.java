package com.packageIxia.sistemaControleEscala.daos.referencias;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.packageIxia.sistemaControleEscala.models.referencias.Estado;

@Repository
public interface EstadoDao extends CrudRepository<Estado, Long> {

}
