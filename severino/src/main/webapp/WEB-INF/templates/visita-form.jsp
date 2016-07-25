<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<div class="col-md-8">

	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">
				<img alt="Carregando..." src="img/ajax-loader.gif" style="visibility: hidden;" id="wait" /> Visita -
				<c:if test="${not empty visita.id}">
					<fmt:formatDate value="${visita.dataHoraCadastro}" dateStyle="medium" timeStyle="medium" type="both" /> -
					${visita.visitante.nome}
				</c:if>
				<c:if test="${empty visita.id}">
					<i>novo registro</i>
				</c:if>
			</h3>
		</div>
		<div class="panel-body">
			<form:form cssClass="form-horizontal" commandName="visita" autocomplete="off" servletRelativeAction="/visita">
				<form:errors path="validacao*" cssClass="alert alert-danger fade in" element="div"></form:errors>
				<form:hidden path="id" id="id" />
				<form:hidden path="dataHoraCadastro" id="dataHoraCadastro" />
				<div class="row">
					<c:if test="${empty visita.id}">
						<tags:inputField name="entrada" label="Entrada" type="date" mask="00/00/0000 00:00" extraCssClass="col-xs-6" tip="Preencha a data de entrada, ao deixá-la vazia será preenchido com a data/hora atual" requiredField="true" />
					</c:if>
					<c:if test="${not empty visita.id}">
						<tags:inputField name="entrada" label="Entrada" type="date" mask="00/00/0000 00:00" extraCssClass="col-xs-3" requiredField="true" />
						<c:if test="${empty visita.saida}">
							<c:set var="faltaBaixaError" value="has-error" />
						</c:if>
						<tags:inputField name="saida" label="Saída" type="date" mask="00/00/0000 00:00" extraCssClass="col-xs-3 ${faltaBaixaError}" />
					</c:if>
					<tags:inputField name="empresa.nome" label="Empresa ou órgão" type="text" extraCssClass="col-xs-3" />
					<tags:selectField name="setor.id" label="Local" collection="${setores}" itemLabel="descricaoCompleta" itemValue="id" searchItems="true" extraCssClass="col-xs-3" requiredField="true" />
				</div>
				<div class="row">
					<tags:inputField name="nomeProcurado" label="Servidor responsável" type="text" tip="Nome do servidor responsável ou procurado pelo visitante" extraCssClass="col-xs-6" />
					<tags:inputField name="setorProcurado" label="Setor" type="text" tip="Departamento ou divisão" extraCssClass="col-xs-6" />
				</div>
				<form:hidden path="empresa.id" id="empresa.id" />

				<fieldset style="margin-bottom: 10px; margin-top: 5px;">
					<legend>Dados do visitante</legend>
					<form:hidden path="visitante.nome" id="visitante.nome_hidden" />
					<form:hidden path="visitante.uf" id="visitante.uf_hidden" />
					<form:hidden path="visitante.orgaoEmissor" id="visitante.orgaoEmissor_hidden" />
					<div class="row">
						<tags:inputField label="Documento" name="visitante.documento" type="text" extraCssClass="col-xs-6" requiredField="true" />
						<tags:inputField label="Nome" name="visitante.nome" type="text" extraCssClass="col-xs-6" requiredField="true" />
					</div>
					<div class="row">
						<tags:selectField label="UF de Emissão" name="visitante.uf" collection="${ufs}" itemLabel="name" itemValue="name" searchItems="false" extraCssClass="col-xs-6" requiredField="true" />
						<tags:inputField label="Órgão Emissor" name="visitante.orgaoEmissor" type="text" extraCssClass="col-xs-6" requiredField="true" />
					</div>
				</fieldset>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-primary active" title="Persistir dados no sistema">
							<span class="glyphicon glyphicon-floppy-save"></span>
							Salvar
						</button>
						<c:if test="${empty visita.saida and not empty visita.id}">
							<button type="submit" class="btn btn-default" title="Registrar saída do visitante com a data e hora atual" name="registrarSaida" value="true">
								<span class="glyphicon glyphicon-ok"></span>
								Baixar
							</button>
						</c:if>
						<c:if test="${not empty visita.id}">
							<a href="javascript:CesDataTableUtils.confirmBeforeDelete('visita/delete/${visita.id}')" class="btn btn-default" title="Eliminar registro do sistema">
								<span class="glyphicon glyphicon-trash"></span>
								Apagar
							</a>
						</c:if>
						<a class="btn btn-default" href="visita/registros" title="Voltar para a tela de listagem de registros">
							<span class="glyphicon glyphicon-circle-arrow-left"></span>
							Voltar
						</a>

						<c:if test="${not empty visita.id}">
							<a class="btn btn-default" href="visita/" title="Criar um novo registro">
								<span class="glyphicon glyphicon-file"></span>
								Novo
							</a>
						</c:if>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>