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
		<form:form servletRelativeAction="/visitante" cssClass="form-horizontal" commandName="visitante">
			<form:hidden path="id" />
			<div class="form-group">
				<tags:inputField label="Nome" name="nome" type="text" />
				<tags:inputField label="Documento de identificação" name="documento" type="text" />
				<tags:inputField label="Órgão Emissor" name="orgaoEmissor" type="text" />
				<tags:selectField label="UF de Emissão" name="uf" collection="${ufs}" itemLabel="name" itemValue="name" searchItems="true"></tags:selectField>
			</div>
			<tags:defaultButtons backUrl="visitante/registros" />
		</form:form>
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>