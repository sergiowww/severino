<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Condutor" menuSelecionado="motorista/registros">
	<jsp:attribute name="head">
		<script type="text/javascript" src="js/helpers/motorista.js" charset="UTF-8"></script>
	</jsp:attribute>
	<jsp:body>
		<form:form servletRelativeAction="/motorista" cssClass="form-horizontal" modelAttribute="motorista">
			<form:hidden path="id" />
			<div class="form-group">
				<tags:inputField label="Nome" name="nome" type="text" requiredField="true" extraCssClass="col-md-6" />
				<tags:inputField label="Matricula" name="matricula" type="text" requiredField="true" extraCssClass="col-md-3" />
				<tags:selectField label="Cargo" name="cargo" collection="${cargos}" itemLabel="descricao" itemValue="name" searchItems="false" requiredField="true" extraCssClass="col-md-3" />
			</div>
			<tags:defaultButtons backUrl="motorista/registros" />
		</form:form>
	</jsp:body>
</tags:base-template>