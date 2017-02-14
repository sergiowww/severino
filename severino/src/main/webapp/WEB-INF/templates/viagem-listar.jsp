<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Viagens" menuSelecionado="viagem/registros">
	<jsp:body>
		<tags:displayTableData urlEdit="viagem/" urlAdd="viagem/" fieldList="controleSaida.dataHora:date,controleRetorno.dataHora:date,motorista.nome,viatura.modelo,usuario.id" urlRemove="viagem/delete/" urlJsonList="viagem/listar" labelList="Saída,Retorno,Técnico,Veículo,Usuário" />
	</jsp:body>
</tags:base-template>