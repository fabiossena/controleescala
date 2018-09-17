<!DOCTYPE html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page session="true"%>

<head>
	<title>Ixia - Sistema de Escala - Cadastro ausencias</title>
    <jsp:include page="../shared/headerPartialView.jsp"/>
    <script>
	 function apagarSolicitacao(id) {
		if (confirm("Deseja realmente apagar esta solicitação de ausência?")) {
			window.location.href = urlBase + "ausencia/delete/" + id;
		}
		else{
			return false;
		}
	 }

		function aceitaRecusaSolicitacaoAusencia(id, aceita, motivo, origem) {

			if (!aceita){
				motivo = prompt("Digite aqui o motivo da recusa:")
				if (motivo == "" || motivo == null){
					alert("Preencha o campo observação com o motivo da recusa");
					return;
				}
			}
			
			if (confirm("Deseja realmente " + (aceita ? "aceitar" : "recusar") + " esta solicitação?")) {
				window.location.href = urlBase + "ausencia/aceita/" + id + "?origem=" + origem + "&aceita=" + aceita + (motivo == null || motivo == "" ? "" :  "&motivo=" + motivo);
			}	
		}
    </script>
</head>

<body>

	<jsp:include page="../shared/navbarPartialView.jsp"/>

    <div class="container">    
	        <div style="margin-top:50px" class="mainbox col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
        	  
				<h3>Solicitações ausência</h3>
				<div class="container border-top panel-custom"> 
					<c:if test="${isAdministracao || isAtendimento}">
	        	  		<a id="btn-novo" class="btn btn-primary" href="<c:url value='/ausencia' />">Nova</a>
        	  		</c:if>
        	  		<br />
					<br />
					<div class="table-container table-responsive">
						<table id="tabelaAusencias" class="display tabela-avancada">
					      
					       <!-- Header Table -->
					     <thead>
					            <tr>
					                <th>Ação</th>
					                <th>Id</th>
					                <th>Projeto escala</th>
									<th>Nível acesso</th>
									<th>Solicitante</th>
									<th>Data</th> 
									<th>Horário</th>
					                <th>Status</th>
				                	<th>Motivo</th>
									<th>Tipo ausência</th>
									<th>Dados adicionais</th>
									<th>Dados reposição</th>
					            </tr>
					        </thead>
					        <tbody>        
						        <c:forEach items="${solicitacoes}" var="solicitacao">
						            <tr>
		                				<td>

		                					<a class="btn btn-sm btn-primary" href="<c:url value='/ausencia' />/${solicitacao.id}" style="margin: 1px">ver</a>		                					
		                				
					           				<a class="btn btn-sm btn-primary float-right" target="_blank" style="margin: 1px" href="<c:url value='/dashboard' />/${solicitacao.projetoEscala.projetoId}?aceito=0&solicitacao=${solicitacao.id}&ano=${solicitacao.dataInicio.year}&mes=${solicitacao.dataInicio.monthValue}#selecionar">dashboard</a>
		                					
		                					<c:if test="${isAdministracao || solicitacao.usuario.id == usuarioLogado.id}">
			                					<c:if test="${(solicitacao.ativo == 0 || solicitacao.ativo == 2)}">	
			                						<input onclick="apagarSolicitacao(${solicitacao.id})" id="btn-apagar-reposicao" type="button" class="btn btn-sm btn-danger" value="apagar" style="margin: 1px" />
			                						</c:if>
		                					</c:if>											  
										    <c:if test="${solicitacao.dadosAcesso.visivelAprovacao}">
								     	  		<c:if test="${solicitacao.dadosAcesso.aceitePrincipal == 0 || solicitacao.dadosAcesso.aceitePrincipal == 2}">
													<input id="btn-aprovar-reposicao" type="button" onclick="aceitaRecusaSolicitacaoAusencia(${solicitacao.id}, true, '', 2)" class="btn btn-sm btn-success" value="Aceitar" style="margin: 1px" />
								      	  		</c:if>
								    			<c:if test="${solicitacao.dadosAcesso.aceitePrincipal == 0 || solicitacao.dadosAcesso.aceitePrincipal == 1}">
													<input id="btn-recusar-reposicao" type="button" onclick="aceitaRecusaSolicitacaoAusencia(${solicitacao.id}, false, '', 2)" class="btn btn-sm btn-danger" value="Recusar" style="margin: 1px" /> 
												</c:if>											  
								     	  	  </c:if> 
											
											 
		                				</td>
						                <td>${solicitacao.id}</td>
						                <td>${solicitacao.projetoEscala.projeto.nome}(${solicitacao.projetoEscala.projetoId}) - ${solicitacao.projetoEscala.descricaoEscala}(${solicitacao.projetoEscala.id})</td>
						                <td>
							                			        
										   <c:forEach items="${solicitacao.dadosAcesso.dadosAcesso}" var="acesso">
						        	  			<c:if test='${!acesso.nome.isEmpty()}'>
											  	 	<div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
						  							  	${acesso.nome}
						  							  </div><br>
					  							 </c:if>
										   </c:forEach>
										  
							                
					                	</td>
						                <td>${solicitacao.usuario.nomeCompletoMatricula}</td>
										<td>${solicitacao.dataInicio} <c:if test="${solicitacao.dataFim != null}"> - ${solicitacao.dataFim}</c:if></td>
										<td>${solicitacao.horaInicio} - ${solicitacao.horaFim} <c:if test="${solicitacao.horas != ''}">(${solicitacao.horas}/dia)</c:if></td>
						                <td>
						                	<c:if test="${solicitacao.aceito==0}">Pendente</c:if>
						                	<c:if test="${solicitacao.aceito==1}">Aceita</c:if>
						                	<c:if test="${solicitacao.aceito==2}">Recusada</c:if>
					                	</td>
					                	<td>${solicitacao.motivoAusencia.nome}</td>										
						                <td>
						                		                
						                	<c:if test="${solicitacao.tipoAusencia == 0}">Simples</c:if>                
						                	<c:if test="${solicitacao.tipoAusencia == 1}">Horário colocado a disposição</c:if>
						                	<c:if test="${solicitacao.tipoAusencia == 2}">Indicado outro horário/usuário</c:if>
					                    </td>
					                	<td style="font-size: 10pt"><p style="width: 200px">
					                			
											<c:if test="${solicitacao.observacao != ''}">Observação: ${solicitacao.observacao}</c:if>
												
											  <c:forEach items="${solicitacao.dadosAcesso.dadosAprovacao}" var="aprovacao">
											  	<c:if test='${aprovacao.nome.contains("Pendente") || aprovacao.nome.contains("Não enviada")}'>
												  	<div class="text-primary col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" style="font-size: 10pt">
				    	  							  	${aprovacao.nome}
				    	  							  </div><br>
											  	</c:if>
											  	<c:if test='${aprovacao.nome.contains("Aprovado")}'>
												  	<div class="text-success col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" style="font-size: 10pt">
				    	  							  	${aprovacao.nome}
				    	  							  </div><br>
											  	</c:if>
											  	<c:if test='${aprovacao.nome.contains("Recusado")}'>
												  	<div class="text-danger col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" style="font-size: 10pt">
				    	  							  	${aprovacao.nome}
				    	  							  </div><br>
											  	</c:if>
											  </c:forEach>						                	
					                	

					                	</p></td>	
					                	<td style="font-size: 10pt"><p style="width: 200px">
					                	
										  	<c:if test='${solicitacao.tipoAusencia == 1}'>
										  	Horário a disposição
										  	</c:if>
										  	<c:if test='${solicitacao.tipoAusencia == 2}'>
											  <c:forEach items="${solicitacao.ausenciaReposicoes}" var="reposicao">			    	  							  
												  	<c:if test='${reposicao.indicadoOutroUsuario}'>
													  	<div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" style="font-size: 10pt">
					    	  							  	<b>${reposicao.usuarioTroca.nomeCompletoMatricula}</b>
					    	  							  </div>
												  	</c:if> 
												  	<fmt:parseDate pattern="yyyy-MM-dd" value="${reposicao.data}" var="data" />
											  		<div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" style="font-size: 10pt">
				    	  							  	<b><fmt:formatDate value="${data}" pattern="dd/MM/yyyy" /> | ${reposicao.horaInicio} - ${reposicao.horaFim}  <c:if test="${reposicao.horas != ''}">(${reposicao.horas})</c:if></b>
				    	  							 </div>
												  	<div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" style="font-size: 10pt">
				    	  							  	<i>Projeto/escala: ${reposicao.projetoEscalaTroca.descricaoCompletaEscala}</i>
				    	  							  	<c:if test="${reposicao.observacao != ''}">Observação: ${reposicao.observacao}</c:if>
				    	  							 </div>	
												  	<br>
											  </c:forEach>		
										  	</c:if>

					                	</p></td>
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