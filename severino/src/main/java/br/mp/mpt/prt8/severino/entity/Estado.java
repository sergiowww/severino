package br.mp.mpt.prt8.severino.entity;

/**
 * Estados.
 * 
 * @author sergio.eoliveira
 *
 */
public enum Estado {
	AC("Acre"), //
	AL("Alagoas"), //
	AP("Amap�"), //
	AM("Amazonas"), //
	BA("Bahia"), //
	CE("Cear�"), //
	DF("Distrito Federal"), //
	ES("Esp�rito Santo"), //
	GO("Goi�s"), //
	MA("Maranh�o"), //
	MT("Mato Grosso"), //
	MS("Mato Grosso do Sul"), //
	MG("Minas Gerais"), //
	PA("Par�"), //
	PB("Para�ba"), //
	PR("Paran�"), //
	PE("Pernambuco"), //
	PI("Piau�"), //
	RJ("Rio de Janeiro"), //
	RN("Rio Grande do Norte"), //
	RS("Rio Grande do Sul"), //
	RO("Rond�nia"), //
	RR("Roraima"), //
	SC("Santa Catarina"), //
	SP("S�o Paulo"), //
	SE("Sergipe"), //
	TO("Tocantins"), //
	EX("Fora do Brasil");
	private String nome;

	private Estado(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return this.nome;
	}

	public String getName() {
		return name();
	}
}
