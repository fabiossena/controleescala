<!DOCTYPE html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@page session="true"%>

<head>
	<title>Ixia - Sistema de Escala - Cadastro usuário</title>
    <jsp:include page="../shared/headerPartialView.jsp"/>

    <script>    
		var globalUsuarioEnderecoCidadeId = ${usuario.cidadeId};
    </script>
    
	<script src="<c:url value='/js/usuario-endereco-cidade.js' />"></script>
	<script src="<c:url value='/js/usuario-turno-folga.js' />"></script>
	<script src="<c:url value='/js/usuario-projeto.js' />"></script>
	<script src="<c:url value='/js/usuario-principal.js' />"></script>
</head>
<body onload="$('#primeiroNome').focus()">

	<jsp:include page="../shared/navbarPartialView.jsp"/>

    <div class="container">    
	        <div style="margin-top:50px" class="mainbox col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
        	  <form:form modelAttribute="usuario" class="form-horizontal" method="POST">
		        	  
		          <div class="panel panel-info">
		              <div class="panel-heading row">
		                	
		                  <h3 class="panel-title col-7 col-sm-7 col-md-7 col-lg-7 col-xl-7">
			                  <c:if test="${isCadastroUsuarioPage}">Cadastro usuário</c:if>
			                  <c:if test="${!isCadastroUsuarioPage}">Meu cadastro</c:if>     
		                  </h3>     			
						  <form:hidden path="id"/>
			        	  <div class="col-3 col-sm-3 col-md-3 col-lg-3 col-xl-3">
							<c:if test="${isCadastroUsuarioPage}">
			        	  		<a id="btn-voltar" class="btn-default" href="<c:url value='/usuarios' />" style="margin: 1px">Voltar</a>
			        	  	</c:if>
			        	  	
			        	  	<c:if test="${isDisableTodosCampos}">
				        	  	<c:if test="${isAdministracao}">
					        	  	<a class="btn btn-sm btn-primary" style="margin: 1px" href="<c:url value='/usuario' />/${usuario.id}/editar">Editar</a>
				        	  	</c:if>
			        	  	</c:if>
			        	  	<c:if test="${!isDisableTodosCampos}">
			        	  		<input id="btn-salvar" type="submit" class="btn btn-success" value="Salvar" style="margin: 1px"  />
			        	  	</c:if>
		        	  	</div>
		              </div>  
		              <div class="panel-body" >
		              
		        		<jsp:include page="../shared/errorPartialView.jsp"/>	
	        	  	                      
            			<div class="row align-items-start" style="margin-top: 30px">	  
							<div class="tab-content col-8 col-sm-8 col-md-9 col-lg-9 col-xl-9 border" style="padding-top:20px; min-height: 450px;" id="pills-tabContent">
							  <div class="tab-pane fade" id="pills-dados" role="tabpanel" aria-labelledby="pills-dados-tab">
   								<jsp:include page="usuarioDadosPrincipaisTabView.jsp"/>	
							  </div>
							  <div class="tab-pane fade" id="pills-endereco" role="tabpanel" aria-labelledby="pills-endereco-tab">
   								<jsp:include page="usuarioEnderecoTabView.jsp"/>								  
							  </div>
							  <div class="tab-pane fade" id="pills-turnos" role="tabpanel" aria-labelledby="pills-turnos-tab">
   								<jsp:include page="usuarioTurnosTabView.jsp"/>			
							  </div>
							  <div class="tab-pane fade" id="pills-projetos" role="tabpanel" aria-labelledby="pills-projetos-tab">
   								<jsp:include page="usuarioProjetosTabView.jsp"/>			
							  </div>
							  <div class="tab-pane fade" id="pills-bancarios" role="tabpanel" aria-labelledby="pills-bancarios-tab">
   								<jsp:include page="usuarioDadosBancariosTabView.jsp"/>								  
							  </div>
							  <div class="tab-pane fade" id="pills-administrativos" role="tabpanel" aria-labelledby="pills-administrativos-tab">
   								<jsp:include page="usuarioDadosAdministrativosTabView.jsp"/>										  
							  </div>
							  <div class="tab-pane fade" id="pills-acesso" role="tabpanel" aria-labelledby="pills-acesso-tab">
								<jsp:include page="usuarioDadosAcessoTabView.jsp"/>						  
							  </div>
							</div>
            			
            			  <div class="nav nav-pills nav-stacked col-4 col-sm-4 col-md-2 col-lg-2 col-xl-2" id="pills-tab" role="tablist" aria-orientation="vertical">
							  <a class="nav-link" id="pills-dados-tab" data-toggle="pill" href="#pills-dados" role="tab" aria-controls="pills-dados" aria-selected="true">Dados principais</a>
							  <a class="nav-link" id="pills-endereco-tab" data-toggle="pill" href="#pills-endereco" role="tab" aria-controls="pills-endereco" aria-selected="false">Endereço</a>
							  <a class="nav-link" id="pills-turnos-tab" data-toggle="pill" href="#pills-turnos" role="tab" aria-controls="pills-turnos aria-selected="false">Turnos disponíveis</a>
							  <a class="nav-link" id="pills-projetos-tab" data-toggle="pill" href="#pills-projetos" role="tab" aria-controls="pills-projetos aria-selected="false">Projetos cadastrados</a>
							  <a class="nav-link" id="pills-bancarios-tab" data-toggle="pill" href="#pills-bancarios" role="tab" aria-controls="pills-bancarios aria-selected="false">Dados bancários</a>
							  <a class="nav-link" id="pills-administrativos-tab" data-toggle="pill" href="#pills-administrativos" role="tab" aria-controls="pills-administrativos aria-selected="false">Dados administrativos</a>
							  <a class="nav-link" id="pills-acesso-tab" data-toggle="pill" href="#pills-acesso" role="tab" aria-controls="pills-acesso aria-selected="false">Dados de acesso</a>
							</div>
            				                      
						</div>
		               </div>
		          </div>
                      
           	</form:form>

                
         </div> 
    </div>
    
</body>

</html>