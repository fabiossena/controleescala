
$(function() {
	var checkBoxDataFim = "";

	if ($("#form-projeto #dataFimProjeto").val() != ""){
		$("#form-projeto #check-box-data-fim").prop("checked", true);	
	}
	else{
		$("#form-projeto #dataFimProjeto").prop("disabled", true); 
	}
	
	$("#form-projeto #check-box-data-fim").click(function(e){
		if (!$(this).prop("checked")){
			checkBoxDataFim = $("#form-projeto #dataFimProjeto").val();
			$("#form-projeto #dataFimProjeto").val("");
		}
		else {
			$("#form-projeto #dataFimProjeto").val(checkBoxDataFim);
		}
		
		$("#form-projeto #dataFimProjeto").prop("disabled", !$(this).prop("checked"));
	});    	
});

function deleteProjeto(id) {
   	if (confirm("Deseja realmente apagar este projeto?")) {
   		window.location.href = urlBase + "projeto/delete/" + id;
   	}
}