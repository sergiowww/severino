package br.mp.mpt.prt8.severino.entity;

import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.FetchType.LAZY;

import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonView;

import br.mp.mpt.prt8.severino.mediator.intervalodatas.ValidarIntervalo;
import br.mp.mpt.prt8.severino.utils.Constantes;
import br.mp.mpt.prt8.severino.validators.SelecionarMotorista;

/**
 * The persistent class for the controle_motorista database table.
 * 
 */
@Entity
@Table(name = "controle_motorista")
public class ControleMotorista extends AbstractEntity<Integer> implements Comparable<ControleMotorista> {
	private static final long serialVersionUID = 2886563297565235407L;
	private static final Comparator<ControleMotorista> COMPARE_ID = Comparator.comparing(ControleMotorista::getMotorista, Comparator.nullsFirst(Comparator.naturalOrder()));

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_controle_motorista", unique = true, nullable = false)
	@JsonView(DataTablesOutput.View.class)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_hora", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	@Past(message = ValidarIntervalo.MENSAGEM_DATA_FUTURA)
	@NotNull
	@DateTimeFormat(pattern = Constantes.DATE_TIME_FORMAT)
	private Date dataHora;

	@Column(nullable = false)
	@Enumerated(ORDINAL)
	@JsonView(DataTablesOutput.View.class)
	@NotNull
	private Fluxo fluxo;

	// uni-directional many-to-one association to Motorista
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_motorista", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	@NotNull
	@Valid
	@ConvertGroup(from = Default.class, to = SelecionarMotorista.class)
	private Motorista motorista;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "id_local", nullable = false, updatable = false)
	private Local local;

	/**
	 * Construtor.
	 * 
	 * @param dataHora
	 * @param fluxo
	 */
	public ControleMotorista(Date dataHora, Fluxo fluxo) {
		super();
		this.dataHora = dataHora;
		this.fluxo = fluxo;
	}

	/**
	 * Construtor.
	 * 
	 * @param motorista
	 */
	public ControleMotorista(Motorista motorista) {
		super();
		this.motorista = motorista;
	}

	/**
	 * Construtor.
	 */
	public ControleMotorista() {
		super();
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataHora() {
		return this.dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public Fluxo getFluxo() {
		return this.fluxo;
	}

	public void setFluxo(Fluxo fluxo) {
		this.fluxo = fluxo;
	}

	public Motorista getMotorista() {
		return this.motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	/**
	 * Verifica se o fluxo � de entrada ou sa�da.
	 * 
	 * @return
	 */
	public boolean isFluxoEntrada() {
		return Fluxo.ENTRADA.equals(fluxo);
	}

	@Override
	public int compareTo(ControleMotorista o) {
		return COMPARE_ID.compare(o, this);
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

}