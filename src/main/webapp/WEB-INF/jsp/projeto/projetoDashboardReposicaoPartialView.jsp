<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page session="true"%>


<c:forEach items="${reposicoes}" var="reposicao">
	<c:if test="${reposicao.data == dia.data}"> 
	
		<br>
		<b>Reposição solicitada</b>

		<button style="font-size: 10pt; font-weight: bold"
			class="btn btn-sm btn-warning"
			onclick="$(this.nextElementSibling).toggle();"
			style="margin: 1px">detalhes</button>

		<div
			id="card-reposicao${reposicao.ausenciaSolicitacao.id}"
			class="card bg-light" style="position: absolute; display: none">
			<div class="card-body">

				<b>Solicitação recebida</b> <a target="_blank"
					style="font-size: 10pt; font-weight: bold; margin: 1px"
					class="btn btn-sm btn-warning"
					href="<c:url value='/ausencia' />/${reposicao.ausenciaSolicitacao.id}">ver</a>

				<div style="font-size: 10pt;">
					<b>Status: </b>
					<c:if test='${reposicao.ausenciaSolicitacao.aceito == 0}'>Pendente</c:if>
					<c:if test='${reposicao.ausenciaSolicitacao.aceito == 1}'>Em aprovação</c:if>
					<c:if test='${reposicao.ausenciaSolicitacao.aceito == 2}'>Recusada</c:if>
					<c:if test='${reposicao.ausenciaSolicitacao.aceito == 3}'>Aprovada</c:if>
					<c:if test='${reposicao.ausenciaSolicitacao.aceito == 4}'>Finalizada</c:if>
				</div>

				<c:if
					test='${reposicao.ausenciaSolicitacao.tipoAusencia != 0}'>

					<c:if test='${reposicao.indicadoOutroUsuario}'>
						<div style="font-size: 10pt">
							<b>Solicitada para:</b>
							${reposicao.usuarioTroca.nomeCompletoMatricula}
						</div>
					</c:if>
					
					<fmt:parseDate pattern="yyyy-MM-dd" value="${reposicao.data}" var="data" />
					<div class="badge badge-info" style="font-size: 10pt">
						<fmt:formatDate value="${data}" pattern="dd/MM/yyyy" />
						| ${reposicao.horaInicio} - ${reposicao.horaFim}
						<c:if test="${reposicao.horas != ''}">(${reposicao.horas})</c:if>
					</div>

					<div style="font-size: 10pt">
						<b>Projeto/escala: </b> <a target="_blank"
							href="<c:url value='/projeto' />/${reposicao.projetoEscalaTroca.projetoId}">
							<i>${reposicao.projetoEscalaTroca.descricaoCompletaEscala}</i>
						</a>
						<c:if test="${reposicao.observacao != ''}">Observação: ${reposicao.observacao}</c:if>
					</div>


				</c:if>



				<div
					style="margin-top: 10px; font-size: 12pt; font-weight: bold">Dados
					solicitação</div>

				<div style="font-size: 10pt" class="badge badge-info">${reposicao.ausenciaSolicitacao.motivoAusencia.nome}
					(${reposicao.ausenciaSolicitacao.horaInicio} -
					${reposicao.ausenciaSolicitacao.horaFim} |
					${reposicao.ausenciaSolicitacao.horas}/dia)</div>

				<div style="font-size: 10pt">
					<b>Solicitada por: </b>
					${reposicao.ausenciaSolicitacao.usuario.nomeCompletoMatricula}
				</div>

				<b style="font-size: 10pt">Solicitação projeto/escala: </b> <a
					target="_blank" style="font-size: 10pt"
					href="<c:url value='/dashboard' />/${reposicao.ausenciaSolicitacao.projetoEscala.projetoId}?mes=${reposicao.ausenciaSolicitacao.dataInicio.monthValue}&ano=${reposicao.ausenciaSolicitacao.dataInicio.year}&escala=${reposicao.ausenciaSolicitacao.projetoEscala.id}&solicitacao=${reposicao.ausenciaSolicitacao.id}&aceito=0#selecionar">
					<i>${reposicao.ausenciaSolicitacao.projetoEscala.descricaoCompletaEscala}</i>
				</a> 				
				

				<fmt:parseDate pattern="yyyy-MM-dd"
					value="${reposicao.ausenciaSolicitacao.dataInicio}"
					var="dtIni" />					
				<c:set var="dataInicio">
					<fmt:formatDate value="${dtIni}" pattern="dd/MM/yyyy" />
				</c:set>				
				<c:if
					test="${reposicao.ausenciaSolicitacao.dataFim == null}">
					<div style="font-size: 10pt">
						<b style="font-size: 10pt;">Data: </b>${dataInicio}</div>
				</c:if>				
				<c:if
					test="${reposicao.ausenciaSolicitacao.dataFim != null}">

					<fmt:parseDate pattern="yyyy-MM-dd"
						value="${reposicao.ausenciaSolicitacao.dataFim}"
						var="dtFim" />
					<c:set var="dataFim">
						<fmt:formatDate value="${dtFim}" pattern="dd/MM/yyyy" />
					</c:set>
					<div style="font-size: 10pt">De ${dataInicio} até ${dataFim}</div>

				</c:if>
				
				
				<br>
			</div>
		</div>

		<div style="font-size: 10pt;">
			<b>Status: </b>
			<c:if test='${reposicao.ausenciaSolicitacao.aceito == 0}'>Pendente</c:if>
			<c:if test='${reposicao.ausenciaSolicitacao.aceito == 1}'>Em aprovação</c:if>
			<c:if test='${reposicao.ausenciaSolicitacao.aceito == 2}'>Recusada</c:if>
			<c:if test='${reposicao.ausenciaSolicitacao.aceito == 3}'>Aprovada</c:if>
			<c:if test='${reposicao.ausenciaSolicitacao.aceito == 4}'>Finalizada</c:if>
		</div>
				
		<c:if
			test='${reposicao.ausenciaSolicitacao.tipoAusencia != 0}'>
				<fmt:parseDate pattern="yyyy-MM-dd"
				value="${reposicao.data}" var="data" />
			
			<c:if test='${reposicao.indicadoOutroUsuario}'>
				<div style="font-size: 10pt">
					<b>Solicitada para:</b>
					${reposicao.usuarioTroca.nomeCompletoMatricula}
				</div>
			</c:if>
			
			<c:if test='${!reposicao.indicadoOutroUsuario}'>
				<div style="font-size: 10pt">
					<b>Solicitada por:</b>
					${reposicao.ausenciaSolicitacao.usuario.nomeCompletoMatricula}
				</div>
			</c:if>
			
			<div class="badge badge-info" style="font-size: 10pt">
				<fmt:formatDate value="${data}" pattern="dd/MM/yyyy" />
				| ${reposicao.horaInicio} - ${reposicao.horaFim}
				<c:if test="${reposicao.horas != ''}">(${reposicao.horas})</c:if>
			</div>
			
		</c:if>

		<c:if
			test="${solicitacaoId == reposicao.ausenciaSolicitacao.id}">
			<input type="hidden"
				<c:if test="${selecionarReposicao == 1}">id="selecionarReposicao"</c:if>
				value="${reposicao.ausenciaSolicitacao.id}" />


			<%-- <div <c:if test="${selecionarSolicitacao == 1}">id="selecionar"</c:if> class="alert alert-xl alert-danger selecionar" style="margin: 5px 0 5px 0">Reposição selecionada</div> --%>
			<c:set var="selecionarReposicao">0</c:set>
		</c:if>
		
				
	</c:if>								
</c:forEach>
		