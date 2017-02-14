<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Visitante" menuSelecionado="visitante/registros">
	<jsp:body>
		<form:form servletRelativeAction="/visitante" cssClass="form-horizontal" modelAttribute="visitante">
			<form:hidden path="id" />
			<div class="form-group">
				<tags:inputField label="Nome" name="nome" type="text" requiredField="true" />
				<tags:inputField label="Documento de identificação" name="documento" type="text" requiredField="true" />
				<tags:inputField label="Órgão Emissor" name="orgaoEmissor" type="text" requiredField="true" />
				<tags:inputField label="Profissão" name="profissao" type="text" requiredField="false" />
				<tags:inputField label="Telefone de contato" name="telefone" type="text" requiredField="false" mask="tel" />
				<tags:selectField label="UF de Emissão" name="uf" collection="${ufs}" itemLabel="name" itemValue="name" searchItems="false" requiredField="true" />
			</div>
			<tags:defaultButtons backUrl="visitante/registros" />
		</form:form>
	</jsp:body>
</tags:base-template>