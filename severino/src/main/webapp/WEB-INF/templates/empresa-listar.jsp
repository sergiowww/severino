<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<title>Empresas Cadastradas</title>
</head>
<body>
	<tags:menu selectedItem="empresa/registros" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<tags:displayTableData urlEdit="empresa/" urlAdd="empresa/" fieldList="id,nome" urlRemove="empresa/delete/" urlJsonList="empresa/listar" labelList="Código,Nome" />
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>