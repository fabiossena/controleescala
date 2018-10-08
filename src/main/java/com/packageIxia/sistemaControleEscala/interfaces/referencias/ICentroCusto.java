package com.packageIxia.sistemaControleEscala.interfaces.referencias;

import java.util.List;

import com.packageIxia.sistemaControleEscala.models.referencias.CentroCusto;

public interface ICentroCusto {
	CentroCusto findById(long id);
	List<CentroCusto> findAll();
}
