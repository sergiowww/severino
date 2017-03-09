package br.mp.mpt.prt8.severino.mediator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.VisitanteRepository;
import br.mp.mpt.prt8.severino.entity.Endereco;
import br.mp.mpt.prt8.severino.entity.Visitante;
import br.mp.mpt.prt8.severino.utils.EntidadeUtil;
import br.mp.mpt.prt8.severino.utils.NegocioException;
import br.mp.mpt.prt8.severino.utils.StringUtilApp;
import br.mp.mpt.prt8.severino.validators.CadastrarVisita;

/**
 * Mediador de operações.
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

	@Autowired
	private ValidatorServiceBean<Visitante> validator;

	@Transactional
	@Override
	public Visitante save(Visitante visitante) {
		if (visitante.getEndereco() != null && !visitante.getEndereco().algumDadoPreenchido()) {
			visitante.setEndereco(null);
		}
		validator.validate(visitante, CadastrarVisita.class);

		Integer id = EntidadeUtil.getIdNaoNulo(visitante);

		visitante.setTelefone(StringUtilApp.limparMascara(visitante.getTelefone()));
		visitante.setTelefoneAlternativo(StringUtilApp.limparMascara(visitante.getTelefoneAlternativo()));
		checkEndereco(visitante);

		Long totalPorDocumento = visitanteRepository.countByDocumentoIgnoreCaseAndIdNot(visitante.getDocumento(), id);
		if (totalPorDocumento > 0) {
			throw new NegocioException("Já existe um visitante com o mesmo número de documento informado");
		}
		return super.save(visitante);
	}

	private void checkEndereco(Visitante visitante) {
		Endereco endereco = visitante.getEndereco();
		if (endereco != null) {
			if (visitante.getId() != null && endereco.getId() == null) {
				endereco.setId(visitante.getId());
			}
			endereco.setCep(StringUtilApp.limparMascara(endereco.getCep()));
		}
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
