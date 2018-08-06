<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page session="true"%>

<head>
	<title>Ixia - Sistema de Escala - Pagina inicial</title>
    <jsp:include page="shared/headerPartialView.jsp"/>
	<script src="<c:url value='/js/index-projeto.js' />"></script>
</head>
<body>

	<jsp:include page="shared/navbarPartialView.jsp"/>

    <div class="container">    
	    <div style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">  
          <div class="panel panel-info">
              <div class="panel-heading">
                  <h4 class="panel-title" style="font-weight: bold;">Bem vindo, ${usuarioLogado.primeiroNome} (${usuarioLogado.funcao.nome.toLowerCase()})</h4>
              </div> 
		 </div> 
		</div>
		
		<div class="table-container row table-responsive col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12"  style="margin: 0 0 30px 0">    
	        
	        <c:forEach items="${projetosCadastrados}" var="item"> 
	        
	        	<div id="card${item.id}" class="card" style="width: 18rem; margin: 12px; float: left;">
				  <div class="card-body">
				    <h5 class="card-title">${item.projeto.descricaoProjeto} - ${item.projetoEscala.descricaoEscala}</h5>
				    
				    <h6 class="card-subtitle mb-2 text-muted">
	                	<fmt:parseDate pattern="yyyy-MM-dd" value="${item.dataInicio} " var="dataInicio" />
	                	<fmt:formatDate value="${dataInicio}" pattern="dd/MM/yyyy" />
	                	
	                	<fmt:parseDate pattern="yyyy-MM-dd" value="${item.dataFim}" var="dataFim" />
	                	<fmt:formatDate value="${dataFim}" pattern="dd/MM/yyyy" />
			    	</h6>
			    	
				    <h6 class="card-subtitle mb-2 text-muted">Horário: ${item.projetoEscala.horaInicio} às ${item.projetoEscala.horaFim}</h6>
				    <h6 class="card-subtitle mb-2 text-muted">Função: ${item.funcao.nome}</h6>
				    
				    <p class="card-text" style="font-size: 10pt" id="observacao-prestador${item.id}">${item.observacaoPrestador}</p>
				                    	
		                <input  type="button" 
				                id="bt-aceita-prestador${item.id}"
		                		onclick="aceitarPrestador(${item.id})" 
		                		class="btn btn-sm btn-success"
		                		style="margin-bottom: 10px"
		                		value="clique aqui para aceitar" />				                
	               		
		                <input  type="button"
		                		id="bt-recusa-prestador${item.id}"
		                		onclick="recusarPrestador(${item.id})" 
		                		class="btn btn-sm btn-danger" 
		                		value="clique aqui para recusar" />
		                	
	              			<div class="form-group" 
	              				 id="panel-motivo-recusa${item.id}"
	              				 style="font-size: 10pt; margin-top: 15px; display: none">
	              				 <label for="motivo-recusa${item.id}">Motivo:</label>
	                			<textarea 
		                			class="form-control" 
		                			rows="3" 
		                			maxlength="500"
		                			id="motivo-recusa${item.id}">${item.motivoRecusa}</textarea>
              		 			<div id="motivo-recusa-feedback${item.id}" class="invalid-feedback"></div>	
						</div>	               
	             	
				  </div>
				</div>
	        </c:forEach>
	        
	        
	        
		</div>
	</div>	
</body>

</html>
