
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@page session="true"%>

<form:form id="form-escala" modelAttribute="escala" method="POST" class="form-horizontal row col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">

	<h4 class="col-8 col-sm-8 col-md-8 col-lg-8 col-xl-8" style="margin-bottom: 17px">Escalas</h4> 
		    
	<div class="table-container  table-responsive row border-top panel-custom col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12"> 
	
		<div class="form-group col-12 col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">  
		  	<h5>Todas escalas</h5>
		</div>
		
		<table id="tabela-escala" class="display tabela-simples">
	       <!-- Header Table -->
	       <thead>
	            <tr>
	                <th>Descrição</th>
	                <th>Dia semana de</th>
	                <th>Dia semana até</th>
					<th>Hora início</th>
	                <th>Hora fim</th>
	                <th>Monitor</th>
	                <th>Qtde prestadores planejada</th>
	                <th>Ação</th>
	            </tr>
	        </thead>
	        <tbody>        
		        <c:forEach items="${escalas}" var="item">
		            <tr id="escala${item.id}" class="escala">
						<td class="rowclick" id="descricaoEscala${item.id}" >${item.descricaoEscala}</td>
		                <td class="rowclick" id="diaSemanaDeId${item.id}">${item.diaSemanaDe.nome}</td>
		                <td class="rowclick" id="diaSemanaAteId${item.id}">${item.diaSemanaAte.nome}</td>
		                <td class="rowclick" id="horaInicio${item.id}">${item.horaInicio}</td>
		                <td class="rowclick" id="horaFim${item.id}">${item.horaFim}</td>
		                <td class="rowclick" id="monitor${item.id}">${item.monitor.nomeCompletoMatricula}</td>
		                <td class="rowclick" id="quantidadePrestadoresPlanejada${item.id}" >${item.quantidadePrestadoresPlanejada}</td>
		                <td>
			                <input id="btn-delete-escala" type="button" onclick="deleteEscala(${item.id})" class="btn btn-sm btn-danger" value="apagar" <c:if test="${isDisableCamposEscala}">disabled</c:if> />
			               
			                <input type="hidden" id="definidaPrioridade${item.id}" value="${item.definidaPrioridade}" />
			                <input type="hidden" id="id${item.id}" value="${item.id}" />
			                <input type="hidden" id="monitor.id${item.id}" value="${item.monitor.id}" />
			                <input type="hidden" id="observacaoEscala${item.id}" value="${item.observacaoEscala}" />
			                <input type="hidden" id="prioridadeId${item.id}" value="${item.prioridadeId}" />
			                <input type="hidden" id="horaInicioPrioridade${item.id}" value="${item.horaInicioPrioridade}" />
			                <input type="hidden" id="horaFimPrioridade${item.id}" value="${item.horaFimPrioridade}" />
			                <input type="hidden" id="diaSemanaDePrioridadeId${item.id}" value="${item.diaSemanaDePrioridadeId}" />
			                <input type="hidden" id="diaSemanaAtePrioridadeId${item.id}" value="${item.diaSemanaAtePrioridadeId}" />
			                <input type="hidden" id="escalaAtiva${item.id}" value="${item.ativo}" />
		                
		                </td>
		            </tr>
		        </c:forEach>
	        </tbody>
	    </table>
	</div>
		
	<div class="form-group col-12 col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" style="margin-top: 25px"> 
	  	<h5>Detalhes escala</h5>      
	</div>	       
	
	<div class="container row panel-custom col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12"  style="margin: -20px 0 30px 0">

   	 	  <div class="form-group col-12 col-sm-12 col-md-12 col-lg-6 col-xl-4">
		      <label for="descricaoEscala" class="control-label">Descrição *</label>
	          <form:input path="descricaoEscala" class='form-control ${result.hasFieldErrors("descricaoEscala") ? "is-invalid" : ""}' placeholder2="descrição" disabled="${isDisableCamposEscala}" />
	          <form:hidden path="id" />
	          <form:hidden path="projetoId" />
	      	  <div class="invalid-feedback"><form:errors path="descricaoEscala" /></div>
		  </div>    
		   
		  <div class="form-group col-12 col-sm-6 col-md-6 col-lg-3 col-xl-2">
		      <label for="horaInicio" class="control-label">Hora início *</label>
	          <form:input path="horaInicio" class='form-control mask-hour ${result.hasFieldErrors("horaInicio") ? "is-invalid" : ""}' placeholder2="hora início" disabled="${isDisableCamposEscala}" />
	      	  <div class="invalid-feedback"><form:errors path="horaInicio" /></div>
		  </div>    
		   
		  <div class="form-group col-12 col-sm-6 col-md-6 col-lg-3 col-xl-2">
		      <label for="horaFim" class="control-label">Hora fim *</label>
	          <form:input path="horaFim" class='form-control mask-hour ${result.hasFieldErrors("horaFim") ? "is-invalid" : ""}' placeholder2="hora fim" disabled="${isDisableCamposEscala}" />
	      	  <div class="invalid-feedback"><form:errors path="horaFim" /></div>
		  </div>    
		   
		  <div class="form-group col-12 col-sm-12 col-md-12 col-lg-6 col-xl-4">
		      <label for="quantidadePrestadoresPlanejada" class="control-label">Qtde prestadores planejada *</label>
	          <form:input path="quantidadePrestadoresPlanejada" class='spinner number form-control ${result.hasFieldErrors("quantidadePrestadoresPlanejada") ? "is-invalid" : ""}' placeholder2="quantidade atendentes planejada" disabled="${isDisableCamposEscala}" />
	      	  <div class="invalid-feedback"><form:errors path="quantidadePrestadoresPlanejada" /></div>
		  </div>    
		  
		  <div class="form-group col-12 col-sm-12 col-md-12 col-lg-6 col-xl-6">
		    <label for="monitor" class="control-label">Monitor *</label>      
	        <form:select path="monitor.id" items="${monitores}"  itemLabel="nomeCompletoMatricula" itemValue="id" class='form-control editable-select ${result.hasFieldErrors("monitor.id") ? "is-invalid" : ""}'  disabled="${isDisableCamposEscala}"/>
	        <div class="invalid-feedback"><form:errors path="monitor.id" /></div>
		  </div>
		  
		  <div class="form-group col-12 col-sm-6 col-md-6 col-lg-3 col-xl-3">
		    <label for="diaSemanaDeId" class="control-label">De *</label>      
	        <form:select path="diaSemanaDeId" items="${diasSemana}"  itemLabel="nome" itemValue="id" class='form-control ${result.hasFieldErrors("diaSemanaDeId") ? "is-invalid" : ""}'  disabled="${isDisableCamposEscala}"/>
	        <div class="invalid-feedback"><form:errors path="diaSemanaDeId" /></div>
		  </div>
		  
		  <div class="form-group col-12 col-sm-6 col-md-6 col-lg-3 col-xl-3">
		    <label for="diaSemanaAteId" class="control-label">Até *</label>      
	        <form:select path="diaSemanaAteId" items="${diasSemana}"  itemLabel="nome" itemValue="id" class='form-control ${result.hasFieldErrors("diaSemanaAteId") ? "is-invalid" : ""}'  disabled="${isDisableCamposEscala}"/>
	        <div class="invalid-feedback"><form:errors path="diaSemanaAteId" /></div>
		  </div>
		  
		  <div class="form-group col-12 col-xm-10 col-sm-10 col-md-10 col-lg-10 col-xl-10">
		      <label for="observacoes" class="control-label">Observações</label>
	          <form:textarea path="observacaoEscala" class='form-control' placeholder2="observações" disabled="${isDisableCamposEscala}" maxlength="500" />
	      	  <div class="invalid-feedback"><form:errors path="observacaoEscala" /></div>
		  </div>
		        
		  <div class="form-group col-12 col-xm-2 col-sm-2 col-md-2 col-lg-2 col-xl-2">
		      <label for="ativo" class="control-label"><br/>Ativo
			      <form:checkbox path="ativo" disabled="${isDisableCamposEscala}" class="form-control" />
		      </label>	      
		  </div>
		  
		  <div class="form-group row col-12 col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
		      <label for="definidaPrioridade" class='control-label container row'> 
		   	    <form:checkbox path="definidaPrioridade" disabled="${isDisableCamposEscala}" class="form-control row col-2 col-xm-2 col-sm-2 col-md-2 col-lg-2 col-xl-2" style="top: 5px;" />
		   	    <span class="col-10 col-xm-10 col-sm-10 col-md-10 col-lg-10 col-xl-10" >Indicar horário com mais prioridade</span>
		      </label>
		  </div> 
		  
		  
		    
		  
		  <div class="row col-12 col-xm-12 col-sm-12 col-md-8 col-lg-8 col-xl-8">
		  
			<div id="painel-prioridade" class="container border panel-custom" style="display: none">
		     <div class="container row">
			     
			  <div class="form-group col-12 col-sm-12 col-md-12 col-lg-12 col-xl-6">
			    <label for="prioridadeId" class="control-label">Prioridade</label>      
		        <form:select path="prioridadeId" items="${prioridades}"  itemLabel="nome" itemValue="id" class='form-control'  disabled="${isDisableCamposEscala}"/>
			  </div>
		   
			  <div class="form-group col-12 col-sm-6 col-md-6 col-lg-6 col-xl-3"> 
			      <label for="horaInicioPrioridade" class="control-label">Hora início</label>
		          <form:input path="horaInicioPrioridade" class='form-control mask-hour' placeholder2="hora início prioridade" disabled="${isDisableCamposEscala}" />
			  </div>    
			   
			  <div class="form-group col-12 col-sm-6 col-md-6 col-lg-6 col-xl-3">
			      <label for="horaFimPrioridade" class="control-label">Hora fim</label>
		          <form:input path="horaFimPrioridade" class='form-control mask-hour' placeholder2="hora fim prioridade" disabled="${isDisableCamposEscala}" />
			  </div>     
			  
			  
			  <div class="form-group col-12 col-sm-12 col-md-6 col-lg-6 col-xl-6">
			    <label for="diaSemanaDePrioridadeId" class="control-label">De *</label>      
		        <form:select path="diaSemanaDePrioridadeId" items="${diasSemana}"  itemLabel="nome" itemValue="id" class='form-control ${result.hasFieldErrors("diaSemanaDePrioridadeId") ? "is-invalid" : ""}'  disabled="${isDisableCamposEscala}"/>
		        <div class="invalid-feedback"><form:errors path="diaSemanaDePrioridadeId" /></div>
			  </div>
			  
			  <div class="form-group col-12 col-sm-12 col-md-6 col-lg-6 col-xl-6">
			    <label for="diaSemanaAtePrioridadeId" class="control-label">Até *</label>      
		        <form:select path="diaSemanaAtePrioridadeId" items="${diasSemana}"  itemLabel="nome" itemValue="id" class='form-control ${result.hasFieldErrors("diaSemanaAtePrioridadeId") ? "is-invalid" : ""}'  disabled="${isDisableCamposEscala}"/>
		        <div class="invalid-feedback"><form:errors path="diaSemanaAtePrioridadeId" /></div>
			  </div>
		  	</div>
		  </div>
    	</div>		
		       	 
			  
       <div class="col-12 col-sm-12 col-md-4 col-lg-4 col-xl-4">
      	  	<c:if test="${!isDisableCamposEscala}">
      	  		<input id="btn-limpar-escala" type="button" class="btn btn-success float-right" value="Cancelar" style="margin: 0 10px 10px 0" <c:if test="${isDisableCamposEscala}">disabled</c:if> />
				<input id="btn-editar-escala" type="button" class="btn btn-success float-right" value="Novo" style="margin: 0 10px 10px 0" <c:if test="${isDisableCamposEscala}">disabled</c:if> />
      	  		<input id="btn-salvar-escala" type="submit" class="btn btn-success float-right" value="Salvar" style="display: none; margin: 0 10px 10px 0" <c:if test="${isDisableCamposEscala}">disabled</c:if> /> 
		    <br />
    		<br />
     	  	</c:if>
   	  </div>  	
  </div>  
</form:form>
		