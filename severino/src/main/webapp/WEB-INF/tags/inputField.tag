<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="label" description="Rótulo do campo" required="true" type="java.lang.String"%>
<%@ attribute name="name" description="Nome do campo" required="true" type="java.lang.String"%>
<%@ attribute name="type" description="Tipo do campo" required="true" type="java.lang.String"%>
<%@ attribute name="extraCssClass" description="Classes de estilo para o agrupador" required="false" type="java.lang.String"%>
<%@ attribute name="mask" description="Máscara do campo" required="false" type="java.lang.String"%>
<%@ attribute name="tip" description="Dica do campo" required="false" type="java.lang.String"%>
<%@ attribute name="requiredField" description="Indica se o campo é obrigatório o preenchimento" required="false" type="java.lang.Boolean"%>
<%@ attribute name="readonlyField" description="Indica se o campo é somente leitura" required="false" type="java.lang.Boolean"%>
<%@ attribute name="onkeyup" description="Javascript onkeyup event" required="false" type="java.lang.String"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:set var="idJquery" value="${fn:replace(name, '.', '\\\\.')}" />
<c:if test="${type eq 'date' }">
	<c:set var="type" value="text" />
	<script type="text/javascript">
		$(document).ready(function() {
			$("#${idJquery}").datepicker({
				showAnim: "slideDown",
				showButtonPanel: true,
				autoSize: true
			});
		});
	</script>
</c:if>
<c:if test="${not empty mask}">
	<script type="text/javascript">
		$(document).ready(function() {
			var field = $("#${idJquery}");
			field.mask("${mask}");
			field.prop("placeholder", "${mask}");
		});
	</script>
</c:if>
<spring:bind path="${name}">
	<c:if test="${status.error}">
		<c:set var="extraCssClass" value="${extraCssClass += ' has-error has-feedback'}"></c:set>
	</c:if>
	<div class="${extraCssClass}">
		<label class="control-lable">
			${label}
			<c:if test="${requiredField}">
				<span style="color: red;">*</span>
			</c:if>
		</label>
		<form:input type="${type}" path="${name}" cssClass="form-control" id="${name}" title="${tip}" readonly="${readonlyField}" onkeyup="${onkeyup}" />
		<c:if test="${status.error}">
			<i class="form-control-feedback glyphicon glyphicon-remove" data-fv-icon-for="${name}" data-toggle="tooltip" data-placement="left" data-delay="3000" data-animation="true" title="${fn:join(status.errorMessages, ', ') }"></i>
		</c:if>
	</div>
</spring:bind>