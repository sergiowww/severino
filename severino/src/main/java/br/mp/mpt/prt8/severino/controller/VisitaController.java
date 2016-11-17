package br.mp.mpt.prt8.severino.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.mp.mpt.prt8.severino.entity.Estado;
import br.mp.mpt.prt8.severino.entity.Visita;
import br.mp.mpt.prt8.severino.mediator.AbstractMediator;
import br.mp.mpt.prt8.severino.mediator.SetorMediator;
import br.mp.mpt.prt8.severino.mediator.VisitaMediator;
import br.mp.mpt.prt8.severino.utils.Roles;
import br.mp.mpt.prt8.severino.validators.CadastrarVisita;

/**
 * Controlador de operações da entidade.
 * 
 * @author sergio.eoliveira
 *
 */
@Controller
@RequestMapping("/visita")
public class VisitaController extends AbstractFullCrudController<Visita, Integer> {

	@Autowired
	private VisitaMediator visitaMediator;

	@Autowired
	private SetorMediator setorMediator;

	@Override
	protected void addCollections(ModelAndView mav, Visita entity) {
		mav.addObject("setores", setorMediator.findAll());
		mav.addObject("ufs", Arrays.asList(Estado.values()));
		mav.addObject("visitantesSemBaixa", visitaMediator.findVisitasSemBaixa());
	}

	@Override
	protected AbstractMediator<Visita, Integer> getMediatorBean() {
		return visitaMediator;
	}

	@Override
	protected String redirectAposGravar(Visita entity) {
		return "redirect:/visita/" + entity.getId();
	}

	@PostMapping("")
	@PreAuthorize(Roles.PADRAO)
	@Override
	public ModelAndView salvar(@Validated(CadastrarVisita.class) Visita entity, BindingResult result, RedirectAttributes redirectAttributes) {
		return super.salvar(entity, result, redirectAttributes);
	}

}
