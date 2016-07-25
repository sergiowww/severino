/**
 * Javascript da página visita.jsp
 */

var Visita = {};

Visita.configurarAutoCompleteNomeProcurado = function() {
	var contextPath = Utils.getContextPath();
	$("#nomeProcurado").autocomplete({
		source: contextPath + "visita/listarPessoasPorParteNome",
		minLength: 2,
		delay: 600,
		autoFocus: true,
		select: function(event, ui) {
			$("#nomeProcurado").val(ui.item.nome);
			$("#setorProcurado").val(ui.item.departamento);
			return false;
		}

	}).data("ui-autocomplete")._renderItem = function(ul, item) {
		return $("<li></li>").data("item.autocomplete", item).append("<a>" + item.nome + " - " + item.departamento + "</a>").appendTo(ul);
	};
};
/**
 * autocomplete da empresa.
 */
Visita.configurarAutoCompleteEmpresa = function() {
	var contextPath = Utils.getContextPath();
	$("#empresa\\.nome").autocomplete({
		source: contextPath + "empresa/listarPorNome",
		minLength: 1,
		autoFocus: true,
		select: function(event, ui) {
			$("#empresa\\.nome").val(ui.item.nome);
			$("#empresa\\.id").val(ui.item.id);
			return false;
		}
	}).data("ui-autocomplete")._renderItem = function(ul, item) {
		return $("<li></li>").data("item.autocomplete", item).append("<a>(" + item.id + ") " + item.nome + "</a>").appendTo(ul);
	};
};
/**
 * Buscar os dados do visitante pelo documento.
 */
Visita.configurarConsultaPeloDocumento = function() {
	var contextPath = Utils.getContextPath();
	var fieldDocumento = $("#visitante\\.documento");
	var fieldHiddenId = $("#id");

	fieldDocumento.on("change keyup paste", function() {
		var documentoValor = fieldDocumento.val();
		$.getJSON(contextPath + "visitante/getByDocumento?term=" + documentoValor, Visita.pesquisarVisitantePeloDocumento);
	});
	if (fieldDocumento.val() && !fieldHiddenId.val()) {
		Visita.toggleElementsState(false);
	} else {
		Visita.toggleElementsState(true);
	}
};
Visita.toggleElementsState = function(disabled) {
	var fieldNomeHidden = $("#visitante\\.nome_hidden");
	var fieldUfHidden = $("#visitante\\.uf_hidden");
	var fieldOrgaoHidden = $("#visitante\\.orgaoEmissor_hidden");

	var fieldNome = $("#visitante\\.nome");
	var fieldUf = $("#visitante\\.uf");
	var fieldOrgao = $("#visitante\\.orgaoEmissor");

	fieldNomeHidden.prop("disabled", !disabled);
	fieldUfHidden.prop("disabled", !disabled);
	fieldOrgaoHidden.prop("disabled", !disabled);

	fieldNome.prop("disabled", disabled);
	fieldUf.prop("disabled", disabled);
	fieldOrgao.prop("disabled", disabled);

}

Visita.pesquisarVisitantePeloDocumento = function(visitante) {
	var fieldNome = $("#visitante\\.nome");
	var fieldUf = $("#visitante\\.uf");
	var fieldOrgao = $("#visitante\\.orgaoEmissor");

	var fieldNomeHidden = $("#visitante\\.nome_hidden");
	var fieldUfHidden = $("#visitante\\.uf_hidden");
	var fieldOrgaoHidden = $("#visitante\\.orgaoEmissor_hidden");

	if (visitante != null) {
		Visita.toggleElementsState(true);
		fieldNomeHidden.val(visitante.nome);
		fieldUfHidden.val(visitante.uf);
		fieldOrgaoHidden.val(visitante.orgaoEmissor);

		fieldNome.val(visitante.nome);
		fieldUf.val(visitante.uf);
		fieldOrgao.val(visitante.orgaoEmissor);
	} else {
		Visita.toggleElementsState(false);
		fieldNome.val("");
		fieldUf.val("");
		fieldOrgao.val("");

	}
};
/**
 * Configuração dos elementos de formulário.
 */
$(document).ready(function() {
	Visita.configurarAutoCompleteEmpresa();
	Visita.configurarConsultaPeloDocumento();
	Visita.configurarAutoCompleteNomeProcurado();

	$(document).on({
		ajaxStart: function() {
			$("#wait").css("visibility", "visible");
		},
		ajaxStop: function() {
			$("#wait").css("visibility", "hidden");
		}
	});
});
