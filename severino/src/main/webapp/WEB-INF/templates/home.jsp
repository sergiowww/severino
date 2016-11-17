<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="css/helpers/home.css" />
<title>In�cio</title>
</head>
<body>
	<tags:menu />
	<div class="container">
		<div class="btn-group">
			<a href="visita/" class="btn btn-default btn-sm">
				<span class="glyphicon glyphicon-briefcase"></span>
				Nova visita
			</a>
			<a href="viagem/" class="btn btn-default btn-sm">
				<span class="glyphicon glyphicon-road"></span>
				Fluxo Motoristas
			</a>
			<a href="acessoGaragem/" class="btn btn-default btn-sm">
				<span class="glyphicon glyphicon-list-alt"></span>
				Acesso Garagem
			</a>
		</div>
		<h3>
			Severino
			<small class="text-muted">Controle de acesso da Procuradoria Regional do Trabalho da 8� Regi�o - PRT8</small>
		</h3>
		<div class="row">
			<div class="col-md-4">
				<jsp:include page="home-motoristas.jsp" />
			</div>
			<div class="col-md-8">
				<jsp:include page="home-servidores.jsp" />
			</div>
		</div>
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>