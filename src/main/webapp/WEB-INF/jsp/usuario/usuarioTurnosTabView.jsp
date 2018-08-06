
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h5>Turnos disponíveis</h5>
<div class="container border-top panel-custom">
    <div class="form-group">
        <label for="banco" class="col-md-8 control-label">Periodo disponível para trabalho</label>
        <div class="col-md-8">
            <form:select path="periodoDisponivelId" class="form-control is-valid" disabled="${isDisableTodosCampos}">
   				<option>Selecione um periodo</option>
            	<form:options items="${periodos}" itemLabel="nome" itemValue="id" />
            </form:select>
            <div class="valid-feedback">${orientacoesPeriodoDisponivel}</div>
        </div>
    </div>
           
</div>


<br />


<h5>Folga semanal planejada</h5>      
<div class="container border-top panel-custom">
     <div class="container row">
       
       <div class="form-group col-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
       	<label for="folgaMotivo" class="control-label">Motivo</label>
       	<div>
           <select id="folgaMotivo" class="form-control" <c:if test="${isDisableTodosCampos}">disabled</c:if>>
	        <c:forEach items="${motivos}" var="motivo">
	            <option value="${motivo.id}">${motivo.nome}</option>
	        </c:forEach>
           </select>
           	<div class="invalid-feedback" id="feedback-folgaMotivo"></div>
           </div>
       </div>
       
       <div class="form-group col-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
       	<label for="folgaDiaSemana" class="control-label">Dia semana</label>
       	<div>
            <select id="folgaDiaSemana" class="form-control" <c:if test="${isDisableTodosCampos}">disabled</c:if>>
		        <c:forEach items="${diasSemana}" var="diaSemana">
		        	<option value="${diaSemana.id}">${diaSemana.nome}</option>
		        </c:forEach>
            </select>
           	<div class="invalid-feedback" id="feedback-folgaDiaSemana"></div>
           </div>
       </div>
       
       <div class="form-group col-12 col-sm-6 col-md-6 col-lg-6 col-xl-2">
       	<label for="folgaId" class="control-label">Hora inicio</label>
       	<div>
           	<input id="folgaId" class="form-control mask-hour" type="hidden" />
           	<input class="form-control mask-hour" id="folgaHoraInicio" <c:if test="${isDisableTodosCampos}">disabled</c:if> />
           	<div class="invalid-feedback" id="feedback-folgaHoraInicio"></div>
           </div>
       </div>
       
       <div class="form-group col-12 col-sm-6 col-md-6 col-lg-6 col-xl-2">
       	<label for="folgaHoraFim" class="control-label">Hora fim</label>
       	<div>
           	<input class="form-control mask-hour" id="folgaHoraFim"  <c:if test="${isDisableTodosCampos}">disabled</c:if>/>
           	<div class="invalid-feedback" id="feedback-folgaHoraFim"></div>
           </div>
       </div>
       
       <div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
			<input id="btn-salvar-item" type="button" class="btn btn-sm btn-success float-right" value="Salvar item"  onclick="salvarFolga()" <c:if test="${isDisableTodosCampos}">disabled</c:if> />
			<input id="btn-limpar-item" type="button" class="btn btn-sm btn-success float-right" value="Limpar" style="margin-right: 10px" onclick="limparFolga()" <c:if test="${isDisableTodosCampos}">disabled</c:if> />
		</div>
     </div>

 	
	<div class="table-container table-responsive"  style="margin: 20px 0 30px 0">
		<table id="tabela-folga-semanal-planejada" class="display tabela-simples">
	       <!-- Header Table -->
	       <thead>
	            <tr>
					<th>Motivo</th>
	                <th>Dia semana</th>
					<th>Hora início</th>
	                <th>Hora fim</th>
	                <th>Ação</th>
	            </tr>
	        </thead>
	        <!-- Footer Table -->
	        <tfoot>
	        <tbody>        
		        <c:forEach items="${folgasSemanaisPlanejadas}" var="item">
		            <tr id="folga${item.id}" class="folga">
		                <td class="rowclick" id="folgaMotivo${item.id}" >${item.motivo.nome}</td>
						<td class="rowclick" id="folgaDiaSemana${item.id}" >${item.diaSemana.nome}</td>
		                <td class="rowclick" id="folgaHoraInicio${item.id}">${item.horaInicio}</td>
		                <td class="rowclick" id="folgaHoraFim${item.id}" >${item.horaFim}</td>
		                <td><input type="button" class="btn btn-sm btn-danger" value="apagar" <c:if test="${isDisableTodosCampos}">disabled</c:if> /></td>
		            </tr>
		        </c:forEach>
	        </tbody>
	    </table>
	</div>

</div>