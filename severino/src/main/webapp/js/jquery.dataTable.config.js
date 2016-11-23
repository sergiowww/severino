/**
 * Configuração do jquery datatable.
 */
var CesDataTableUtils = {};
CesDataTableUtils.actionButtons = function(row, contextPath, urlEdit, urlRemove) {
	var editButton = "<a class=\"btn btn-default actionButton\" href=\"" + contextPath + urlEdit + row.id + "\"><span class=\"glyphicon glyphicon-edit\"></span></a> ";
	var deleteButton = "";
	if (urlRemove != "") {
		deleteButton = "<a class=\"btn btn-default actionButton\" href=\"javascript:CesDataTableUtils.confirmBeforeDelete('" + urlRemove + row.id + "')\"><span class=\"glyphicon glyphicon-trash\"></span></a>";
	}
	return editButton + deleteButton;
};
CesDataTableUtils.confirmBeforeDelete = function(urlRemove) {
	bootbox.confirm("Deseja apagar este registro?", function(result) {
		if (result) {
			var contextPath = Utils.getContextPath();
			document.location.href = contextPath + urlRemove;
		}
	});
};
CesDataTableUtils.displayDataTableOnReady = function(urlJsonList, urlEdit, urlRemove, columnProperties) {
	var contextPath = Utils.getContextPath();
	$(document).ready(CesDataTableUtils.createDataTable.bind(CesDataTableUtils, contextPath, urlJsonList, urlEdit, urlRemove, columnProperties));
};
CesDataTableUtils.createDataTable = function(contextPath, urlJsonList, urlEdit, urlRemove, columnProperties) {
	var propriedadesValue = columnProperties.split(",");
	var columns = new Array();
	for (var i = 0; i < propriedadesValue.length; i++) {
		var propValue = propriedadesValue[i];
		var columnData = {};
		if (propValue.indexOf(":") != -1) {
			var columnMeta = propValue.split(":");
			propValue = columnMeta[0];
			type = columnMeta[1];
			switch (type) {
			case "date":
				columnData.render = function(data, type, row) {
					if (data != null) {
						var date = new Date(data);
						return date.toLocaleString();
					}
					return null;
				};
				break;

			case "sim_nao":
				columnData.render = function(data, type, row) {
					return data ? "Sim" : "N�o";
				};
				break;
			}
		}
		columnData.data = propValue;
		columnData.defaultContent = "";
		columns.push(columnData);

	}
	var actionColumn = {};
	actionColumn.width = "70px";
	actionColumn.sortable = false;
	actionColumn.render = function(data, type, row) {
		return CesDataTableUtils.actionButtons(row, contextPath, urlEdit, urlRemove);
	};
	columns.push(actionColumn);

	var configObject = {};
	configObject.columns = columns;
	configObject.processing = true;
	configObject.serverSide = true;
	configObject.ajax = {
		"contentType": "application/json",
		"url": contextPath + urlJsonList,
		"type": "POST",
		"data": function(d) {
			return JSON.stringify(d);
		}
	};
	configObject.language = {
		url: contextPath + "/js/datatable.lang.pt-BR.json"
	}

	$("#tableRecordSet").DataTable(configObject);
};
