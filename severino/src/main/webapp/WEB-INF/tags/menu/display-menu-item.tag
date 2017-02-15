<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="selectedItem" description="Item de menu selecionado" required="false" type="java.lang.String"%>
<%@ attribute name="menuItens" description="Itens de menu separados por virgula" required="false" type="java.lang.String"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:forTokens items="${menuItens}" delims="," var="menuItem">
	<c:set var="urlMenu" value="${fn:substringAfter(menuItem, ';')}" />
	<c:set var="labelMenu" value="${fn:substringBefore(menuItem, ';')}" />
	<li ${selectedItem eq urlMenu ? 'class=\"active\"' : ''}><a href="${urlMenu}">${labelMenu}</a></li>
</c:forTokens>