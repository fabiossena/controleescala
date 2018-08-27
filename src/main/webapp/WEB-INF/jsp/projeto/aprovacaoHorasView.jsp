<!DOCTYPE html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@page session="true"%>

<head>
	<title>Ixia - Sistema de Escala - Aprovação de horas</title>
    <jsp:include page="../shared/headerPartialView.jsp"/>

</head>
<body onload="$('#primeiroNome').focus()">

	<jsp:include page="../shared/navbarPartialView.jsp"/>

    <div class="container">    
	        <div style="margin-top:50px" class="mainbox col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
        	  <%-- <form:form modelAttribute="usuario" class="form-horizontal" method="POST"> --%>
		        	  
		          <div class="panel panel-info">
		              <div class="panel-heading row">
		                	
		                  <h3 class="panel-title col-7 col-sm-7 col-md-7 col-lg-7 col-xl-7">
		                  	Aprovação horas     
		                  </h3>     	
		                  		
						  <div class="col-3 col-sm-3 col-md-3 col-lg-3 col-xl-3">
			        	  	<c:if test="${!isDisableTodosCampos}">
			        	  		<input id="btn-salvar" type="submit" class="btn btn-success" value="Salvar" style="margin: 1px"  />
			        	  	</c:if>				        	  	
		        	  	  </div>
		              </div>  
		              
		              <div class="panel-body" >
		              
		        		<jsp:include page="../shared/errorPartialView.jsp"/>	
	        	  	                      
            			<div class="row align-items-start" style="margin-top: 30px">	  
							<div class="tab-content col-8 col-sm-8 col-md-9 col-lg-9 col-xl-9 border" style="padding-top:20px; min-height: 450px;" >
							
															
								<h5>Aceite/validação de dias/horas trabalhadas</h5>
								<div class="container border-top panel-custom">
								  <div class="form-group">
									                   
																                   
								      <div class='form-group col-12 col-sm-12 col-md-12 col-lg-8 col-xl-8'>
									    <label for="escalaId" class="control-label">Selecione uma escala para iniciar:</label>
								        <form:select path="escalaId" id="selected-projeto-escala-principal" class='form-control' >
									        <option value="0"></option>
									        <c:forEach items="${escalas}" var="item">
									        	<option <c:if test="${item.id == escalaId}">selected</c:if> value="${item.id}">${item.descricaoCompletaEscala}</option> 
									        </c:forEach>
								        </form:select>
							          </div>
							         
									   <div class="form-group">
									      <label for="matricula" class="col-md-12 control-label">Escala</label>
									      <div class="col-md-9">
								             
									      </div>
									    </div>
									  
									  </div>
								 </div>
							</div>
      		           </div>
		          </div>
                      
           <%-- 	</form:form> --%>

                
         </div> 
    </div>
    
</body>

</html>