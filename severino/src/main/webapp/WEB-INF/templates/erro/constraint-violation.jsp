<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:base-template title="Erro de v�nculo ao excluir">
	<jsp:body>
		<div class="centering text-center error-container">
			<div class="text-center">
				<h4 class="text-warning">Registro relacionado em outros locais</h4>
			</div>
			<div class="text-center">
				<div class="alert alert-danger">
					<strong>Erro:</strong>
					N�o foi poss�vel eliminar este registro, h� refer�ncias a ele em outros locais do sistema
				</div>
			</div>
			<hr>
		</div>
	</jsp:body>
</tags:base-template>