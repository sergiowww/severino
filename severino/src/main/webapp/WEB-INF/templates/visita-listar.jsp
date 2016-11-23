<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<title>Visitas</title>
</head>
<body>
	<tags:menu selectedItem="visita/registros" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<tags:displayTableData 
			urlEdit="visita/" 
			urlAdd="visita/" 
			fieldList="id,entrada:date,saida:date,nomeProcurado,empresa.nome,setor.nome,usuario.id,visitante.nome" 
			urlJsonList="visita/listar" 
			labelList="Código,Entrada,Saída,Responsável,Empresa,Setor,Usuário cadastro,Nome visitante" />
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>