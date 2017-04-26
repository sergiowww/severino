package br.mp.mpt.prt8.severino.entity;

import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.FetchType.LAZY;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import br.mp.mpt.prt8.severino.validators.SelecionarLocal;
import br.mp.mpt.prt8.severino.validators.SelecionarMotorista;

/**
 * The persistent class for the motorista database table.
 * 
 */
@Entity
@Table(name = "motorista")
public class Motorista extends AbstractEntity<Integer> implements Comparable<Motorista> {
	private static final long serialVersionUID = -7425879325275604313L;
	private static final Comparator<Motorista> COMPARE_ID = Comparator.comparing(Motorista::getId, Comparator.nullsFirst(Comparator.naturalOrder()));

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_motorista", unique = true, nullable = false)
	@JsonView(DataTablesOutput.View.class)
	@NotNull(groups = SelecionarMotorista.class)
	private Integer id;

	@Column(nullable = false, length = 9, unique = true)
	@JsonView(DataTablesOutput.View.class)
	@NotNull
	@Pattern(regexp = "\\d{3,}-[0-9xX]{1}", message = "O formato da matrícula está inválido, digite a matrícula com no formato 999-9")
	@Size(max = 9)
	private String matricula;

	@Column(nullable = false, length = 200)
	@JsonView(DataTablesOutput.View.class)
	@NotNull
	@Size(min = 5, max = 200)
	private String nome;

	@Column(nullable = false, name = "tipo")
	@Enumerated(ORDINAL)
	@NotNull
	private Cargo cargo;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "id_local", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	@NotNull
	@ConvertGroup(from = Default.class, to = SelecionarLocal.class)
	@Valid
	private Local local;

	/**
	 * Construtor.
	 */
	public Motorista() {
		super();
	}

	/**
	 * Construtor.
	 * 
	 * @param id
	 */
	public Motorista(Integer id) {
		super();
		this.id = id;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cargo getCargo() {
		return this.cargo;
	}

	public void setCargo(Cargo tipo) {
		this.cargo = tipo;
	}

	@Override
	public int compareTo(Motorista o) {
		return COMPARE_ID.compare(o, this);
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	/**
	 * Descrição do cargo.
	 * 
	 * @return
	 */
	@Transient
	@JsonView(DataTablesOutput.View.class)
	public String getCargoDescricao() {
		if (cargo != null) {
			return cargo.getDescricao();
		}
		return null;
	}

}