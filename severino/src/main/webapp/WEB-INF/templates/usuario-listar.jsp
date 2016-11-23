<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<title>Usuários do Sistema</title>
</head>
<body>
	<tags:menu selectedItem="usuario/registros" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<tags:displayTableData urlEdit="usuario/" fieldList="id,nome" urlJsonList="usuario/listar" labelList="Login de rede,Nome" />
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>