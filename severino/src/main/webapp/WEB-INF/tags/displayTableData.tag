<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="urlEdit" description="url para abrir a tela de edição" required="false" type="java.lang.String"%>
<%@ attribute name="urlAdd" description="url para abrir a tela de cadastro" required="false" type="java.lang.String"%>
<%@ attribute name="urlRemove" description="url para remover um registro" required="false" type="java.lang.String"%>
<%@ attribute name="urlJsonList" description="url que retorna os dados para preencher a tabela no formato json (Ex. empresas/listar)" required="true" type="java.lang.String"%>
<%@ attribute name="fieldList" description="Lista separada por virgula contendo as propriedades a serem exibidas" required="true" type="java.lang.String"%>
<%@ attribute name="labelList" description="Lista contendo o rótulo das colunas separada por virgula" required="true" type="java.lang.String"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type="text/javascript">
	CesDataTableUtils.displayDataTableOnReady("${urlJsonList}", "${urlEdit}", "${urlRemove}", "${fieldList}");
</script>

<table id="tableRecordSet" class="table table-striped table-bordered" style="width: 100%">
	<thead>
		<c:if test="${not empty urlAdd}">
			<tr>
				<th colspan="${fn:length(fn:split(labelList, ',')) + 1}">
					<a href="${urlEdit}" class="btn btn-default">
						<span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
						Adicionar
					</a>
				</th>
			</tr>
		</c:if>
		<tr>
			<c:forTokens items="${labelList}" delims="," var="label">
				<th>${label}</th>
			</c:forTokens>
			<th></th>
		</tr>
	</thead>
</table>