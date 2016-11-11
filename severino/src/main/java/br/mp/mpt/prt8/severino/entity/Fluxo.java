package br.mp.mpt.prt8.severino.entity;

/**
 * Tipo do evento de controle, entrada ou saída do motorista.
 * 
 * @author sergio.eoliveira
 *
 */
public enum Fluxo {
	ENTRADA("Entrada"),

	SAIDA("Saída");

	private String descricao;

	private Fluxo(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Inverter o fluxo.
	 * 
	 * @return
	 */
	public Fluxo inverter() {
		if (this == Fluxo.ENTRADA) {
			return Fluxo.SAIDA;
		}
		return Fluxo.ENTRADA;
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
