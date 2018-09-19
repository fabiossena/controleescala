package com.packageIxia.sistemaControleEscala.services.projetos;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.daos.projeto.ProjetoDao;
import com.packageIxia.sistemaControleEscala.daos.projeto.ProjetoEscalaDao;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoEscala;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.models.projeto.Projeto;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscala;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.models.referencias.FuncaoEnum;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Service
public class ProjetoEscalaService implements IProjetoEscala {

	private ProjetoEscalaDao projetoEscalaDao;
	private IProjetoEscalaPrestador projetoEscalaPrestadorService;
	private HttpSession session;
	private ProjetoDao projetoDao;

	@Autowired
	public ProjetoEscalaService(
			ProjetoDao projetoDao,
			ProjetoEscalaDao projetoEscalaDao,
			IProjetoEscalaPrestador projetoEscalaPrestadorService,
			HttpSession session) {
		this.projetoEscalaDao = projetoEscalaDao;
		this.projetoEscalaPrestadorService = projetoEscalaPrestadorService;
		this.session = session;
		this.projetoDao = projetoDao;
	}
	
	@Override
	public List<ProjetoEscala> findAllByProjetoId(long projetoId) {
		return Utilities.toList(projetoEscalaDao.findAllByProjetoIdAndExcluido(projetoId, false));
	}
	
	@Override
	public ProjetoEscala findById(long id) {
		return projetoEscalaDao.findById(id).orElse(null);
	}
	
	@Override
	public String saveEscala(ProjetoEscala escala) {

		if (!Utilities.validarHora(escala.getHoraInicio())) {
			return "Campo 'Hora início' inválido";
		}

		if (!Utilities.validarHora(escala.getHoraFim())) {
			return "Campo 'Hora fim' inválido";
		}
		
		if (escala.getHoraInicio() != "" && 
			escala.getHoraFim() != "" &&
			Integer.parseInt(escala.getHoraInicio().replace(":", "")) > Integer.parseInt(escala.getHoraFim().replace(":", ""))) {
			return "O campo 'Hora início' deve ser maior que 'Hora fim'";
		}

		if (escala.getDiaSemanaDe().getId() != 0 && 
			escala.getDiaSemanaAte().getId() != 0 &&
			escala.getDiaSemanaDe().getId() > escala.getDiaSemanaAte().getId()) {
			return "O campo 'Dia da semana de' deve ser maior que 'Dia da semana até'";
		}
		
		if (escala.getDefinidaPrioridade()) {
			
			if (escala.getHoraInicioPrioridade() == "") {
				return "Preencha o campo 'Hora início prioridade'";
			}
	
			if (escala.getHoraFimPrioridade() == "") {
				return "Preencha o campo 'Hora fim prioridade'";
			}
			
			if (!Utilities.validarHora(escala.getHoraInicioPrioridade())) {
				return "Campo 'Hora início prioridade' inválido";
			}
	
			if (!Utilities.validarHora(escala.getHoraFimPrioridade())) {
				return "Campo 'Hora fim prioridade' inválido";
			}
			
			if (Integer.parseInt(escala.getHoraInicioPrioridade().replace(":", "")) > Integer.parseInt(escala.getHoraFimPrioridade().replace(":", ""))) {
				return "O campo 'Hora início prioridade' deve ser maior que 'Hora fim prioridade'";
			}
	
			if (escala.getDiaSemanaDePrioridade().getId() > escala.getDiaSemanaAtePrioridade().getId()) {
				return "O campo 'Dia da semana de prioridade' deve ser maior que 'Dia da semana até prioridade'";
			}
			
			if (Integer.parseInt(escala.getHoraInicioPrioridade().replace(":", "")) < Integer.parseInt(escala.getHoraInicio().replace(":", "")) ||
				 Integer.parseInt(escala.getHoraFimPrioridade().replace(":", "")) > Integer.parseInt(escala.getHoraFim().replace(":", ""))) {
				return "O campo 'Hora início/fim prioridade' deve estar entre a 'Hora início/fim' da escala'";
			}
	
			if (escala.getDiaSemanaDePrioridade().getId() < escala.getDiaSemanaDe().getId() ||
				escala.getDiaSemanaAtePrioridade().getId() > escala.getDiaSemanaAte().getId()) {
				return "O campo 'Dia da semana de/até prioridade' deve estar entre o 'Dia da semana de/até' da escala'";
			}
		}
		
		this.projetoEscalaDao.save(escala);
		return "";
	}

	@Override
	public boolean existsByProjetoId(long projetoId) {
		return this.projetoEscalaDao.existsByProjetoId(projetoId);
	}

	@Override
	public String delete(long escalaId) {
		if (this.projetoEscalaPrestadorService.existsByProjetoEscalaId(escalaId)) {
			ProjetoEscala escala = this.findById(escalaId);
			escala.setExcluido(true);
			projetoEscalaDao.save(escala);		
		}
		else {
			this.projetoEscalaDao.deleteById(escalaId);
		}
		return "";
	}

	@Override
	public boolean existsByMonitorId(long monitorId) {
		return this.projetoEscalaDao.existsByMonitorId(monitorId);
	}

	@Override
	public List<ProjetoEscala> findAllByMonitorId(long monitorId) {
		return this.projetoEscalaDao.findAllByMonitorId(monitorId);
	}

	@Override
	public List<ProjetoEscala> findAllByPrestadorId(long id) {
		return this.findAllByPrestadorId(id, true);
	}
	
	@Override
	public List<ProjetoEscala> findAllByPrestadorId(long id, boolean somenteTipoApontamentoSistema) {
		List<ProjetoEscala> escalas =  this.projetoEscalaPrestadorService.findAllByPrestadorId(id)
				.stream().filter(x-> !somenteTipoApontamentoSistema || x.getProjeto().getTipoApontamentoHorasId() == 1)
				.map(x->x.getProjetoEscala()).distinct().collect(Collectors.toList());		
		return escalas;
	}
	
	@Override
	public List<ProjetoEscala> findAllByPrestadorIdExceptPrestadorEscalaId(long usuarioId, long prestadorEscalaId) {
		List<ProjetoEscalaPrestador> projetosEscalas = this.projetoEscalaPrestadorService.findAllByPrestadorId(usuarioId);
		return projetosEscalas
				.stream().filter(x->(prestadorEscalaId == 0 || x.getProjetoEscala().getId() != prestadorEscalaId) && x.getProjeto().getTipoApontamentoHorasId() ==  1)
				.map(x->x.getProjetoEscala()).distinct().collect(Collectors.toList());
	}

	@Override
	public List<ProjetoEscala> findAllByPermissao() {
		return findAllByPermissao(false);
	}
	
	@Override
	public List<ProjetoEscala> findAllByPermissao(boolean somenteTipoApontamentoSistema) {
		List<Projeto> projetos = Utilities.toList(this.projetoDao.findAll());
		
		Usuario usuario = ((Usuario)this.session.getAttribute("usuarioLogado"));
		List<ProjetoEscala> escalas = new ArrayList<ProjetoEscala>();
		if (usuario.getFuncaoId() == FuncaoEnum.atendimento.funcao.getId()) {
			escalas = findAllByPrestadorId(usuario.getId());
		} 
		else if (usuario.getFuncaoId() == FuncaoEnum.monitoramento.funcao.getId()) {
			escalas = findAllByMonitorId(usuario.getId());
		}
		else if (usuario.getFuncaoId() == FuncaoEnum.gerencia.funcao.getId()) {
			List<Projeto> projs = this.projetoDao.findAllByGerenteId(usuario.getId());
			List<Long> idProjs = projs.stream().map(x->x.getId()).collect(Collectors.toList());
			escalas = Utilities.toList(this.projetoEscalaDao.findAll())
					.stream().filter(x-> idProjs.stream().anyMatch(y->y==x.getProjetoId()))
					.collect(Collectors.toList()); // todo: ajustar
		}
		else {
			escalas = Utilities.toList(projetoEscalaDao.findAll());			
		}
		
		// todo: remove
		for (ProjetoEscala projetoEscala : escalas) {
			projetoEscala.setProjeto(projetos.stream().filter(x->x.getId() == projetoEscala.getProjetoId()).findFirst().get());
		}
		
		if (somenteTipoApontamentoSistema) {
			escalas = escalas.stream().filter(x->x.getProjeto().getTipoApontamentoHorasId() == 1).collect(Collectors.toList());
		}
		
		return escalas;
	}
}
