/**
 * Javascript da página acessoGaragem.jsp
 */
function AcessoGaragemClass() {
	this.CAMPOS_VEICULO = [ "input#veiculo\\.id", "#veiculo\\.marca", "#veiculo\\.modelo", "input#veiculo\\.cor" ];
	this.toogleFieldsetVeiculo = function() {
		var isVisitante = eval($("input[name=\"usuarioVisitante\"]:checked").val());
		$("#fieldsetVeiculo input, #fieldsetVeiculo select").prop("disabled", !isVisitante);
		if (isVisitante) {
			$("select#veiculo\\.id").val(null).trigger("change");
		} else {
			$("select#visita\\.id").val(null).trigger("change");
		}
	};
	this.configurarEventos = function() {
		$("input[name=\"usuarioVisitante\"]").on("click", this.toogleFieldsetVeiculo.bind(this));
		$("select#veiculo\\.id").on("select2:select", this.buscarDadosVeiculos.bind(this));
		$("input#veiculo\\.id").on("change keyup paste", this.buscarDadosVeiculos.bind(this));
	};
	this.buscarDadosVeiculos = function(event) {
		var fieldEvent = $(event.target);
		var placa = $(event.target).val();
		if (placa.length == 7) {
			var contextPath = Utils.getContextPath();
			var preencherVeiculoCallback = this.preencherVeiculo.bind(this)
			var callBackLimpar = function(veiculo) {
				preencherVeiculoCallback(veiculo, fieldEvent[0].tagName.toLowerCase() == "select");
			};
			$.getJSON(contextPath + "veiculo/buscarPorPlaca?placa=" + placa, callBackLimpar);
		}
	};
	this.preencherVeiculo = function(veiculo, limparCampos) {

		for (var i = 0; i < this.CAMPOS_VEICULO.length; i++) {
			var fieldName = this.CAMPOS_VEICULO[i];
			var field = $(fieldName);
			if (veiculo != null) {
				var fieldObject = fieldName.substr(fieldName.lastIndexOf(".") + 1, fieldName.length);
				field.val(veiculo[fieldObject]);
			} else if (limparCampos) {
				field.val("");
			}
		}
	};

	$(document).ready(this.toogleFieldsetVeiculo.bind(this));
	$(document).ready(this.configurarEventos.bind(this));
}

var acessoGaragem = new AcessoGaragemClass();