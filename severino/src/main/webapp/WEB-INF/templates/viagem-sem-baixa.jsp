<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			Viagens sem baixa
			<c:if test="${not empty viagensSemBaixa}">
				<span class="badge" style="float: right;">${fn:length(viagensSemBaixa)}</span>
			</c:if>
		</h3>
	</div>
	<div class="panel-body">
		<div class="table-responsive bodycontainer scrollable" style="height: 238px;">
			<table class="table table-scrollable">
				<tbody>
					<c:if test="${empty viagensSemBaixa}">
						<tr>
							<td colspan="3" class="text-center text-info">Nenhuma viagem em andamento</td>
						</tr>
					</c:if>
					<c:forEach items="${viagensSemBaixa}" var="viagemSemBaixa">
						<tr class="row-viagems-em-andamento ${viagemSemBaixa.id eq viagem.id ? 'active' : ''}">
							<fmt:formatDate value="${viagemSemBaixa.saida}" type="both" timeStyle="SHORT" dateStyle="MEDIUM" var="dataSaidaCompleta" />
							<td title="${dataSaidaCompleta}">
								<fmt:formatDate value="${viagemSemBaixa.saida}" type="time" timeStyle="SHORT" />
							</td>
							<td title="${viagemSemBaixa.motorista.nome}">
								<div class="nomeMotorista">${viagemSemBaixa.motorista.nome}</div>
							</td>
							<td>
								<a href="viagem/${viagemSemBaixa.id}" class="btn btn-xs btn-default">
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