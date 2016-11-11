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
	private String matricula;
	private String tipo;

	public String getNome() {
		return nome;
	}

	public void setDisplayName(String nome) {
		this.nome = nome;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartment(String departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * @param matricula
	 *            the matricula to set
	 */
	public void setEmployeeID(String matricula) {
		this.matricula = matricula;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setEmployeeType(String tipo) {
		this.tipo = tipo;
	}
}
