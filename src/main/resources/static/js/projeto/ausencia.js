
	function selectOption(key, value) {
	 $(key + " option").each(function () { 
         $(this).removeAttr("selected");
         $(this).prop("selected", false);
 		if ($(this).val() > 0 && $(this).val() == value) {
            $(this).prop("selected", true);
            $(this).attr("selected", "selected");
        }
	 });
	}
	
	$(document).ready(function() {		

   	 	cancelarReposicao(); 

	     $("#btn-cancelar-reposicao").click(function(){
	    	 cancelarReposicao();
			 $("#btn-editar-reposicao").val("Nova");
			 $("#btn-editar-reposicao").show();
			 $("#btn-salvar-reposicao").hide();
	    	 
	      });
	     
		 $("#btn-editar-reposicao").click(function() {
	         editarReposicao();
		 }); 
		 
		if ($("#indicar-horario-para-repor").prop("checked")) {
			$("#panel-horario-reposicao").show();			
		}
		else {
			$("#panel-horario-reposicao").hide(); 				
		}

		if ($("input[name='dataFim']").val() == null || $("input[name='dataFim']").val() == "") {
			$("#check-box-data-fim").prop("checked", false);

			if (!$("input[name='dataFim']").prop("disabled")) {
				$("input[name='dataFim']").prop("disabled", true);
			}
		}
		else {
			$("#check-box-data-fim").prop("checked", true);
			if (!$("input[name='dataFim']").prop("disabled")) {
				$("input[name='dataFim']").prop("disabled", false);
			}
		}
		
		
		$("#indicar-horario-para-repor").change(function() { 
			if (this.checked) {
				$("#colocar-horario-a-disposicao").prop("checked", false);
				$("#panel-horario-reposicao").show();
			} else {
				$("#panel-horario-reposicao").hide();		
			}
		});	
		
		$("#colocar-horario-a-disposicao").change(function() {
			if (this.checked) {
				$("#indicar-horario-para-repor").prop("checked", false);
				$("#panel-horario-reposicao").hide();	
			}
		});

		$("#motivo-ausencia").change(function() {
			motivoAusenciaEscondePaineis(true);
			//var mot = $("#motivo-ausencia").val();
			//var value = $("#motivo-tipo" + mot).val() == 0 ? 3 : $("#motivo-tipo" + mot).val();
			//selectOption("#tipo-motivo-ausencia", value);
		});

		function addRemoveOptionsAusencia(changed) {

			var mot = $("#motivo-ausencia").val();
			var opcoes = $("#motivo-opcoes" + mot).val();
			
			if (changed) {
				 $("#tipo-motivo-ausencia option").each(function () {
					 var i  = $(this).val();
					 $("#tipo-motivo-ausencia option[value='" + i + "']").remove();
				 });
			}
			
			addRemoveOption(1, "Não descontada no banco de horas", opcoes);
			addRemoveOption(2, "Descontada do saldo banco de horas", opcoes);
			addRemoveOption(3, "Remunerada", opcoes);
			
		}
		
		function addRemoveOption(index, text, opcoes) {
			var hasOption = $('#tipo-motivo-ausencia option[value="' + index + '"]').length > 0;
			if (opcoes.search(index)>=0 && !hasOption) {
				$("#tipo-motivo-ausencia").append('<option value="' + index + '">' + text + '</option>');
			}
			else if (opcoes.search(index)==-1 && hasOption) {
				 $("#tipo-motivo-ausencia option[value='" + index + "']").remove();
			}
		}
		
		function motivoAusenciaEscondePaineis(changed) {
			
			addRemoveOptionsAusencia(changed);

			/*			
			var mot = $("#motivo-ausencia").val();
			if (!$("#motivo-ausencia").prop("disabled")) {
				var value = $("#motivo-tipo" + mot).val() == 0 ? 3 : $("#motivo-tipo" + mot).val();
				$("#tipo-motivo-ausencia").prop("disabled", value == 2);
			}*/
			
			
			/*if ($("#motivo-tipo" + mot).val() != 1) {
				$("#indicar-horario-para-repor").prop("checked", false);
				$("#observacao-motivo-ausencia").html("Ausencia não descontada do banco de horas");
				$("#panel-horario-reposicao").hide();	
				$("#panel-indicar-horario-para-repor").hide();				
			} 
			else {
				$("#observacao-motivo-ausencia").html("Ausencia descontada do banco de horas");
				$("#panel-indicar-horario-para-repor").show();				
			}*/
		}
		
		motivoAusenciaEscondePaineis();
		
		$("#check-box-data-fim").change(function() {
			if (this.checked) {
				$("input[name='dataFim']").prop("disabled", false);
			} else {
				$("input[name='dataFim']").prop("disabled", true);		
			}
		}); 
		
		$("#selected-usuario-troca").prop("disabled", true);
		$(".campos-troca-usuario-reposicao").hide();
		$("#selected-usuario-troca").hide();
		
		$("#checkbox-indicar-outro-usuario-troca").change(function() {			
			if (this.checked) {
				$("#selected-usuario-troca").prop("disabled", false);

				$("#selected-usuario-troca").show();
				$(".campos-troca-usuario-reposicao").show();
					
				if ($("#data-troca").val()=="") {
					$("#data-troca").val($("#dataInicio").val());
					$("#hora-inicio-troca").val($("#horaInicio").val());
					$("#hora-fim-troca").val($("#horaFim").val()); 
				}
			} else {
				//$("#panel-horario-reposicao").hide();	
				$(".campos-troca-usuario-reposicao").hide();
				$("#selected-usuario-troca").hide();
				$("#selected-usuario-troca").prop("disabled", true);	
				 $("#selected-usuario-troca option").each(function () { 
			         $(this).removeAttr("selected");
			         $(this).prop("selected", false);
				 });	
			}
		});

		

		// 3 
		if ($("#panel-selected-projeto-escala-principal").is(":visible")) {
			$("#selected-projeto-escala-principal").change(function() {
				preencherUsuarioPorProjetoEscalaId(this.value, '#selected-usuario-solicitacao')
			});
			
			preencherProjetoEscalaPorUsuarioId($("#selected-usuario-solicitacao").val(), '#selected-projeto-escala-troca', '#selected-usuario-troca'); //, true)
		}
		
		// 2 main
		$("#selected-projeto-escala-troca").change(function() {
			preencherUsuarioPorProjetoEscalaId(this.value, '#selected-usuario-troca', null, true)
		});

		// 1 optional
	    $("#selected-usuario-solicitacao").change(function() {
			preencherProjetoEscalaPorUsuarioId(this.value, '#selected-projeto-escala-troca', '#selected-usuario-troca'); //, true)
		});

		
		$("#selected-usuario-troca").prop("disabled", true);
		$(".campos-troca-usuario-reposicao").hide();
		$("#selected-usuario-troca").hide();
		
	});
	
	function showHideCamposTroca() {
		if ($("#checkbox-indicar-outro-usuario-troca").prop("checked")) {
			$("#selected-usuario-troca").show();
			$(".campos-troca-usuario-reposicao").show();						
		}
		else {
			$(".campos-troca-usuario-reposicao").hide();
			$("#selected-usuario-troca").hide();						
		}
	}
	
	function selectReposicao(id) {
		cancelarReposicao();
		$("#id-reposicao").val($("#id-reposicao"+id).val());
		$("#btn-editar-reposicao").val("Editar");
		$("#data-reposicao").val($("#data-reposicao"+id).val());
		$("#hora-inicio-reposicao").val($("#hora-inicio-reposicao"+id).val());
		$("#hora-fim-reposicao").val($("#hora-fim-reposicao"+id).val()); 
		$("#observacao-reposicao").val($("#observacao-reposicao"+id).val());

		$("#data-troca").val($("#data-troca"+id).val());
		$("#hora-inicio-troca").val($("#hora-inicio-troca"+id).val());
		$("#hora-fim-troca").val($("#hora-fim-troca"+id).val()); 
		
		var projeto = null;
		 $("#selected-projeto-escala-troca option").each(function () { 
	         $(this).removeAttr("selected");
	         $(this).prop("selected", false);
	 		if ($(this).val() > 0 && $(this).val() == $("#selected-projeto-escala-troca"+id).val()) {
	            $(this).prop("selected", true);
	            $(this).attr("selected", "selected");
	        }
		 });
		 
		 preencherUsuarioPorProjetoEscalaId(
				 $("#selected-projeto-escala-troca"+id).val(), 
				 "#selected-usuario-troca", 
			 	 id,
			 	 true);
		 // preencherUsuarioTroca(id)
		
		 
	}
	
	function preencherUsuarioTroca(id) {
		 $("#selected-usuario-troca option").each(function () { 
	         $(this).removeAttr("selected");
	         $(this).prop("selected", false);
	 		if ($(this).val() > 0 && $(this).val() == $("#selected-usuario-troca"+id).val()) {
	            $(this).attr("selected", "selected");
		         $(this).prop("selected", true);
	    		$("#checkbox-indicar-outro-usuario-troca").prop("checked", true);
	    		showHideCamposTroca();
	        }
		 });		
		 
	}
	
	function editarReposicao(id) {
		if ($("#btn-editar-reposicao").val() == "Novo"){
			cancelarReposicao();
			$("#data-troca").val($("#dataInicio").val());
			$("#hora-inicio-troca").val($("#horaInicio").val());
			$("#hora-fim-troca").val($("#horaFim").val()); 
		} 

		$("#btn-editar-reposicao").hide();
		$("#btn-salvar-reposicao").show();
		 
		$("#data-reposicao").prop("disabled", false);
		$("#hora-inicio-reposicao").prop("disabled", false);
		$("#hora-fim-reposicao").prop("disabled", false);
		$("#observacao-reposicao").prop("disabled", false);
		 
		$("#data-troca").prop("disabled", false);
		$("#hora-inicio-troca").prop("disabled", false);
		$("#hora-fim-troca").prop("disabled", false);

		$("#checkbox-indicar-outro-usuario-troca").prop("disabled", false);
		$("#selected-projeto-escala-troca").prop("disabled", false);
		if ($("#selected-usuario-troca").val() != null && $("#selected-usuario-troca").val() > 0) { 
			 $("#selected-usuario-troca").prop("disabled", false);
		}
		
		$(".btn-class-apagar-reposicao").prop("disabled", false);
	}
	
	function cancelarReposicao() { 
		$("#btn-editar-reposicao").val("Novo");
		$("#btn-editar-reposicao").show();
		$("#btn-salvar-reposicao").hide();
		$("#id-reposicao").val(0);
		$("#data-reposicao").val("");
		$("#hora-inicio-reposicao").val("");
		$("#hora-fim-reposicao").val("");
		$("#observacao-reposicao").val("");

		$("#selected-projeto-escala-troca option").each(function () { 
	         $(this).removeAttr("selected");
	         $(this).prop("selected", false);
		});

		$("#selected-usuario-troca option").each(function () { 
	         $(this).removeAttr("selected");
	         $(this).prop("selected", false);
		});

		$("#checkbox-indicar-outro-usuario-troca").prop("checked", false);
		
		$("#data-reposicao").prop("disabled", true);
		$("#hora-inicio-reposicao").prop("disabled", true);
		$("#hora-fim-reposicao").prop("disabled", true);
		$("#observacao-reposicao").prop("disabled", true);
		$("#selected-projeto-escala-troca").prop("disabled", true);
		$("#selected-usuario-troca").prop("disabled", true);

		$("#data-troca").prop("disabled", true);
		$("#hora-inicio-troca").prop("disabled", true);
		$("#hora-fim-troca").prop("disabled", true);

		$("#selected-usuario-troca").hide();
		$(".campos-troca-usuario-reposicao").hide();
		
		$("#checkbox-indicar-outro-usuario-troca").prop("disabled", true);
	}

	// 2 main - escala depois usuario							       
	// 2 main - #selected-projeto-escala-troca 			#selected-usuario-troca								    
	function preencherUsuarioPorProjetoEscalaId(id, destino, reposicaoId, exceptUsuario)
    {
		var estaDesabilitado = $(destino).prop("disabled");
		$(destino).prop("disabled", true);
		var localUrl = urlBase + "ausencia/usuariosPorProjetoEscalaId/" + id;
		if (exceptUsuario) {
			var usuarioIdExcept = $("#selected-usuario-solicitacao").val();
			if (usuarioIdExcept) {
				localUrl = localUrl + "?usuarioId="+usuarioIdExcept;
			}	
		}
		
       $.ajax({
           type:'GET',
           url:localUrl,         
           dataType:'json',                    
           cache:false,
           success:function(aData) {
               $(destino).get(0).options.length = 0;
               $(destino).get(0).options[0] = new Option("", "0");      
               for(var i = 0; i < aData.length;i++) {
            	   var item = aData[i];
               		$(destino).get(0).options[$(destino).get(0).options.length] = new Option(item.nomeCompletoMatricula, item.id);                                                                                
           	   }
               
				if (reposicaoId) {
					preencherUsuarioTroca(reposicaoId);
				}
				
				if (!estaDesabilitado) {
					$(destino).prop("disabled", false);
				}
           },
           error:function(){
        	   alert("error");
				
				if (!estaDesabilitado) {
					$(destino).prop("disabled", false);
				}
			}
       });

       return false;
    }

	// 1 optional - escalas pelos usuarios e depois (escala depois usuario)
	// 1 optional - #selected-usuario-solicitacao depois    #selected-projeto-escala-troca  (depois #selected-projeto-escala-troca 			#selected-usuario-troca					) 

   function preencherProjetoEscalaPorUsuarioId(id, destino, destinoPai) //, exceptPrestadorEscala) 
   {
	   var estaDesabilitado = $(destino).prop("disabled");
	   var estaDesabilitadoPai = $(destinoPai).prop("disabled");
	   $(destino).prop("disabled", true);
	   $(destinoPai).prop("disabled", true);
	   var localUrl = urlBase + "ausencia/projetoEscalaPorUsuarioId/" + id;

//		if (exceptPrestadorEscala) {
//			var prestadorEscalaIdExcept = $("#selected-projeto-escala-principal").val();
//			if (prestadorEscalaIdExcept) {
//				localUrl = localUrl + "?prestadorEscalaId="+prestadorEscalaIdExcept;
//			}	
//		}
		
       $.ajax({
           type:'GET',
           url:localUrl,         
           dataType:'json',                    
           cache:false,
           success:function(aData){ 
               $(destinoPai).get(0).options.length = 0;
               $(destino).get(0).options.length = 0;
               $(destino).get(0).options[0] = new Option("", "0");        
               for(var i = 0; i < aData.length;i++) {
            	   var item = aData[i];
	               $(destino).get(0).options[$(destino).get(0).options.length] = new Option(item.descricaoCompletaEscala, item.id);                                                                                   
           	   }
               
				if (!estaDesabilitado) {
					$(destino).prop("disabled", false);
				}
				if (!estaDesabilitadoPai) {
					$(destinoPai).prop("disabled", false);
				}
           },
           error: function() {
        	   alert("error");
				if (!estaDesabilitado) {
					$(destino).prop("disabled", false);
				}
				if (!estaDesabilitadoPai) {
					$(destinoPai).prop("disabled", false);
				}
   	   	   }
       });

       return false;
   }

	 function salvarReposicao() {
     	
		$("#feedback-data-reposicao").val("");
		$("#feedback-hora-inicio-reposicao").val("");
		$("#feedback-hora-fim-reposicao").val("");
		$("#feedback-selected-projeto-escala-troca").val("");
		$("#feedback-selected-usuario-troca").val("");

		$("#data-reposicao").removeClass("is-invalid");
		$("#hora-inicio-reposicao").removeClass("is-invalid");
		$("#hora-fim-reposicao").removeClass("is-invalid");
		$("#selected-projeto-escala-troca").removeClass("is-invalid");
		$("#selected-usuario-troca").removeClass("is-invalid");

		$("#data-troca").removeClass("is-invalid");
		$("#hora-inicio-troca").removeClass("is-invalid");
		$("#hora-fim-troca").removeClass("is-invalid");
		
		validaReposicao();	  	
	  	
		if ($("#checkbox-indicar-outro-usuario-troca").prop("checked")) {
			validaTroca();
		}	  	
	  	
	  	
	 	var projetoEscalaSelected = $("#selected-projeto-escala-troca option:selected");
	 	var usuarioTrocaSelected =  $("#selected-usuario-troca option:selected");
	 	
	 	var data = $("#data-reposicao").val().split("/");
	 	var dataTroca = $("#data-troca").val().split("/");
	 	console.log("#id-reposicao " +  $("#id-reposicao").val());
	 	var checkTrocaDisable = !$("#checkbox-indicar-outro-usuario-troca").prop("checked");
	 	var item = {
			id : $("#id-reposicao").val(),
			data : data[2]+"-"+data[1]+"-"+data[0], 
			dataFormatada :  $("#data-reposicao").val(), 
			horaInicio : $("#hora-inicio-reposicao").val(), 
			horaFim : $("#hora-fim-reposicao").val(),
			
			dataTroca : (checkTrocaDisable || dataTroca == null ? null : (dataTroca[2]+"-"+dataTroca[1]+"-"+dataTroca[0])), 
			dataTrocaFormatada :  checkTrocaDisable ? "" : $("#data-troca").val(), 
			horaInicioTroca : checkTrocaDisable ? "" : $("#hora-inicio-troca").val(), 
			horaFimTroca : checkTrocaDisable ? "" : $("#hora-fim-troca").val(),
			
			projetoEscalaTroca : { id: projetoEscalaSelected.val(), descricaoPrestador: projetoEscalaSelected.text() },
			indicadoOutroUsuario : $("#checkbox-indicar-outro-usuario-troca").prop("checked"),
			usuarioTroca : { id: usuarioTrocaSelected.val(), descricaoPrestador: usuarioTrocaSelected.text() },
			observacao : $("#observacao-reposicao").val()
	 	};
		 	
	 	console.log(JSON.stringify(item));
		var url1 = urlBase + "ausencia/reposicao";

    	$("#btn-salvar-reposicao").prop("disabled", true); 
		$.ajax({
			type : "POST",
			contentType : "application/json",
			accept: 'text/plain',
			url : url1,
			data : JSON.stringify(item),
			dataType: 'text',
			success : function(id) {
		
				//$("#folgaId").html(id);
				console.log("success: "+item.id);
				 
				if (item.id == null || item.id == "" || item.id == "0") {
	 				var row = 
	 					  '<tr id="reposicao' + id + '"  onclick="selectReposicao(' + id  + ')">' +
							'<td id="xdetalhes-reposicao' + id + '" class="rowclick" style="font-size: 10pt">' + (item.usuarioTroca.nome == null ? '' : item.usuarioTroca.nome+'<br>') + item.projetoEscalaTroca.descricaoPrestador + '</td>' +
							'<td id="xhora-inicio-fim-reposicao' + id + '" class="rowclick" style="font-size: 10pt">Reposição ' + item.dataFormatada  + ' ' + item.horaInicio + ' - ' + item.horaFim + '</td>' +
							'<td>' +
								'<input type="button" onclick="apagarReposicao('+ id +')" class="btn-apagar-folga btn btn-sm btn-danger" value="apagar" />' +
								
								'<input id="id-reposicao' + id  + '" type="hidden" value="' +  id + '" />' +
								'<input id="data-reposicao' + id  + '" type="hidden" value="' + item.dataFormatada + '" />' +
								'<input id="hora-inicio-reposicao' + id  + '" type="hidden" value="' + item.horaInicio + '" />' +
								'<input id="hora-fim-reposicao' + id  + '" type="hidden" value="' + item.horaFim + '" />' +
								
								'<input id="data-troca' + id  + '" type="hidden" value="' + item.dataTrocaFormatada + '" />' +
								'<input id="hora-inicio-troca' + id  + '" type="hidden" value="' + item.horaInicioTroca + '" />' +
								'<input id="hora-fim-troca' + id  + '" type="hidden" value="' + item.horaFimTroca + '" />' +
								
								'<input id="observacao-reposicao' + id  + '" type="hidden" value="' + item.observacao + '" />' +
								'<input id="selected-projeto-escala-troca' + id  + '" type="hidden" value="' + item.projetoEscalaTroca.id + '" />' +
								'<input id="selected-usuario-troca' + id  + '" type="hidden" value="' + item.usuarioTroca.id + '" />' +
							'</td>'
						  '</tr>';
						  

	 				if ($('#tabela-reposicao input').val() == null || $('#tabela-reposicao input').val() == "") {
	 					$('#tabela-reposicao tbody tr.odd').remove();
	 				}
	 				 
				   $('#tabela-reposicao tbody').prepend(row);
				   aplicarSelecionarTabela();
	    		}
			    else {
			    	$("#tabela-reposicao tbody #data-reposicao" + item.id).val(item.dataFormatada);
			    	$("#tabela-reposicao tbody #hora-inicio-reposicao" + item.id).val(item.horaInicio);
			    	$("#tabela-reposicao tbody #hora-fim-reposicao" + item.id).val(item.horaFim);

			    	$("#tabela-reposicao tbody #data-troca" + item.id).val(item.dataTrocaFormatada);
			    	$("#tabela-reposicao tbody #hora-inicio-troca" + item.id).val(item.horaInicioTroca);
			    	$("#tabela-reposicao tbody #hora-fim-troca" + item.id).val(item.horaFimTroca);
			    	
			    	$("#tabela-reposicao tbody #observacao-reposicao" + item.id).val(item.observacao);
			    	$("#tabela-reposicao tbody #selected-projeto-escala-troca" + item.id).val(item.projetoEscalaTroca.id);
			    	$("#tabela-reposicao tbody #selected-usuario-troca" + item.id).val(item.usuarioTroca.id);
			    	
			    	$("#tabela-reposicao tbody #xdata-reposicao" + item.id).val(item.dataFormatada);
			    	$("#tabela-reposicao tbody #xhora-inicio-fim-reposicao" + item.id).val(item.horaInicio + " - " + item.horaFim);
			    	$("#tabela-reposicao tbody #xdetalhes-reposicao" + item.id).val((item.usuarioTroca.nome == null ? '' : item.usuarioTroca.nome+'<br>') + item.projetoEscalaTroca.descricaoPrestador);
			    }

				cancelarReposicao();
	        	$("#btn-salvar-reposicao").prop("disabled",false);
	        	$("#alerta-salvar-reposicao").html("clique em salvar (acima) para enviar as informações e ver os totais atualizados");

			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
	        	$("#btn-salvar-reposicao").prop("disabled",false);
			}
		});
	 }
	 
	 function validaTroca() {

	  	if ($("#selected-projeto-escala-troca").val() == "" || 
		  	    $("#selected-projeto-escala-troca").val() == "0" ||
		  	    $("#selected-projeto-escala-troca").val() == null) {
	  		$("#selected-projeto-escala-troca").addClass("is-invalid");
	  		$("#feedback-selected-projeto-escala-troca").html("Preencha o campo escala projeto");
	  		return;
	  	}
	  	
		if ($("#selected-usuario-troca").val() == "" || 
		  	    $("#selected-usuario-troca").val() == "0" ||
		  	    $("#selected-usuario-troca").val() == null) {
	  		$("#selected-usuario-troca").addClass("is-invalid");
	  		$("#feedback-select-usuario-troca").html("Preencha o campo usuário reposição");
	  		return;
	  	}
		
	  	
	 	if ($("#data-troca").val().length <  10) { 		
	 		$("#data-troca").addClass("is-invalid");
	 		$("#feedback-data-troca").html("Preencha o campo data troca");
	 		return;
	 	}
	  	
	 	if ($("#hora-inicio-troca").val().length <  5) { 		
	 		$("#hora-inicio-troca").addClass("is-invalid");
	 		$("#feedback-hora-inicio-troca").html("Preencha o campo hora início troca");
	 		return;
	 	}

	 	if ($("#hora-fim-troca").val().length <  5) { 		
	 		$("#hora-fim-troca").addClass("is-invalid");
	 		$("#feedback-hora-fim-troca").html("Preencha o campo hora fim troca");
	 		return;
	 	}


	 	if (parseInt($("#hora-inicio-troca").val().replace(":", "")) > parseInt($("#hora-fim-troca").val().replace(":", ""))) { 		
	 		$("#hora-fim-troca").addClass("is-invalid");
	 		$("#feedback-hora-fim-troca").html("A hora início troca deve ser menor do que a hora fim");
	 		return;
	 	}
	 	
	 	
	 	if (!/^(([0-1][0-9])||([2][0-3])):[0-5][0-9]$/.test($("#hora-inicio-troca").val())) {
	 		$("#hora-inicio-reposicao").addClass("is-invalid");
	 		$("#feedback-hora-inicio-reposicao").html("Preencha o campo hora início troca corretamente");
	 		return;
	 	}

	 	if (!/^(([0-1][0-9])||([2][0-3])):[0-5][0-9]$/.test($("#hora-fim-troca").val())) {
	 		$("##hora-fim-troca").addClass("is-invalid");
	 		$("#feedback-hora-fim-troca").html("Preencha o campo hora fim troca corretamente");
	 		return;
	 	}		 
	 }
	 
	 function validaReposicao() {

			$("#data-troca").removeClass("is-invalid");
			$("#hora-inicio-troca").removeClass("is-invalid");
			$("#hora-fim-troca").removeClass("is-invalid");
			
		 	if ($("#data-reposicao").val().length <  10) { 		
		 		$("#hora-data-reposicao").addClass("is-invalid");
		 		$("#feedback-hora-inicio-reposicao").html("Preencha o campo data reposição");
		 		return;
		 	}
		  	
		 	if ($("#hora-inicio-reposicao").val().length <  5) { 		
		 		$("#hora-inicio-reposicao").addClass("is-invalid");
		 		$("#feedback-hora-inicio-reposicao").html("Preencha o campo hora início reposição");
		 		return;
		 	}

		 	if ($("#hora-fim-reposicao").val().length <  5) { 		
		 		$("#hora-fim-reposicao").addClass("is-invalid");
		 		$("#feedback-hora-fim-reposicao").html("Preencha o campo hora fim reposição");
		 		return;
		 	}


		 	if (parseInt($("#hora-inicio-reposicao").val().replace(":", "")) > parseInt($("#hora-fim-reposicao").val().replace(":", ""))) { 		
		 		$("#hora-fim-reposicao").addClass("is-invalid");
		 		$("#feedback-hora-fim-reposicao").html("A hora início deve ser menor do que a hora fim");
		 		return;
		 	}
		 	
		 	
		 	if (!/^(([0-1][0-9])||([2][0-3])):[0-5][0-9]$/.test($("#hora-inicio-reposicao").val())) {
		 		$("#hora-inicio-reposicao").addClass("is-invalid");
		 		$("#feedback-hora-inicio-reposicao").html("Preencha o campo hora início corretamente");
		 		return;
		 	}

		 	if (!/^(([0-1][0-9])||([2][0-3])):[0-5][0-9]$/.test($("#hora-fim-reposicao").val())) {
		 		$("##hora-fim-reposicao").addClass("is-invalid");
		 		$("#feedback-hora-fim-reposicao").html("Preencha o campo hora fim corretamente");
		 		return;
		 	}
	 }

