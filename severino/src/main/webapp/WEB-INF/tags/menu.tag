<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="selectedItem" description="Item de menu selecionado" required="false" type="java.lang.String"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- Os itens de menu são colocados na variável menuItens separados por virgula -->
<c:set var="menuItens" value="Empresas;empresa/registros,Setores;setor/registros,Usuários do Sistema;usuario/registros,Visitantes;visitante/registros,Registro de Visitantes;visita/registros,Nova visita;visita/" />

<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}/">SEVERINO</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<c:forTokens items="${menuItens}" delims="," var="menuItem">
					<c:set var="urlPart" value="${fn:substringAfter(menuItem, ';')}" />
					<c:set var="urlMenu" value="${pageContext.request.contextPath}/${urlPart}" />
					<c:set var="labelMenu" value="${fn:substringBefore(menuItem, ';')}" />
					<c:if test="${selectedItem eq  urlPart}">
						<li class="active"><a href="${urlMenu}">${labelMenu}</a></li>
					</c:if>
					<c:if test="${selectedItem ne urlPart}">
						<li><a href="${urlMenu}">${labelMenu}</a></li>
					</c:if>
				</c:forTokens>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li style="padding-right: 10px;">
					<b>${pageContext.request.userPrincipal.name}</b>
					<br />
					<span class="text-success">${sessionScope['scopedTarget.usuarioHolder'].usuario.nome}</span>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/logout" class="btn" role="button">
						Sair
						<span class="glyphicon glyphicon-log-out"></span>
					</a>
				</li>

			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>