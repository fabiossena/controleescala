<!DOCTYPE html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page session="true"%>
<head>
<title>Ixia - Sistema de Escala - Dashboard projeto</title>
<jsp:include page="../shared/headerPartialView.jsp" />
<script type="text/javascript">
    $(document).ready(function() {
    	//$("body").css('overflow', 'hidden');
    	$("#projeto").change(function() {
    		selectProjeto(this.value)
    	});
    	
    	$("#escala").change(function() {
    		selectEscala(this.value)    		
    	});
    	
    	$("#anterior").click(function() {
        	window.location.href = ajustarUrl("<c:url value='/dashboard' />/${projeto.id}?mes=${mesAnterior}&ano=${anoAnterior}");	
    	});

    	
    	$("#proximo").click(function() {
        	window.location.href = ajustarUrl("<c:url value='/dashboard' />/${projeto.id}?mes=${mesProximo}&ano=${anoProximo}");
    	});


    	if (window.location.href.indexOf("selecionarReposicao")>0 ||
   			window.location.href.indexOf("card-reposicao")>0) {
        	$("#card-reposicao" + $("#selecionarReposicao").val()).show();
        	if ($("#selecionarReposicao").val()!=null) {
        		window.location = "#card-reposicao" + $("#selecionarReposicao").val();
        	}
    	}
    	else{
        	$("#card-solicitacao" + $("#selecionar").val()).show();
        	if ($("#selecionar").val()!=null) {
    			window.location = "#card-solicitacao" + $("#selecionar").val();
        	}
    	}
    	
    	//$("#selecionar").closest("td").css("background-color", "red");
    	$(".selecionar").closest("td").addClass("bg-light");
    });

    function selectProjeto(projeto){
    	window.location.href = ajustarUrl("<c:url value='/dashboard' />/" + projeto + "?mes=${mes}&ano=${ano}&escala=0");
    }
    
    function selectEscala(escala){
    	window.location.href = ajustarUrl("<c:url value='/dashboard' />/${projeto.id}?mes=${mes}&ano=${ano}&escala=" + escala);
    }
    
    function ajustarUrl(url){
    	var urlBase = window.location.href;
    	if (urlBase.indexOf("?") < 1) {
    		return url;
    	}
    	
    	var parametrosString = urlBase.split("?")[1];
    	var parametrosArray = parametrosString.split("&");
    	for(var i=0; i<parametrosArray.length; i++) {
    		var parametro = parametrosArray[i];
    		var variavel = parametro.split("=")[0];
    		if (url.indexOf(variavel+"=")<1){
    			url += "&"+parametro
    		}
    	}
    	
    	return url
    }
    
    </script>
</head>
<body>
	<jsp:include page="../shared/navbarPartialView.jsp" />



	<c:set var="selecionarSolicitacao">1</c:set>
	<c:set var="selecionarReposicao">1</c:set>
	
	<div style="border: 0; height: 100%">

		<fmt:parseDate pattern="yyyy-MM-dd" value="${data}"
			var="dataFormatada" />

		<div class="border-bottom" style="padding: 20px 20px 20px 20px"
			style="widht: 100%">

			<span class="row">
				<h3>
					Dashboard projeto (
					<fmt:formatDate value="${dataFormatada}" pattern="MMM/yyyy" />
					)${isAdmistracao}
				</h3>
			</span>

			<div class="form-group row" style="vertical-align: bottom">
				<form:select 
					path="projeto.id" 
					id="projeto" 
					class='form-control'
					style="width: 550px;margin: 1px 15px 1px 15px">
					<c:forEach items="${projetos}" var="item">
						<option <c:if test="${item.id == projeto.id}">selected</c:if>
							value="${item.id}">${item.nome}(${item.dataInicio}
							<c:if test="${item.dataFim != null}"> até ${item.dataFim}</c:if>)
							<c:if test="${!item.ativo}">
								<i>desativado</i>
							</c:if></option>
					</c:forEach>
				</form:select>
				<span
					style="vertical-align: center <c:if test="${isAtendimento}">;display:none;</c:if>">
					<a class="btn btn-sm btn-primary" style="margin: 1px 1px 1px 15px"
					href="<c:url value='/projeto' />/${projeto.id}"> <span
						class="glyphicon glyphicon-wrench" aria-hidden="true"></span> ver
						configurações projeto
				</a>
				</span>
			</div>

			<button 
				id="anterior"
				class="btn btn-sm btn-primary" 
				style="margin: 1px">mês anterior</button>
			<button class="btn btn-sm btn-primary" id="proximo"
				style="margin: 1px">mês próximo</button>

		</div>
		<div>

			<table style="font-size: 10pt" id="table-dashboard"
				class="display tabela-simples">
				<!-- Header Table -->
				<thead>
					<tr>
						<th><div style="width: 150px">Nome</div></th>
						<th class="border-left"><div style="width: 150px">Observações</div></th>
						<c:forEach items="${diasMes}" var="dia">
							<th>
								<div style="width: 120px">${dia.descricao}</div> <span
								class="fixar-x badge badge-primary"
								style="vertical-align: top; display: none; position: absolute; font-size: 9pt">${dia.descricao}</span>
							</th>
						</c:forEach>

					</tr>
				</thead>
				<tbody>

					<c:forEach items="${escalas}" var="escala">
						<tr>
							<th style="vertical-align: top; font-size: 11pt">
								<span
									class="fixar badge badge-primary"
									style="vertical-align: top; display: none; position: absolute; margin-top: -13px; font-size: 9pt">${escala.descricaoEscala}</span>
									Escala: ${escala.descricaoEscala}<c:if test="${!escala.ativo}">
									<i class="badge badge-danger">desativada</i>
								</c:if> 
								<br> 
								<c:if test="${escala.monitor.sexo=='M'}">Monitor</c:if>
								<c:if test="${escala.monitor.sexo=='F'}">Monitora</c:if> <c:if
									test="${escala.monitor.sexo==null || escala.monitor.sexo==''}">Monitor(a)</c:if>:
								${escala.monitor.nomeCompletoMatricula}</th>
							<th class="border-left"
								style="vertical-align: top; font-size: 11pt">Qtd
								(real/planejada):
								${escala.quantidadePrestadoresReal}/${escala.quantidadePrestadoresPlanejada}</th>
							<c:forEach items="${diasMes}" var="dia">
							
												
								<td class="border-left"
									style="font-size: 14pt; vertical-align: top">
									
									<%-- <c:if test="${dia.data >= escala.projeto.dataInicio && dia.data <= escala.projeto.dataFim}"> --%>
									<div class="badge badge-success">${escala.horaInicio}-
										${escala.horaFim}</div> <br> <c:if
										test="${escala.definidaPrioridade}">
										<c:if
											test="${dia.diaSemana >= escala.diaSemanaDePrioridadeId && dia.diaSemana <= escala.diaSemanaAtePrioridadeId}">
											<div class="badge badge-danger">Horário prioritario:
												${escala.horaInicioPrioridade} - ${escala.horaFimPrioridade}</div>
										</c:if>
									</c:if> <%-- </c:if>  --%>

									<c:set var="reposicoes" value="${escala.ausenciaReposicoes}" scope="request"/>
									<c:set var="solicitacaoId" value="${solicitacaoId}" scope="request"/>		
									<c:set var="dia" value="${dia}" scope="request"/>	
									<c:set var="selecionarReposicao" value="${selecionarReposicao}" scope="request"/>
									<jsp:include page="projetoDashboardReposicaoPartialView.jsp" />
										 				
										<c:forEach items="${horasTrabalhadas}" var="hora">

												<c:if test="${hora.projetoEscala.id == escala.id && 
														  hora.horaAprovacao.prestador.id == escala.monitor.id && 
														  hora.dataHoraInicio.dayOfMonth == dia.data.dayOfMonth}">
												<c:if test="${hora.dataHoraFim != null && hora.tipoAcao == 1}">
													<div class="badge badge-info" style="font-size: 10pt;">
														Trabalhado de ${hora.dataHoraInicio.toString().substring(11, 16)} até ${hora.dataHoraFim.toString().substring(11, 16)}
													</div>
												</c:if>
												<c:if test="${hora.dataHoraFim == null && hora.tipoAcao == 1}">
													<div class="badge badge-warning" style="font-size: 10pt;">
														Andamento - início ${hora.dataHoraInicio.toString().substring(11, 16)} (${hora.horas} horas)
													</div>
												</c:if>
												<c:if test="${hora.dataHoraFim == null && hora.tipoAcao == 2}">
													<div class="badge badge-warning" style="font-size: 10pt;">
														Pausada (${hora.motivoPausa}) - início ${hora.dataHoraInicio.toString().substring(11, 16)} (${hora.horas} horas)
													</div>
												</c:if>
											</c:if>
										</c:forEach>
												
								</td>
							</c:forEach>
						</tr>
					</c:forEach>

					<tr style="height: 50px">
						<td colspan="40">
							<h4 style="margin-top: 20px">Prestadores</h4>
							<div class="form-group">
								<form:select path="escala.id" id="escala" class='form-control'
									style="width: 550px">
									<option value="0">Selecione uma escala</option>
									<form:options items="${escalas}"
										itemLabel="descricaoCompletaEscala" itemValue="id" />
								</form:select>
							</div>
						</td>
					</tr>

					<c:forEach items="${prestadores}" var="item">
						<tr>
							<td style="vertical-align: top">${item.prestador.nomeCompletoMatricula}
								<span class="fixar"
								style="vertical-align: top; display: none; position: absolute; margin-top: -15px">
									<span style="font-size: 10pt" class=" badge badge-info">${item.prestador.nomeCompletoMatricula}</span><br>
									<span style="font-size: 10pt" class=" badge badge-info">${item.projetoEscala.descricaoEscala}</span>
							</span>
							</td>

							<td class="border-left" style="vertical-align: top">${item.observacaoPrestador.trim()}</td>

							<c:forEach items="${diasMes}" var="dia">
								<td class="border-left"
									style="font-size: 12pt; vertical-align: top">
									
									<c:if
										test="${dia.data >= item.dataInicio && (item.dataFim == null || dia.data <= item.dataFim)}">
										
										<div class="badge badge-success" style="font-size: 10pt;">${item.projetoEscala.horaInicio}
											- ${item.projetoEscala.horaFim}</div>

										<c:forEach items="${item.projetoFolgasSemanais}" var="folga">
											<c:if test="${folga.diaSemanaId == dia.diaSemana}">
												<br>
												<b>Folga programada</b>
												<br>
												<div class="badge badge-info" style="font-size: 10pt">${folga.motivo.nome}
													(${folga.horaInicio} - ${folga.horaFim})</div>
												<br>
											</c:if>
										</c:forEach>

										<c:set var="dia" value="${dia}" scope="request"/>	
										<c:set var="solicitacaoId" value="${solicitacaoId}" scope="request"/>	
										<c:set var="solicitacoes" value="${item.ausenciaSolicitacoes}" scope="request"/>
										<c:set var="selecionarReposicao" value="${selecionarReposicao}" scope="request"/>	
										<c:set var="selecionarSolicitacao" value="${selecionarSolicitacao}" scope="request"/>	
										<jsp:include page="projetoDashboardSolicitacaoPartialView.jsp" />


										<c:set var="reposicoes" value="${item.ausenciaReposicoes}" scope="request"/>
										<jsp:include page="projetoDashboardReposicaoPartialView.jsp" />	
										


									</c:if>
								
								
																		
									<c:forEach items="${horasTrabalhadas}" var="hora">
										
											<c:if test="${hora.projetoEscala.id == item.projetoEscala.id && 
													  hora.horaAprovacao.prestador.id == item.prestador.id && 
													  hora.dataHoraInicio.dayOfMonth == dia.data.dayOfMonth}">
											<c:if test="${hora.dataHoraFim != null && hora.tipoAcao == 1}">
												<div class="badge badge-info" style="font-size: 10pt;">
													Trabalhado de ${hora.dataHoraInicio.toString().substring(11, 16)} até ${hora.dataHoraFim.toString().substring(11, 16)}
												</div>
											</c:if>
											<c:if test="${hora.dataHoraFim == null && hora.tipoAcao == 1}">
												<div class="badge badge-warning" style="font-size: 10pt;">
													Andamento - início ${hora.dataHoraInicio.toString().substring(11, 16)} (${hora.horas} horas)
												</div>
											</c:if>
											<c:if test="${hora.dataHoraFim == null && hora.tipoAcao == 2}">
												<div class="badge badge-warning" style="font-size: 10pt;">
													Pausada (${hora.motivoPausa}) - início ${hora.dataHoraInicio.toString().substring(11, 16)} (${hora.horas} horas)
												</div>
											</c:if>
										</c:if>
									</c:forEach>
								
								</td>
							</c:forEach>
						</tr>

					</c:forEach>
				</tbody>
			</table>
		</div>
</body>

</html>