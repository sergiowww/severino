<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Fluxo de Técnicos de Transporte" menuSelecionado="viagem/">
	<jsp:attribute name="head">
		<script type="text/javascript" src="js/helpers/viagem.js" charset="ISO-8859-1"></script>
		<script type="text/javascript" src="js/RadioButtonToggleArea.js" charset="ISO-8859-1"></script>
		<script type="text/javascript" src="resources/jquery.tmpl.min.js"></script>
		<script type="text/x-jquery-tmpl" id="templatePassageiro">
			<jsp:include page="viagem-template-passageiro.jsp" />
		</script>

		<link rel="stylesheet" type="text/css" href="css/helpers/viagem.css" />
	</jsp:attribute>
	<jsp:body>
		<div class="col-md-9" style="margin-bottom: 51px;">
			<jsp:include page="viagem-form.jsp"></jsp:include>
		</div>
		<div class="col-md-3">
			<jsp:include page="viagem-sem-baixa.jsp"></jsp:include>
			<jsp:include page="viagem-motoristas.jsp"></jsp:include>
		</div>
	</jsp:body>
</tags:base-template>