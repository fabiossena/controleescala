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
        	
        	$("#opcoes").click(function () {
        		$("#funcao-id").val(0);
        		
        		$('#area-funcao').toggle();
				if ($('#area-funcao').is(":visible")) {
					$("#opcoes").html("esconder opções");		
        		}
				else{
					$("#opcoes").html("opções");					
				}
        	});
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
                    <div style="font-size: 10pt; float:right; position: relative">
                    <a class="block-click" href="<c:url value='/esqueceuSenha' />">Esqueceu a senha? Clique aqui!</a></div>                                           
                </div> 
                
                <div style="padding-top:30px" class="container border-top panel-body">
                
                    <form:form id="form-login" autocomplete="off" modelAttribute="login" class="form-horizontal" method='POST'>
                    
                        <jsp:include page="shared/errorPartialView.jsp"/>
                         
                        <div style="margin-bottom: 25px" class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <form:input path="matricula" class='form-control col-md-9 ${result.hasFieldErrors("matricula") ? "is-invalid" : ""}' placeholder="Matrícula Ixia" maxlength="50" />
                            <div class="invalid-feedback">
                                <form:errors path='matricula' />
                            </div>
                        </div>
                        
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <form:password path="senha" class='form-control col-md-9 ${result.hasFieldErrors("senha") ? "is-invalid" : ""}' placeholder="Senha" maxlength="50" />
                            <div class="invalid-feedback">
                                <form:errors path='senha' />
                            </div>
                       	</div>
                       	
               		    <div id="area-funcao" class="input-group" style="margin-top: 25px; display: none;">
				            <form:select 
				            	id="funcao-id" 
				            	path="funcao.id" 
				           		class="form-control col-md-9">
				           		<form:option value="0">Selecione uma função</form:option>
				           		<form:options 
					           		items="${funcoes}"  
					           		itemValue="id" 
					           		itemLabel="nome"/>
			           		</form:select>
		                </div>		
		                		
                        <div class="input-group" style="margin-bottom: 25px">
                        	<div class="col-md-9">
	                        <a class="float-right" id="opcoes" style="font-size: 10pt; float: right" href="#">opções</a>
	                        </div>
                       	</div>
						                		
                		
                        <div class="input-group" style="display: none;">
                            <div class="checkbox">
                                <label>
                                <input id="login-remember" class="block-click" type="checkbox" name="remember" value="1">Lembrar senha
                                </label>
                            </div>
                        </div>
                        
                        <div style="margin-top:10px" class="form-group">
                            <div class="col-sm-12 controls">
                                <input id="btn-login" type="submit" class="btn btn-success" value="Acessar" />
                                <a id="btn-fblogin" href="#" class="btn btn-primary"  style="display: none;">Login with Facebook</a>
                            </div>
                        </div>
                        
                         <div class="border-top" style="padding: 10px 0 0 5px; font-size:10pt" >
                             Recebeu o convite por e-mail!
                             <a href="<c:url value='/cadastroinicial' />" class="block-click">
                             Clique aqui para se cadastrar
                             </a>
                         </div>
                        
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>