<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page session="true"%>

<form:form id="form-prestador" modelAttribute="prestador" method="POST" class="form-horizontal row col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">

	<h4 class="col-8 col-sm-8 col-md-8 col-lg-8 col-xl-8" style="margin-bottom: 17px">Prestadores </h4> 
		    
	<div class="container row panel-custom col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12 border-top"> 

  	 <div class="form-group col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
	      <label for="projetoEscala" class="control-label">Selecione uma escala para configurar *</label>
          <form:select path="projetoEscala.id" id="projetoEscala" items="${escalas}"  itemLabel="descricaoCompletaEscala" itemValue="id" class='form-control select-cache-tab' />
       	  <form:hidden path="id" />
      	  <div class="invalid-feedback"><form:errors path="projetoEscala" /></div>
	  </div>    
	  
		<%-- <c:set var="total" value="${0}"/>--%>
		<c:forEach var="item" items="${prestadores}">
		    <c:set var="total" value="${total + 1}" />
		</c:forEach>
		 
		 
		<div class="form-group col-12 col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" style="margin-top: 25px"> 
		  	<h5>Todos prestadores<span class="badge"> | Quantidade ${total}/${escalaSelecionada.quantidadePrestadoresPlanejada} (real/planejada)</span></h5>      
		</div>
		<div class="table-container row table-responsive col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12"  style="margin: -20px 0 30px 0">
			<table id="tabela-prestador" class="display tabela-simples">
		       <!-- Header Table -->
		       <thead>
		            <tr>
		                <th>Prestador(a)</th>
		                <th>Função</th>
		                <th>Data início</th>
		                <th>Status</th>
	                	<th>Ação</th>
		            </tr>
		        </thead>
		        <!-- Body Table -->
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
				               
				                <input type="hidden" id="id${item.id}" value="${item.id}" />
				                <input type="hidden" id="aceito${item.id}" value="${item.aceito}" />
				                <input type="hidden" id="prestador.id${item.id}" value="${item.prestador.id}" />
				                <input type="hidden" id="projeto.id" value="${item.projeto.id}" />
				                <input type="hidden" id="observacaoPrestador${item.id}" value="${item.observacaoPrestador}" />
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
				       
		<br />	      
		  <div class="form-group col-12 col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12"> 
		  	<h5>Detalhes prestador</h5>      
		  </div>           
		  
          <div id="escala-sugerida" class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12 text-success" style="font-size: 10pt"></div>
          <div id="escala-aceite" class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12 text-danger" style="font-size: 10pt">
          </div>
          
		  <div class="form-group col-12 col-sm-12 col-md-12 col-lg-4 col-xl-4">
	        <label for="funcaoId" class="control-label">Função *</label>
            <form:select path="funcaoId" class="form-control" items="${funcoes}"  itemLabel="nome" itemValue="id" disabled="${isDisableCamposPrestador}"/>
	      </div>	
	    
	      <div class="form-group col-12 col-sm-12 col-md-12 col-lg-4 col-xl-4">
		    <label for="prestador.id" class="control-label">Prestador(a) *</label>      
	        <form:select path="prestador.id" items="${atendentes}"  itemLabel="nomeCompletoMatricula" itemValue="id" class='form-control editable-select ${result.hasFieldErrors("prestador.id") ? "is-invalid" : ""}'  disabled="${isDisableCamposPrestador}" />
	        <div class="invalid-feedback"><form:errors path="prestador.id" /></div>
		  </div>		                   
		  
		  <div class="form-group col-12 col-sm-12 col-md-12 col-lg-4 col-xl-4">
		      <label for="ramalIntegracaoRobo" class="control-label">Ramal integração robô</label>      
	          <form:input path="ramalIntegracaoRobo" class='form-control' placeholder2="id integração robô" disabled="${isDisableCamposPrestador}" maxlength="50" />
	      	  <div class="invalid-feedback"><form:errors path="ramalIntegracaoRobo" /></div>
		  </div>
		  
		  
		                   
		  <div class="form-group col-12 col-xm-12 col-sm-12 col-md-12 col-lg-4 col-xl-4">
		      <label for="dataInicio" class='control-label ${result.hasFieldErrors("dataInicio") ? "is-invalid" : ""}'>Data início *</label>
	          <form:input path="dataInicio" id="dataInicioPrestador" class='form-control datepicker' placeholder2="data início" disabled="${isDisableCamposPrestador}" />
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
		      <label for="observacoes" class="control-label">Observações</label>
	          <form:textarea path="observacaoPrestador" class='form-control' placeholder2="observações" disabled="${isDisableCamposPrestador}" />
	      	  <div class="invalid-feedback"><form:errors path="observacaoPrestador" /></div>
		  </div>
		  
		  
		  
		  <div class="form-group row col-12 col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
		      <label for="indicar-folga-semanal" class='control-label container row'> 
		   	    <form:checkbox path="indicadaFolgaSemana" id="indicar-folga-semanal" class="form-control row col-2 col-xm-2 col-sm-2 col-md-2 col-lg-2 col-xl-2" style="top: 5px;" />
		   	    <span class="col-10 col-xm-10 col-sm-10 col-md-10 col-lg-10 col-xl-10" >Indicar folga semanal</span>
		      </label>
		  </div> 
    	  
    	  <div id="folga-sugerida" class="text-success col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" style="font-size: 10pt"></div>
		  
		  <div class="form-group col-12 col-xm-12 col-sm-12 col-md-8 col-lg-8 col-xl-8">
		  
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
									<th>Hora início</th>
					                <th>Hora fim</th>
					                <th>Ação</th>
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
		  
		  
			  
      	  <div class="col-12 col-sm-12 col-md-4 col-lg-4 col-xl-4">
      	  	<c:if test="${!isDisableCamposPrestador}">
      	  		<input id="btn-cancelar-prestador" type="button" class="btn btn-success float-right" value="Cancelar" style="margin: 0 10px 10px 0"  />
				<input id="btn-editar-prestador" type="button" class="btn btn-success float-right" value="Novo" style="margin: 0 10px 10px 0" <c:if test="${isDisableCamposPrestador}">disabled</c:if> />
				<input id="btn-salvar-prestador" type="submit" class="btn btn-success float-right" value="Salvar" style="margin: 0 10px 10px 0; display: none" <c:if test="${isDisableCamposPrestador}">disabled</c:if> /> 
			    <br />
	    		<br />
     	  	</c:if>
   	  	  </div>  	
   </div>  	
 </form:form>