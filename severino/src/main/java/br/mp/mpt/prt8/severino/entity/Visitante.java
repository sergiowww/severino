package br.mp.mpt.prt8.severino.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import br.mp.mpt.prt8.severino.validatorgroups.CadastrarVisita;

/**
 * The persistent class for the visitante database table.
 * 
 * @author sergio.eoliveira
 * 
 */
@Entity
@Table(name = "visitante")
public class Visitante implements IEntity<Integer> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_visitante", unique = true, nullable = false)
	@JsonView(DataTablesOutput.View.class)
	private Integer id;

	@Column(nullable = false, length = 20)
	@NotEmpty(groups = CadastrarVisita.class)
	@Size(max = 20, min = 3, groups = CadastrarVisita.class)
	@JsonView(DataTablesOutput.View.class)
	@Pattern(regexp = "[\\dxX]+", message = "Digite apenas números neste campo", groups = CadastrarVisita.class)
	private String documento;

	@Column(name = "orgao_emissor", nullable = false, length = 45)
	@NotEmpty(groups = CadastrarVisita.class)
	@Size(max = 45, groups = CadastrarVisita.class)
	@JsonView(DataTablesOutput.View.class)
	private String orgaoEmissor;

	@Enumerated(EnumType.STRING)
	@Column(length = 2, nullable = false)
	@NotNull(groups = CadastrarVisita.class)
	@JsonView(DataTablesOutput.View.class)
	private Estado uf;

	@Column(nullable = false, length = 200)
	@NotEmpty(groups = CadastrarVisita.class)
	@Size(max = 200, min = 5, groups = CadastrarVisita.class)
	@JsonView(DataTablesOutput.View.class)
	private String nome;

	@Column(length = 120)
	@Size(max = 120, min = 2, groups = CadastrarVisita.class)
	@JsonView(DataTablesOutput.View.class)
	private String profissao;

	public String getOrgaoEmissor() {
		return orgaoEmissor;
	}

	public void setOrgaoEmissor(String orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor;
	}

	public Estado getUf() {
		return uf;
	}

	public void setUf(Estado uf) {
		this.uf = uf;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDocumento() {
		return this.documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the profissao
	 */
	public String getProfissao() {
		return profissao;
	}

	/**
	 * @param profissao
	 *            the profissao to set
	 */
	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

}