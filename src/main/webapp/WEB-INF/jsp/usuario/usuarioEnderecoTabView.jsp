
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@page session="true"%>
	
<h5>Endereço</h5>
<div class="container border-top panel-custom"> 
   <div class="form-group">
       <label for="cep" class="col-md-12 control-label">Cep</label>
       <div class="col-md-9">
           <form:input path="cep" class="form-control" name="cep" placeholder="cep" disabled="${isDisableTodosCampos}" maxlength="20" />	
       </div>
   </div>                   
                    
   <div class="form-group">
       <label for="logradouro" class="col-md-12 control-label">Logradouro</label>
       <div class="col-md-9">
           <form:input path="logradouro" class="form-control" name="logradouro" placeholder="logradouro" disabled="${isDisableTodosCampos}" maxlength="100"/>
       </div>
   </div>                 
                    
   <div class="form-group">
       <label for="numerologradouro" class="col-md-12 control-label">Numero</label>
       <div class="col-md-9">
           <form:input path="numeroLogradouro" class="form-control" name="numeroLogradouro" placeholder="numero" disabled="${isDisableTodosCampos}" maxlength="20"/>
       </div>
   </div>                           
                    
   <div class="form-group">
       <label for="bairro" class="col-md-12 control-label">Bairro</label>
       <div class="col-md-9">
           <form:input path="bairro" class="form-control" name="bairro" placeholder="bairro" disabled="${isDisableTodosCampos}" maxlength="100"/>
       </div>
   </div>                             
                    
   <div class="form-group">
       <label for="informacoesadicionaisendereco" class="col-md-12 control-label">Informações adicionais </label>
       <div class="col-md-9">
           <form:input path="informacoesAdicionaisEndereco" class="form-control" name="informacoesAdicionaisEndereco" placeholder="informações adicionais" disabled="${isDisableTodosCampos}" maxlength="100"/>
       </div>
   </div>                          
                    
   <div class="form-group">
       <label for="banco" class="col-md-12 control-label">Pais</label>
       <div class="col-md-9">
           <select id="select-endereco-pais" name="pais" class="form-control editable-select" <c:if test="${isDisableTodosCampos}">disabled</c:if>>
		       <c:forEach items="${paises}" var="pais">
		           <option value="${pais.id}">${pais.nome}</option>
		       </c:forEach>
		   </select>
       </div>
   </div>                
                    
   <div class="form-group">
       <label for="estado" class="col-md-12 control-label">Estado</label>
       <div class="col-md-9">
         <select id="select-endereco-estado" name="estado" class="form-control editable-select" <c:if test="${isDisableTodosCampos}">disabled</c:if>></select>
       </div>
   </div>
                    
   <div class="form-group">
       <label for="cidade" class="col-md-12 control-label">Cidade</label>
       <div class="col-md-9">
         <form:select path="cidadeId" id="select-endereco-cidade" name="cidade" class="form-control editable-select" disabled="${isDisableTodosCampos}"/>
       </div>
   </div>
 </div>
		