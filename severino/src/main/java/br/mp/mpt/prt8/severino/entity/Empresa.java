package br.mp.mpt.prt8.severino.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * The persistent class for the empresa database table.
 * 
 * @author sergio.eoliveira
 *
 */
@Entity
@Table(name = "empresa")
public class Empresa extends AbstractEntity<Integer> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_empresa", unique = true, nullable = false)
	@JsonView(DataTablesOutput.View.class)
	private Integer id;

	@NotEmpty
	@Size(max = 45)
	@Column(nullable = false, length = 45)
	@JsonView(DataTablesOutput.View.class)
	private String nome;

	@Override
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}