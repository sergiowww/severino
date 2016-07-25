package br.mp.mpt.prt8.severino.mediator;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.SetorRepository;
import br.mp.mpt.prt8.severino.entity.Setor;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Mediador de operações.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class SetorMediator extends AbstractMediator<Setor, Integer> {

	@Autowired
	private SetorRepository setorRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	protected BaseRepositorySpecification<Setor, Integer> repositoryBean() {
		return setorRepository;
	}

	@Override
	protected Setor getExampleForSearching(String searchValue) {
		Setor probe = new Setor();
		probe.setNome(searchValue);
		probe.setSala(searchValue);
		return probe;
	}

	@Transactional
	@Override
	public Setor save(Setor setor) {
		String nome = setor.getNome();
		Integer id = setor.getId();
		if (id == null) {
			id = -1;
		}
		Short andar = setor.getAndar();
		String sala = setor.getSala();
		Long total = countSetoresComMesmosValores(nome, id, andar, sala);
		if (total > 0) {
			throw new NegocioException("O setor com o nome \"" + nome + "\" já existe!");
		}
		return super.save(setor);
	}

	private Long countSetoresComMesmosValores(String nome, Integer id, Short andar, String sala) {
		if (StringUtils.isEmpty(sala)) {
			setorRepository.countByNomeIgnoreCaseAndAndarAndSalaIsNullAndIdNot(nome, andar, id);
		}
		return setorRepository.countByNomeIgnoreCaseAndAndarAndSalaIgnoreCaseAndIdNot(nome, andar, sala, id);
	}

	/**
	 * Buscar todos os setores ordenando pelo andar.
	 * 
	 * @return
	 */
	public List<Setor> findAll() {
		return setorRepository.findAll(new Sort("andar"));
	}

}
