package com.packageIxia.SistemaControleEscala.Controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscala;
import com.packageIxia.SistemaControleEscala.Services.Projetos.ProjetoEscalaService;

@Controller
public class AprovacaoHorasController {

	private ModelAndView modelView = new ModelAndView("projeto/aprovacaoHorasView");
	private ProjetoEscalaService projetoEscalaService;
	
	public AprovacaoHorasController(ProjetoEscalaService projetoEscalaService) {
		this.projetoEscalaService = projetoEscalaService;
	}
	
	@GetMapping("aprovacaohoras")
	public ModelAndView index() {

		List<ProjetoEscala> projetoEscalas = this.projetoEscalaService.findAllByPermissao();
		this.modelView.addObject("escalas", projetoEscalas);

		this.modelView.addObject("escalaId", 54);
		
		return this.modelView;
	}
}
