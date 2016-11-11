<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<title>Registro de Ponto</title>
</head>
<body>
	<tags:menu selectedItem="controleMotorista/registros" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<form:form servletRelativeAction="/controleMotorista" cssClass="form-horizontal" modelAttribute="controleMotorista">
			<form:hidden path="id" />
			<div class="form-group">
				<tags:inputField name="dataHora" label="Data e hora" type="date" mask="00/00/0000 00:00" requiredField="true" />
				<tags:selectField name="fluxo" label="Fluxo" collection="${fluxos}" itemLabel="descricao" itemValue="name" searchItems="false" requiredField="true" />
				<tags:selectField name="motorista.id" label="T�cnico" collection="${motoristas}" itemLabel="nome" itemValue="id" searchItems="false" requiredField="true" />
			</div>
			<tags:defaultButtons backUrl="controleMotorista/registros" />
		</form:form>
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>