package br.mp.mpt.prt8.severino.entity;

import static javax.persistence.EnumType.ORDINAL;

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

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonView;

import br.mp.mpt.prt8.severino.utils.Constantes;
import br.mp.mpt.prt8.severino.validators.PastOrPresent;

/**
 * The persistent class for the controle_motorista database table.
 * 
 */
@Entity
@Table(name = "controle_motorista")
public class ControleMotorista extends AbstractEntity<Integer> implements Comparable<ControleMotorista> {
	private static final long serialVersionUID = 2886563297565235407L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_controle_motorista", unique = true, nullable = false)
	@JsonView(DataTablesOutput.View.class)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_hora", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	@PastOrPresent(message = "Não pode ser uma data futura")
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
	private Motorista motorista;

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
	 * Verifica se o fluxo é de entrada ou saída.
	 * 
	 * @return
	 */
	public boolean isFluxoEntrada() {
		return Fluxo.ENTRADA.equals(fluxo);
	}

	@Override
	public int compareTo(ControleMotorista o) {
		Integer id2 = o.getMotorista().getId();
		Integer id1 = motorista.getId();
		return id1.compareTo(id2);
	}

}