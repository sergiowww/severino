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
					No momento, seu usu�rio n�o est� autorizado a promover qualquer altera��o nos registros do sistema, caso deseje, pe�a a inclus�o do seu usu�rio no grupo ${roleNameSeverino}
				</div>
			</div>
			<hr>

		</div>
	</jsp:body>
</tags:base-template>