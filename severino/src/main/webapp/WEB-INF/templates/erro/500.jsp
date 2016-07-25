<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../includes/head.jsp"></jsp:include>
<title>Erro da aplicação</title>
</head>
<body>
	<tags:menu />
	<div class="container">
		<div class="centering text-center error-container">
			<div class="text-center">
				<h4 class="text-warning">500</h4>
			</div>
			<div class="text-center">
				<div class="alert alert-danger">
					<strong>Erro:</strong>
					<i>${exception.message}</i> <br/>
					O sistema se comportou de maneira inesperada, entre em contato com a equipe de suporte de TI
				</div>
			</div>
			<hr>

		</div>
	</div>
	<jsp:include page="../includes/footer.jsp"></jsp:include>
</body>
</html>