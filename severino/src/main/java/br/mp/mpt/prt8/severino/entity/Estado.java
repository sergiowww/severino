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
	AP("Amapá"), //
	AM("Amazonas"), //
	BA("Bahia"), //
	CE("Ceará"), //
	DF("Distrito Federal"), //
	ES("Espírito Santo"), //
	GO("Goiás"), //
	MA("Maranhão"), //
	MT("Mato Grosso"), //
	MS("Mato Grosso do Sul"), //
	MG("Minas Gerais"), //
	PA("Pará"), //
	PB("Paraíba"), //
	PR("Paraná"), //
	PE("Pernambuco"), //
	PI("Piauí"), //
	RJ("Rio de Janeiro"), //
	RN("Rio Grande do Norte"), //
	RS("Rio Grande do Sul"), //
	RO("Rondônia"), //
	RR("Roraima"), //
	SC("Santa Catarina"), //
	SP("São Paulo"), //
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
