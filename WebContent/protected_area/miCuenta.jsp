<%@page import="controller.Config"%>
<%@page import="classes.Usuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mi Cuenta</title>
</head>
<body>

<jsp:include page="/common/userHeader.jsp" />

		<div class="contentWrapper">
		<div id="login-form"class="miCuenta-form">
				<h3>Mi Cuenta</h3>
				<fieldset>
				<% Usuario usuario = (Usuario) request.getAttribute("usuario");
				%>
					<form method="POST" action="${pageContext.request.contextPath}/protected_area/updateDeleteUsuario">
						<input class="field" type="text" name="username" placeholder="Usuario" value="<%=usuario.getUsername()%>" readonly> 
						<input class="field" type="text" name="mail" placeholder="E-mail" value="<%=usuario.getMail()%>"> 
						<input class="field" type="password" name="password" placeholder="Contrase�a" value="<%=usuario.getPassword()%>">
						<input class="field" type="password" name="password2" placeholder="Repetir contrase�a" value="<%=usuario.getPassword()%>">
						<input class="button blue" name="delete" type="submit" value="Darse de baja" onClick="return confirm('�Desea darse de baja?\nEsta operaci�n NO podra deshacerse');">
						<input class="button blue" name="update" type="submit" value="Actualizar" onClick="return confirm('�Desea actualizar su informaci�n personal?');">
					</form>
				</fieldset>
				<%
				if (request.getSession().getAttribute("mensaje") != null) {
				%>
				<div class="errormsg">
					<p class="errorfont"><%=request.getSession().getAttribute("mensaje")%></p>
				</div>
				<%
					}
					request.getSession().setAttribute("mensaje", null);
				%>
			</div>
	</div>
	
<jsp:include page="/common/footer.jsp" />

</body>
</html>