
		var lastActiveTab;
		$(document).ready(function() {
			
			$(".mask-phone").mask("(00) 000000009");	
			$(".mask-date").mask("00/00/0000");	
			$(".mask-hour").mask("00:00");	
			$(".mask-number").mask("00000000000000");

			$( ".number, .mask-number").keydown(function (e) {
		        // Allow: backspace, delete, tab, escape, enter and .
		        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110]) !== -1 ||
		             // Allow: Ctrl+A, Command+A
		            (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) || 
		             // Allow: home, end, left, right, down, up
		            (e.keyCode >= 35 && e.keyCode <= 40)) {
		                 // let it happen, don't do anything
		                 return;
		        }
		        // Ensure that it is a number and stop the keypress
		        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
		            e.preventDefault();
		        }
		    });
			
			//$('.editable-select').editableSelect(); 
			$( ".datepicker" ).datepicker({dateFormat: 'dd/mm/yy'});
			$( ".spinner" ).spinner();
			
			console.log("cookie");
			if ($.cookie("lastActiveTab") != null) {
				console.log("cookie " + $.cookie("lastActiveTab"));
				if ($('#pills-tab a[href="#' + $.cookie("lastActiveTab") + '"]').html() != null) {
					$('#pills-tab a[href="#' + $.cookie("lastActiveTab") + '"]').tab('show');
				}
				else{
					$('#pills-tab a[href="#pills-dados').tab('show');
				}
			}
			else {
				console.log("cookie else");
				$('#pills-tab a[href="#pills-dados').tab('show');
			}
			
			if ($.cookie("currentPage") == null || $.cookie("currentPage").replace("/editar", "") != window.location.href.replace("/editar", "")) {
				$.cookie("lastActiveTab", null, { path: "/", expires:-1 });
				$.removeCookie("lastActiveTab", { path: "/" });

				$.cookie("currentPage", null, { path: "/", expires:-1 });	
				$.removeCookie("currentPage", { path: "/" });
			}
			console.log("load");
			console.log(lastActiveTab);
			console.log(urlBase);
			
			// store the currently selected tab in the hash value
			$("#pills-tab a").on("shown.bs.tab", function(e) {
			  	var id = $(e.target).attr("href").substr(1);
			  	lastActiveTab = id;
				$.cookie("lastActiveTab", lastActiveTab, { path: "/" });	
				$.cookie("currentPage", window.location.href, { path: "/" });	
				console.log(lastActiveTab)
			});			
			
			
			$("form").validate({
			       rules : {
			             nome:{ required:true },
			             primeiroNome:{ required:true },
			             cpf:{ required:true },
			             email:{ required:true },
			             email:{ required:true },
			             horaInicio:{ required:true },
			             horaFim:{ required:true },
			             dataInicio:{ required:true },
			             dataInicioProjeto:{ required:true }                      
			       },
			       messages:{
			             nome:{ required:"Preencha o campo nome" },
			             primeiroNome:{ required:"Preencha o campo primeiro nome" },
			             cpf:{ required:"Preencha o campo cpf" },
			             email:{ required:"Preencha o campo e-mail" },
			             dataInicio:{ required:"Preencha o campo data início" },
			             horaInicio:{ required:"Preencha o campo hora início" },
			             horaFim:{ required:"Preencha o campo hora fim" },
			             dataInicioProjeto:{ required:"Preencha o campo data início" }
			       }
			});	
			
			
			$("#form-login").validate({
			       rules : {
			             matricula:{ required:true },
			             senha:{ required:true }                     
			       },
			       messages:{
			    	   matricula:{ required:"Preencha o matricula" },
			    	   senha:{ required:"Preencha o campo senha" }
			       }
			});
			
			$("#form-escala").validate({
			       rules : {
			             "monitor.id":{ required:true },
            			 descricaoEscala:{ required:true },
			             horaFim:{ required:true },
			             dataInicio:{ required:true }
			       },
			       messages:{
			    	   "monitor.id":{ required:"Preencha o campo monitor" },
		               descricaoEscala:{ required:"Preencha o campo descrição escala" },
		               horaInicio:{ required:"Preencha o campo hora início" },
		               horaFim:{ required:"Preencha o campo hora fim" },  
			       }
			});
			
			$("#form-prestador").validate({
			       rules : {
			             funcaoId:{ required:true },
			             "prestador.id":{ required:true },
			             dataInicioPrestador:{ required:true }   
			       },
			       messages:{
			    	   funcaoId:{ required:"Preencha o campo função" },
			    	   "prestador.id":{ required:"Preencha o campo prestador" },
			           dataInicioPrestador:{ required:"Preencha o campo data início" }
			       }
			});


			
		   	 $('.tabela-simples').DataTable({
	   	            "language": {
	   	             "sEmptyTable": "Nenhum registro encontrado",
		   	          "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
		   	          "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
		   	          "sInfoFiltered": "(Filtrados de _MAX_ registros)",
		   	          "sInfoPostFix": "",
		   	          "sInfoThousands": ".",
		   	          "sLengthMenu": "_MENU_ resultados por página",
		   	          "sLoadingRecords": "Carregando...",
		   	          "sProcessing": "Processando...",
		   	          "sZeroRecords": "Nenhum registro encontrado",
		   	          "sSearch": "Pesquisar",
		   	          "oPaginate": {
		   	              "sNext": "Próximo",
		   	              "sPrevious": "Anterior",
		   	              "sFirst": "Primeiro",
		   	              "sLast": "Último"
		   	          },
		   	          "oAria": {
		   	              "sSortAscending": ": Ordenar colunas de forma ascendente",
		   	              "sSortDescending": ": Ordenar colunas de forma descendente"
		   	          }
		   	      },
		        	paging:         false,
		        	ordering:       false,
			        info:           false,
			        searching:      false,
			        //pageLength: 5
 	            });

		   	 $('.tabela-avancada').DataTable({
	   	            "language": {
	   	             "sEmptyTable": "Nenhum registro encontrado",
		   	          "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
		   	          "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
		   	          "sInfoFiltered": "(Filtrados de _MAX_ registros)",
		   	          "sInfoPostFix": "",
		   	          "sInfoThousands": ".",
		   	          "sLengthMenu": "_MENU_ resultados por página",
		   	          "sLoadingRecords": "Carregando...",
		   	          "sProcessing": "Processando...",
		   	          "sZeroRecords": "Nenhum registro encontrado",
		   	          "sSearch": "Pesquisar",
		   	          "oPaginate": {
		   	              "sNext": "Próximo",
		   	              "sPrevious": "Anterior",
		   	              "sFirst": "Primeiro",
		   	              "sLast": "Último"
		   	          },
		   	          "oAria": {
		   	              "sSortAscending": ": Ordenar colunas de forma ascendente",
		   	              "sSortDescending": ": Ordenar colunas de forma descendente"
		   	          }
		   	      }
  	            });

		   	 $('.tabela-fixed').DataTable({
	   	            "language": {
	   	             "sEmptyTable": "Nenhum registro encontrado",
		   	          "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
		   	          "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
		   	          "sInfoFiltered": "(Filtrados de _MAX_ registros)",
		   	          "sInfoPostFix": "",
		   	          "sInfoThousands": ".",
		   	          "sLengthMenu": "_MENU_ resultados por página",
		   	          "sLoadingRecords": "Carregando...",
		   	          "sProcessing": "Processando...",
		   	          "sZeroRecords": "Nenhum registro encontrado",
		   	          "sSearch": "Pesquisar",
		   	          "oPaginate": {
		   	              "sNext": "Próximo",
		   	              "sPrevious": "Anterior",
		   	              "sFirst": "Primeiro",
		   	              "sLast": "Último"
		   	          },
		   	          "oAria": {
		   	              "sSortAscending": ": Ordenar colunas de forma ascendente",
		   	              "sSortDescending": ": Ordenar colunas de forma descendente"
		   	          }
		   	      },
			        select: {
			            style: 'os',
			            items: 'cell'
			        }
  	            });

			   	$("table tbody tr").click(function(e) {
			   		console.log(1);
		            $(this).addClass('selected').siblings().removeClass('selected');
			    });
				
		   	 
		});	