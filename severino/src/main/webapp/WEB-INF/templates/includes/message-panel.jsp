<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="js/setup-page.js" charset="UTF-8"></script>

<c:if test="${not empty error}">
	<div class="alert alert-danger fade in">
		<a href="#" class="close" data-dismiss="alert">&times;</a>
		${error}
	</div>
</c:if>
<c:if test="${not empty msg}">
	<div class="alert alert-success fade in">
		<a href="#" class="close" data-dismiss="alert">&times;</a>
		${msg}
	</div>
</c:if>
