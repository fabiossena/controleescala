package com.packageIxia.sistemaControleEscala.controllers.projeto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IAusenciaReposicao;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IAusenciaSolicitacao;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IHoraTrabalhada;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjeto;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoEscala;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoFolgaSemanal;
import com.packageIxia.sistemaControleEscala.models.projeto.AusenciaReposicao;
import com.packageIxia.sistemaControleEscala.models.projeto.AusenciaSolicitacao;
import com.packageIxia.sistemaControleEscala.models.projeto.HoraTrabalhada;
import com.packageIxia.sistemaControleEscala.models.projeto.Projeto;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscala;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.models.referencias.DiaSemanaEnum;
import com.packageIxia.sistemaControleEscala.models.referencias.DiasMes;
import com.packageIxia.sistemaControleEscala.models.referencias.PerfilAcessoEnum;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Controller
public class DashboardController {
	
	private IProjetoEscalaPrestador prestadorService;
	private ModelAndView modelView = new ModelAndView("projeto/projetoDashboardView");
	private List<ProjetoEscalaPrestador> prestadores;
	private IProjetoFolgaSemanal projetoFolgaSemanalService;
	private IProjetoEscala escalaService;
	private IProjeto projetoService;
	private IAusenciaSolicitacao ausenciaSolicitacaoService;
	private IAusenciaReposicao ausenciaReposicaoService;
	private IHoraTrabalhada horaTrabalhadaService;
	private Usuario usuarioLogado;

	@Autowired
	public DashboardController(
			IProjeto projetoService,
			IProjetoEscala escalaService,
			IProjetoEscalaPrestador prestadorService,
			IProjetoFolgaSemanal projetoFolgaSemanalService,
			IAusenciaSolicitacao ausenciaSolicitacaoService,
			IHoraTrabalhada horaTrabalhadaService,
			IAusenciaReposicao ausenciaReposicaoService) {
		this.escalaService = escalaService;
		this.projetoService = projetoService;
		this.prestadorService = prestadorService;
		this.projetoFolgaSemanalService = projetoFolgaSemanalService;
		this.ausenciaSolicitacaoService = ausenciaSolicitacaoService;
		this.ausenciaReposicaoService = ausenciaReposicaoService;
		this.horaTrabalhadaService = horaTrabalhadaService;
	}	

	@GetMapping("/dashboard")
	public ModelAndView projeto(HttpServletRequest request) {

		System.out.println("dashboard principal");
    	List<Projeto> projetos = this.projetoService.findAllByUsuarioLogado();

    	if (projetos == null || projetos.isEmpty()) {
    		ModelAndView erroModelView = new ModelAndView("errorView");
    		erroModelView.addObject("errorMessage", "Sem projetos cadastrados");
            return erroModelView;
    	}

		this.usuarioLogado = ((Usuario)request.getSession().getAttribute("usuarioLogado"));
		
    	if (usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.financeiro.getId()) {
    		ModelAndView erroModelView = new ModelAndView("errorView");
    		erroModelView.addObject("errorMessage", "Não permitido acesso a tela de projetos");
            return erroModelView;
    	}
    	
		return dashboard(request, projetos.get(0).getId(), 0, 0, 0, 1, 0);
	}

	@GetMapping("/dashboard/{id}")
	public ModelAndView projetos(
			HttpServletRequest request,
			@PathVariable("id") long id,
			@RequestParam(value = "mes", defaultValue = "0") int mes,
			@RequestParam(value = "ano", defaultValue = "0") int ano,
			@RequestParam(value = "escala", defaultValue = "0") int escalaId,
			@RequestParam(value = "aceito", defaultValue = "1") int aceito,
			@RequestParam(value = "solicitacao", defaultValue = "0") long solicitacao) {	
		
		System.out.println("dashboard");
		return dashboard(request, id, mes, ano, escalaId, aceito, solicitacao);
	}

	private ModelAndView dashboard(
			HttpServletRequest request, 
			long projetoId, 
			int mes, 
			int ano, 
			int escalaId, 
			int aceito, 
			long solicitacao) {
		
		Projeto projeto = this.projetoService.findById(projetoId);
    	
    	if (projeto == null) {
    		ModelAndView erroModelView = new ModelAndView("errorView");
    		erroModelView.addObject("errorMessage", "Projetos não encontrado ou cadastrado para este usuário");
            return erroModelView;
    	}

    	List<Projeto> projetos = this.projetoService.findAllByUsuarioLogado();

    	if (projetos == null || projetos.isEmpty()) {
    		ModelAndView erroModelView = new ModelAndView("errorView");
    		erroModelView.addObject("errorMessage", "Sem projetos cadastrados para este usuário");
            return erroModelView;
    	}
    	
    	modelView.addObject("projetos", projetos);
    	modelView.addObject("projeto", projeto);
    	
    	ProjetoEscala esc = new ProjetoEscala();
    	esc.setId(0);
    	if (escalaId > 0) {
    		esc = this.escalaService.findById(escalaId);
    	}
    	
		modelView.addObject("escala", esc);
    	
		this.prestadores = this.prestadorService.findAllByProjetoId(projetoId, true);
		this.prestadores = this.prestadores.stream().sorted(new Comparator<ProjetoEscalaPrestador>() {

			public int compare(ProjetoEscalaPrestador p1, ProjetoEscalaPrestador p2) {
			   String item1 = p1.getPrestador().getPrimeiroNome().toUpperCase();
			   String item2 = p2.getPrestador().getPrimeiroNome().toUpperCase();

			   //ascending order
			   return item1.compareTo(item2);

			   //descending order
			   //return item1.compareTo(item2);
		    }}).collect(Collectors.toList());
		
		for (ProjetoEscalaPrestador prest : prestadores) {
			prest.setProjetoFolgasSemanais(projetoFolgaSemanalService.findAllByProjetoEscalaPrestadorId(prest.getId()));
		}

		
		int mesAtual = (mes != 0 ? mes : Utilities.now().getMonth().getValue());
		int anoAtual = (ano != 0 ? ano : Utilities.now().getYear());

		
		List<AusenciaReposicao> ausenciaReposicoes = null;
		if (escalaId != 0) {
			ausenciaReposicoes = this.ausenciaReposicaoService.findAllByProjetoEscalaId(anoAtual, mesAtual, escalaId, aceito);
		}
		else {
			ausenciaReposicoes = this.ausenciaReposicaoService.findAllByProjetoId(anoAtual, mesAtual, projetoId, aceito);
		}

		if (ausenciaReposicoes != null && ausenciaReposicoes.size() > 0) {
			for (ProjetoEscalaPrestador projEsc : this.prestadores) {
				projEsc.setAusenciaReposicoes(
						ausenciaReposicoes.stream().filter(x->
						x.getProjetoEscalaTroca().getId() == projEsc.getProjetoEscala().getId() &&
						(x.getUsuarioTroca() == null || x.getUsuarioTroca().getId() == projEsc.getPrestador().getId()) ).collect(Collectors.toList()));
			}
		}
		

    	List<HoraTrabalhada> horasTrabalhadas = new ArrayList<HoraTrabalhada>();
    	List<ProjetoEscala> escalas = this.escalaService.findAllByProjetoId(projetoId);
    	for (ProjetoEscala projetoEscala : escalas) {
    		projetoEscala.setProjeto(this.projetoService.findById(projetoId));
    		projetoEscala.setQuantidadePrestadoresReal((int)this.prestadores.stream().filter(x->x.getProjetoEscala().getId()==projetoEscala.getId()).count());
    		projetoEscala.setAusenciaReposicoes(
					ausenciaReposicoes.stream().filter(x->
					x.getProjetoEscalaTroca().getId() == projetoEscala.getId() &&
					(x.getUsuarioTroca() != null &&
					!prestadores.stream().anyMatch(y->y.getPrestador().getId() == x.getUsuarioTroca().getId())) ).collect(Collectors.toList()));
    		
    		horasTrabalhadas.addAll(this.horaTrabalhadaService.findByProjetoEscalaId(projetoEscala.getId(), anoAtual, mesAtual));
		}

		modelView.addObject("horasTrabalhadas", horasTrabalhadas.stream().filter(x->x.getTipoAcao() == 1 || x.getDataHoraFim()==null).collect(Collectors.toList()));	
		modelView.addObject("solicitacaoId", solicitacao);	
		
		modelView.addObject("mes", mesAtual);	
		modelView.addObject("ano", anoAtual);

		modelView.addObject("mesProximo", mesAtual == 12 ? 1 : mesAtual+1);	
		modelView.addObject("anoProximo", mesAtual == 12 ? anoAtual + 1 : anoAtual);

		modelView.addObject("mesAnterior", mesAtual == 1 ? 12 : mesAtual-1);	
		modelView.addObject("anoAnterior", mesAtual == 1 ? anoAtual-1 : anoAtual);
		
    	LocalDate data = LocalDate.of((ano != 0 ? ano : Utilities.now().getYear()), (mes != 0 ? mes : Utilities.now().getMonth().getValue()), 1);
    	modelView.addObject("data", data);	
    	List<DiasMes> diasMes = new ArrayList<DiasMes>();
    	LocalDate dataIni = data;
		while (data.getMonth() == dataIni.getMonth()) {
			int diaSemana = data.getDayOfWeek().getValue();
			diasMes.add(new DiasMes(diaSemana, DiaSemanaEnum.GetDiaSemanaFromId(diaSemana).getNome() + " " + data.getDayOfMonth() + "/" + data.getMonthValue(), data));
			data = data.plusDays(1);
		}

		
		List<AusenciaSolicitacao> ausenciaSolicitacoes = null;
		if (escalaId != 0) {
			ausenciaSolicitacoes = this.ausenciaSolicitacaoService.findAllByProjetoEscalaId(anoAtual, mesAtual, escalaId, aceito);
		}
		else {
			ausenciaSolicitacoes = this.ausenciaSolicitacaoService.findAllByProjetoId(anoAtual, mesAtual, projetoId, aceito);
		}

		
		if (ausenciaSolicitacoes != null && ausenciaSolicitacoes.size() > 0) {
			for (ProjetoEscalaPrestador projEsc : this.prestadores) {
				projEsc.setAusenciaSolicitacoes(
						ausenciaSolicitacoes.stream().filter(x->
						x.getProjetoEscala().getId() == projEsc.getProjetoEscala().getId() &&
						x.getUsuario().getId() == projEsc.getPrestador().getId() ).collect(Collectors.toList()));
			}
		}
    	
		
		if (ausenciaReposicoes != null && ausenciaReposicoes.size() > 0) {
			for (ProjetoEscalaPrestador projEsc : this.prestadores) {
				projEsc.setAusenciaReposicoes(
						ausenciaReposicoes.stream().filter(x->
						x.getProjetoEscalaTroca().getId() == projEsc.getProjetoEscala().getId() &&						
						(x.getUsuarioTroca() != null &&
							x.getUsuarioTroca().getId() == projEsc.getPrestador().getId()) ).collect(Collectors.toList()));
			}
		}
		
		List<ProjetoEscalaPrestador> prestadores = escalaId == 0 ? this.prestadores : this.prestadores.stream().filter(x->x.getProjetoEscala().getId()==escalaId).collect(Collectors.toList());
				
		modelView.addObject("prestadores", prestadores); //.toArray(ProjetoEscalaPrestador[]::new)	
    	
		modelView.addObject("diasMes", diasMes);		
    	
    	modelView.addObject("escalas", escalas);
    	
		return modelView;
	}   
}
