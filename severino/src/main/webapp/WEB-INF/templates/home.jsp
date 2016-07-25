<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<title>Início</title>
</head>
<body>
	<tags:menu />
	<div class="container">
		<h3>
			Severino
			<small class="text-muted">Controle de acesso da Procuradoria Regional do Trabalho da 8ª Região - PRT8</small>
		</h3>
		<a href="visita/" class="btn btn-default btn-lg">
			<span class="glyphicon glyphicon-triangle-right"></span>
			Cadastrar visita
		</a>
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>