<!DOCTYPE html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@page session="true"%>

<head>
	<title>Ixia - Sistema de Escala - Login</title>
    <jsp:include page="shared/headerPartialView.jsp"/>
    
	<script type="text/javascript">	
		$(document).ready(function() {
			$('#no-js').hide();
			$('.yes-js').show();
		});
	</script>
</head>

<body onload="$('#no-js').hide();$('.yes-js').show();$('#matricula').focus()">
 
	
	<div id="no-js" style="position: absolute; top: 0;">
		Javascript não habilitado, por favor atualize sua tela ou verifique suas configurações.	
	</div> 
	
	<span class="yes-js" style="display: none;">
		<jsp:include page="shared/navbarPartialView.jsp"/>
	</span>

    <div class="container yes-js" style="display: none;	">    
   	 	    <div style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">                    
       	<div class="panel panel-info" >
            <div class="panel-heading">
                <h3 class="panel-title" style="font-weight: bold;">Efetuar login</h3>
                <div style="float:right; font-size: 80%; position: relative"><a href="#">Esqueceu a senha? Clique aqui!</a></div>
            </div>     

            <div style="padding-top:30px" class="panel-body" >
                
		        <form:form id="form-login" autocomplete="off" modelAttribute="login" class="form-horizontal" method='POST'>
		        
		        	<jsp:include page="shared/errorPartialView.jsp"/>       
					                               
					<div style="margin-bottom: 25px" class="input-group">
					     <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
					     <form:input path="matricula" class='form-control ${result.hasFieldErrors("matricula") ? "is-invalid" : ""}' placeholder="Matricula Ixia" maxlength="50" />
		  				 <div class="invalid-feedback"><form:errors path='matricula' /></div>
                    	 
				     </div>
					        
					<div style="margin-bottom: 25px" class="input-group">
					    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
					    <form:password path="senha" class='form-control ${result.hasFieldErrors("senha") ? "is-invalid" : ""}' placeholder="Senha" maxlength="50" />
		  				 <div class="invalid-feedback"><form:errors path='senha' /></div>	
					</div>
			                         
		            <div class="input-group" style="display: none;">
		               <div class="checkbox">
		                 <label>
		                   <input id="login-remember" type="checkbox" name="remember" value="1">Lembrar senha
		                 </label>
		               </div>
		             </div>
			
			
                      <div style="margin-top:10px" class="form-group">
                          <!-- Button -->
                          <div class="col-sm-12 controls">
                            <input id="btn-login" type="submit" class="btn btn-success" value="Acessar" />
                            <a id="btn-fblogin" href="#" class="btn btn-primary"  style="display: none;">Login with Facebook</a>

                          </div>
                      </div>
	
	
                      <div class="form-group">
                          <div class="col-md-12 control">
                              <div style="border-top: 1px solid#888; padding-top:15px; font-size:85%" >
                                  Não tem conta ainda! 
                              <a href="<c:url value='/cadastroinicial' />">
                                  Clique aqui para se cadastrar
                              </a>
                              </div>
                          </div>
                      </div>    
                  </form:form>     
	
              </div>                     
          </div>  
	    </div>
	    
	
    </div>	  
</body>
</html>