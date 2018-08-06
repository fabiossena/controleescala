package com.packageIxia.SistemaControleEscala.Controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.packageIxia.SistemaControleEscala.Models.Projeto.Projeto;
import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscala;
import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscalaPrestador;
import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoFolgaSemanal;
import com.packageIxia.SistemaControleEscala.Models.Referencias.DiaSemanaEnum;
import com.packageIxia.SistemaControleEscala.Models.Referencias.DiasMes;
import com.packageIxia.SistemaControleEscala.Models.Usuario.Usuario;
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

	@Autowired
	public DashboardController(
			ProjetoService projetoService,
			ProjetoEscalaService escalaService,
			ProjetoEscalaPrestadorService prestadorService,
			ProjetoFolgaSemanalService projetoFolgaSemanalService) {
		this.escalaService = escalaService;
		this.projetoService = projetoService;
		this.prestadorService = prestadorService;
		this.projetoFolgaSemanalService = projetoFolgaSemanalService;
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
    	
		return dashboard(request, projetos.get(0).getId(), 0, 0, 0);
	}

	@GetMapping("/dashboard/{id}")
	public ModelAndView projetos(
			HttpServletRequest request,
			@PathVariable("id") long id,
			@RequestParam(value = "mes", defaultValue = "0") int mes,
			@RequestParam(value = "ano", defaultValue = "0") int ano,
			@RequestParam(value = "escala", defaultValue = "0") int escalaId) {		
		
		System.out.println("dashboard");
		return dashboard(request, id, mes, ano, escalaId);
	}

	private ModelAndView dashboard(HttpServletRequest request, long id, int mes, int ano, int escalaId) {
		
		Projeto projeto = this.projetoService.findById(id);
    	
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
    	
		this.prestadores = this.prestadorService.findAllByProjetoId(id);
		for (ProjetoEscalaPrestador prest : prestadores) {
			prest.setProjetoFolgasSemanais(projetoFolgaSemanalService.findAllByProjetoEscalaPrestadorId(prest.getId()).stream().toArray(ProjetoFolgaSemanal[]::new));
		}
		
    	List<ProjetoEscala> escalas = this.escalaService.findAllByProjetoId(id);
    	for (ProjetoEscala projetoEscala : escalas) {
    		projetoEscala.setQuantidadePrestadoresReal((int)this.prestadores.stream().filter(x->x.getProjetoEscala().getId()==projetoEscala.getId()).count());
		}
		
		int mesAtual = (mes != 0 ? mes : LocalDate.now().getMonth().getValue());
		int anoAtual = (ano != 0 ? ano : LocalDate.now().getYear());

		modelView.addObject("mes", mesAtual);	
		modelView.addObject("ano", anoAtual);

		modelView.addObject("mesProximo", mesAtual == 12 ? 1 : mesAtual+1);	
		modelView.addObject("anoProximo", mesAtual == 12 ? anoAtual + 1 : anoAtual);

		modelView.addObject("mesAnterior", mesAtual == 1 ? 12 : mesAtual-1);	
		modelView.addObject("anoAnterior", mesAtual == 1 ? anoAtual-1 : anoAtual);
		
    	LocalDate data = LocalDate.of((ano != 0 ? ano : LocalDate.now().getYear()), (mes != 0 ? mes : LocalDate.now().getMonth().getValue()), 1);
    	modelView.addObject("data", data);	
		
    	modelView.addObject("prestadores", escalaId == 0 ? this.prestadores : this.prestadores.stream().filter(x->x.getProjetoEscala().getId()==escalaId).toArray(ProjetoEscalaPrestador[]::new));	
    	
    	List<DiasMes> diasMes = new ArrayList<DiasMes>();
    	LocalDate dataIni = data;
		while (data.getMonth() == dataIni.getMonth()) {
			int diaSemana = data.getDayOfWeek().getValue() == 7 ? 1 : data.getDayOfWeek().getValue() + 1;
			diasMes.add(new DiasMes(diaSemana, DiaSemanaEnum.GetDiaSemanaFromId(diaSemana).getNome() + " " + data.getDayOfMonth() + "/" + data.getMonthValue(), data));
			data = data.plusDays(1);
		}
    	
    	modelView.addObject("diasMes", diasMes);		
    	
    	modelView.addObject("escalas", escalas);
    	
		return modelView;
	}   
}
