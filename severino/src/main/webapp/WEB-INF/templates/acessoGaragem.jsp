<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Acesso a Garagem" menuSelecionado="acessoGaragem/">
	<jsp:attribute name="head">
		<script type="text/javascript" src="js/RadioButtonToggleArea.js" charset="UTF-8"></script>
		<script type="text/javascript" src="js/helpers/acessoGaragem.js" charset="UTF-8"></script>
		<link rel="stylesheet" type="text/css" href="css/helpers/acessoGaragem.css" />
	</jsp:attribute>
	<jsp:body>
		<div class="col-md-9">
			<jsp:include page="acessoGaragem-form.jsp"></jsp:include>
		</div>
		<div class="col-md-3">
			<jsp:include page="acessoGaragem-sem-baixa.jsp"></jsp:include>
		</div>
	</jsp:body>
</tags:base-template>