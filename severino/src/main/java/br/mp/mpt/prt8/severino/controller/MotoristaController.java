package br.mp.mpt.prt8.severino.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.mp.mpt.prt8.severino.entity.Cargo;
import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.mediator.AbstractMediator;
import br.mp.mpt.prt8.severino.mediator.LocalMediator;
import br.mp.mpt.prt8.severino.mediator.MotoristaMediator;
import br.mp.mpt.prt8.severino.mediator.UsuarioHolder;

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

	@Autowired
	private LocalMediator localMediator;

	@Autowired
	private UsuarioHolder usuarioHolder;

	@Override
	protected AbstractMediator<Motorista, Integer> getMediatorBean() {
		return motoristaMediator;
	}

	@Override
	protected Motorista getNewEntity() {
		Motorista motorista = super.getNewEntity();
		motorista.setLocal(usuarioHolder.getLocal());
		return motorista;
	}

	@Override
	protected void addCollections(ModelAndView mav, Motorista entity) {
		mav.addObject("cargos", Arrays.asList(Cargo.values()));
		mav.addObject("locais", localMediator.findAll());
	}

}
