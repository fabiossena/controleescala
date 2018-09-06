package com.packageIxia.SistemaControleEscala.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.packageIxia.SistemaControleEscala.Models.Projeto.DadosAcessoAprovacaoHoras;
import com.packageIxia.SistemaControleEscala.Models.Projeto.HoraTrabalhada;
import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscala;
import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscalaPrestador;
import com.packageIxia.SistemaControleEscala.Models.Referencias.FuncaoEnum;
import com.packageIxia.SistemaControleEscala.Models.Usuario.Usuario;
import com.packageIxia.SistemaControleEscala.Services.Projetos.AusenciaSolicitacaoService;
import com.packageIxia.SistemaControleEscala.Services.Projetos.FuncaoConfiguracaoService;
import com.packageIxia.SistemaControleEscala.Services.Projetos.HoraAprovacaoService;
import com.packageIxia.SistemaControleEscala.Services.Projetos.HoraTrabalhadaService;
import com.packageIxia.SistemaControleEscala.Services.Projetos.ProjetoEscalaPrestadorService;
import com.packageIxia.SistemaControleEscala.Services.Projetos.ProjetoEscalaService;

@Controller
public class IndexController {

	private ProjetoEscalaPrestadorService projetoEscalaPrestadorService;
	private AusenciaSolicitacaoService ausenciaSolicitacaoService;
	private ProjetoEscalaService projetoEscalaService;
	private HoraTrabalhadaService horaTrabalhadaService;
	private HoraAprovacaoService horaAprovacaoService;
	private FuncaoConfiguracaoService funcaoConfiguracaoService;

	public IndexController(
			ProjetoEscalaPrestadorService projetoEscalaPrestadorService,
			AusenciaSolicitacaoService ausenciaSolicitacaoService,
			HoraTrabalhadaService horaTrabalhadaService,
			HoraAprovacaoService horaAprovacaoService,
			ProjetoEscalaService projetoEscalaService,
			FuncaoConfiguracaoService funcaoConfiguracaoService) {
		this.projetoEscalaPrestadorService = projetoEscalaPrestadorService;
		this.ausenciaSolicitacaoService = ausenciaSolicitacaoService;
		this.projetoEscalaService = projetoEscalaService;
		this.horaTrabalhadaService = horaTrabalhadaService;
		this.horaAprovacaoService = horaAprovacaoService;
		this.funcaoConfiguracaoService = funcaoConfiguracaoService;
	}
	
    @GetMapping(value = "/")
	public ModelAndView index(HttpServletRequest request) throws Exception {
    	Usuario usuarioLogado = (Usuario)request.getSession().getAttribute("usuarioLogado");
        if(usuarioLogado != null) {
        	ModelAndView index = new ModelAndView("index");
    		System.out.println(usuarioLogado.getMatricula());
        	index.addObject("usuario", usuarioLogado);
        
        	List<ProjetoEscalaPrestador> projetosCadastrados = this.projetoEscalaPrestadorService.findAllProjetosByPrestadorId(usuarioLogado != null ? usuarioLogado.getId() : 0, true, true, true, true, true, false, false);
    		index.addObject("projetosCadastrados", projetosCadastrados);
        	
    		index.addObject("solicitacoesAusencias", ausenciaSolicitacaoService.findAll(true));
			index.addObject("escalaId", 0);	
        	

    		if (usuarioLogado.getFuncaoId() == FuncaoEnum.atendimento.getFuncao().getId() ||
				usuarioLogado.getFuncaoId() == FuncaoEnum.monitoramento.getFuncao().getId()) {
	    		List<ProjetoEscala> projetoEscalas = this.projetoEscalaService.findAllByPermissao();
	    		index.addObject("escalas", projetoEscalas);

	    		index.addObject("iniciarDisabled", false);
	    		index.addObject("pausarDisabled", true);
	    		index.addObject("pausar", "pausar");
	    		index.addObject("motivo", "");
	    		index.addObject("pararDisabled", true);
	    		
	    		HoraTrabalhada horaTrabalhada = this.horaTrabalhadaService.findLastByPrestadorId(usuarioLogado.getId());
	    		if (horaTrabalhada != null && horaTrabalhada.getTipoAcao() == 2 && horaTrabalhada.getDataHoraFim() != null){
	    			horaTrabalhada = this.horaTrabalhadaService.findLastStartedByPrestadorId(usuarioLogado.getId());
	    		}
	    		
	    		if (horaTrabalhada != null) {
	    			index.addObject("escalaId", horaTrabalhada.getProjetoEscala().getId());	    			
	    			if (horaTrabalhada.getDataHoraFim() == null) {
	    				
	    				DadosAcessoAprovacaoHoras dadosAcessoAprovacaoHoras = new DadosAcessoAprovacaoHoras();

	    				HoraTrabalhada horaTrabalhadaPrimeira = this.horaTrabalhadaService.findLastStartedByPrestadorId(usuarioLogado.getId());
	    				if (horaTrabalhadaPrimeira != null && horaTrabalhadaPrimeira.getDataHoraFim() == null) {
		    				dadosAcessoAprovacaoHoras.setDadosAprovacao(horaTrabalhada.getHoraAprovacao().getHorasTrabalhadas().stream().filter(x->x.getDataHoraInicio().isAfter(horaTrabalhadaPrimeira.getDataHoraInicio().minusSeconds(1))).collect(Collectors.toList()), funcaoConfiguracaoService.findAll());
		    				index.addObject("totalSegundos", dadosAcessoAprovacaoHoras.getTotalSegundos());
	    				}

		    			if (horaTrabalhada.getTipoAcao() == 1) {	
				    		index.addObject("iniciarDisabled", true);
				    		index.addObject("pausarDisabled", false);
				    		index.addObject("pausar", "pausar");
				    		index.addObject("motivo", "");
				    		index.addObject("pararDisabled", false);
		    			}
		    			else  {	
				    		index.addObject("iniciarDisabled", true);
				    		index.addObject("pausarDisabled", false);
				    		index.addObject("pausar", "retomar");
				    		index.addObject("motivo", "");
				    		index.addObject("pararDisabled", true);
		    			}
	    			}
	    		}
    		}
    		
        	return index;
        }
        		
        return new ModelAndView("loginView");
	}

    @GetMapping(value = "/error")
	public ModelAndView error(HttpServletRequest request) {
        return new ModelAndView("errorView");
    }
	
	@GetMapping("horas/iniciarEscala/{id}")
	public ModelAndView horaIniciar(
			@PathVariable("id") long id,
			@RequestParam(value = "tipo", defaultValue = "1") int tipo,
			@RequestParam(value = "iniciar", defaultValue = "true") boolean iniciar,
			@RequestParam(value = "motivo", defaultValue = "") String motivo) throws Exception {
		
		
		this.horaTrabalhadaService.save(id, tipo, iniciar, motivo);

		return new ModelAndView("redirect:/");
	}
}
