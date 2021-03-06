package br.mp.mpt.prt8.severino.mediator.intervalodatas;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mp.mpt.prt8.severino.entity.AbstractEntityIntervaloData;
import br.mp.mpt.prt8.severino.entity.Usuario;
import br.mp.mpt.prt8.severino.mediator.UsuarioHolder;
import br.mp.mpt.prt8.severino.utils.DateUtils;

/**
 * Mediator para atribuir os campos padr�es para as entidades com intervalo de
 * datas.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class FluxoCamposIntervalo {

	@Autowired
	private UsuarioHolder usuarioHolder;

	/**
	 * Ajustar valores e atribuir campos conforme as condi��es.
	 * 
	 * @param entidade
	 */
	public void setCamposIniciais(AbstractEntityIntervaloData<?> entidade) {
		Boolean registrarSaida = entidade.isRegistrarSaida();
		if (registrarSaida) {
			entidade.setSaida(DateUtils.getDataHoraAtual());
		}
		if (entidade.getId() == null) {
			entidade.setDataHoraCadastro(new Date());
			if (entidade.getEntrada() == null) {
				entidade.setEntrada(DateUtils.getDataHoraAtual());
			}
		}
		Usuario usuario = usuarioHolder.getUsuario();
		entidade.setUsuario(Objects.requireNonNull(usuario));
		entidade.setLocal(Objects.requireNonNull(usuario.getLocal()));
	}
}
