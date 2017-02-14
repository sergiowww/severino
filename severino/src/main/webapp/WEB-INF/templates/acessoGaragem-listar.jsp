<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Registro de acesso a garagem" menuSelecionado="acessoGaragem/registros">
	<jsp:body>
		<tags:displayTableData urlEdit="acessoGaragem/" urlAdd="acessoGaragem/" fieldList="id,motorista.nome,entrada:date,saida:date" urlRemove="acessoGaragem/delete/" urlJsonList="acessoGaragem/listar" labelList="Código,Servidor / Membro / Visitante,Entrada,Saída" />
	</jsp:body>
</tags:base-template>