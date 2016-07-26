package br.mp.mpt.prt8.severino.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.mp.mpt.prt8.severino.mediator.DeployInfoMediator;

/**
 * Controlador para logar no sistema.
 * 
 * @author sergio.eoliveira
 *
 */
@Controller
public class LoginController {

	@Autowired
	private DeployInfoMediator deploInfoMediator;

	/**
	 * Apresentar as mensagens de retorno após a validação do login.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		if (request.getParameterMap().containsKey("error")) {
			model.addObject("error", "Usuário ou senha inválidos!");
		}

		if (request.getParameterMap().containsKey("logout")) {
			model.addObject("msg", "Sessão finalizada, efetue login para entrar novamente");
		}
		model.addObject("dataHoraPublicacao", deploInfoMediator.getDataHoraPublicacao());
		return model;

	}

}
