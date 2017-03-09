<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="label" description="Rótulo do campo" required="false" type="java.lang.String"%>
<%@ attribute name="name" description="Nome do campo" required="true" type="java.lang.String"%>
<%@ attribute name="searchItems" description="Indica se este select deve ser pesquisável" required="true" type="java.lang.Boolean"%>
<%@ attribute name="collection" description="Coleção de objetos" required="true" type="java.util.List"%>
<%@ attribute name="itemValue" description="Valor para id de cada option" required="false" type="java.lang.String"%>
<%@ attribute name="itemLabel" description="Propriedade a ser apresentada como rótulo de cada option" required="false" type="java.lang.String"%>
<%@ attribute name="extraCssClass" description="Classes de estilo para o agrupador" required="false" type="java.lang.String"%>
<%@ attribute name="requiredField" description="Indica se o campo é obrigatório o preenchimento" required="false" type="java.lang.Boolean"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:bind path="${name}">
	<c:if test="${status.error}">
		<c:set var="extraCssClass" value="${extraCssClass} has-feedback has-error"></c:set>
	</c:if>

	<div class="${extraCssClass}">
		<c:if test="${not empty label }">
			<form:label path="${name}" cssClass="control-lable" for="${name}">
				${label}
				<c:if test="${requiredField}">
					<span style="color: red;" title="${fn:join(status.errorMessages, ', ') }">*</span>
				</c:if>
			</form:label>
		</c:if>
		<form:select path="${name}" id="${name}" cssClass="form-control">
			<form:option value=""></form:option>
			<form:options itemValue="${itemValue}" itemLabel="${itemLabel}" items="${collection}" />
		</form:select>
		<c:if test="${status.error}">
			<i class="form-control-feedback glyphicon glyphicon-remove" data-fv-icon-for="${name}" data-toggle="tooltip" data-placement="left" data-delay="3000" data-animation="true" title="${fn:join(status.errorMessages, ', ') }"></i>
		</c:if>
	</div>

</spring:bind>
<c:if test="${searchItems}">
	<script type="text/javascript">
		$("#${fn:replace(name, '.', '\\\\.')}").select2();
	</script>
</c:if>
