	
		var estados;
		var cidades;
		$(function() {

			$( "#select-endereco-pais").change(function(e) {	
				console.log("select-endereco-pais");
	        	var val = $("#select-endereco-pais").val();
	        	$("#select-endereco-estado").empty().append('<option selected="selected" value="-1">Selecione um estado</option>');
	        	$("#select-endereco-cidade").empty().append('<option selected="selected" value="-1">Selecione uma cidade</option>');
				$.each(estados, function (data, value) {
	        		if (value.paisId == val) {
		        		$("#select-endereco-estado").append($("<option></option>").val(value.id).text(value.nome));
	        		}
				});
				
			});		
	
			$( "#select-endereco-estado").change(function(e) {			
	        	var val = $("#select-endereco-estado").val();
	        	$("#select-endereco-cidade").empty().append('<option selected="selected" value="-1">Selecione uma cidade</option>');
				$.each(cidades, function (data, value) {
	        		if (value.estadoId == val) {
		        		$("#select-endereco-cidade").append($("<option></option>").val(value.id).text(value.nome));
	        		}
				});
				
			});		
	
			$( "#select-endereco-cidade").change(function(e) {			
	        	var val = $("#select-endereco-cidade").val();
	        	if (val > 0) {
	        		//$("#cidade-id").val(val);
	        	}
				
			});	
			
		    $.ajax({
		        type: "GET",
		        url: urlBase + "referencias/estados",
		        contentType: "application/json; charset=utf-8",
		        dataType: "json",
		        success: function (data) {
			        	estados = data;
			        	var val = $("#select-endereco-pais").val();
			        	$("#select-endereco-estado").empty().append('<option selected="selected" value="-1">Selecione um estado</option>');
						$.each(estados, function (data, value) {
			        		if (value.paisId == val) {
				        		$("#select-endereco-estado").append($("<option></option>").val(value.id).text(value.nome));
			        		}
						});
						loadCidades();
		        	
		        },
		        failure: function (response) {
		            alert(response.d);
		        }
		    });
	
		    function loadCidades() {
			    $.ajax({
			        type: "GET",
			        url: urlBase + "referencias/cidades",
			        contentType: "application/json; charset=utf-8",
			        dataType: "json",
			        success: function (data) {
						console.log(data);
			        	cidades = data;		
			        	
			        	var cidadeId = globalUsuarioEnderecoCidadeId;
			        	console.log(cidadeId);
			        	console.log(cidadeId);
			        	$("#select-endereco-cidade").empty().append('<option selected="selected" value="-1">Selecione uma cidade</option>');
			        	var estadoId = 0;
			        	var paisId = 0;
			        	if (cidadeId > 0) {
							$.each(cidades, function (data, value) {
								if (cidadeId == value.id) {
									estadoId = value.estadoId;
									return;
								}
							});

							$.each(estados, function (data, value) {
								if (estadoId == value.id) {
									paisId = value.paisId;
									return;
								}
							});

							console.log(cidadeId);
							console.log(estadoId);
							console.log(paisId);
							$("#select-endereco-pais").val(paisId).change();
							$("#select-endereco-estado").val(estadoId).change();
							$("#select-endereco-cidade").val(cidadeId).change();
			        	}
						
						
			        },
			        failure: function (response) {
			            alert(response.d);
			        }
			    });
		    }

		});