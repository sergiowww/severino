<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="totalPessoas" value="${fn:length(pessoasDisponiveis)}" />
<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			Procuradores e Servidores na Casa
			<c:if test="${not empty pessoasDisponiveis}">
				<span class="badge" style="float: right;" title="servidores na casa/total">${totalPessoasNaCasa}/${totalPessoas}</span>
			</c:if>
		</h3>
	</div>
	<div class="panel-body">
		<div class="table-responsive bodycontainer scrollable">
			<table class="table table-scrollable">
				<tbody>
					<c:if test="${empty pessoasDisponiveis}">
						<tr>
							<td colspan="4" class="text-center text-info">Nenhum registro de disponibilidade encontrado</td>
						</tr>
					</c:if>
					<c:forEach items="${pessoasDisponiveis}" var="p" varStatus="status" step="2">
						<tr>
							<c:set var="pessoa" value="${p}" scope="request" />
							<jsp:include page="home-servidores-item.jsp" />
							<c:if test="${status.index + 1 lt totalPessoas}">
								<c:set var="pessoa" value="${pessoasDisponiveis[status.index + 1]}" scope="request" />
								<jsp:include page="home-servidores-item.jsp" />
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>