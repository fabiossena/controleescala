
	function aceitarPrestador(id) {
		if (!confirm("Deseja aceitar este projeto?")){
			return;
		}
		
		$("#motivo-recusa-feedback" + id).html("");
		$("#motivo-recusa" + id).removeClass("is-invalid");
		aceitaRecusa(id, 1, $("#motivo-recusa" + id).html());	
	}
	
	function recusarPrestador(id) {
		if ($("#panel-motivo-recusa" + id).is(":hidden") || 
				$("#motivo-recusa" + id).val() == "") {
			$("#panel-motivo-recusa" + id).show();
			$("#motivo-recusa" + id).addClass("is-invalid");
			$("#motivo-recusa-feedback" + id).html("Preencha o campo motivo recusa");		
		}
		else if (confirm("Deseja recusar este projeto?")) {	
			$("#motivo-recusa-feedback" + id).html("");
			$("#motivo-recusa" + id).removeClass("is-invalid");
			aceitaRecusa(id, 2, $("#motivo-recusa" + id).val());			
		}
	}
	
	function aceitaRecusa(id, statusAceite, motivo) {
	
		var localUrl = urlBase + "usuario/aceiteProjeto/" + id + "?statusAceite=" + statusAceite + "&motivo=" + motivo;
		console.log(localUrl); 
		$.ajax({
			type : "GET", 
			contentType : "application/json",
			url : localUrl,
			dataType: 'text',
			success : function(data) {
				if (data != "") {
					//
					$("#motivo-recusa" + id).addClass("is-invalid");
					$("#motivo-recusa-feedback" + id).html(data);		
				}
				else{
					$("#card" + id).hide();	
					if (statusAceite == 1){
						alert("Projeto aceito com sucesso!");
					}
					else {
						alert("Projeto recusado com sucesso!");
					}
				}
			},
			error : function(e) {
				$("#motivo-recusa" + id).addClass("is-invalid");		
				$("#motivo-recusa-feedback" + id).html(e);	
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});	
	}