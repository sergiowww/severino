package br.mp.mpt.prt8.severino.mediator;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.ViagemRepository;
import br.mp.mpt.prt8.severino.entity.ControleMotorista;
import br.mp.mpt.prt8.severino.entity.FonteDisponibilidade;
import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.entity.Passageiro;
import br.mp.mpt.prt8.severino.entity.Veiculo;
import br.mp.mpt.prt8.severino.entity.Viagem;
import br.mp.mpt.prt8.severino.utils.NegocioException;
import br.mp.mpt.prt8.severino.validators.CadastrarViagem;
import br.mp.mpt.prt8.severino.valueobject.PessoaDisponibilidade;

/**
 * Mediator para viagens.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class ViagemMediator extends AbstractMediator<Viagem, Integer> {

	@Autowired
	private ViagemRepository viagemRepository;

	@Autowired
	private MotoristaMediator motoristaMediator;

	@Autowired
	private ControleMotoristaMediator controleMotoristaMediator;

	@Autowired
	private UsuarioHolder usuarioHolder;

	@Autowired
	private VeiculoMediator veiculoMediator;

	@Autowired
	private SmartValidator validator;

	@Override
	protected BaseRepositorySpecification<Viagem, Integer> repositoryBean() {
		return viagemRepository;
	}

	@Override
	protected Viagem getExampleForSearching(String searchValue) {
		Viagem viagem = new Viagem();
		Motorista motorista = new Motorista();
		motorista.setNome(searchValue);
		viagem.setMotorista(motorista);
		return viagem;
	}

	@Transactional
	@Override
	public Viagem save(Viagem viagem) {
		checkMotorista(viagem);
		Boolean registrarRetorno = viagem.isRegistrarSaida();
		Date dataHoraAtual = new Date();
		if (registrarRetorno) {
			viagem.setRetorno(dataHoraAtual);
		}
		checkViatura(viagem);
		if (viagem.getId() == null) {
			if (viagem.getSaida() == null) {
				viagem.setSaida(dataHoraAtual);
			}
			viagem.setDataHoraCadastro(dataHoraAtual);
		} else {
			checkControle(viagem);
		}
		viagem.setUsuario(Objects.requireNonNull(usuarioHolder.getUsuario()));
		checkPassageiros(viagem);
		return super.save(viagem);
	}

	private void checkMotorista(Viagem viagem) {
		Motorista motorista = viagem.getMotorista();
		motoristaMediator.checkMotorista(motorista);
		ControleMotorista ultimoControle;
		Integer idMotorista = motorista.getId();
		if (viagem.getId() == null && (ultimoControle = controleMotoristaMediator.findUltimoControle(idMotorista)) != null && !ultimoControle.isFluxoEntrada()) {
			throw new NegocioException("O motorista selecionado não está disponível mais!");
		}
	}

	private void checkControle(Viagem viagem) {
		Integer idControleSaida = viagemRepository.findIdControleSaidaByViagem(viagem.getId());
		viagem.setIdControleSaida(idControleSaida);

		Integer idControleRetorno = viagemRepository.findIdControleRetornoByViagem(viagem.getId());
		viagem.setIdControleRetorno(idControleRetorno);
		if (viagem.getRetorno() == null) {
			viagem.setControleRetorno(null);
			if (idControleRetorno != null) {
				controleMotoristaMediator.apagar(idControleRetorno);
			}
		}

	}

	private void checkPassageiros(Viagem viagem) {
		List<Passageiro> passageiros = viagem.getPassageiros();
		if (!CollectionUtils.isEmpty(passageiros)) {
			passageiros.forEach(p -> p.setViagem(viagem));
		}
		if (passageiros == null) {
			viagem.setPassageiros(Collections.emptyList());
		}
	}

	private void checkViatura(Viagem viagem) {
		Veiculo viatura = viagem.getViatura();
		if (viatura != null && StringUtils.isEmpty(viatura.getId())) {
			viagem.setViatura(viatura = null);
		}
		if (viatura != null) {
			if (!veiculoMediator.isViatura(viatura)) {
				throw new NegocioException("O veículo informado não está cadastrado como viatura!");
			}
			viatura.setViaturaMp(true);
			if (!viagem.isGravarVeiculo()) {
				viagem.setViatura(veiculoMediator.findOne(Optional.of(viatura.getId())));
			}
		}

	}

	/**
	 * Buscar viagens em andamento.
	 * 
	 * @return
	 */
	public List<Viagem> findViagensSemBaixa() {
		return viagemRepository.findByControleRetornoIsNull();
	}

	/**
	 * Alterar viagem para
	 * 
	 * @param motorista
	 * @param dataHoraRetorno
	 * @return
	 */
	@Transactional
	public ControleMotorista realizarBaixa(Motorista motorista, Date dataHoraRetorno) {
		Viagem ultimaViagem = viagemRepository.findByControleRetornoIsNullAndMotorista(motorista);
		if (ultimaViagem != null) {
			ultimaViagem.setRetorno(dataHoraRetorno);

			Set<ConstraintViolation<Viagem>> validate = ((LocalValidatorFactoryBean) validator).getValidator().validate(ultimaViagem, CadastrarViagem.class);
			if (validate.isEmpty()) {
				return ultimaViagem.getControleRetorno();
			}
			String messages = validate.stream().map(c -> c.getMessage()).collect(Collectors.joining(","));
			throw new NegocioException(messages);
		}
		return null;
	}

	/**
	 * Buscar passageiros com a última entrada e saída.
	 * 
	 * @return
	 */
	public List<PessoaDisponibilidade> findUltimaDisponibilidade() {
		List<PessoaDisponibilidade> passageiros = viagemRepository.findPassageirosUltimaViagem();
		passageiros.forEach(p -> p.setFonte(FonteDisponibilidade.VIAGEM));
		return passageiros;
	}
}
