package com.packageIxia.sistemaControleEscala.services.projetos;

import java.util.List;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.packageIxia.sistemaControleEscala.daos.projeto.FuncaoDao;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IFuncao;
import com.packageIxia.sistemaControleEscala.models.referencias.Funcao;

@Service
public class FuncaoService implements IFuncao {
	
	private FuncaoDao funcaoDao;
	//private Environment enviroment;

	public FuncaoService(
			FuncaoDao funcaoDao,
			Environment enviroment) {
		this.funcaoDao = funcaoDao;
		//this.enviroment = enviroment;
	}

	@Override
	public List<Funcao> findAll() {
		List<Funcao> configs = Utilities.toList(this.funcaoDao.findAll());
		if (configs == null || configs.isEmpty()) {
//			for (PerfilAcessoEnum perfilAcesso : PerfilAcessoEnum.values()) {
//				configs.add(new Funcao())
//			} 
//			configs = new ArrayList<Funcao>();
//			configs.add(new Funcao("monitoramento", (enviroment.getProperty("valor.minuto.monitoramento") == null ? 0.25 :  Double.parseDouble(enviroment.getProperty("valor.minuto.monitoramento")))));
//			configs.add(new Funcao("atendimento", (enviroment.getProperty("valor.minuto.atendimento") == null ? 0.135 :  Double.parseDouble(enviroment.getProperty("valor.minuto.atendimento")))));
		}
		
		return configs;
	}

	@Override
	public Funcao findById(long id) {
		return this.funcaoDao.findById(id).orElseGet(null);
	}

	@Override
	public List<Funcao> findByPerfilAcessoId(int perfilAcessoId) {
		return this.funcaoDao.findByPerfilAcessoId(perfilAcessoId);
	}
}
