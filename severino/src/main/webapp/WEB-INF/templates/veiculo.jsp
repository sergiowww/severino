<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Veículo" menuSelecionado="veiculo/registros">
	<jsp:body>
		<form:form servletRelativeAction="/veiculo" cssClass="form-horizontal" modelAttribute="veiculo">
			<div class="form-group">
				<tags:inputField label="Placa" name="id" type="text" extraCssClass="col-xs-6" requiredField="true" onkeyup="this.value = this.value.toLocaleUpperCase()" />
				<tags:inputField label="Marca" name="marca" type="text" extraCssClass="col-xs-6" requiredField="true" />
				<tags:inputField label="Modelo" name="modelo" type="text" extraCssClass="col-xs-3" requiredField="true" />
				<tags:inputField label="Cor" name="cor" type="text" extraCssClass="col-xs-3" requiredField="true" />
				<tags:selectField name="motorista.id" label="Proprietário" collection="${motoristas}" itemLabel="nome" itemValue="id" searchItems="false" extraCssClass="col-xs-6" />
			</div>
			<tags:checkbox label="Viatura do MP" name="viaturaMp" tip="Indica se este veículo é uma viatura do MP" />
			<tags:defaultButtons backUrl="veiculo/registros" />
		</form:form>
	</jsp:body>
</tags:base-template>