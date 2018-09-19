package com.packageIxia.sistemaControleEscala.controllers;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.packageIxia.sistemaControleEscala.models.CadastroInicialPage;
import com.packageIxia.sistemaControleEscala.services.UsuarioAcessoService;

@Controller
public class EsqueceuSenhaController {

	ModelAndView mv = new ModelAndView("esqueceuSenhaView");

	@Autowired
	private UsuarioAcessoService usuarioAcessoService;
	
    @GetMapping(value = "/esqueceuSenha")
	public ModelAndView esqueceuSenha()  {
		mv.addObject("esqueceuSenha", new CadastroInicialPage()); 
		return mv;
	}

    @PostMapping(value = "/esqueceuSenha")
    public ModelAndView submit(
    		@ModelAttribute("esqueceuSenha")CadastroInicialPage esqueceuSenha,
            RedirectAttributes redirectAttributes) throws IOException {

        mv.addObject("esqueceuSenha", esqueceuSenha); 

        String message = usuarioAcessoService.esqueceuSenha(esqueceuSenha);        
        if (message.isEmpty()) {
        	redirectAttributes.addFlashAttribute("message",
                    "Senha enviada para seu e-mail");
    		return new ModelAndView("redirect:/login");
        }
        else {
        	mv.addObject("errorMessage", message);
        }
        
        return mv;
    }

}
