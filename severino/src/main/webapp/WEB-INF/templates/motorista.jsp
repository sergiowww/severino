<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<script type="text/javascript" src="js/helpers/motorista.js" charset="UTF-8"></script>
<title>Condutor</title>
</head>
<body>
	<tags:menu selectedItem="motorista/registros" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<form:form servletRelativeAction="/motorista" cssClass="form-horizontal" modelAttribute="motorista">
			<form:hidden path="id" />
			<div class="form-group">
				<tags:inputField label="Nome" name="nome" type="text" requiredField="true" extraCssClass="col-md-6" />
				<tags:inputField label="Matricula" name="matricula" type="text" requiredField="true" extraCssClass="col-md-3"/>
				<tags:selectField label="Cargo" name="cargo" collection="${cargos}" itemLabel="descricao" itemValue="name" searchItems="false" requiredField="true" extraCssClass="col-md-3" />
			</div>
			<tags:defaultButtons backUrl="motorista/registros" />
		</form:form>
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>