<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			Visitas sem baixa
			<c:if test="${not empty visitantesSemBaixa}">
				<span class="badge" style="float: right;">${fn:length(visitantesSemBaixa)}</span>
			</c:if>
		</h3>
	</div>
	<div class="panel-body">
		<div class="table-responsive bodycontainer scrollable" style="height: 402px;">
			<table class="table table-scrollable table-hover">
				<tbody>
					<c:if test="${empty visitantesSemBaixa}">
						<tr>
							<td colspan="3" class="text-center text-info">Nenhum visitante encontrado</td>
						</tr>
					</c:if>
					<c:forEach items="${visitantesSemBaixa}" var="visitaSemBaixa">
						<tr class="${visitaSemBaixa.id eq visita.id ? 'active ' : ''}table-row" data-href="${param.baseUrl}/${visitaSemBaixa.id}">
							<fmt:formatDate value="${visitaSemBaixa.entrada}" type="both" dateStyle="MEDIUM" timeStyle="SHORT" var="dataEntradaCompleta" />
							<td title="${dataEntradaCompleta}">
								<fmt:formatDate value="${visitaSemBaixa.entrada}" type="time" dateStyle="MEDIUM" timeStyle="SHORT" />
							</td>
							<td>
								<div class="nomeVisitante" title="${visitaSemBaixa.visitante.nome}">${visitaSemBaixa.visitante.nome}</div>
							</td>
							<td>
								<a href="${param.baseUrl}/${visitaSemBaixa.id}" class="btn btn-xs btn-default">
									<span class="glyphicon glyphicon-edit"></span>
								</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
