
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@page session="true"%>                            
                      
<h5>Dados banc�rios</h5>
<div class="container border-top panel-custom">            
     <div class="form-group">
         <label for="banco" class="col-md-12 control-label">Banco</label>
         <div class="col-md-9">
             <form:select path="bancoId" class="form-control" disabled="${isDisableTodosCampos}">
              <form:options itemLabel="nome" itemValue="id" items="${bancos}" />
              <option value="0">Outro</option>
              </form:select>
         </div>
     </div>
                      
     <div class="form-group">
         <label for="agencia" class="col-md-12 control-label">Ag�ncia</label>
         <div class="col-md-9">
             <form:input path="agencia" class="form-control" name="agencia" placeholder="agencia" disabled="${isDisableTodosCampos}" maxlength="50"/>
         </div>
     </div>                           
                      
     <div class="form-group">
         <label for="conta" class="row col-12 col-xm-10 col-sm-10 col-md-10 col-lg-10 col-xl-10 control-label">Conta</label>
         <div class="col-md-9">
             <form:input path="conta" class="form-control" name="conta" placeholder="conta" disabled="${isDisableTodosCampos}" maxlength="50"/>
         </div>
     </div>                        
                      
     <div class="form-group">
         <label for="digito" class="col-12 col-xm-2 col-sm-2 col-md-2 col-lg-2 col-xl-2 control-label">D�gito</label>
         <div class="col-md-9">
             <form:input path="digito" class="form-control" name="digito" placeholder="digito" disabled="${isDisableTodosCampos}" maxlength="10"/>
         </div>
     </div>                           
                      
     <div class="form-group">
         <label for="nomeCompletoCadastradoBanco" class="col-md-12 control-label">Nome completo* (cadastrado no banco)</label>
         <div class="col-md-9">
             <form:input path="nomeCompletoCadastradoBanco" class="form-control" name="nomeCompletoCadastradoBanco" placeholder="nome completo cadastrado no banco" disabled="${isDisableTodosCampos}" maxlength="250"/>
         </div>
     </div>
	        
	  <div class="form-group">
	      <label for="contaCorrente" class="col-md-12 control-label">Conta corrente</label>
          <form:checkbox  path="contaCorrente" disabled="${isDisableTodosCampos}" class="form-control  col-1 col-sm-1 col-md-1 col-lg-1 col-xl-1" name="contaCorrente" />
	  </div>		                           
                      
     <div class="form-group">
         <label for="cpf" class="col-md-12 control-label">Cpf</label>
         <div class="col-md-9">
             <input readOnly value="${usuario.cpf}" class="form-control"/>
         </div>
     </div>

	 <div
		class="form-group col-12 col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
		<label for="observacoes" class="control-label">Observa��es</label>
		<form:textarea path="observacaoConta" class='form-control'
			placeholder="observa��es" disabled="${isDisableTodosCampos}"
			maxlength="500" />
		<div class="invalid-feedback">
			<form:errors path="observacaoConta" />
		</div>
	 </div>
</div>