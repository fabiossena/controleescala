<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page session="true"%>

<head>
	<title>Ixia - Sistema de Escala - Error</title>

    <jsp:include page="shared/headerPartialView.jsp"/>
</head>

<body>

	<jsp:include page="shared/navbarPartialView.jsp"/>


    <div class="container">    
        <div id="signupbox" style="margin-top:50px" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<c:if test="${not empty error}">
			<div>
				Error: ${error}
			</div>
			</c:if>
			
			<c:if test="${not empty status}">
			<div>
				Status: ${status}
			</div>
			</c:if>
			
			<c:if test="${not empty message}">
				<div class="msg">Message: ${message}</div>
			</c:if>	 
	   
         </div> 
    </div>
	   
</body>

</html>
