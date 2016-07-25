package br.mp.mpt.prt8.severino.mediator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.VisitanteRepository;
import br.mp.mpt.prt8.severino.entity.Visitante;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Mediador de operações.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class VisitanteMediator extends AbstractMediator<Visitante, Integer> {

	@Autowired
	private VisitanteRepository visitanteRepository;

	@Override
	protected BaseRepositorySpecification<Visitante, Integer> repositoryBean() {
		return visitanteRepository;
	}

	@Transactional
	@Override
	public Visitante save(Visitante entity) {
		Integer id = entity.getId();
		if (id == null) {
			id = -1;
		}
		Long totalPorDocumento = visitanteRepository.countByDocumentoIgnoreCaseAndIdNot(entity.getDocumento(), id);
		if (totalPorDocumento > 0) {
			throw new NegocioException("Já existe um visitante com o mesmo número de documento informado");
		}
		return super.save(entity);
	}

	@Override
	protected Visitante getExampleForSearching(String searchValue) {
		Visitante probe = new Visitante();
		probe.setDocumento(searchValue);
		probe.setNome(searchValue);
		probe.setOrgaoEmissor(searchValue);
		return probe;
	}

	/**
	 * Buscar visitante pelo número do documento.
	 * 
	 * @param documento
	 * @return
	 */
	public Visitante findByDocumento(String documento) {
		return visitanteRepository.findByDocumentoIgnoreCase(documento);
	}

}
