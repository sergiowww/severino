package br.mp.mpt.prt8.severino.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.mp.mpt.prt8.severino.entity.Setor;
import br.mp.mpt.prt8.severino.mediator.AbstractMediator;
import br.mp.mpt.prt8.severino.mediator.SetorMediator;

/**
 * Controlador de operações da entidade.
 * 
 * @author sergio.eoliveira
 *
 */
@Controller
@RequestMapping("/setor")
public class SetorController extends AbstractFullCrudController<Setor, Integer> {

	@Autowired
	private SetorMediator setorMediator;

	@Override
	protected AbstractMediator<Setor, Integer> getMediatorBean() {
		return setorMediator;
	}

}
