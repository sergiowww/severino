<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="selectedItem" description="Item de menu selecionado" required="false" type="java.lang.String"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/menu" prefix="menu"%>

<%-- Os itens de menu são colocados na variável menuItens separados por virgula --%>
<c:set var="menuItensTabelasAuxiliares">
	Empresas;empresa/registros,
	Setores;setor/registros,
	Usuários do Sistema;usuario/registros,
</c:set>

<c:set var="menuItensVisitas">
	Visitantes;visitante/registros,
	Registro de visitas;visita/registros
</c:set>

<c:set var="menuItemMotoristas">
	Condutores;motorista/registros,
	Histórico de viagens;viagem/registros,
	Registro de entrada e saída;controleMotorista/registros
</c:set>

<c:set var="menuItemAcessoGaragem">
	Veículos autorizados;veiculo/registros,
	Histórico de Acessos;acessoGaragem/registros
</c:set>

<c:set var="menuItemPrincipais">
	<span class="glyphicon glyphicon-briefcase"></span>
	Nova visita;visita/,
	<span class="glyphicon glyphicon-road"></span>
	Fluxo Motoristas;viagem/,
	<span class="glyphicon glyphicon-list-alt"></span>
	Acesso Garagem;acessoGaragem/
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
					<img alt="logo severino" src="img/favicon.ico" style="display: inline;"> SEVERINO <img alt="Carregando..." src="img/ajax-loader.gif" style="visibility: hidden;" id="wait" />
				</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<menu:parent-menu-item title="Tabelas Auxiliares" menuItens="${menuItensTabelasAuxiliares}" selectedItem="${selectedItem}" />
					<menu:parent-menu-item title="Visitas" menuItens="${menuItensVisitas}" selectedItem="${selectedItem}" />
					<menu:parent-menu-item title="Motoristas" menuItens="${menuItemMotoristas}" selectedItem="${selectedItem}" />
					<menu:parent-menu-item title="Garagem" menuItens="${menuItemAcessoGaragem}" selectedItem="${selectedItem}" />
					<menu:display-menu-item menuItens="${menuItemPrincipais}" selectedItem="${selectedItem}" />
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li style="padding-right: 10px;">
						<b>${pageContext.request.userPrincipal.name}</b> <br />
						<span class="text-success bg-success">${sessionScope['scopedTarget.usuarioHolder'].usuario.nome}</span>
						<span class="text-info bg-info">${sessionScope['scopedTarget.usuarioHolder'].usuario.local.titulo} </span>
					</li>
					<li>
						<a href="logout" class="btn" role="button">
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
</header>