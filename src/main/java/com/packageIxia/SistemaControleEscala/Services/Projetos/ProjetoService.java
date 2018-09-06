package com.packageIxia.SistemaControleEscala.Services.Projetos;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageIxia.SistemaControleEscala.Daos.ProjetoDao;
import com.packageIxia.SistemaControleEscala.Daos.ProjetoEscalaDao;
import com.packageIxia.SistemaControleEscala.Daos.ProjetoEscalaPrestadorDao;
import com.packageIxia.SistemaControleEscala.Helper.Utilities;
import com.packageIxia.SistemaControleEscala.Models.Projeto.Projeto;
import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscala;
import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscalaPrestador;
import com.packageIxia.SistemaControleEscala.Models.Referencias.FuncaoEnum;
import com.packageIxia.SistemaControleEscala.Models.Usuario.Usuario;

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
		List<Projeto> projetos = Utilities.toList(projetoDao.findAllByExcluido(false));

		Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
		
		if (projetos == null || 
			projetos.isEmpty() || 
			usuarioLogado.getFuncaoId() == FuncaoEnum.administracao.funcao.getId() ||
			usuarioLogado.getFuncaoId() == FuncaoEnum.diretoria.funcao.getId() ||
			usuarioLogado.getFuncaoId() == FuncaoEnum.financeiro.funcao.getId()) {
			return projetos;			
		}

    	if (usuarioLogado.getFuncaoId() == FuncaoEnum.monitoramento.funcao.getId()) {
    		List<ProjetoEscala> escalas = projetoEscalaDao.findAllByMonitorId(usuarioLogado.getId());
            return projetos.stream().filter(x-> escalas.stream().anyMatch(y->y.getProjetoId()==x.getId())).collect(Collectors.toList());
    	}

    	if (usuarioLogado.getFuncaoId() == FuncaoEnum.atendimento.funcao.getId()) {
    		List<ProjetoEscalaPrestador> prestadores = projetoEscalaPrestadorDao.findAllByPrestadorId(usuarioLogado.getId());
            return projetos.stream().filter(x-> prestadores.stream().anyMatch(y->y.getProjeto().getId()==x.getId())).collect(Collectors.toList());
    	}

    	if (!(usuarioLogado.getFuncaoId() == FuncaoEnum.gerencia.funcao.getId())) {
            return projetos.stream().filter(x->x.getGerente().getId() == usuarioLogado.getId()).collect(Collectors.toList());
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
		Projeto projeto = this.findById(id);
		projeto.setExcluido(true);
		projetoDao.save(projeto);		
		return "";
	}

	public boolean existsByGerenteId(long id) {
		return this.projetoDao.existsByGerenteId(id);
	}

	public List<Projeto> findAll() {
		return Utilities.toList(this.projetoDao.findAll());
	}
}
