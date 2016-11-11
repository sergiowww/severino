<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<script type="text/javascript" src="js/helpers/viagem.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/RadioButtonToggleArea.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/jquery.tmpl.min.js"></script>
<script type="text/x-jquery-tmpl" id="templatePassageiro">
	<jsp:include page="viagem-template-passageiro.jsp" />
</script>

<link rel="stylesheet" type="text/css" href="resources/bootstrap-toggle.min.css" />
<link rel="stylesheet" type="text/css" href="css/helpers/viagem.css" />
<title>Fluxo de Técnicos de Transporte</title>
</head>
<body>
	<tags:menu selectedItem="viagem/" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<div class="col-md-9" style="margin-bottom: 51px;">
			<jsp:include page="viagem-form.jsp"></jsp:include>
		</div>
		<div class="col-md-3">
			<jsp:include page="viagem-sem-baixa.jsp"></jsp:include>
			<jsp:include page="viagem-motoristas.jsp"></jsp:include>
		</div>
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>