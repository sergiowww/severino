package br.mp.mpt.prt8.severino.controller;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.mp.mpt.prt8.severino.entity.Estado;
import br.mp.mpt.prt8.severino.entity.Visita;
import br.mp.mpt.prt8.severino.entity.Visitante;
import br.mp.mpt.prt8.severino.mediator.AbstractMediator;
import br.mp.mpt.prt8.severino.mediator.SetorMediator;
import br.mp.mpt.prt8.severino.mediator.VisitaMediator;
import br.mp.mpt.prt8.severino.utils.Roles;

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

	@Autowired
	private SmartValidator smartValidator;

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
	protected Visita getNewEntity() {
		Visita visita = super.getNewEntity();
		visita.setVisitante(new Visitante());
		visita.getVisitante().gerarToken();
		return visita;
	}

	@Override
	protected String redirectAposGravar(Visita entity) {
		return "redirect:/visita/" + entity.getId();
	}

	/**
	 * Exibir tela com detalhes da visita.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping({ "/detalhe/{id:.+}", "/detalhe/" })
	public ModelAndView detalhar(@PathVariable Optional<Integer> id) {
		ModelAndView mav = new ModelAndView(getModelName() + "-detalhe");
		Visita visita = visitaMediator.findOne(id);
		if (visita != null) {
			mav.addObject("visita", visita);
			mav.addObject("visitantesSemBaixa", visitaMediator.findVisitasSemBaixa());
			return mav;
		}
		return new ModelAndView("redirect:/visita/");
	}

	@PostMapping("")
	@PreAuthorize(Roles.PADRAO)
	@Override
	public ModelAndView salvar(@ModelAttribute("visita") Visita visita, BindingResult result, RedirectAttributes redirectAttributes) {
		Visitante visitante = visita.getVisitante();
		if (visitante.getEndereco() != null && !visitante.getEndereco().algumDadoPreenchido()) {
			visitante.setEndereco(null);
		}
		smartValidator.validate(visita, result);
		return super.salvar(visita, result, redirectAttributes);
	}

}
