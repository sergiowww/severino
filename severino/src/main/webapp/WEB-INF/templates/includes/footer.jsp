<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<footer class="footer">
	<div class="container">
		<p class="text-muted">
			Severino &copy; Departamento de Informática da PRT da 8ª Região - 
			<jsp:useBean id="dataHoraAtual" class="java.util.Date" /> 
			<fmt:formatDate value="${dataHoraAtual}" dateStyle="medium" timeStyle="short" type="both" />
		</p>
	</div>
</footer>