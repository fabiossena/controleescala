

	$(function() {
	   	limpar();
	   	ativaClicks();
	});	
	
	function ativaClicks() {	     
	     $("#tabelaUsuarioEmailPrimeiroAcesso tbody td").click(function(e) {
			select($(this).closest("tr").attr("id").replace("usuarioAcesso", ""));	
	     });
	}	
	
	function select(item) {
		 
		 limpar();
	
		 $("#id").val(item);
		 $("#matricula").val($("td#matricula"+item).html());
		 $("#email").val($("td#email"+item).html());
		 
		 $("#funcaoId option").each(function () { 
	 		if ($(this).html() == $("td#funcao"+item).html()) {
	            $(this).attr("selected", "selected");
	        }
		 });
	 }
    
    function limpar() {
	   	 $("#id").val(null);
	   	 $("#matricula").val("");
	   	 $("#email").val("");
	   	 $("#funcaoId option").each(function () { 
	   		 $(this).removeAttr("selected");
	   	 });
	   	 
	   	$("#tabelaUsuarioEmailPrimeiroAcesso tbody tr").removeClass("selected");
	
	   	 $("#funcaoId option").each(function () { 
	   		 $(this).removeAttr("selected");
	   	 });	
	
		 $("#funcaoId").val("").change();
    }