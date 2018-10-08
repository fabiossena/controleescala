

	$(function() {
	   	limpar();
	   	ativaClicks();
	});	
	
	function ativaClicks() {	     
	     $("#tabelaUsuarioEmailPrimeiroAcesso tbody td").click(function(e) {
			select($(this).closest("tr").attr("id").replace("usuarioAcesso", ""));	
	     });    
     
		$("#funcao-id").change(function() {
			$("#perfil-acesso").html("Perfil acesso: " + $("#perfil-acesso-funcao"+this.value).val());
		});
	}	
	
	function select(item) {
		 
		 limpar();
	
		 $("#id").val(item);
		 $("#matricula").val($("td#matricula"+item).html());
		 $("#email").val($("td#email"+item).html());
		 
		 $("#funcao-id option").each(function () { 
	 		if ($(this).html() == $("td#funcao"+item).html()) {
	            $(this).attr("selected", "selected");
	            $("#perfil-acesso").html("Perfil acesso:" + $("#perfil-acesso-funcao"+this.value).val());
	        }
		 });
		 
		 $("#centro-custo-id option").each(function () { 
	 		if ($(this).html() == $("td#centroCusto"+item).html()) {
	            $(this).attr("selected", "selected");
	        }
		 });
	 }
    
    function limpar() {
	   	 $("#id").val(null);
	   	 $("#matricula").val("");
	   	 $("#email").val("");
	   	 $("#funcao-id option").each(function () { 
	   		 $(this).removeAttr("selected");
	   	 });
	   	 
	   	 $("#centro-custo-id option").each(function () { 
	   		 $(this).removeAttr("selected");
	   	 });
	   	 
	   	$("#tabelaUsuarioEmailPrimeiroAcesso tbody tr").removeClass("selected");
	
	   	 $("#funcao-id option").each(function () { 
	   		 $(this).removeAttr("selected");
	   	 });	
	
		 $("#funcao-id").val("").change();

	   	 $("#centro-custo-id option").each(function () { 
	   		 $(this).removeAttr("selected");
	   	 });	
	
		 $("#centro-custo-id").val("").change();
    }