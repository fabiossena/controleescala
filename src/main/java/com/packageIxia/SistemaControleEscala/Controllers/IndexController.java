package com.packageIxia.SistemaControleEscala.Controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscalaPrestador;
import com.packageIxia.SistemaControleEscala.Models.Usuario.Usuario;
import com.packageIxia.SistemaControleEscala.Services.Projetos.AusenciaSolicitacaoService;
import com.packageIxia.SistemaControleEscala.Services.Projetos.ProjetoEscalaPrestadorService;

@Controller
public class IndexController {

	private ProjetoEscalaPrestadorService projetoEscalaPrestadorService;
	private AusenciaSolicitacaoService ausenciaSolicitacaoService;

	public IndexController(
			ProjetoEscalaPrestadorService projetoEscalaPrestadorService,
			AusenciaSolicitacaoService ausenciaSolicitacaoService) {
		this.projetoEscalaPrestadorService = projetoEscalaPrestadorService;
		this.ausenciaSolicitacaoService = ausenciaSolicitacaoService;
	}
	
    @GetMapping(value = "/")
	public ModelAndView index(HttpServletRequest request) {
    	Usuario usuarioLogado = (Usuario)request.getSession().getAttribute("usuarioLogado");
        if(usuarioLogado != null) {
        	ModelAndView index = new ModelAndView("index");
    		System.out.println(usuarioLogado.getMatricula());
        	index.addObject("usuario", usuarioLogado);
        
        	List<ProjetoEscalaPrestador> projetosCadastrados = this.projetoEscalaPrestadorService.findAllProjetosByPrestadorId(usuarioLogado != null ? usuarioLogado.getId() : 0, true, true, true, true, true, false, false);
    		index.addObject("projetosCadastrados", projetosCadastrados);
        	
    		index.addObject("solicitacoesAusencias", ausenciaSolicitacaoService.findAll(true));
        	
        	return index;
        }
        		
        return new ModelAndView("loginView");
	}

    @GetMapping(value = "/error")
	public ModelAndView error(HttpServletRequest request) {
        return new ModelAndView("errorView");
    }
}
