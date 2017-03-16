package br.mp.mpt.prt8.severino.valueobject;

import java.util.Comparator;
import java.util.Date;

import br.mp.mpt.prt8.severino.entity.FonteDisponibilidade;

/**
 * Disponibilidade pessoa.
 * 
 * @author sergio.eoliveira
 *
 */
public class PessoaDisponibilidade implements Comparable<PessoaDisponibilidade> {

	private static final Comparator<PessoaDisponibilidade> COMPARE_PESSOA = Comparator.comparing(PessoaDisponibilidade::getNome)
			.thenComparing(PessoaDisponibilidade::getEntrada, Comparator.nullsLast(Comparator.naturalOrder()))
			.thenComparing(PessoaDisponibilidade::getSaida, Comparator.nullsLast(Comparator.naturalOrder()));

	private Integer id;
	private String nome;
	private Date entrada;
	private Date saida;
	private FonteDisponibilidade fonte;

	/**
	 * Construtor.
	 * 
	 * @param id
	 * @param nome
	 * @param entrada
	 * @param saida
	 */
	public PessoaDisponibilidade(Integer id, String nome, Date entrada, Date saida) {
		super();
		setId(id);
		setNome(nome);
		setEntrada(entrada);
		setSaida(saida);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getEntrada() {
		return entrada;
	}

	public void setEntrada(Date entrada) {
		this.entrada = entrada;
	}

	public Date getSaida() {
		return saida;
	}

	public void setSaida(Date saida) {
		this.saida = saida;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FonteDisponibilidade getFonte() {
		return fonte;
	}

	public void setFonte(FonteDisponibilidade fonte) {
		this.fonte = fonte;
	}

	@Override
	public int compareTo(PessoaDisponibilidade o) {
		return COMPARE_PESSOA.compare(this, o);
	}

	/**
	 * Indica se esta pessoa entrou ou saiu das dependências da PRT.
	 * 
	 * @return
	 */
	public boolean isEntrou() {
		if (saida == null) {
			return true;
		}
		if (entrada == null) {
			return false;
		}
		return getSaida().before(getEntrada());
	}

	/**
	 * Retorna a data do evento que ocorreu.
	 * 
	 * @return
	 */
	public Date getDataEvento() {
		if (isEntrou()) {
			return getEntrada();
		}
		return getSaida();
	}

	@Override
	public String toString() {
		return "PessoaDisponibilidade [nome=" + nome + ", entrada=" + entrada + ", saida=" + saida + "]";
	}

}
