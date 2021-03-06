<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page session="true"%>

<form:form id="form-prestador" modelAttribute="prestador" method="POST" class="form-horizontal row col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">

	<div class="form-group col-12 col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12 border-bottom row" style="padding:0 0 10px 0;">
        <h4 class="panel-title" style="margin-right: 25px">Prestadores</h4>
    </div> 
		    
	<div class="container row panel-custom col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">  

  	 <div class="form-group col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
	      <label for="projetoEscala" class="control-label">Selecione uma escala para configurar *</label>
          <form:select path="projetoEscala.id" id="projetoEscala" items="${escalas}"  itemLabel="descricaoCompletaEscala" itemValue="id" class='form-control select-cache-tab' />
       	  <form:hidden path="id" />
      	  <div class="invalid-feedback"><form:errors path="projetoEscala" /></div>
      	  <form:hidden id="json-prestador-dias-horas" path="projetoEscala.jsonPrestadorDiasHoras" />
	  </div>    
	  
		<%-- <c:set var="total" value="${0}"/>--%>
		<c:forEach var="item" items="${prestadores}">
		    <c:set var="total" value="${total + 1}" />
		</c:forEach>
		 
		<div class="form-group col-12 col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12 border-bottom row" style="padding:0 0 10px 0; margin: 25px 10px 20px 15px"> 
		  	<h5>Todos prestadores<span class="badge"> | Quantidade ${total}/${escalaSelecionada.quantidadePrestadoresPlanejada} (real/planejada)</span></h5>      
		</div>
		<div class="table-container row table-responsive col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12"  style="margin: -20px 0 30px 0">
			<table id="tabela-prestador" class="display tabela-simples">
		       <!-- Header Table -->
		       <thead>
		            <tr>
		                <th>Prestador(a)</th>
		                <th>Fun��o</th>
		                <th>Data in�cio</th>
		                <th>Status</th>
	                	<th>A��o</th>
		            </tr>
		        </thead>

		        <tbody>        
			        <c:forEach items="${prestadores}" var="item">
			            <tr id="prestador${item.id}" class="prestador">
							<td class="rowclick" id="prestador${item.id}" >${item.prestador.nomeCompletoMatricula}</td>
			                <td class="rowclick" id="funcao${item.id}">${item.funcao.nome}</td>
			                <td class="rowclick" id="dataInicio${item.id}">
			                	<fmt:parseDate pattern="yyyy-MM-dd" value="${item.dataInicio}" var="dataInicio" />
			                	<fmt:formatDate value="${dataInicio}" pattern="dd/MM/yyyy" />
							</td>
			                <td class="rowclick" id="escala-aceite-grid${item.id}" style="font-size: 10pt">
	                
				                	<c:if test="${item.projeto.ativo}">Projeto ativo</c:if>
				                	<c:if test="${!item.projeto.ativo}">Projeto desativado</c:if><br>
						          	<c:if test="${item.aceito == 0}">Aguardando aceite pelo prestador</c:if>
						          	<c:if test="${item.aceito == 1}">Aceito pelo prestador</c:if>
						          	<c:if test="${item.aceito == 2}">Recusado pelo prestador</c:if>
							</td>
			                <td>
			                
				                <input id="btn-delete-prestador" type="button" onclick="deletePrestador(${item.id})" class="btn btn-sm btn-danger" value="apagar" <c:if test="${isDisableCamposPrestador}">disabled</c:if> />
				               
				                <input type="hidden" id="chk-dias-horas-trabalho-customizado${item.id}" value='${item.hasDiasHorasTrabalho}' /> 
				                <input type="hidden" id="json-prestador-dias-horas${item.id}" value='${item.jsonDiasHorasTrabalho}' /> 
				                <input type="hidden" id="id${item.id}" value="${item.id}" />
				                <input type="hidden" id="aceito${item.id}" value="${item.aceito}" /> 
				                <input type="hidden" id="prestador.id${item.id}" value="${item.prestador.id}" />
				                <input type="hidden" id="projeto.id" value="${item.projeto.id}" />
				                <input type="hidden" id="observacaoPrestador${item.id}" value="${item.observacaoPrestador}" />
				                <input type="hidden" id="ramalIntegracaoRobo${item.id}" value="${item.ramalIntegracaoRobo}" />
				                <input type="hidden" id="prestadorAtivo${item.id}" value="${item.ativo}" />
			                	<fmt:parseDate pattern="yyyy-MM-dd" value="${item.dataFim}" var="dataFim" />
				                <input type="hidden" id="dataFim${item.id}" value="<fmt:formatDate value="${dataFim}" pattern="dd/MM/yyyy" />" />
		                
				                <span id="escala-aceite${item.id}" style="display: none">
							          	<c:if test="${item.aceito == 0}">Aguardando aceite pelo prestador</c:if>
							          	<c:if test="${item.aceito == 1}">Aceito pelo prestador</c:if>
							          	<c:if test="${item.aceito == 2}">Recusado pelo prestador</c:if>
							          	<c:if test='${item.motivoRecusa != null && item.motivoRecusa != ""}'> (Motivo: ${item.motivoRecusa})</c:if>
								</span>
			                </td>
			            </tr>
			        </c:forEach>
		        </tbody>
		    </table>
		</div>
				    
		<div class="form-group col-12 col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12 border-bottom row" style="padding:0 0 10px 0; margin: 25px 10px 20px 15px">
              <h5 class="panel-title" style="margin-right: 25px">Detalhes prestador</h5> 
	      	  	<c:if test="${!isDisableCamposPrestador}">
					<input id="btn-salvar-prestador" type="submit" class="btn btn-sm btn-success float-left" value="Salvar" style="margin: 1px; height: 30px; display: none" <c:if test="${isDisableCamposPrestador}">disabled</c:if> /> 
					<input id="btn-editar-prestador" type="button" class="btn btn-sm btn-success float-left" value="Novo" style="margin: 1px; height: 30px" <c:if test="${isDisableCamposPrestador}">disabled</c:if> />
	      	  		<input id="btn-cancelar-prestador" type="button" class="btn btn-sm btn-success float-left" value="Cancelar" style="margin: 1px; height: 30px"  />
	     	  	</c:if>
  	  	  </div>
   	  	  
          <div id="escala-sugerida" class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12 text-success" style="font-size: 10pt"></div>
          <div id="escala-aceite" class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12 text-danger" style="font-size: 10pt">
          </div>
          
		  <div class="form-group col-12 col-sm-12 col-md-12 col-lg-4 col-xl-4">
	        <label for="funcao.id" class="control-label">Fun��o *</label>
            <form:select path="funcao.id" id="funcaoId" class="form-control" items="${funcoesAtendentes}"  itemLabel="nomeCompleto" itemValue="id" disabled="${isDisableCamposPrestador}"/>
	      </div>	
	    
	      <div class="form-group col-12 col-sm-12 col-md-12 col-lg-4 col-xl-4">
		    <label for="prestador.id" class="control-label">Prestador(a) *</label>      
	        <form:select path="prestador.id" items="${atendentes}"  itemLabel="nomeCompletoMatricula" itemValue="id" class='form-control editable-select ${result.hasFieldErrors("prestador.id") ? "is-invalid" : ""}' disabled="${isDisableCamposPrestador}" />
	        <div class="invalid-feedback"><form:errors path="prestador.id" /></div>
		  </div>		                   
		  
		  <div class="form-group col-12 col-sm-12 col-md-12 col-lg-4 col-xl-4">
		      <label for="ramalIntegracaoRobo" class="control-label">Ramal integra��o rob�</label>      
	          <form:input path="ramalIntegracaoRobo" class='form-control' placeholder2="id integra��o rob�" disabled="${isDisableCamposPrestador}" maxlength="50" />
	      	  <div class="invalid-feedback"><form:errors path="ramalIntegracaoRobo" /></div>
		  </div>
		                   
		  <div class="form-group col-12 col-xm-12 col-sm-12 col-md-12 col-lg-4 col-xl-4">
		      <label for="dataInicio" class='control-label ${result.hasFieldErrors("dataInicio") ? "is-invalid" : ""}'>Data in�cio *</label>
	          <form:input path="dataInicio" id="dataInicioPrestador" class='form-control datepicker' placeholder2="data in�cio" disabled="${isDisableCamposPrestador}" />
	      	  <div class="invalid-feedback"><form:errors path="dataInicio" /></div>
		  </div>
		    
		  <div class="form-group col-12 col-xm-12 col-sm-7 col-md-8 col-lg-4 col-xl-4">
		      <label for="dataFim" id="dataFimPrestador" class='control-label container row'> 
		   	    <input id="check-box-data-fim" ${isDisableCamposProjeto ? "disabled" : ""} class="form-control row col-2 col-xm-2 col-sm-2 col-md-2 col-lg-2 col-xl-2" type="checkbox" style="top: 5px;" />
		   	    <span class="col-12 col-xm-12 col-sm-10 col-md-10 col-lg-10 col-xl-10" >Data fim</span> 
		      </label>
	          <form:input path="dataFim" class='form-control datepicker'  placeholder2="data fim" disabled="${isDisableCamposPrestador}" />
	      	  <div class="invalid-feedback"><form:errors path="dataFim" /></div>
		  </div> 
		        
		  <div class="form-group col-12 col-xm-6 col-sm-2 col-md-2 col-lg-2 col-xl-2"> 
		      <label for="ativo" class="control-label"><br/>Ativo
			      <form:checkbox path="ativo" disabled="${isDisableCamposPrestador}" class="form-control" />
		      </label>	      
		  </div> 
		        
		  <div id="panel-reenviar-convite" class="form-group col-12 col-xm-6 col-sm-2 col-md-2 col-lg-2 col-xl-2">
		      <label for="reenviarConvite" class="control-label">Reenviar convite
			      <form:checkbox path="reenviarConvite" disabled="${isDisableCamposPrestador}" class="form-control" />
		      </label>	      
		  </div>
		  
		  
		  <div class="form-group col-12 col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
		      <label for="observacoes" class="control-label">Observa��es</label>
	          <form:textarea path="observacaoPrestador" class='form-control' placeholder2="observa��es" disabled="${isDisableCamposPrestador}" />
	      	  <div class="invalid-feedback"><form:errors path="observacaoPrestador" /></div>
		  </div>
		  
		  
		  
		     
		  <div class="form-group row col-12 col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
		      <label for="chk-dias-horas-trabalho-customizado" class='control-label container row'> 
		   	    <form:checkbox path="hasDiasHorasTrabalho" id="chk-dias-horas-trabalho-customizado" class="form-control row col-2 col-xm-2 col-sm-2 col-md-2 col-lg-2 col-xl-2" style="top: 5px;" />
		   	    <span class="col-10 col-xm-10 col-sm-10 col-md-10 col-lg-10 col-xl-10" >Indicar dias e hor�rios de trabalho customizados</span>
		      </label>
		  </div>  
		  
		  <div class="table-responsive form-group col-12 col-xm-12 col-sm-12 col-md-12 col-lg-8 col-xl-8" style="font-size: 10pt; display: none;" id="panel-dias-horas-trabalho-customizado"> 
				<table id="tabela-dias-trabalho" class="display tabela-simples">
			       <!-- Header Table -->
			       <thead>
			            <tr>
			                <th>Dia semana</th>
							<th>Hora in�cio/fim</th>
			            </tr>
			        </thead>
			        <!-- Body Table -->
			        <tbody>    
			            <tr>
							<td	style="font-weight: bold">Segunda<form:hidden id="segunda-diaSemana" path="diasHorasTrabalho[0].diaSemana" /><form:hidden id="segunda-id" path="diasHorasTrabalho[0].id" /></td> 
							<td>
								<form:input id="segunda-hora-inicio" path="diasHorasTrabalho[0].horaInicio" class="form-control mask-hour" style="margin-right: 10px; width: 80px; position: relative; float: left;" disabled="true" />
								<form:input id="segunda-hora-fim" path="diasHorasTrabalho[0].horaFim" class="form-control mask-hour" style="width: 80px; position: relative; float: left;" disabled="true" />
							</td>
			            </tr>   
			            <tr>
							<td	style="font-weight: bold">Ter�a<form:hidden id="terca-diaSemana" path="diasHorasTrabalho[1].diaSemana" /><form:hidden id="terca-id" path="diasHorasTrabalho[1].id" /></td>
							<td>
								<form:input id="terca-hora-inicio" path="diasHorasTrabalho[1].horaInicio" class="form-control mask-hour" style="margin-right: 10px; width: 80px; position: relative; float: left;" disabled="true" />
								<form:input id="terca-hora-fim" path="diasHorasTrabalho[1].horaFim" class="form-control mask-hour" style="width: 80px; position: relative; float: left;" disabled="true" />
							</td>
			            </tr>  
			            <tr>
							<td	style="font-weight: bold">Quarta<form:hidden id="quarta-diaSemana" path="diasHorasTrabalho[2].diaSemana" /><form:hidden id="quarta-id" path="diasHorasTrabalho[2].id" /></td>
							<td>
								<form:input id="quarta-hora-inicio" path="diasHorasTrabalho[2].horaInicio" class="form-control mask-hour" style="margin-right: 10px; width: 80px; position: relative; float: left;" disabled="true" />
								<form:input id="quarta-hora-fim" path="diasHorasTrabalho[2].horaFim" class="form-control mask-hour" style="width: 80px; position: relative; float: left;" disabled="true" />
							</td>
			            </tr>  
			            <tr>
							<td	style="font-weight: bold">Quinta<form:hidden id="quinta-diaSemana" path="diasHorasTrabalho[3].diaSemana" /><form:hidden id="quinta-id" path="diasHorasTrabalho[3].id" /></td>
							<td>
								<form:input id="quinta-hora-inicio" path="diasHorasTrabalho[3].horaInicio" class="form-control mask-hour" style="margin-right: 10px; width: 80px; position: relative; float: left;" disabled="true" />
								<form:input id="quinta-hora-fim" path="diasHorasTrabalho[3].horaFim" class="form-control mask-hour" style="width: 80px; position: relative; float: left;" disabled="true" />
							</td>
			            </tr>  
			            <tr> 
							<td	style="font-weight: bold">Sexta<form:hidden id="sexta-diaSemana" path="diasHorasTrabalho[4].diaSemana" /><form:hidden id="sexta-id" path="diasHorasTrabalho[4].id" /></td>
							<td>
								<form:input id="sexta-hora-inicio" path="diasHorasTrabalho[4].horaInicio" class="form-control mask-hour" style="margin-right: 10px; width: 80px; position: relative; float: left;" disabled="true" />
								<form:input id="sexta-hora-fim" path="diasHorasTrabalho[4].horaFim" class="form-control mask-hour" style="width: 80px; position: relative; float: left;" disabled="true" />
							</td>
			            </tr>  
			            <tr>
							<td	style="font-weight: bold">S�bado<form:hidden id="sabado-diaSemana" path="diasHorasTrabalho[5].diaSemana" /><form:hidden id="sabado-id" path="diasHorasTrabalho[5].id" /></td>
							<td>
								<form:input id="sabado-hora-inicio" path="diasHorasTrabalho[5].horaInicio" class="form-control mask-hour" style="margin-right: 10px; width: 80px; position: relative; float: left;" disabled="true" />
								<form:input id="sabado-hora-fim" path="diasHorasTrabalho[5].horaFim" class="form-control mask-hour" style="width: 80px; position: relative; float: left;" disabled="true" />
							</td>
			            </tr>  
			            <tr>
							<td	style="font-weight: bold">Domingo<form:hidden id="domingo-diaSemana" path="diasHorasTrabalho[6].diaSemana" /><form:hidden id="domingo-id" path="diasHorasTrabalho[6].id" /></td> 
							<td>
								<form:input id="domingo-hora-inicio" path="diasHorasTrabalho[6].horaInicio" class="form-control mask-hour" style="margin-right: 10px; 	width: 80px; position: relative; float: left;" disabled="true" />
								<form:input id="domingo-hora-fim" path="diasHorasTrabalho[6].horaFim" class="form-control mask-hour" style="width: 80px; position: relative; float: left;" disabled="true" />
							</td>
			            </tr>   
			        </tbody>
			    </table>
			    <br>
		  </div>
		  
		  
		  <div class="form-group row col-12 col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
		      <label for="indicar-folga-semanal" class='control-label container row'> 
		   	    <form:checkbox path="indicadaFolgaSemana" id="indicar-folga-semanal" class="form-control row col-2 col-xm-2 col-sm-2 col-md-2 col-lg-2 col-xl-2" style="top: 5px;" />
		   	    <span class="col-10 col-xm-10 col-sm-10 col-md-10 col-lg-10 col-xl-10" >Indicar folga semanal</span>
		      </label>
		  </div> 
    	  
    	  <div id="folga-sugerida" class="text-success col-12 col-xm-12 col-sm-12 col-md-12 col-lg-8 col-xl-8" style="font-size: 10pt"></div>
    	  
		  <div class="form-group col-12 col-xm-12 col-sm-9 col-md-8 col-lg-8 col-xl-8">
		  
			<div id="panel-folga-semanal" class="container border panel-custom" style="display: none">
			     <div class="container row">
				       
          
          
				       <div class="form-group col-12 col-sm-12 col-md-12 col-lg-6 col-xl-6">
				       	<label for="folgaMotivo" class="control-label">Motivo</label>
				       	<div>
				           <select id="folgaMotivo" class="form-control" <c:if test="${isDisableCamposPrestador}">disabled</c:if>>
					        <c:forEach items="${motivos}" var="motivo">
					            <option value="${motivo.id}">${motivo.nome}</option>
					        </c:forEach>
				           </select>
				           	<div class="invalid-feedback" id="feedback-folgaMotivo"></div>
				           </div>
				       </div>
				       
				       <div class="form-group col-12 col-sm-12 col-md-12 col-lg-6 col-xl-6"> 
				       	<label for="folgaDiaSemana" class="control-label">Dia semana</label>
				       	<div>
				            <select id="folgaDiaSemana" class="form-control" <c:if test="${isDisableCamposPrestador}">disabled</c:if>>
						        <c:forEach items="${diasSemana}" var="diaSemana">
						        	<option value="${diaSemana.id}">${diaSemana.nome}</option>
						        </c:forEach>
				            </select>
				           	<div class="invalid-feedback" id="feedback-folgaDiaSemana"></div>
				           </div>
				       </div>
				       
				       <div class="form-group col-12 col-sm-12 col-md-6 col-lg-6 col-xl-6">
				       	<label for="folgaId" class="control-label">Hora inicio</label>
				       	<div>
				           	<input id="folgaId" class="form-control mask-hour" type="hidden" />
				           	<input class="form-control mask-hour" id="folgaHoraInicio" <c:if test="${isDisableCamposPrestador}">disabled</c:if> />
				           	<div class="invalid-feedback" id="feedback-folgaHoraInicio"></div>
				           </div>
				       </div>
				       
				       <div class="form-group col-12 col-sm-12 col-md-6 col-lg-6 col-xl-6">
				       	<label for="folgaHoraFim" class="control-label">Hora fim</label>
				       	<div>
				           	<input class="form-control mask-hour" id="folgaHoraFim"  <c:if test="${isDisableCamposPrestador}">disabled</c:if>/>
				           	<div class="invalid-feedback" id="feedback-folgaHoraFim"></div>
				           </div>
				       </div>
				       
				       <div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
							<input id="btn-cancelar-folga" type="button" class="btn btn-sm btn-success float-right" value="Cancelar folga" onclick="limparFolga()" style="margin: 0 10px 10px 0" <c:if test="${isDisableCamposPrestador}">disabled</c:if> />
							<input id="btn-salvar-folga" type="button" class="btn btn-sm btn-success float-right" value="Salvar folga"  onclick="salvarFolga()" style="style: display: none; margin: 0 10px 10px 0" <c:if test="${isDisableCamposPrestador}">disabled</c:if> />
							<input id="btn-editar-folga" type="button" class="btn btn-sm btn-success float-right" value="Nova folga" style="margin: 0 10px 10px 0"  />
				
						</div>
			
			 	
					 <div class="table-container table-responsive"  style="margin-left: 20px">
						<table id="tabela-folga-semanal" class="display tabela-simples">
					       <!-- Header Table -->
					       <thead>
					            <tr>
									<th>Motivo</th>
					                <th>Dia semana</th>
									<th>Hora in�cio</th>
					                <th>Hora fim</th>
					                <th>A��o</th>
					            </tr>
					        </thead>
					        <!-- Body Table -->
					        <tbody>        
					        </tbody>
					    </table>
					</div>
			     </div>
	  		</div>
		  </div>
   </div>  	
 </form:form>