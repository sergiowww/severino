<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<title>Registro de acesso a garagem</title>
</head>
<body>
	<tags:menu selectedItem="acessoGaragem/registros" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<tags:displayTableData urlEdit="acessoGaragem/" urlAdd="acessoGaragem/" fieldList="id,motorista.nome,entrada:date,saida:date" urlRemove="acessoGaragem/delete/" urlJsonList="acessoGaragem/listar" labelList="Código,Servidor / Membro / Visitante,Entrada,Saída" />
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>