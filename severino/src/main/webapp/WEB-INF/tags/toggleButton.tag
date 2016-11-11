<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="status" description="Indica o estado do toggle true para ativado false para desativado" required="true" type="java.lang.Boolean"%>
<%@ attribute name="label" description="Rótulo do toggle" required="true" type="java.lang.String"%>
<%@ attribute name="tip" description="Dica do campo" required="false" type="java.lang.String"%>

<div class="toggle btn ${status ? 'btn-primary' : 'btn-default off'} btn-xs" title="${tip}">
	<div class="toggle-group">
		<button class="btn btn-primary btn-xs ${not status ? 'active ' : ''}toggle-on" type="submit">
			<i class="glyphicon glyphicon-log-in"></i> ${label}
		</button>
		<button class="btn btn-default btn-xs ${status ? 'active ' : ''}toggle-off" type="submit">
			<i class="glyphicon glyphicon-log-out"></i> ${label}
		</button>
		<span class="toggle-handle btn btn-default btn-xs"></span>
	</div>
</div>