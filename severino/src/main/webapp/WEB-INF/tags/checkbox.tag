<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="label" description="Rótulo do campo" required="true" type="java.lang.String"%>
<%@ attribute name="name" description="Nome do campo" required="true" type="java.lang.String"%>
<%@ attribute name="extraCssClass" description="Classes de estilo para o agrupador" required="false" type="java.lang.String"%>
<%@ attribute name="tip" description="Dica do campo" required="false" type="java.lang.String"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:bind path="${name}">
	<c:if test="${status.error}">
		<c:set var="extraCssClass" value="${extraCssClass} has-error"></c:set>
	</c:if>
	<div class="${extraCssClass}">
		<form:label path="${name}" cssClass="control-lable" for="${name}">
			<form:checkbox path="${name}" id="${name}" title="${tip}" />
			${label}
		</form:label>
		<c:if test="${status.error}">
			<div class="help-block">
				<form:errors path="${name}" cssClass="help-inline" />
			</div>
		</c:if>
	</div>
</spring:bind>