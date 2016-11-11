package br.mp.mpt.prt8.severino.entity;

import static br.mp.mpt.prt8.severino.mediator.intervalodatas.ValidarIntervalo.VALIDACAO_DATA_ENTRADA;
import static br.mp.mpt.prt8.severino.mediator.intervalodatas.ValidarIntervalo.VALIDACAO_INTERVALO_ENTRADA_SAIDA;
import static br.mp.mpt.prt8.severino.mediator.intervalodatas.ValidarIntervalo.isDataEntradaValida;
import static br.mp.mpt.prt8.severino.mediator.intervalodatas.ValidarIntervalo.isIntervaloEntradaSaidaValido;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonView;

import br.mp.mpt.prt8.severino.utils.Constantes;
import br.mp.mpt.prt8.severino.utils.DateUtils;
import br.mp.mpt.prt8.severino.validators.SelecionarVisita;

/**
 * The persistent class for the acesso_garagem database table.
 * 
 */
@Entity
@Table(name = "acesso_garagem")
public class AcessoGaragem extends AbstractEntityIntervaloData<Integer> {
	private static final long serialVersionUID = -8836753558231868937L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_acesso_garagem", unique = true, nullable = false)
	@JsonView(DataTablesOutput.View.class)
	private Integer id;

	@Column(length = 400)
	@Size(max = 400)
	private String anotacao;

	@Column(name = "data_hora_cadastro", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataHoraCadastro;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	@DateTimeFormat(pattern = Constantes.DATE_TIME_FORMAT)
	@JsonView(DataTablesOutput.View.class)
	@Past(message = "Não pode ser uma data futura")
	private Date entrada;

	@Temporal(TemporalType.TIMESTAMP)
	@Past(message = "Não pode ser uma data futura")
	@DateTimeFormat(pattern = Constantes.DATE_TIME_FORMAT)
	@JsonView(DataTablesOutput.View.class)
	private Date saida;

	// uni-directional many-to-one association to Motorista
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_motorista")
	@JsonView(DataTablesOutput.View.class)
	private Motorista motorista;

	// uni-directional many-to-one association to Veiculo
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "placa", nullable = false)
	@Valid
	@NotNull
	private Veiculo veiculo;

	// uni-directional many-to-one association to Usuario
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", nullable = false, updatable = false)
	private Usuario usuario;

	// uni-directional many-to-one association to Visita
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_visita")
	@NotNull(groups = SelecionarVisita.class)
	@Valid
	private Visita visita;

	@Transient
	private boolean usuarioVisitante;

	@AssertFalse(message = VALIDACAO_DATA_ENTRADA)
	public boolean isValidacaoDataEntrada() {
		return isDataEntradaValida(this);
	}

	@AssertFalse(message = VALIDACAO_INTERVALO_ENTRADA_SAIDA)
	public boolean isValidacaoIntervaloEntradaSaida() {
		return isIntervaloEntradaSaidaValido(this);
	}

	@Transient
	public LocalDate getEntradaAsLocalDate() {
		return DateUtils.toLocalDate(getEntrada());
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnotacao() {
		return this.anotacao;
	}

	public void setAnotacao(String anotacao) {
		this.anotacao = anotacao;
	}

	public Date getDataHoraCadastro() {
		return this.dataHoraCadastro;
	}

	@Override
	public void setDataHoraCadastro(Date dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}

	@Override
	public Date getEntrada() {
		return this.entrada;
	}

	@Override
	public void setEntrada(Date entrada) {
		this.entrada = entrada;
	}

	@Override
	public Date getSaida() {
		return this.saida;
	}

	@Override
	public void setSaida(Date saida) {
		this.saida = saida;
	}

	public Motorista getMotorista() {
		return this.motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public Veiculo getVeiculo() {
		return this.veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	@Override
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Visita getVisita() {
		return this.visita;
	}

	public void setVisita(Visita visita) {
		this.visita = visita;
	}

	/**
	 * @return the usuarioVisitante
	 */
	public boolean isUsuarioVisitante() {
		if (getMotorista() != null) {
			return usuarioVisitante = false;
		}
		if (getVisita() != null) {
			return usuarioVisitante = true;
		}
		return usuarioVisitante;
	}

	/**
	 * @param usuarioVisitante
	 *            the usuarioVisitante to set
	 */
	public void setUsuarioVisitante(boolean usuarioVisitante) {
		this.usuarioVisitante = usuarioVisitante;
	}

}