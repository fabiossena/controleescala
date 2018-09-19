package com.packageIxia.sistemaControleEscala.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.packageIxia.sistemaControleEscala.models.CadastroInicialPage;
import com.packageIxia.sistemaControleEscala.services.UsuarioAcessoService;

@Controller
public class CadastroInicialController {

	@Autowired
	private UsuarioAcessoService usuarioAcessoService;
	
	ModelAndView mv = new ModelAndView("cadastroInicialView");
	
    @GetMapping("/cadastroinicial")
	public ModelAndView cadastro(
			@RequestParam(value="matricula", defaultValue="") String matricula,
			@RequestParam(value="email", defaultValue="") String email) {
    	CadastroInicialPage cadastro = new CadastroInicialPage();
    	cadastro.setMatricula(matricula);
    	cadastro.setEmail(email);
		mv.addObject("cadastro", cadastro); 
		return mv;
	}
    
    @PostMapping(value = "/cadastroinicial")
    public ModelAndView submit(
    		@Valid @ModelAttribute("cadastro")CadastroInicialPage cadastro,
    		BindingResult result, 
    		HttpSession session, 
    		HttpServletRequest request,
    		HttpServletResponse response) throws IOException {		

		System.out.println("submit cadastro");
    	mv.addObject("cadastro", cadastro);
    	mv.addObject("errorMessage", "");

		boolean hasErrors = result.hasErrors();
		System.out.println(hasErrors);  
    	mv.addObject("result", result);
    	if(hasErrors) {
            return mv;
        }   
    	
        String message = this.usuarioAcessoService.insertUsuarioCadastroInicial(cadastro, session);
    	mv.addObject("errorMessage", null);
        if (message.isEmpty()) {
    		response.sendRedirect("index");
        }
        else {
        	mv.addObject("errorMessage", message);
        }

        return mv;
    }
}
