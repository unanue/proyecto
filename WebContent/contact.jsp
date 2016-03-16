<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-3.3.6-dist/css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-3.3.6-dist/css/custom.css" type="text/css">
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
<title>Contacto</title>
</head>
<body>
	<jsp:include page="/common/header.jsp" />
	<div class="contentWrapper">
		<h1>Contacto</h1>	
	    <h3>E-Mail</h3>
		    <address>
	  			<strong>I�igo Unanue Teller�a</strong><br>
	 			 <a href="mailto:#">i.unanue.telleria@gmail.com</a>
			</address>
		<h3>Link al repositorio del proyecto</h3>
		<a href="https://github.com/iunanue/proyecto">Repositorio</a>
	</div>
	<jsp:include page="/common/footer.jsp" />
</body>
</html>