<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="selectedItem" description="Item de menu selecionado" required="false" type="java.lang.String"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<%-- Os itens de menu são colocados na variável menuItens separados por virgula --%>
<c:set var="menuItens">
	Empresas;empresa/registros,
	Setores;setor/registros,
	Usuários do Sistema;usuario/registros,
	Visitantes;visitante/registros,
	Registro de Visitantes;visita/registros,
	<span class="text-primary">Nova visita</span>;visita/
</c:set>
<c:set var="garagemItens">
	Condutores;motorista/registros,
	Veículos;veiculo/registros,
	Histórico;viagem/registros,
	Controle de Ponto;controleMotorista/registros,
	<span class="text-primary">Fluxo Motoristas</span>;viagem/,
	<span class="text-primary">Acesso Garagem</span>;acessoGaragem/
</c:set>
<header>
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
				<a class="navbar-brand" href=".">
					<img alt="logo severino" src="img/favicon.ico" style="display: inline;"> SEVERINO
				</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav divider">
					<tags:display-menu-item menuItens="${menuItens}" selectedItem="${selectedItem}" />
				</ul>
				<ul class="nav navbar-nav">
					<tags:display-menu-item menuItens="${garagemItens}" selectedItem="${selectedItem}" />
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li style="padding-right: 10px;"><b>${pageContext.request.userPrincipal.name}</b> <br /> <span class="text-success">${sessionScope['scopedTarget.usuarioHolder'].usuario.nome}</span></li>
					<li><a href="logout" class="btn" role="button">
							Sair
							<span class="glyphicon glyphicon-log-out"></span>
						</a></li>

				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
</header>