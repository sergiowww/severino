<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Controle de ponto dos técnicos de transporte" menuSelecionado="controleMotorista/registros">
	<jsp:body>
		<tags:displayTableData urlEdit="controleMotorista/" urlAdd="controleMotorista/" fieldList="id,dataHora:date,fluxo,motorista.nome" urlRemove="controleMotorista/delete/" urlJsonList="controleMotorista/listar" labelList="Código,Data e Hora,Fluxo,Técnico" />
	</jsp:body>
</tags:base-template>