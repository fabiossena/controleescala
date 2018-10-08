<!DOCTYPE html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@page session="true"%>

<head>
	<title>Ixia - Sistema de Escala - Cadastro projetos</title>
    <jsp:include page="../shared/headerPartialView.jsp"/>
</head>

<body>

	<jsp:include page="../shared/navbarPartialView.jsp"/>
	<script src="<c:url value='/js/projeto/projeto-principal.js' />"></script>

    <div class="container">    
	        <div style="margin-top:50px" class="mainbox col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
        	  
                <div class="container panel-heading row">
				  <h3 class="panel-title">Projetos cadastrados</h3>
	        	  <div style="margin-left: 20px">
	        	   <c:if test="${isAdministracao || isMonitoria}"> 
       	  			<a id="btn-novo" class="btn btn-sm btn-primary float-right" href="<c:url value='/projeto' />">Novo</a>
        	  		</c:if>
	        	  </div>
				</div>
				
				<div class="container border-top panel-custom">					
					
				<br>
				
					<div class="table-container table-responsive">
						<table id="tabelaProjetos" class="display tabela-avancada">

					        <!-- Header Table -->
					        <thead>
					            <tr>
					                <th>id</th>
									<th>Nome</th>
					                <th>Gerente responsável</th> 
					                <th>Status</th>
					                <th>Ação</th>
					            </tr>
					        </thead>
					        <tbody>        
						        <c:forEach items="${projetosCadastrados}" var="projeto">
						            <tr>
						                <td>${projeto.id}</td>
										<td>${projeto.nome}</td>
						                <td>${projeto.gerente.nomeCompletoMatricula}</td>
						                <td>
						                	<c:if test="${projeto.ativo}">Ativo</c:if>
						                	<c:if test="${!projeto.ativo}">Desativado</c:if>
					                	</td>
		                				<td>
		                					<a class="btn btn-sm btn-primary" href="<c:url value='/projeto' />/${projeto.id}" style="margin: 1px">ver</a>
		                					<a class="btn btn-sm btn-primary" href="<c:url value='/dashboard' />/${projeto.id}" style="margin: 1px">dashboard</a>
											<input class="btn btn-sm btn-danger" id="btn-delete-projeto" type="button" onclick="deleteProjeto(${projeto.id})" value="apagar" style="margin: 1px"> 
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