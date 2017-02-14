<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:base-template title="Acesso Negado">
	<jsp:body>
		<div class="centering text-center error-container">
			<div class="text-center">
				<h4 class="text-warning">403</h4>
			</div>
			<div class="text-center">
				<div class="alert alert-warning">
					<strong>Aviso:</strong>
					No momento, seu usuário não está autorizado a promover qualquer alteração nos registros do sistema, caso deseje, peça a inclusão do seu usuário no grupo ${roleNameSeverino}
				</div>
			</div>
			<hr>

		</div>
	</jsp:body>
</tags:base-template>