
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page session="true"%>

<head>
	<title>Ixia - Sistema de Escala - Extrato horas</title>
    <jsp:include page="../shared/headerPartialView.jsp"/>
	<script src="<c:url value='/js/usuario/usuario-principal.js' />"></script>
	<script>
	$(function() {
	    
	    //$('#select-ano-horas, #select-mes-horas').on('change',function() {
	   	//	console.log(this.value);
		//	window.location.href = urlBase + "usuario/extratoHoras?ano=" + $("#select-ano-horas").val() + "mes=" + $("#select-mes-horas").val();
		//});
         
         $('#prestador').on('change',function() {
     		window.location.href = urlBase + "extratoHoras/" + this.value;
    	 });

	});
	</script>
</head>

<body>
	<jsp:include page="../shared/navbarPartialView.jsp"/>

    <div class="container">    
       <div style="margin-top:50px" class="mainbox col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
        	  
		<h3>Extrato banco de horas</h3>      
		<div class="container border-top panel-custom">
		     <div class="container row">
				
				<c:if test="${isAdministracao || isMonitoramento || isGerencia}">				
				<div class="form-group col-12 col-sm-10 col-md-8 col-lg-6 col-xl-6">
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
		         
			    <%-- <div class="form-group" style="display: none">
			        <label for="data" class="col-md-8 control-label">Ano</label>
			        <div class="col-md-6">
			            <form:select id="select-ano-horas" path="data" class="form-control" items="${anos}" />
			        </div>
			     </div>
		       
			    <div class="form-group" style="display: none">
			        <label for="data" class="col-md-8 control-label">Mês</label>
			        <div class="col-md-6">
			            <form:select id="select-mes-horas" path="data" class="form-control" items="${meses}" />
			        </div>
			     </div> --%>
			
			 	
				<div class="table-container table-responsive"  style="margin: 20px 0 30px 0">
				<c:set var="total">0</c:set>
					<table id="tabela-hora-extra" class="display tabela-simples">
				       <!-- Header Table -->
				       <thead>
				            <tr>
								<th>Data</th>
				                <th>Descrição</th>
								<th style="text-align: right;">Horas	</th>
								<th style="text-align: right;">Total</th>
				            </tr>
				        </thead>
				        <!-- Footer Table -->
				        <tfoot>
				        <tbody>        
					        <c:forEach items="${extratoHoras}" var="item">
					            <tr>
				                	<fmt:parseDate pattern="yyyy-MM-dd" value="${item.data}" var="data" />	                	
					                <td><fmt:formatDate value="${data}" pattern="dd/MM/yyyy" /></td>
									<td>${item.descricao}</td>
					                <td style="text-align: right;">${item.minutosEntradaSaida/60} horas</td>
					                <td style="text-align: right;">${item.minutosTotalDisponiveis/60} horas</td>
					            </tr>
					            <c:set var="total">${item.minutosTotalDisponiveis}</c:set>
					        </c:forEach>
				        </tbody>
				    </table>
				    <div  style="text-align: right;"><h4>Total ${total/60} horas</h4></div>
				    <div  style="text-align: right;"><h5>Total ${String.format("%1.2f", total/60/6)} dias <br>(de 6 horas de trabalho)</h5></div>
				</div>
			
			</div>
		</div>
	</div>
</div>
</body>