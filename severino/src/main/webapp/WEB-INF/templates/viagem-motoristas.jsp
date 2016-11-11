<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			Controle de Entrada e Saída
			<c:if test="${not empty controleMotoristas}">
				<span class="badge" style="float: right;" title="técnicos disponíveis/total">${fn:length(motoristas)}/${fn:length(controleMotoristas)}</span>
			</c:if>
		</h3>
	</div>
	<div class="panel-body">
		<div class="table-responsive bodycontainer scrollable" style="height: 251px;">
			<table class="table table-scrollable">
				<tbody>
					<c:if test="${empty controleMotoristas}">
						<tr>
							<td colspan="2" class="text-center text-info">
								Nenhum técnico de transporte cadastrado.
								<a href="motorista/">Adicionar...</a>
							</td>
						</tr>
					</c:if>
					<c:forEach items="${controleMotoristas}" var="controle">
						<tr class="row-motoristas">
							<td title="${controle.motorista.nome}">
								<div class="nomeMotorista">${controle.motorista.nome}</div>
							</td>
							<td>
								<form action="viagem/atualizarDisponibilidade" method="post">
									<input type="hidden" name="idViagem" value="${viagem.id}" />
									<input type="hidden" name="idMotorista" value="${controle.motorista.id}" />
									<input type="text" placeholder="hh:mm" class="form-control" id="horario-${controle.motorista.id}" name="horario" />
									<c:if test="${empty controle.dataHora}">
										<c:set var="dataHoraFormatada">00:00</c:set>
									</c:if>
									<c:if test="${not empty controle.dataHora}">
										<c:set var="dataHoraFormatada">
											<fmt:formatDate value="${controle.dataHora}" type="time" timeStyle="SHORT" />
										</c:set>
									</c:if>
									<c:if test="${controle.fluxoEntrada}">
										<c:set var="dicaUltimoEvento">
											Entrou as ${dataHoraFormatada}
										</c:set>
									</c:if>
									<c:if test="${not controle.fluxoEntrada}">
										<c:set var="dicaUltimoEvento">
											Saiu as ${dataHoraFormatada}
										</c:set>
									</c:if>
									<tags:toggleButton status="${controle.fluxoEntrada}" label="${dataHoraFormatada}" tip="${dicaUltimoEvento}" />
									<script type="text/javascript">
										$(document).ready(function() {
											var field = $("#horario-${controle.motorista.id}");
											field.mask("00:00");
										});
									</script>
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>