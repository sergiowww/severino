package br.mp.mpt.prt8.severino.mediator;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.MotoristaRepository;
import br.mp.mpt.prt8.severino.entity.Cargo;
import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.utils.EntidadeUtil;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Mediator de operações para o motorista.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class MotoristaMediator extends AbstractExampleMediator<Motorista, Integer> {

	@Autowired
	private MotoristaRepository motoristaRepository;

	@Override
	protected BaseRepositorySpecification<Motorista, Integer> repositoryBean() {
		return motoristaRepository;
	}

	@Override
	protected Motorista getExampleForSearching(String searchValue) {
		Motorista motorista = new Motorista();
		motorista.setMatricula(searchValue);
		motorista.setNome(searchValue);
		return motorista;
	}

	/**
	 * Listar motoristas.
	 * 
	 * @return
	 */
	public List<Motorista> findAll() {
		return motoristaRepository.findAll();
	}

	/**
	 * Verificar se motorista é um técnico de transporte.
	 * 
	 * @param motorista
	 */
	public void checkMotorista(Motorista motorista) {
		if (motoristaRepository.countByIdAndCargo(motorista.getId(), Cargo.MOTORISTA) < 1) {
			throw new NegocioException("O motorista selecionado não é um técnico de transporte!");
		}
	}

	@Transactional
	@Override
	public Motorista save(Motorista motorista) {
		Integer id = EntidadeUtil.getIdNaoNulo(motorista);

		String matricula = motorista.getMatricula();
		if (motoristaRepository.countByMatriculaIgnoreCaseAndIdNot(matricula, id) > 0) {
			throw new NegocioException("Já existe um motorista com a matrícula " + matricula + " cadastrado");
		}
		return super.save(motorista);
	}

	/**
	 * Buscar todos os técnicos de transporte.
	 * 
	 * @return
	 */
	public List<Motorista> findAllMotoristasTecnicos() {
		return motoristaRepository.findByCargoIn(Arrays.asList(Cargo.MOTORISTA));
	}

}
