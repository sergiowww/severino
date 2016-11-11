package br.mp.mpt.prt8.severino.entity;

import java.util.Date;

import javax.persistence.Transient;

/**
 * Entidade com intervalo de datas para validar.
 * 
 * @author sergio.eoliveira
 *
 */
public abstract class AbstractEntityIntervaloData<ID> extends AbstractEntity<ID> {
	private static final long serialVersionUID = 2284443305748037324L;
	@Transient
	private boolean registrarSaida;

	/**
	 * Data e hora de entrada.
	 * 
	 * @return
	 */
	public abstract Date getEntrada();

	/**
	 * Data e hora de saída.
	 * 
	 * @return
	 */
	public abstract Date getSaida();

	/**
	 * Atribuir a data de entrada.
	 * 
	 * @param entrada
	 */
	public abstract void setEntrada(Date entrada);

	/**
	 * Atribuir a data de saída.
	 * 
	 * @param saida
	 */
	public abstract void setSaida(Date saida);

	/**
	 * Atribuir o usuário.
	 * 
	 * @param usuario
	 */
	public abstract void setUsuario(Usuario usuario);

	/**
	 * Atribuir a data de cadastro.
	 * 
	 * @param dataHoraCadastro
	 */
	public abstract void setDataHoraCadastro(Date dataHoraCadastro);

	/**
	 * @return the registrarSaida
	 */
	public boolean isRegistrarSaida() {
		return registrarSaida;
	}

	/**
	 * @param registrarSaida
	 *            the registrarSaida to set
	 */
	public void setRegistrarSaida(boolean registrarSaida) {
		this.registrarSaida = registrarSaida;
	}

}
