
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@page session="true"%>

<form:form modelAttribute="projeto" id="form-projeto" method="POST"
	class="form-horizontal row col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
	<h4 class="col-8 col-sm-8 col-md-8 col-lg-8 col-xl-8">Dados
		principais</h4>



	<span class="col-4 col-sm-4 col-md-4 col-lg-4" style="top: -5px">
		<c:if test="${!isDisableCamposProjeto}">
			<input id="btn-salvar" type="submit"
				class="btn btn-success float-right" value="Salvar" />
		</c:if>
	</span>

	<div
		class="container row border-top panel-custom col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">

		<div class="form-group col-12 col-sm-12 col-md-4 col-lg-4 col-xl-4">
			<label for="nome" class="control-label">Nome *</label>
			<form:input 
				path="nome"
				class='form-control ${result.hasFieldErrors("nome") ? "is-invalid" : ""}'
				placeholder="nome" disabled="${isDisableCamposProjeto}"
				maxlength="150" />
			<div class="invalid-feedback">
				<form:errors path="nome" />
			</div>
		</div>

		<div class="form-group col-12 col-sm-12 col-md-8 col-lg-8 col-xl-8">
			<label for="descricaoProjeto" class="control-label">Descrição</label>
			<form:input path="descricaoProjeto"
				class='form-control ${result.hasFieldErrors("descricaoProjeto") ? "is-invalid" : ""}'
				placeholder="descrição" disabled="${isDisableCamposProjeto}"
				maxlength="200" />
			<div class="invalid-feedback">
				<form:errors path="descricaoProjeto" />
			</div>
		</div>

		<div class="form-group col-12 col-sm-12 col-md-6 col-lg-6 col-xl-6">
			<label for="tipoApontamentoHorasId" class="control-label">Tipo
				apontamento horas *</label>
			<form:select path="tipoApontamentoHorasId"
				items="${tipoApontamentoHoras}" itemLabel="nome" itemValue="id"
				id="select-tipo-apontamento-horas"
				class='form-control ${result.hasFieldErrors("integracaoRoboId") ? "is-invalid" : ""}'
				disabled="${isDisableCamposProjeto}" />
			<div class="invalid-feedback">
				<form:errors path="tipoApontamentoHorasId" />
			</div>
		</div>

		<div class="form-group col-12 col-sm-12 col-md-6 col-lg-6 col-xl-6">
			<label for="integracaoRoboId" class="control-label">Id
				integração robô</label>
			<form:input path="integracaoRoboId" class='form-control'
				placeholder="id integração robô"
				disabled="${isDisableCamposProjeto}" maxlength="50" />
			<div class="invalid-feedback">
				<form:errors path="integracaoRoboId" />
			</div>
		</div>

		<div class="form-group col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
			<label for="gerenteId" class="control-label">Gerente *</label>
			<form:select path="gerente.Id" items="${gerentes}"
				itemLabel="nomeCompletoMatricula" itemValue="id" id="select-gerente"
				class='form-control editable-select ${result.hasFieldErrors("gerente") ? "is-invalid" : ""}'
				disabled="${isDisableCamposProjeto}" />
			<div class="invalid-feedback">
				<form:errors path="gerente.id" />
			</div>
		</div>

		<div
			class="form-group col-12 col-xm-12 col-sm-12 col-md-5 col-lg-5 col-xl-5">
			<label for="dataInicio"
				class='control-label ${result.hasFieldErrors("dataInicio") ? "is-invalid" : ""}'>Data
				início *</label>
			<form:input path="dataInicio" 
				 		id="dataInicioProjeto"
						class='form-control datepicker'
						placeholder="data início" 
						disabled="${isDisableCamposProjeto}" />
			<div class="invalid-feedback">
				<form:errors path="dataInicio" />
			</div>
		</div>

		<div
			class="form-group col-12 col-xm-12 col-sm-10 col-md-5 col-lg-5 col-xl-5">
			<label for="dataFim" class='control-label container row'> 
			<input
				id="check-box-data-fim" ${isDisableCamposProjeto ? "disabled" : ""}
				class="form-control row col-2 col-xm-2 col-sm-2 col-md-2 col-lg-2 col-xl-2"
				type="checkbox" style="top: 5px;" /> 
				<span class="col-10 col-xm-10 col-sm-10 col-md-10 col-lg-10 col-xl-10">Data fim</span>
			</label>
			<form:input 
				path="dataFim" 
				 id="dataFimProjeto"
				class='form-control datepicker'
				placeholder="data fim" 
				disabled="${isDisableCamposProjeto}" />
			<div class="invalid-feedback">
				<form:errors path="dataFim" />
			</div>
		</div>

		<div
			class="form-group col-2 col-xm-2 col-sm-2 col-md-2 col-lg-2 col-xl-2">
			<label for="ativo" class="control-label"><br />Ativo <form:checkbox
					path="ativo" disabled="${isDisableCamposProjeto}"
					class="form-control" /> </label>
		</div>

		<div
			class="form-group col-12 col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
			<label for="observacoes" class="control-label">Observações</label>
			<form:textarea path="observacaoProjeto" class='form-control'
				placeholder="observações" disabled="${isDisableCamposProjeto}"
				maxlength="500" />
			<div class="invalid-feedback">
				<form:errors path="observacaoProjeto" />
			</div>
		</div>

	</div>
</form:form>