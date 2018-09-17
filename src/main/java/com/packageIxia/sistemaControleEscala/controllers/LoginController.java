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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.packageIxia.sistemaControleEscala.models.LoginPage;
import com.packageIxia.sistemaControleEscala.services.UsuarioAcessoService;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

	@Autowired
	private UsuarioAcessoService usuarioAcessoService;
	
	ModelAndView mv = new ModelAndView("loginView");
	
    @GetMapping
	public ModelAndView login()  {
		System.out.println("login inicializado");
		mv.addObject("login", new LoginPage()); 
		return mv;
	}

    @PostMapping
    public ModelAndView submit(
    		@Valid @ModelAttribute("login")LoginPage login,
    		BindingResult result,
    		HttpSession session, 
    		HttpServletRequest request,
    		HttpServletResponse response) throws IOException {

        mv.addObject("login", login); 
		System.out.println("submit cadastro");  

    	mv.addObject("errorMessage", null);
		boolean hasErrors = result.hasErrors();
		System.out.println(hasErrors);  
    	mv.addObject("result", result);
    	if(hasErrors) {
            return mv;
        }    	

        String message = usuarioAcessoService.efetuarLoginUsuario(login, session);        
        if (message.isEmpty()) {
    		response.sendRedirect("index");
        }
        else {
        	mv.addObject("errorMessage", message);
        }
        
        return mv;
    }
}
