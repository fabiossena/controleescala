
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@page session="true"%>                            
                      
<h5>Dados bancários</h5>
<div class="container border-top panel-custom">            
     <div class="form-group">
         <label for="banco" class="col-md-12 control-label">Banco</label>
         <div class="col-md-9">
             <form:select path="bancoId" class="form-control" itemLabel="nome" itemValue="id" items="${bancos}" disabled="${isDisableTodosCampos}"/>
         </div>
     </div>
                      
     <div class="form-group">
         <label for="agencia" class="col-md-12 control-label">Agência</label>
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
         <label for="digito" class="col-12 col-xm-2 col-sm-2 col-md-2 col-lg-2 col-xl-2 control-label">Dígito</label>
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
         <label for="cpf" class="col-md-12 control-label">Cpf</label>
         <div class="col-md-9">
             <input readOnly value="${usuario.cpf}" class="form-control"/>
         </div>
     </div>
</div>