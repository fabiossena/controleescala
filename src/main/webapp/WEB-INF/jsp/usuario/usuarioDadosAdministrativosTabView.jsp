
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@page session="true"%>                            
         
<h5>Dados administrativos</h5>
<div class="container border-top panel-custom">                  
    <div class="form-group">
        <label for="funcaoId" class="col-md-12 control-label">Função</label>
        <div class="col-md-9">
            <form:select path="funcaoId" class="form-control" items="${funcoes}"  itemLabel="nome" itemValue="id" disabled="${isDisableCamposChaves || isDisableTodosCampos}"/>
        </div>
    </div>		                           
                     
    <div class="form-group">
        <label for="matricula" class="col-md-12 control-label">Matrícula Ixia *</label>
        <div class="col-md-9">
     		<form:input path="matricula" class="form-control" name="matricula" placeholder="matricula" readOnly="${isDisableCamposChaves || isDisableTodosCampos}" maxlength="50" />
        </div>
    </div>                     
                     
    <div class="form-group">
        <label for="folgasDisponiveisAno" class="col-md-12 control-label">Folgas disponívels no ano</label>
        <div class="col-md-9">
            <input readOnly value="${usuario.matricula}" class="form-control" />
        </div>
    </div>                            
                     
    <div class="form-group">
        <label for="bancoHoras" class="col-md-12 control-label">Banco de horas</label>
        <div class="col-md-9">
            <input readOnly value="${usuario.matricula}" class="form-control" />
        </div>
    </div>		                           
                     
    <div class="form-group">
        <label for="valorMinuto" class="col-md-12 control-label">Valor minuto</label>
        <div class="col-md-9">
            <input readOnly value="${usuario.matricula}" class="form-control" />
        </div>
    </div>
</div>