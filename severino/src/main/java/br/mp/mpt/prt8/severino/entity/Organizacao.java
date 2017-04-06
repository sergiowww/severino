package br.mp.mpt.prt8.severino.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the organizacao database table.
 * 
 */
@Entity
@Table(name = "organizacao")
public class Organizacao extends AbstractEntity<Integer> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_organizacao", unique = true, nullable = false)
	private Integer id;

	@Column(length = 45, nullable = false, unique = true)
	private String nome;

	/**
	 * Construtor.
	 */
	public Organizacao() {
		super();
	}

	/**
	 * Construtor.
	 * 
	 * @param nome
	 */
	public Organizacao(String nome) {
		super();
		setNome(nome);
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Retorna o nome LDAP
	 * 
	 * @return
	 */
	@Transient
	public String getLdapName() {
		return "ou=" + nome + ",";
	}

}