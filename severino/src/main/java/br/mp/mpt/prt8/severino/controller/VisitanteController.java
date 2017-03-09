package br.mp.mpt.prt8.severino.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonView;

import br.mp.mpt.prt8.severino.entity.Estado;
import br.mp.mpt.prt8.severino.entity.Visitante;
import br.mp.mpt.prt8.severino.mediator.AbstractExampleMediator;
import br.mp.mpt.prt8.severino.mediator.VisitanteMediator;
import br.mp.mpt.prt8.severino.utils.Roles;
import br.mp.mpt.prt8.severino.validators.CadastrarVisita;
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
	protected AbstractExampleMediator<Visitante, Integer> getMediatorBean() {
		return visitanteMediator;
	}

	@Override
	protected Visitante getNewEntity() {
		Visitante visitante = super.getNewEntity();
		visitante.setUf(Estado.PA);
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
		smartValidator.validate(visitante, result, CadastrarVisita.class);
		return super.salvar(visitante, result, redirectAttributes);
	}
}
