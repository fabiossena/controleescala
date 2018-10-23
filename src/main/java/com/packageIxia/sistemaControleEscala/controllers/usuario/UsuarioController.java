package com.packageIxia.sistemaControleEscala.controllers.usuario;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IFuncao;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IHoraExtrato;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.interfaces.referencias.ICentroCusto;
import com.packageIxia.sistemaControleEscala.interfaces.referencias.IReferencias;
import com.packageIxia.sistemaControleEscala.interfaces.usuario.IUsuario;
import com.packageIxia.sistemaControleEscala.interfaces.usuario.IUsuarioTurnosDisponiveis;
import com.packageIxia.sistemaControleEscala.models.projeto.HoraExtrato;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.models.referencias.Banco;
import com.packageIxia.sistemaControleEscala.models.referencias.CentroCusto;
import com.packageIxia.sistemaControleEscala.models.referencias.DadoGenerico;
import com.packageIxia.sistemaControleEscala.models.referencias.DiaSemana;
import com.packageIxia.sistemaControleEscala.models.referencias.Funcao;
import com.packageIxia.sistemaControleEscala.models.referencias.PerfilAcessoEnum;
import com.packageIxia.sistemaControleEscala.models.referencias.MotivoAusencia;
import com.packageIxia.sistemaControleEscala.models.referencias.Pais;
import com.packageIxia.sistemaControleEscala.models.usuario.FolgaSemanalPlanejadaUsuario;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;
import com.packageIxia.sistemaControleEscala.models.usuario.UsuarioFuncaoAdicional;

@Controller
public class UsuarioController {

	private IUsuario usuarioService;
	private IReferencias referenciasService;
	private IUsuarioTurnosDisponiveis usuarioTurnosDisponiveisService;
	private IProjetoEscalaPrestador projetoEscalaPrestadorService;
	private ModelAndView modelViewCadastro = new ModelAndView("usuario/usuarioView");
	private ModelAndView modelViewCadastros = new ModelAndView("usuario/usuariosView");	
	private Usuario usuarioEditado;	
	private Usuario usuarioLogado;	
	private List<FolgaSemanalPlanejadaUsuario> folgasSemanaisPlanejadas;
	private boolean isDisableCamposChaves;
	private List<ProjetoEscalaPrestador> projetosCadastrados;
	private IProjetoEscalaPrestador prestadorService;
	private IHoraExtrato horaExtratoService;
	private IFuncao funcaoService;
	private ICentroCusto centroCustoService;
	private HttpServletRequest request;
	private HttpSession session;
	
	public UsuarioController(
    		HttpSession session, 
    		HttpServletRequest request,
			IUsuario usuarioService,
			IReferencias referenciasService,
			IUsuarioTurnosDisponiveis usuarioTurnosDisponiveisService,
			IProjetoEscalaPrestador projetoEscalaPrestadorService,
			IProjetoEscalaPrestador prestadorService,
			IFuncao funcaoService,
			IHoraExtrato horaExtratoService,
			ICentroCusto centroCustoService) {
		this.usuarioService = usuarioService;
		this.referenciasService = referenciasService;
		this.usuarioTurnosDisponiveisService = usuarioTurnosDisponiveisService;
		this.projetoEscalaPrestadorService = projetoEscalaPrestadorService;
		this.prestadorService = prestadorService;
		this.funcaoService = funcaoService;
		this.centroCustoService = centroCustoService;
		this.session = session;
		this.request = request;
		this.horaExtratoService = horaExtratoService;
	}

	@GetMapping("/usuarios")
	public ModelAndView usuarios() {
		
		System.out.println("cadastros");
    	modelViewCadastro.addObject("result", null);
    	modelViewCadastro.addObject("errorMessage", null);
    	
		this.usuarioLogado = ((Usuario)request.getSession().getAttribute("usuarioLogado"));
    	if (this.usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.atendimento.getId()) {
    		ModelAndView erroModelView = new ModelAndView("redirect:/error");
    		erroModelView.addObject("errorMessage", "Não permitido acesso a tela de usuários");
            return erroModelView;
    	}

		modelViewCadastros.addObject("usuariosCadastrados", this.usuarioService.findAllByUsuarioLogado(this.usuarioLogado));		
		modelViewCadastro.addObject("isCadastroUsuarioPage", true);		
				
		return modelViewCadastros;
	}
	
	@GetMapping("/usuario/meucadastro")
	public ModelAndView meuCadastro() {	
		
		this.isDisableCamposChaves = true;
		modelViewCadastro.addObject("isCadastroUsuarioPage", false);	
		modelViewCadastro.addObject("isDisableCamposChaves", this.isDisableCamposChaves);		
		modelViewCadastro.addObject("isDisableTodosCampos", false);

		this.usuarioLogado = ((Usuario)request.getSession().getAttribute("usuarioLogado"));
		this.usuarioEditado = this.usuarioService.findByUsuarioId(this.usuarioLogado.getId());	
		return usuarioGet();
	}
	
	@GetMapping("/usuario/{id}")
	public ModelAndView cadastroPorUsuario(
			@PathVariable("id") int id) {		
		return getCadastroPorUsuario(id, true);
	}


	@GetMapping("/usuario/{id}/editar")
	public ModelAndView cadastroPorUsuarioEdit(
			@PathVariable("id") int id) {		
		return getCadastroPorUsuario(id, false);
	}

	private ModelAndView getCadastroPorUsuario(int id, boolean readOnly) {
    	
		modelViewCadastro.addObject("result", null);
    	modelViewCadastro.addObject("errorMessage", null);
    	
		modelViewCadastro.addObject("isCadastroUsuarioPage", true);		
		modelViewCadastro.addObject("isDisableCamposChaves", false);		
		modelViewCadastro.addObject("isDisableTodosCampos", readOnly);

    	
    	if (this.usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.atendimento.getId()) {
    		ModelAndView erroModelView = new ModelAndView("redirect:/error");
    		erroModelView.addObject("errorMessage", "Não permitido acesso a tela de usuários");
            return erroModelView;
    	}
    	
    	if (!readOnly && 
			this.usuarioLogado.getFuncao().getPerfilAcessoId() != PerfilAcessoEnum.administracao.getId() &&
			this.usuarioLogado.getFuncao().getPerfilAcessoId() != PerfilAcessoEnum.gerencia.getId()) {
    		ModelAndView erroModelView = new ModelAndView("redirect:/error");
    		erroModelView.addObject("errorMessage", "Não permitido editar usuários");
            return erroModelView;
    	}
    	
		this.usuarioEditado = usuarioService.findByUsuarioId(id);
		if (this.usuarioEditado == null) {
    		ModelAndView erroModelView = new ModelAndView("redirect:/error");
    		erroModelView.addObject("errorMessage", "Usuário não existe");
            return erroModelView;
    	}
		
		return usuarioGet();
	}

	private ModelAndView usuarioGet() {

		modelViewCadastro.addObject("result", null);
    	modelViewCadastro.addObject("errorMessage", null);
    	
		System.out.println("cadastroUsuario");
		if (this.usuarioEditado != null && Strings.isBlank(this.usuarioEditado.getRepetirSenha())) {
			this.usuarioEditado.setRepetirSenha(this.usuarioEditado.getSenha());
		}
		
    	modelViewCadastro.addObject("orientacoesPeriodoDisponivel", "Periodo que esta disponível para trabalhar. Levaremos em consideração ao montar as escalas de atendimento.");		

		this.projetosCadastrados = this.projetoEscalaPrestadorService.findAllProjetosByPrestadorId(this.usuarioEditado != null ? this.usuarioEditado.getId() : 0, true, true, true, false);
    	modelViewCadastro.addObject("projetosCadastrados", projetosCadastrados);	
    	
    	this.folgasSemanaisPlanejadas = this.usuarioTurnosDisponiveisService.getFolgasSemanaisPlanejadasUsuario(this.usuarioEditado.getId());
    	modelViewCadastro.addObject("folgasSemanaisPlanejadas", folgasSemanaisPlanejadas);
    	modelViewCadastro.addObject("usuario", usuarioEditado); 
    	
    	HoraExtrato horaExtrato = this.horaExtratoService.findLastByUsuarioId(this.usuarioEditado.getId());
    	if (horaExtrato != null) {
    		modelViewCadastro.addObject("horasDisponiveisAno", Utilities.Round((double)horaExtrato.getMinutosTotalDisponiveis()/60));
    		modelViewCadastro.addObject("diasDisponiveisAno", Utilities.Round((double)horaExtrato.getMinutosTotalDisponiveis()/60/6));
    	}
    	
    	System.out.println("PeriodoDisponivel");
		System.out.println(usuarioEditado.getPeriodoDisponivelId());
		return modelViewCadastro;
	}   
    
    @PostMapping("/usuario/meucadastro")	
    public ModelAndView submitMeuCadastro(
    		@Valid @ModelAttribute("usuario")Usuario usuario, 
    		BindingResult result) throws IOException {
    	return usuarioPost(usuario, result, false);        
    }
    
    @PostMapping("/usuario/{id}/editar")	
    public ModelAndView submitCadastro(
    		@Valid @ModelAttribute("usuario")Usuario usuario,
    		BindingResult result,
			@PathVariable("id") long id) throws IOException {		
    	usuario.setId(id);
    	return usuarioPost(usuario, result, true);        
    }

	private ModelAndView usuarioPost(Usuario usuario, BindingResult result, boolean isCadastroUsuarioPage) {
		
		modelViewCadastro.addObject("result", null);
    	modelViewCadastro.addObject("errorMessage", null);
    	
//		if (this.isDisableCamposChaves) {
//			usuario.setFuncaoId(usuarioEditado.getFuncaoId());
//		}
		
    	usuario.setUsuarioFuncaoAdicionais(usuarioEditado.getUsuarioFuncaoAdicionais());
		modelViewCadastro.addObject("usuario", usuario);
		modelViewCadastro.addObject("isCadastroUsuarioPage", isCadastroUsuarioPage);	
    	modelViewCadastro.addObject("projetosCadastrado", projetosCadastrados);	    	
    	modelViewCadastro.addObject("folgasSemanaisPlanejadas", folgasSemanaisPlanejadas);
    	
		boolean hasErrors = result.hasErrors();
    	modelViewCadastro.addObject("result", result);
    	if(hasErrors) {	    		
        	modelViewCadastro.addObject("camposComErro", Utilities.getAllErrosBindingResult(result));
            return modelViewCadastro;
        }   
		
        String message = usuarioService.saveUsuario(usuario, session, isCadastroUsuarioPage);
    	System.out.println(message);
    	modelViewCadastro.addObject("errorMessage", null);
        if (Strings.isNotBlank(message)) {
        	modelViewCadastro.addObject("errorMessage", message);
        	return modelViewCadastro;
        }

    	modelViewCadastro.addObject("usuario", usuario);
    	this.usuarioTurnosDisponiveisService.saveFolgasSemanaisPlanejadasUsuario(folgasSemanaisPlanejadas, usuario.getId());
    	modelViewCadastro.addObject("folgasSemanaisPlanejadas", folgasSemanaisPlanejadas);
		
    	if (isCadastroUsuarioPage) {
    		return new ModelAndView( "redirect:/usuario/" + usuario.getId() + "/editar");
    	}
    	else {
    		return new ModelAndView( "redirect:/usuario/meucadastro");
    	}
	}
	
    @ResponseBody
    @PostMapping(value = "/usuario/funcaoAdicional", produces = MediaType.APPLICATION_JSON_VALUE)
	public String funcaoAdicionalSave(@RequestBody int id) {
    	
    	if (this.usuarioEditado.getFuncao().getId() == id) {
			return "Função já cadastrada para este usuário";
    	}
    	
    	List<UsuarioFuncaoAdicional > funcoes = this.usuarioEditado.getUsuarioFuncaoAdicionais();
    	if (funcoes != null && 
			funcoes.size() > 0 &&
			funcoes.stream().anyMatch(x->x.getFuncao().getId() == id)) {
    			return "Função já cadastrada para este usuário";
		}
		else {
			UsuarioFuncaoAdicional funcao = new UsuarioFuncaoAdicional();
			funcao.setId(0);
			Funcao f = new Funcao();
			f.setId(id);
			funcao.setFuncao(f);
			funcao.setUsuario(usuarioEditado);
			funcoes.add(funcao);
    	}
    	
    	return "";   	 
	}
	
    @ResponseBody
    @DeleteMapping(value = "/usuario/funcaoAdicional", produces = MediaType.APPLICATION_JSON_VALUE)
	public String funcaoAdicionalDelete(@RequestBody int id) {
    	String message = "";
    	List<UsuarioFuncaoAdicional> funcoes = this.usuarioEditado.getUsuarioFuncaoAdicionais();
    	if (funcoes != null && 
			funcoes.size() > 0) {
    		UsuarioFuncaoAdicional funcao = funcoes.stream().filter(x->x.getFuncao().getId() == id).findFirst().get();
    		if (funcao != null) {
    			funcoes.remove(funcao);
    		}
    		else {
    			message = "Função não cadastrada para este usuário";
    		}
    	}
		else {
			message = "Função não cadastrada para este usuário";
		}
    	
    	return message;   	 
	}
	
    @ResponseBody
    @PostMapping(value = "/usuario/folgasSemanalPlanejada")
	public int folgaSemanalPlanejadaSave(@RequestBody FolgaSemanalPlanejadaUsuario folgaSemanalPlanejada) {			
    	return this.usuarioTurnosDisponiveisService.preSaveFolgaSemanalPlanejadaUsuario(folgasSemanaisPlanejadas, folgaSemanalPlanejada);   	 
	}
	
    @ResponseBody
    @DeleteMapping(value = "/usuario/folgaSemanalPlanejada")
	public int folgaSemanalPlanejadaDelete(@RequestBody int id) {
    	return this.usuarioTurnosDisponiveisService.preDeleteFolgaSemanalPlanejadaUsuario(folgasSemanaisPlanejadas, id);   
	}
    
    @ResponseBody
    @RequestMapping(value = "/usuario/aceiteProjeto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String aceiteProjeto(
			@PathVariable("id") long id,
			@RequestParam("statusAceite") int statusAceite,
			@RequestParam("motivo") String motivo,
			HttpServletRequest request) {		
		this.usuarioLogado = ((Usuario)request.getSession().getAttribute("usuarioLogado"));
		return prestadorService.aceitePrestador(this.usuarioLogado, id, statusAceite, motivo);
    }

	@RequestMapping("/usuario/delete/{id}")
	public ModelAndView deleteProjeto(@PathVariable("id") long id) {			

		ModelAndView modelView = new ModelAndView("redirect:/usuarios/");
		System.out.println("projeto delete");
		System.out.println(id);
    	
        String message = this.usuarioService.delete(id);
		modelViewCadastro.addObject("errorMessage", null);
        if (Strings.isNotBlank(message)) {
        	modelView.addObject("errorMessage", message);
        }
        
		return modelView;
    }
    
    @ModelAttribute("bancos")
    public List<Banco> bancos() {
       return this.referenciasService.getBancos();
    }
    
    @ModelAttribute("funcoes")
    public List<Funcao> funcoes() {
       return this.funcaoService.findAll();
    }
    
    @ModelAttribute("paises")
    public List<Pais> paises() {
       return this.referenciasService.getPaises();
    }
    
    @ModelAttribute("periodos")
    public List<DadoGenerico> periodos() {
       return this.referenciasService.getPeriodos();
    }
    
    @ModelAttribute("diasSemana")
    public List<DiaSemana> diasSemana() {
       return this.referenciasService.getDiasSemana();
    }
    
    @ModelAttribute("motivos")
    public List<MotivoAusencia> motivosAusencia() {
       return this.referenciasService.getMotivosAusencia(1);
    }
    
    @ModelAttribute("centroCustos")
    public List<CentroCusto> centroCustos() {
       return this.centroCustoService.findAll();
    }
}
