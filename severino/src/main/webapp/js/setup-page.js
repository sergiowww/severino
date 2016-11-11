/**
 * Configurar o título da página com o título do HTML.
 */
/**
 * Retorna o context path das chamadas.
 */
var Utils = {};
Utils.getContextPath = function() {
	return $("base").get(0).href;
};

Utils.showTooltips = function() {
	$('[data-toggle="tooltip"]').tooltip("show");
	Utils.timeoutid = setTimeout(Utils.hideTooltips, 3000);
};

Utils.hideTooltips = function() {
	$('[data-toggle="tooltip"]').tooltip("hide");
	clearInterval(Utils.timeoutid);
};

var docTitle = document.title;
$("body > .container").prepend($("<div></div>").addClass("page-header").append($("<h3>" + docTitle + "</h3>")));
$(document).ready(Utils.showTooltips);

/**
 * Ajax loading.
 */
$(document).ready(function() {
	$(document).on({
		ajaxStart: function() {
			$("#wait").css("visibility", "visible");
		},
		ajaxStop: function() {
			$("#wait").css("visibility", "hidden");
		}
	});
});