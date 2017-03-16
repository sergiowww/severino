<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Visitante" menuSelecionado="visitante/registros">
	<jsp:attribute name="head">
		<script type="text/javascript" src="js/helpers/camera.js" charset="ISO-8859-1"></script>
		<script type="text/javascript" src="resources/jpeg_camera/jpeg_camera_with_dependencies.min.js"></script>
		<link rel="stylesheet" type="text/css" href="css/helpers/camera.css" />
	</jsp:attribute>
	<jsp:body>
		<form:form servletRelativeAction="/visitante" cssClass="form-horizontal" modelAttribute="visitante">
			<form:hidden path="id" />
			<div class="form-group">
				<div class="row">
					<div class="col-md-10">
						<jsp:include page="visitante-dados.jsp"></jsp:include>
					</div>
					<div class="col-md-2">
						<jsp:include page="camera.jsp" />
					</div>
				</div>
				<fieldset>
					<legend>Endereço</legend>
					<jsp:include page="visitante-endereco.jsp"></jsp:include>
				</fieldset>
			</div>
			<tags:defaultButtons backUrl="visitante/registros" />
		</form:form>
	</jsp:body>
</tags:base-template>