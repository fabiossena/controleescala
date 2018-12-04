package com.packageIxia.sistemaControleEscala.controllers;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IAusenciaSolicitacao;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IFuncao;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IHoraTrabalhada;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoEscala;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.models.projeto.DadosAcessoAprovacaoHoras;
import com.packageIxia.sistemaControleEscala.models.projeto.HoraTrabalhada;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscala;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.models.referencias.PerfilAcessoEnum;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Controller
public class IndexController {

	private IProjetoEscalaPrestador projetoEscalaPrestadorService;
	private IAusenciaSolicitacao ausenciaSolicitacaoService;
	private IProjetoEscala projetoEscalaService;
	private IHoraTrabalhada horaTrabalhadaService;

	public IndexController(
			IProjetoEscalaPrestador projetoEscalaPrestadorService,
			IAusenciaSolicitacao ausenciaSolicitacaoService,
			IHoraTrabalhada horaTrabalhadaService,
			IProjetoEscala projetoEscalaService,
			IFuncao funcaoConfiguracaoService) {
		this.projetoEscalaPrestadorService = projetoEscalaPrestadorService;
		this.ausenciaSolicitacaoService = ausenciaSolicitacaoService;
		this.projetoEscalaService = projetoEscalaService;
		this.horaTrabalhadaService = horaTrabalhadaService;
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
        	

    		if (usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.atendimento.getId() ||
				usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.monitoramento.getId()) {
	    		List<ProjetoEscala> projetoEscalas = this.projetoEscalaService.findAllByPermissao(true);
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
		    				dadosAcessoAprovacaoHoras.setDadosAprovacao(horaTrabalhada.getHoraAprovacao().getHorasTrabalhadas().stream().filter(x->x.getDataHoraInicio().isAfter(horaTrabalhadaPrimeira.getDataHoraInicio().minusSeconds(1))).collect(Collectors.toList()));
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
	
	@GetMapping("horas/iniciarEscala/{id}")
	public ModelAndView horaIniciar(
			@PathVariable("id") long id,
			@RequestParam(value = "tipo", defaultValue = "1") int tipo,
			@RequestParam(value = "iniciar", defaultValue = "true") boolean iniciar,
			@RequestParam(value = "motivo", defaultValue = "") String motivo) throws Exception {
		
		ModelAndView vw = new ModelAndView("redirect:/");
		
		String message = this.horaTrabalhadaService.save(id, tipo, iniciar, motivo);  
		vw.addObject("errorMessage", null);
		
		if (message != "") {
			vw.addObject("errorMessage", message);
		}
		
		return vw;
	}
}
