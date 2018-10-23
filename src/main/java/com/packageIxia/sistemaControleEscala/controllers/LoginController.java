package com.packageIxia.sistemaControleEscala.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.packageIxia.sistemaControleEscala.interfaces.IUsuarioAcesso;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IFuncao;
import com.packageIxia.sistemaControleEscala.models.LoginPage;
import com.packageIxia.sistemaControleEscala.models.referencias.Funcao;

@Controller
public class LoginController {
	
	ModelAndView mv = new ModelAndView("loginView");
	
	private IUsuarioAcesso usuarioAcessoService;
	private IFuncao funcaoService;
	
	public LoginController(
			IUsuarioAcesso usuarioAcessoService,
			IFuncao funcaoService) {
		this.usuarioAcessoService = usuarioAcessoService;
		this.funcaoService = funcaoService;
	}
	
    @GetMapping(value = "/login")
	public ModelAndView login(
			@RequestParam(value="matriculacadastro", defaultValue="") String matricula)  {
		System.out.println("login inicializado");
		LoginPage login = new LoginPage();
		login.setMatricula(matricula);
		mv.addObject("login", login); 
    	mv.addObject("result", null);
    	mv.addObject("errorMessage", null);
		return mv;
	}

    @PostMapping(value = "/login")
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

        String message = usuarioAcessoService.efetuarLoginUsuario(login);
        
        if (message.isEmpty()) {
    		response.sendRedirect("index");
        }
        else {
        	mv.addObject("errorMessage", message);
        }
        
        return mv;
    }
    
    @ModelAttribute("funcoes")
    public List<Funcao> funcoes() {
       return this.funcaoService.findAll();
    }
}
