<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<title>Visitantes</title>
</head>
<body>
	<tags:menu selectedItem="visitante/registros" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<tags:displayTableData urlEdit="visitante/" urlAdd="visitante/" fieldList="documento,orgaoEmissor,uf,nome" urlRemove="visitante/delete/" urlJsonList="visitante/listar" labelList="Documento de Identificação,Órgão Emissor,UF,Nome" />
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>