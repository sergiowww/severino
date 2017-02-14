<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Registro de Visitante" menuSelecionado="visita/">
	<jsp:attribute name="head">
		<script type="text/javascript" src="js/helpers/visita.js" charset="UTF-8"></script>
	</jsp:attribute>
	<jsp:body>
		<div class="col-md-8">
			<jsp:include page="visita-form.jsp" />
		</div>
		<div class="col-md-4">
			<jsp:include page="visita-sem-baixa.jsp"></jsp:include>
		</div>
	</jsp:body>
</tags:base-template>