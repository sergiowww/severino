<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<title>Setor</title>
</head>
<body>
	<tags:menu selectedItem="setor/registros" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<form:form servletRelativeAction="/setor" cssClass="form-horizontal" commandName="setor">
			<form:hidden path="id" />
			<div class="form-group">
				<tags:inputField label="Nome" name="nome" type="text" />
				<tags:inputField label="Andar" name="andar" type="number" />
				<tags:inputField label="Sala" name="sala" type="text" />
			</div>
			<tags:defaultButtons backUrl="setor/registros" />
		</form:form>
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>