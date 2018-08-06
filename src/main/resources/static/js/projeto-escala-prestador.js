

$(function() {   	

	$( "#form-prestador .datepicker" ).datepicker({format: 'dd/mm/yyyy'});
	 $("#btn-editar-prestador").show();
	 $("#btn-salvar-prestador").hide();
	 $("#btn-cancelar-prestador").show();
	 
     $("#tabela-prestador tbody td.rowclick").click(function(e){
		selectPrestador($(this).closest("tr").attr("id").replace("prestador", ""));	
     });
     
     $("#btn-cancelar-prestador").click(function(e){
    	 limparPrestador(true);
		 $("#btn-editar-prestador").val("Novo");
		 $("#btn-editar-prestador").show();
		 $("#btn-salvar-prestador").hide();
      });
     
     $('#projetoEscala').on('change',function() { // input[name='projetoEscala.id'"
    	console.log(this.value);
 		window.location.href = urlBase + "escala/selecionar/" + this.value;
	 });
	 
     if ($("#form-prestador #id").val() == 0) {
    	 limparPrestador();
    	 $("#form-prestador input[id='check-box-data-fim']").prop("checked", false);
    	 $("#form-prestador #dataFim").prop("disabled", true);
     }
     else {
    	 $("#btn-editar-prestador").val("Editar");  
         $("#prestador" + $("#form-prestador #id").val()).addClass('selected').siblings().removeClass('selected');
         editarPrestador();
    	 selectTabelaFolga($("#form-prestador #id").val(), true);
    	 //$("#tabela-folga-semanal input").prop("disabled", false);
    	 //editarFolga();
     }

 	var checkBoxDataFim = "";
 	if ($("#form-prestador #dataFim").val() != ""){
 		$("#form-prestador #check-box-data-fim").prop("checked", true);	
 	}
 	else{
		$("#form-prestador #dataFim").prop("disabled", true); 
 	}

    $("select[name='prestador.id']").change(function() {
   		escalaFolgaSugeridaPrestador(this.value);
	});
 	
  	$("#form-prestador #check-box-data-fim").click(function(e){
  		if (!$(this).prop("checked")){
  			checkBoxDataFim = $("#form-prestador #dataFim").val();
  			$("#form-prestador #dataFim").val("");
  		}
  		else {
  			$("#form-prestador #dataFim").val(checkBoxDataFim);
  		}
  		
 		$("#form-prestador #dataFim").prop("disabled", !$(this).prop("checked"));
  	});  


    $("#indicar-folga-semanal").click(function(e) {
   	 if ($(this).prop("checked")) {
   	 	$("#panel-folga-semanal").show();
   	 }
   	 else {
   		 $("#panel-folga-semanal").hide();
   	 }
    });
    
   	limparFolga(); 
    disabilitarFolga(true);
    //$("#indicar-folga-semanal").prop("disabled", true);

	 $("#form-prestador #projeto.id").val($("#id").html());
	 $("#panel-reenviar-convite").hide();

	 $("#btn-editar-prestador").click(function() {
		 editarPrestador();
	 });
	 


     $("#btn-editar-folga").click(function(e){	        	  
    	 editarFolga();
     });
});



function editarFolga() {
	 $("#folgaHoraInicio").prop("disabled", false);
	 $("#folgaHoraFim").prop("disabled", false);
	 $("#folgaDiaSemana").prop("disabled", false);
	 $("#folgaMotivo").prop("disabled", false);

	 $("#btn-editar-folga").hide();
	 $("#btn-salvar-folga").show();
	 $("#form-prestador #btn-salvar-prestador").prop("disabled", true);	
}

function editarPrestador() {
	 $("#btn-editar-prestador").hide();
	 $("#btn-salvar-prestador").show();
	 
	 if ($("#btn-editar-prestador").val() == "Novo") {
		 $("#form-prestador input[name='dataInicio']").val($("#form-projeto input[name='dataInicio']").val());
		 $("#form-prestador #check-box-data-fim").val($("#form-projeto #check-box-data-fim").val());
		 $("#form-prestador input[name='ativo']").prop("checked", true);
         $("#form-prestador #id").val(-1);
	 }
	 
	 $("#form-prestador #indicar-folga-semanal").prop("disabled", false);
	   	
	 $("#funcaoId").prop("disabled", false);
	 $("#ramalIntegracaoRobo").prop("disabled", false);
	 $("select[name='prestador.id']").prop("disabled", false);
	 $("#form-prestador input[name='dataInicio']").prop("disabled", false);
	 $("#form-prestador #check-box-data-fim").prop("disabled", false);
	 $("#form-prestador input[name='ativo']").prop("disabled", false);
	 $("#form-prestador #observacaoPrestador").prop("disabled", false);

	 $("#folgaHoraInicio").prop("disabled", true);
	 $("#folgaHoraFim").prop("disabled", true);
	 $("#folgaDiaSemana").prop("disabled", true);
	 $("#folgaMotivo").prop("disabled", true);

	 $("#btn-editar-folga").val("Nova folga");
	 $("#btn-editar-folga").prop("disabled", false);
	 $("#btn-salvar-folga").prop("disabled", false);
	 $("#btn-cancelar-folga").prop("disabled", false);

	 $("#folgaHoraInicio").prop("disabled", false);
	 $("#folgaHoraFim").prop("disabled", false);
	 $("#folgaDiaSemana").prop("disabled", false);
	 $("#folgaMotivo").prop("disabled", false);
	 $("#tabela-folga-semanal input").prop("disabled", false);
	 
	 limparFolga();
}

function selectPrestador(item) {
	
	 limparPrestador();

	 $("#btn-editar-prestador").val("Editar");
	 
	 console.log("#form-prestador #aceito");
	 console.log($("#form-prestador #aceito"+item).val());
	 if ($("#form-prestador #aceito"+item).val() != 0) {
		 $("#panel-reenviar-convite").show();
	 }
	 else {
		 $("#panel-reenviar-convite").hide();		 
	 }
			
	 $("#form-prestador #id").val(item);
	 $("#form-prestador #projeto.id").val($("#form-prestador #projeto.id"+item).html());
	
	 $("#form-prestador input[name='dataInicio']").val($("#form-prestador #dataInicio"+item).html().trim());
	 console.log($("#form-prestador #dataFim"+item).val()); 
	 
	 $("#form-prestador #check-box-data-fim").prop("checked", $("#form-prestador #dataFim"+item).val() != "");
	 $("#form-prestador input[name='dataFim']").prop("disabled", true);
	 
	 $("#form-prestador input[name='dataFim']").val($("#form-prestador #dataFim"+item).val().trim());
	 
	 $("#form-prestador #observacaoPrestador").val($("#observacaoPrestador"+item).html());
	 $("#form-prestador input[name='ativo']").prop("checked", JSON.parse($("#prestadorAtivo"+item).val()));
	 
	 $("#funcaoId option").each(function () { 
         $(this).removeAttr("selected");
 		if ($(this).html() == $("#funcao"+item).html()) {
            $(this).attr("selected", "selected");
        }
	 });

	 selectTabelaFolga(item);
	 //$("#form-prestador input[name='indicadaFolgaSemana']").prop("checked", JSON.parse($("#indicadaFolgaSemana"+item).val()));

//   	 if ($("#form-prestador input[name='indicadaFolgaSemana']").prop("checked")) {
//   		 selectTabelaFolga(item);
//   	 	$("#panel-folga-semanal").show();
//   	 }
//   	 else {
//   		 $("#panel-folga-semanal").hide();
//   	 }

	 //$("#form-prestador input[id='prestador.id']").val($("#form-prestador #prestador"+item).html());
//	 $("select[name='prestador.id'] option").each(function () { 
//		 console.log($("#prestadorId"+item).val());
// 		if ($(this).val() == $("#prestadorId"+item).val()) {
//            $(this).attr("selected", "selected");
//        }
//	 });

	 $("select[name='prestador.id'] option").each(function () { 
		 console.log($("#form-prestador #prestador.id"+item).val());
         $(this).removeAttr("selected");
 		if ($(this).val() == $("#form-prestador input[id='prestador.id"+item+"']").val()) {
            $(this).attr("selected", "selected");
        }
	 });

	 escalaFolgaSugeridaPrestador($("#form-prestador input[id='prestador.id"+item+"']").val());
	 
	 $("#form-prestador #escala-aceite").html($("#form-prestador #escala-aceite"+item).html().trim());
	 
     $("#folgaId").val(null);
	 //$("#indicar-folga-semanal").prop("checked", false);
   	 ativaFolgaClicks();
     disabilitarFolga(false);
	 $("#btn-editar-prestador").val("Editar");
	 $("#btn-editar-prestador").show();
	 $("#btn-salvar-prestador").hide();
     
	 
 }

function escalaFolgaSugeridaPrestador(id) {
	console.log(id);
	if (id == 0) {
		$("#escala-sugerida").html("");
		$("#folga-sugerida").html("");
		return;
	}
	
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : urlBase + "projeto/escalafolgasugerida/" + id,
		dataType: 'json',
		success : function(data) {
	
			console.log("success: "+ JSON.stringify(data));
			$("#escala-sugerida").html(data[0]);
			$("#folga-sugerida").html(data[1]);
		},
		error : function(e) {
			alert("Error!")
			console.log("ERROR: ", e);
		}
	});	
	
}

 function limparPrestador(limparCache) {

	 $("#form-prestador #id").val(0);
	 $("#form-prestador input[name='dataInicio']").val("");
	 $("#form-prestador input[name='dataFim']").val("");
	 $("#observacaoPrestador").val("");
	 $("#form-prestador input[name='ativo']").prop("checked", true);
	 $("#form-prestador #check-box-data-fim").prop("checked", false);
	 $("#form-prestador #dataFim").prop("disabled", true);
	 $("#form-prestador #escala-aceite").html("");
	 $("#form-prestador #indicar-folga-semanal").prop("disabled", true);
	 $("#btn-editar-folga").prop("disabled", true);
	 $("#btn-salvar-folga").prop("disabled", true);
	 $("#btn-cancelar-folga").prop("disabled", true);

	$("#panel-reenviar-convite").hide();
		
	$("#escala-sugerida").html("");
	$("#folga-sugerida").html("");
	
     $("#tabela-prestador tr").removeClass('selected');

	 $("#funcaoId option").each(function () { 
		 $(this).removeAttr("selected");
	 });

	 $("select[name='prestador.id'] option").each(function () { 
		 $(this).removeAttr("selected");
	 });
	 
     $("#form-prestador .form-control").removeClass("is-invalid");

     
	 //$("#funcaoId").val("").change();
	 //$("select[name='prestador.id']").val("").change();	
	 
     limparFolga();
     disabilitarFolga(true);
     if (limparCache) {
     	selectTabelaFolga(0);
     }
     
	 $("#funcaoId").prop("disabled", true);
	 $("#ramalIntegracaoRobo").prop("disabled", true);
	 $("select[name='prestador.id']").prop("disabled", true);
	 $("#form-prestador input[name='dataInicio']").prop("disabled", true);
	 $("#form-prestador #check-box-data-fim").prop("disabled", true);
	 $("#form-prestador input[name='ativo']").prop("disabled", true);
	 $("#form-prestador #observacaoPrestador").prop("disabled", true); 
	 
 }
 
 function deletePrestador(id) {
	if (confirm("Deseja realmente apagar este prestador?")) {
		window.location.href = urlBase + "prestador/delete/" + id;
	}
 }
 
 
 
 


 function ativaFolgaClicks() {
      
      $("#tabela-folga-semanal tbody td.rowclick").click(function(e){
     	 selectFolga($(this).closest("tr").attr("id").replace("folga", ""));	
      });

      $("#tabela-folga-semanal tbody td input").click(function(e){	        	  
 		deleteFolgaRequest($(this).closest("tr").attr("id").replace("folga", ""));	
      });
      
 }

 function selectFolga(item){
 	 
 	 limparFolga();

 	 $("#folgaId").val(item);
 	 $("#folgaHoraInicio").val($("td#folgaHoraInicio"+item).html());
 	 $("#folgaHoraFim").val($("td#folgaHoraFim"+item).html());
 	 
 	 $("#folgaDiaSemana option").each(function () { 
 		if ($(this).html() == $("td#folgaDiaSemana"+item).html()) {
 			this.selected = true;
        }
 	 });

 	 $("#folgaMotivo option").each(function () { 
 		if ($(this).html() == $("td#folgaMotivo"+item).html()) {
 			this.selected = true;
        }
 	 }); 

	 $("#folgaHoraInicio").prop("disabled", true);
	 $("#folgaHoraFim").prop("disabled", true);
	 $("#folgaDiaSemana").prop("disabled", true);
	 $("#folgaMotivo").prop("disabled", true);

	 $("#btn-editar-folga").val("Editar");

 }
 
 function selectTabelaFolga(id, habilitarFolga) {
	 
 	console.log(urlBase + "projeto/folgasSemanais/" + id);
 	
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : urlBase + "projeto/folgasSemanais/" + id,
		dataType: 'json',
		success : function(data) {
	
			$('#tabela-folga-semanal tbody').html(""); 

			for (var i=0;i<data.length;++i) {
				var dataItem = data[i];

				var row = 
 					  '<tr id="folga' + dataItem.id  + '"">' +
						'<td class="rowclick" id="folgaMotivo' + dataItem.id  + '" >' + dataItem.motivo.nome  + '</td></td>' +
						'<td class="rowclick" id="folgaDiaSemana' + dataItem.id  + '" >' + dataItem.diaSemana.nome  + '</td></td>' +
						'<td class="rowclick" id="folgaHoraInicio' + dataItem.id  + '" >' + dataItem.horaInicio  + '</td></td>' +
						'<td class="rowclick" id="folgaHoraFim' + dataItem.id  + '" >' + dataItem.horaFim  + '</td></td>' +
						'<td><input type="button" class="btn btn-sm btn-danger" ' + (habilitarFolga ? '' : 'disabled ') + 'value="apagar" /></td>'
					  '</tr>';
					  
			   $('#tabela-folga-semanal tbody').append(row);
			  
			}

			if (data.length > 0) {
	    	 	$("#indicar-folga-semanal").prop("checked", true);
	    	 	$("#panel-folga-semanal").show();
			   	ativaFolgaClicks();
			}
			else {
	    	 	$("#indicar-folga-semanal").prop("checked", false);
	    	 	$("#panel-folga-semanal").hide();
			}
			
		    limparFolga();

		},
		error : function(e) {
			alert("Error!")
			console.log("ERROR: ", e);
        	//$("btn-salvar-folga").prop("disabled",false);
		}
	});
 }

 function salvarFolga() { 	
	
	$("#folgaDiaSemana").removeClass("is-invalid");
	$("#folgaMotivo").removeClass("is-invalid");
	
	$("#feedback-folgaHoraInicio").val("");
	$("#feedback-folgaHoraFim").val("");
	$("#folgaHoraInicio").removeClass("is-invalid");
	$("#folgaHoraFim").removeClass("is-invalid");


  	if ($("#folgaMotivo").val() == "" || 
  	    $("#folgaMotivo").val() == "0" ||
  	    $("#folgaMotivo").val() == null) {
  		$("#folgaMotivo").addClass("is-invalid");
  		$("#feedback-folgaMotivo").html("Preencha o campo motivo");
  		return;
  	}

  	if ($("#folgaDiaSemana").val() == "" || 
   	    $("#folgaDiaSemana").val() == "0" ||
   	    $("#folgaDiaSemana").val() == null) {
  		
  		$("#folgaDiaSemana").addClass("is-invalid");
  		$("#feedback-folgaDiaSemana").html("Preencha o campo dia da semana");
  		return;
   	}
  	
 	if ($("#folgaHoraInicio").val().length <  5) { 		
 		$("#folgaHoraInicio").addClass("is-invalid");
 		$("#feedback-folgaHoraFim").html("Preencha o campo hora início");
 		return;
 	}

 	if ($("#folgaHoraFim").val().length <  5) { 		
 		$("#folgaHoraFim").addClass("is-invalid");
 		$("#feedback-folgaHoraFim").html("Preencha o campo hora fim");
 		return;
 	}


 	if (parseInt($("#folgaHoraInicio").val().replace(":", "")) > parseInt($("#folgaHoraFim").val().replace(":", ""))) { 		
 		$("#folgaHoraFim").addClass("is-invalid");
 		$("#feedback-folgaHoraFim").html("A hora início deve ser menor do que a hora fim");
 		return;
 	}
 	
 	
 	if (!/^(([0-1][0-9])||([2][0-3])):[0-5][0-9]$/.test($("#folgaHoraInicio").val())) {
 		$("#folgaHoraInicio").addClass("is-invalid");
 		$("#feedback-folgaHoraInicio").html("Preencha o campo hora início corretamente");
 		return;
 	}

 	if (!/^(([0-1][0-9])||([2][0-3])):[0-5][0-9]$/.test($("#folgaHoraFim").val())) {
 		$("#folgaHoraFim").addClass("is-invalid");
 		$("#feedback-folgaHoraFim").html("Preencha o campo hora fim corretamente");
 		return;
 	}

 	var folgaDiaSemanaSelected = $("#folgaDiaSemana option:selected");
 	var folgaMotivoSelected =  $("#folgaMotivo option:selected");
 	
	 	console.log("#folgaId");
	 	var item = {
			id : $("#folgaId").val(),
			diaSemana : { id: folgaDiaSemanaSelected.val(), nome: folgaDiaSemanaSelected.text() },
			motivo : { id: folgaMotivoSelected.val(), nome: folgaMotivoSelected.text() },
			horaInicio : $("#folgaHoraInicio").val(), 
			horaFim : $("#folgaHoraFim").val(),
			projetoEscalaPrestadorId: $("#form-prestador #id").val()
 	};
	 	
 	console.log(JSON.stringify(item));

	$.ajax({
		type : "POST",
		contentType : "application/json",
		accept: 'text/plain',
		url : urlBase + "projeto/folgaSemanal",
		data : JSON.stringify(item),
		dataType: 'text',
		success : function(id) {
	
			//$("#folgaId").html(id);
			console.log("success: "+item.id);
			 
			if (item.id == null || item.id == "") {
 				var row = 
 					  '<tr id=folga' + id  + '>' +
						'<td class="rowclick" id="folgaMotivo' + id  + '" >' + item.motivo.nome  + '</td></td>' +
						'<td class="rowclick" id="folgaDiaSemana' + id  + '" >' + item.diaSemana.nome  + '</td></td>' +
						'<td class="rowclick" id="folgaHoraInicio' + id  + '" >' + item.horaInicio  + '</td></td>' +
						'<td class="rowclick" id="folgaHoraFim' + id  + '" >' + item.horaFim  + '</td></td>' +
						'<td><input type="button" class="btn-apagar-folga btn btn-sm btn-danger" value="apagar" /></td>'
					  '</tr>';

 			   $('#tabela-folga-semanal tbody tr.odd').remove();	
			   $('#tabela-folga-semanal tbody').prepend(row);
    		}
		    else {
		    	$("#tabela-folga-semanal tbody #folgaMotivo" + item.id).html(item.motivo.nome);
		    	$("#tabela-folga-semanal tbody #folgaDiaSemana" + item.id).html(item.diaSemana.nome);
		    	$("#tabela-folga-semanal tbody #folgaHoraInicio" + item.id).html(item.horaInicio);
		    	$("#tabela-folga-semanal tbody #folgaHoraFim" + item.id).html(item.horaFim);
		    }

			limparFolga();
		   	ativaFolgaClicks();

		},
		error : function(e) {
			alert("Error!")
			console.log("ERROR: ", e);
        	$("btn-salvar-folga").prop("disabled",false);
		}
	});
 }
 
 function limparFolga() {
	 
	 $("#folgaId").val(null);
	 $("#folgaHoraInicio").val("");
	 $("#folgaHoraFim").val("");
	 
	 $("#btn-salvar-prestador").prop("disabled", false);
	 
	 $("#btn-editar-folga").val("Nova folga");
	 $("#btn-editar-folga").show();
	 $("#btn-salvar-folga").hide();
	 
	 $("#tabela-folga-semanal tr").removeClass('selected');
	 
	 $("#folgaDiaSemana option").each(function () { 
		 $(this).removeAttr("selected");
	 });
	
	 $("#folgaMotivo option").each(function () { 
		 $(this).removeAttr("selected");
	 });	
	
	 $("#folgaMotivo").val("").change();
	 $("#folgaDiaSemana").val("").change();
	
	$("#folgaDiaSemana").removeClass("is-invalid");
	$("#folgaMotivo").removeClass("is-invalid");
	
	$("#feedback-folgaHoraInicio").val("");
	$("#feedback-folgaHoraFim").val("");
	$("#folgaHoraInicio").removeClass("is-invalid");
	$("#folgaHoraFim").removeClass("is-invalid");

	 $("#folgaHoraInicio").prop("disabled", true);
	 $("#folgaHoraFim").prop("disabled", true);
	 $("#folgaDiaSemana").prop("disabled", true);
	 $("#folgaMotivo").prop("disabled", true);
}

 function deleteFolgaRequest(id) {	
	 
 	if (!confirm("Deseja realmente apagar este item?")) {
 		return;
 	}
 	
	$("btn-salvar-folga").prop("disabled",true);
	$("btn-cancelar-folga").prop("disabled",true);       	

 	$.ajax({
		type : "DELETE",
		contentType : "application/json",
		accept: 'text/plain',
		url : urlBase + "projeto/folgaSemanal",
		data : id,
		dataType: 'text',
		success : function(id) {
			$("#folgaId").html(id);
			
			console.log("success: ");

			$("#tabela-folga-semanal tbody tr#folga" + id).remove();
		    
			limparFolga();
		   	 
		},
		error : function(e) {
			alert("Error!")
			console.log("ERROR: ", e);
        	$("btn-salvar-folga").prop("disabled",false);
		}
	});   
 }
 
 function disabilitarFolga(enable) {
	 
	if (isDisableCamposPrestador) {
		return;
	}
	
//	$("#btn-salvar-folga").prop("disabled", enable);
//	$("#btn-cancelar-folga").prop("disabled", enable);    
//	
//	$("#folgaDiaSemana").prop("disabled", enable);
//	$("#folgaMotivo").prop("disabled", enable);		
//	$("#folgaHoraInicio").prop("disabled", enable);
//	$("#folgaHoraFim").prop("disabled", enable);
	
	$("#folgaDiaSemana").removeClass("is-invalid");
	$("#folgaMotivo").removeClass("is-invalid");
	
	$("#feedback-folgaHoraInicio").val("");
	$("#feedback-folgaHoraFim").val("");
	$("#folgaHoraInicio").removeClass("is-invalid");
	$("#folgaHoraFim").removeClass("is-invalid");
    //$("#indicar-folga-semanal").prop("disabled", enable);
    $(".btn-apagar-folga").prop("disabled", enable);
 }