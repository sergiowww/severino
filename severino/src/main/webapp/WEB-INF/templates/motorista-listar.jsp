<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<title>Condutores e usuários da garagem</title>
</head>
<body>
	<tags:menu selectedItem="motorista/registros" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<tags:displayTableData urlEdit="motorista/" urlAdd="motorista/" fieldList="matricula,nome,cargo" urlRemove="motorista/delete/" urlJsonList="motorista/listar" labelList="Matrícula,Nome,Cargo" />
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>