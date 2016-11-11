<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"></jsp:include>
<script type="text/javascript" src="js/helpers/visita.js" charset="UTF-8"></script>
<title>Registro de visitantes</title>
</head>
<body>
	<tags:menu selectedItem="visita/" />
	<div class="container">
		<jsp:include page="includes/message-panel.jsp"></jsp:include>
		<div class="col-md-8">
			<jsp:include page="visita-form.jsp" />
		</div>
		<div class="col-md-4">
			<jsp:include page="visita-sem-baixa.jsp"></jsp:include>
		</div>
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
</body>
</html>