<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
$(document).ready(function() {	 
	window.onscroll = function() {
		$('#nav-main').css("left", window.pageXOffset + 'px');
		
		if (window.pageXOffset > 100) {
			$('.fixar').show();
			$('.fixar').css("left", window.pageXOffset + 'px');
		}	
		else {
			$('.fixar').hide();
			$('.fixar').css("left", '0');			
		}

		if (window.pageYOffset > 250) { 
			$('.fixar-x').show();
			$('.fixar-x').css("top", window.pageYOffset + 'px');
		}	
		else {
			$('.fixar-x').hide();
			$('.fixar-x').css("top", '0');			
		}
	}	
});
</script> 
	<nav id="nav-main" class="navbar navbar-dark bg-dark navbar-toggleable-sm navbar-inverse bg-inverse">
	  <span>
		<button class="navbar-toggler navbar-toggler-left collapsed" type="button" data-toggle="collapse" data-target="#navbar" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
		   <span class="navbar-toggler-icon"></span>
		</button>
	    <a class="navbar-brand" href="<c:url value='/' />" style="margin-left: 10px">Ixia - Controle de escalas</a>
	  </span>
	  		
	  <div class="navbar-collapse justify-content-between collapse" id="navbar">
	  
		<c:if test="${usuarioLogado != null}">
     		<c:if test="${requestScope['javax.servlet.forward.request_uri'] != requestScope['javax.servlet.include.context_path'].concat('/')}">
	  	  		
				<strong><span class="navbar-brand" style="font-size: 12pt; font-style: italic">Bem vindo ${usuarioLogado.primeiroNome} (${usuarioLogado.funcao.nome.toLowerCase()})</span></strong>
  	  		</c:if> 	
   		</c:if>
   		
		<ul class="navbar-nav mr-auto">
			<c:if test="${usuarioLogado == null}">
			    
	     		<li class="nav-item <c:if test='${isLoginPage}'>active</c:if>">
		  	  		<a class="nav-link navbar-brand" style="font-size: 12pt" href="<c:url value='/login' />">Login</a>
	      		</li>
	      		
	     		<li class="nav-item <c:if test='${!isLoginPage}'>active</c:if>">
		  	  		<a class="nav-link navbar-brand" style="font-size: 12pt" href="<c:url value='/cadastroinicial' />">Cadastro</a>
	      		</li>		
				
			</c:if>
			
			<c:if test="${usuarioLogado != null}">	 
	     		<li class="nav-item <c:if test="${requestScope['javax.servlet.forward.request_uri'] == requestScope['javax.servlet.include.context_path'].concat('/')}">active</c:if>">
		  	  		<a class="nav-link navbar-brand" style="font-size: 12pt" href="<c:url value='/' />">Inicial</a>
	      		</li>	     
	     		<li class="nav-item <c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('/usuario/meucadastro')}">active</c:if>">
		  	  		<a class="nav-link navbar-brand" style="font-size: 12pt" href="<c:url value='/usuario/meucadastro' />">Meu cadastro</a>
	      		</li>	
				<c:if test="${!isAtendimento}">			      
		     		<li class="nav-item <c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('/usuario') && !requestScope['javax.servlet.forward.request_uri'].contains('/usuario/meucadastro')}">active</c:if>">
			  	  		<a class="nav-link navbar-brand" style="font-size: 12pt" href="<c:url value='/usuarios' />">Cadastro usuários</a>
		      		</li>	
				</c:if>			
				<c:if test="${isAdministracao}">			      
		     		<li class="nav-item <c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('/convite')}">active</c:if>">
			  	  		<a class="nav-link navbar-brand" style="font-size: 12pt" href="<c:url value='/convite' />">Convite usuário</a>
		      		</li>
				</c:if>
				<c:if test="${!isAtendimento && !isFinanceiro}">			      
		     		<li class="nav-item <c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('/projeto')}">active</c:if>">
			  	  		<a class="nav-link navbar-brand" style="font-size: 12pt" href="<c:url value='/projetos' />">Cadastro projetos</a>
		      		</li>	
				</c:if>			      
	     		<li class="nav-item <c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('/dashboard')}">active</c:if>">
		  	  		<a class="nav-link navbar-brand" style="font-size: 12pt" href="<c:url value='/dashboard' />">Dashboard</a>
	      		</li>	
				<c:if test="${isAtendimento || isAdministracao || isMonitoramento || isGerencia}">			      
		     		<li class="nav-item <c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('/ausencia')}">active</c:if>">
			  	  		<a class="nav-link navbar-brand" style="font-size: 12pt" href="<c:url value='/ausencias' />">Ausências</a>
		      		</li>	
				</c:if>	
							      
	     		<li class="nav-item <c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('/aprova')}">active</c:if>"  style="display: none">
		  	  		<a class="nav-link navbar-brand" style="font-size: 12pt" href="<c:url value='/aprovacaohoras' />">Aprovação horas</a>
	      		</li>	
			</c:if>
			
			
			<c:if test="${usuarioLogado != null}">
	     		<li class="nav-item">
		  	  		<a class="nav-link navbar-brand" style="font-size: 12pt" href="<c:url value='/sair' />">Sair</a>
	      		</li>	
			</c:if>
		
		</ul>
	  </div>
	</nav>