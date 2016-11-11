/**
 * Javascript da página visita.jsp
 */

function VisitaClass() {
	this.FIELDS_VISITANTE = [ "nome", "uf", "orgaoEmissor", "profissao" ];
	this.FIELD_PREFIX = "#visitante\\.";
	this.HIDDEN_SUFFIX = "_hidden";
	this.configurarAutoCompleteNomeProcurado = function() {
		var contextPath = Utils.getContextPath();
		$("#nomeProcurado").autocomplete({
			source: contextPath + "ldap/listarPessoasPorParteNome",
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
	this.configurarAutoCompleteEmpresa = function() {
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
	this.configurarConsultaPeloDocumento = function() {
		var fieldDocumento = $("#visitante\\.documento");
		var fieldHiddenId = $("#id");

		if (fieldDocumento.val()) {
			this.onChangePesquisarDocumento(false);
		}
		fieldDocumento.on("change keyup paste", this.onChangePesquisarDocumento.bind(this, true));
		if (fieldDocumento.val() && !fieldHiddenId.val()) {
			this.toggleElementsState(false);
		} else {
			this.toggleElementsState(true);
		}
	};

	/**
	 * Handler do evento ao digitar o documento.
	 */
	this.onChangePesquisarDocumento = function(eventoUsuario) {
		var contextPath = Utils.getContextPath();
		var callBackDocumento = this.pesquisarVisitantePeloDocumento.bind(this);
		var callback = function(visitante) {
			callBackDocumento(visitante, eventoUsuario);
		}
		var fieldDocumento = $("#visitante\\.documento");
		var documentoValor = fieldDocumento.val();
		$.getJSON(contextPath + "visitante/getByDocumento?term=" + documentoValor, callback);
	};

	/**
	 * Mudar estado dos elementos controlados.
	 */
	this.toggleElementsState = function(disabled) {
		for (var i = 0; i < this.FIELDS_VISITANTE.length; i++) {
			var fieldName = this.FIELD_PREFIX + this.FIELDS_VISITANTE[i];
			var fieldHidden = $(fieldName + this.HIDDEN_SUFFIX);
			var field = $(fieldName);
			fieldHidden.prop("disabled", !disabled);
			field.prop("disabled", disabled);
		}
	};

	this.pesquisarVisitantePeloDocumento = function(visitante, eventoUsuario) {
		var visitantePresente = visitante != null;
		this.toggleElementsState(visitantePresente);
		for (var i = 0; i < this.FIELDS_VISITANTE.length; i++) {
			var fieldName = this.FIELDS_VISITANTE[i];
			var fieldHidden = $(this.FIELD_PREFIX + fieldName + this.HIDDEN_SUFFIX);
			var field = $(this.FIELD_PREFIX + this.FIELDS_VISITANTE[i]);
			if (visitantePresente) {
				fieldHidden.val(visitante[fieldName]);
				field.val(visitante[fieldName]);
			} else if (eventoUsuario) {
				field.val("");
			}
		}
	};
};
var Visita = new VisitaClass();

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
