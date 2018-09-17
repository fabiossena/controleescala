
function aceitarPrestador(id) {
	if (!confirm("Deseja aceitar este projeto?")){
		return;
	}
	
	$("#motivo-recusa-feedback" + id).val("");
	$("#motivo-recusa" + id).removeClass("is-invalid");
	aceitaRecusa(id, 1, "");	
}

function recusarPrestador(id) {
	if ($("#panel-motivo-recusa" + id).is(":hidden") || 
			$("#motivo-recusa" + id).val() == "") {
		$("#panel-motivo-recusa" + id).show();	
		$("#motivo-recusa" + id).prop("disabled", false);
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
				//alert(data);
				$("#motivo-recusa" + id).addClass("is-invalid");
				$("#motivo-recusa-feedback" + id).html(data);		
			}
			else{
				if (statusAceite == 1){
					$("#panel-motivo-recusa" + id).hide(); 
					$("#bt-aceita-prestador" + id).hide();
					$("#bt-recusa-prestador" + id).show();
					$("#observacao-prestador" + id + " i").html("Projeto aceito pelo prestador");	
					$("#motivo-recusa" + id).prop("disabled", true);			
				}
				else {
					$("#bt-recusa-prestador" + id).hide();	
					$("#bt-aceita-prestador" + id).show();						
					$("#observacao-prestador" + id + " i").html("Projeto recusado pelo prestador");
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