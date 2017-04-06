package br.mp.mpt.prt8.severino.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import br.mp.mpt.prt8.severino.validators.CadastrarMotorista;

/**
 * The persistent class for the local database table.
 * 
 */
@Entity
@Table(name = "local", uniqueConstraints = @UniqueConstraint(columnNames = { "nome", "id_organizacao" }))
public class Local extends AbstractEntity<Integer> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_local", unique = true, nullable = false)
	@NotNull(groups = CadastrarMotorista.class)
	private Integer id;

	@Column(nullable = false, length = 45)
	private String nome;

	// uni-directional many-to-one association to Organizacao
	@ManyToOne
	@JoinColumn(name = "id_organizacao", nullable = false)
	private Organizacao organizacao;

	/**
	 * Construtor.
	 */
	public Local() {
		super();
	}

	/**
	 * Construtor.
	 * 
	 * @param nome
	 */
	public Local(String nome) {
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

	public Organizacao getOrganizacao() {
		return this.organizacao;
	}

	public void setOrganizacao(Organizacao organizacao) {
		this.organizacao = organizacao;
	}

	/**
	 * Descrição do local.
	 * 
	 * @return
	 */
	@Transient
	@JsonView(DataTablesOutput.View.class)
	public String getTitulo() {
		return getOrganizacao().getNome() + " / " + nome;
	}
}