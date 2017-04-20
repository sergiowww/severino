<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Condutores e usuários da garagem" menuSelecionado="motorista/registros">
	<jsp:body>
		<tags:displayTableData urlEdit="motorista/" urlAdd="motorista/" fieldList="matricula,nome,cargoDescricao,local.titulo" urlRemove="motorista/delete/" urlJsonList="motorista/listar"
			labelList="Matrícula,Nome,Cargo,Local" />
	</jsp:body>
</tags:base-template>