package com.packageIxia.sistemaControleEscala.controllers.usuario;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IHoraExtrato;
import com.packageIxia.sistemaControleEscala.interfaces.usuario.IUsuario;
import com.packageIxia.sistemaControleEscala.models.referencias.PerfilAcessoEnum;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Controller
public class HoraExtratoController {

	private Usuario usuarioLogado;
	private IHoraExtrato horaExtratoService;
	private IUsuario usuarioService;	
	private HttpServletRequest request;
	private List<Usuario> usuarios;
	
	public HoraExtratoController(
			HttpServletRequest request,
			IHoraExtrato horaExtratoService,
			IUsuario usuarioService) {
		this.horaExtratoService = horaExtratoService;
		this.usuarioService = usuarioService;
		this.request = request;
	}
	
	@GetMapping("/extratoHoras")
	public ModelAndView extrato(
			@RequestParam(value = "ano", defaultValue = "0") int ano,
			@RequestParam(value = "ano", defaultValue = "0") int mes) {		
		return this.getExtrato(0, ano, mes);
	}
	
	@GetMapping("/extratoHoras/{id}")
	public ModelAndView extratoUsuario(
			@PathVariable(value = "id") long id) {		
		return this.getExtrato(id, 0, 0);
	}

	private ModelAndView getExtrato(long id, int ano, int mes) {
		ModelAndView mv = new ModelAndView("usuario/usuarioHoraExtratoView");
		this.usuarioLogado = ((Usuario)request.getSession().getAttribute("usuarioLogado"));

    	if (id > 0 &&
			!(this.usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.monitoramento.getId() 
			|| this.usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.gerencia.getId() 
			|| this.usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.administracao.getId() 
			|| this.usuarioLogado.getId() != id)) {
    		ModelAndView erroModelView = new ModelAndView("redirect/:error");
    		erroModelView.addObject("errorMessage", "Não permitido acesso a tela de usuários");
            return erroModelView;
    	}

		this.loadUsuarios();
		
		ano = ano == 0 ? Utilities.now().getYear() : ano;
		ano = mes == 0 ? Utilities.now().getMonthValue() : mes;

		mv.addObject("anos", new int[] {2018, 2019});
		mv.addObject("meses", new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
		mv.addObject("extratoHoras", this.horaExtratoService.findAllByUsuarioIdAndData(id == 0 ? this.usuarioLogado.getId() : id, ano, mes));
		if (id == 0) {
			mv.addObject("prestadorId", this.usuarioLogado.getId());
		}
		else {

			mv.addObject("prestadorId", id);
		}
		
		return mv;
	}
	
	private void loadUsuarios() {
		if (this.usuarios == null) {
			this.usuarioLogado = ((Usuario)request.getSession().getAttribute("usuarioLogado"));
			this.usuarios = this.usuarioService.findAllByUsuarioLogado(this.usuarioLogado);
		}
	}

    @ModelAttribute("prestadores")
    public List<Usuario> prestadores() {
		this.usuarioLogado = ((Usuario)request.getSession().getAttribute("usuarioLogado"));
    	List<Usuario> prestadores =  new ArrayList<Usuario>();
    	
    	if (usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.administracao.getId() ||
			usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.gerencia.getId() ||
			usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.monitoramento.getId()) {
    		List<Integer> ids = new ArrayList<Integer>();
    		ids.add(PerfilAcessoEnum.atendimento.getId());
    		ids.add(PerfilAcessoEnum.monitoramento.getId());
    		this.loadUsuarios();
    		prestadores =  this.usuarioService.findByPerfilAcessoId(this.usuarios, ids);
    	}
    	
    	return prestadores;
    }	
}
