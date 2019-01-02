<!DOCTYPE html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page session="true"%>

<head>
	<title>Ixia - Sistema de Escala - Aprovação de horas</title>
    <jsp:include page="../shared/headerPartialView.jsp"/>
	<script>
		$(document).ready(function() {

         $('#btn-gerar-prestador').on('click',function() {
			var mes = $("#txt-pesquisar-mes").val();
			var ano = $("#txt-pesquisar-ano").val();
			var prestador = $("#prestador").val();
			if (mes == "" || mes == null || mes <= 0 || mes > 12) {
				alert("Digite um mês válido");
				return;
			}
			
			if (ano == "" || ano == null || ano < 2018 || ano > ${anoBase}) {
				alert("Digite um ano válido");
				return;
			}
			
			if (prestador == "" || prestador == null || prestador == 0) {
				alert("Selecione um prestador");
				return;
			}
			
     		window.location.href = urlBase + "geracaoHoras/" + prestador + "?ano=" + $("#txt-pesquisar-ano").val() + "&mes=" + $("#txt-pesquisar-mes").val();
    	 });


			$("#banco-id").change(function() {
				var mes = $("#txt-pesquisar-mes").val();
				var ano = $("#txt-pesquisar-ano").val();
				var banco = $("#banco-id").val();
				if (mes <= 0 || mes > 12) {
					alert("Digite um mês válido");
					$("#banco-id").val(0)
					 $("#banco-id option").each(function () { 
				         $(this).removeAttr("selected");
					 });
					 
					return;
				}
				
				filtrarGerarHoras();
			});
		});
		
		function filtrarGerarHoras() {
			var mes = $("#txt-pesquisar-mes").val();
			var ano = $("#txt-pesquisar-ano").val();
			var banco = $("#banco-id").val();
			var prestador = $("#prestador").val();
			if (mes == 0 || mes > 12){
				alert("Digite um mês válido");
				return;
			}
			
			if (ano < 2018 || ano > ${anoBase}){
				alert("Digite um ano válido");
				return;
			}

			var item = $("#selected-id-gerar-horas").val();
			

			if (item == "2") {
				
				if (prestador == "" || prestador == null || prestador == 0) {
					alert("Selecione um prestador");
					return;
				}
				
	     		window.location.href = urlBase + "geracaoHoras/" + prestador + "?ano=" + $("#txt-pesquisar-ano").val() + "&mes=" + $("#txt-pesquisar-mes").val();
			} else if (item == "4") {
				integracaoRobo();				
			} else if (item == "3") {
				gerarCsv();
			} else {
				$("*").css("cursor", "progress");
	        	window.location.href = "<c:url value='/aprovacaoHoras' />?mes=" + mes + "&ano=" + ano + (banco != null && banco != 0 ? "&banco=" + banco : "") + (prestador != null && prestador != 0 ? "&prestador=" + prestador : "");
			}
			
		}
		
		function aprovar(id){
			post(urlBase + "/horas/aprovar/" + id, true, id);
		}
		
		function recusar(id){
			if (!confirm("Deseja realmente recusar estas horas?")) {
				return;	
			}

			motivo = prompt("Digite aqui o motivo da recusa:")
			post(urlBase + "/horas/aprovar/" + id + "?aprovar=false&motivo=" + motivo, false, id);
			
		}
		
		function post(localUrl, aprovar,id) {

			console.log(localUrl); 
			$.ajax({
				type : "GET", 
				contentType : "application/json",
				url : localUrl,
				dataType: 'text',
				success : function(data) {
					$("#aprovar").hide();
					$("#recusar").hide();
					$("#linha-aprovacao"+id).html("<span style='font-size: 10pt'>" + (aprovar ? "Aprovado" : "Reprovado") + " Financeiro (atualizar tela)<span>");
				},
				error : function(e) {
					alert("Error!")
					console.log("ERROR: ", e);
				}
			});	
		}

		function gerarCsv() {
			var banco = $("#banco-id").val();
			if (banco == null || banco <= 0) {
				alert("Selecione um banco");
				return;
			}
			
			var item = $("#selected-id-gerar-csv").val();
			var items = "";
			var total = 1;
			var rows = $(".select-rows");
			rows.each(function() {
				var id = this.id.replace("id-row", "");
				var status = $("#status-financeiro" + id).val();
				if (item == "1" || item == "2") {
					if (item == "1" && status == 1) {
						items = (items!=""?",":"") + (status != null ? status : "");
						total++;
					} else if (item == "2" && (status == 1 || status == 3)) {
						items = (items!=""?",":"") + (status != null ? status : "");
						total++;
					}
				}
				else if (item == "3") {
					var checked = $("#checked-item" + id);
					if (checked != null && checked.prop("checked")) {
						items = (items!=""?",":"") + (status != null ? status : "");
						total++;		
					}
				}
			});
			
			total--;
			if (items=="") {
				alert("Não existem itens para a opção selecionada");
				return;
			}
			
			if (confirm("Deseja realmente gerar " + (item == "3" ? "todos" : "") + " '" + $("#selected-text-gerar-csv").html().toLowerCase() + "' (" + total + (total > 1 ? " itens" :  " item") + ").")) {
				$("*").css("cursor", "progress");
	        	window.open("<c:url value='/horas/gerarCsv' />?itens=" + items, '_blank');
			}
		}
		
		function integracaoRobo() {
			var integracao = $("#integracao-robo").val();
			if (integracao == null || integracao == "") {
				alert("Selecione um arquivo para integracao");
				return false;
			}
			else {
				$("#form-integracao-robo").submit();
			}
		}
		
		function selectGerarCsv(item) {
			$("#selected-id-gerar-csv").val(item);
			if (item == 3) {
				$("#selected-text-gerar-csv").html("Selecionados");
			}
			else if (item == 2) {
				$("#selected-text-gerar-csv").html("Todos aprovados financeiro");
			}
			else {
				$("#selected-text-gerar-csv").html("Todos aprovados financeiro e ainda não gerados");
			}
		}
		
		function selectGerarHoras(item) {
			$("#selected-id-gerar-horas").val(item);
			$("#panel-gerar-csv").hide();
			$("#panel-banco").show();
			$("#panel-prestador").show();
			$("#panel-dados-robo").hide();
			if (item == 3) {
				$("#selected-text-gerar-horas").html("Gerar CSV");
				$("#panel-gerar-csv").show();
			}
			else if (item == 4) {
				$("#selected-text-gerar-horas").html("Integrar dados robô");
				$("#panel-banco").hide();
				$("#panel-prestador").hide();
				$("#panel-dados-robo").show();
			}
			else if (item == 2) {
				$("#selected-text-gerar-horas").html("Gerar mês para prestador");
				$("#panel-banco").hide();
			}
			else {
				$("#selected-text-gerar-horas").html("Filtro");
			}
		}
	</script>
</head>
<body>

	<jsp:include page="../shared/navbarPartialView.jsp"/>

    <div class="container">  
        <div style="margin-top:50px" class="mainbox col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
		              
       		<jsp:include page="../shared/errorPartialView.jsp"/>	
       	  
			<h3>Horas aprovação</h3>
			<div class="container border-top panel-custom"> 
				  <c:if test="${!isAtendimento}">
				  <div class="form-group col-sm-12 row">
				  
					  <div class="form-group col-6 col-sm-6 col-md-6 col-lg-2 col-xl-2">
					      <label for="txt-pesquisar-ano" class="control-label">Ano</label>
				          <input id="txt-pesquisar-ano" class="form-control mask-year" value="${ano}" />
					  </div> 
					
					  <div class="form-group col-6 col-sm-6 col-md-6 col-lg-2 col-xl-2">
					      <label for="txt-pesquisar-mes" class="control-label">Mês</label>
				          <input id="txt-pesquisar-mes" class="form-control mask-month" value="${mes}" />
					  </div>

				     <div id="panel-banco" class="form-group col-12 col-sm-6 col-md-6 col-lg-4 col-xl-4">
				         <label for="banco" class="control-label">Banco</label>
			             <select id="banco-id" class="form-control">
			             	<option <c:if test="${bancoId == 0}">selected</c:if> value="0">Todos</option>
			             	<option <c:if test="${bancoId < 0}">selected</c:if> value="-1">Sem banco cadastrado</option>
			             	<c:forEach items="${bancos}" var="banco">
				             	<option <c:if test="${banco.id == bancoId}">selected</c:if> value="${banco.id}">${banco.nome}</option>
		             	  	</c:forEach> 
				         </select>
				     </div>

	
					
					<c:if test="${isAdministracao}">				
						<div id="panel-prestador" class="form-group  col-12 col-sm-6 col-md-6 col-lg-4 col-xl-4">
					      <label for="projetoEscala" class="control-label">Selecione um prestador</label>
				          <select 
				          	id="prestador" 
				          	class='form-control select-cache-tab'>
				          	<option value="0"></option>
				          	<c:forEach var="prestador" items="${prestadores}">
				          		<option <c:if test="${prestadorId==prestador.id}">selected</c:if> value="${prestador.id}">${prestador.nomeCompletoMatricula}</option>
				          	</c:forEach>
				          	</select>
			       	    </div>    
			        </c:if>

				     <div class="form-group col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12"> 
				         <label for="banco" class="control-label">&nbsp;</label>
				        <br> 
				        
				  	
					  <div class="btn-group " role="group">
						  
						<c:if test="${!isAdministracao && !isFinanceiro}">	
    	  		  			<button id="btn-filtrar" class="btn btn-sm btn-primary" onclick="filtrarGerarHoras()" style="height: 35px">Filtrar</button>						
						</c:if>
						
						<c:if test="${isAdministracao || isFinanceiro}">	
	    	  		  		<button id="btn-filtrar" class="btn btn-sm btn-primary" onclick="filtrarGerarHoras()" style="height: 35px">Executar</button>
							  <div class="btn-group" role="group">
								    <button id="btnGroupDrop2"  style=";height: 35px" type="button" class="btn btn-sm btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								      <span id="selected-text-gerar-horas">Filtro</span>
								      <input type="hidden" id="selected-id-gerar-horas" value="1" />
								    </button>
						           	<div id="select-gerar-csv" class="dropdown-menu">
							        	<button type="button" class="dropdown-item" onclick="selectGerarHoras(1)">Filtro</button> 
										  <c:if test="${isAdministracao}">
							        		<button type="button" class="dropdown-item" onclick="selectGerarHoras(2)">Gerar mês para prestador</button>
							        		<button type="button" class="dropdown-item" onclick="selectGerarHoras(4)">Integrar dados robô</button>
										  </c:if>
										  <c:if test="${isFinanceiro}">
											<button type="button" class="dropdown-item" onclick="selectGerarHoras(3)">Gerar CSV</button>
										  </c:if>
							        </div>
							        
							        
									  <c:if test="${isFinanceiro}">				  	
											  <!-- <button type="button" class="btn btn-sm btn-primary" onclick="gerarCsv()">Gerar CSV</button> -->
											  <div id="panel-gerar-csv" class="btn-group" role="group" style="display: none; margin-left: -2px;">
											    <button id="btnGroupDrop1" type="button" class="btn btn-sm btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
											      <span id="selected-text-gerar-csv">Todos aprovados financeiro e ainda não gerados</span> 
											      <input type="hidden" id="selected-id-gerar-csv" value="1" />
											    </button>
										           	<div id="select-gerar-csv" class="dropdown-menu">
											        	<button type="button" class="dropdown-item" onclick="selectGerarCsv(1)">Todos aprovados financeiro e ainda não gerados</button>
											        	<button type="button" class="dropdown-item" onclick="selectGerarCsv(2)">Todos aprovados financeiro</button>
											        	<button type="button" class="dropdown-item" onclick="selectGerarCsv(3)">Selecionados</button>
											        </div>
											    </div> 
									  </c:if> 
							        
							    </div> 
						    </c:if>
					  </c:if>
						        	  			
		   	  			<c:if test="${isAdministracao}">					          
			       	  		<div id="panel-dados-robo" class="form-group" style="display: none; height: 35px; margin-left: -2px;">	
			       	  			<form method="POST" id="form-integracao-robo" action="<c:url value='integracaoRobo'/>" enctype="multipart/form-data"> 				
								    <input class="btn btn-sm btn-primary float-left" type="file" style="height: 35px;" name="file" id="integracao-robo" />			        	  				
								</form>
				  			</div>	 
		      	  		</c:if> 
		      	  		
	
				</div> 
			</div>
				<div class="table-container table-responsive" style="margin-top: 100px">
					<table id="tabela" class="display tabela-avancada">

				        <!-- Header Table -->
				        <thead>
				            <tr>
				                <th>Ação</th>
				                <th>Dados aprovação</th>
				                <th>Aprovação etapa 1 (prestador)</th>
				                <th>Aprovação etapa 2 (monitor)</th>
				                <th>Aprovação etapa 3 (financeiro)</th>
				            </tr>
				        </thead>
				        <tbody>        
					        <c:forEach items="${aprovacaoHoras}" var="item">
					            <tr class="select-rows" id="id-row${item.id}">
				                	<fmt:parseDate pattern="yyyy-MM-dd" value="${item.data}" var="data" /> 
											
	                				<td>
	                					<a  class="btn btn-sm btn-primary" 
	                						href="<c:url value='/horasTrabalhadas' />/${item.id}"
	                						style="margin: 1px">ver	</a> 
									     <c:if test="${isFinanceiro}"> 
										     <c:if test="${item.aceitePrestador == 1 && item.dadosAcessoAprovacaoHoras.aprovado == 1}">
											     <c:if test="${item.aceiteAprovador == 0}"> 
				                					<button  class="btn btn-sm btn-primary" 
				                						id="aprovar"
				                						onclick="aprovar(${item.id})" 
				                						style="margin: 1px">aprovar</button>
	
				                					<button  class="btn btn-sm btn-danger" 
				                						id="recusar"
				                						onclick="recusar(${item.id})"
				                						style="margin: 1px">recusar</button>
											     </c:if>
											     <c:if test="${item.aceiteAprovador == 1}">
											     	Selecionar
											     	<input type="checkbox" id="checked-item${item.id}"> 
											     </c:if> 
										     </c:if>
									     </c:if>
	                				</td> 
	                				
					                <td style="font-size: 10pt">
					                  <div style="width: 200px">
					                	<b>Id:</b> ${item.id}<br>
					                	<b>Prestador:</b> ${item.prestador.nomeCompletoMatricula}<br>
					                	<b>Data:</b> <fmt:formatDate value="${data}" pattern="dd/MM/yyyy" /><br>
									  	<b>Horas:</b> ${item.totalHorasFormatada}hr  <c:if test="${item.totalHoras < 1}">(${item.totalHoras}hr)</c:if><br> 
										<b>Valor:</b> R$ ${item.totalValor}
									  </div>
									</td> 
				                							
								  	<td style="font-size: 10pt">
		                				<div style="width: 150px">
										  
									  
										  <c:if test="${item.aceitePrestador == 0}">
										      <span class="text-primary">(Pendente) Prestador</span>
										   </c:if>
										  <c:if test="${item.aceitePrestador == 1}">
										      <span class="text-success">(Aprovado) Prestador
											  </span>
										   </c:if>
										  <c:if test="${item.aceitePrestador == 2}">
										      <span class="text-danger">(Recusado) Prestador</span>
										   </c:if>
										   <c:if test="${item.arquivoNota!=null && item.arquivoNota!=''}"><br><a class="text-dark" href="<c:url value='nota/${item.id}'/>" target="_blank">Nota fiscal anexa</a></c:if>
										   </div>
										  <c:set var="status">${item.data.year == dataAtual.year && item.data.monthValue == dataAtual.monthValue}</c:set>
										  <i style="font-size: 10pt" class="text-danger">
							        	  	  <c:if test="${status}">
							        	  	  	As etapas de aprovação serão iniciadas apenas após o ultimo dia do mês.
							        	  	  </c:if>
											  <c:if test="${!status}">
											  	<c:if test="${item.aceitePrestador != 1}">Processo de aprovação iniciado</c:if>
											  	<c:if test="${item.aceitePrestador == 1 &&  item.aceiteAprovador == 1}">Processo finalizado</c:if>
											  </c:if>
										  </i> 
										  
									  </td>
				                	  <td style="font-size: 10pt">
		                				  <div style="width: 350px">
										  	
									         <c:forEach items="${item.dadosAcessoAprovacaoHoras.dadosAprovacao}" var="item2">
		               			
									  			  <c:set var="styleStatusAprovador">
												  <c:if test="${item2.nome.contains('(Pendente') || item2.nome.contains('(Parcial')}">text-primary</c:if>
												  <c:if test="${item2.nome.contains('(Aprovado')}">text-success</c:if>
												  <c:if test="${item2.nome.contains('(Reprovado')}">text-danger</c:if>
												  </c:set>
												  
											      <br />		
											      <label class="control-label ${styleStatusAprovador}">${item2.nome} | <b>${item2.descricao}hr  <c:if test="${item2.doubleValue < 1}">(${item2.doubleValue}hr)</c:if></b> </label>					          
									          </c:forEach>
								          </div>
									  </td>
				                	  <td style="font-size: 10pt" id="linha-aprovacao${item.id}">
		                				<div style="width: 200px">
								          <c:if test="${item.aceiteAprovador == 0}"><span class="text-primary">(Pendente) Financeiro</span></c:if>
								          <c:if test="${item.aceiteAprovador == 1}"><span class="text-success">(Aprovado) Financeiro | ${item.aprovador.nomeCompletoMatricula}</span></c:if>
								          <c:if test="${item.aceiteAprovador == 2}"><span class="text-danger">(Recusado) Financeiro (${item.motivoRecusaAprovador}) | ${item.aprovador.nomeCompletoMatricula}</span></c:if>
								          <c:if test="${item.aceiteAprovador == 3}"><span class="text-success">(Finalizado) Financeiro | ${item.aprovador.nomeCompletoMatricula}</span></c:if>
								          <input type="hidden" id="status-financeiro${item.id}" value="${item.aceiteAprovador}" />
										</div>
				                	</td>
					            </tr>
					        </c:forEach>
				        </tbody>
				    </table>

                
         </div> 

    </div>
    
</body>

</html>