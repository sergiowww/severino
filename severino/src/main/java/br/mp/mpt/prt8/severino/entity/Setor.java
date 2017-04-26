package br.mp.mpt.prt8.severino.entity;

import static javax.persistence.FetchType.LAZY;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import br.mp.mpt.prt8.severino.validators.SelecionarSetor;

/**
 * The persistent class for the setor database table.
 * 
 * @author sergio.eoliveira
 * 
 */
@Entity
@Table(name = "setor")
public class Setor extends AbstractEntity<Integer> {
	private static final long serialVersionUID = 1L;

	private static final String DESCRICAO_TEMPLATE = "%dº - %s %s";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_setor", unique = true, nullable = false)
	@JsonView(DataTablesOutput.View.class)
	@NotNull(groups = SelecionarSetor.class)
	private Integer id;

	@JsonView(DataTablesOutput.View.class)
	@Digits(fraction = 0, integer = 5, message = "Este campo aceita somente números!")
	@NotNull
	private Short andar;

	@Column(nullable = false, length = 45)
	@Size(max = 45)
	@NotEmpty
	@JsonView(DataTablesOutput.View.class)
	private String nome;

	@Column(length = 5)
	@JsonView(DataTablesOutput.View.class)
	@Size(max = 5)
	private String sala;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "id_local", nullable = false, updatable = false)
	@JsonView(DataTablesOutput.View.class)
	private Local local;

	@Override
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Short getAndar() {
		return this.andar;
	}

	public void setAndar(Short andar) {
		this.andar = andar;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSala() {
		return this.sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	/**
	 * Descrição completa do setor.
	 * 
	 * @return
	 */
	@Transient
	public String getDescricaoCompleta() {
		return String.format(DESCRICAO_TEMPLATE, getAndar(), getNome(), Objects.toString(getSala(), ""));
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}
}