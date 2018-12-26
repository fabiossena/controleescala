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
	<script type="text/javascript">
		function apagar(id){
			if (confirm("Deseja realmente apagar esta hora trabalhada?")) {
				window.location.href = "../horatrabalhada/apagar/" + id
			}
		}
		
		$(document).ready(function() {
			if ($("#id").val() == "" || $("#id").val() == null || $("#id").val() == 0) {
				 cancelarHoras();
			}
			else{
				 $("#btn-editar-hora").hide();
				 $("#btn-salvar-hora").show();
			}
			 
			$("#btn-editar-hora").click(function () {
				if ($("#btn-editar-hora").val() == "nova") {
	  				cancelarHoras();
	  				$("#id").val(-1); 
				}
				
	  			habilitaHoras();
				 $("#btn-editar-hora").val("nova");
				 $("#btn-editar-hora").hide();
				 $("#btn-salvar-hora").show();
						
			});
			
	  		$("#btn-cancelar-hora").click(function () {
	  			cancelarHoras();
				 $("#btn-editar-hora").val("nova");
				 $("#btn-editar-hora").show();
				 $("#btn-salvar-hora").hide();
				
			});
	  		

			$("#btn-aceitar-todas").click(function () {
				<c:if test="${usuarioLogado.id == aprovacaoHora.prestador.id}">
				if ($("#nota-anexa") == null || $("#nota-anexa").html() == "") {
					alert("Anexe a nota fiscal")
					return;
				}
				</c:if>
				
				window.location.href = "../horas/aprovar/${aprovacaoHora.id}"
			});
			

			$("#btn-recusar-todas").click(function () {
				var motivo = $("#motivo-recusa").val();
				if (motivo == "") {
					alert("Preencha o campo motivo recusa")
					return;
				}
				
				window.location.href = "../horas/aprovar/${aprovacaoHora.id}?aprovar=false&motivo=" + motivo;					
			});
		});
		
		function anexarClique() {
			if ($("#arquivo-updload") == null || $("#arquivo-updload").val() == "") {
				alert("Anexe a nota fiscal")
				return false; 
			}
		}
		
		function selecionarHora(id) {
			cancelarHoras();
			$("#btn-editar-hora").val("editar");
			 $("#btn-editar-hora").show();
			 $("#btn-salvar-hora").hide();
			
			$("#id").val(id); //$("#id"+id).val());
			$("#data-hora-inicio").val($("#data-hora-inicio"+id).val());
			$("#data-hora-fim").val($("#data-hora-fim"+id).val());
			//$("#horas").val($("horas"+id).val());
			$("#motivo-pausa").val($("#motivo-pausa"+id).val());
			$("#chk-excluido").prop("checked", $("#chk-excluido"+id).val() == "true");
			$("#historico").html($("#historico"+id).val());

			 $("#select-tipo-acao option").each(function () { 
		         $(this).removeAttr("selected");
		         $(this).prop("selected", false);
		 		if ($(this).val() > 0 && $(this).val() == $("#tipo-acao"+id).val()) {
		            $(this).prop("selected", true);
		            $(this).attr("selected", "selected");
		        }
			 });

			 $("#select-projeto-escala-id option").each(function () { 
		         $(this).removeAttr("selected");
		         $(this).prop("selected", false);
		 		if ($(this).val() > 0 && $(this).val() == $("#projeto-escala-id"+id).val()) {
		            $(this).prop("selected", true);
		            $(this).attr("selected", "selected");
		        }
			 });
		}
		
		function habilitaHoras() {
			 $("#data-hora-inicio").prop("disabled", false);
			 $("#data-hora-fim").prop("disabled", false);
			 $("#motivo-pausa").prop("disabled", false);
			 $("#chk-excluido").prop("disabled", false);
			 $("#select-tipo-acao").prop("disabled", false);
			 $("#select-projeto-escala-id").prop("disabled", false); 
		}
		
		function cancelarHoras() {
			 $("#data-hora-inicio").prop("disabled", true);
			 $("#data-hora-fim").prop("disabled", true);
			 $("#motivo-pausa").prop("disabled", true);
			 $("#chk-excluido").prop("disabled", true);
			 $("#select-tipo-acao").prop("disabled", true);
			 $("#select-projeto-escala-id").prop("disabled", true); 
			 
			 
			$("#btn-editar-hora").val("nova");
			$("#btn-editar-hora").show();
			$("#btn-salvar-hora").hide();
			$("#historico").html("");
			
			$("#id").val("");
			$("#data-hora-inicio").val("");
			$("#data-hora-fim").val("");
			//$("#horas").val($("horas"+id).val());
			$("#motivo-pausa").val("");
			$("#chk-excluido").prop("checked", false);

			 $("#select-tipo-acao option").each(function () { 
		         $(this).removeAttr("selected");
		         $(this).prop("selected", false);
			 });

			 $("#select-projeto-escala-id option").each(function () { 
		         $(this).removeAttr("selected");
		         $(this).prop("selected", false);
			 });
			 
			 
		}
	</script>
</head>
<body>

	<jsp:include page="../shared/navbarPartialView.jsp"/>

    <div class="container">    
	        <div style="margin-top:50px" class="mainbox col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
		        	  
		          <div class="panel panel-info">
		              <div class="panel-heading row">
		                	
		                  <h3 class="panel-title col-7 col-sm-7 col-md-7 col-lg-7 col-xl-7">
		                  	Aprovação horas     
		                  </h3>     	
		                  		
						  <div class="col-3 col-sm-3 col-md-3 col-lg-3 col-xl-3">
			        	  	<%-- <c:if test="${!isDisableTodosCampos}"> --%>
        	  					<a id="btn-voltar" class="btn btn-sm btn-primary" href="<c:url value='../aprovacaoHoras' />" style="margin: 1px">Voltar</a>
			        	  	<%-- </c:if> --%>				        	  	
		        	  	  </div>
		              </div>  
		              
		              <div class="panel-body" >
		              
		        		<jsp:include page="../shared/errorPartialView.jsp"/>	
	        	  	                      
            			<div class="row align-items-start" style="margin-top: 30px">	  
							<div class="tab-content col-8 col-sm-8 col-md-9 col-lg-9 col-xl-9 border" style="padding-top:20px; min-height: 450px;">
															
								<h5>Resumo</h5>
								
								<div class="container border-top panel-custom" style="font-size: 10pt">		
						   	  		  
						  			  <c:set var="styleStatusPrestador">
										  <c:if test="${aprovacaoHora.aceitePrestador == 0}">text-primary</c:if>
										  <c:if test="${aprovacaoHora.aceitePrestador == 1}">text-success</c:if>
										  <c:if test="${aprovacaoHora.aceitePrestador == 2}">text-danger</c:if>
									  </c:set>
									  
									  <i style="font-size: 10pt" class="form-group col-sm-12 text-danger">
						        	  	  <c:if test="${isDisableCampos}">As etapas de aprovação serão iniciadas apenas após o ultimo dia do mês, enquanto isso é possivel apenas editar as horas trabalhadas.</c:if>
										  <c:if test="${!isDisableCampos}">
										  	<c:if test="${aprovacaoHora.aceitePrestador != 1}">Processo de aprovação iniciado</c:if>
										  	<c:if test="${aprovacaoHora.aceitePrestador == 1}">Processo finalizado</c:if>
										  </c:if>
									  </i> 
									  <br>
									  
									  <div class="form-group col-sm-12" style="font-size: 10pt" >
										  <b>Etapa 1: </b> 
										  <span class="${styleStatusPrestador}">
											  <c:if test="${aprovacaoHora.aceitePrestador == 0}">(Pendente)</c:if>
											  <c:if test="${aprovacaoHora.aceitePrestador == 1}">(Aprovado)</c:if>
											  <c:if test="${aprovacaoHora.aceitePrestador == 2}">(Recusado)</c:if> Prestador: ${aprovacaoHora.prestador.nomeCompletoMatricula}
										   </span><br>	        	  		
				        	  			   <c:if test="${aprovacaoHora.arquivoNota!=null && aprovacaoHora.arquivoNota!=''}"><a class="text-dark" id="nota-anexa" href="<c:url value='../nota/${aprovacaoHora.id}'/>" target="_blank">Nota fiscal anexa</a></c:if>											  
									  </div> 
									    
									    
									  <div class="form-group col-sm-12">
									  <c:if test="${aprovacaoHora.dadosAcessoAprovacaoHoras.dadosAprovacao.size() > 0}"><b>Etapa 2:</b><br> </c:if>
									  
									     <c:forEach items="${aprovacaoHora.dadosAcessoAprovacaoHoras.dadosAprovacao}" var="item">
								          
								  			  <c:set var="styleStatusAprovador">
												  <c:if test="${item.nome.contains('(Pendente') || item.nome.contains('(Parcial')}">text-primary</c:if>
												  <c:if test="${item.nome.contains('(Aprovado')}">text-success</c:if>
												  <c:if test="${item.nome.contains('(Reprovado')}">text-danger</c:if>
											  </c:set>
										      <label style="font-size: 10pt" class="control-label ${styleStatusAprovador}">${item.nome} | <b>${item.descricao} <c:if test="${item.doubleValue < 1}">(${item.doubleValue}hrs)</c:if></b></label>
								          	  <br>
									      </c:forEach>
								       </div>	
									  
									  
										<div class="form-group col-sm-12" style="font-size: 10pt">	
									   	  <b>Etapa 3: </b> 
								          <c:if test="${aprovacaoHora.aceiteAprovador == 0}"><span class="text-primary">(Pendente) Financeiro</span></c:if>
								          <c:if test="${aprovacaoHora.aceiteAprovador == 1}"><span class="text-success">(Aprovado) Financeiro | ${aprovacaoHora.aprovador.nomeCompletoMatricula}</span></c:if>
								          <c:if test="${aprovacaoHora.aceiteAprovador == 2}"><span class="text-danger">(Recusado) Financeiro (${aprovacaoHora.motivoRecusaAprovador}) | ${aprovacaoHora.aprovador.nomeCompletoMatricula}</span></c:if>
								          <c:if test="${aprovacaoHora.aceiteAprovador == 3}"><span class="text-success">(Finalizado) Financeiro | ${aprovacaoHora.aprovador.nomeCompletoMatricula}</span></c:if>
									   </div>
									      
								  	  <div class="form-group col-sm-12" style="font-size: 10pt"> 								          
								      	<label class="control-label"> 
											<b>Total:</b> ${aprovacaoHora.totalHorasFormatada} <c:if test="${aprovacaoHora.totalHoras < 1}">(${aprovacaoHora.totalHoras}hrs</c:if>) 
											<%-- <c:if test="${aprovacaoHora.totalHoras > 0}">${aprovacaoHora.totalHoras}</c:if><c:if test="${aprovacaoHora.totalHoras == 0}">${aprovacaoHora.dadosAcessoAprovacaoHoras.totalHoras}</c:if> --%>
										</label><br>					          
								      	<label class="control-label">
											<b>Total valor:</b> ${aprovacaoHora.totalValor}
											<%-- <c:if test="${aprovacaoHora.totalValor > 0}">${aprovacaoHora.totalValor}</c:if><c:if test="${aprovacaoHora.totalValor == 0}">${aprovacaoHora.dadosAcessoAprovacaoHoras.totalValor}</c:if> --%>
										</label>
									  </div>	
									  
					        	  	  <c:if test="${!isDisableCampos}">
										   <div class="form-group col-sm-12"> 
										      <c:if test="${!isDisableCampos && 
										      				((aprovacaoHora.aceitePrestador == 0 && aprovacaoHora.prestador.id == usuarioLogado.id) || 
										      				 (aprovacaoHora.aceitePrestador == 1 && aprovacaoHora.dadosAcessoAprovacaoHoras.aprovado == 0 && aprovacaoHora.dadosAcessoAprovacaoHoras.aprovador.id == usuarioLogado.id))}">		
											      <label for="motivo" class="control-label">Motivo recusa</label>
										          <input id="motivo-recusa" class='form-control' />
									          </c:if>
									          
										      <c:if test="${horasAprovacao.motivoRecusa != '' && aprovacaoHora.aceitePrestador == 2}">
										      <label class="control-label txt-success">Motivo recusa prestador: ${horasAprovacao.motivoRecusa}</label>
										      </c:if>
										   </div>				
								      </c:if>										   
								          
							          
					        	  	  <c:if test="${!isDisableCampos}">
									   <div class="form-group col-sm-12">	
									   		<c:set var="atendentePodeAprovar">${((aprovacaoHora.aceitePrestador == 0 || aprovacaoHora.aceitePrestador == 2) && (aprovacaoHora.prestador.id == usuarioLogado.id)) ||
						        	  				 		((aprovacaoHora.aceitePrestador == 1 && (aprovacaoHora.dadosAcessoAprovacaoHoras.aprovado == 0 || aprovacaoHora.dadosAcessoAprovacaoHoras.aprovado == 2)) && (aprovacaoHora.dadosAcessoAprovacaoHoras.aprovador.id == usuarioLogado.id))}</c:set>	        	  			
						        	  		<c:if test="${atendentePodeAprovar}">						          
							        	  		<input 
							        	  			id="btn-aceitar-todas" 
							        	  			type="button" 
							        	  			class="btn btn-sm btn-success" 
							        	  			value="Aceitar todas" 
							        	  			style="margin: 1px" />
					        	  			</c:if>				        	  			
						        	  		<c:if test="${(aprovacaoHora.aceitePrestador == 0 && aprovacaoHora.prestador.id == usuarioLogado.id) ||
						        	  						(aprovacaoHora.aceitePrestador == 1 && aprovacaoHora.dadosAcessoAprovacaoHoras.aprovado == 0 && aprovacaoHora.dadosAcessoAprovacaoHoras.aprovador.id == usuarioLogado.id)}">
							        	  		<input 
							        	  			id="btn-recusar-todas" 
							        	  			type="button" 
							        	  			class="btn btn-sm btn-danger" 
							        	  			value="Recusar todas" 
							        	  			style="margin: 1px" />	
					        	  			</c:if>		
					        	  			
				        	  			</div>	
			        	  			
				        	  			<c:if test="${usuarioLogado.id == aprovacaoHora.prestador.id && atendentePodeAprovar}">					          
						        	  		<div class="form-group col-sm-12">	
						        	  			<form method="POST" action="<c:url value='../upload/${aprovacaoHora.id}'/>" enctype="multipart/form-data"> 							        	  				
												    <input class="btn btn-sm btn-primary" type="file" name="file" id="arquivo-updload" /><br/><br/>
												    <input class="btn btn-sm btn-primary" type="submit" value="Salvar nota anexa" onclick="anexarClique()" />						        	  				
												    <input type="hidden" name="forcar" value="${forcar}" />
												</form>
			        	  					</div>	 
					        	  		</c:if>
					        	  			
				        	  		</c:if>	
			        	  				
					            </div>
							          
								<h5 style="margin-top: 30px">Horas trabalhadas</h5>
								<div class="container border-top panel-custom">																   
							          
							          
									<div class="table-container table-responsive">
										<table id="tabela" class="display tabela-avancada" style="font-size: 10pt">
					
									        <thead>
									            <tr>
									                <th>Id</th>
													<th>Escala</th>
									                <th>Data/hora início/fim</th> 
									                <th>Horas</th> 
									                <th>Ação/Motivo</th>
									                <th>Observação</th>
									                <th>Histórico</th>
									            </tr>
									        </thead>
									        <tbody>    
										      <c:forEach var = "i" begin = "1" end = "2">
										        <c:forEach items="${horasTrabalhadas}" var="item">
										        	<c:if test="${
											        	(i == 1 && (item.horaAprovacao.prestador.id == usuarioLogado.id || item.projetoEscala.monitor.id == usuarioLogado.id ||  item.projetoEscala.projeto.gerente.id == usuarioLogado.id)) ||
											        	(i == 2 && !(item.horaAprovacao.prestador.id == usuarioLogado.id || item.projetoEscala.monitor.id == usuarioLogado.id ||  item.projetoEscala.projeto.gerente.id == usuarioLogado.id))}">
											            <tr onclick="selecionarHora(${item.id})" <c:if test="${item.tipoAcao==1}">style='font-weight: bold'</c:if>>
										                	<fmt:parseDate pattern="yyyy-MM-dd" value="${item.dataHoraInicio}" var="dataHrIni" /> 
										                	<fmt:parseDate pattern="yyyy-MM-dd" value="${item.dataHoraFim}" var="dataHrFim" /> 
											                <td>${item.id}</td>
															<td>${item.projetoEscala.descricaoEscala}</td>
											                <td>
												                <div style="width: 150px"> 
													                <fmt:formatDate value="${dataHrIni}" pattern="dd/MM/yyyy" /> ${item.dataHoraInicio.toString().substring(11, 16)} <br>
																	<fmt:formatDate value="${dataHrFim}" pattern="dd/MM/yyyy" /> ${item.dataHoraFim.toString().substring(11, 16)}
																</div>
															</td>
														  	<td>${item.horasFormatada} <c:if test="${item.horas < 1}">(${item.horas}hrs)</c:if></td> 
											                <td>
											                	<c:if test="${item.tipoAcao == 1 || item.tipoAcao == 3}">Inicio/Parada</c:if>
											                	<c:if test="${item.tipoAcao == 2 || item.tipoAcao == 4}">Pausado/Reiniciado</c:if>
										                		<c:if test="${item.motivoPausa != null && item.motivoPausa != ''}"><br>(${item.motivoPausa})</c:if>
										                	</td>
											                <td style="font-size: 10pt">
											                	<div  style="width: 200px">
																	  <c:if test="${item.aprovadoResponsavel == 0}"><span class="text-primary">Aprovação pendente responsável | ${item.projetoEscala.monitor.nomeCompletoMatricula}</span></c:if>
																	  <c:if test="${item.aprovadoResponsavel == 1}"><span class="text-success">Aprovado responsável<c:if test="${item.responsavelAprovacao != null}"> | ${item.responsavelAprovacao.nomeCompletoMatricula}</c:if></span></c:if>
																	  <c:if test="${item.aprovadoResponsavel == 2}"><span class="text-danger">Recusado responsável<c:if test="${item.responsavelAprovacao != null}"> | ${item.responsavelAprovacao.nomeCompletoMatricula}</c:if></span></c:if>
																	  
												                	<c:if test="${item.incluidoManualmente}">
												                		<br> <br> 
												                		<i>Inclusão manual					                	
												                		<c:if test="${item.usuarioCriacao != null}"> (${item.usuarioCriacao.nomeCompletoMatricula})<br></c:if></i>
												                	</c:if>
												                	
												                	<c:if test="${item.excluido}">
												                		<br><br><i>Excluido
												                		<c:if test="${item.usuarioExclusao != null}"> (${item.usuarioExclusao.nomeCompletoMatricula})</c:if>
												                		</i>
												                	</c:if> 
											                	</div>
										                	</td>
															<td style="font-size: 10pt">
																<div  style="width: 200px">
																	<c:if test="${item.historicoCorrecao.length() <= 60}">${item.historicoCorrecao.replace('<br>', '<br><br>')}</c:if><c:if test="${item.historicoCorrecao.length() > 60}">${item.historicoCorrecao.substring(0, 60).replace('<br>', '<br><br>')}... </c:if>
																</div>
																
																<input type="hidden" id="id${item.id} value="${item.id}" />
																<input type="hidden" id="tipo-acao${item.id}" value="${item.tipoAcao}" />
																<input type="hidden" id="motivo-pausa${item.id}" value="${item.motivoPausa}" />
																<input type="hidden" id="data-hora-inicio${item.id}" value="<fmt:formatDate value="${dataHrIni}" pattern="dd/MM/yyyy" /> ${item.dataHoraInicio.toString().substring(11, 16)}" />
																<input type="hidden" id="data-hora-fim${item.id}" value="<fmt:formatDate value="${dataHrFim}" pattern="dd/MM/yyyy" /> ${item.dataHoraFim.toString().substring(11, 16)}" />
																<input type="hidden" id="horas${item.id}" value="${item.horas}" />
																<input type="hidden" id="projeto-escala-id${item.id}" value="${item.projetoEscala.id}" />
																<input type="hidden" id="chk-excluido${item.id}" value="${item.excluido}" />
																<input type="hidden" id="historico${item.id}" value="${item.historicoCorrecao}" />
							                				
							                				</td>
							                				
											            </tr>											            
										            </c:if>
										        </c:forEach>
										      </c:forEach>    
									        </tbody>
									    </table>
									</div>
									
									
       	  							<form:form modelAttribute="horaTrabalhada" class="form-horizontal row container col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12" method="POST">	
		  
									<div class="form-group col-12 col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12 border-bottom row" style="padding:0 0 10px 0; margin: 25px 10px 20px 15px">	
   											<h5 class="panel-title"style="margin-right: 25px">Detalhes hora trabalhada</h5>	
						                	<c:if test="${(isAdministracao || isAtendimento) && aprovacaoHora.aceitePrestador != 1}">
							        	  		<span class="col-6 col-sm-6 col-md-6 col-lg-6 col-xl-6 float-right">
								        	  		<input id="btn-editar-hora" type="button" class="btn btn-sm btn-success float-left" value="nova" style="margin: 1px"  />	
								        	  		<button id="btn-salvar-hora" type="submit" class="btn btn-sm btn-success float-left" style="margin: 1px">salvar</button>
								        	  		<input id="btn-cancelar-hora" type="button" class="btn btn-sm btn-primary float-left" value="cancelar" style="margin: 1px"  />
							        	  		</span>	
							        	  		<br> 
						        	  		</c:if>
					        	  		</div>	
										  <form:hidden path="id" />
									      							
									      <div class='form-group col-12 col-sm-12 col-md-12 col-lg-6 col-xl-6 '>
										    <label for="projetoEscala.id" class="control-label">Escala projeto *</label>
									        <form:select 
							        			path="projetoEscala.id" 
							        			id="select-projeto-escala-id" 
							        			items="${escalas}" itemLabel="descricaoCompletaEscala" itemValue="id"
							        			class='form-control ${result.hasFieldErrors("projetoEscala.id") ? "is-invalid" : ""}' 
							        			disabled="${isDisableInsertCampos}" >
									            <option value="0"></option>
										        <c:forEach items="${escalas}" var="esc">
										            <option value="${esc.id}" <c:if test="${esc.id == horaTrabalhada.projetoEscala.id}">select</c:if> >${esc.descricaoCompletaEscala}</option>
										        </c:forEach>
									        </form:select>
									        <div class="invalid-feedback"><form:errors path="projetoEscala.id" /></div>
										  </div>		 
			
										  <div class="form-group col-12 col-xs-12 col-sm-12 col-md-6 col-lg-3 col-xl-3">
										      <label for="dataHoraInicio" class='control-label'>Data/hora início *</label>
					                	  	  <fmt:parseDate pattern="yyyy-MM-dd hh:mm" value="${dataHoraInicio}" var="dtHrIni" />
					                	  	  <c:set var="dataHoraInicioFormatada"><fmt:formatDate value="${dtHrIni}" pattern="dd/MM/yyyy hh:mm" /></c:set>
										      <form:input 
								      				path="dataHoraInicio"
								      				id="data-hora-inicio"
								      				value="${dataHoraInicioFormatada}"
								      				class='form-control mask-date-hour  ${result.hasFieldErrors("dataHoraInicio") ? "is-invalid" : ""}' 
								      				disabled="${isDisableInsertCampos}" 
								      				style="z-index: 1;" />
								      				<span id="horas">${horas}</span>
					                          <div class="invalid-feedback"><form:errors path="dataHoraInicio" /></div>
					                          
										  </div>	 
			
										  <div class="form-group col-12 col-xs-12 col-sm-12 col-md-6 col-lg-3 col-xl-3">
										      <label for="dataHoraFim" class='control-label'>Data/hora fim *</label>
					                	  	  <fmt:parseDate pattern="yyyy-MM-dd hh:mm" value="${dataHoraFim}" var="dtHrFim" />
					                	  	  <c:set var="dataHoraFimFormatada"><fmt:formatDate value="${dtHrFim}" pattern="dd/MM/yyyy hh:mm" /></c:set>
										      <form:input 
								      				path="dataHoraFim"
								      				id="data-hora-fim"
								      				value="${dataHoraFimFormatada}"
								      				class='form-control mask-date-hour  ${result.hasFieldErrors("dataHoraFim") ? "is-invalid" : ""}' 
								      				disabled="${isDisableInsertCampos}" 
								      				style="z-index: 1;" />
					                          <div class="invalid-feedback"><form:errors path="dataHoraFim" /></div>
										  </div>	
										  
										  <div class="form-group col-12 col-xs-12 col-sm-12 col-md-5 col-lg-5 col-xl-5">
										      <label for="tipoAcao" class='control-label'>Tipo ação *</label>
											  <form:select 
											  		path="tipoAcao"
											  		id="select-tipo-acao" 
											  		class='form-control ${result.hasFieldErrors("tipoAcao") ? "is-invalid" : ""}' 
											  		disabled="${isDisableInsertCampos}" >
											      <option <c:if test="${horaTrabalhada.tipoAcao == 1 || horaTrabalhada.tipoAcao == 3}">selected</c:if> value="1">Inicio/Parada</option>
											      <option <c:if test="${horaTrabalhada.tipoAcao == 2 || horaTrabalhada.tipoAcao == 4}">selected</c:if> value="2">Pausa/Reinicio</option>
										      </form:select>	
									      </div>			   	  
								   	  
										  <div class="form-group col-12 col-xs-12 col-sm-8 col-md-5 col-lg-5 col-xl-5">
										      <label for="motivoPausa" class="control-label">Motivo pausa</label>
									          <form:input 
									          		path="motivoPausa"
									          		id="motivo-pausa" 
									          		class='form-control' 
									          		disabled="${isDisableInsertCampos}" />
										  </div>

											<div
												class="form-group col-2 col-xm-2 col-sm-2 col-md-2 col-lg-2 col-xl-2">
												<label for="excluido" class="control-label"><br />Excluido <form:checkbox
														path="excluido"
														id="chk-excluido" 
														disabled="${isDisableInsertCampos}"
														class="form-control" /> </label>
											</div>		
										  
										  <div class="form-group col-sm-12 text-primary" id="historico" style="font-size: 10pt">	
										  	${historicoCorrecao}
										  </div>
          							 </form:form>
									 </div>
								 </div>
							</div>
      		           </div>
                      

                
	         </div> 
	    </div>
    </div>
    
</body>

</html>