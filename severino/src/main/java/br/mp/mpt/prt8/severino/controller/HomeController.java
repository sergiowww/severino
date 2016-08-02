package br.mp.mpt.prt8.severino.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.mp.mpt.prt8.severino.mediator.UsuarioMediator;
import br.mp.mpt.prt8.severino.utils.Roles;

/**
 * Controlador da tela inicial.
 * 
 * @author sergio.eoliveira
 *
 */
@Controller
public class HomeController {

	@Autowired
	private UsuarioMediator usuarioMediator;

	/**
	 * Url de início.
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("/")
	public String inicio(Authentication auth) {
		usuarioMediator.save(auth);
		return "home";
	}

	/**
	 * Url para acesso negado.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/403", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView acessoNegado() {
		ModelAndView mav = new ModelAndView("erro/403");
		mav.addObject("roleNameSeverino", Roles.GRUPO_SEVERINO_ACTIVE_DIRECTORY);
		return mav;
	}
}
