package br.mp.mpt.prt8.severino.valueobject;

/**
 * Objeto mapeado do LDAP.
 * 
 * @author sergio.eoliveira
 *
 */
public class PessoaLdap {
	private String nome;
	private String departamento;

	public PessoaLdap(String nome, String departamento) {
		super();
		this.nome = nome;
		this.departamento = departamento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
}
