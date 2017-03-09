<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
			Visita
			<c:if test="${not empty visita.id}">
					- ${visita.visitante.nome}
				</c:if>
		</h3>
		<div class="label label-default" style="float: right;">
			<c:if test="${empty visita.id}">
					novo registro
				</c:if>
			<c:if test="${not empty visita.id}">
					cadastrado em
					<fmt:formatDate value="${visita.dataHoraCadastro}" dateStyle="medium" timeStyle="short" type="both" />
					por ${visita.usuario.id}
				</c:if>
		</div>
	</div>
	<div class="panel-body">
		<form:form cssClass="form-horizontal" modelAttribute="visita" autocomplete="off" servletRelativeAction="/visita">
			<form:errors path="validacao*" cssClass="alert alert-danger fade in" element="div"></form:errors>
			<form:hidden path="id" id="id" />
			<form:hidden path="dataHoraCadastro" id="dataHoraCadastro" />
			<form:hidden path="usuario.id" />
			<div class="row">
				<div class="col-md-5 nestedColumn">
					<c:if test="${empty visita.id}">
						<tags:inputField name="entrada" label="Entrada" type="date" mask="00/00/0000 00:00" extraCssClass="col-md-12" tip="Preencha a data de entrada, ao deixá-la vazia será preenchido com a data/hora atual" requiredField="true" />
					</c:if>
					<c:if test="${not empty visita.id}">
						<tags:inputField name="entrada" label="Entrada" type="date" mask="00/00/0000 00:00" extraCssClass="col-md-6" requiredField="true" />
						<tags:inputField name="saida" label="Saída" type="date" mask="00/00/0000 00:00" extraCssClass="col-md-6${empty visita.saida ? ' has-error' : ''}" />
					</c:if>
				</div>
				<tags:inputField name="empresa.nome" label="Empresa" type="text" extraCssClass="col-md-3" tip="Empresa ou órgão" />
				<tags:selectField name="setor.id" label="Local" collection="${setores}" itemLabel="descricaoCompleta" itemValue="id" searchItems="true" extraCssClass="col-md-4" requiredField="true" />
			</div>
			<div class="row">
				<tags:inputField name="nomeProcurado" label="Servidor responsável" type="text" tip="Nome do servidor responsável ou procurado pelo visitante" extraCssClass="col-md-5" />
				<tags:inputField name="setorProcurado" label="Setor" type="text" tip="Departamento ou divisão" extraCssClass="col-md-7" />
			</div>
			<form:hidden path="empresa.id" id="empresa.id" />
			<ul class="nav nav-tabs" style="margin-top: 18px;">
				<li class="active">
					<a data-toggle="tab" href="#tabdados">Dados do visitante</a>
				</li>
				<li>
					<a data-toggle="tab" href="#tabendereco">
						Endereço
						<c:if test="${not empty visita.visitante.endereco}">
							<span class="badge" title="${visita.visitante.endereco}">1</span>
						</c:if>
					</a>
				</li>
			</ul>
			<div class="tab-content mesmaAltura" style="margin-bottom: 10px;">
				<div id="tabdados" class="tab-pane fade in active">
					<form:hidden path="visitante.nome" id="visitante.nome_hidden" />
					<form:hidden path="visitante.uf" id="visitante.uf_hidden" />
					<form:hidden path="visitante.orgaoEmissor" id="visitante.orgaoEmissor_hidden" />
					<jsp:include page="visitante-dados.jsp">
						<jsp:param value="visitante." name="prefixName" />
					</jsp:include>
				</div>
				<div id="tabendereco" class="tab-pane fade">
					<jsp:include page="visitante-endereco.jsp">
						<jsp:param value="visitante." name="prefixName" />
					</jsp:include>
				</div>
			</div>
			<div class="col-sm-offset-2 col-sm-10">
				<div class="btn-group" role="group">
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
						<a class="btn btn-default" href="visita/detalhe/${visita.id}" title="Voltar para a tela de detalhe">
							<span class="glyphicon glyphicon-circle-arrow-left"></span>
							Voltar
						</a>
					</c:if>
					<c:if test="${empty visita.id}">
						<a class="btn btn-default" href="visita/registros" title="Voltar para a tela de listagem de registros">
							<span class="glyphicon glyphicon-circle-arrow-left"></span>
							Voltar
						</a>
					</c:if>
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