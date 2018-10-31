

$(function() {   	

	$( "#form-prestador .datepicker" ).datepicker({format: 'dd/mm/yyyy'});
	 $("#btn-editar-prestador").show();
	 $("#btn-salvar-prestador").hide();
	 $("#btn-cancelar-prestador").show();
	 
     $("#tabela-prestador tbody td.rowclick").click(function(e){
		selectPrestador($(this).closest("tr").attr("id").replace("prestador", ""));	
     });  

   	if (!$("#form-prestador #chk-dias-horas-trabalho-customizado").prop("checked")){
 		$("#form-prestador #panel-dias-horas-trabalho-customizado").hide();
 	}
 	else {
 		$("#form-prestador #panel-dias-horas-trabalho-customizado").show();
 	}
     
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
  	
  	$("#form-prestador #chk-dias-horas-trabalho-customizado").click(function(e){
  		//limparDiasHoras();
        //setCamposJsonDiasHorasTrabalho($("#form-prestador #json-prestador-dias-horas").val());
  		if (!$(this).prop("checked")){
  			$("#form-prestador #panel-dias-horas-trabalho-customizado").hide();
  		}
  		else {
  			$("#form-prestador #panel-dias-horas-trabalho-customizado").show();
  		}
  	}); 
  	

	 $("#form-prestador #btn-salvar-prestador").click(function(e) {
	   	 if (!$("#chk-dias-horas-trabalho-customizado").prop("checked")) {
	   		 limparDiasHoras(true);
    	 }
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

function setCamposJsonDiasHorasTrabalho(jsonDiasHorasTrabalho) {
	if (jsonDiasHorasTrabalho != null && jsonDiasHorasTrabalho != "") {
		 var json = JSON.parse(jsonDiasHorasTrabalho);
		 $("#form-prestador #segunda-id").val(json[0] == null ? "" : json[0].id);
		 $("#form-prestador #segunda-diaSemana").val(json[0] == null ? "" : json[0].diaSemana);
		 $("#form-prestador #segunda-hora-inicio").val(json[0] == null ? "" : json[0].horaInicio);
		 $("#form-prestador #segunda-hora-fim").val(json[0] == null ? "" : json[0].horaFim);

		 $("#form-prestador #terca-id").val(json[1] == null ? "" : json[1].id);
		 $("#form-prestador #terca-diaSemana").val(json[1] == null ? "" : json[1].diaSemana);
		 $("#form-prestador #terca-hora-inicio").val(json[1] == null ? "" : json[1].horaInicio);
		 $("#form-prestador #terca-hora-fim").val(json[1] == null ? "" : json[1].horaFim);

		 $("#form-prestador #quarta-id").val(json[2] == null ? "" : json[2].id);
		 $("#form-prestador #quarta-diaSemana").val(json[2] == null ? "" : json[2].diaSemana);
		 $("#form-prestador #quarta-hora-inicio").val(json[2] == null ? "" : json[2].horaInicio);
		 $("#form-prestador #quarta-hora-fim").val(json[2] == null ? "" : json[2].horaFim);

		 $("#form-prestador #quinta-id").val(json[3] == null ? "" : json[3].id);
		 $("#form-prestador #quinta-diaSemana").val(json[3] == null ? "" : json[3].diaSemana);
		 $("#form-prestador #quinta-hora-inicio").val(json[3] == null ? "" : json[3].horaInicio);
		 $("#form-prestador #quinta-hora-fim").val(json[3] == null ? "" : json[3].horaFim);

		 $("#form-prestador #sexta-id").val(json[4] == null ? "" : json[4].id);
		 $("#form-prestador #sexta-diaSemana").val(json[4] == null ? "" : json[4].diaSemana);
		 $("#form-prestador #sexta-hora-inicio").val(json[4] == null ? "" : json[4].horaInicio);
		 $("#form-prestador #sexta-hora-fim").val(json[4] == null ? "" : json[4].horaFim);

		 $("#form-prestador #sabado-id").val(json[5] == null ? "" : json[5].id);
		 $("#form-prestador #sabado-diaSemana").val(json[5] == null ? "" : json[5].diaSemana);
		 $("#form-prestador #sabado-hora-inicio").val(json[5] == null ? "" : json[5].horaInicio);
		 $("#form-prestador #sabado-hora-fim").val(json[5] == null ? "" : json[5].horaFim);

		 $("#form-prestador #domingo-id").val(json[6] == null ? "" : json[6].id);
		 $("#form-prestador #domingo-diaSemana").val(json[6] == null ? "" : json[6].diaSemana);
		 $("#form-prestador #domingo-hora-inicio").val(json[6] == null ? "" : json[6].horaInicio);
		 $("#form-prestador #domingo-hora-fim").val(json[6] == null ? "" : json[6].horaFim);
	 }
}

function editarPrestador() {
	 $("#btn-editar-prestador").hide();
	 $("#btn-salvar-prestador").show();
	 
	 if ($("#btn-editar-prestador").val() == "Novo") {
		 $("#form-prestador input[name='dataInicio']").val($("#form-projeto input[name='dataInicio']").val());
		 $("#form-prestador #check-box-data-fim").val($("#form-projeto #check-box-data-fim").val());
		 $("#form-prestador input[name='ativo']").prop("checked", true);
         $("#form-prestador #id").val(-1);
         
         setCamposJsonDiasHorasTrabalho($("#form-prestador #json-prestador-dias-horas").val());
    	 
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
	 

	 $("#form-prestador #chk-dias-horas-trabalho-customizado").prop("disabled", false); 

	 $("#form-prestador #segunda-hora-inicio").prop("disabled", false); 
	 $("#form-prestador #segunda-hora-fim").prop("disabled", false); 
	 
	 $("#form-prestador #terca-hora-inicio").prop("disabled", false); 
	 $("#form-prestador #terca-hora-fim").prop("disabled", false); 
	 
	 $("#form-prestador #quarta-hora-inicio").prop("disabled", false); 
	 $("#form-prestador #quarta-hora-fim").prop("disabled", false); 
	 
	 $("#form-prestador #quinta-hora-inicio").prop("disabled", false); 
	 $("#form-prestador #quinta-hora-fim").prop("disabled", false); 
	 
	 $("#form-prestador #sexta-hora-inicio").prop("disabled", false); 
	 $("#form-prestador #sexta-hora-fim").prop("disabled", false); 
	 
	 $("#form-prestador #sabado-hora-inicio").prop("disabled", false); 
	 $("#form-prestador #sabado-hora-fim").prop("disabled", false); 
	 
	 $("#form-prestador #domingo-hora-inicio").prop("disabled", false); 
	 $("#form-prestador #domingo-hora-fim").prop("disabled", false); 
	 
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
	
	 var jsonDiasHorasTrabalho = $("#form-prestador #json-prestador-dias-horas"+item).val();
	 
     
	 if (jsonDiasHorasTrabalho == null || jsonDiasHorasTrabalho == "")
	 {
		 jsonDiasHorasTrabalho = $("#form-prestador #json-prestador-dias-horas").val();
	 }	 

	 $("#form-prestador #chk-dias-horas-trabalho-customizado").prop("checked", $("#form-prestador #chk-dias-horas-trabalho-customizado"+item).val() == "true"); 
	 if ( $("#form-prestador #chk-dias-horas-trabalho-customizado").prop("checked")) {
		 $("#form-prestador #panel-dias-horas-trabalho-customizado").show();
	 }
	 else {
		 $("#form-prestador #panel-dias-horas-trabalho-customizado").hide();
	 }
     
     setCamposJsonDiasHorasTrabalho(jsonDiasHorasTrabalho);	 

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
	 
	 $("#ramalIntegracaoRobo").val($("#ramalIntegracaoRobo"+item).val())
	 selectTabelaFolga(item);

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
	 $("#ramalIntegracaoRobo").val("")
	 $("#form-prestador input[name='ativo']").prop("checked", true);
	 $("#form-prestador #check-box-data-fim").prop("checked", false);
	 $("#form-prestador #dataFim").prop("disabled", true);
	 $("#form-prestador #escala-aceite").html("");
	 $("#form-prestador #indicar-folga-semanal").prop("disabled", true);
	 $("#btn-editar-folga").prop("disabled", true);
	 $("#btn-salvar-folga").prop("disabled", true);
	 $("#btn-cancelar-folga").prop("disabled", true);

	 $("#form-prestador #chk-dias-horas-trabalho-customizado").prop("checked", false);
	 
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
	 

	 $("#form-prestador #chk-dias-horas-trabalho-customizado").prop("checked", false); 

	 $("#form-prestador #chk-dias-horas-trabalho-customizado").prop("disabled", true); 
	 
	 $("#form-prestador #segunda-hora-inicio").prop("disabled", true); 
	 $("#form-prestador #segunda-hora-fim").prop("disabled", true); 
	 
	 $("#form-prestador #terca-hora-inicio").prop("disabled", true); 
	 $("#form-prestador #terca-hora-fim").prop("disabled", true); 
	 
	 $("#form-prestador #quarta-hora-inicio").prop("disabled", true); 
	 $("#form-prestador #quarta-hora-fim").prop("disabled", true); 
	 
	 $("#form-prestador #quinta-hora-inicio").prop("disabled", true); 
	 $("#form-prestador #quinta-hora-fim").prop("disabled", true); 
	 
	 $("#form-prestador #sexta-hora-inicio").prop("disabled", true); 
	 $("#form-prestador #sexta-hora-fim").prop("disabled", true); 
	 
	 $("#form-prestador #sabado-hora-inicio").prop("disabled", true); 
	 $("#form-prestador #sabado-hora-fim").prop("disabled", true); 
	 
	 $("#form-prestador #domingo-hora-inicio").prop("disabled", true); 
	 $("#form-prestador #domingo-hora-fim").prop("disabled", true); 
	 

	 limparDiasHoras();
 }
 
 function limparDiasHoras(onlyHoras) {
	 onlyHoras = onlyHoras == null ? false : onlyHoras; 

	 if (!onlyHoras) { $("#form-prestador #segunda-id").val(""); }
	 $("#form-prestador #segunda-hora-inicio").val("");
	 $("#form-prestador #segunda-hora-fim").val("");

	 if (!onlyHoras) { $("#form-prestador #terca-id").val(""); }
	 $("#form-prestador #terca-hora-inicio").val("");
	 $("#form-prestador #terca-hora-fim").val("");

	 if (!onlyHoras) { $("#form-prestador #quarta-id").val(""); }
	 $("#form-prestador #quarta-hora-inicio").val("");
	 $("#form-prestador #quarta-hora-fim").val("");

	 if (!onlyHoras) { $("#form-prestador #quinta-id").val(""); }
	 $("#form-prestador #quinta-hora-inicio").val("");
	 $("#form-prestador #quinta-hora-fim").val("");

	 if (!onlyHoras) { $("#form-prestador #sexta-id").val(""); }
	 $("#form-prestador #sexta-hora-inicio").val("");
	 $("#form-prestador #sexta-hora-fim").val("");

	 if (!onlyHoras) { $("#form-prestador #sabado-id").val(""); }
	 $("#form-prestador #sabado-hora-inicio").val("");
	 $("#form-prestador #sabado-hora-fim").val("");

	 if (!onlyHoras) { $("#form-prestador #domingo-id").val(""); }
	 $("#form-prestador #domingo-hora-inicio").val("");
	 $("#form-prestador #domingo-hora-fim").val("");
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
			console.log("ERROR: ", e);S
		}
	});   
 }
 
 function disabilitarFolga(enable) {
	 
	if (isDisableCamposPrestador) {
		return;
	}
	
	$("#folgaDiaSemana").removeClass("is-invalid");
	$("#folgaMotivo").removeClass("is-invalid");
	
	$("#feedback-folgaHoraInicio").val("");
	$("#feedback-folgaHoraFim").val("");
	$("#folgaHoraInicio").removeClass("is-invalid");
	$("#folgaHoraFim").removeClass("is-invalid");
    $(".btn-apagar-folga").prop("disabled", enable);
 }