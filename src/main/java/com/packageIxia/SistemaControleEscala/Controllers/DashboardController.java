package com.packageIxia.SistemaControleEscala.Controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.packageIxia.SistemaControleEscala.Models.Projeto.AusenciaReposicao;
import com.packageIxia.SistemaControleEscala.Models.Projeto.AusenciaSolicitacao;
import com.packageIxia.SistemaControleEscala.Models.Projeto.HoraTrabalhada;
import com.packageIxia.SistemaControleEscala.Models.Projeto.Projeto;
import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscala;
import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscalaPrestador;
import com.packageIxia.SistemaControleEscala.Models.Referencias.DiaSemanaEnum;
import com.packageIxia.SistemaControleEscala.Models.Referencias.DiasMes;
import com.packageIxia.SistemaControleEscala.Services.Projetos.AusenciaReposicaoService;
import com.packageIxia.SistemaControleEscala.Services.Projetos.AusenciaSolicitacaoService;
import com.packageIxia.SistemaControleEscala.Services.Projetos.HoraTrabalhadaService;
import com.packageIxia.SistemaControleEscala.Services.Projetos.ProjetoEscalaPrestadorService;
import com.packageIxia.SistemaControleEscala.Services.Projetos.ProjetoEscalaService;
import com.packageIxia.SistemaControleEscala.Services.Projetos.ProjetoFolgaSemanalService;
import com.packageIxia.SistemaControleEscala.Services.Projetos.ProjetoService;

@Controller
public class DashboardController {
	
	private ProjetoEscalaPrestadorService prestadorService;
	private ModelAndView modelView = new ModelAndView("projeto/projetoDashboardView");
	private List<ProjetoEscalaPrestador> prestadores;
	private ProjetoFolgaSemanalService projetoFolgaSemanalService;
	private ProjetoEscalaService escalaService;
	private ProjetoService projetoService;
	private AusenciaSolicitacaoService ausenciaSolicitacaoService;
	private AusenciaReposicaoService ausenciaReposicaoService;
	private HoraTrabalhadaService horaTrabalhadaService;

	@Autowired
	public DashboardController(
			ProjetoService projetoService,
			ProjetoEscalaService escalaService,
			ProjetoEscalaPrestadorService prestadorService,
			ProjetoFolgaSemanalService projetoFolgaSemanalService,
			AusenciaSolicitacaoService ausenciaSolicitacaoService,
			HoraTrabalhadaService horaTrabalhadaService,
			AusenciaReposicaoService ausenciaReposicaoService) {
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
    	
//    	if (usuarioLogado.getFuncaoId() == FuncaoEnum.atendimento.funcao.getId() ||
//			usuarioLogado.getFuncaoId() == FuncaoEnum.financeiro.funcao.getId()) {
//    		ModelAndView erroModelView = new ModelAndView("errorView");
//    		erroModelView.addObject("errorMessage", "Não permitido acesso a tela de projetos");
//            return erroModelView;
//    	}
    	
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
    	
		this.prestadores = this.prestadorService.findAllByProjetoId(projetoId);
		for (ProjetoEscalaPrestador prest : prestadores) {
			prest.setProjetoFolgasSemanais(projetoFolgaSemanalService.findAllByProjetoEscalaPrestadorId(prest.getId()));
		}

		
		int mesAtual = (mes != 0 ? mes : LocalDate.now().getMonth().getValue());
		int anoAtual = (ano != 0 ? ano : LocalDate.now().getYear());

		
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
					(x.getUsuarioTroca() == null) ).collect(Collectors.toList()));
    		
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
		
    	LocalDate data = LocalDate.of((ano != 0 ? ano : LocalDate.now().getYear()), (mes != 0 ? mes : LocalDate.now().getMonth().getValue()), 1);
    	modelView.addObject("data", data);	
    	List<DiasMes> diasMes = new ArrayList<DiasMes>();
    	LocalDate dataIni = data;
		while (data.getMonth() == dataIni.getMonth()) {
			int diaSemana = data.getDayOfWeek().getValue() == 7 ? 1 : data.getDayOfWeek().getValue() + 1;
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
						(x.getUsuarioTroca() != null && x.getUsuarioTroca().getId() == projEsc.getPrestador().getId()) ).collect(Collectors.toList()));
			}
		}
		    	
		List<ProjetoEscalaPrestador> prestadores = escalaId == 0 ? this.prestadores : this.prestadores.stream().filter(x->x.getProjetoEscala().getId()==escalaId).collect(Collectors.toList());
		modelView.addObject("prestadores", prestadores); //.toArray(ProjetoEscalaPrestador[]::new)	
    	
		modelView.addObject("diasMes", diasMes);		
    	
    	modelView.addObject("escalas", escalas);
    	
		return modelView;
	}   
}
