<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags"%>

<div class="row">
	<tags:inputField label="Documento" name="${param.prefixName}documento" type="text" extraCssClass="col-md-3" requiredField="true" />
	<tags:inputField label="Emissor" name="${param.prefixName}orgaoEmissor" type="text" extraCssClass="col-md-2" requiredField="true" tip="Órgão Emissor do documento" />
	<tags:selectField label="UF" name="${param.prefixName}uf" collection="${ufs}" itemLabel="name" itemValue="name" searchItems="false" extraCssClass="col-md-2" requiredField="true" />
	<tags:inputField label="Nome" name="${param.prefixName}nome" type="text" extraCssClass="col-md-5" requiredField="true" />
</div>
<div class="row">
	<tags:inputField label="Profissão / Cargo" name="${param.prefixName}profissao" type="text" requiredField="false" extraCssClass="col-md-3" />
	<tags:inputField label="E-mail" name="${param.prefixName}email" type="text" requiredField="false" extraCssClass="col-md-4" />
	<div class="col-md-5 nestedColumn">
		<tags:inputField label="Telefone 1" name="${param.prefixName}telefone" type="text" requiredField="false" extraCssClass="col-md-6" mask="tel" />
		<tags:inputField label="Telefone 2" name="${param.prefixName}telefoneAlternativo" type="text" requiredField="false" extraCssClass="col-md-6" mask="tel" />
	</div>
</div>