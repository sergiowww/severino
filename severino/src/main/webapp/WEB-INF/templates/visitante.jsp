<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Visitante" menuSelecionado="visitante/registros">
	<jsp:body>
		<form:form servletRelativeAction="/visitante" cssClass="form-horizontal" modelAttribute="visitante">
			<form:hidden path="id" />
			<div class="form-group">
				<jsp:include page="visitante-dados.jsp"></jsp:include>
				<fieldset>
					<legend>Endereço</legend>
					<jsp:include page="visitante-endereco.jsp"></jsp:include>
				</fieldset>
			</div>
			<tags:defaultButtons backUrl="visitante/registros" />
		</form:form>
	</jsp:body>
</tags:base-template>