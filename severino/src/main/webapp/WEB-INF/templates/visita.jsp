<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Registro de Visitante" menuSelecionado="visita/">
	<jsp:attribute name="head">
		<script type="text/javascript" src="js/helpers/visita.js" charset="ISO-8859-1"></script>
		<script type="text/javascript" src="js/helpers/camera.js" charset="ISO-8859-1"></script>
		<script type="text/javascript" src="resources/jpeg_camera/jpeg_camera_with_dependencies.min.js"></script>
		<link rel="stylesheet" type="text/css" href="css/helpers/visita.css" />
		<link rel="stylesheet" type="text/css" href="css/helpers/camera.css" />
	</jsp:attribute>
	<jsp:body>
		<div class="col-md-9">
			<jsp:include page="visita-form.jsp" />
		</div>
		<div class="col-md-3">
			<jsp:include page="visita-sem-baixa.jsp">
				<jsp:param value="visita" name="baseUrl" />
			</jsp:include>
		</div>
	</jsp:body>
</tags:base-template>
