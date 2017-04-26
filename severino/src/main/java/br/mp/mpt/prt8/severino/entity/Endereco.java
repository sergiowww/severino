package br.mp.mpt.prt8.severino.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

import br.mp.mpt.prt8.severino.utils.ConversorLimparMascara;
import br.mp.mpt.prt8.severino.utils.StringUtilApp;
import br.mp.mpt.prt8.severino.viewhelpers.PesquisaDoc;

/**
 * The persistent class for the endereco database table.
 * 
 */
@Entity
@Table(name = "endereco")
public class Endereco implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Rua xxx, 545 - Bairro \n Municipio-ES \n cep
	 */
	private static final String FORMATO_DADOS_RESUMO = "%s, %s - %s \n %s - %s \n %s";

	@Id
	private Integer id;

	@Column(nullable = false, length = 80)
	@NotNull
	@Size(min = 2, max = 80)
	@JsonView(PesquisaDoc.class)
	private String bairro;

	@Column(length = 8)
	@Pattern(regexp = "\\d{5}-\\d{3}", message = "o formato do CEP deve ser 00000-000")
	@JsonView(PesquisaDoc.class)
	@Convert(converter = ConversorLimparMascara.class)
	private String cep;

	@Column(length = 50)
	@Size(max = 50)
	@JsonView(PesquisaDoc.class)
	private String complemento;

	@Column(nullable = false, length = 300)
	@Size(min = 2, max = 300)
	@NotNull
	@JsonView(PesquisaDoc.class)
	private String logradouro;

	@Column(nullable = false, length = 80)
	@Size(min = 2, max = 80)
	@NotNull
	@JsonView(PesquisaDoc.class)
	private String municipio;

	@Column(length = 10)
	@Size(max = 10)
	@JsonView(PesquisaDoc.class)
	private String numero;

	@Column(length = 30)
	@Size(max = 30)
	@JsonView(PesquisaDoc.class)
	private String referencia;

	@Enumerated(EnumType.STRING)
	@Column(length = 2, nullable = false)
	@NotNull
	@JsonView(PesquisaDoc.class)
	private Estado uf;

	@OneToOne
	@MapsId
	@JoinColumn(name = "id_visitante", referencedColumnName = "id_visitante")
	private Visitante visitante;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Estado getUf() {
		return this.uf;
	}

	public void setUf(Estado uf) {
		this.uf = uf;
	}

	public Visitante getVisitante() {
		return visitante;
	}

	public void setVisitante(Visitante visitante) {
		this.visitante = visitante;
	}

	@Override
	public String toString() {
		return String.format(FORMATO_DADOS_RESUMO, //
				Objects.toString(logradouro), //
				Objects.toString(numero, "(sem número)"), //
				Objects.toString(bairro), //
				Objects.toString(municipio), //
				Objects.toString(uf), //
				Objects.toString(cep)//
		);
	}

	public boolean algumDadoPreenchido() {
		return StringUtilApp.algumValorPreenchido(bairro, cep, complemento, logradouro, municipio, numero, referencia, uf);
	}

}