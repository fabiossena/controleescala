<!DOCTYPE html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@page session="true"%>

<head>
	<title>Ixia - Sistema de Escala - Cadastro usuários</title>
    <jsp:include page="../shared/headerPartialView.jsp"/>
	<script src="<c:url value='/js/usuario-principal.js' />"></script>
</head>

<body>

	<jsp:include page="../shared/navbarPartialView.jsp"/>

    <div class="container">    
	        <div style="margin-top:50px" class="mainbox col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
        	  
				<h3>Usuários cadastrados</h3>
				<div class="container border-top panel-custom"> 
					<br />
					<div class="table-container table-responsive">
						<table id="tabelaUsuarios" class="display tabela-avancada">
					      
					       <!-- Header Table -->
					     <thead>
					            <tr>
					                <th>Matrícula</th>
									<th>Nome</th>
					                <th>E-mail</th>
									<th>Função</th>
					                <th>Status</th>
					                <th>Ação</th>
					            </tr>
					        </thead>
					        <tbody>        
						        <c:forEach items="${usuariosCadastrados}" var="usuario">
						            <tr>
						                <td>${usuario.matricula}</td>
										<td>${usuario.nomeCompleto}</td>
						                <td>${usuario.email}</td>
						                <td>${usuario.funcao.nome}</td>
						                <td>
						                	<c:if test="${usuario.ativo}">Ativo</c:if>
						                	<c:if test="${!usuario.ativo}">Desativado</c:if>
					                	</td>
		                				<td>
		                					<a class="btn btn-sm btn-primary" href="<c:url value='/usuario' />/${usuario.id}" style="margin: 1px" >ver</a>
											<input class="btn btn-sm btn-danger" id="btn-delete-usuario" type="button" onclick="deleteUsuario(${usuario.id})" value="apagar" style="margin: 1px" >
										</td>
						            </tr>
						        </c:forEach>
					        </tbody>
					    </table>
					</div>
				</div>

                
         </div> 
    </div>
    
</body>

</html>