/**
 * Habilitar e desabilitar área de elementos de acordo com a seleção.
 */
function RadioButtonToggleArea(radioButtonName) {
	this.radioButtonName = radioButtonName;
	this.toggleArea = function() {
		this.getRadioButtons().on("click", this.toggle.bind(this))
		$("input[name=\"" + this.radioButtonName + "\"]:checked").click();
	};

	this.getRadioButtons = function() {
		return $("input[name=\"" + this.radioButtonName + "\"]");
	};

	this.toggle = function(event) {
		var opcaoSelecionada = $(event.target).val();
		var radios = this.getRadioButtons();
		for (var i = 0; i < radios.length; i++) {
			var opcao = radios[i].value;
			var elementsToHandle = this.queryElements(opcao);
			if (opcao == opcaoSelecionada) {
				elementsToHandle.prop("disabled", false);
			} else {
				elementsToHandle.prop("disabled", true);
			}
		}
	};

	this.queryElements = function(opcaoSelecionada) {
		return $("#" + this.radioButtonName + opcaoSelecionada + " input, #" + this.radioButtonName + opcaoSelecionada + " select");
	};

	$(document).ready(this.toggleArea.bind(this));
}