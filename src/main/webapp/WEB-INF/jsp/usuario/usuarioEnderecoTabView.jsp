
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@page session="true"%>
<script>

var isDisableTodosCampos = ${isDisableTodosCampos};
var estados;
var cidades;
$(function() {
	
	loadDadosEndereco();
	loadClickIndicarEndereco();
	
	$("#select-endereco-pais").change(function(e) {	
		console.log("select-endereco-pais");
    	var val = $("#select-endereco-pais").val();
    	$("#select-endereco-estado").empty().append('<option selected="selected" value="-1">Selecione um estado</option>');
    	$("#select-endereco-cidade").empty().append('<option selected="selected" value="-1">Selecione uma cidade</option>');
		$.each(estados, function (data, value) {
    		if (value.paisId == val) {
        		$("#select-endereco-estado").append($("<option></option>").val(value.id).text(value.nome));
    		}
		});
		
	});		

	$( "#select-endereco-estado").change(function(e) {			
    	var val = $("#select-endereco-estado").val();
    	$("#select-endereco-cidade").empty().append('<option selected="selected" value="-1">Selecione uma cidade</option>');
		$.each(cidades, function (data, value) {
    		if (value.estadoId == val) {
        		$("#select-endereco-cidade").append($("<option></option>").val(value.id).text(value.nome));
    		}
		});
		
	});	

	
    $.ajax({
        type: "GET",
        url: urlBase + "referencias/estados",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
	        	estados = data;
	        	var val = $("#select-endereco-pais").val();
	        	$("#select-endereco-estado").empty().append('<option selected="selected" value="-1">Selecione um estado</option>');
				$.each(estados, function (data, value) {
	        		if (value.paisId == val) {
		        		$("#select-endereco-estado").append($("<option></option>").val(value.id).text(value.nome));
	        		}
				});
				loadCidades();
        	
        },
        failure: function (response) {
            alert(response.d);
        }
    });

    function loadCidades() {
	    $.ajax({
	        type: "GET",
	        url: urlBase + "referencias/cidades",
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function (data) {
				console.log(data);
	        	cidades = data;		
	        	
	        	var cidadeId = globalUsuarioEnderecoCidadeId;
	        	console.log(cidadeId);
	        	console.log(cidadeId);
	        	$("#select-endereco-cidade").empty().append('<option selected="selected" value="-1">Selecione uma cidade</option>');
	        	var estadoId = 0;
	        	var paisId = 0;
	        	if (cidadeId > 0) {
					$.each(cidades, function (data, value) {
						if (cidadeId == value.id) {
							estadoId = value.estadoId;
							return;
						}
					});

					$.each(estados, function (data, value) {
						if (estadoId == value.id) {
							paisId = value.paisId;
							return;
						}
					});

					console.log(cidadeId);
					console.log(estadoId);
					console.log(paisId);
					$("#select-endereco-pais").val(paisId).change();
					$("#select-endereco-estado").val(estadoId).change();
					$("#select-endereco-cidade").val(cidadeId).change();
	        	}
				
				
	        },
	        failure: function (response) {
	            alert(response.d);
	        }
	    });
    }

}); 

function loadDadosEndereco() {
	
	if ( ($("#paisString").val() == null || $("#paisString").val() == "") &&
		 ($("#estadoString").val() == null || $("#estadoString").val() == "") &&
		 ($("#cidadeString").val() == null || $("#cidadeString").val() == "")) {			
	
		$("#select-endereco-pais").show();
		$("#paisString").hide();    	

		$("#estadoString").hide();
   		$("#indicar-cidade-string").prop("checked", false); 

		$("#cidadeString").hide();
 		if (!isDisableTodosCampos) {
	   		$("#indicar-cidade-string").prop("disabled", false); 
 		}
   		return true;
   		
	} else {
		
       	 $("#select-endereco-pais option").each(function () { 
       		 $(this).removeAttr("selected");
       	 });

       	 $("#select-endereco-estado option").each(function () { 
       		 $(this).removeAttr("selected");
       	 });

       	 $("#select-endereco-cidade option").each(function () { 
       		 $(this).removeAttr("selected");
       	 });
		
	     $("#select-endereco-pais").hide();
	     $("#paisString").show();
		 $("#panel-indicar-pais-string").show();
      	
       	$("#indicar-cidade-string").attr("checked", true);

	    $("#select-endereco-estado").hide();
	    $("#select-endereco-cidade").hide();
  		$("#estadoString").show(); 	
 		$("#cidadeString").show();
 		
		

        return false;

	} 
	
}

function loadClickIndicarEndereco() {
	
	$("#indicar-cidade-string").change(function(e) {

		$("#paisString").val("");
		$("#estadoString").val("");
		$("#cidadeString").val("");
		
		if (!this.checked) { 				

	       	$("#select-endereco-pais").show();
			$("#paisString").hide();
	   		$("#indicar-cidade-string").show();
	   		

	       	$("#select-endereco-estado").show();
			$("#estadoString").hide();

	       	$("#select-endereco-cidade").show();
			$("#cidadeString").hide();
	   		
		} else {
 
			$("#paisString").val($("#select-endereco-pais option:selected").text());
			if ($("#select-endereco-estado").val() >= 0) {
				$("#estadoString").val($("#select-endereco-estado option:selected").text());
			}
			if ($("#select-cidade-pais").val() >= 0) {
				$("#cidadeString").val($("#select-endereco-cidade option:selected").text());
			}

		    $("#select-endereco-pais").hide();
			$("#paisString").show();
	       	
	       	$("#select-endereco-estado").hide();
			$("#estadoString").show(); 
	   		
	       	$("#select-endereco-cidade").hide();
			$("#cidadeString").show();
			$("#panel-indicar-cidade-string").show();
	       	$("#indicar-cidade-string").prop("checked", true); 	   		
		}
	});
	
}

</script>	
<h5>Endereço</h5>
<div class="container border-top panel-custom"> 
   <div class="form-group">
       <label for="cep" class="col-md-12 control-label">Cep</label>
       <div class="col-md-9">
           <form:input path="cep" class="form-control" name="cep" placeholder="cep" disabled="${isDisableTodosCampos}" maxlength="20" />	
       </div>
   </div>                   
                    
   <div class="form-group">
       <label for="logradouro" class="col-md-12 control-label">Logradouro</label>
       <div class="col-md-9">
           <form:input path="logradouro" class="form-control" name="logradouro" placeholder="logradouro" disabled="${isDisableTodosCampos}" maxlength="100"/>
       </div>
   </div>                 
                    
   <div class="form-group">
       <label for="numerologradouro" class="col-md-12 control-label">Numero</label>
       <div class="col-md-9">
           <form:input path="numeroLogradouro" class="form-control" name="numeroLogradouro" placeholder="numero" disabled="${isDisableTodosCampos}" maxlength="20"/>
       </div>
   </div>                           
                    
   <div class="form-group">
       <label for="bairro" class="col-md-12 control-label">Bairro</label>
       <div class="col-md-9">
           <form:input path="bairro" class="form-control" name="bairro" placeholder="bairro" disabled="${isDisableTodosCampos}" maxlength="100"/>
       </div>
   </div>                             
                    
   <div class="form-group">
       <label for="informacoesadicionaisendereco" class="col-md-12 control-label">Informações adicionais </label>
       <div class="col-md-9">
           <form:input path="informacoesAdicionaisEndereco" class="form-control" name="informacoesAdicionaisEndereco" placeholder="informações adicionais" disabled="${isDisableTodosCampos}" maxlength="100"/>
       </div>
   </div>                          
                    
   <div class="form-group">
       <label for="pais" class="col-md-12 control-label">Pais</label>
       <div class="col-md-9">
           <select id="select-endereco-pais" name="pais" class="form-control editable-select" <c:if test="${isDisableTodosCampos}">disabled</c:if>>
		       <c:forEach items="${paises}" var="pais">
		           <option value="${pais.id}">${pais.nome}</option>
		       </c:forEach>
		   </select> 	
		   <form:input path="paisString" class="form-control" disabled="${isDisableTodosCampos}" maxlength="100" />
       </div>
   </div>                
                    
   <div class="form-group">
       <label for="estado" class="col-md-12 control-label">Estado</label>
       <div class="col-md-9">
         <select id="select-endereco-estado" name="estado" class="form-control editable-select" <c:if test="${isDisableTodosCampos}">disabled</c:if>></select>
         <form:input path="estadoString" class="form-control" disabled="${isDisableTodosCampos}" maxlength="150" />
   		</div>
   </div>
                    
   <div class="form-group"> 
   		<label for="cidadeId" class="col-md-12 control-label">
			<span>Cidade</span> 
			</label>
       <div class="col-md-9">
         <form:select path="cidadeId" id="select-endereco-cidade" name="cidade" class="form-control editable-select" disabled="${isDisableTodosCampos}" />
		 <form:input path="cidadeString" class="form-control" disabled="${isDisableTodosCampos}" maxlength="150" />
		 <span id="panel-indicar-cidade-string" class="row" style="font-size: 10pt">
			<input type="checkbox"
				   id="indicar-cidade-string" 
				   <c:if test="${isDisableTodosCampos}">disabled </c:if>
				   class="form-control float-right"
				   style="margin: 5px 0 0 25px; width: 15px;" /> não encontrada</span>
       </div>
   </div>
       
 </div>
		