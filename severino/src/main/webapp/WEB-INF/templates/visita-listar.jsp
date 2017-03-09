<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<tags:base-template title="Visitas" menuSelecionado="visita/registros">
	<jsp:body>
		<tags:displayTableData urlEdit="visita/detalhe/" urlAdd="visita/" fieldList="id,entrada:date,saida:date,nomeProcurado,empresa.nome,setor.nome,usuario.id,visitante.nome" urlJsonList="visita/listar" labelList="C�digo,Entrada,Sa�da,Respons�vel,Empresa,Setor,Usu�rio cadastro,Nome visitante" />
	</jsp:body>
</tags:base-template>