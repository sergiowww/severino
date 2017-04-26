package br.mp.mpt.prt8.severino.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.mp.mpt.prt8.severino.entity.ControleMotorista;
import br.mp.mpt.prt8.severino.entity.Fluxo;
import br.mp.mpt.prt8.severino.mediator.AbstractMediator;
import br.mp.mpt.prt8.severino.mediator.ControleMotoristaMediator;
import br.mp.mpt.prt8.severino.mediator.MotoristaMediator;

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
	protected AbstractMediator<ControleMotorista, Integer> getMediatorBean() {
		return controleMotoristaMediator;
	}

	@Override
	protected void addCollections(ModelAndView mav, ControleMotorista entity) {
		mav.addObject("fluxos", Arrays.asList(Fluxo.values()));
		mav.addObject("motoristas", motoristaMediator.findAllMotoristasTecnicos());
	}

}
