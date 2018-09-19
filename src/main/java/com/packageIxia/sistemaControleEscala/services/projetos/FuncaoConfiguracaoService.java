package com.packageIxia.sistemaControleEscala.services.projetos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.daos.projeto.FuncaoConfiguracaoDao;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IFuncaoConfiguracao;
import com.packageIxia.sistemaControleEscala.models.projeto.FuncaoConfiguracao;

@Service
public class FuncaoConfiguracaoService implements IFuncaoConfiguracao {
	
	private FuncaoConfiguracaoDao funcaoConfiguracaoDao;
	private Environment enviroment;

	public FuncaoConfiguracaoService(
			FuncaoConfiguracaoDao funcaoConfiguracaoDao,
			Environment enviroment) {
		this.funcaoConfiguracaoDao = funcaoConfiguracaoDao;
		this.enviroment = enviroment;
	}

	@Override
	public List<FuncaoConfiguracao> findAll() {
		List<FuncaoConfiguracao> configs = Utilities.toList(this.funcaoConfiguracaoDao.findAll());
		if (configs == null || configs.isEmpty()) {
			configs = new ArrayList<FuncaoConfiguracao>();
			configs.add(new FuncaoConfiguracao("monitoramento", (enviroment.getProperty("valor.minuto.monitoramento") == null ? 0.25 :  Double.parseDouble(enviroment.getProperty("valor.minuto.monitoramento")))));
			configs.add(new FuncaoConfiguracao("atendimento", (enviroment.getProperty("valor.minuto.atendimento") == null ? 0.135 :  Double.parseDouble(enviroment.getProperty("valor.minuto.atendimento")))));
		}
		
		return configs;
	}
}
