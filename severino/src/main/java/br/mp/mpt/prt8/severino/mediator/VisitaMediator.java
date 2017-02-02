package br.mp.mpt.prt8.severino.mediator;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.VisitaRepository;
import br.mp.mpt.prt8.severino.entity.Empresa;
import br.mp.mpt.prt8.severino.entity.Visita;
import br.mp.mpt.prt8.severino.entity.Visitante;
import br.mp.mpt.prt8.severino.mediator.intervalodatas.CheckConflitoIntervalo;
import br.mp.mpt.prt8.severino.mediator.intervalodatas.FluxoCamposIntervalo;
import br.mp.mpt.prt8.severino.utils.EntidadeUtil;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Mediador de operações.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class VisitaMediator extends AbstractExampleMediator<Visita, Integer> {

	@Autowired
	private VisitaRepository visitaRepository;

	@Autowired
	private VisitanteMediator visitanteMediator;

	@Autowired
	private EmpresaMediator empresaMediator;

	@Autowired
	private FluxoCamposIntervalo fluxoCamposIntervalo;

	private CheckConflitoIntervalo<String, Visita> checkConflitoIntervalo;

	@PostConstruct
	public void setUp() {
		this.checkConflitoIntervalo = new CheckConflitoIntervalo<String, Visita>(visitaRepository,
				"O visitante com documento %s possui intervalo de entrada e saída conflitante com a(s) visita(s) %s");
	}

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
		fluxoCamposIntervalo.setCamposIniciais(visita);
		checkEmpresa(visita);

		checkVisitante(visita);
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
		Integer id = EntidadeUtil.getIdNaoNulo(visita);
		if (visitaRepository.countByUsuarioAndSaida(documento, id) > 0) {
			throw new NegocioException("O visitante com documento " + documento + " já entrou no prédio e não saiu!");
		}

		checkConflitoIntervalo.validar(visita, documento);

	}

	private void checkEmpresa(Visita visita) {
		Empresa empresa = visita.getEmpresa();
		if (empresa != null && empresa.getId() == null) {
			if (StringUtils.isEmpty(empresa.getNome())) {
				visita.setEmpresa(null);
			} else {
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
	 * Recupera todas as visitas com data de entrada na data corrente.
	 * 
	 * @param visita
	 * @return
	 */
	public List<Visita> findAllRegistradasHoje(Visita visita) {
		Integer idVisita = EntidadeUtil.getIdNaoNulo(visita);
		return visitaRepository.findAllRegistradasHoje(idVisita);
	}

}
