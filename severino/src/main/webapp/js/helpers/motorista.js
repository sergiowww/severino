/**
 * Javascript da página motorista.jsp
 */

function MotoristaClass() {
	this.configurarAutoCompleteNome = function() {
		var contextPath = Utils.getContextPath();
		$("#nome").autocomplete({
			source: contextPath + "ldap/listarPessoasPorParteNome",
			minLength: 2,
			delay: 600,
			autoFocus: true,
			select: function(event, ui) {
				$("#nome").val(ui.item.nome);
				$("#matricula").val(ui.item.matricula);
				var tipo = ui.item.tipo;
				if (tipo != null) {
					$("#cargo").val(tipo.toUpperCase());
				}
				return false;
			}

		}).data("ui-autocomplete")._renderItem = function(ul, item) {
			return $("<li></li>").data("item.autocomplete", item).append("<a>" + item.nome + " - " + item.departamento + "</a>").appendTo(ul);
		};
	};
	$(document).ready(this.configurarAutoCompleteNome.bind(this));
}
var motorista = new MotoristaClass();