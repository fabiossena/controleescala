
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@page session="true"%>                            
<script type="text/javascript">
	$(function() {
		$("#funcao-id").change(function() {
			$("#perfil-acesso").html("Perfil acesso:" + $("#perfil-acesso-funcao"+this.value).val());
		});
	});

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
           		itemLabel="nome" 
           		disabled="${isDisableCamposChaves || isDisableTodosCampos}" />
			<span class="text-success" style="font-size: 10pt" id="perfil-acesso">Perfil acesso: ${usuario.funcao.perfilAcesso.nome}</span>
        </div>
	    <c:forEach items="${funcoes}" var="func">
			<input type="hidden" id="perfil-acesso-funcao${func.id}"
				value="${func.perfilAcesso.nome}" />
		</c:forEach>
    </div>
                      
    <div class="form-group">
        <label for="centroCusto.id" class="col-md-12 control-label">Centro de custo</label>
        <div class="col-md-9">
            <form:select path="centroCusto.id" class="form-control" items="${centroCustos}"  itemLabel="descricaoCompleta" itemValue="id" disabled="${isDisableCamposChaves || isDisableTodosCampos}"/>
        </div>
    </div>		 	                           
                     
    <div class="form-group">
        <label for="valorMinuto" class="col-md-12 control-label">Valor minuto</label>
        <div class="col-md-9">
     		<form:input path="valorMinuto" class="form-control" placeholder="matricula" disabled="${isDisableCamposChaves || isDisableTodosCampos}" maxlength="50" />
        </div>
    </div>                          
                     
    <div class="form-group">
        <label for="matricula" class="col-md-12 control-label">Matrícula Ixia *</label>
        <div class="col-md-9">
     		<form:input path="matricula" class="form-control" name="matricula" placeholder="matricula" disabled="${isDisableCamposChaves || isDisableTodosCampos}" maxlength="50" />
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