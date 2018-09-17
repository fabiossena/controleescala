<!DOCTYPE html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page session="true"%>
<head>
    <title>Ixia - Sistema de Escala - Cadastro inicial</title>
    <jsp:include page="shared/headerPartialView.jsp"/>
</head>
<body onload="$('#email').focus()">
    <jsp:include page="shared/navbarPartialView.jsp"/>
    <div class="container">
        <div style="margin-top:50px" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title" style="font-weight: bold;">Cadastro de usuário</h3>
                    <div style="float:right; font-size: 85%; position: relative"><a id="signinlink" href="<c:url value='/login' />">Efetuar login</a></div>
                </div>
                <div class="panel-body" >
                    <form:form modelAttribute="cadastro" class="form-horizontal" method='POST'>
                    
                        <jsp:include page="shared/errorPartialView.jsp"/>
                        
                        <div class="form-group">
                            <label for="email" class="col-md-3 control-label">E-mail</label>
                            <div class="col-md-9">
                                <form:input path="email" class='form-control ${result.hasFieldErrors("email") ? "is-invalid" : ""}' name="email" placeholder="E-mail" maxlength="100" />
                                <div class="invalid-feedback">
                                    <form:errors path='email' />
                                </div>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="icode" class="col-md-12 control-label">Matricula Ixia</label>
                            <div class="col-md-9">
                                <form:input path="matricula" type="text" class='form-control ${result.hasFieldErrors("matricula") ? "is-invalid" : ""}' name="matricula" placeholder="Matricula Ixia" maxlength="50" />
                                <div class="invalid-feedback">
                                    <form:errors path='matricula' />
                                </div>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="firstname" class="col-md-12 control-label">Primeiro nome</label>
                            <div class="col-md-9">
                                <form:input path="primeiroNome" type="text" class='form-control ${result.hasFieldErrors("primeiroNome") ? "is-invalid" : ""}' name="primeiroNome" placeholder="Primeiro nome" maxlength="100" />
                                <div class="invalid-feedback">
                                    <form:errors path='primeiroNome' />
                                </div>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="lastname" class="col-md-12 control-label">Sobrenome</label>
                            <div class="col-md-9">
                                <form:input path="sobrenome" type="text" class='form-control ${result.hasFieldErrors("sobrenome") ? "is-invalid" : ""}' name="sobreNome" placeholder="Sobrenome" maxlength="150" />
                                <div class="invalid-feedback">
                                    <form:errors path='sobrenome' />
                                </div>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="password" class="col-md-12 control-label">Senha</label>
                            <div class="col-md-9">
                                <form:input path="senha" type="password" class='form-control ${result.hasFieldErrors("senha") ? "is-invalid" : ""}' name="senha" placeholder="Senha" maxlength="50" />
                                <div class="invalid-feedback">
                                    <form:errors path='senha' />
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="col-md-12 control-label">Repetir senha</label>
                            <div class="col-md-9">
                                <form:input path="repetirSenha" type="password" class='form-control ${result.hasFieldErrors("repetirSenha") ? "is-invalid" : ""}' name="repetirSenha" placeholder="Repetir senha" maxlength="50" />
                                <div class="invalid-feedback">
                                    <form:errors path='repetirSenha' />
                                </div>
                            </div>
                        </div>
                        
                        <div class="form-group">                                 
                            <div class="col-md-offset-3 col-md-9">
                                <input id="btn-login" type="submit" class="btn btn-success" value="Efetuar cadastro" />
                                <span style="margin-left:8px; display: none;">or</span>  
                            </div>
                        </div>
                        
                        <div style="border-top: 1px solid #999; padding-top:20px"  class="form-group">
                            <div class="col-md-offset-3 col-md-9" style="display: none;">
                                <button id="btn-fbsignup" type="button" class="btn btn-primary"><i class="icon-google"></i>   Conectar com conta google</button>
                            </div>
                        </div>
                        
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>