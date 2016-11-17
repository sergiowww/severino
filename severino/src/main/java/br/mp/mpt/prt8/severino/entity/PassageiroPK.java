package br.mp.mpt.prt8.severino.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the passageiro database table.
 * 
 */
@Embeddable
public class PassageiroPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 200)
	private String nome;

	@Column(name = "id_viagem", insertable = false, updatable = false, nullable = false)
	private int idViagem;

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdViagem() {
		return this.idViagem;
	}

	public void setIdViagem(int idViagem) {
		this.idViagem = idViagem;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PassageiroPK)) {
			return false;
		}
		PassageiroPK castOther = (PassageiroPK) other;
		return this.nome.equals(castOther.nome) && (this.idViagem == castOther.idViagem);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.nome.hashCode();
		hash = hash * prime + this.idViagem;

		return hash;
	}
}