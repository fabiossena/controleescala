package com.packageIxia.sistemaControleEscala.daos.referencias;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.packageIxia.sistemaControleEscala.models.referencias.CentroCusto;

@Repository
public interface CentroCustoDao extends CrudRepository<CentroCusto, Long> {

}
