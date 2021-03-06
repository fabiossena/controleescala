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
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjeto;
import com.packageIxia.sistemaControleEscala.models.projeto.Projeto;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscala;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.models.referencias.PerfilAcessoEnum;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Service
public class ProjetoService implements IProjeto {

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

	@Override
	public List<Projeto> findAllByUsuarioLogado() {
		List<Projeto> projetos = new ArrayList<Projeto>(); 
		Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
		
		if (usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.administracao.getId() ||
			usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.diretoria.getId() ||
			usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.financeiro.getId() ||
			usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.monitoramento.getId()) {
			
			projetos = Utilities.toList(projetoDao.findAllByExcluido(false));			
			
		} else if (usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.atendimento.getId()) {
			
    		List<ProjetoEscalaPrestador> prestadores = projetoEscalaPrestadorDao.findAllByPrestadorId(usuarioLogado.getId());
    		projetos = Utilities.toList(projetoDao.findAllById(Utilities.streamLongToIterable(prestadores.stream().map(y->y.getProjeto().getId()))));
    		
    	} else if (usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.gerencia.getId()) {
    		List<Projeto> projs = projetoDao.findAllByGerenteId(usuarioLogado.getId());
    		projetos = Utilities.toList(projetoDao.findAllById(Utilities.streamLongToIterable(projs.stream().map(y->y.getId()))));
    	}
    	
		return projetos;
	}

	
	@Override
	public Projeto findById(long id, boolean all) {	
		if (!all) {
			return findById(id);
		}
		
		Projeto projeto = projetoDao.findById(id).orElse(null);
		return projeto;
	}
	
	@Override
	public Projeto findById(long id) {		
		Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
		Projeto projeto = projetoDao.findById(id).orElse(null);

		if (usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.administracao.getId() ||
			usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.diretoria.getId() ||
			usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.financeiro.getId() ||
			usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.gerencia.getId() ||
			usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.monitoramento.getId()) {
			return projeto;
		}
		
    	if (usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.atendimento.getId()) {
    		List<ProjetoEscalaPrestador> projetoEscalaPrestador = projetoEscalaPrestadorDao.findAllByProjetoId(projeto.getId());
    		if (!projetoEscalaPrestador.stream().anyMatch(x->x.getPrestador().getId()==usuarioLogado.getId())) {
    			return null;
    		}
    	}

    	if (usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.monitoramento.getId()) {
    		List<ProjetoEscala> projetoEscala = projetoEscalaDao.findAllByProjetoId(projeto.getId());
    		if (!projetoEscala.stream().anyMatch(x->x.getMonitor().getId()==usuarioLogado.getId())) {
    			return null;
    		}
    	}

    	if (usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.gerencia.getId()) {
    		if (projeto.getGerente().getId() != usuarioLogado.getId()) {
    			return null;
    		}
    	}
    	
		return projeto;
	}
	
	@Override
	public String saveProjeto(Projeto projeto) {
		if (projeto.getDataInicio() != null && projeto.getDataFim() != null &&
				projeto.getDataInicio().isAfter((projeto.getDataFim()))) {
			return "Data início do projeto não pode ser menor do que a data fim";
		}
		
		projetoDao.save(projeto);
		return "";
	}

	@Override
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

	@Override
	public boolean existsByGerenteId(long id) {
		return this.projetoDao.existsByGerenteId(id);
	}

	@Override
	public List<Projeto> findAll() {
		return Utilities.toList(this.projetoDao.findAll());
	}
}
