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
$(document).ready(function() {
	var docTitle = document.title;
	$("body > .container").prepend($("<div></div>").addClass("page-header").append($("<h3>" + docTitle + "</h3>")));
});