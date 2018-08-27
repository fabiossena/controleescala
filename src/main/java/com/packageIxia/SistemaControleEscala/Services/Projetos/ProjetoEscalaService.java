package com.packageIxia.SistemaControleEscala.Services.Projetos;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageIxia.SistemaControleEscala.Daos.ProjetoDao;
import com.packageIxia.SistemaControleEscala.Daos.ProjetoEscalaDao;
import com.packageIxia.SistemaControleEscala.Helper.Utilities;
import com.packageIxia.SistemaControleEscala.Models.Projeto.Projeto;
import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscala;
import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscalaPrestador;
import com.packageIxia.SistemaControleEscala.Models.Referencias.FuncaoEnum;
import com.packageIxia.SistemaControleEscala.Models.Usuario.Usuario;

@Service
public class ProjetoEscalaService {

	private ProjetoEscalaDao projetoEscalaDao;
	private ProjetoEscalaPrestadorService projetoEscalaPrestadorService;
	private HttpSession session;
	private ProjetoDao projetoDao;

	@Autowired
	public ProjetoEscalaService(
			ProjetoDao projetoDao,
			ProjetoEscalaDao projetoEscalaDao,
			ProjetoEscalaPrestadorService projetoEscalaPrestadorService,
			HttpSession session) {
		this.projetoEscalaDao = projetoEscalaDao;
		this.projetoEscalaPrestadorService = projetoEscalaPrestadorService;
		this.session = session;
		this.projetoDao = projetoDao;
	}
	
	public List<ProjetoEscala> findAllByProjetoId(long projetoId) {
		return Utilities.toList(projetoEscalaDao.findAllByProjetoIdAndExcluido(projetoId, false));
	}
	
	public ProjetoEscala findById(long id) {
		return projetoEscalaDao.findById(id).orElse(null);
	}
	
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

	public boolean existsByProjetoId(long projetoId) {
		return this.projetoEscalaDao.existsByProjetoId(projetoId);
	}

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

	public boolean existsByMonitorId(long monitorId) {
		return this.projetoEscalaDao.existsByMonitorId(monitorId);
	}

	public List<ProjetoEscala> findAllByMonitorId(long monitorId) {
		return this.projetoEscalaDao.findAllByMonitorId(monitorId);
	}
	
	public List<ProjetoEscala> findAllByPrestadorId(long id) {
		return this.projetoEscalaPrestadorService.findAllByPrestadorId(id)
				.stream().map(x->x.getProjetoEscala()).distinct().collect(Collectors.toList());
	}
	
	public List<ProjetoEscala> findAllByPrestadorIdExceptPrestadorEscalaId(long usuarioId, long prestadorEscalaId) {
		List<ProjetoEscalaPrestador> projetosEscalas = this.projetoEscalaPrestadorService.findAllByPrestadorId(usuarioId);
		return projetosEscalas
				.stream().filter(x->prestadorEscalaId == 0 || x.getProjetoEscala().getId() != prestadorEscalaId)
				.map(x->x.getProjetoEscala()).distinct().collect(Collectors.toList());
	}

	public List<ProjetoEscala> findAllByPermissao() {
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
		
		return escalas;
	}
}
