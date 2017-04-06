package br.mp.mpt.prt8.severino.mediator;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.VisitaRepository;
import br.mp.mpt.prt8.severino.dao.specs.AbstractSpec;
import br.mp.mpt.prt8.severino.dao.specs.VisitaSpec;
import br.mp.mpt.prt8.severino.entity.Empresa;
import br.mp.mpt.prt8.severino.entity.Local;
import br.mp.mpt.prt8.severino.entity.Visita;
import br.mp.mpt.prt8.severino.entity.Visitante;
import br.mp.mpt.prt8.severino.mediator.intervalodatas.CheckConflitoIntervalo;
import br.mp.mpt.prt8.severino.mediator.intervalodatas.FluxoCamposIntervalo;
import br.mp.mpt.prt8.severino.utils.Constantes;
import br.mp.mpt.prt8.severino.utils.DateUtils;
import br.mp.mpt.prt8.severino.utils.EntidadeUtil;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Mediador de operações.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class VisitaMediator extends AbstractSpecMediator<Visita, Integer> {

	private static final int MAXIMO_DIAS_ALTERACAO = 30;
	private static final String MENSAGEM_FORA_INTERVALO_ALTERACAO = "Este registro foi cadastrado em %s a alteração somente é permitida nos "
			+ "%d dias subsequentes ao cadastro. Entre em contato com a TI caso queira promover alterações neste registro";

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
			visitanteTransient.setId(visitante.getId());
			visitanteTransient.setNome(visitante.getNome());
			visitanteTransient.setOrgaoEmissor(visitante.getOrgaoEmissor());
			visitanteTransient.setUf(visitante.getUf());
		}
		visita.setVisitante(visitanteMediator.save(visitanteTransient));
		checkVisita(visita, documento);
	}

	private void checkVisita(Visita visita, String documento) {
		if (visita.getId() != null) {
			Date dataHoraCadastro = visitaRepository.findDataHoraCadastroById(visita.getId());
			LocalDate ultimoDiaAlteracao = DateUtils.toLocalDate(dataHoraCadastro).plusDays(MAXIMO_DIAS_ALTERACAO);
			if (ultimoDiaAlteracao.isBefore(LocalDate.now())) {
				String dataHoraFormatada = DateFormat.getDateInstance(DateFormat.MEDIUM, Constantes.DEFAULT_LOCALE).format(dataHoraCadastro);
				throw new NegocioException(String.format(MENSAGEM_FORA_INTERVALO_ALTERACAO, dataHoraFormatada, MAXIMO_DIAS_ALTERACAO));
			}
		}
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
		Local local = usuarioHolder.getLocal();
		return visitaRepository.findBySaidaIsNullAndLocal(local);
	}

	/**
	 * Recupera todas as visitas com data de entrada na data corrente.
	 * 
	 * @param visita
	 * @return
	 */
	public List<Visita> findAllRegistradasHoje(Visita visita) {
		Integer idVisita = EntidadeUtil.getIdNaoNulo(visita);
		Integer idLocal = usuarioHolder.getLocal().getId();
		return visitaRepository.findAllRegistradasHoje(idVisita, idLocal);
	}

	@Override
	public Class<? extends AbstractSpec<Visita>> specClass() {
		return VisitaSpec.class;
	}

}
