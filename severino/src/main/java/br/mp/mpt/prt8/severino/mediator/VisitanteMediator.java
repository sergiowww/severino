package br.mp.mpt.prt8.severino.mediator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.VisitanteRepository;
import br.mp.mpt.prt8.severino.entity.Visitante;
import br.mp.mpt.prt8.severino.utils.EntidadeUtil;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Mediador de opera��es.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class VisitanteMediator extends AbstractExampleMediator<Visitante, Integer> {

	@Autowired
	private VisitanteRepository visitanteRepository;

	@Override
	protected BaseRepositorySpecification<Visitante, Integer> repositoryBean() {
		return visitanteRepository;
	}

	@Transactional
	@Override
	public Visitante save(Visitante visitante) {
		Integer id = EntidadeUtil.getIdNaoNulo(visitante);

		Long totalPorDocumento = visitanteRepository.countByDocumentoIgnoreCaseAndIdNot(visitante.getDocumento(), id);
		if (totalPorDocumento > 0) {
			throw new NegocioException("J� existe um visitante com o mesmo n�mero de documento informado");
		}
		return super.save(visitante);
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
	 * Buscar visitante pelo n�mero do documento.
	 * 
	 * @param documento
	 * @return
	 */
	public Visitante findByDocumento(String documento) {
		return visitanteRepository.findByDocumentoIgnoreCase(documento);
	}

}
