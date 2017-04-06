<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:base-template title="Usuário - ${usuario.id}" menuSelecionado="usuario/registros">
	<jsp:body>
		<div class="form-group">
			<dl class="dl-horizontal">
				<dt>Login:</dt>
				<dd>${usuario.id}</dd>
				<dt>Nome:</dt>
				<dd>${usuario.nome}</dd>
				<dt>Local:</dt>
				<dd>${usuario.local.nome}</dd>
				<dt>Regional:</dt>
				<dd>${usuario.local.organizacao.nome}</dd>
			</dl>
		
			<div class="col-sm-offset-2 col-sm-10">
				<a class="btn btn-default" href="usuario/registros">
					<span class="glyphicon glyphicon-circle-arrow-left"></span>
					Voltar
				</a>
			</div>
		</div>
	</jsp:body>
</tags:base-template>