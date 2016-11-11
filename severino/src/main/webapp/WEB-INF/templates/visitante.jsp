<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<title>Visitante</title>
</head>
<body>
	<tags:menu selectedItem="visitante/registros" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<form:form servletRelativeAction="/visitante" cssClass="form-horizontal" modelAttribute="visitante">
			<form:hidden path="id" />
			<div class="form-group">
				<tags:inputField label="Nome" name="nome" type="text" requiredField="true" />
				<tags:inputField label="Documento de identifica��o" name="documento" type="text" requiredField="true" />
				<tags:inputField label="�rg�o Emissor" name="orgaoEmissor" type="text" requiredField="true" />
				<tags:inputField label="Profiss�o" name="profissao" type="text" requiredField="false" />
				<tags:selectField label="UF de Emiss�o" name="uf" collection="${ufs}" itemLabel="name" itemValue="name" searchItems="false" requiredField="true" />
			</div>
			<tags:defaultButtons backUrl="visitante/registros" />
		</form:form>
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>