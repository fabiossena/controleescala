
	    $(function() {
		   	 
		   	limparFolga();
		   	ativaClicks();
	    	
	    });

	    function ativaClicks() {
	         
	         $("#tabela-folga-semanal-planejada tbody td.rowclick").click(function(e){
        		select($(this).closest("tr").attr("id").replace("folga", ""));	
	         });
	    
	         $("#tabela-folga-semanal-planejada tbody td input").click(function(e){	        	  
        		deleteRequest($(this).closest("tr").attr("id").replace("folga", ""));	
	         });
	    }

	    function select(item) {
	    	 
	    	 limparFolga();

			 $("#folgaId").val(item);
	    	 $("#folgaHoraInicio").val($("td#folgaHoraInicio"+item).html());
	    	 $("#folgaHoraFim").val($("td#folgaHoraFim"+item).html());
	    	 
	    	 $("#folgaDiaSemana option").each(function () { 
   		 		if ($(this).html() == $("td#folgaDiaSemana"+item).html()) {
    	            $(this).attr("selected", "selected");
    	        }
	    	 });

	    	 $("#folgaMotivo option").each(function () { 
   		 		if ($(this).html() == $("td#folgaMotivo"+item).html()) {
    	            $(this).attr("selected", "selected");
    	        }
	    	 });
	     }

	     function deleteRequest(id) {	
	    	 
	    	 	if (!confirm("Deseja realmente apagar este item?")){
	    	 		return;
	    	 	}
	    	 	
	        	$("btn-salvar-item").prop("disabled",true);
	        	$("btn-limpar-item").prop("disabled",true);       	

		     	$.ajax({
		 			type : "DELETE",
		 			contentType : "application/json",
		 			accept: 'text/plain',
		 			url : urlBase + "usuario/folgaSemanalPlanejada",
		 			data : id,
		 			dataType: 'text',
		 			success : function(id) {
		 				$("#folgaId").html(id);
		 				
		 				console.log("success: ");

		 				$("#tabela-folga-semanal-planejada tbody tr#folga" + id).remove();
					    
		 				limparFolga();
			        	$("btn-salvar-item").prop("disabled",false);
			        	$("btn-limpar-item").prop("disabled",false);
					   	 
		 			},
		 			error : function(e) {
		 				alert("Error!")
		 				console.log("ERROR: ", e);
			        	$("btn-salvar-item").prop("disabled",false);
		 			}
		 		});   
         }

	     function salvarFolga() {
        	$("btn-salvar-item").prop("disabled",true);
        	$("btn-limpar-item").prop("disabled",true);
        	

   		     $("#folgaDiaSemana").removeClass("is-invalid");
   		     $("#folgaMotivo").removeClass("is-invalid");
    		 	 
    		 $("#folgaHoraInicio").removeClass("is-invalid");
    		 $("#feedback-folgaHoraInicio").val("");
    		 $("#folgaHoraFim").removeClass("is-invalid");
    		 $("#feedback-folgaHoraFim").val("");
    		

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
 	    	
	    	if ($("#folgaHoraInicio").val().length < 5) {
	    		$("#folgaHoraInicio").addClass("is-invalid");
	    		$("#feedback-folgaHoraInicio").html("Preencha o campo hora inicio");
	    		return;
	    	}

	    	if ($("#folgaHoraFim").val().length < 5) {
	    		$("#folgaHoraFim").addClass("is-invalid");
	    		$("#feedback-folgaHoraFim").html("Preencha o campo hora fim");
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
        	};
       	 	
       	 	console.log(JSON.stringify(item));

			$.ajax({
	 			type : "POST",
	 			contentType : "application/json",
	 			accept: 'text/plain',
	 			url : urlBase + "usuario/folgasSemanalPlanejada",
	 			data : JSON.stringify(item),
	 			dataType: 'text',
	 			success : function(id) {
	 		
	 				$("#folgaId").html(id);
	 				console.log("success: "+item.id);

					if (item.id == null || item.id == "") {
		 				var row = '<tr id="folga' + id  + '">' +
						'<td class="rowclick" id="folgaMotivo' + id  + '" >' + item.motivo.nome  + '</td></td>' +
						'<td class="rowclick" id="folgaDiaSemana' + id  + '" >' + item.diaSemana.nome  + '</td></td>' +
						'<td class="rowclick" id="folgaHoraInicio' + id  + '" >' + item.horaInicio  + '</td></td>' +
						'<td class="rowclick" id="folgaHoraFim' + id  + '" >' + item.horaFim  + '</td></td>' +
						'<td><input type="button" class="btn btn-sm btn-danger" value="apagar" /></td>'
					  '</tr>';
	
		 			   $('#tabela-folga-semanal-planejada tbody tr.odd').remove();	
					   $('#tabela-folga-semanal-planejada tbody').prepend(row);
		    		}
				    else {
				    	$("#tabela-folga-semanal-planejada tbody #folgaMotivo" + item.id).html(item.motivo.nome);
				    	$("#tabela-folga-semanal-planejada tbody #folgaDiaSemana" + item.id).html(item.diaSemana.nome);
				    	$("#tabela-folga-semanal-planejada tbody #folgaHoraInicio" + item.id).html(item.horaInicio);
				    	$("#tabela-folga-semanal-planejada tbody #folgaHoraFim" + item.id).html(item.horaFim);
				    }
   
	 				limparFolga();
				   	ativaClicks();

	 			},
	 			error : function(e) {
	 				alert("Error!")
	 				console.log("ERROR: ", e);
		        	$("btn-salvar-item").prop("disabled",false);
	 			}
	 		});
	     }   
	     
	     function limparFolga() {
	    	 $("#folgaId").val(null);
	    	 $("#folgaHoraInicio").val("");
	    	 $("#folgaHoraFim").val("");

	         $("#tabela-folga-semanal-planejada tr").removeClass('selected');
	         
	    	 $("#folgaDiaSemana option").each(function () { 
	    		 $(this).removeAttr("selected");
	    	 });

	    	 $("#folgaMotivo option").each(function () { 
	    		 $(this).removeAttr("selected");
	    	 });	

			 $("#folgaMotivo").val("").change();
			 $("#folgaDiaSemana").val("").change();
	     }