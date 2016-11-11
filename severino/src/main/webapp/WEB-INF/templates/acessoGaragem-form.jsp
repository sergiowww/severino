<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
			<img alt="Carregando..." src="img/ajax-loader.gif" style="visibility: hidden;" id="wait" /> Acesso
			<c:if test="${not empty acessoGaragem.id}">
				- <c:out value="${acessoGaragem.visita.visitante.nome}" default="${acessoGaragem.motorista.nome}" />
			</c:if>
		</h3>
		<div class="label label-default" style="float: right;">
			<c:if test="${empty acessoGaragem.id}">
					novo registro
				</c:if>
			<c:if test="${not empty acessoGaragem.id}">
					cadastrado em
					<fmt:formatDate value="${acessoGaragem.dataHoraCadastro}" dateStyle="medium" timeStyle="short" type="both" />
					por ${acessoGaragem.usuario.id}
				</c:if>
		</div>
	</div>
	<div class="panel-body">
		<form:form servletRelativeAction="/acessoGaragem" cssClass="form-horizontal" modelAttribute="acessoGaragem">
			<form:errors path="validacao*" cssClass="alert alert-danger fade in" element="div"></form:errors>
			<form:hidden path="id" />
			<form:hidden path="dataHoraCadastro" id="dataHoraCadastro" />
			<form:hidden path="usuario.id" />

			<fieldset>
				<legend>Condutor</legend>
				<div class="col-md-5">
					<div class="panel panel-default">
						<div class="panel-heading radioButtonSmall">
							<form:radiobutton path="usuarioVisitante" value="false" label="Servidor ou membro" />
						</div>
						<div class="panel-body" id="usuarioVisitantefalse">
							<tags:selectField name="veiculo.id" collection="${servidoresMembros}" itemLabel="descricaoMotorista" itemValue="id" searchItems="true" />
						</div>
					</div>
				</div>
				<div class="col-md-7">
					<div class="panel panel-default">
						<div class="panel-heading radioButtonSmall">
							<form:radiobutton path="usuarioVisitante" value="true" label="Visitantes de hoje" />
						</div>
						<div class="panel-body" id="usuarioVisitantetrue">
							<tags:selectField name="visita.id" collection="${visitasHoje}" itemLabel="visitante.dadosResumo" itemValue="id" searchItems="true" />
						</div>
					</div>
				</div>
				<script type="text/javascript">
					new RadioButtonToggleArea("usuarioVisitante");
				</script>
			</fieldset>
			<div class="row" style="margin-bottom: 10px;">
				<fieldset class="col-md-5">
					<legend>Informações</legend>
					<div class="row">
						<c:if test="${empty acessoGaragem.id}">
							<tags:inputField name="entrada" label="Entrada" type="date" mask="00/00/0000 00:00" extraCssClass="col-md-12" tip="Preencha a data de entrada, ao deixá-la vazia será preenchido com a data/hora atual" requiredField="true" />
						</c:if>
						<c:if test="${not empty acessoGaragem.id}">
							<tags:inputField name="entrada" label="Entrada" type="date" mask="00/00/0000 00:00" extraCssClass="col-md-6" requiredField="true" />
							<tags:inputField name="saida" label="Saída" type="date" mask="00/00/0000 00:00" extraCssClass="col-md-6${empty acessoGaragem.saida ? ' has-error': ''}" />
						</c:if>
					</div>
					<tags:textarea name="anotacao" label="Anotações" rows="1" cols="3" />
				</fieldset>
				<fieldset class="col-md-7" id="fieldsetVeiculo">
					<legend>Veículo</legend>
					<div class="row">
						<tags:inputField name="veiculo.id" label="Placa" type="text" extraCssClass="col-md-6" onkeyup="this.value = this.value.toLocaleUpperCase()" />
						<tags:inputField name="veiculo.marca" label="Marca" type="text" extraCssClass="col-md-6" />
					</div>
					<div class="row">
						<tags:inputField name="veiculo.modelo" label="Modelo" type="text" extraCssClass="col-md-6" />
						<tags:inputField name="veiculo.cor" label="Cor" type="text" extraCssClass="col-md-6" />
					</div>
				</fieldset>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary active" title="Persistir dados no sistema">
						<span class="glyphicon glyphicon-floppy-save"></span>
						Salvar
					</button>
					<c:if test="${empty acessoGaragem.saida and not empty acessoGaragem.id}">
						<button type="submit" class="btn btn-default" title="Registrar saída do usuário com a data e hora atual" name="registrarSaida" value="true">
							<span class="glyphicon glyphicon-ok"></span>
							Dar baixa
						</button>
					</c:if>
					<c:if test="${not empty acessoGaragem.id}">
						<a href="javascript:CesDataTableUtils.confirmBeforeDelete('acessoGaragem/delete/${acessoGaragem.id}')" class="btn btn-default" title="Eliminar registro do sistema">
							<span class="glyphicon glyphicon-trash"></span>
							Apagar
						</a>
					</c:if>
					<a class="btn btn-default" href="acessoGaragem/registros" title="Voltar para a tela de listagem de registros">
						<span class="glyphicon glyphicon-circle-arrow-left"></span>
						Voltar
					</a>

					<c:if test="${not empty acessoGaragem.id}">
						<a class="btn btn-default" href="acessoGaragem/" title="Criar um novo registro">
							<span class="glyphicon glyphicon-file"></span>
							Novo
						</a>
					</c:if>
				</div>
			</div>
		</form:form>
	</div>
</div>