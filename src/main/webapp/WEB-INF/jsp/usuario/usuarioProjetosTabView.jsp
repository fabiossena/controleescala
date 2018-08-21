
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h5>Projetos cadastrados</h5>
<div class="container border-top panel-custom"> 
	
		
		<div class="table-container row table-responsive col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12"  style="margin: 0 0 30px 0">    
	        <c:forEach items="${projetosCadastrados}" var="item"> 
	        
	        	<div class="card col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" style="margin: 12px; float: left;">
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
				    
	                <c:if test="${item.ativo}">
		                	
               			<div class="form-group" 
               				 id="panel-motivo-recusa${item.id}"
               				 style="font-size: 10pt; margin-top: 15px;
	                		 	<c:if test='${!((item.motivoRecusa != null && item.motivoRecusa != "") || item.aceito == 2)}'>
	                		 		 display: none
                		 		</c:if>">
  							<label for="motivo-recusa${item.id}">Motivo:</label>
  							<textarea 
	                			class="form-control" 
	                			rows="3" 
	                			maxlength="500"
	                			id="motivo-recusa${item.id}"	                		 			
              		 				<c:if test='${isDisableTodosCampos || item.aceito == 2}'>disabled</c:if>>${item.motivoRecusa}</textarea>
              		 			<div id="motivo-recusa-feedback${item.id}" class="invalid-feedback"></div>	
						</div>  
		                        	
		                <input  type="button"
				                id="bt-aceita-prestador${item.id}"
		                		onclick="aceitarPrestador(${item.id})" 
		                		class="btn btn-sm btn-success" 
		                		value="clique aqui para aceitar" 
		                		style='margin: 1px <c:if test="${!(item.aceito == 0 || item.aceito == 2) || item.aceito == 1}">; display: none</c:if>'	 
		                		<c:if test="${isDisableTodosCampos}">disabled</c:if> />
                			       
		                <input  type="button"
		                		id="bt-recusa-prestador${item.id}"
		                		onclick="recusarPrestador(${item.id})" 
		                		class="btn btn-sm btn-danger" 
		                		value="clique aqui para recusar"
		                		style='margin: 1px; <c:if test="${!(item.aceito == 0 || item.aceito == 1)}">display: none</c:if>'
		                		<c:if test="${isDisableTodosCampos}">disabled</c:if> />
               		</c:if>		
				  </div>
				</div>
	        </c:forEach>
		</div>
</div>