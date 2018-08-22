<!DOCTYPE html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page session="true"%>
<head>
	<title>Ixia - Sistema de Escala - Dashboard projeto</title>
    <jsp:include page="../shared/headerPartialView.jsp"/>
    <script type="text/javascript">
    $(document).ready(function() {
    	//$("body").css('overflow', 'hidden');
    	$("#projeto").change(function() {
    		selectProjeto(this.value)
    	});
    	
    	$("#escala").change(function() {
    		selectEscala(this.value)    		
    	});
    	
    	//$("#selecionar").closest("td").css("background-color", "red");
    	$(".selecionar").closest("td").addClass("bg-light");
    });

    function selectProjeto(projeto){
    	window.location.href = "<c:url value='/dashboard' />/" + projeto + "?mes=${mes}&ano=${ano}&escala=0";
    }
    function selectEscala(escala){
    	window.location.href = "<c:url value='/dashboard' />/${projeto.id}?mes=${mes}&ano=${ano}&escala=" + escala;
    }
    </script>
</head>
<body>
	<jsp:include page="../shared/navbarPartialView.jsp"/>
  
    <div style="border: 0;height: 100%">    
    
   <fmt:parseDate pattern="yyyy-MM-dd" value="${data}" var="dataFormatada" />
    
	<div class="border-bottom" style="padding: 20px 20px 20px 20px" style="widht: 100%">  
	
		<span class="row">
			<h3>Dashboard projeto (<fmt:formatDate value="${dataFormatada}" pattern="MMM/yyyy" />)${isAdmistracao}</h3>
		</span>
 
   	  	<div class="form-group row" style="vertical-align: bottom">
          <form:select path="projeto.id" id="projeto"  class='form-control' style="width: 550px;margin: 1px 15px 1px 15px">
	          <c:forEach items="${projetos}" var="item">
	          	<option <c:if test="${item.id == projeto.id}">selected</c:if> value="${item.id}">${item.nome} (${item.dataInicio}<c:if test="${item.dataFim != null}"> até ${item.dataFim}</c:if>) <c:if test="${!item.ativo}"> <i>desativado</i></c:if></option>
	          </c:forEach>
          </form:select>
          <span style="vertical-align: center <c:if test="${isAtendimento}">;display:none;</c:if>">
          	<a class="btn btn-sm btn-primary" style="margin: 1px 1px 1px 15px" href="<c:url value='/projeto' />/${projeto.id}">
          	<span class="glyphicon glyphicon-wrench" aria-hidden="true"></span> ver configurações projeto</a></span>
	    </div>
	    
		<a class="btn btn-sm btn-primary" style="margin: 1px" href="<c:url value='/dashboard' />/${projeto.id}?mes=${mesAnterior}&ano=${anoAnterior}&escala=${escala.id}">mês anterior</a>
		<a class="btn btn-sm btn-primary" style="margin: 1px" href="<c:url value='/dashboard' />/${projeto.id}?mes=${mesProximo}&ano=${anoProximo}&escala=${escala.id}">mês próximo</a>
		
	</div>
		<div>  
	
		<table style="font-size: 10pt" id="table-dashboard" class="display tabela-simples">
	       <!-- Header Table -->
	       <thead>
	            <tr>
	                <th><div style="width: 150px">Nome</div></th> 
	                <th class="border-left"><div style="width: 150px">Observações</div></th>
			     	<c:forEach items="${diasMes}" var="dia">
		            	<th>
		            		<div style="width: 120px">${dia.descricao}</div>	 	
							<span class="fixar-x badge badge-primary" style="vertical-align: top; display: none; position: absolute; font-size: 9pt">${dia.descricao}</span>	   
						</th>  
			     	</c:forEach>

	            </tr>
	        </thead>
	        <tbody>

	     	<c:forEach items="${escalas}" var="escala">
	            <tr>
					<th style="vertical-align: top; font-size: 11pt">
						<span class="fixar badge badge-primary" style="vertical-align: top; display: none; position: absolute; margin-top:-13px; font-size: 9pt">${escala.descricaoEscala}</span>
						Escala: ${escala.descricaoEscala}<c:if test="${!escala.ativo}"> <i class="badge badge-danger">desativada</i></c:if> 
						<br>
						<c:if test="${escala.monitor.sexo=='M'}">Monitor</c:if>
						<c:if test="${escala.monitor.sexo=='F'}">Monitora</c:if>
						<c:if test="${escala.monitor.sexo==null || escala.monitor.sexo==''}">Monitor(a)</c:if>: ${escala.monitor.nomeCompletoMatricula}
					</th>
					<th class="border-left" style="vertical-align: top; font-size: 11pt">Qtd (real/planejada): ${escala.quantidadePrestadoresReal}/${escala.quantidadePrestadoresPlanejada}</th> 
			     	<c:forEach items="${diasMes}" var="dia">
		            	<td class="border-left" style="font-size: 14pt; vertical-align: top">
		            		<%-- <c:if test="${dia.data >= escala.projeto.dataInicio && dia.data <= escala.projeto.dataFim}"> --%>
			            		<div class="badge badge-success">${escala.horaInicio} - ${escala.horaFim}</div> <br>
				            	<c:if test="${escala.definidaPrioridade}">
				            		<c:if test="${dia.diaSemana >= escala.diaSemanaDePrioridadeId && dia.diaSemana <= escala.diaSemanaAtePrioridadeId}">
				            			<div class="badge badge-danger">Horário prioritario: ${escala.horaInicioPrioridade} - ${escala.horaFimPrioridade}</div>
				            		</c:if>
				            	</c:if> 
			            	<%-- </c:if>  --%>
		            	</td>			     
			     	</c:forEach>
	   			</tr>
	     	</c:forEach>
	     
	     	<tr style="height: 50px">
		     	<td colspan="40">
		     		<h4 style="margin-top: 20px">Prestadores</h4>
		     	  	<div class="form-group">
			          <form:select path="escala.id" id="escala" class='form-control' style="width: 550px">
			          	<option value="0">Selecione uma escala</option>
           				<form:options items="${escalas}" itemLabel="descricaoCompletaEscala" itemValue="id" />
			          </form:select>
				    </div>
		     	</td>
	     	</tr>
	     	
	     	
	     	<c:set var="selecionarSolicitacao">1</c:set>
			     	
	     	<c:forEach items="${prestadores}" var="item"> 
	            <tr> 
					<td style="vertical-align: top">${item.prestador.nomeCompletoMatricula}
						<span class="fixar" style="vertical-align: top; display: none; position: absolute; margin-top:-15px">
							<span style="font-size: 10pt" class=" badge badge-info">${item.prestador.nomeCompletoMatricula}</span><br> 
							<span style="font-size: 10pt" class=" badge badge-info">${item.projetoEscala.descricaoEscala}</span>
						</span>
					</td>
					
					<td class="border-left" style="vertical-align: top">${item.observacaoPrestador.trim()}</td>
					
			     	<c:forEach items="${diasMes}" var="dia">
	            		<td  class="border-left" style="font-size: 12pt; vertical-align: top">
			            	<c:if test="${dia.data >= item.dataInicio && (item.dataFim == null || dia.data <= item.dataFim)}">
			            		
			            		<div class="badge badge-success">${item.projetoEscala.horaInicio} - ${item.projetoEscala.horaFim}</div>
						     	
						     	<c:forEach items="${item.projetoFolgasSemanais}" var="folga">
					            	<c:if test="${folga.diaSemanaId == dia.diaSemana}"> 
			            				<br><div class="badge badge-info" style="font-size: 10pt">Folga programada:</div><br>
					            		<div class="badge badge-info" style="font-size: 10pt">${folga.motivo.nome} (${folga.horaInicio} - ${folga.horaFim})</div><br>
					            	</c:if>     
						     	</c:forEach>





               	  			  	
						     	<c:forEach items="${item.ausenciaSolicitacoes}" var="solicitacao">
						     	 
						     		<c:if test="${(solicitacao.dataFim == null ? solicitacao.dataInicio == dia.data : dia.data <= solicitacao.dataFim && dia.data >= solicitacao.dataInicio) }">
						     			
	                	  	 			<c:if test="${solicitacaoId == solicitacao.id}">
												<div <c:if test="${selecionarSolicitacao == 1}">id="selecionar"</c:if> class="alert alert-danger selecionar" style="font-size: 12pt; width: 100%; margin: 5px 0 5px 0">Folga selecionada</div>
											<c:set var="selecionarSolicitacao">0</c:set>											
										</c:if>	  	
				            			 
							     		<br><b>Folga solicitada</b> <a target="_blank" style="font-size: 10pt;font-weight: bold" class="btn btn-sm btn-warning" href="<c:url value='/ausencia' />/${solicitacao.id}" style="margin: 1px">ver</a>
							     		
							     		<div style="font-size: 10pt;">
							     			<b>Status: </b>
										  	<c:if test='${solicitacao.aceito == 0}'>Pendente</c:if>
										  	<c:if test='${solicitacao.aceito == 1}'>Em aprovação</c:if>
										  	<c:if test='${solicitacao.aceito == 2}'>Recusado</c:if>
										  	<c:if test='${solicitacao.aceito == 3}'>Aprovado</c:if>
										  	<c:if test='${solicitacao.aceito == 4}'>Finalizado</c:if> 
			            				</div>
			            										
			            				<div class="badge badge-info" style="font-size: 10pt">${solicitacao.motivoAusencia.nome} (${solicitacao.horaInicio} - ${solicitacao.horaFim} | ${solicitacao.horas}/dia)</div>
			            				
				            			<c:if test="${solicitacao.dataFim != null}">
						            	  	<fmt:parseDate pattern="yyyy-MM-dd" value="${solicitacao.dataInicio}" var="dtIni" />
				                	  	  	<c:set var="dataInicio"><fmt:formatDate value="${dtIni}" pattern="dd/MM/yyyy" /></c:set>
									      	<fmt:parseDate pattern="yyyy-MM-dd" value="${solicitacao.dataFim}" var="dtFim" />
				                	  	  	<c:set var="dataFim"><fmt:formatDate value="${dtFim}" pattern="dd/MM/yyyy" /></c:set>
					            			<div style="font-size: 10pt">De ${dataInicio} até ${dataFim}</div>
				            			</c:if> 
				            			
				            			<c:if test='${solicitacao.tipoAusencia == 1}'>
									  	<div style="font-size: 10pt;font-weight: bold">Horário a disposição:</div>
									  											  	
									  	</c:if>
									  	<c:if test='${solicitacao.tipoAusencia == 2 && solicitacao.ausenciaReposicoes != null && solicitacao.ausenciaReposicoes.size() > 0}'>
									  		
									  		<div style="font-size: 12pt;font-weight: bold; margin-top: 10px">Dados reposição</div>
										  	
										  	<c:forEach items="${solicitacao.ausenciaReposicoes}" var="reposicao"> 
											  	<
											  	fmt:parseDate pattern="yyyy-MM-dd" value="${reposicao.data}" var="data" />
										  		<div style="font-size: 10pt">
			    	  							  	<fmt:formatDate value="${data}" pattern="dd/MM/yyyy" /> | ${reposicao.horaInicio} - ${reposicao.horaFim}  <c:if test="${reposicao.horas != ''}">(${reposicao.horas})</c:if>
			    	  							 </div>
			    	  							 			    	  							  
											  	<c:if test='${reposicao.indicadoOutroUsuario}'>
												  	<div style="font-size: 10pt">
				    	  							  	<b>Nome prestador troca: </b>${reposicao.usuarioTroca.nomeCompletoMatricula}
				    	  							  </div>
											  	</c:if>
											  	
											  	<div style="font-size: 10pt">
											  		<b>Projeto/escala: </b>			    	  							  	
											  		<a  target="_blank"
												  		href="<c:url value='/dashboard' />/${reposicao.projetoEscalaTroca.projetoId}?mes=${reposicao.data.monthValue}&ano=${reposicao.data.year}&escala=${reposicao.projetoEscalaTroca.id}&solicitacao=${solicitacao.id}&aceito=0#selecionar">
														<i>${reposicao.projetoEscalaTroca.descricaoCompletaEscala}</i>
													</a>
			    	  							  	<c:if test="${reposicao.observacao != ''}">Observação: ${reposicao.observacao}</c:if>
			    	  							 </div>	
											  	<br>
										  </c:forEach>		
									  	</c:if>
										<br>
				            			 	     
				            		</c:if>					       
				            		
						     	</c:forEach>
						     	
						     	
						     	
						     	
						     	
						     	
						     	<c:forEach items="${item.ausenciaReposicoes}" var="reposicao">
						     	 
						     		<c:if test="${reposicao.data == dia.data}">
						     			
	                	  	 			<c:if test="${solicitacaoId == reposicao.ausenciaSolicitacao.id}">
											<div <c:if test="${selecionarSolicitacao == 1}">id="selecionar"</c:if> class="alert alert-xl alert-danger selecionar" style="margin: 5px 0 5px 0">Reposição selecionada</div>
											<c:set var="selecionarSolicitacao">0</c:set>											
										</c:if>	  	
				            			 
							     		<br><b>Reposição solicitada</b> 
							     		<a target="_blank" style="font-size: 10pt;font-weight: bold" class="btn btn-sm btn-warning" href="<c:url value='/ausencia' />/${reposicao.id}" style="margin: 1px">ver</a>
							     		
							     		<div style="font-size: 10pt">
							     			<b>Status: </b>
										  	<c:if test='${reposicao.ausenciaSolicitacao.aceito == 0}'>Pendente</c:if>
										  	<c:if test='${reposicao.ausenciaSolicitacao.aceito == 1}'>Em aprovação</c:if>
										  	<c:if test='${reposicao.ausenciaSolicitacao.aceito == 2}'>Recusado</c:if>
										  	<c:if test='${reposicao.ausenciaSolicitacao.aceito == 3}'>Aprovado</c:if>
										  	<c:if test='${reposicao.ausenciaSolicitacao.aceito == 4}'>Finalizado</c:if> 
			            				</div>


				            			
									  	<c:if test='${reposicao.ausenciaSolicitacao.tipoAusencia != 0}'>
			    	  							   
										  	<fmt:parseDate pattern="yyyy-MM-dd" value="${reposicao.data}" var="data" />
									  		<div class="badge badge-info" style="font-size: 10pt">
		    	  							  	<fmt:formatDate value="${data}" pattern="dd/MM/yyyy" /> | ${reposicao.horaInicio} - ${reposicao.horaFim}  <c:if test="${reposicao.horas != ''}">(${reposicao.horas})</c:if>
		    	  							 </div>
		    	  							 
										  	<c:if test='${reposicao.indicadoOutroUsuario}'>
											  	<div style="font-size: 10pt">
											  		<b>Nome prestador troca:</b>
			    	  							  	${reposicao.usuarioTroca.nomeCompletoMatricula}
			    	  							  </div>
										  	</c:if>
		    	  							 <%-- 
										  	<div style="font-size: 10pt">
										  		<b>Projeto/escala: </b>			    	  							  	
										  		<i>${reposicao.projetoEscalaTroca.descricaoCompletaEscala}</i>
		    	  							  	<c:if test="${reposicao.observacao != ''}">Observação: ${reposicao.observacao}</c:if>
		    	  							 </div>	
										  	<br> --%>

									  	</c:if>



										<div style="margin-top: 10px; font-size: 12pt;font-weight: bold">Dados solicitação</div>
										
										<fmt:parseDate pattern="yyyy-MM-dd" value="${reposicao.ausenciaSolicitacao.dataInicio}" var="dtIni" />
				                	  	<c:set var="dataInicio"><fmt:formatDate value="${dtIni}" pattern="dd/MM/yyyy" /></c:set>
				                	  	<c:if test="${reposicao.ausenciaSolicitacao.dataFim == null}">
				                	  	<div style="font-size: 10pt"><b style="font-size: 10pt;">Data: </b>${dataInicio}</div>
				                	  	</c:if>
									    <c:if test="${reposicao.ausenciaSolicitacao.dataFim != null}">
						            			
									      <fmt:parseDate pattern="yyyy-MM-dd" value="${reposicao.ausenciaSolicitacao.dataFim}" var="dtFim" />
				                	  	  <c:set var="dataFim"><fmt:formatDate value="${dtFim}" pattern="dd/MM/yyyy" /></c:set>
					            		  <div style="font-size: 10pt">De ${dataInicio} até ${dataFim}</div>
					            		  
				            			</c:if> 
				            			
				            			
			            				<div style="font-size: 10pt" class="badge badge-info">${reposicao.ausenciaSolicitacao.motivoAusencia.nome} (${reposicao.ausenciaSolicitacao.horaInicio} - ${reposicao.ausenciaSolicitacao.horaFim} | ${reposicao.ausenciaSolicitacao.horas}/dia)</div>
				            						            							            				
										<div style="font-size: 10pt"><b>Nome solicitante: </b>
    	  							  	${reposicao.ausenciaSolicitacao.usuario.nomeCompletoMatricula}</div>
    	  							  		
   	  							  		<b style="font-size: 10pt">Projeto/escala: </b>			  	
								  		<a  target="_blank"
								  			style="font-size: 10pt"
									  		href="<c:url value='/dashboard' />/${reposicao.ausenciaSolicitacao.projetoEscala.projetoId}?mes=${reposicao.ausenciaSolicitacao.dataInicio.monthValue}&ano=${reposicao.ausenciaSolicitacao.dataInicio.year}&escala=${reposicao.ausenciaSolicitacao.projetoEscala.id}&solicitacao=${reposicao.ausenciaSolicitacao.id}&aceito=0#selecionar">
											<i>${reposicao.ausenciaSolicitacao.projetoEscala.descricaoCompletaEscala}</i>
										</a>
										<br>
				            			 	     
				            		</c:if>					       
				            		
						     	</c:forEach>
						     	
						     	
						     	
						     	
						     	
						     	
						     	
						     	
						     	
						     	
						     	
					     	</c:if>     
		            	</td>
			     	</c:forEach>
	   			</tr>
	     
	     	</c:forEach>
	        </tbody>
	    </table>
    </div>
    
</body>

</html>