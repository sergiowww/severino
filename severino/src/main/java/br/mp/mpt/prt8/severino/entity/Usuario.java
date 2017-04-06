package br.mp.mpt.prt8.severino.entity;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * The persistent class for the usuario database table.
 * 
 * @author sergio.eoliveira
 * 
 */
@Entity
@Table(name = "usuario")
public class Usuario extends AbstractEntity<String> {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_usuario", unique = true, nullable = false, length = 20)
	@JsonView(DataTablesOutput.View.class)
	private String id;

	@Column(length = 200, nullable = false)
	@JsonView(DataTablesOutput.View.class)
	private String nome;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "id_local", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	private Local local;

	@Override
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Usuario)) {
			return false;
		}
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

}