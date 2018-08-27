package com.packageIxia.SistemaControleEscala.Controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscala;
import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscalaPrestador;
import com.packageIxia.SistemaControleEscala.Models.Usuario.Usuario;
import com.packageIxia.SistemaControleEscala.Services.Projetos.AusenciaSolicitacaoService;
import com.packageIxia.SistemaControleEscala.Services.Projetos.ProjetoEscalaPrestadorService;
import com.packageIxia.SistemaControleEscala.Services.Projetos.ProjetoEscalaService;

@Controller
public class IndexController {

	private ProjetoEscalaPrestadorService projetoEscalaPrestadorService;
	private AusenciaSolicitacaoService ausenciaSolicitacaoService;
	private ProjetoEscalaService projetoEscalaService;

	public IndexController(
			ProjetoEscalaPrestadorService projetoEscalaPrestadorService,
			AusenciaSolicitacaoService ausenciaSolicitacaoService,
			ProjetoEscalaService projetoEscalaService) {
		this.projetoEscalaPrestadorService = projetoEscalaPrestadorService;
		this.ausenciaSolicitacaoService = ausenciaSolicitacaoService;
		this.projetoEscalaService = projetoEscalaService;
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
        	

    		List<ProjetoEscala> projetoEscalas = this.projetoEscalaService.findAllByPermissao();
    		index.addObject("escalas", projetoEscalas);

    		index.addObject("escalaId", 54);

    		index.addObject("iniciarDisabled", false);
    		index.addObject("pausarDisabled", true);
    		index.addObject("pausar", "pausar");
    		index.addObject("motivo", "");
    		index.addObject("pararDisabled", true);
    		
    		
        	return index;
        }
        		
        return new ModelAndView("loginView");
	}

    @GetMapping(value = "/error")
	public ModelAndView error(HttpServletRequest request) {
        return new ModelAndView("errorView");
    }
}
