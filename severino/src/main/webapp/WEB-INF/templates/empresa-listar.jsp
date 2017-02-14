<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Empresas Cadastradas" menuSelecionado="empresa/registros">
	<jsp:body>
		<tags:displayTableData urlEdit="empresa/" urlAdd="empresa/" fieldList="id,nome" urlRemove="empresa/delete/" urlJsonList="empresa/listar" labelList="Código,Nome" />
	</jsp:body>
</tags:base-template>