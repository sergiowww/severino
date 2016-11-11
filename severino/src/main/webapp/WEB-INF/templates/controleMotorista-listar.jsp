<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<title>Controle de ponto dos t�cnicos de transporte</title>
</head>
<body>
	<tags:menu selectedItem="controleMotorista/registros" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<tags:displayTableData urlEdit="controleMotorista/" urlAdd="controleMotorista/" fieldList="id,dataHora:date,fluxo,motorista.nome" urlRemove="controleMotorista/delete/" urlJsonList="controleMotorista/listar" labelList="C�digo,Data e Hora,Fluxo,T�cnico" />
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>