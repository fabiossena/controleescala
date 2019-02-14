
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@page session="true"%>
	
<h5>Dados principais</h5>
	<div class="container border-top panel-custom"> 
	  <div class="form-group">
		  <div class="form-group">
		      <label for="primeiroNome" class="col-md-12 control-label">Nome *</label>
		      <div class="col-md-9">
		          <form:input path="primeiroNome" class='form-control ${result.hasFieldErrors("primeiroNome") ? "is-invalid" : ""}' name="primeiroNome" placeholder="primeiro nome" disabled="${isDisableTodosCampos}" maxlength="100" />
	      	  <div class="invalid-feedback"><form:errors path="primeiroNome" /></div>
	      </div>
	  </div>
		           
	  <div class="form-group">
	      <label for="sobrenomeprincipal" class="col-md-12 control-label">Sobrenome *</label>
	      <div class="col-md-9">
	          <form:input path="sobrenome" class='form-control ${result.hasFieldErrors("sobrenome") ? "is-invalid" : ""}' name="sobrenome" placeholder="sobrenome" disabled="${isDisableTodosCampos}" maxlength="150" />
	      	  <div class="invalid-feedback"><form:errors path="sobrenome" /></div>
	      </div>
	  </div>
	                   
	  <div class="form-group">
	      <label for="cpf" class="col-md-12 control-label">Cpf *</label>
	      <div class="col-md-9">	      
	          <form:input path="cpf" class='form-control ${result.hasFieldErrors("cpf") ? "is-invalid" : ""}' name="cpf" placeholder="cpf" disabled="${isDisableTodosCampos}" maxlength="50" />
	      	  <div class="invalid-feedback"><form:errors path="cpf" /></div>
	      </div>
	  </div>
	                   
	  <div class="form-group">
	      <label for="email" class="col-md-12 control-label">Email *</label>
	      <div class="col-md-9">
	          <form:input path="email" class='form-control ${result.hasFieldErrors("email") ? "is-invalid" : ""}' name="email" placeholder="email" disabled="${isDisableTodosCampos}" maxlength="100" />
	      	  <div class="invalid-feedback"><form:errors path="email" /></div>
	      </div>
	  </div>
	                   
	  <div class="form-group">
	      <label for="documentoidentidade" class="col-md-12 control-label">Rg</label>
	      <div class="col-md-9">
	          <form:input path="rg" class="form-control" name="rg" placeholder="rg" disabled="${isDisableTodosCampos}" maxlength="50" />
	      </div>
	  </div>
	                   
	  <div class="form-group">
	      <label for="cnpj" class="col-md-12 control-label">Cnpj *</label>
	      <div class="col-md-9">	      
	          <form:input path="cnpj" class='form-control ${result.hasFieldErrors("cnpj") ? "is-invalid" : ""}' name="cnpj" placeholder="cnpj" disabled="${isDisableTodosCampos}" maxlength="50" />
	      	  <div class="invalid-feedback"><form:errors path="cnpj" /></div>
	      </div>
	  </div>
	                   
	  <div class="form-group">
	      <label for="razaoSocial" class="col-md-12 control-label">Razão social</label>
	      <div class="col-md-9">	      
	          <form:input path="razaoSocial" class='form-control ${result.hasFieldErrors("razaoSocial") ? "is-invalid" : ""}' name="razaoSocial" placeholder="razaoSocial" disabled="${isDisableTodosCampos}" maxlength="50" />
	      	  <div class="invalid-feedback"><form:errors path="razaoSocial" /></div>
	      </div>
	  </div>
	                   
	  <div class="form-group">
	      <label for="telefone" class="col-md-12 control-label">Telefone *</label>
	      <div class="col-md-9">
	          <form:input path="telefone" class='form-control mask-phone ${result.hasFieldErrors("telefone") ? "is-invalid" : ""}' name="telefone" placeholder="telefone com DDD" disabled="${isDisableTodosCampos}" maxlength="50" />
	      	  <div class="invalid-feedback"><form:errors path="telefone" /></div>
	      </div>
	  </div>
	                   
<%-- 	  <div class="form-group">
	      <label for="ramal" class="col-md-12 control-label">Ramal</label>
	      <div class="col-md-9">
	          <form:input path="ramal" class="form-control" name="ramal" placeholder="ramal" disabled="${isDisableTodosCampos}" maxlength="20" />
	      </div>
	  </div> --%>
	                   
	  <div class="form-group">
	      <label for="celular" class="col-md-12 control-label">Celular</label>
	      <div class="col-md-9">
	          <form:input path="celular" class="form-control mask-phone" name="celular" placeholder="celular com DDD" disabled="${isDisableTodosCampos}" maxlength="50" />
	      </div>
	  </div>
	                   
	  <div class="form-group">
	      <label for="telefonerecado" class="col-md-12 control-label">Telefone recado</label>
	      <div class="col-md-9">
	          <form:input path="telefoneRecado" class="form-control mask-phone" name="telefoneRecado" placeholder="telefone de recado" disabled="${isDisableTodosCampos}" maxlength="50" />
	      </div>
	  </div>
	                   
	  <div class="form-group">
	      <label for="skype" class="col-md-12 control-label">Skype</label>
	      <div class="col-md-9">
	          <form:input path="skype" class="form-control" name="skype" placeholder="skype" disabled="${isDisableTodosCampos}" maxlength="50" />
	      </div>
	  </div>	  
	        
	  <div class="form-group">
	      <label for="skype" class="col-md-12 control-label">Sexo</label>
	      <div class="col-md-9">
	      		<form:radiobutton path="sexo" value="M" disabled="${isDisableTodosCampos}" />Masculino
				<form:radiobutton path="sexo" value="F" disabled="${isDisableTodosCampos}" />Feminino
	      </div>
	  </div>
	                   
	  <div class="form-group">
	      <label for="skype" class="col-md-12 control-label">Data de nascimento</label>
	      <div class="col-md-9">
	          <form:input path="dataNascimento" class="form-control mask-date datepicker" placeholder="data de nascimento" disabled="${isDisableTodosCampos}"/>
	      </div>
	  </div>

	 <div
		class="form-group">
		<label for="observacaoAdicionais" class="control-label  col-12 col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">Observações adicionais</label>
	      <div class="col-md-9">
			<form:textarea path="observacaoAdicionais" class='form-control'
				placeholder="observações" disabled="${isDisableTodosCampos}"
				maxlength="500" />
			</div>
		<div class="invalid-feedback">
			<form:errors path="observacaoAdicionais" />
		</div>
	 </div>
	        
	  <div class="form-group">
	      <label for="ativo" class="col-md-12 control-label">Ativo</label>
          <form:checkbox  path="ativo" disabled="${isDisableCamposChaves || isDisableTodosCampos}" class="form-control  col-1 col-sm-1 col-md-1 col-lg-1 col-xl-1" name="ativo" />
	  </div>
	                   
	  <div class="form-group">
	      <div class="col-md-9">
	      	  <i>Último acesso: ${usuario.ultimoAcessoDataHora}</i>
	      </div>
	  </div>
		
	</div>
</div>