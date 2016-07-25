package br.mp.mpt.prt8.severino.utils;

/**
 * Exceção de negócio.
 * 
 * @author sergio.eoliveira
 *
 */
public class NegocioException extends RuntimeException {
	private static final long serialVersionUID = -4388539242329291729L;

	/**
	 * Construtor.
	 * 
	 * @param message
	 */
	public NegocioException(String message) {
		super(message);
	}

}
