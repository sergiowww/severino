<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<script type="text/javascript" src="js/RadioButtonToggleArea.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/helpers/acessoGaragem.js" charset="UTF-8"></script>
<link rel="stylesheet" type="text/css" href="css/helpers/acessoGaragem.css" />
<title>Acesso a Garagem</title>
</head>
<body>
	<tags:menu selectedItem="acessoGaragem/" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<div class="col-md-9">
			<jsp:include page="acessoGaragem-form.jsp"></jsp:include>
		</div>
		<div class="col-md-3">
			<jsp:include page="acessoGaragem-sem-baixa.jsp"></jsp:include>
		</div>
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>