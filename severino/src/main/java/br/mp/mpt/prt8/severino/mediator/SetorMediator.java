package br.mp.mpt.prt8.severino.mediator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.SetorRepository;
import br.mp.mpt.prt8.severino.dao.specs.AbstractSpec;
import br.mp.mpt.prt8.severino.dao.specs.SetorSpec;
import br.mp.mpt.prt8.severino.entity.Setor;
import br.mp.mpt.prt8.severino.utils.EntidadeUtil;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Mediador de opera��es.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class SetorMediator extends AbstractSpecMediator<Setor, Integer> {

	@Autowired
	private SetorRepository setorRepository;

	@Override
	protected BaseRepositorySpecification<Setor, Integer> repositoryBean() {
		return setorRepository;
	}

	@Transactional
	@Override
	public Setor save(Setor setor) {
		String nome = setor.getNome();
		Integer id = EntidadeUtil.getIdNaoNulo(setor);

		Short andar = setor.getAndar();
		String sala = setor.getSala();
		Long total = countSetoresComMesmosValores(nome, id, andar, sala);
		if (total > 0) {
			throw new NegocioException("O setor com o nome \"" + nome + "\" j� existe!");
		}
		setor.setLocal(usuarioHolder.getLocal());
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
		return setorRepository.findByLocalOrderByAndar(usuarioHolder.getLocal());
	}

	@Override
	public Class<? extends AbstractSpec<Setor>> specClass() {
		return SetorSpec.class;
	}

}
