package br.mp.mpt.prt8.severino.entity;

import static javax.persistence.CascadeType.ALL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonView;

import br.mp.mpt.prt8.severino.utils.ConversorLimparMascara;
import br.mp.mpt.prt8.severino.utils.FileUtilsApp;
import br.mp.mpt.prt8.severino.viewhelpers.PesquisaDoc;

/**
 * The persistent class for the visitante database table.
 * 
 * @author sergio.eoliveira
 * 
 */
@Entity
@Table(name = "visitante")
public class Visitante extends AbstractEntity<Integer> {
	private static final long serialVersionUID = 1L;

	private static final String PREFIXO_ARQUIVO = "foto";
	private static final String PADRAO_TELEFONE = "\\(\\d{2}\\) \\d{4,5}-\\d{4}";
	private static final String MENSAGEM_TELEFONE = "O formato do telefone deve ser (##) ####-#### - digite apenas números.";
	private static final String FORMATO_DADOS_RESUMO = "%s (%s %s-%s)";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_visitante", unique = true, nullable = false)
	@JsonView({ DataTablesOutput.View.class, PesquisaDoc.class })
	private Integer id;

	@Column(nullable = false, length = 20)
	@NotEmpty
	@Size(max = 20, min = 3)
	@JsonView({ DataTablesOutput.View.class, PesquisaDoc.class })
	@Pattern(regexp = "[\\dxX]+", message = "Digite apenas números neste campo")
	private String documento;

	@Column(name = "orgao_emissor", nullable = false, length = 45)
	@NotEmpty
	@Size(max = 45)
	@JsonView({ DataTablesOutput.View.class, PesquisaDoc.class })
	private String orgaoEmissor;

	@Enumerated(EnumType.STRING)
	@Column(length = 2, nullable = false)
	@NotNull
	@JsonView({ DataTablesOutput.View.class, PesquisaDoc.class })
	private Estado uf;

	@Column(nullable = false, length = 200)
	@NotNull
	@Size(max = 200, min = 5)
	@JsonView({ DataTablesOutput.View.class, PesquisaDoc.class })
	private String nome;

	@Column(length = 120)
	@Size(max = 120, min = 2)
	@JsonView({ DataTablesOutput.View.class, PesquisaDoc.class })
	private String profissao;

	@Column(length = 11)
	@Pattern(regexp = PADRAO_TELEFONE, message = MENSAGEM_TELEFONE)
	@JsonView({ DataTablesOutput.View.class, PesquisaDoc.class })
	@Convert(converter = ConversorLimparMascara.class)
	private String telefone;

	@Column(name = "telefone_alternativo", length = 11)
	@Pattern(regexp = PADRAO_TELEFONE, message = MENSAGEM_TELEFONE)
	@JsonView(PesquisaDoc.class)
	@Convert(converter = ConversorLimparMascara.class)
	private String telefoneAlternativo;

	@Column(length = 45)
	@Size(max = 45)
	@Email(message = "O e-mail informado é inválido")
	@JsonView(PesquisaDoc.class)
	private String email;

	@OneToOne(orphanRemoval = true, cascade = ALL)
	@PrimaryKeyJoinColumn
	@Valid
	@JsonView(PesquisaDoc.class)
	private Endereco endereco;

	/**
	 * Token para armazenar e recuperar a foto do upload.
	 */
	@Transient
	private String tokenFoto;

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

	/**
	 * Retorna uma descrição com os dados mais importantes.
	 * 
	 * @return
	 */
	@Transient
	public String getDadosResumo() {
		return String.format(FORMATO_DADOS_RESUMO, getNome(), getDocumento(), getOrgaoEmissor(), getUf().name());
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTelefoneAlternativo() {
		return telefoneAlternativo;
	}

	public void setTelefoneAlternativo(String telefoneAlternativo) {
		this.telefoneAlternativo = telefoneAlternativo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
		if (endereco != null) {
			endereco.setVisitante(this);
		}
	}

	public String getTokenFoto() {
		return tokenFoto;
	}

	public void setTokenFoto(String tokenFoto) {
		this.tokenFoto = tokenFoto;
	}

	/**
	 * Gerar um novo token pra foto.
	 */
	@PostLoad
	public void gerarToken() {
		this.tokenFoto = UUID.randomUUID().toString();
	}

	/**
	 * Verifica se a foto está cadastrada para este visitante.
	 * 
	 * @return
	 */
	@Transient
	@JsonView(PesquisaDoc.class)
	public boolean isFotoCadastrada() {
		return getArquivoFinal() != null || getArquivoTemp() != null;
	}

	/**
	 * Retorna a referência para o arquivo recém subido pelo usuário (antes de
	 * clicar no botão salvar).
	 * 
	 * @return
	 */
	public Path getArquivoTemp() {
		if (!StringUtils.isEmpty(tokenFoto)) {
			Path arquivoTemp = FileUtilsApp.getArquivoTemp(tokenFoto);
			return Files.exists(arquivoTemp) ? arquivoTemp : null;
		}
		return null;
	}

	/**
	 * Retorna a referência para o arquivo final, gravado pelo usuário.
	 * 
	 * @return
	 */
	public Path getArquivoFinal() {
		if (id == null) {
			return null;
		}
		Path arquivoFinal = getReferenciaArquivoFinal();
		return Files.exists(arquivoFinal) ? arquivoFinal : null;
	}

	/**
	 * Retorna uma referência ao arquivo.
	 * 
	 * @return
	 */
	public Path getReferenciaArquivoFinal() {
		return Paths.get(FileUtilsApp.getDiretorioFotos().toString(), getNomeArquivoFoto());
	}

	/**
	 * Retorna o nome do arquivo final da foto.
	 * 
	 * @return
	 */
	@Transient
	public String getNomeArquivoFoto() {
		return PREFIXO_ARQUIVO + id + FileUtilsApp.EXTENSAO_PADRAO;
	}
}
