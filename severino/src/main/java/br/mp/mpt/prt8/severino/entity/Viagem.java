package br.mp.mpt.prt8.severino.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonView;

import br.mp.mpt.prt8.severino.mediator.intervalodatas.ValidarIntervalo;
import br.mp.mpt.prt8.severino.utils.Constantes;
import br.mp.mpt.prt8.severino.validators.SelecionarMotorista;

/**
 * The persistent class for the viagem database table.
 * 
 */
@Entity
@Table(name = "viagem")
public class Viagem extends AbstractEntity<Integer> {
	private static final long serialVersionUID = -2416567901140832750L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_viagem", unique = true, nullable = false)
	@JsonView(DataTablesOutput.View.class)
	private Integer id;

	@Column(length = 400)
	@Size(max = 400)
	private String anotacao;

	@Column(name = "data_hora_cadastro", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataHoraCadastro;

	// bi-directional many-to-one association to Passageiro
	@OneToMany(mappedBy = "viagem", orphanRemoval = true, cascade = ALL)
	private List<Passageiro> passageiros;

	// uni-directional many-to-one association to Motorista
	@ManyToOne(cascade = { REFRESH, DETACH })
	@JoinColumn(name = "id_motorista", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	@NotNull
	@ConvertGroup(from = Default.class, to = SelecionarMotorista.class)
	@Valid
	private Motorista motorista;

	@ManyToOne(cascade = { PERSIST, MERGE, REMOVE })
	@JoinColumn(name = "id_controle_saida", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	private ControleMotorista controleSaida;

	@ManyToOne(cascade = { PERSIST, MERGE, REMOVE })
	@JoinColumn(name = "id_controle_retorno")
	@JsonView(DataTablesOutput.View.class)
	private ControleMotorista controleRetorno;

	// uni-directional many-to-one association to Veiculo
	@ManyToOne(fetch = FetchType.LAZY, cascade = { PERSIST, MERGE })
	@JoinColumn(name = "placa")
	@JsonView(DataTablesOutput.View.class)
	@Valid
	private Veiculo viatura;

	// uni-directional many-to-one association to Usuario
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", nullable = false, updatable = false)
	@JsonView(DataTablesOutput.View.class)
	private Usuario usuario;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "id_local", nullable = false, updatable = false)
	private Local local;

	@Transient
	private boolean gravarVeiculo = false;

	@Transient
	private boolean registrarSaida;

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

	public void setDataHoraCadastro(Date dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}

	@Transient
	@DateTimeFormat(pattern = Constantes.DATE_TIME_FORMAT)
	@JsonView(DataTablesOutput.View.class)
	@Past(message = ValidarIntervalo.MENSAGEM_DATA_FUTURA)
	public Date getRetorno() {
		if (getControleRetorno() != null) {
			return getControleRetorno().getDataHora();
		}
		return null;
	}

	public void setRetorno(Date retorno) {
		singletonControleRetorno();
		getControleRetorno().setDataHora(retorno);
		getControleRetorno().setFluxo(Fluxo.ENTRADA);
		sincronizarLocal();
	}

	private void singletonControleRetorno() {
		if (getControleRetorno() == null) {
			setControleRetorno(new ControleMotorista(getMotorista()));
		}
	}

	@Transient
	@DateTimeFormat(pattern = Constantes.DATE_TIME_FORMAT)
	@JsonView(DataTablesOutput.View.class)
	@Past(message = ValidarIntervalo.MENSAGEM_DATA_FUTURA)
	public Date getSaida() {
		if (getControleSaida() != null) {
			return getControleSaida().getDataHora();
		}
		return null;
	}

	public void setSaida(Date saida) {
		singletonControleSaida();
		getControleSaida().setDataHora(saida);
		getControleSaida().setFluxo(Fluxo.SAIDA);
		sincronizarLocal();
	}

	private void singletonControleSaida() {
		if (getControleSaida() == null) {
			setControleSaida(new ControleMotorista(getMotorista()));
		}
	}

	/**
	 * Copiar o valor do local para as variáveis de controle.
	 */
	@PrePersist
	public void sincronizarLocal() {
		if (getControleRetorno() != null) {
			getControleRetorno().setLocal(getLocal());
		}
		if (getControleSaida() != null) {
			getControleSaida().setLocal(getLocal());
		}
	}

	public List<Passageiro> getPassageiros() {
		return this.passageiros;
	}

	public void setPassageiros(List<Passageiro> passageiros) {
		this.passageiros = passageiros;
	}

	public Passageiro addPassageiro(Passageiro passageiro) {
		if (getPassageiros() == null) {
			setPassageiros(new ArrayList<>());
		}
		getPassageiros().add(passageiro);
		passageiro.setViagem(this);

		return passageiro;
	}

	public Motorista getMotorista() {
		return this.motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public Veiculo getViatura() {
		return this.viatura;
	}

	public void setViatura(Veiculo veiculo) {
		this.viatura = veiculo;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@AssertFalse(message = "A data de saída não pode ser vazia!")
	public boolean isValidacaoDataEntrada() {

		return getId() != null && getSaida() == null;
	}

	@AssertFalse(message = "A data/hora de entrada não pode estar antes do retorno!")
	public boolean isValidacaoIntervaloEntradaSaida() {
		return getSaida() != null && getRetorno() != null && getRetorno().before(getSaida());
	}

	/**
	 * @return the registrarSaida
	 */
	public boolean isRegistrarSaida() {
		return registrarSaida;
	}

	/**
	 * @param registrarSaida
	 *            the registrarSaida to set
	 */
	public void setRegistrarSaida(boolean registrarSaida) {
		this.registrarSaida = registrarSaida;
	}

	/**
	 * @return the gravarVeiculo
	 */
	public boolean isGravarVeiculo() {
		return gravarVeiculo;
	}

	/**
	 * @param gravarVeiculo
	 *            the gravarVeiculo to set
	 */
	public void setGravarVeiculo(boolean gravarVeiculo) {
		this.gravarVeiculo = gravarVeiculo;
	}

	/**
	 * @return the controleSaida
	 */
	public ControleMotorista getControleSaida() {
		return controleSaida;
	}

	/**
	 * @param controleSaida
	 *            the controleSaida to set
	 */
	public void setControleSaida(ControleMotorista controleSaida) {
		this.controleSaida = controleSaida;
	}

	/**
	 * @return the controleRetorno
	 */
	public ControleMotorista getControleRetorno() {
		return controleRetorno;
	}

	/**
	 * @param controleRetorno
	 *            the controleRetorno to set
	 */
	public void setControleRetorno(ControleMotorista controleRetorno) {
		this.controleRetorno = controleRetorno;
	}

	public void setIdControleSaida(Integer idControleSaida) {
		if (idControleSaida != null) {
			singletonControleSaida();
			getControleSaida().setId(idControleSaida);
		}
	}

	public void setIdControleRetorno(Integer idControleRetorno) {
		if (idControleRetorno != null) {
			singletonControleRetorno();
			getControleRetorno().setId(idControleRetorno);
		}
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

}