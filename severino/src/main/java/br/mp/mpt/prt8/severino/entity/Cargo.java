package br.mp.mpt.prt8.severino.entity;

/**
 * Tipo do servidor.
 * 
 * @author sergio.eoliveira
 *
 */
public enum Cargo {
	PROCURADOR("Procurador"),

	SERVIDOR("Servidor"),

	MOTORISTA("Técnico de Transporte");

	private String descricao;

	private Cargo(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	public String getName() {
		return name();
	}

}
