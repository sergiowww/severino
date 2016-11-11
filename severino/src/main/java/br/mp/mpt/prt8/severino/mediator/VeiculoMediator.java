package br.mp.mpt.prt8.severino.mediator;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.VeiculoRepository;
import br.mp.mpt.prt8.severino.entity.Cargo;
import br.mp.mpt.prt8.severino.entity.Veiculo;

/**
 * Mediator para veículos.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class VeiculoMediator extends AbstractMediator<Veiculo, String> {
	@Autowired
	private VeiculoRepository veiculoRepository;

	@Transactional
	@Override
	public Veiculo save(Veiculo veiculo) {
		if (veiculo.getMotorista() != null && veiculo.getMotorista().getId() == null) {
			veiculo.setMotorista(null);
		}
		if (veiculo.getViaturaMp()) {
			veiculo.setMotorista(null);
		}
		return super.save(veiculo);
	}

	@Override
	protected BaseRepositorySpecification<Veiculo, String> repositoryBean() {
		return veiculoRepository;
	}

	@Override
	protected ExampleMatcher getExampleMatcher() {
		return super.getExampleMatcher().withIgnorePaths("viaturaMp");
	}

	@Override
	protected Veiculo getExampleForSearching(String searchValue) {
		Veiculo veiculo = new Veiculo();
		veiculo.setId(searchValue);
		veiculo.setMarca(searchValue);
		veiculo.setModelo(searchValue);
		veiculo.setCor(searchValue);
		return veiculo;
	}

	/**
	 * Buscar viaturas do mp.
	 * 
	 * @return
	 */
	public List<Veiculo> findViaturas() {
		return veiculoRepository.findByViaturaMpTrue();
	}

	/**
	 * Verifica se a viatura já está cadastrada.
	 * 
	 * @param viatura
	 * @return
	 */
	public boolean isViatura(Veiculo viatura) {

		Boolean viaturaMp = veiculoRepository.getViaturaMpById(viatura.getId());
		if (viaturaMp == null) {
			return true;
		}
		return viaturaMp;
	}

	/**
	 * Buscar todos os membros e servidores.
	 * 
	 * @return
	 */
	public List<Veiculo> findAllServidoresMembros() {
		Predicate<? super Cargo> predicate = m -> !m.equals(Cargo.MOTORISTA);
		List<Cargo> cargos = Arrays.stream(Cargo.values()).filter(predicate).collect(Collectors.toList());

		return veiculoRepository.findByCargoIn(cargos);
	}

	/**
	 * Buscar veículo pela placa.
	 * 
	 * @param placa
	 * @return
	 */
	public Veiculo findByPlaca(String placa) {
		if (StringUtils.isEmpty(placa)) {
			return null;
		}
		return veiculoRepository.findByIdIgnoreCase(placa.trim());
	}

	/**
	 * Verificar se o veículo não pertence a um servidor ou membro ou é uma
	 * viatura.
	 * 
	 * @param veiculo
	 * @return
	 */
	public boolean isVeiculoVisitante(Veiculo veiculo) {
		if (!veiculoRepository.exists(veiculo.getId())) {
			return true;
		}
		Long total = veiculoRepository.countByMotoristaIsNullAndViaturaMpFalseAndId(veiculo.getId());
		return total > 0;
	}

}
