package br.mp.mpt.prt8.severino.mediator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.EmpresaRepository;
import br.mp.mpt.prt8.severino.entity.Empresa;
import br.mp.mpt.prt8.severino.utils.EntidadeUtil;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Mediador de operações.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class EmpresaMediator extends AbstractExampleMediator<Empresa, Integer> {

	@Autowired
	private EmpresaRepository empresaRepository;

	@Override
	protected BaseRepositorySpecification<Empresa, Integer> repositoryBean() {
		return empresaRepository;
	}

	@Override
	protected Empresa getExampleForSearching(String searchValue) {
		Empresa empresa = new Empresa();
		empresa.setNome(searchValue);
		return empresa;
	}

	/**
	 * Buscar empresas por parte do nome.
	 * 
	 * @param nomeEmpresa
	 * @return
	 */
	public List<Empresa> findByParteNome(String nomeEmpresa) {
		Empresa empresa = getExampleForSearching(nomeEmpresa);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING);
		return empresaRepository.findAll(Example.of(empresa, exampleMatcher));
	}

	@Transactional
	@Override
	public Empresa save(Empresa empresa) {
		String nome = empresa.getNome();
		Integer id = EntidadeUtil.getIdNaoNulo(empresa);

		if (empresaRepository.countByNomeIgnoreCaseAndIdNot(nome, id) > 0) {
			throw new NegocioException("A empresa com o nome \"" + nome + "\" já existe!");
		}
		return super.save(empresa);
	}
}
