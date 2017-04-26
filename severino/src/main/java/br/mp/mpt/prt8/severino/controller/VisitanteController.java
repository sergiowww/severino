package br.mp.mpt.prt8.severino.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonView;

import br.mp.mpt.prt8.severino.entity.Estado;
import br.mp.mpt.prt8.severino.entity.Visitante;
import br.mp.mpt.prt8.severino.mediator.AbstractMediator;
import br.mp.mpt.prt8.severino.mediator.VisitanteMediator;
import br.mp.mpt.prt8.severino.utils.Roles;
import br.mp.mpt.prt8.severino.viewhelpers.PesquisaDoc;

/**
 * Controlador de operações da entidade.
 * 
 * @author sergio.eoliveira
 *
 */
@Controller
@RequestMapping("/visitante")
public class VisitanteController extends AbstractFullCrudController<Visitante, Integer> {

	@Autowired
	private VisitanteMediator visitanteMediator;

	@Autowired
	private SmartValidator smartValidator;

	@Override
	protected void addCollections(ModelAndView mavError, Visitante entity) {
		mavError.addObject("ufs", Arrays.asList(Estado.values()));
	}

	@Override
	protected AbstractMediator<Visitante, Integer> getMediatorBean() {
		return visitanteMediator;
	}

	@Override
	protected Visitante getNewEntity() {
		Visitante visitante = super.getNewEntity();
		visitante.setUf(Estado.PA);
		visitante.gerarToken();
		return visitante;
	}

	/**
	 * Buscar visitante pelo número do documento.
	 * 
	 * @param documento
	 * @return
	 */
	@JsonView(PesquisaDoc.class)
	@GetMapping(value = "/getByDocumento", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Visitante getByDocumento(@RequestParam("term") String documento) {
		return visitanteMediator.findByDocumento(documento);
	}

	@PostMapping("")
	@PreAuthorize(Roles.PADRAO)
	@Override
	public ModelAndView salvar(@ModelAttribute("visitante") Visitante visitante, BindingResult result, RedirectAttributes redirectAttributes) {
		if (visitante.getEndereco() != null && !visitante.getEndereco().algumDadoPreenchido()) {
			visitante.setEndereco(null);
		}
		smartValidator.validate(visitante, result);
		return super.salvar(visitante, result, redirectAttributes);
	}

	/**
	 * Gravar imagem no servidor.
	 * 
	 * @param tokenFoto
	 * @param conteudoArquivo
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/gravarImagem")
	public ResponseEntity<String> gravarImagem(@RequestParam("tokenFoto") String tokenFoto, InputStream conteudoArquivo) throws IOException {
		visitanteMediator.gravarImagemTemporaria(tokenFoto, conteudoArquivo);
		return ResponseEntity.ok("upload_complete");
	}

	/**
	 * Remover foto temporária.
	 * 
	 * @param tokenFoto
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/removerFotoTemporaria")
	public ResponseEntity<String> removerFotoTemporaria(@RequestParam("tokenFoto") String tokenFoto) throws IOException {
		visitanteMediator.removerFotoTemporaria(tokenFoto);
		return ResponseEntity.ok("removido");
	}

	/**
	 * Realizar o download de uma foto de visitante.
	 * 
	 * @param documento
	 * @param tokenFoto
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/foto", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public FileSystemResource downloadFoto(@RequestParam(value = "documento", required = false) String documento, @RequestParam(value = "tokenFoto", required = false) String tokenFoto)
			throws IOException {
		Path path = visitanteMediator.getFotoByDocumento(documento, tokenFoto);
		return new FileSystemResource(path.toFile());
	}
}
