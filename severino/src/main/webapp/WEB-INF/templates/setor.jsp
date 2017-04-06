<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<c:if test="${empty setor.local}">
	<c:set target="${setor}" property="local" value="${sessionScope['scopedTarget.usuarioHolder'].usuario.local}" />
</c:if>
<tags:base-template title="Setor - ${setor.local.titulo}" menuSelecionado="setor/registros">
	<jsp:body>
		<form:form servletRelativeAction="/setor" cssClass="form-horizontal" modelAttribute="setor">
			<form:hidden path="id" />
			<div class="form-group row">
				<tags:inputField label="Nome" name="nome" type="text" extraCssClass="col-md-6" />
				<tags:inputField label="Andar" name="andar" type="number" extraCssClass="col-md-3" />
				<tags:inputField label="Sala" name="sala" type="text" extraCssClass="col-md-3" />
			</div>
			<tags:defaultButtons backUrl="setor/registros" />
		</form:form>
	</jsp:body>
</tags:base-template>