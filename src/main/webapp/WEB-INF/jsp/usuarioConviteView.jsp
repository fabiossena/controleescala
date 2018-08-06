<!DOCTYPE html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@page session="true"%>

<head>
	<title>Ixia - Sistema de Escala - Convite usuário</title>
    <jsp:include page="shared/headerPartialView.jsp"/>
	<script src="<c:url value='/js/usuario-convite.js' />"></script>
</head>
<body onload="$('#matricula').focus()">

   <jsp:include page="shared/navbarPartialView.jsp"/>

      <div class="container">    
     	<div style="margin-top:50px" class="mainbox col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
	      	  
			<h3>Convite usuários</h3>
			<div class="container border-top panel-custom"> 
     
    	  		<form:form modelAttribute="usuarioConvite" class="form-horizontal" method='POST'>
       	  				
	       			<jsp:include page="shared/errorPartialView.jsp"/>	
	       			
	        		<div class="container row">
					       
				       <div class="form-group col-12 col-sm-6 col-md-6 col-lg-6 col-xl-2">
				       	<label for="matricula" class="control-label">Matrícula</label>
				       	<div>
				           <form:hidden path="id"/>
				           	<form:input path="matricula" class='form-control ${result.hasFieldErrors("matricula") ? "is-invalid" : ""}' maxlength="50" />
				           	<div class="invalid-feedback" id="feedback-matricula"></div>
				           </div>
				       </div>				       
				       
				       <div class="form-group col-12 col-sm-6 col-md-6 col-lg-6 col-xl-2">
				       	<label for="email" class="control-label">E-mail</label>
				       	<div>
				           	<form:input path="email" class='form-control ${result.hasFieldErrors("email") ? "is-invalid" : ""}' maxlength="100" />
				           	<div class="invalid-feedback" id="feedback-email"></div>
				           </div>
				       </div>
				       
				       <div class="form-group col-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
				       	<label for="funcaoId" class="control-label">Função</label>
				       	<div>
				           <form:select path="funcaoId" class='form-control ${result.hasFieldErrors("funcaoId") ? "is-invalid" : ""}'  items="${funcoes}" itemLabel="nome" itemValue="id"></form:select>
				           	<div class="invalid-feedback" id="feedback-funcaoId"></div>
				           </div>
				       </div>
				       
				       <div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
        	  				<input id="btn-salvar" type="submit" class="btn btn-sm btn-success" value="Enviar" />
							<input id="btn-LimparItem" type="button" class="btn btn-sm btn-success" value="Limpar" style="margin-left: 10px" onclick="limpar()" />
						</div>
				     </div>
			
              		</form:form>
				<br />
			 	
				<div class="table-container table-responsive"  style="margin: 20px 0 30px 0">
					<table  id="tabelaUsuarioEmailPrimeiroAcesso" class="display tabela-avancada">
				       <!-- Header Table -->
				       <thead>
				            <tr>
								<th>Matrícula</th>
				                <th>E-mail</th>
								<th>Função</th>
				                <th>Status</th>
				            </tr>
				        </thead>
				        <!-- Footer Table -->
				        <tfoot>
				        <tbody>        
					        <c:forEach items="${usuariosConvites}" var="item">
					            <tr id="usuarioAcesso${item.id}">
					                <td id="matricula${item.id}" >${item.matricula}</td>
					                <td id="email${item.id}">${item.email}</td>
									<td id="funcao${item.id}">${item.funcao.nome}</td>
					                <td><c:if test="${item.aceito}">Aceito</c:if><c:if test="${!item.aceito}">Pendente</c:if></td>
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