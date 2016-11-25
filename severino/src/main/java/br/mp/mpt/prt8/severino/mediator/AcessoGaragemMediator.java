package br.mp.mpt.prt8.severino.mediator;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.mp.mpt.prt8.severino.dao.AcessoGaragemRepository;
import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.entity.AcessoGaragem;
import br.mp.mpt.prt8.severino.entity.FonteDisponibilidade;
import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.entity.Veiculo;
import br.mp.mpt.prt8.severino.entity.Visita;
import br.mp.mpt.prt8.severino.mediator.intervalodatas.CheckConflitoIntervalo;
import br.mp.mpt.prt8.severino.mediator.intervalodatas.FluxoCamposIntervalo;
import br.mp.mpt.prt8.severino.utils.EntidadeUtil;
import br.mp.mpt.prt8.severino.utils.NegocioException;
import br.mp.mpt.prt8.severino.valueobject.PessoaDisponibilidade;

/**
 * Mediator para acesso garagem.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class AcessoGaragemMediator extends AbstractMediator<AcessoGaragem, Integer> {

	private static final String MENSAGEM_CONFLITO = "O acesso a garagem do veículo placa %s possui intervalo de entrada e saída conflitante com a(s) acessos(s) %s";

	@Autowired
	private AcessoGaragemRepository acessoGaragemRepository;

	@Autowired
	private VisitaMediator visitaMediator;

	@Autowired
	private VeiculoMediator veiculoMediator;

	@Autowired
	private FluxoCamposIntervalo fluxoCamposIntervalo;

	private CheckConflitoIntervalo<String, AcessoGaragem> checkConflitoIntervaloMembro;

	@Override
	protected BaseRepositorySpecification<AcessoGaragem, Integer> repositoryBean() {
		return acessoGaragemRepository;
	}

	@PostConstruct
	public void setUp() {
		this.checkConflitoIntervaloMembro = new CheckConflitoIntervalo<String, AcessoGaragem>(acessoGaragemRepository, MENSAGEM_CONFLITO);
	}

	@Override
	protected AcessoGaragem getExampleForSearching(String searchValue) {
		AcessoGaragem acessoGaragem = new AcessoGaragem();
		Motorista motorista = new Motorista();
		motorista.setNome(searchValue);
		acessoGaragem.setMotorista(motorista);
		return acessoGaragem;
	}

	@Transactional
	@Override
	public AcessoGaragem save(AcessoGaragem acessoGaragem) {
		fluxoCamposIntervalo.setCamposIniciais(acessoGaragem);

		if (acessoGaragem.isUsuarioVisitante()) {
			checkVisitante(acessoGaragem);
		} else {
			checkServidorMembro(acessoGaragem);
		}
		return super.save(acessoGaragem);
	}

	private void checkVisitante(AcessoGaragem acessoGaragem) {
		acessoGaragem.setMotorista(null);
		checkVisita(acessoGaragem);
		checkVeiculoVisitante(acessoGaragem);
	}

	private void checkVeiculoVisitante(AcessoGaragem acessoGaragem) {
		Veiculo veiculo = acessoGaragem.getVeiculo();
		if (!veiculoMediator.isVeiculoVisitante(veiculo)) {
			throw new NegocioException("O veículo placa " + veiculo.getId() + " é uma viatura ou pertence a um servidor ou membro!");
		}
		acessoGaragem.setVeiculo(veiculoMediator.save(veiculo));
	}

	private void checkVisita(AcessoGaragem acessoGaragem) {
		Visita visita = visitaMediator.findOne(Optional.of(acessoGaragem.getVisita().getId()));
		LocalDate dataEntrada = visita.getEntradaAsLocalDate();

		Integer idAcesso = acessoGaragem.getId();
		if ((idAcesso == null && !dataEntrada.equals(LocalDate.now())) || (idAcesso != null && !dataEntrada.equals(acessoGaragem.getEntradaAsLocalDate()))) {
			throw new NegocioException("A visita informada não foi cadastrada hoje!");
		}
		idAcesso = EntidadeUtil.getIdNaoNulo(acessoGaragem);
		Integer idVisita = visita.getId();
		if (acessoGaragemRepository.countByIdVisita(idVisita, idAcesso) > 0) {
			throw new NegocioException("Já existe um acesso associado a visita selecionada!");
		}

	}

	private void checkServidorMembro(AcessoGaragem acessoGaragem) {
		String placa = acessoGaragem.getVeiculo().getId();
		Veiculo veiculo = veiculoMediator.findByPlaca(placa);
		if (veiculo == null) {
			throw new NegocioException("Não foi encontrado veículo com a placa " + placa);
		}
		if (veiculo.getMotorista() == null) {
			throw new NegocioException("Este veículo não possui dono!");
		}
		Integer idAcesso = EntidadeUtil.getIdNaoNulo(acessoGaragem);
		Integer idMotorista = veiculo.getMotorista().getId();
		if (acessoGaragemRepository.countAcessoMotoristaSemBaixa(idMotorista, idAcesso) > 0) {
			throw new NegocioException("O servidor ou membro selecionado (" + idMotorista + ") já entrou na garagem e não saiu!");
		}
		checkConflitoIntervaloMembro.validar(acessoGaragem, placa);
		acessoGaragem.setVeiculo(veiculo);
		acessoGaragem.setMotorista(veiculo.getMotorista());
		acessoGaragem.setVisita(null);
	}

	/**
	 * Buscar registros que ainda não possuem a data de saída.
	 * 
	 * @return
	 */
	public List<AcessoGaragem> findAllSemBaixa() {
		return acessoGaragemRepository.findBySaidaIsNull();
	}

	/**
	 * Buscar o ultimo horario de entrada e saída de cada motorista.
	 * 
	 * @param inicio
	 * @param fim
	 * 
	 * @return
	 */
	public List<PessoaDisponibilidade> findUltimaDisponibilidade(Date inicio, Date fim) {
		List<PessoaDisponibilidade> acessos = acessoGaragemRepository.findUltimaDisponibilidade(inicio, fim);
		acessos.forEach(p -> p.setFonte(FonteDisponibilidade.ACESSO_GARAGEM));
		return acessos;
	}

}
