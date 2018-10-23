$(function() {
	
	 $("#form-escala #descricaoEscala").prop("disabled", true);
	 $("#form-escala #horaInicio").prop("disabled", true);
	 $("#form-escala #horaFim").prop("disabled", true);
	 $("#form-escala #quantidadePrestadoresPlanejada").prop("disabled", true);
	 $("#form-escala #observacaoEscala").prop("disabled", true);
	 $("#form-escala input[name='ativo']").prop("disabled", true);
	 $("#form-escala #diaSemanaDeId").prop("disabled", true);
	 $("#form-escala #diaSemanaAteId").prop("disabled", true);
	 $("select[name='monitor.id']").prop("disabled", true);
	 
     $("#tabela-escala tbody td.rowclick").click(function(e){
		selectEscala($(this).closest("tr").attr("id").replace("escala", ""));	
     });
     
     $("#btn-limpar-escala").click(function(e){
    	 limparEscala();
		 $("#btn-editar-escala").val("Nova");
		 $("#btn-editar-escala").show();
		 $("#btn-salvar-escala").hide();
    	 
    	 togglePrioridade(false);
    	 limparPrioridade();
    	 $("input[name='definidaPrioridade']").prop("checked", false);
    	 $("#painel-prioridade").hide();  
      });

	 $("#painel-prioridade").hide();    

	 togglePrioridade(!$("input[name='definidaPrioridade']").prop("checked"));

     $("input[name='definidaPrioridade']").click(function(e){
    	 togglePrioridade(!$("input[name='definidaPrioridade']").prop("checked"));
    	 limparPrioridade();
      });  
    
    if ($("#form-escala #id").val() == 0) {
   	 	$("#form-escala input[name='ativo']").prop("checked", true);
     	 limparEscala();
    }
    else {
   	 	//$("#btn-salvar-escala").val("Alterar");
        $("#escala" + $("#form-escala #id").val()).addClass('selected').siblings().removeClass('selected');
        editarEscala();
    }


	 $("#btn-editar-escala").click(function() {
         editarEscala();
	 }); 	
});

function editarEscala() {
	 $("#btn-editar-escala").hide();
	 $("#btn-salvar-escala").show();
    
	 $("#form-escala #descricaoEscala").prop("disabled", false);
	 $("#form-escala #horaInicio").prop("disabled", false);
	 $("#form-escala #horaFim").prop("disabled", false);
	 $("#form-escala #quantidadePrestadoresPlanejada").prop("disabled", false);
	 $("#form-escala #observacaoEscala").prop("disabled", false);
	 $("#form-escala input[name='ativo']").prop("disabled", false);
	 $("#form-escala #diaSemanaDeId").prop("disabled", false);
	 $("#form-escala #diaSemanaAteId").prop("disabled", false);
	 $("select[name='monitor.id']").prop("disabled", false);
	 $("input[name='definidaPrioridade']").prop("disabled", false);	
	 $("#form-escala #prioridadeId").prop("disabled", false);
	 $("#form-escala #horaInicioPrioridade").prop("disabled", false);
	 $("#form-escala #horaFimPrioridade").prop("disabled", false);
	 $("#form-escala #diaSemanaDePrioridadeId").prop("disabled", false);
	 $("#form-escala #diaSemanaAtePrioridadeId").prop("disabled", false);

     if ($("#form-escala #id").val()==0) {
         $("#form-escala #id").val(-1);
     }
}

function togglePrioridade(hide) {

	 if (hide) {
		 $("#painel-prioridade").hide();
		 return;
	 }

	 console.log("#painel-prioridade show");
	 $("#painel-prioridade").show();	 
}

function selectEscala(item) {

 	 limparEscala();
 	 
	 //$("#btn-salvar-escala").val("Alterar");
	 $("#form-escala #id").val(item);
	 $("#form-escala #descricaoEscala").val($("#descricaoEscala"+item).html());
	 $("#form-escala #horaInicio").val($("#horaInicio"+item).html());
	 $("#form-escala #horaFim").val($("#horaFim"+item).html());
	 $("#form-escala #quantidadePrestadoresPlanejada").val($("#quantidadePrestadoresPlanejada"+item).html());
	 $("#form-escala #observacaoEscala").val($("#observacaoEscala"+item).val());
	 $("#form-escala input[name='ativo']").prop("checked", JSON.parse($("#escalaAtiva"+item).val()));
	 
	 $("input[name='definidaPrioridade']").prop("checked", JSON.parse($("#definidaPrioridade"+item).val()));
	 togglePrioridade(!$("input[name='definidaPrioridade']").prop("checked"));
	 
	 $("#diaSemanaDeId option").each(function () { 
         $(this).removeAttr("selected");
 		if ($(this).html() == $("#diaSemanaDeId"+item).html()) {
            $(this).attr("selected", "selected");
        }
	 });

	 $("#diaSemanaAteId option").each(function () { 
         $(this).removeAttr("selected");
 		if ($(this).html() == $("#diaSemanaAteId"+item).html()) {
            $(this).attr("selected", "selected");
        }
	 });

	 //$("#form-escala input[id='monitor.id']").val($("#monitor"+item).html());
	 $("select[name='monitor.id'] option").each(function () { 
         $(this).removeAttr("selected");
 		if ($(this).val() == $("#monitor-id" + item).val()) {
            $(this).attr("selected", "selected");
            //$(this).editableSelect('select', $("#monitor"+item).val());
        }
	 });

	 $("#prioridadeId").val($("#prioridadeId"+item).val());
	 $("#horaInicioPrioridade").val($("#horaInicioPrioridade"+item).val());
	 $("#horaFimPrioridade").val($("#horaFimPrioridade"+item).val());
	 
	 $("#diaSemanaDePrioridadeId option").each(function () { 
         $(this).removeAttr("selected");
 		if ($(this).val() == $("#diaSemanaDePrioridadeId"+item).val()) {
            $(this).attr("selected", "selected");
        }
	 });

	 $("#diaSemanaAtePrioridadeId option").each(function () { 
         $(this).removeAttr("selected");
 		if ($(this).val() == $("#diaSemanaAtePrioridadeId"+item).val()) {
            $(this).attr("selected", "selected");
        }
	 });	 
	 
	 //$("#btn-salvar-escala").val("Salvar");
	 $("#btn-editar-escala").val("Editar");
	 $("#btn-editar-escala").show();
	 $("#btn-salvar-escala").hide();
	 
 }

 function limparEscala() {
	 
	 $("#btn-salvar-escala").hide();
	 $("#btn-editar-escala").show();
	 $("#form-escala #id").val("0");
	 $("#form-escala #descricaoEscala").val("");
	 $("#form-escala #horaInicio").val("");
	 $("#form-escala #horaFim").val("");
	 $("#form-escala #quantidadePrestadoresPlanejada").val(0);
	 $("#form-escala #observacaoEscala").val("");
	 $("#form-escala input[name='ativo']").prop("checked", true);

     $("#tabela-escala tr").removeClass('selected');
     
	 $("#diaSemanaDeId option").each(function () { 
		 $(this).removeAttr("selected");
	 });

	 $("#diaSemanaAteId option").each(function () { 
		 $(this).removeAttr("selected");
	 });

	 $("select[name='monitor.id'] option").each(function () { 
		 $(this).removeAttr("selected");
	 });

//	 $("select[name='monitor.id']").val("").change();
//	 $("#diaSemanaDe").val("").change();
//	 $("#diaSemanaAte").val("").change();
	 	
	 limparPrioridade();
	 
     $("#form-escala .form-control").removeClass("is-invalid");
     
     $("#form-escala #id").val(0); 
	 $("#form-escala #descricaoEscala").prop("disabled", true);
	 $("#form-escala #horaInicio").prop("disabled", true);
	 $("#form-escala #horaFim").prop("disabled", true);
	 $("#form-escala #quantidadePrestadoresPlanejada").prop("disabled", true);
	 $("#form-escala #observacaoEscala").prop("disabled", true);
	 $("#form-escala input[name='ativo']").prop("disabled", true);
	 $("#form-escala #diaSemanaDeId").prop("disabled", true);
	 $("#form-escala #diaSemanaAteId").prop("disabled", true);
	 $("select[name='monitor.id']").prop("disabled", true);
	 $("input[name='definidaPrioridade']").prop("disabled", true);
	 $("#form-escala #prioridadeId").prop("disabled", true);
	 $("#form-escala #horaInicioPrioridade").prop("disabled", true);
	 $("#form-escala #horaFimPrioridade").prop("disabled", true);
	 $("#form-escala #diaSemanaDePrioridadeId").prop("disabled", true);
	 $("#form-escala #diaSemanaAtePrioridadeId").prop("disabled", true);
	 
 }

 function limparPrioridade() {

	 $("#prioridadeId").val("");
	 $("#horaInicioPrioridade").val("");
	 $("#horaFimPrioridade").val("");
	 
	 $("#diaSemanaDePrioridade option").each(function () { 
		 $(this).removeAttr("selected");
	 });

	 $("#diaSemanaAtePrioridade option").each(function () { 
		 $(this).removeAttr("selected");
	 });    	 

 }
 
 function deleteEscala(id) {
	if (confirm("Deseja realmente apagar esta escala?")) {
		window.location.href = urlBase + "escala/delete/" + id;
	}
 }