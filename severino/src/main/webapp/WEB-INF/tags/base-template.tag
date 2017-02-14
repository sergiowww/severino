<%@ tag description="Template para todas as páginas" language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="title" fragment="false" required="true"%>
<%@ attribute name="head" fragment="true" required="false"%>
<%@ attribute name="menuSelecionado" fragment="false" required="false"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/includes/head.jsp"></jsp:include>
<title>${title}</title>
<jsp:invoke fragment="head"></jsp:invoke>
</head>
<body>
	<tags:menu selectedItem="${menuSelecionado}" />
	<div class="container">
		<jsp:include page="/WEB-INF/templates/includes/message-panel.jsp"></jsp:include>
		<jsp:doBody />
	</div>
	<jsp:include page="/WEB-INF/templates/includes/footer.jsp"></jsp:include>
</body>
</html>