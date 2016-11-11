<%@ page isELIgnored="true"%>
<%-- este arquivo é lido pelo javascript, ou seja as variáveis ${matricula} e outras são substituídas por um plugin do javascript e não pelo JSP EL --%>
<tr class="passageiroItem">
	<td>
		<input type="hidden" value="${matricula}" name="passageiros[${index}].matricula" />
		<input type="hidden" value="${nome}" name="passageiros[${index}].nome" />
		${matricula}
	</td>
	<td>${nome}</td>
	<td style="text-align: right;">
		<a href="javascript:viagem.removerPassageiro('${nome}');" class="btn btn-xs btn-default">
			<span class="glyphicon glyphicon-remove-circle"></span>
		</a>
	</td>
</tr>