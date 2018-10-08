package com.packageIxia.sistemaControleEscala.services.referencias;

import java.util.List;

import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.daos.referencias.CentroCustoDao;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.interfaces.referencias.ICentroCusto;
import com.packageIxia.sistemaControleEscala.models.referencias.CentroCusto;

@Service
public class CentroCustoService implements ICentroCusto {

	private CentroCustoDao centroCustoDao;

	public CentroCustoService(CentroCustoDao centroCustoDao) {
		this.centroCustoDao = centroCustoDao;
	}
	
	@Override
	public CentroCusto findById(long id) {
		return this.centroCustoDao.findById(id).orElseGet(null);
	}

	@Override
	public List<CentroCusto> findAll() {
		return Utilities.toList(this.centroCustoDao.findAll());
	}

}
