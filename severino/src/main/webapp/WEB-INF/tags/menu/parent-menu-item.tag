<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="title" description="Título do menu pai" required="true" type="java.lang.String"%>
<%@ attribute name="selectedItem" description="Item de menu selecionado" required="false" type="java.lang.String"%>
<%@ attribute name="menuItens" description="Itens de menu separados por virgula" required="false" type="java.lang.String"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/menu" prefix="menu"%>


<c:forTokens items="${menuItens}" delims="," var="menuItem">
	<c:set var="urlMenu" value="${fn:substringAfter(menuItem, ';')}" />
	<c:if test="${urlMenu eq selectedItem}">
		<c:set var="activeStyleMenu" value=" active" />
	</c:if>
</c:forTokens>

<li class="dropdown${activeStyleMenu}">
	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
		${title}
		<span class="caret"></span>
	</a>
	<ul class="dropdown-menu">
		<menu:display-menu-item menuItens="${menuItens}" selectedItem="${selectedItem}" />
	</ul>
</li>