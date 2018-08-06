package com.packageIxia.SistemaControleEscala.Controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.packageIxia.SistemaControleEscala.Models.UsuarioEmailPrimeiroAcesso;
import com.packageIxia.SistemaControleEscala.Models.Referencias.Funcao;
import com.packageIxia.SistemaControleEscala.Services.ReferenciasService;
import com.packageIxia.SistemaControleEscala.Services.Usuario.UsuarioConviteService;

@Controller
@RequestMapping(value = "/convite")
public class UsuarioConviteController {

	@Autowired
    private UsuarioConviteService usuarioConviteService;
	@Autowired
	private ReferenciasService referenciasService;
	
	ModelAndView mv = new ModelAndView("usuarioConviteView");
	
    @GetMapping
	public ModelAndView getUsuarioConvite() {
		mv.addObject("usuarioConvite", new UsuarioEmailPrimeiroAcesso()); 
		mv.addObject("usuariosConvites", this.usuarioConviteService.findAll()); 
		return mv;
	}

    @PostMapping
    public ModelAndView submit(
    		@ModelAttribute("usuarioConvite")UsuarioEmailPrimeiroAcesso usuarioConvite,
    		BindingResult result, 
    		HttpSession session, 
    		HttpServletRequest request,
    		HttpServletResponse response) throws IOException {		

		System.out.println("submit convite");
		System.out.println(usuarioConvite.getId());
    	mv.addObject("usuarioConvite", usuarioConvite);

        String message = this.usuarioConviteService.save(usuarioConvite);
    	mv.addObject("errorMessage", null);
        if (!message.isEmpty()) {
        	mv.addObject("errorMessage", message);
            return mv;
        }

        return new ModelAndView("redirect:/convite");
    }
    
    @ModelAttribute("funcoes")
    public List<Funcao> funcoes() {
       return this.referenciasService.getFuncoes();
    }
}
