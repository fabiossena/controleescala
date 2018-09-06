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
		function filtrar() {
			var mes = $("#txt-pesquisar-mes").val();
			var ano = $("#txt-pesquisar-ano").val();
			if (mes == 0 || mes > 12){
				alert("Digite um mês válido")
				return;
			}
			
			if (ano < 2018 || ano > ${anoBase}){
				alert("Digite um ano válido")
				return;
			}
			
        	window.location.href = "<c:url value='/aprovacaoHoras' />?mes=" + mes + "&ano=" + ano;	
		}
		
		function aprovar(id){
			post(urlBase + "/horas/aprovar/" + id, true);
		}
		
		function recusar(id){
			if (!confirm("Deseja realmente recusar estas horas?")) {
				return;	
			}

			motivo = prompt("Digite aqui o motivo da recusa:")
			post(urlBase + "/horas/aprovar/" + id + "?aprovar=false&motivo=" + motivo, false);
			
		}
		
		function post(localUrl, aprovar) {

			console.log(localUrl); 
			$.ajax({
				type : "GET", 
				contentType : "application/json",
				url : localUrl,
				dataType: 'text',
				success : function(data) {
					$("#aprovar").hide();
					$("#recusar").hide();
				},
				error : function(e) {
					alert("Error!")
					console.log("ERROR: ", e);
				}
			});	
		}
		
		
	</script>
</head>
<body>

	<jsp:include page="../shared/navbarPartialView.jsp"/>

    <div class="container">  
        <div style="margin-top:50px" class="mainbox col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
       	  
			<h3>Horas aprovação</h3>
			<div class="container border-top panel-custom"> 
				  <c:if test="${!isAtendimento}">
				  <div class="form-group col-sm-12 row">
					  <div class="form-group" style="margin-right: 10px">
					      <label for="txt-pesquisar-ano" class="control-label">Ano</label>
				          <input id="txt-pesquisar-ano" class="form-control mask-year" value="${ano}" />
					  </div> 
					
					  <div class="form-group">
					      <label for="txt-pesquisar-mes" class="control-label">Mês</label>
				          <input id="txt-pesquisar-mes" class="form-control mask-month" value="${mes}" />
					  </div>
				  </div> 
    	  		  <button id="btn-filtrar" class="btn btn-primary float-left" onclick="filtrar()">Filtrar</button> 
				  </c:if>
       	  		
				<div class="table-container table-responsive" style="margin-top: 100px">
					<table id="tabela" class="display tabela-avancada">

				        <!-- Header Table -->
				        <thead>
				            <tr>
				                <th>Id</th>
								<th>Nome prestador</th>
				                <th>Data</th> 
				                <th>Horas</th>
				                <th>Valor</th>
				                <th>Detalhes</th>
				                <th>Ação</th>
				            </tr>
				        </thead>
				        <tbody>        
					        <c:forEach items="${aprovacaoHoras}" var="item">
					            <tr>
				                	<fmt:parseDate pattern="yyyy-MM-dd" value="${item.data}" var="data" /> 
											
					                <td>${item.id}</td>
					                <td>${item.prestador.nomeCompletoMatricula}</td>
					                <td><fmt:formatDate value="${data}" pattern="dd/MM/yyyy" /></td>
				                
								  	<td>${item.totalHoras}</td>
				                	<td>${item.totalValor}</td> 
				                							
								  	<%-- <td><c:if test="${item.totalHoras > 0}">${item.totalHoras}</c:if><c:if test="${item.totalHoras == 0}">${item.dadosAcessoAprovacaoHoras.totalHoras}</c:if></td>
				                	<td><c:if test="${item.totalValor > 0}">${item.totalValor}</c:if><c:if test="${item.totalValor == 0}">${item.dadosAcessoAprovacaoHoras.totalValor}</c:if></td> --%>
				                	<td style="font-size: 10pt" class="text-primary">
				                	
		                			<div style="width: 200px">
										  
										  <c:if test="${item.aceitePrestador == 0}">
										      <span class="text-primary"><b>Etapa 1:</b> (Pendente) Prestador</span>
										   </c:if>
										  <c:if test="${item.aceitePrestador == 1}">
										      <span class="text-success"><b>Etapa 1:</b> (Aprovado) Prestador</span>
										   </c:if>
										  <c:if test="${item.aceitePrestador == 2}">
										      <span class="text-danger"><b>Etapa 1:</b> (Recusado) Prestador</span>
										   </c:if>
									      <br />		
									   		   	  
							   	  
								          <c:forEach items="${item.dadosAcessoAprovacaoHoras.dadosAprovacao}" var="item2">
                			
								  			  <c:set var="styleStatusAprovador">
											  <c:if test="${item2.nome.contains('(Pendente') || item2.nome.contains('(Parcial')}">text-primary</c:if>
											  <c:if test="${item2.nome.contains('(Aprovado')}">text-success</c:if>
											  <c:if test="${item2.nome.contains('(Reprovado')}">text-danger</c:if>
											  </c:set>
											  
										      <br />		
										      <label class="control-label ${styleStatusAprovador}"><b>Etapa 2:</b> ${item2.nome} | <b>${item2.doubleValue} horas</b></label>					          
								          </c:forEach>
								          
								          <br>
								          <c:if test="${item.aceiteAprovador == 0}"><span class="text-primary"><b>Etapa 3:</b> (Pendente) Financeiro</span></c:if>
								          <c:if test="${item.aceiteAprovador == 1}"><span class="text-success"><b>Etapa 3:</b> (Aprovado) Financeiro | ${item.aprovador.nomeCompletoMatricula}</span></c:if>
								          <c:if test="${item.aceiteAprovador == 2}"><span class="text-danger"><b>Etapa 3:</b> (Recusado) Financeiro (${item.motivoRecusaAprovador}) | ${item.aprovador.nomeCompletoMatricula}</span></c:if>
								          <c:if test="${item.aceiteAprovador == 3}"><span class="text-success"><b>Etapa 3:</b> (Finalizado) Financeiro | ${item.aprovador.nomeCompletoMatricula}</span></c:if>
										</div>
				                	</td>
	                				<td>
	                					<a  class="btn btn-sm btn-primary" 
	                						href="<c:url value='/horasTrabalhadas' />/${item.id}"
	                						style="margin: 1px">ver	</a> 
									     <c:if test="${isFinanceiro}"> 
									     <c:if test="${item.aceitePrestador == 1 && item.dadosAcessoAprovacaoHoras.aprovado == 1}">
										     <c:if test="${item.aceiteAprovador != 1}"> 
			                					<button  class="btn btn-sm btn-primary" 
			                						id="aprovar"
			                						onclick="aprovar(${item.id})" 
			                						style="margin: 1px">aprovar</button>
										     </c:if>
										     <c:if test="${item.aceiteAprovador != 1}"> 
			                					<button  class="btn btn-sm btn-danger" 
			                						id="recusar"
			                						onclick="recusar(${item.id})"
			                						style="margin: 1px">recusar</button>
										     </c:if>
									     </c:if>
									     </c:if>
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