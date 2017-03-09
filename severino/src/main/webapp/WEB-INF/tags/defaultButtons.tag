<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="backUrl" description="Url para voltar a página de listagem" required="true" type="java.lang.String"%>

<div class="col-sm-offset-2 col-sm-10">
	<div class="btn-group" role="group">
		<button type="submit" class="btn btn-primary active" title="Persistir dados no sistema">
			<span class="glyphicon glyphicon-floppy-save"></span>
			Salvar
		</button>
		<a class="btn btn-default" href="${backUrl}" title="Voltar para a tela de listagem de registros">
			<span class="glyphicon glyphicon-circle-arrow-left"></span>
			Voltar
		</a>
	</div>
</div>
