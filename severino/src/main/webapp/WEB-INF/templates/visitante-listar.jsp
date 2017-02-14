<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Visitantes" menuSelecionado="visitante/registros">
	<jsp:body>
		<tags:displayTableData urlEdit="visitante/" urlAdd="visitante/" fieldList="documento,orgaoEmissor,uf,nome,profissao,telefone" urlRemove="visitante/delete/" urlJsonList="visitante/listar" labelList="Documento de Identificação,Órgão Emissor,UF,Nome,Profissão,Telefone" />
	</jsp:body>
</tags:base-template>