package br.mp.mpt.prt8.severino.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.mp.mpt.prt8.severino.entity.Usuario;
import br.mp.mpt.prt8.severino.mediator.AbstractExampleMediator;
import br.mp.mpt.prt8.severino.mediator.UsuarioMediator;

/**
 * Controlador de operações da entidade.
 * 
 * @author sergio.eoliveira
 *
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioController extends AbstractViewDataController<Usuario, String> {

	@Autowired
	private UsuarioMediator usuarioMediator;

	@Override
	protected AbstractExampleMediator<Usuario, String> getMediatorBean() {
		return usuarioMediator;
	}

}
