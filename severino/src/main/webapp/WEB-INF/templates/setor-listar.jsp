<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Setores Cadastrados" menuSelecionado="setor/registros">
	<jsp:body>
		<tags:displayTableData urlEdit="setor/" urlAdd="setor/" fieldList="id,andar,nome,sala,local.titulo" urlRemove="setor/delete/" urlJsonList="setor/listar" labelList="Código,Andar,Setor,Sala,Local" />
	</jsp:body>
</tags:base-template>