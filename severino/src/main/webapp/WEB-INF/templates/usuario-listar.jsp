<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Usuários do Sistema" menuSelecionado="usuario/registros">
	<jsp:body>
		<tags:displayTableData urlEdit="usuario/" fieldList="id,nome" urlJsonList="usuario/listar" labelList="Login de rede,Nome" />
	</jsp:body>
</tags:base-template>