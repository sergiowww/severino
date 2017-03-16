/**
 * Javascript da página visita.jsp
 */

function VisitaClass() {
	/**
	 * Campos que não permitem alteração por esta tela.
	 */
	this.FIELDS_VISITANTE = [ "nome", "uf", "orgaoEmissor" ];
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
		this.setFormValues(eventoUsuario, visitante, this.FIELD_PREFIX);
		if (visitantePresente && visitante.endereco != undefined && visitante.endereco != null) {
			this.setFormValues(eventoUsuario, visitante.endereco, this.FIELD_PREFIX + "endereco\\.");
		}
		if (eventoUsuario && !visitantePresente) {
			$('[id^=visitante]').not("#visitante\\.tokenFoto, #visitante\\.documento").val("");
		}
		if (visitantePresente) {
			CameraController.carregarFoto();
		}
	};

	this.setFormValues = function(eventoUsuario, object, prefix) {
		for ( var fieldName in object) {
			var valor = object[fieldName];
			if (object.hasOwnProperty(fieldName) && jQuery.type(valor) !== "object") {
				var fieldHidden = $(prefix + fieldName + this.HIDDEN_SUFFIX);
				var field = $(prefix + fieldName);

				if (field.is(":disabled")) {
					field.val(valor);
				} else if (eventoUsuario) {
					field.val(valor);
				}
				if (field.prop("placeholder") !== "") {
					field.trigger("keyup")
				}
				fieldHidden.val(field.val());
			}
		}
	};

	$(document).ready(this.configurarAutoCompleteEmpresa.bind(this));
	$(document).ready(this.configurarConsultaPeloDocumento.bind(this));
	$(document).ready(this.configurarAutoCompleteNomeProcurado.bind(this));
};
var Visita = new VisitaClass();