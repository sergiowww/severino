<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Empresa" menuSelecionado="empresa/registros">
	<jsp:body>
		<form:form servletRelativeAction="/empresa" cssClass="form-horizontal" modelAttribute="empresa">
			<form:hidden path="id" />
			<tags:inputField label="Nome" name="nome" type="text" extraCssClass="form-group" requiredField="true" />
			<tags:defaultButtons backUrl="empresa/registros" />
		</form:form>
	</jsp:body>
</tags:base-template>