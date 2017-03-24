<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="label" description="Rótulo do campo" required="true" type="java.lang.String"%>
<%@ attribute name="name" description="Nome do campo" required="true" type="java.lang.String"%>
<%@ attribute name="extraCssClass" description="Classes de estilo para o agrupador" required="false" type="java.lang.String"%>
<%@ attribute name="tip" description="Dica do campo" required="false" type="java.lang.String"%>
<%@ attribute name="rows" description="Linhas do textarea" required="true" type="java.lang.Integer"%>
<%@ attribute name="cols" description="Colunas do textarea" required="true" type="java.lang.Integer"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<spring:bind path="${name}">
	<c:if test="${status.error}">
		<c:set var="extraCssClass" value="${extraCssClass} has-error"></c:set>
	</c:if>
	<div class="${extraCssClass}">
		<form:label path="${name}" cssClass="control-label" for="${name}">
			${label}
			<c:if test="${status.error}">
				<span style="color: red;" data-toggle="tooltip" data-placement="right" data-delay="3000" data-animation="true" title="${fn:join(status.errorMessages, ', ') }">*</span>
			</c:if>
		</form:label>
		<form:textarea path="${name}" id="${name}" title="${tip}" cssClass="form-control" rows="${rows}" cols="${cols}" />
	</div>
</spring:bind>