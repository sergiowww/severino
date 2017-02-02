package br.mp.mpt.prt8.severino.controller;

import java.util.Arrays;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.mp.mpt.prt8.severino.entity.ControleMotorista;
import br.mp.mpt.prt8.severino.entity.Fluxo;
import br.mp.mpt.prt8.severino.mediator.AbstractExampleMediator;
import br.mp.mpt.prt8.severino.mediator.ControleMotoristaMediator;
import br.mp.mpt.prt8.severino.mediator.MotoristaMediator;
import br.mp.mpt.prt8.severino.utils.Roles;
import br.mp.mpt.prt8.severino.validators.CadastrarViagem;

/**
 * Controlador de operações da entidade.
 * 
 * @author sergio.eoliveira
 *
 */
@Controller
@RequestMapping("/controleMotorista")
public class ControleMotoristaController extends AbstractFullCrudController<ControleMotorista, Integer> {

	@Autowired
	private ControleMotoristaMediator controleMotoristaMediator;

	@Autowired
	private MotoristaMediator motoristaMediator;

	@Override
	protected AbstractExampleMediator<ControleMotorista, Integer> getMediatorBean() {
		return controleMotoristaMediator;
	}

	@Override
	protected void addCollections(ModelAndView mav, ControleMotorista entity) {
		mav.addObject("fluxos", Arrays.asList(Fluxo.values()));
		mav.addObject("motoristas", motoristaMediator.findAllMotoristasTecnicos());
	}

	@PostMapping("")
	@PreAuthorize(Roles.PADRAO)
	@Override
	public ModelAndView salvar(@Validated({ CadastrarViagem.class, Default.class }) ControleMotorista entity, BindingResult result, RedirectAttributes redirectAttributes) {
		return super.salvar(entity, result, redirectAttributes);
	}
}
