/**
 * Javascript da página viagem.jsp
 */
function ViagemClass() {

	this.buscarServidorText = null;

	this.configurarAutoCompleteNomePassageiro = function() {
		var contextPath = Utils.getContextPath();
		this.buscarServidorText = $("#buscarServidorText");
		this.buscarServidorText.autocomplete({
			source: contextPath + "ldap/listarPessoasPorParteNome",
			minLength: 2,
			delay: 600,
			autoFocus: true,
			select: this.selectCallback.bind(this)

		}).data("ui-autocomplete")._renderItem = function(ul, item) {
			return $("<li></li>").data("item.autocomplete", item).append("<a>" + item.matricula + " - " + item.nome + " - " + item.departamento + "</a>").appendTo(ul);
		};

	};

	this.buscarCelulaComNomePassageiro = function(nome) {
		return $("#passageirosTable tbody td:contains('" + nome + "')");
	};

	this.removerPassageiro = function(nome) {
		var tr = this.buscarCelulaComNomePassageiro(nome).parent();
		tr.remove();
		this.toggleMensagemSemPassageiros();
	};

	this.selectCallback = function(event, ui) {
		this.buscarServidorText.val("");
		this.adicionarPassageiro(ui.item);
		return false;
	};

	this.adicionarPassageiroAvulso = function() {
		var passageiroNome = this.buscarServidorText.val();
		this.buscarServidorText.val("");
		var pessoa = {
			nome: passageiroNome,
			matricula: ""
		};
		this.adicionarPassageiro(pessoa);
	};

	this.adicionarPassageiro = function(pessoa) {
		var td = this.buscarCelulaComNomePassageiro(pessoa.nome);
		if (td.length == 0) {
			var tbody = this.getTbodyPassageiros();
			pessoa.index = this.getUniqueIndexPassageiro();
			var template = $("#templatePassageiro").tmpl(pessoa);
			template.appendTo(tbody);
			this.toggleMensagemSemPassageiros();
		}
	};

	this.getUniqueIndexPassageiro = function() {
		var total = this.getTotalPassageiros();
		while ($("input[name=\"passageiros[" + total + "].nome\"]").length) {
			total++;
		}
		return total;
	};

	this.getTotalPassageiros = function() {
		return $("#passageirosTable tbody .passageiroItem").length;
	};

	this.toggleMensagemSemPassageiros = function() {
		var totalPassageiros = this.getTotalPassageiros();
		var tr = $("#mensagemSemRegistros")
		if (totalPassageiros == 0) {
			tr.show();
		} else {
			tr.hide();
		}
	};

	this.confirmarFluxo = function(form, nomeMotorista, entrou) {
		entrou = !entrou;
		var acao = entrou ? "entrada" : "saída";

		bootbox.confirm("Confirma a <b>" + acao.toUpperCase() + "</b> de <b>" + nomeMotorista + "</b>?", function(result) {
			if (result) {
				form.submit();
			}
		});
		return false;
	};

	this.getTbodyPassageiros = function() {
		return $("#passageirosTable tbody");
	};

	$(document).ready(this.toggleMensagemSemPassageiros.bind(this))
	$(document).ready(this.configurarAutoCompleteNomePassageiro.bind(this));
}

var viagem = new ViagemClass();