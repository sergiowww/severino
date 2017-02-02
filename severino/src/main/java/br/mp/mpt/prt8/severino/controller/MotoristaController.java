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

import br.mp.mpt.prt8.severino.entity.Cargo;
import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.mediator.AbstractExampleMediator;
import br.mp.mpt.prt8.severino.mediator.MotoristaMediator;
import br.mp.mpt.prt8.severino.utils.Roles;
import br.mp.mpt.prt8.severino.validators.CadastrarMotorista;

/**
 * Controlador de operações da entidade.
 * 
 * @author sergio.eoliveira
 *
 */
@Controller
@RequestMapping("/motorista")
public class MotoristaController extends AbstractFullCrudController<Motorista, Integer> {

	@Autowired
	private MotoristaMediator motoristaMediator;

	@Override
	protected AbstractExampleMediator<Motorista, Integer> getMediatorBean() {
		return motoristaMediator;
	}

	@Override
	protected void addCollections(ModelAndView mav, Motorista entity) {
		mav.addObject("cargos", Arrays.asList(Cargo.values()));
	}

	@PostMapping("")
	@PreAuthorize(Roles.PADRAO)
	@Override
	public ModelAndView salvar(@Validated(CadastrarMotorista.class) Motorista entity, BindingResult result, RedirectAttributes redirectAttributes) {
		return super.salvar(entity, result, redirectAttributes);
	}

}
