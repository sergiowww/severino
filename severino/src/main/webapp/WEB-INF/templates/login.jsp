<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<title>Severino - Login</title>
</head>
<body>
	<div class="container">
		<h3 style="text-align: center; margin-bottom: 20px;">
			Controle de Entrada e Sa�da de Visitantes - Severino <br />
			<small>Procuradoria Regional do Trabalho da 8� Regi�o</small>
		</h3>
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<div style="max-width: 200px; margin-left: auto; margin-right: auto;">
			<form:form cssClass="form-signin" servletRelativeAction="/login">
				<h5 class="form-signin-heading">Autentica��o do usu�rio</h5>
				<div class="form-group">
					<label for="inputEmail" class="sr-only">Usu�rio</label>
					<input type="text" name="username" id="inputEmail" class="form-control" placeholder="nome.sobrenome" required autofocus>
					<label for="inputPassword" class="sr-only">Senha</label>
					<input type="password" name="password" id="inputPassword" class="form-control" placeholder="Senha" required>
				</div>
				<div class="form-group">
					<button class="btn btn-lg btn-primary btn-block" type="submit">
						<span class="glyphicon glyphicon-log-in"></span>
						Entrar
					</button>
				</div>
			</form:form>
		</div>
		<span class="text-muted">
			Vers�o publicada em:
			<fmt:formatDate value="${dataHoraPublicacao}" dateStyle="medium" timeStyle="short" type="both" />
		</span>
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>