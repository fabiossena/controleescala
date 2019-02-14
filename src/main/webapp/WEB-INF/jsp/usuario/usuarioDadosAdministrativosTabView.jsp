
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@page session="true"%>                            
<script type="text/javascript">
	$(function() {
		$("#funcao-id").change(function() {
			$("#perfil-acesso").html("Perfil acesso:" + $("#perfil-acesso-funcao"+this.value).val());
		});
		
		$( "form" ).submit(function() {
			  $("#funcao-id").val(3);
			});

		$("#select-funcao-adicional").change(function() {
			var perfil = $("#perfil-acesso-funcao"+this.value).val();
			$("#perfil-acesso-funcao-adicional").html((perfil == null || perfil == "") ? "" : "Perfil acesso:" + perfil);
		});

		$("#btn-funcao-adicional").click(function() {
			$("#select-funcao-adicional").prop("disable", true);
			$("#btn-funcao-adicional").prop("disable", true);
			var id = $("#select-funcao-adicional").val();
			var funcao = $("#select-funcao-adicional").val();
			var perfil = $("#perfil-acesso-funcao"+funcao).val();
			
			if (id == null || id == 0) {
				alert("Selecione uma função adicional");
				return;
			}
			
			$.ajax({
	 			type : "POST",
	 			contentType : "application/json",
	 			accept: 'text/plain',
	 			url : urlBase + "usuario/funcaoAdicional",
	 			data : JSON.stringify(id),
	 			dataType: 'text',
	 			success : function(message) {
	 				if (message == null || message == "") {

	 					var id = $("#select-funcao-adicional").val();
	 					var funcao = $("#select-funcao-adicional").val();
	 					var perfil = $("#perfil-acesso-funcao"+funcao).val();
	 					
			 			$("#area-funcoes-adicionais").append(
								'<tr class="border">' +
								'<td class="col-9 col-sm-9 col-md-9 col-lg-9 col-xl-9" style="padding: 5px; background-color: #EEE">' + $("#select-funcao-adicional option:selected").text() + ' (' + perfil + ')' + '</td>' +
								'<td class="col-3 col-sm-3 col-md-3 col-lg-3 col-xl-3" style="padding: 5px; background-color: #EEE">' +
								'	<input type="button" style="font-size: 10pt" class="btn btn-sm btn-outline-danger" onclick="apagarFuncaoSelecionada(' + id + ', this)" value="Apagar" /></td>' +
								'</tr>');
								
	 				}
	 				else {
	 					alert(message);
	 				}
	 				
	 				$("#select-funcao-adicional").val(0);
	 				$("#perfil-acesso-funcao-adicional").html("");
 					$("#select-funcao-adicional").prop("disable", false);
 					$("#btn-funcao-adicional").prop("disable", false);
	 			},
	 			error : function(e) {
	 				console.log(e);
 					$("#select-funcao-adicional").prop("disable", false);
 					$("#btn-funcao-adicional").prop("disable", false);
	 			}
	 		});
	        

		
		});
		
	});
	
	function apagarFuncaoSelecionada(id, e) {

		$.ajax({
 			type : "DELETE",
 			contentType : "application/json",
 			accept: 'text/plain',
 			url : urlBase + "usuario/funcaoAdicional",
 			data : JSON.stringify(id),
 			dataType: 'text',
 			success : function(message) {
 				if (message == null || message == "") {
		 			$(e).closest("tr").remove();
 				}
 				else{
 					alert(message);
 				}
 			},
 			error : function(e) {
 				console.log(e);
 			}
 		});
		
	}
	
	

</script>       
  
<h5>Dados administrativos</h5>
<div class="container border-top panel-custom">                  
    <div class="form-group">
        <label for="funcao.id" class="col-md-12 control-label">Função</label>
        <div class="col-md-9">
            <form:select 
            	id="funcao-id" 
            	path="funcao.id" 
           		class="form-control" 
           		items="${funcoes}"  
           		itemValue="id" 
           		itemLabel="nomeCompleto" 
           		disabled="${isDisableCamposChaves || isDisableTodosCampos}" />
			<span class="text-success" style="font-size: 10pt" id="perfil-acesso">Perfil acesso: ${usuario.funcao.perfilAcesso.nome}</span>
        </div>
	    <c:forEach items="${funcoes}" var="func">
			<input type="hidden" id="perfil-acesso-funcao${func.id}"
				value="${func.perfilAcesso.nome}" />
		</c:forEach>
    </div>
    
    <div class="form-group container col-12 col-sm-12 col-md-11 col-lg-11 col-xl-10 float-left">
        <label  for="select-funcao-adicional" class="control-label"
       	<c:if test='${(isDisableCamposChaves || isDisableTodosCampos) && usuario.usuarioFuncaoAdicionais.size() == 0}'>style="display: none;"</c:if>>Funções adicionais:</label>
        <div class="col-12 col-sm-12 col-md-10 col-lg-10 col-xl-10 row">
	        <select id="select-funcao-adicional" 
	        		class="form-control col-12 col-sm-8 col-md-8 col-lg-9 col-xl-9"  
	        		<c:if test='${isDisableCamposChaves || isDisableTodosCampos}'>style="display: none;"</c:if>>
				<option value="0"></option>
			    <c:forEach items="${funcoes}" var="func">
					<option value="${func.id}">${func.nome}</option>
				</c:forEach>
			</select>
			<button type="button" class="form-control btn btn-sm btn-success col-12 col-sm-4 col-md-4 col-lg-3 col-xl-3" 
					id="btn-funcao-adicional"
					<c:if test='${isDisableCamposChaves || isDisableTodosCampos}'>style="display: none;"</c:if>>adicionar</button>
			<span style="font-size: 10pt" class="text-success" id="perfil-acesso-funcao-adicional"></span>
			
			<table id="area-funcoes-adicionais" class="display table-container table-responsive"> 
			<tbody>
		    <c:forEach items="${usuario.usuarioFuncaoAdicionais}" var="func">
		    	<tr class="border"> 
					<td class="col-9 col-sm-9 col-md-9 col-lg-9 col-xl-9" style="padding: 5px; background-color: #EEE">${func.funcao.nome} (${func.funcao.perfilAcesso.nome})</td>
					<td class="col-3 col-sm-3 col-md-3 col-lg-3 col-xl-3" style="padding: 5px; background-color: #EEE">
						<input  type="button" 
								style="font-size: 10pt; <c:if test='${isDisableCamposChaves || isDisableTodosCampos}'>display: none;</c:if>"
								class="btn btn-sm btn-outline-danger" 
								onclick="apagarFuncaoSelecionada(${func.funcao.id}, this)"
								value="Apagar" /></td>
				</tr>
			</c:forEach>
			</tbody>
			</table>
        </div>
    </div>
    
                      
    <div class="form-group">
        <label for="centroCusto.id" class="col-md-12 control-label">Centro de custo</label>
        <div class="col-md-9">
            <form:select path="centroCusto.id" class="form-control" items="${centroCustos}"  itemLabel="descricaoCompleta" itemValue="id" disabled="${isDisableCamposChaves || isDisableTodosCampos}"/>
        </div>
    </div>		
     	                           
    <c:if test="${usuarioLogado.id == usuario.id || isFinanceiro || isAdministracao}">	
    <div class="form-group">
        <label for="valorMinuto" class="col-md-12 control-label">Valor minuto</label>
        <div class="col-md-9">
     		<form:input path="valorMinuto" class="form-control" disabled="${isDisableCamposChaves || isDisableTodosCampos}" maxlength="50" />
        </div>
    </div>    
    </c:if>                       
                     
    <div class="form-group">
        <label for="matricula" class="col-md-12 control-label">Matrícula Ixia *</label>
        <div class="col-md-9"> 
     		<form:input path="matricula" name="matricula" class="form-control" placeholder="matrícula" disabled="${isDisableCamposChaves || isDisableTodosCampos}" />
        </div>
    </div>                     
                    
    <c:if test="${horasDisponiveisAno!=null && horasDisponiveisAno!=0}"> 
    <div class="form-group">
        <label for="folgasDisponiveisAno" class="col-md-12 control-label">Horas disponívels no ano</label>
        <div class="col-md-9">
            <input readOnly value="${horasDisponiveisAno} horas / ${diasDisponiveisAno} dias(de 6 horas de trabalho)" class="form-control" />
            <a style="font-size: 10pt" href="<c:url value='/extratoHoras/' />${usuario.id}">clique aqui para ver o extrato de horas</a>
        </div>
    </div>
    </c:if>                            
                     
   <%--  <div class="form-group">
        <label for="bancoHoras" class="col-md-12 control-label">Banco de horas</label>
        <div class="col-md-9">
            <input readOnly value="${usuario.bancoHoras}" class="form-control" />
        </div>
    </div>	 --%>
</div>