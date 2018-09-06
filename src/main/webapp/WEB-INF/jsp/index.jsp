<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page session="true"%>

<head>
	<title>Ixia - Sistema de Escala - Pagina inicial</title>
    <jsp:include page="shared/headerPartialView.jsp"/>
	<script src="<c:url value='/js/index-projeto.js' />"></script>
	<script>
	
		function aprovarSolicitacao(id) {
			$("#motivo-recusa-solicitacao-feedback" + id).html("");
			$("#motivo-recusa-solicitacao" + id).removeClass("is-invalid");
			aceitaRecusaSolicitacaoAusencia(id, true, $("#motivo-recusa-solicitacao" + id).html(), 3);	
		}
		
		function recusarSolicitacao(id) {
			if ($("#panel-motivo-recusa-solicitacao" + id).is(":hidden") || 
					$("#motivo-recusa-solicitacao" + id).val() == "") {
				$("#panel-motivo-recusa-solicitacao" + id).show();
				$("#motivo-recusa-solicitacao" + id).addClass("is-invalid");
				$("#motivo-recusa-solicitacao-feedback" + id).html("Preencha o campo motivo recusa");		
			}
			else { //if (confirm("Deseja recusar este projeto?")) {	
				$("#motivo-recusa-solicitacao-feedback" + id).html("");
				$("#motivo-recusa-solicitacao" + id).removeClass("is-invalid");
				aceitaRecusaSolicitacaoAusencia(id, false, $("#motivo-recusa-solicitacao" + id).val(), 3);			
			}
		}
	
		function aceitaRecusaSolicitacaoAusencia(id, aceita, motivo, origem)
	    {
			if (!aceita && origem != 2){
				if (motivo == "" || motivo == null){
					alert("Preencha o campo observação com o motivo da recusa");
					return;
				}
			}
			
			if (confirm("Deseja realmente " + (aceita ? "aceitar" : "recusar") + " esta solicitação?")) {
				window.location.href = urlBase + "ausencia/aceita/" + id + "?origem=" + origem + "&aceita=" + aceita + (motivo == null || motivo == "" ? "" :  "&motivo=" + motivo);
			}		
	    }
		
		function iniciar(tipo, iniciar){
			var escala= $("#selected-projeto-escala-principal").val();
			var motivo = $("#motivo").val();
			if (tipo == 2 && !$("#motivo").prop("disabled") && motivo == "") {
				alert("Preencha o campo motivo");	
				return;
			} else if (tipo == 2 && !$("#motivo").prop("disabled")) {
				iniciar = true;
			}
			else if (tipo == 2 && $("#motivo").prop("disabled")) {
				iniciar = false;			
			}		
			
	    	window.location.href = "<c:url value='/horas/iniciarEscala/' />" + escala + "?tipo=" + tipo +"&motivo=" + motivo +"&iniciar=" + iniciar;
		}
		
		var tempoServer = ${totalSegundos+0};
		var tempoInicial = Date.now();
		$(document).ready(function() {
			if (${totalSegundos+0} > 0) {
				setTempo();
			}
			
			if ("${pausar}" ==  "pausar") {
				$("#btn-pausar").mouseover(function() {
					timeout = setTimeout(function () { $("#panel-motivo").show(500); }, 200);
				});
				var timeout;
				$("#btn-pausar").mouseleave(function() {
					clearTimeout(timeout);
					timeout = setTimeout(function () { $("#panel-motivo").hide(500); }, 500);
				});
				
				$("#panel-motivo, #motivo").mouseover(function() {
					clearTimeout(timeout);
					$("#panel-motivo").show();
				});
	
				$("#panel-motivo").mouseleave(function() {
					clearTimeout(timeout);
					timeout = setTimeout(function () { $("#panel-motivo").hide(500); }, 500);
				});
			}
			
		});

		function setTempo() {

			var date = new Date(null);
			tempoServer++;
			date.setSeconds(tempoServer); // + (((Date.now() - tempoInicial)/60)/60)*2,5); // specify value for SECONDS here
			var timeString = date.toISOString().substr(11, 8);
			if (${iniciarDisabled}) {
				$("#tempo").html(timeString);				
				if ("${pausar}" ==  "pausar") {
					setTimeout(setTempo, 1000);
				}
			}
		}
		
	
	</script>
</head>
<body>

	<jsp:include page="shared/navbarPartialView.jsp"/>

    <div class="container">  
		    <div style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">  
	          <div class="panel panel-info">
	              <div class="panel-heading">
	                  <h4 class="panel-title" style="font-weight: bold;">Bem vindo, ${usuarioLogado.primeiroNome} (${usuarioLogado.funcao.nome.toLowerCase()})</h4>
	              </div> 
			 </div> 
			</div>
			
			<div class="table-container row table-responsive col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12"  style="margin: 30px 0 30px 0">    
		        
				        
			<c:if test="${isAtendimento || isMonitoramento}">
		      <div class='form-group col-12 col-sm-12 col-md-12 col-lg-8 col-xl-8'>
			    <label for="escalaId" class="control-label">Selecione uma escala para iniciar:</label>
		        <form:select path="escalaId" id="selected-projeto-escala-principal" class='form-control editable-select ${result.hasFieldErrors("projetoEscala.id") ? "is-invalid" : ""}'  disabled="${iniciarDisabled}" >
			        <option value="0"></option>
			        <c:forEach items="${escalas}" var="item">
			        	<option <c:if test="${item.id == escalaId}">selected</c:if> value="${item.id}">${item.descricaoCompletaEscala}</option> 
			        </c:forEach>
		        </form:select>
				<br />
				<button id="btn-iniciar" onclick="iniciar(1,true)" class="btn btn-sm btn-primary" style="margin: 1px" <c:if test="${iniciarDisabled}">disabled</c:if>>Iniciar</button>
				
				<button id="btn-pausar" " onclick="iniciar(2)" class="btn btn-sm btn-primary" style="margin: 1px"  <c:if test="${pausarDisabled}">disabled</c:if>>
					<span id="lbl-pausar">${pausar}</span>
				</button>
						   
				<button id="btn-parar" onclick="iniciar(1,false)" class="btn btn-sm btn-primary" style="margin: 1px" <c:if test="${pararDisabled}">disabled</c:if>>Parar</button>
				
				<b>Tempo médio: </b><span id="tempo">00:00:00</span>
				<br>
				<div id="panel-motivo" style="display: none">
					<label for="motivo"><b>Motivo:</b></label>
					<input id="motivo" 
							   value="${motivo}" 
							   <c:if test="${pausar != 'pausar' || pausarDisabled}">disabled</c:if> />
			    </div> 
			  </div>		                    
	        </c:if>	
	        <c:forEach items="${projetosCadastrados}" var="item"> 
	        
	        	<div id="card${item.id}" class="card col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" style="margin: 12px; float: left;">
				  <div class="card-body">
				  
				    <h4 class="card-title">Projeto cadastrado</h4>
				    <h5 class="card-title">${item.projeto.descricaoProjeto} - ${item.projetoEscala.descricaoEscala}</h5>
				    
				    <h6 class="card-subtitle mb-2 text-muted">
	                	<fmt:parseDate pattern="yyyy-MM-dd" value="${item.dataInicio} " var="dataInicio" />
	                	<fmt:formatDate value="${dataInicio}" pattern="dd/MM/yyyy" />
	                	
	                	<fmt:parseDate pattern="yyyy-MM-dd" value="${item.dataFim}" var="dataFim" />
	                	<fmt:formatDate value="${dataFim}" pattern="dd/MM/yyyy" />
			    	</h6>
			    	
				    <h6 class="card-subtitle mb-2 text-muted">Horário: ${item.projetoEscala.horaInicio} às ${item.projetoEscala.horaFim}</h6>
				    <h6 class="card-subtitle mb-2 text-muted">Função: ${item.funcao.nome}</h6>
				    
				    <p class="card-text" style="font-size: 10pt" id="observacao-prestador${item.id}">${item.observacaoPrestador}</p>
				                    	
		                <input  type="button" 
				                id="bt-aceita-prestador${item.id}"
		                		onclick="aceitarPrestador(${item.id})" 
		                		class="btn btn-sm btn-success"
		                		style="margin: 1px"
		                		value="clique aqui para aceitar" />				                
	               		
		                <input  type="button"
		                		id="bt-recusa-prestador${item.id}"
		                		onclick="recusarPrestador(${item.id})" 
		                		class="btn btn-sm btn-danger" 
		                		style="margin: 1px"
		                		value="clique aqui para recusar" />
		                	
	              			<div class="form-group" 
	              				 id="panel-motivo-recusa${item.id}"
	              				 style="font-size: 10pt; margin-top: 15px; display: none">
	              				 <label for="motivo-recusa${item.id}">Motivo:</label>
	                			<textarea 
		                			class="form-control" 
		                			rows="3" 
		                			maxlength="500"
		                			id="motivo-recusa${item.id}">${item.motivoRecusa}</textarea>
              		 			<div id="motivo-recusa-feedback${item.id}" class="invalid-feedback"></div>	
						</div>	               
	             	
				  </div>
				</div>
	        </c:forEach>
	        
	        
	        
	        
	        <c:forEach items="${solicitacoesAusencias}" var="solicitacao"> 
	        
	        	<div id="card${solicitacao.id}" class="card col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" style="margin: 12px; float: left;">
				  <div class="card-body">
				  
				    <h4 class="card-title">Solicitação ausência</h4>
				    <h5 class="card-title">${solicitacao.projetoEscala.projeto.descricaoProjeto} - ${solicitacao.projetoEscala.descricaoEscala}</h5>
				    
				    <h6 class="card-subtitle mb-2 text-muted">
	                	<fmt:parseDate pattern="yyyy-MM-dd" value="${solicitacao.dataInicio} " var="dataInicio" />
	                	<fmt:formatDate value="${dataInicio}" pattern="dd/MM/yyyy" />
	                	
	                	<fmt:parseDate pattern="yyyy-MM-dd" value="${solicitacao.dataFim}" var="dataFim" />
	                	<fmt:formatDate value="${dataFim}" pattern="dd/MM/yyyy" />
			    	</h6>
			    	
				    <h6 class="card-subtitle mb-2 text-muted">Horário: ${solicitacao.horaInicio} às ${solicitacao.horaFim} <c:if test="${solicitacao.horas != ''}">(${solicitacao.horas}/dia)</c:if></h6>
				    
				    <h6 class="card-subtitle mb-2 text-muted">Status: 
						                	<c:if test="${solicitacao.aceito==0}">Pendente</c:if>
						                	<c:if test="${solicitacao.aceito==1}">Aceita</c:if>
						                	<c:if test="${solicitacao.aceito==2}">Recusada</c:if>
                	</h6>
                	
				    <h6 class="card-subtitle mb-2 text-muted">Motivo: ${solicitacao.motivoAusencia.nome}</h6>
				    
				    <h6 class="card-subtitle mb-2 text-muted">
	                	Tipo: <c:if test="${solicitacao.tipoAusencia == 0}">Simples</c:if>                
	                	<c:if test="${solicitacao.tipoAusencia == 1}">Horário colocado a disposição</c:if>
	                	<c:if test="${solicitacao.tipoAusencia == 2}">Indicado outro horário/usuário</c:if>
                	</h6>
                	
				    <h6 class="card-subtitle mb-2 text-muted">Seu acesso: <br>
				        			        
					   <c:forEach items="${solicitacao.dadosAcesso.dadosAcesso}" var="acesso">
	        	  			<c:if test='${!acesso.nome.isEmpty()}'> 
						  	 	<div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
	  							  	- ${acesso.nome}
	  							  </div>
  							 </c:if>
					   </c:forEach>
				    </h6>
				    
				    
	               	<br>				    
				    <c:if test='${solicitacao.tipoAusencia == 1}'>
				    <h6 class="card-subtitle mb-2 text-muted">Horário a disposição</h6>
				    </c:if>
					<c:if test='${solicitacao.tipoAusencia == 2}'>
						<h6 class="card-subtitle mb-2 text-muted">Reposição:</h6>
						<p class="card-text" style="font-size: 10pt">
							
						  	<c:if test='${solicitacao.tipoAusencia == 2}'>
							  <c:forEach items="${solicitacao.ausenciaReposicoes}" var="reposicao">			    	  							  
								  	<c:if test='${reposicao.indicadoOutroUsuario}'>
									  	<div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" style="font-size: 10pt">
	    	  							  	<b>${reposicao.usuarioTroca.nomeCompletoMatricula}</b>
	    	  							  </div>
								  	</c:if> 
								  	<fmt:parseDate pattern="yyyy-MM-dd" value="${reposicao.data}" var="data" />
							  		<div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" style="font-size: 10pt">
	   	  							  	<b><fmt:formatDate value="${data}" pattern="dd/MM/yyyy" /> | ${reposicao.horaInicio} - ${reposicao.horaFim}<c:if test="${solicitacao.horas != ''}"> (${solicitacao.horas})</c:if></b>
	   	  							 </div>	
								  	<div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" style="font-size: 10pt">
	   	  							  	<i>Projeto/escala: ${reposicao.projetoEscalaTroca.descricaoCompletaEscala}</i>
	   	  							  	<c:if test="${reposicao.observacao != ''}">Observação: ${reposicao.observacao}</c:if>
	   	  							 </div>	
								  	<br>
							  </c:forEach>		
						  	</c:if>
		
						
						</p>
			         </c:if>   
				    
				    
				    
				    <h6 class="card-subtitle mb-2 text-muted">Detalhes:</h6>
				    <p class="card-text" style="font-size: 10pt">
						
						<c:if test="${solicitacao.observacao != ''}">()Observação) ${solicitacao.observacao}</c:if>
							
						  <c:forEach items="${solicitacao.dadosAcesso.dadosAprovacao}" var="aprovacao">
						  	<c:if test='${aprovacao.nome.contains("Pendente") || aprovacao.nome.contains("Não enviada")}'>
							  	<div class="text-primary col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" style="font-size: 10pt">
 	  							  	- ${aprovacao.nome}
 	  							  </div>
						  	</c:if>
						  	<c:if test='${aprovacao.nome.contains("Aprovado")}'>
							  	<div class="text-success col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" style="font-size: 10pt">
 	  							  	- ${aprovacao.nome}
 	  							  </div>
						  	</c:if>
						  	<c:if test='${aprovacao.nome.contains("Recusado")}'>
							  	<div class="text-danger col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" style="font-size: 10pt">
	  							  	- ${aprovacao.nome}
	  							  </div>
						  	</c:if>
						  </c:forEach>		
					</p>				                	
	               	        	
					  <c:if test="${solicitacao.dadosAcesso.visivelAprovacao}">
			              <a class="btn btn-sm btn-primary" href="<c:url value='/ausencia' />/${solicitacao.id}" style="margin: 1px">ver solicitação</a>
        				  
        				  <c:if test="${solicitacao.dadosAcesso.aceitePrincipal == 0 || solicitacao.dadosAcesso.aceitePrincipal == 2}">
			              
			                <input  type="button" 
					                id="bt-aprova-solicitacao${solicitacao.id}"
			                		onclick="aprovarSolicitacao(${solicitacao.id})" 
			                		class="btn btn-sm btn-success"
			                		style="margin: 1px"
			                		value="clique aqui para aprovar" />				                
		               		</c:if>
		               		
			                <c:if test="${solicitacao.dadosAcesso.aceitePrincipal == 0 || solicitacao.dadosAcesso.aceitePrincipal == 1}">
								<input  type="button"
			                		id="bt-recusa-solicitacao${solicitacao.id}"
			                		onclick="recusarSolicitacao(${solicitacao.id})" 
			                		class="btn btn-sm btn-danger" 
			                		style="margin: 1px"
			                		value="clique aqui para recusar" />
		                	</c:if>
			                	
		              			<div class="form-group" 
		              				 id="panel-motivo-recusa-solicitacao${solicitacao.id}"
		              				 style="font-size: 10pt; margin-top: 15px; display: none">
		              				 <label for="motivo-recusa-solicitacao${solicitacao.id}">Motivo:</label>
		                			<textarea 
			                			class="form-control" 
			                			rows="3" 
			                			maxlength="500"
			                			id="motivo-recusa-solicitacao${solicitacao.id}"></textarea>
	              		 			<div id="motivo-recusa-solicitacao-feedback${solicitacao.id}" class="invalid-feedback"></div>	
								</div>	               
	             		</c:if>
				  </div>
				</div>
	        </c:forEach>
	        
	        
	        
		</div>
	</div>	
</body>

</html>
