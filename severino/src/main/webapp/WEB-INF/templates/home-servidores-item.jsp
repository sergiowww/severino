<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<td>${pessoa.nome}</td>
<td>
	<fmt:formatDate value="${pessoa.dataEvento}" type="time" timeStyle="SHORT" var="horaEvento" />
	<fmt:formatDate value="${pessoa.dataEvento}" type="both" dateStyle="MEDIUM" timeStyle="SHORT" var="dataHoraEvento" />
	<c:if test="${pessoa.entrou}">
		<c:set var="dicaUltimoEvento">
			Entrou as ${dataHoraEvento}
		</c:set>
	</c:if>
	<c:if test="${not pessoa.entrou}">
		<c:set var="dicaUltimoEvento">
			Saiu as ${dataHoraEvento}
		</c:set>
	</c:if>
	<c:if test="${pessoa.fonte eq 'VIAGEM'}">
		<c:set var="urlDetalhar" value="viagem/${pessoa.id}" />
	</c:if>
	<c:if test="${pessoa.fonte eq 'ACESSO_GARAGEM'}">
		<c:set var="urlDetalhar" value="acessoGaragem/${pessoa.id}" />
	</c:if>
	<form action="${urlDetalhar}" method="get">
		<tags:toggleButton status="${pessoa.entrou}" label="${horaEvento}" tip="${dicaUltimoEvento}" />
	</form>
</td>