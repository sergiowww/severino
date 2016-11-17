package br.mp.mpt.prt8.severino.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The persistent class for the passageiro database table.
 * 
 */
@Entity
@Table(name = "passageiro", uniqueConstraints = @UniqueConstraint(columnNames = { "nome", "id_viagem" }))
public class Passageiro extends AbstractEntity<PassageiroPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PassageiroPK id;

	@Column(length = 9)
	@JsonView(DataTablesOutput.View.class)
	private String matricula;

	// bi-directional many-to-one association to Viagem
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_viagem", nullable = false, insertable = false, updatable = false)
	@MapsId("idViagem")
	private Viagem viagem;

	@Override
	public PassageiroPK getId() {
		return this.id;
	}

	public void setId(PassageiroPK id) {
		this.id = id;
	}

	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String documentoMatricula) {
		this.matricula = documentoMatricula;
	}

	public Viagem getViagem() {
		return this.viagem;
	}

	public void setViagem(Viagem viagem) {
		this.viagem = viagem;
		if (viagem.getId() != null) {
			this.id.setIdViagem(viagem.getId());
		}
	}

	/**
	 * @see PassageiroPK#setNome(String)
	 * @param nome
	 */
	public void setNome(String nome) {
		if (getId() == null) {
			setId(new PassageiroPK());
		}
		getId().setNome(nome);
	}

	/**
	 * @see PassageiroPK#getNome()
	 * @return
	 */
	@JsonView(DataTablesOutput.View.class)
	public String getNome() {
		if (getId() != null) {
			return getId().getNome();
		}
		return null;
	}

	/**
	 * Retorna a representação json.
	 * 
	 * @return
	 * @throws JsonProcessingException
	 */
	@Transient
	public String getJson() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> mapa = new HashMap<>();
		mapa.put("matricula", matricula);
		mapa.put("nome", getNome());
		return objectMapper.writeValueAsString(mapa);
	}

}