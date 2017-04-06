<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Veículos Cadastrados" menuSelecionado="veiculo/registros">
	<jsp:body>
		<tags:displayTableData urlEdit="veiculo/" urlAdd="veiculo/" fieldList="id,cor,marca,modelo,ativo:sim_nao,viaturaMp:sim_nao,motorista.nome" urlRemove="veiculo/delete/" urlJsonList="veiculo/listar"
			labelList="Placa,Cor,Marca,Modelo,Ativo,Viatura MP,Proprietário" />
	</jsp:body>
</tags:base-template>