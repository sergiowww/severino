<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<title>Empresa</title>
</head>
<body>
	<tags:menu selectedItem="empresa/registros" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<form:form servletRelativeAction="/empresa" cssClass="form-horizontal" commandName="empresa">
			<form:hidden path="id" />
			<tags:inputField label="Nome" name="nome" type="text" extraCssClass="form-group" />
			<tags:defaultButtons backUrl="empresa/registros" />
		</form:form>
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>