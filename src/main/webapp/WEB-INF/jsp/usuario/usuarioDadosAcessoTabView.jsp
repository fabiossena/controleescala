
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@page session="true"%>
	
<h5>Dados acesso</h5>
<div class="container border-top panel-custom">
  <div class="form-group">
	                   
	  <div class="form-group">
	      <label for="matricula" class="col-md-12 control-label">Matricula Ixia</label>
	      <div class="col-md-9">
             <input readOnly value="${usuario.matricula}" class="form-control"  disabled="${isDisableTodosCampos}"/>
	      </div>
	  </div>
	  
	  <div class="form-group">
	      <label for="senha" class="col-md-12 control-label">Senha *</label>
	      <div class="col-md-9">
	          <form:input path="senha" type="password" class='form-control ${result.hasFieldErrors("senha") ? "is-invalid" : ""}' name="senha" placeholder="senha" disabled="${isDisableTodosCampos}" maxlength="50"/>
	      	  <div class="invalid-feedback"><form:errors path="senha"  disabled="${isDisableTodosCampos}"/></div>
	       </div>
	   </div>
		
		<div class="form-group">
		    <label for="password" class="col-md-12 control-label">Repetir senha *</label>
		    <div class="col-md-9">
		        <form:input path="repetirSenha" type="password" class='form-control ${result.hasFieldErrors("email") ? "is-invalid" : ""}' name="repetirSenha" placeholder="Repetir senha" disabled="${isDisableTodosCampos}" maxlength="50"/>
	      	  	<div class="invalid-feedback"><form:errors path="repetirSenha"  disabled="${isDisableTodosCampos}"/></div>
		    </div>
		</div>
		
	</div>
</div>