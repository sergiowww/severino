<%@ page isELIgnored="true"%>
<%-- este arquivo � lido pelo javascript, ou seja as vari�veis ${matricula} e outras s�o substitu�das por um plugin do javascript e n�o pelo JSP EL --%>
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