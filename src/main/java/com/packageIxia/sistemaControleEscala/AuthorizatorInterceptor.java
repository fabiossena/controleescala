package com.packageIxia.sistemaControleEscala;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthorizatorInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, 
        HttpServletResponse response,
        Object controller) throws Exception {
		
		System.out.println("handle");
		System.out.println(request.getRequestURI());
		System.out.println(request.getContextPath());
	    
		String uri = request.getRequestURI();
		System.out.println(uri);
	    
		if(uri.endsWith("sair")) {
			request.getSession()
            .setAttribute("usuarioLogado", null);
	        response.sendRedirect(request.getContextPath() + "/login");
            return false;
		}
		
        if(uri.endsWith("login") ||
           uri.endsWith("cadastroinicial") ||
           uri.endsWith("esqueceuSenha") ||
           uri.endsWith("error")) {
        	
        	// grava se é ou não a pagina de login
    		request.setAttribute("isLoginPage", uri.endsWith("login"));
        	
    		// evita usuarios logados acessarem a tela de login redirecionando para o diretório base
            if((uri.endsWith("login") ||
                uri.endsWith("cadastroinicial")) &&
                request.getSession().getAttribute("usuarioLogado") != null) {
                response.sendRedirect(request.getContextPath());
            }
            
            return true;
        }
        

		if(uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".map")) {
            return true;
		}


    	// se tenta ir para o index redirecionado para o diretório base
        if(uri.endsWith("index")) {
            response.sendRedirect(request.getContextPath());
            return false;
        }

        // se tem dados de usuario logado permite acesso
        if(request.getSession().getAttribute("usuarioLogado") != null) {
            return true;
        }


        // caso nenhum dos criterios acima sejam satisfeitos manda para a pagina de login
        response.sendRedirect(request.getContextPath() + "/login");
        
        return false;

    }

}
