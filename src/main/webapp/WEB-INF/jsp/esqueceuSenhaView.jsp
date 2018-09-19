<!DOCTYPE html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page session="true"%>
<head>
    <title>Ixia - Sistema de Escala - Esqueceu senha</title>
    <jsp:include page="shared/headerPartialView.jsp"/>
</head>
<body onload="$('#matricula').focus()">
    
    <span>
        <jsp:include page="shared/navbarPartialView.jsp"/>
    </span>
    
    <div class="container">
        <div style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
            <div class="panel panel-info" >
            
                <div class="panel-heading">
                    <h3 class="panel-title" style="font-weight: bold;">Recuperar senha</h3>
                    <div style="float:right; font-size: 80%; position: relative"><a href="<c:url value='/login' />">Efetuar login</a></div>
                </div>
                
                <div style="padding-top:30px" class="panel-body" >
                    <form:form id="form-enviar" autocomplete="off" modelAttribute="esqueceuSenha" class="form-horizontal" method='POST'>
                    
                        <jsp:include page="shared/errorPartialView.jsp"/>
                        
                        <div style="margin-bottom: 25px" class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <form:input path="matricula" class='form-control ${result.hasFieldErrors("matricula") ? "is-invalid" : ""}' placeholder="matrícula ixia" maxlength="50" />
                            <div class="invalid-feedback">
                                <form:errors path='matricula' />
                            </div>
                        </div>
                        
                        <div style="margin-bottom: 25px" class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <form:input path="email" class='form-control ${result.hasFieldErrors("email") ? "is-invalid" : ""}' placeholder="e-mail" maxlength="50" />
                            <div class="invalid-feedback">
                                <form:errors path='email' />
                            </div>
                        </div>
                        
                        <div style="margin-top:10px" class="form-group">
                            <div class="col-sm-12 controls">
                                <input id="btn-enviar" type="submit" class="btn btn-success" value="Enviar" />
                            </div>
                        </div>
                        
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>