<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>


<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
			Viagem (quando o motorista sai em serviço com a viatura)
		</h3>
		<div class="label label-default" style="float: right;">
			<c:if test="${empty viagem.id}">
				novo registro
			</c:if>
			<c:if test="${not empty viagem.id}">
				cadastrado em
				<fmt:formatDate value="${viagem.dataHoraCadastro}" dateStyle="medium" timeStyle="short" type="both" />
				por ${viagem.usuario.id}
			</c:if>
		</div>
	</div>
	<div class="panel-body">
		<form:form servletRelativeAction="/viagem" cssClass="form-horizontal" modelAttribute="viagem">
			<form:hidden path="id" id="id" />
			<form:hidden path="usuario.id" />
			<form:hidden path="dataHoraCadastro" id="dataHoraCadastro" />
			<form:errors path="validacao*" cssClass="alert alert-danger fade in" element="div"></form:errors>
			<div class="row">
				<fieldset class="col-md-5">
					<legend>Informações da viagem</legend>
					<tags:selectField name="motorista.id" label="Técnico" collection="${motoristas}" itemLabel="nome" itemValue="id" searchItems="false" requiredField="true" />
					<div class="row">
						<c:if test="${empty viagem.id}">
							<tags:inputField name="saida" label="Saída" type="date" mask="00/00/0000 00:00" extraCssClass="col-md-12" tip="Preencha a data de saída, ao deixá-la vazia será preenchido com a data/hora atual" requiredField="true" />
						</c:if>
						<c:if test="${not empty viagem.id}">
							<tags:inputField name="saida" label="Saída" type="date" mask="00/00/0000 00:00" extraCssClass="col-md-6" requiredField="true" />
							<tags:inputField name="retorno" label="Retorno" type="date" mask="00/00/0000 00:00" extraCssClass="col-md-6${empty viagem.retorno ? ' has-error': ''}" />
						</c:if>
					</div>
					<tags:textarea name="anotacao" label="Anotações" rows="2" cols="3" />
				</fieldset>
				<fieldset class="col-md-7">
					<legend>Passageiros</legend>
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="input-group">
								<input type="text" placeholder="Buscar membro ou servidor por nome ... " title="digite parte do nome e pressione enter quando aparecer o resultado" id="buscarServidorText" class="form-control" />
								<span class="input-group-btn">
									<button class="btn btn-default" onclick="viagem.adicionarPassageiroAvulso()" type="button">
										<span class="glyphicon glyphicon-plus-sign"></span>
									</button>
								</span>
							</div>
						</div>
						<div class="panel-body">
							<div class="table-responsive bodycontainer scrollable" style="height: 114px; margin-top: 5px">
								<c:forEach items="${viagem.passageiros}" var="passageiro">
									<script type="text/javascript">
										$(document).ready(viagem.adicionarPassageiro.bind(viagem, ${passageiro.json}));
									</script>
								</c:forEach>
								<table class="table table-scrollable" id="passageirosTable">
									<tbody>
										<tr id="mensagemSemRegistros">
											<td colspan="3" class="text-center text-info">Nenhum passageiro adicionado</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>

				</fieldset>
			</div>
			<fieldset>
				<legend>Viatura</legend>
				<div class="row equal">
					<div class="col-md-5">
						<div class="panel panel-default" style="width: 100%;">
							<div class="panel-heading radioButtonSmall">
								<form:radiobutton path="gravarVeiculo" value="false" label="Escolher uma viatura cadastrada" />
							</div>
							<div class="panel-body" id="gravarVeiculofalse">
								<tags:selectField name="viatura.id" label="Viaturas" collection="${viaturas}" itemLabel="descricaoCompleta" itemValue="id" searchItems="false" />
							</div>
						</div>
					</div>
					<div class="col-md-7">
						<div class="panel panel-default">
							<div class="panel-heading radioButtonSmall">
								<form:radiobutton path="gravarVeiculo" value="true" label="Cadastrar uma nova viatura" />
							</div>
							<div class="panel-body" id="gravarVeiculotrue">
								<div class="row">
									<tags:inputField name="viatura.id" label="Placa" type="text" extraCssClass="col-md-6" onkeyup="this.value = this.value.toLocaleUpperCase()" />
									<tags:inputField name="viatura.marca" label="Marca" type="text" extraCssClass="col-md-6" />
								</div>
								<div class="row">
									<tags:inputField name="viatura.modelo" label="Modelo" type="text" extraCssClass="col-md-6" />
									<tags:inputField name="viatura.cor" label="Cor" type="text" extraCssClass="col-md-6" />
								</div>
							</div>
						</div>
					</div>
					<script type="text/javascript">new RadioButtonToggleArea("gravarVeiculo");</script>
				</div>
			</fieldset>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary active" title="Persistir dados no sistema">
						<span class="glyphicon glyphicon-floppy-save"></span>
						Salvar
					</button>
					<c:if test="${empty viagem.retorno and not empty viagem.id}">
						<button type="submit" class="btn btn-default" title="Registrar o retorno do motorista e da viatura com a data e hora atual" name="registrarSaida" value="true">
							<span class="glyphicon glyphicon-ok"></span>
							Dar baixa
						</button>
					</c:if>
					<c:if test="${not empty viagem.id}">
						<a href="javascript:CesDataTableUtils.confirmBeforeDelete('viagem/delete/${viagem.id}')" class="btn btn-default" title="Eliminar registro do sistema">
							<span class="glyphicon glyphicon-trash"></span>
							Apagar
						</a>
					</c:if>
					<a class="btn btn-default" href="viagem/registros" title="Voltar para a tela de listagem de registros">
						<span class="glyphicon glyphicon-circle-arrow-left"></span>
						Voltar
					</a>

					<c:if test="${not empty viagem.id}">
						<a class="btn btn-default" href="viagem/" title="Criar um novo registro">
							<span class="glyphicon glyphicon-file"></span>
							Novo
						</a>
					</c:if>
				</div>
			</div>
		</form:form>
	</div>
</div>
