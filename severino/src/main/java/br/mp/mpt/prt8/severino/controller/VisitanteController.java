package br.mp.mpt.prt8.severino.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
import br.mp.mpt.prt8.severino.validators.CadastrarVisita;

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
		return visitante;
	}

	/**
	 * Buscar visitante pelo número do documento.
	 * 
	 * @param documento
	 * @return
	 */
	@JsonView(DataTablesOutput.View.class)
	@GetMapping(value = "/getByDocumento", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Visitante getByDocumento(@RequestParam("term") String documento) {
		return visitanteMediator.findByDocumento(documento);
	}

	@PostMapping("")
	@PreAuthorize(Roles.PADRAO)
	@Override
	public ModelAndView salvar(@Validated(CadastrarVisita.class) Visitante entidade, BindingResult result, RedirectAttributes redirectAttributes) {
		return super.salvar(entidade, result, redirectAttributes);
	}
}
