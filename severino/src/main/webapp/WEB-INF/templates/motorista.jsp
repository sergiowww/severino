<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Condutor" menuSelecionado="motorista/registros">
	<jsp:attribute name="head">
		<script type="text/javascript" src="js/helpers/motorista.js" charset="ISO-8859-1"></script>
	</jsp:attribute>
	<jsp:body>
		<form:form servletRelativeAction="/motorista" cssClass="form-horizontal" modelAttribute="motorista">
			<form:hidden path="id" />
			<div class="form-group">
				<div class="row">
					<tags:inputField label="Nome (digite parte do nome e selecione)" name="nome" type="text" requiredField="true" extraCssClass="col-md-4"
							tip="Digite parte do nome e pressione a tecla Tab para selecionar" />
					<tags:inputField label="Matricula" name="matricula" type="text" requiredField="true" extraCssClass="col-md-2" />
					<tags:selectField label="Cargo" name="cargo" collection="${cargos}" itemLabel="descricao" itemValue="name" searchItems="false" requiredField="true" extraCssClass="col-md-2" />
					<tags:selectField label="Local" name="local.id" collection="${locais}" itemLabel="titulo" itemValue="id" searchItems="false" requiredField="true" extraCssClass="col-md-2" />
					<tags:checkbox label="Ativo" name="ativo" extraCssClass="col-md-2" />
				</div>
				<div class="row">
					
				</div>
			</div>
			<tags:defaultButtons backUrl="motorista/registros" />
		</form:form>
	</jsp:body>
</tags:base-template>