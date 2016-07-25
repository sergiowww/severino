<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<title>Usuário - ${usuario.id}</title>
</head>
<body>
	<tags:menu selectedItem="usuario/registros" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<div class="form-group">
			<div class="form-group row">
				<label class="col-sm-2 form-control-label">Login</label>
				<div class="col-sm-10">
					<p class="form-control-static">${usuario.id}</p>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2 form-control-label">Nome</label>
				<div class="col-sm-10">
					<p class="form-control-static">${usuario.nome}</p>
				</div>
			</div>
			<div class="col-sm-offset-2 col-sm-10">
				<a class="btn btn-default" href="usuario/registros">
					<span class="glyphicon glyphicon-circle-arrow-left"></span>
					Voltar
				</a>
			</div>
		</div>
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>