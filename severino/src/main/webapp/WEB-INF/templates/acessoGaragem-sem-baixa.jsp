<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			Acessos sem baixa
			<c:if test="${not empty acessosSemBaixa}">
				<span class="badge" style="float: right;">${fn:length(acessosSemBaixa)}</span>
			</c:if>
		</h3>
	</div>
	<div class="panel-body">
		<div class="table-responsive bodycontainer scrollable" style="height: 405px;">
			<table class="table table-scrollable">
				<tbody>
					<c:if test="${empty acessosSemBaixa}">
						<tr>
							<td colspan="3" class="text-center text-info">Não há usuários na garagem</td>
						</tr>
					</c:if>
					<c:forEach items="${acessosSemBaixa}" var="acessoGaragemSemBaixa">
						<tr class="${acessoGaragemSemBaixa.id eq acessoGaragem.id ? 'active' : ''}">
							<c:set var="dataEntrada">
								<fmt:formatDate value="${acessoGaragemSemBaixa.entrada}" type="both" dateStyle="MEDIUM" timeStyle="SHORT" />
							</c:set>
							<td title="${dataEntrada}">
								<fmt:formatDate value="${acessoGaragemSemBaixa.entrada}" type="time" timeStyle="SHORT" />
							</td>
							<td>
								<c:set var="nomeVisitante">
									<c:out value="${acessoGaragemSemBaixa.visita.visitante.nome}" default="${acessoGaragemSemBaixa.motorista.nome}" />
								</c:set>
								<div class="nomeVisitante" title="${nomeVisitante}">${nomeVisitante}</div>
							</td>
							<td>
								<a href="acessoGaragem/${acessoGaragemSemBaixa.id}" class="btn btn-xs btn-default">
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
