<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<title>Viagens</title>
</head>
<body>
	<tags:menu selectedItem="viagem/registros" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<tags:displayTableData urlEdit="viagem/" urlAdd="viagem/" fieldList="controleSaida.dataHora:date,controleRetorno.dataHora:date,motorista.nome,viatura.modelo,usuario.id" urlRemove="viagem/delete/" urlJsonList="viagem/listar" labelList="Saída,Retorno,Técnico,Veículo,Usuário" />
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>