<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page session="true" %>
<%
    if (request.getSession().getAttribute("usuario") != null) {
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Página Principal</title>
        <link rel="icon" href="img/perfil.png">
    </head>
    <body>
        <h1>Hola, ${usuario.persona.nombres} ${usuario.persona.apellidos}. Bienvenido a SISCOOB</h1>
    </body>
</html>
<%
    } else {
        response.sendRedirect("../backend/loginAdministrator.jsp");
    }
%>