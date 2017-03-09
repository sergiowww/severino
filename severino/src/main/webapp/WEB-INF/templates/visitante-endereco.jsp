<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<form:hidden path="${param.prefixName}endereco.id" id="${param.prefixName}endereco.id" />
	<tags:inputField label="Logradouro" name="${param.prefixName}endereco.logradouro" type="text" extraCssClass="col-md-3" requiredField="true" />
	<tags:inputField label="Número" name="${param.prefixName}endereco.numero" type="text" extraCssClass="col-md-3" />
	<tags:inputField label="Complemento" name="${param.prefixName}endereco.complemento" type="text" extraCssClass="col-md-3" />
	<tags:inputField label="Referência" name="${param.prefixName}endereco.referencia" type="text" extraCssClass="col-md-3" />
</div>
<div class="row">
	<tags:inputField label="Bairro" name="${param.prefixName}endereco.bairro" type="text" extraCssClass="col-md-3" requiredField="true" />
	<tags:inputField label="CEP" name="${param.prefixName}endereco.cep" type="text" extraCssClass="col-md-3" mask="00000-000" />
	<tags:inputField label="Municipio" name="${param.prefixName}endereco.municipio" type="text" extraCssClass="col-md-3" requiredField="true" />
	<tags:selectField label="Estado" name="${param.prefixName}endereco.uf" collection="${ufs}" itemLabel="name" itemValue="name" searchItems="false" extraCssClass="col-md-3" requiredField="true" />
</div>
<div class="label label-info" style="float: right; margin-top: 10px;">preencha todos os dados ou deixe-os em branco</div>