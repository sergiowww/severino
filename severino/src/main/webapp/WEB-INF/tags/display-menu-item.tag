<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="selectedItem" description="Item de menu selecionado" required="false" type="java.lang.String"%>
<%@ attribute name="menuItens" description="Itens de menu separados por virgula" required="false" type="java.lang.String"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:forTokens items="${menuItens}" delims="," var="menuItem">
	<c:set var="urlPart" value="${fn:substringAfter(menuItem, ';')}" />
	<c:set var="urlMenu" value="${urlPart}" />
	<c:set var="labelMenu" value="${fn:substringBefore(menuItem, ';')}" />
	<c:if test="${selectedItem eq  urlPart}">
		<li class="active">
			<a href="${urlMenu}">${labelMenu}</a>
		</li>
	</c:if>
	<c:if test="${selectedItem ne urlPart}">
		<li>
			<a href="${urlMenu}">${labelMenu}</a>
		</li>
	</c:if>
</c:forTokens>