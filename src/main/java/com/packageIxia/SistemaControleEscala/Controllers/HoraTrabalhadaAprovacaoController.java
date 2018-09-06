package com.packageIxia.SistemaControleEscala.Controllers;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.packageIxia.SistemaControleEscala.Models.Projeto.DadosAcessoAprovacaoHoras;
import com.packageIxia.SistemaControleEscala.Models.Projeto.FuncaoConfiguracao;
import com.packageIxia.SistemaControleEscala.Models.Projeto.HoraAprovacao;
import com.packageIxia.SistemaControleEscala.Models.Projeto.HoraTrabalhada;
import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscala;
import com.packageIxia.SistemaControleEscala.Models.Referencias.DadoGenerico;
import com.packageIxia.SistemaControleEscala.Models.Referencias.FuncaoEnum;
import com.packageIxia.SistemaControleEscala.Models.Usuario.Usuario;
import com.packageIxia.SistemaControleEscala.Services.Projetos.FuncaoConfiguracaoService;
import com.packageIxia.SistemaControleEscala.Services.Projetos.HoraAprovacaoService;
import com.packageIxia.SistemaControleEscala.Services.Projetos.HoraTrabalhadaService;
import com.packageIxia.SistemaControleEscala.Services.Projetos.ProjetoEscalaService;

@Controller
public class HoraTrabalhadaAprovacaoController {

	private ModelAndView horaAprovacaoView = new ModelAndView("projeto/horasAprovacoesView");
	private ModelAndView horaTrabalhadaView = new ModelAndView("projeto/horasTrabalhadasView");
	private ProjetoEscalaService projetoEscalaService;
	private HoraAprovacaoService horaAprovacaoService;
	private HoraTrabalhadaService horaTrabalhadaService;
	private HttpSession session;
	private List<HoraAprovacao> aprovacaoHoras;
	private List<ProjetoEscala> projetoEscalas;
	private List<HoraTrabalhada> horasTrabalhadas;
	private HoraAprovacao aprovacaoHora;
	private FuncaoConfiguracaoService funcaoConfiguracaoService;
	
	public HoraTrabalhadaAprovacaoController(
			ProjetoEscalaService projetoEscalaService,
			HoraAprovacaoService horaAprovacaoService,
			HoraTrabalhadaService horaTrabalhadaService,
			FuncaoConfiguracaoService funcaoConfiguracaoService,
			HttpSession session) {
		this.projetoEscalaService = projetoEscalaService;
		this.horaAprovacaoService = horaAprovacaoService;
		this.horaTrabalhadaService = horaTrabalhadaService;
		this.funcaoConfiguracaoService = funcaoConfiguracaoService;
		this.session = session;
	}
	
	@GetMapping("aprovacaoHoras")
	public ModelAndView index(
			@RequestParam(value = "ano", defaultValue = "0") int ano,
			@RequestParam(value = "mes", defaultValue = "0") int mes) throws Exception {

		this.projetoEscalas = this.projetoEscalaService.findAllByPermissao();
		this.horaAprovacaoView.addObject("escalas", this.projetoEscalas);

		if (ano == 0 || mes == 0) {
			ano = LocalDate.now().getMonthValue() == 1 ? LocalDate.now().getYear() - 1: LocalDate.now().getYear();
			mes = LocalDate.now().getMonthValue() == 1 ? 12: LocalDate.now().getMonthValue()-1;
		}
		
		this.horaAprovacaoView.addObject("anoBase", LocalDate.now().getYear());
		this.horaAprovacaoView.addObject("ano", ano);
		this.horaAprovacaoView.addObject("mes", mes);

		Usuario usuarioLogado = ((Usuario)session.getAttribute("usuarioLogado"));
		this.aprovacaoHoras = this.horaAprovacaoService.findAll(ano, mes);
		List<FuncaoConfiguracao> funcaoConfiguracao = funcaoConfiguracaoService.findAll();
		for (HoraAprovacao horaAprovacao : aprovacaoHoras) {
			horaAprovacao.setDadosAcessoAprovacaoHoras(new DadosAcessoAprovacaoHoras(horaAprovacao, usuarioLogado, funcaoConfiguracao));
			horaAprovacao.setTotalHoras(horaAprovacao.getDadosAcessoAprovacaoHoras().getTotalHoras());
			horaAprovacao.setTotalValor(horaAprovacao.getDadosAcessoAprovacaoHoras().getTotalValor());
		}
		
		this.horaAprovacaoView.addObject("aprovacaoHoras", this.aprovacaoHoras);
		
		return this.horaAprovacaoView;
	}
	
	@GetMapping("horasTrabalhadas/{id}")
	public ModelAndView horaTrabalhada(@PathVariable("id") long id) {

		horaTrabalhadaView.addObject("result", null);
		horaTrabalhadaView.addObject("errorMessage", null);
		horaTrabalhadaView.addObject("isDisableCampos", false); 
		

		Usuario usuarioLogado = ((Usuario)session.getAttribute("usuarioLogado"));
		this.aprovacaoHora = this.horaAprovacaoService.findById(id);
		
		boolean isMonitoriaGerencia = 
			(usuarioLogado.getFuncaoId() == FuncaoEnum.monitoramento.funcao.getId() &&
				this.aprovacaoHora.getHorasTrabalhadas().stream().anyMatch(x->x.getProjetoEscala().getMonitor().getId() == usuarioLogado.getId())) || 
			(usuarioLogado.getFuncaoId() == FuncaoEnum.gerencia.funcao.getId() &&
				this.aprovacaoHora.getHorasTrabalhadas().stream().anyMatch(x->x.getProjetoEscala().getProjeto().getGerente().getId() == usuarioLogado.getId()));
		
    	if (usuarioLogado.getFuncaoId() != FuncaoEnum.administracao.funcao.getId() &&
   			usuarioLogado.getFuncaoId() != FuncaoEnum.financeiro.funcao.getId() &&
			usuarioLogado.getId() != this.aprovacaoHora.getPrestador().getId() && 
			!isMonitoriaGerencia) {
    		ModelAndView erroModelView = new ModelAndView("redirect:/error");
    		erroModelView.addObject("errorMessage", "Não permitido acesso a tela de horas trabalhadas");
            return erroModelView;
    	}

		horaTrabalhadaView.addObject("isDisableInsertCampos", true);   
    	this.aprovacaoHora.setDadosAcessoAprovacaoHoras(new DadosAcessoAprovacaoHoras(this.aprovacaoHora, usuarioLogado, funcaoConfiguracaoService.findAll()));
    	if ((LocalDate.now().getYear() + LocalDate.now().getMonthValue()) <= (this.aprovacaoHora.getData().getYear() + this.aprovacaoHora.getData().getMonthValue()) ||
    			!this.aprovacaoHora.getDadosAcessoAprovacaoHoras().getDadosAcesso()) {
    		horaTrabalhadaView.addObject("isDisableCampos", true);    		
    	}  
    	
    	this.aprovacaoHora.setTotalHoras(this.aprovacaoHora.getDadosAcessoAprovacaoHoras().getTotalHoras());
    	this.aprovacaoHora.setTotalValor(this.aprovacaoHora.getDadosAcessoAprovacaoHoras().getTotalValor());
		
		this.projetoEscalas = this.projetoEscalas != null ? this.projetoEscalas : this.projetoEscalaService.findAllByPermissao();
		this.horaTrabalhadaView.addObject("escalas", projetoEscalas);

		//this.horaAprovacaoId = id;
		
		this.horasTrabalhadas = this.horaTrabalhadaService.findByHoraAprovacaoId(id);	
		this.horaTrabalhadaView.addObject("aprovacaoHora", this.aprovacaoHora);		
		this.horaTrabalhadaView.addObject("horaTrabalhada", new HoraTrabalhada());		
		this.horaTrabalhadaView.addObject("horasTrabalhadas", horasTrabalhadas);	

		return this.horaTrabalhadaView;
	}
	
	@PostMapping("horasTrabalhadas/{id}")
	public ModelAndView horaTrabalhada(
			@Valid @ModelAttribute("horaTrabalhada") HoraTrabalhada horaTrabalhada,
			BindingResult result) {

		System.out.println("submit hora trabalhada"); 
		horaTrabalhadaView.addObject("isDisableInsertCampos", false);    	
    	
		this.horaTrabalhadaView.addObject("escalas", this.projetoEscalas);
		this.horaTrabalhadaView.addObject("aprovacaoHora", this.aprovacaoHora);	
		this.horaTrabalhadaView.addObject("horaTrabalhada", horaTrabalhada);		
		this.horaTrabalhadaView.addObject("horasTrabalhadas", this.horasTrabalhadas);	

		//this.horaAprovacaoId = id; 

		horaTrabalhadaView.addObject("errorMessage", null);
		boolean hasErrors = result.hasErrors();
		System.out.println(hasErrors);  
		horaTrabalhadaView.addObject("result", result);
		
    	if(hasErrors) {
            return horaTrabalhadaView;
        }    		
    	
    	horaTrabalhada.setHoraAprovacao(this.aprovacaoHora);
    	if (horaTrabalhada.getId() == -1) {
    		horaTrabalhada.setId(0);
    	}
    	
    	String error = this.horaTrabalhadaService.setAndSave(horaTrabalhada, horaTrabalhada.getId() == 0 ? null : this.horasTrabalhadas.stream().filter(x -> x.getId() == horaTrabalhada.getId()).findFirst().orElseGet(null));
    	if(!error.isEmpty()) {
        	if (horaTrabalhada.getId() == 0) {
        		horaTrabalhada.setId(-1);
        	}
    		horaTrabalhadaView.addObject("errorMessage", error);
            return horaTrabalhadaView;
        }    		
    	
		return new ModelAndView("redirect:/horasTrabalhadas/"+ horaTrabalhada.getHoraAprovacao().getId());
	}
	
	@GetMapping("horas/aprovar/{id}")
	public ModelAndView horaAprovar(
			@PathVariable("id") long id,
			@RequestParam(value = "aprovar", defaultValue = "true") boolean aprovar,
			@RequestParam(value = "motivo", defaultValue = "") String motivo) throws Exception {
		
		
		this.horaAprovacaoService.updateAprovacao(aprovar, id, motivo, this.aprovacaoHora);

		return new ModelAndView("redirect:/horasTrabalhadas/"+id);
	}
}
