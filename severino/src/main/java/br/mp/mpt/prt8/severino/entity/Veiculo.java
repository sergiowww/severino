package br.mp.mpt.prt8.severino.entity;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import br.mp.mpt.prt8.severino.validators.CadastrarVeiculo;
import br.mp.mpt.prt8.severino.validators.SelecionarVeiculo;

/**
 * The persistent class for the veiculo database table.
 * 
 */
@Entity
@Table(name = "veiculo")
public class Veiculo extends AbstractEntity<String> {

	private static final long serialVersionUID = 4430369112378957922L;
	private static final String FORMATO_DESCRICAO = "%s - %s %s (%s)";
	private static final String FORMATO_MOTORISTA = "%s - " + FORMATO_DESCRICAO;

	@Id
	@Column(name = "placa", unique = true, nullable = false, length = 7)
	@NotNull(groups = { CadastrarVeiculo.class, SelecionarVeiculo.class })
	@Pattern(regexp = "[A-Z]{3}\\d{4}", message = "O formato da placa deve ser xxx0000", groups = CadastrarVeiculo.class)
	@JsonView(DataTablesOutput.View.class)
	private String id;

	@Column(nullable = false, length = 10)
	@NotNull(groups = CadastrarVeiculo.class)
	@Size(max = 10, groups = CadastrarVeiculo.class)
	@JsonView(DataTablesOutput.View.class)
	private String cor;

	@Column(nullable = false, length = 30)
	@NotNull(groups = CadastrarVeiculo.class)
	@Size(max = 30, groups = CadastrarVeiculo.class)
	@JsonView(DataTablesOutput.View.class)
	private String marca;

	@Column(nullable = false, length = 30)
	@Size(max = 30, groups = CadastrarVeiculo.class)
	@NotNull(groups = CadastrarVeiculo.class)
	@JsonView(DataTablesOutput.View.class)
	private String modelo;

	@Column(name = "viatura_mp", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	private boolean viaturaMp;

	@Column(nullable = false)
	@JsonView(DataTablesOutput.View.class)
	private boolean ativo = true;

	// bi-directional many-to-one association to Motorista
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_motorista")
	@JsonView(DataTablesOutput.View.class)
	private Motorista motorista;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "id_local", nullable = false, updatable = false)
	private Local local;

	@Override
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCor() {
		return this.cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public boolean getViaturaMp() {
		return this.viaturaMp;
	}

	public void setViaturaMp(boolean viaturaMp) {
		this.viaturaMp = viaturaMp;
	}

	public Motorista getMotorista() {
		return this.motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	/**
	 * Descrição completa do veículo.
	 * 
	 * @return
	 */
	public String getDescricaoCompleta() {
		return String.format(FORMATO_DESCRICAO, getId(), getMarca(), getModelo(), getCor());
	}

	/**
	 * Descrição completa do veículo com motorista.
	 * 
	 * @return
	 */
	public String getDescricaoMotorista() {
		String nomeMotorista = "";
		if (getMotorista() != null) {
			nomeMotorista = getMotorista().getNome();
		}
		return String.format(FORMATO_MOTORISTA, nomeMotorista, getId(), getMarca(), getModelo(), getCor());
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

}