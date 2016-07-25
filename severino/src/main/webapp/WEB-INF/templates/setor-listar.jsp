<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<title>Setores Cadastrados</title>
</head>
<body>
	<tags:menu selectedItem="setor/registros" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<tags:displayTableData urlEdit="setor/" urlAdd="setor/" fieldList="id,andar,nome,sala" urlRemove="setor/delete/" urlJsonList="setor/listar" labelList="Código,Andar,Setor,Sala" />
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>