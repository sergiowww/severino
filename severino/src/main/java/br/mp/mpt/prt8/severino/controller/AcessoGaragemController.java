package br.mp.mpt.prt8.severino.controller;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.mp.mpt.prt8.severino.entity.AcessoGaragem;
import br.mp.mpt.prt8.severino.mediator.AbstractExampleMediator;
import br.mp.mpt.prt8.severino.mediator.AcessoGaragemMediator;
import br.mp.mpt.prt8.severino.mediator.VeiculoMediator;
import br.mp.mpt.prt8.severino.mediator.VisitaMediator;
import br.mp.mpt.prt8.severino.utils.Roles;
import br.mp.mpt.prt8.severino.validators.CadastrarVeiculo;
import br.mp.mpt.prt8.severino.validators.SelecionarVisita;

/**
 * Controlador de operações da entidade.
 *
 * @author sergio.eoliveira
 *
 */
@Controller
@RequestMapping("/acessoGaragem")
public class AcessoGaragemController extends AbstractFullCrudController<AcessoGaragem, Integer> {

	@Autowired
	private AcessoGaragemMediator acessoGaragemMediator;

	@Autowired
	private VisitaMediator visitaMediator;

	@Autowired
	private VeiculoMediator veiculoMediator;

	@Autowired
	private SmartValidator smartValidator;

	@Override
	protected AbstractExampleMediator<AcessoGaragem, Integer> getMediatorBean() {
		return acessoGaragemMediator;
	}

	@Override
	protected String redirectAposGravar(AcessoGaragem entity) {
		return "redirect:/acessoGaragem/" + entity.getId();
	}

	@Override
	protected void addCollections(ModelAndView mav, AcessoGaragem entity) {
		mav.addObject("servidoresMembros", veiculoMediator.findAllServidoresMembros());
		mav.addObject("acessosSemBaixa", acessoGaragemMediator.findAllSemBaixa());
		mav.addObject("visitasHoje", visitaMediator.findAllRegistradasHoje(entity.getVisita()));
	}

	@PostMapping("")
	@PreAuthorize(Roles.PADRAO)
	@Override
	public ModelAndView salvar(@ModelAttribute("acessoGaragem") AcessoGaragem acessoGaragem, BindingResult result, RedirectAttributes redirectAttributes) {
		if (acessoGaragem.isUsuarioVisitante()) {
			smartValidator.validate(acessoGaragem, result, Default.class, CadastrarVeiculo.class, SelecionarVisita.class);
		} else {
			smartValidator.validate(acessoGaragem, result);
		}
		return super.salvar(acessoGaragem, result, redirectAttributes);
	}
}
