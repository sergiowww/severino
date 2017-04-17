package br.mp.mpt.prt8.severino.mediator;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.ControleMotoristaRepository;
import br.mp.mpt.prt8.severino.dao.MotoristaRepository;
import br.mp.mpt.prt8.severino.dao.specs.AbstractSpec;
import br.mp.mpt.prt8.severino.dao.specs.ControleMotoristaSpec;
import br.mp.mpt.prt8.severino.entity.Cargo;
import br.mp.mpt.prt8.severino.entity.ControleMotorista;
import br.mp.mpt.prt8.severino.entity.Fluxo;
import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.utils.DateUtils;
import br.mp.mpt.prt8.severino.utils.EntidadeUtil;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Mediator para o controle de ponto do motorista.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class ControleMotoristaMediator extends AbstractSpecMediator<ControleMotorista, Integer> {

	@Autowired
	private ControleMotoristaRepository controleMotoristaRepository;

	@Autowired
	private MotoristaRepository motoristaRepository;

	@Autowired
	private MotoristaMediator motoristaMediator;

	@Autowired
	private ViagemMediator viagemMediator;

	@Override
	protected BaseRepositorySpecification<ControleMotorista, Integer> repositoryBean() {
		return controleMotoristaRepository;
	}

	@Transactional
	@Override
	public ControleMotorista save(ControleMotorista controleMotorista) {
		Motorista motorista = controleMotorista.getMotorista();
		motoristaMediator.checkMotorista(motorista);
		Integer idNaoNulo = EntidadeUtil.getIdNaoNulo(controleMotorista);
		Date dataHoraCorrente = controleMotorista.getDataHora();
		ControleMotorista controleAnterior = controleMotoristaRepository.findControleAnterior(dataHoraCorrente, motorista.getId());
		if (controleAnterior != null && controleAnterior.getFluxo().equals(controleMotorista.getFluxo())) {
			Date dataHora = controleAnterior.getDataHora();
			throw new NegocioException("O ponto anterior " + DateUtils.toString(dataHora) + " � uma " + controleAnterior.getFluxo());
		}

		ControleMotorista controlePosterior = controleMotoristaRepository.findControleProximo(dataHoraCorrente, motorista.getId());
		if (controlePosterior != null && controlePosterior.getFluxo().equals(controleMotorista.getFluxo())) {
			Date dataHora = controlePosterior.getDataHora();
			throw new NegocioException("O registro de ponto " + DateUtils.toString(dataHora) + " posterior a este � uma " + controlePosterior.getFluxo());
		}

		Long total = controleMotoristaRepository.countByDataHoraAndMotoristaAndIdNot(dataHoraCorrente, motorista, idNaoNulo);
		if (total > 0) {
			throw new NegocioException("N�o � poss�vel registrar uma data e hora que tenha sido registrada antes!");
		}
		controleMotorista.setLocal(usuarioHolder.getLocal());
		return super.save(controleMotorista);
	}

	/**
	 * Procurar motoristas dispon�veis hoje.
	 * 
	 * @return
	 */
	public List<ControleMotorista> findDisponiveis() {
		Integer idLocal = usuarioHolder.getLocal().getId();
		List<ControleMotorista> controles = controleMotoristaRepository.findUltimoAgrupadoPorMotorista(idLocal);
		List<Motorista> motoristas = motoristaRepository.findMotoristasSemRegistroPonto(Cargo.MOTORISTA, idLocal);
		List<ControleMotorista> motoristasSemRegistro = motoristas.stream().map(m -> new ControleMotorista(m)).collect(Collectors.toList());
		controles.addAll(motoristasSemRegistro);
		Collections.sort(controles);
		return controles;
	}

	/**
	 * Buscar motoristas disponiveis.
	 * 
	 * @param idMotorista
	 * 
	 * 
	 * @return
	 */
	public List<Motorista> findMotoristasDisponiveis(Integer idMotorista) {
		Integer idLocal = usuarioHolder.getLocal().getId();
		List<Motorista> motoristas = controleMotoristaRepository.findMotoristasDisponiveis(Fluxo.ENTRADA, idLocal);
		if (idMotorista != null && !motoristas.contains(new Motorista(idMotorista))) {
			Motorista motorista = motoristaRepository.findOne(idMotorista);
			motoristas.add(motorista);
		}
		return motoristas;
	}

	/**
	 * Atualizar disponibilidade do motorista.
	 * 
	 * @param idMotorista
	 * @param horario
	 */
	@Transactional
	public void atualizarDisponibilidade(Integer idMotorista, Date horario) {
		Date dataHora = getDataHora(horario);
		Motorista motorista = new Motorista(idMotorista);

		ControleMotorista controle = viagemMediator.realizarBaixa(motorista, dataHora);
		if (controle == null) {
			Fluxo novaDirecao = getProximaDirecao(idMotorista);
			controle = new ControleMotorista();
			controle.setFluxo(novaDirecao);
			controle.setMotorista(motorista);
			controle.setDataHora(dataHora);
		}
		checkDataHora(controle);
		controle.setLocal(usuarioHolder.getLocal());
		controleMotoristaRepository.save(controle);
	}

	private void checkDataHora(ControleMotorista controle) {
		Date ultimaDataHoraRegistrada = controleMotoristaRepository.findMaxDataHoraByMotorista(controle.getMotorista().getId());
		if (ultimaDataHoraRegistrada != null && ultimaDataHoraRegistrada.after(controle.getDataHora())) {
			String message = "N�o � poss�vel inserir um hor�rio antes do �ltimo hor�rio registrado: " + DateUtils.toString(ultimaDataHoraRegistrada);
			throw new NegocioException(message);
		}
	}

	private Fluxo getProximaDirecao(Integer idMotorista) {
		ControleMotorista ultimoControle = findUltimoControle(idMotorista);
		if (ultimoControle != null) {
			return ultimoControle.getFluxo().inverter();
		}
		// quando o cabra n�o tiver ponto, o ponto inicial � uma entrada
		return Fluxo.ENTRADA;
	}

	private Date getDataHora(Date horario) {
		if (horario == null) {
			return DateUtils.getDataHoraAtual();
		}
		Calendar calendarTime = Calendar.getInstance();
		calendarTime.setTime(horario);

		Calendar calendarCompleto = Calendar.getInstance();
		calendarCompleto.set(Calendar.HOUR, calendarTime.get(Calendar.HOUR));
		calendarCompleto.set(Calendar.AM_PM, calendarTime.get(Calendar.AM_PM));
		calendarCompleto.set(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE));
		calendarCompleto.set(Calendar.MILLISECOND, 0);
		calendarCompleto.set(Calendar.SECOND, 0);

		return calendarCompleto.getTime();
	}

	/**
	 * Buscar o ultimo ponto de controle de um motorista.
	 * 
	 * @param idMotorista
	 * @return
	 */
	public ControleMotorista findUltimoControle(Integer idMotorista) {
		List<ControleMotorista> result = controleMotoristaRepository.findUltimoControle(idMotorista);
		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

	@Override
	public Class<? extends AbstractSpec<ControleMotorista>> specClass() {
		return ControleMotoristaSpec.class;
	}

}
