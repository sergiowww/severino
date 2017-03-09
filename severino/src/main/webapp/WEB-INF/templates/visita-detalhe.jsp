<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Detalhes da Visita #${visita.id}" menuSelecionado="visita/registros">
	<jsp:attribute name="head">
        <link rel="stylesheet" type="text/css" href="css/helpers/visita.css" />
    </jsp:attribute>
	<jsp:body>
	<div class="row equal">
		<div class="col-md-9">
			<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">Detalhe</h3>
					</div>
					<div class="panel-body">
						<div class="camposDetalhe">
							<dl>
								<dt>Data/hora de entrada</dt>
								<dd>
									<fmt:formatDate value="${visita.entrada}" dateStyle="medium" timeStyle="short" type="both" />
								</dd>
							</dl>
							<dl>
								<dt>Data/hora de saída</dt>
								<dd>
									<fmt:formatDate value="${visita.saida}" dateStyle="medium" timeStyle="short" type="both" />
								</dd>
							</dl>
							<dl>
								<dt>Data e hora de cadastro</dt>
								<dd>
									<fmt:formatDate value="${visita.dataHoraCadastro}" dateStyle="medium" timeStyle="short" type="both" />
								</dd>
							</dl>
							<dl>
								<dt>Servidor responsável</dt>
								<dd>${visita.nomeProcurado}</dd>
							</dl>
							<dl>
								<dt>Setor procurado</dt>
								<dd>${visita.setorProcurado}</dd>
							</dl>
							<dl>
								<dt>Empresa</dt>
								<dd>${visita.empresa.nome}</dd>
							</dl>
							<dl>
								<dt>Local</dt>
								<dd>${visita.setor.nome}</dd>
							</dl>
							<dl>
								<dt>Cadastrado por</dt>
								<dd>${visita.usuario.nome}</dd>
							</dl>
						</div>

					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="headingOne">
						<h4 class="panel-title">
							<a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">Dados do visitante</a>
						</h4>
					</div>
					<div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
						<div class="panel-body">
							<div class="camposDetalhe">
								<dl>
									<dt>Nome</dt>
									<dd>${visita.visitante.nome}</dd>
								</dl>
								<dl>
									<dt>Documento</dt>
									<dd>${visita.visitante.documento}</dd>
								</dl>
								<dl>
									<dt>Órgao Emissor</dt>
									<dd>${visita.visitante.orgaoEmissor}</dd>
								</dl>
								<dl>
									<dt>UF de Emissão</dt>
									<dd>${visita.visitante.uf}</dd>
								</dl>
								<dl>
									<dt>Profissão ou Cargo</dt>
									<dd>${visita.visitante.profissao}</dd>
								</dl>
								<dl>
									<dt>E-mail</dt>
									<dd>${visita.visitante.email}</dd>
								</dl>
								<dl>
									<dt>Telefone 1</dt>
									<dd>${visita.visitante.telefone}</dd>
								</dl>
								<dl>
									<dt>Telefone 2</dt>
									<dd>${visita.visitante.telefoneAlternativo}</dd>
								</dl>
							</div>
						</div>
					</div>
				</div>
				<c:if test="${not empty visita.visitante.endereco}">
					<div class="panel panel-default">
						<div class="panel-heading" role="tab" id="headingTwo">
							<h4 class="panel-title">
								<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo" title="${visita.visitante.endereco}">Dados de Endereço</a>
							</h4>
						</div>
						<div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
							<div class="panel-body">
								<div class="camposDetalhe">
									<dl>
										<dt>Logradouro</dt>
										<dd>${visita.visitante.endereco.logradouro}</dd>
									</dl>
									<dl>
										<dt>Número</dt>
										<dd>${visita.visitante.endereco.numero}</dd>
									</dl>
									<dl>
										<dt>Complemento</dt>
										<dd>${visita.visitante.endereco.complemento}</dd>
									</dl>
									<dl>
										<dt>Bairro</dt>
										<dd>${visita.visitante.endereco.bairro}</dd>
									</dl>
									<dl>
										<dt>CEP</dt>
										<dd>${visita.visitante.endereco.cep}</dd>
									</dl>
									<dl>
										<dt>Referência</dt>
										<dd>${visita.visitante.endereco.referencia}</dd>
									</dl>
									<dl>
										<dt>Município</dt>
										<dd>${visita.visitante.endereco.municipio}</dd>
									</dl>
									<dl>
										<dt>UF</dt>
										<dd>${visita.visitante.endereco.uf}</dd>
									</dl>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<div class="botoesDetalhar">
					<div class="col-sm-offset-2 col-sm-10" style="margin-top: 10px">
						<div class="btn-group" role="group">
							<a class="btn btn-primary active" title="Ir para tela de edição" href="visita/${visita.id}">
								<span class="glyphicon glyphicon-edit"></span>
								Editar
							</a>
							<a class="btn btn-default" href="visita/registros" title="Voltar para a tela de listagem de registros">
								<span class="glyphicon glyphicon-circle-arrow-left"></span>
								Voltar
							</a>
						</div>
				</div>
				</div>
			</div>
		</div>
		<div class="col-md-3">
			<jsp:include page="visita-sem-baixa.jsp">
				<jsp:param value="visita/detalhe" name="baseUrl" />
			</jsp:include>
		</div>
	</div>
	</jsp:body>
</tags:base-template>
