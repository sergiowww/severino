<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<title>Veículos Cadastrados</title>
</head>
<body>
	<tags:menu selectedItem="veiculo/registros" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<tags:displayTableData urlEdit="veiculo/" urlAdd="veiculo/" fieldList="id,cor,marca,modelo,viaturaMp:sim_nao,motorista.nome" urlRemove="veiculo/delete/" urlJsonList="veiculo/listar" labelList="Placa,Cor,Marca,Modelo,Viatura MP,Proprietário" />
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>