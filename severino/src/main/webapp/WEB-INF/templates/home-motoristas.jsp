<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			Motoristas na Casa
			<c:if test="${not empty controleMotoristas}">
				<span class="badge" style="float: right;" title="técnicos disponíveis/total">${totalMotoristasNaCasa}/${fn:length(controleMotoristas)}</span>
			</c:if>
		</h3>
	</div>
	<div class="panel-body">
		<div class="table-responsive bodycontainer scrollable">
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
						<tr>
							<td>${controle.motorista.nome}</td>
							<td>
								<c:if test="${empty controle.dataHora}">
									<c:set var="dataHoraFormatada">00:00</c:set>
								</c:if>
								<c:if test="${not empty controle.dataHora}">
									<fmt:formatDate value="${controle.dataHora}" type="time" timeStyle="SHORT" var="dataHoraFormatada" />
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
								<form action="controleMotorista/${controle.id}" method="get">
									<tags:toggleButton status="${controle.fluxoEntrada}" label="${dataHoraFormatada}" tip="${dicaUltimoEvento}" />
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>