package br.mp.mpt.prt8.severino.entity;

import static javax.persistence.TemporalType.TIMESTAMP;

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

import br.mp.mpt.prt8.severino.validatorgroups.CadastrarVisita;

/**
 * The persistent class for the visita database table.
 * 
 * @author sergio.eoliveira
 * 
 */
@Entity
@Table(name = "visita")
public class Visita implements IEntity<Integer> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_visita", unique = true, nullable = false)
	@JsonView(DataTablesOutput.View.class)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@JsonView(DataTablesOutput.View.class)
	@Past(message = "Não pode ser uma data futura", groups = CadastrarVisita.class)
	private Date entrada;

	@Column(name = "nome_procurado", length = 45)
	@Size(max = 45, groups = CadastrarVisita.class)
	@JsonView(DataTablesOutput.View.class)
	private String nomeProcurado;

	@Column(name = "setor_procurado", length = 200)
	@Size(max = 200, groups = CadastrarVisita.class)
	@JsonView(DataTablesOutput.View.class)
	private String setorProcurado;

	@Column(name = "data_hora_cadastro", nullable = false, updatable = false)
	@Temporal(TIMESTAMP)
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataHoraCadastro;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@JsonView(DataTablesOutput.View.class)
	@Past(message = "Não pode ser uma data futura", groups = CadastrarVisita.class)
	private Date saida;

	// uni-directional many-to-one association to Empresa
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empresa", nullable = true)
	@JsonView(DataTablesOutput.View.class)
	private Empresa empresa;

	// uni-directional many-to-one association to Setor
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_setor", nullable = false)
	@NotNull(groups = CadastrarVisita.class)
	@JsonView(DataTablesOutput.View.class)
	@Valid
	private Setor setor;

	// uni-directional many-to-one association to Usuario
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", nullable = false, updatable = false)
	@JsonView(DataTablesOutput.View.class)
	private Usuario usuario;

	// uni-directional many-to-one association to Visitante
	@ManyToOne
	@JoinColumn(name = "id_visitante", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	@Valid
	private Visitante visitante;

	@Transient
	private boolean registrarSaida;

	public Date getDataHoraCadastro() {
		return dataHoraCadastro;
	}

	public void setDataHoraCadastro(Date dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}

	public String getSetorProcurado() {
		return setorProcurado;
	}

	public void setSetorProcurado(String setorProcurado) {
		this.setorProcurado = setorProcurado;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getEntrada() {
		return this.entrada;
	}

	public void setEntrada(Date entrada) {
		this.entrada = entrada;
	}

	public String getNomeProcurado() {
		return this.nomeProcurado;
	}

	public void setNomeProcurado(String nomeProcurado) {
		this.nomeProcurado = nomeProcurado;
	}

	public Date getSaida() {
		return this.saida;
	}

	public void setSaida(Date saida) {
		this.saida = saida;
	}

	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Setor getSetor() {
		return this.setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Visitante getVisitante() {
		return this.visitante;
	}

	public void setVisitante(Visitante visitante) {
		this.visitante = visitante;
	}

	@AssertFalse(message = "A data de entrada não pode ser vazia!", groups = CadastrarVisita.class)
	public boolean isValidacaoDataEntrada() {

		return getId() != null && getEntrada() == null;
	}

	@AssertFalse(message = "A data/hora de saída não pode estar antes da entrada!", groups = CadastrarVisita.class)
	public boolean isValidacaoIntervaloEntradaSaida() {
		return getSaida() != null && getEntrada() != null && getSaida().before(getEntrada());
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

}