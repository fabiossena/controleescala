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
			$("#banco-id").change(function() {
				var mes = $("#txt-pesquisar-mes").val();
				var ano = $("#txt-pesquisar-ano").val();
				var banco = $("#banco-id").val();
				if (mes == 0 || mes > 12){
					alert("Digite um mês válido");
					$("#banco-id").val(0)
					 $("#banco-id option").each(function () { 
				         $(this).removeAttr("selected");
					 });
					 
					return;
				}
				
				filtrar();
			});
		});
		
		function filtrar() {
			var mes = $("#txt-pesquisar-mes").val();
			var ano = $("#txt-pesquisar-ano").val();
			var banco = $("#banco-id").val();
			if (mes == 0 || mes > 12){
				alert("Digite um mês válido");
				return;
			}
			
			if (ano < 2018 || ano > ${anoBase}){
				alert("Digite um ano válido");
				return;
			}
			$("*").css("cursor", "progress");
        	window.location.href = "<c:url value='/aprovacaoHoras' />?mes=" + mes + "&ano=" + ano + (banco != null && banco != 0 ? "&banco=" + banco : "");
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

		function gerar() {
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
					  <div class="form-group" style="margin-right: 10px">
					      <label for="txt-pesquisar-ano" class="control-label">Ano</label>
				          <input id="txt-pesquisar-ano" class="form-control mask-year" value="${ano}" />
					  </div> 
					
					  <div class="form-group" style="margin-right: 10px">
					      <label for="txt-pesquisar-mes" class="control-label">Mês</label>
				          <input id="txt-pesquisar-mes" class="form-control mask-month" value="${mes}" />
					  </div>

				     <div class="form-group">
				         <label for="banco" class="control-label">Banco</label>
			             <select id="banco-id" class="form-control">
			             	<option <c:if test="${bancoId == 0}">selected</c:if> value="0">Todos</option>
			             	<option <c:if test="${bancoId < 0}">selected</c:if> value="-1">Sem banco cadastrado</option>
			             	<c:forEach items="${bancos}" var="banco">
				             	<option <c:if test="${banco.id == bancoId}">selected</c:if> value="${banco.id}">${banco.nome}</option>
		             	  	</c:forEach>
				         </select>
				     </div>
				      
				     <div class="form-group"> 
				         <label for="banco" class="control-label">&nbsp;</label>
				        <br> 
    	  		  		<button id="btn-filtrar" class="btn btn-sm btn-primary" onclick="filtrar()" style="margin-left: 10px;  height: 35px">Filtrar</button>
				     </div>
				  </div> 

			        	  			
   	  			<c:if test="${isAdministracao}">					          
	       	  		<div class="form-group col-sm-12">	
	       	  			<form method="POST" id="form-integracao-robo" action="<c:url value='integracaoRobo'/>" enctype="multipart/form-data"> 				
						    <input class="btn btn-sm btn-primary float-left" type="file" style="height: 35px; margin: 1px" name="file" id="integracao-robo" />
						    <input class="btn btn-sm btn-primary float-left" type="button" style="height: 35px; margin: 1px; font-size: 10pt" value="Integrar dados robô" onclick="integracaoRobo()" />			        	  				
						</form>
		  			</div>	 
      	  		</c:if>

			     
				  <c:if test="${isFinanceiro}">
				  	
					  <div class="btn-group" role="group" style="margin-left: 20px">
						  <button type="button" class="btn btn-sm btn-primary" onclick="gerar()">Gerar CSV</button>
						  <div class="btn-group" role="group">
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
					    </div> 
				  </c:if> 
			  </c:if>
       	  		
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
									  	<b>Horas:</b> ${item.totalHoras}<br>
										<b>Valor:</b> ${item.totalValor}
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
											      <label class="control-label ${styleStatusAprovador}">${item2.nome} | <b>${item2.doubleValue} horas</b></label>					          
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

                
         </div> 

    </div>
    
</body>

</html>