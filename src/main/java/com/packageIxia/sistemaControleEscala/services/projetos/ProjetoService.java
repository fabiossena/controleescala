package com.packageIxia.sistemaControleEscala.services.projetos;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.daos.projeto.ProjetoDao;
import com.packageIxia.sistemaControleEscala.daos.projeto.ProjetoEscalaDao;
import com.packageIxia.sistemaControleEscala.daos.projeto.ProjetoEscalaPrestadorDao;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.models.projeto.Projeto;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscala;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.models.referencias.FuncaoEnum;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Service
public class ProjetoService {

	private ProjetoDao projetoDao;
	private ProjetoEscalaDao projetoEscalaDao;
	private ProjetoEscalaPrestadorDao projetoEscalaPrestadorDao;
	private HttpSession session;

	@Autowired
	public ProjetoService(
			ProjetoDao projetoDao,
			ProjetoEscalaDao projetoEscalaDao,
			ProjetoEscalaPrestadorDao projetoEscalaPrestadorDao,
			HttpSession session) {
		this.projetoDao = projetoDao;
		this.projetoEscalaDao = projetoEscalaDao;
		this.projetoEscalaPrestadorDao = projetoEscalaPrestadorDao;
		this.session = session;
	}

	public List<Projeto> findAllByUsuarioLogado() {
		List<Projeto> projetos = new ArrayList<Projeto>(); 
		Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
		
		if (usuarioLogado.getFuncaoId() == FuncaoEnum.administracao.funcao.getId() ||
			usuarioLogado.getFuncaoId() == FuncaoEnum.diretoria.funcao.getId() ||
			usuarioLogado.getFuncaoId() == FuncaoEnum.financeiro.funcao.getId()) {
			projetos = Utilities.toList(projetoDao.findAllByExcluido(false));			
		} else if (usuarioLogado.getFuncaoId() == FuncaoEnum.monitoramento.funcao.getId()) {
    		List<ProjetoEscala> escalas = projetoEscalaDao.findAllByMonitorId(usuarioLogado.getId());
    		projetos = Utilities.toList(projetoDao.findAllById(Utilities.streamLongToIterable(escalas.stream().map(y->y.getProjetoId()))));
    	} else if (usuarioLogado.getFuncaoId() == FuncaoEnum.atendimento.funcao.getId()) {
    		List<ProjetoEscalaPrestador> prestadores = projetoEscalaPrestadorDao.findAllByPrestadorId(usuarioLogado.getId());
    		projetos = Utilities.toList(projetoDao.findAllById(Utilities.streamLongToIterable(prestadores.stream().map(y->y.getProjeto().getId()))));
    	} else if (!(usuarioLogado.getFuncaoId() == FuncaoEnum.gerencia.funcao.getId())) {
    		List<Projeto> projs = projetoDao.findAllByGerenteId(usuarioLogado.getId());
    		projetos = Utilities.toList(projetoDao.findAllById(Utilities.streamLongToIterable(projs.stream().map(y->y.getId()))));
    	}
    	
		return projetos;
	}
	
	public Projeto findById(long id) {		
		Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
		Projeto projeto = projetoDao.findById(id).orElse(null);

		if (usuarioLogado.getFuncaoId() == FuncaoEnum.administracao.funcao.getId() ||
			usuarioLogado.getFuncaoId() == FuncaoEnum.diretoria.funcao.getId() ||
			usuarioLogado.getFuncaoId() == FuncaoEnum.financeiro.funcao.getId()) {
			return projeto;
		}
		
    	if (usuarioLogado.getFuncaoId() == FuncaoEnum.atendimento.funcao.getId()) {
    		List<ProjetoEscalaPrestador> projetoEscalaPrestador = projetoEscalaPrestadorDao.findAllByProjetoId(projeto.getId());
    		if (!projetoEscalaPrestador.stream().anyMatch(x->x.getPrestador().getId()==usuarioLogado.getId())) {
    			return null;
    		}
    	}

    	if (usuarioLogado.getFuncaoId() == FuncaoEnum.monitoramento.funcao.getId()) {
    		List<ProjetoEscala> projetoEscala = projetoEscalaDao.findAllByProjetoId(projeto.getId());
    		if (!projetoEscala.stream().anyMatch(x->x.getMonitor().getId()==usuarioLogado.getId())) {
    			return null;
    		}
    	}

    	if (usuarioLogado.getFuncaoId() == FuncaoEnum.gerencia.funcao.getId()) {
    		if (projeto.getGerente().getId() != usuarioLogado.getId()) {
    			return null;
    		}
    	}
    	
		return projeto;
	}
	
	public String saveProjeto(Projeto projeto) {
		if (projeto.getDataInicio() != null && projeto.getDataFim() != null &&
				projeto.getDataInicio().isAfter((projeto.getDataFim()))) {
			return "Data início do projeto não pode ser menor do que a data fim";
		}
		
		projetoDao.save(projeto);
		return "";
	}

	public String delete(long id) {
		List<ProjetoEscala> projetoEscalas = this.projetoEscalaDao.findAllByProjetoId(id);
		if (projetoEscalas != null && projetoEscalas.size() > 0) {
			Projeto projeto = this.findById(id);
			projeto.setExcluido(true);
			projetoDao.save(projeto);		
		}
		else {
			projetoDao.deleteById(id);
		}
		
		return "";
	}

	public boolean existsByGerenteId(long id) {
		return this.projetoDao.existsByGerenteId(id);
	}

	public List<Projeto> findAll() {
		return Utilities.toList(this.projetoDao.findAll());
	}
}
