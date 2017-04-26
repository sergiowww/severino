package br.mp.mpt.prt8.severino.mediator;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.VisitanteRepository;
import br.mp.mpt.prt8.severino.entity.Endereco;
import br.mp.mpt.prt8.severino.entity.Visitante;
import br.mp.mpt.prt8.severino.utils.EntidadeUtil;
import br.mp.mpt.prt8.severino.utils.FileUtilsApp;
import br.mp.mpt.prt8.severino.utils.NegocioException;
import br.mp.mpt.prt8.severino.validators.SelecionarSetor;

/**
 * Mediador de operações.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class VisitanteMediator extends AbstractExampleMediator<Visitante, Integer> {

	private static final Logger LOG = LoggerFactory.getLogger(VisitaMediator.class);

	@Autowired
	private VisitanteRepository visitanteRepository;

	@Autowired
	private ValidatorServiceBean<Visitante> validator;

	@Override
	protected BaseRepositorySpecification<Visitante, Integer> repositoryBean() {
		return visitanteRepository;
	}

	@Transactional
	@Override
	public Visitante save(Visitante visitante) {
		String tokenFoto = visitante.getTokenFoto();
		if (visitante.getEndereco() != null && !visitante.getEndereco().algumDadoPreenchido()) {
			visitante.setEndereco(null);
		}
		validator.validate(visitante, SelecionarSetor.class);

		Integer id = EntidadeUtil.getIdNaoNulo(visitante);

		checkEndereco(visitante);

		Long totalPorDocumento = visitanteRepository.countByDocumentoIgnoreCaseAndIdNot(visitante.getDocumento(), id);
		if (totalPorDocumento > 0) {
			throw new NegocioException("Já existe um visitante com o mesmo número de documento informado");
		}
		Visitante visitanteSaved = super.save(visitante);
		visitanteSaved.setTokenFoto(tokenFoto);
		repositoryBean().flush();
		gravarImagem(visitanteSaved);
		return visitanteSaved;
	}

	@Transactional
	@Override
	public void apagar(Integer id) {
		Visitante visitante = repositoryBean().findOne(id);
		repositoryBean().delete(visitante);
		try {
			Files.deleteIfExists(visitante.getReferenciaArquivoFinal());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void checkEndereco(Visitante visitante) {
		Endereco endereco = visitante.getEndereco();
		if (endereco != null) {
			if (visitante.getId() != null && endereco.getId() == null) {
				endereco.setId(visitante.getId());
			}
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

	/**
	 * Remover arquivo da foto temporária.
	 * 
	 * @param tokenFoto
	 * @throws IOException
	 */
	public void removerFotoTemporaria(String tokenFoto) throws IOException {
		Path arquivoTemporario = FileUtilsApp.getArquivoTemp(tokenFoto);
		LOG.info("Apagando arquivo " + arquivoTemporario + ": " + Files.deleteIfExists(arquivoTemporario));
	}

	/**
	 * Gravar arquivo na pasta temporária.
	 * 
	 * @param tokenFoto
	 * @param conteudoArquivo
	 * @throws IOException
	 */
	public void gravarImagemTemporaria(String tokenFoto, InputStream conteudoArquivo) throws IOException {
		Path arquivoImagem = FileUtilsApp.getArquivoTemp(tokenFoto);
		Files.deleteIfExists(arquivoImagem);
		Path file = Files.createFile(arquivoImagem);
		try (InputStream entrada = conteudoArquivo;) {
			LOG.info("Gravando arquivo " + arquivoImagem + " ...");
			Files.copy(entrada, file, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	private void gravarImagem(Visitante visitante) {
		try {
			Path arquivoTemporario = visitante.getArquivoTemp();
			if (arquivoTemporario != null) {
				Path destino = visitante.getReferenciaArquivoFinal();
				Files.move(arquivoTemporario, destino, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Buscar foto pelo identificador do visitante.
	 * 
	 * @param documento
	 * @param tokenFoto
	 * @return
	 * @throws IOException
	 */
	public Path getFotoByDocumento(String documento, String tokenFoto) throws IOException {
		Visitante visitante;
		if (StringUtils.isEmpty(documento) || (visitante = findByDocumento(documento)) == null) {
			visitante = new Visitante();
		}
		visitante.setTokenFoto(tokenFoto);
		Path arquivoFinal = visitante.getArquivoFinal();
		Path arquivoTemp = visitante.getArquivoTemp();
		if (arquivoTemp != null) {
			return arquivoTemp;
		}
		return arquivoFinal;
	}

}
