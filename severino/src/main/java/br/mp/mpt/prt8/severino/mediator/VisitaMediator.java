package br.mp.mpt.prt8.severino.mediator;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.LdapRepository;
import br.mp.mpt.prt8.severino.dao.VisitaRepository;
import br.mp.mpt.prt8.severino.entity.Empresa;
import br.mp.mpt.prt8.severino.entity.Visita;
import br.mp.mpt.prt8.severino.entity.Visitante;
import br.mp.mpt.prt8.severino.utils.NegocioException;
import br.mp.mpt.prt8.severino.valueobject.PessoaLdap;

/**
 * Mediador de operações.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class VisitaMediator extends AbstractMediator<Visita, Integer> {

	@Autowired
	private VisitaRepository visitaRepository;

	@Autowired
	private VisitanteMediator visitanteMediator;

	@Autowired
	private EmpresaMediator empresaMediator;

	@Autowired
	private UsuarioHolder usuarioHolder;

	@Autowired
	private LdapRepository ldapRepository;

	@Override
	protected BaseRepositorySpecification<Visita, Integer> repositoryBean() {
		return visitaRepository;
	}

	@Override
	protected Visita getExampleForSearching(String searchValue) {
		Visita probe = new Visita();
		probe.setNomeProcurado(searchValue);
		Visitante visitante = new Visitante();
		visitante.setNome(searchValue);
		probe.setVisitante(visitante);
		return probe;
	}

	@Transactional
	@Override
	public Visita save(Visita visita) {
		Boolean registrarSaida = visita.isRegistrarSaida();
		if (registrarSaida) {
			visita.setSaida(new Date());
		}
		boolean cadastro = visita.getId() == null;
		if (cadastro) {
			visita.setDataHoraCadastro(new Date());
		}
		if (visita.getEntrada() == null && cadastro) {
			visita.setEntrada(new Date());
		}
		checkEmpresa(visita);

		checkVisitante(visita);
		visita.setUsuario(Objects.requireNonNull(usuarioHolder.getUsuario()));
		return super.save(visita);
	}

	private void checkVisitante(Visita visita) {
		Visitante visitanteTransient = Objects.requireNonNull(visita.getVisitante(), "Visitante não informado");
		String documento = visitanteTransient.getDocumento();
		Visitante visitante = visitanteMediator.findByDocumento(documento);
		if (visitante != null) {
			visita.setVisitante(visitante);
		} else {
			visita.setVisitante(visitanteMediator.save(visitanteTransient));
		}
		checkVisita(visita, documento);
	}

	private void checkVisita(Visita visita, String documento) {
		Integer id = visita.getId();
		if (id == null) {
			id = -1;
		}
		if (visitaRepository.countByUsuarioAndSaida(documento, id) > 0) {
			throw new NegocioException("O visitante com documento " + documento + " já entrou no prédio e não saiu!");
		}
		Date saida = visita.getSaida();
		Date entrada = visita.getEntrada();
		List<Integer> idsConflitantes = null;
		if (entrada != null && saida != null && !(idsConflitantes = visitaRepository.findIdsByDocumentoAndIntervalo(entrada, saida, documento, id)).isEmpty()) {
			String idsString = StringUtils.collectionToCommaDelimitedString(idsConflitantes);
			throw new NegocioException("O visitante com documento " + documento + " possui intervalo de entrada e saída conflitante com a(s) visita(s) " + idsString);
		}
	}

	private void checkEmpresa(Visita visita) {
		Empresa empresa = visita.getEmpresa();
		if (empresa != null) {
			if (StringUtils.isEmpty(empresa.getNome()) && empresa.getId() == null) {
				visita.setEmpresa(null);
			}
			if (empresa.getId() == null && !StringUtils.isEmpty(empresa.getNome())) {
				visita.setEmpresa(empresaMediator.save(empresa));
			}
		}
	}

	/**
	 * Listar visitas que ainda não fora dado baixa.
	 * 
	 * @return
	 */
	public List<Visita> findVisitasSemBaixa() {
		return visitaRepository.findBySaidaIsNull();
	}

	/**
	 * Listar pessoas por parte do nome.
	 * 
	 * @param nome
	 * @return
	 */
	public List<PessoaLdap> findResponsaveisByNome(String nome) {
		if (StringUtils.isEmpty(nome)) {
			return Collections.emptyList();
		}
		return ldapRepository.findByNomeLike(nome);
	}

	@Transactional
	@Override
	public void apagar(Integer id) {
		Visita visita = visitaRepository.findByIdAndUsuario(id, usuarioHolder.getUsuario());
		if (visita != null) {
			visitaRepository.delete(visita);
		} else {
			throw new NegocioException("A visita só pode ser removida no mesmo dia e pelo mesmo usuário criador.");
		}
	}
}
