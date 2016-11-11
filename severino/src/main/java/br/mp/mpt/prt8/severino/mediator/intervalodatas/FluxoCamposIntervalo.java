package br.mp.mpt.prt8.severino.mediator.intervalodatas;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mp.mpt.prt8.severino.entity.AbstractEntityIntervaloData;
import br.mp.mpt.prt8.severino.mediator.UsuarioHolder;

/**
 * Mediator para atribuir os campos padrões para as entidades com intervalo de
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
	 * Ajustar valores e atribuir campos conforme as condições.
	 * 
	 * @param entidade
	 */
	public void setCamposIniciais(AbstractEntityIntervaloData<?> entidade) {
		Boolean registrarSaida = entidade.isRegistrarSaida();
		if (registrarSaida) {
			entidade.setSaida(new Date());
		}
		if (entidade.getId() == null) {
			entidade.setDataHoraCadastro(new Date());
			if (entidade.getEntrada() == null) {
				entidade.setEntrada(new Date());
			}
		}
		entidade.setUsuario(Objects.requireNonNull(usuarioHolder.getUsuario()));

	}
}
