package br.mp.mpt.prt8.severino.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.mp.mpt.prt8.severino.entity.ControleMotorista;
import br.mp.mpt.prt8.severino.mediator.ControleMotoristaMediator;
import br.mp.mpt.prt8.severino.mediator.PessoaDisponibilidadeMediator;
import br.mp.mpt.prt8.severino.mediator.UsuarioMediator;
import br.mp.mpt.prt8.severino.utils.DateUtils;
import br.mp.mpt.prt8.severino.utils.Roles;
import br.mp.mpt.prt8.severino.valueobject.PessoaDisponibilidade;

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

	@Autowired
	private PessoaDisponibilidadeMediator pessoaDisponibilidadeMediator;

	@Autowired
	private ControleMotoristaMediator controleMotoristaMediator;

	/**
	 * Url de início.
	 * 
	 * @param auth
	 * @return
	 */
	@GetMapping("/")
	public ModelAndView inicio(Authentication auth) {
		usuarioMediator.save(auth);
		ModelAndView mav = new ModelAndView("home");
		Date inicio = DateUtils.toDate(LocalDateTime.now().minusDays(10));
		Date fim = new Date();
		List<PessoaDisponibilidade> pessoas = pessoaDisponibilidadeMediator.findUltimaDisponibilidade(inicio, fim);
		mav.addObject("totalPessoasNaCasa", pessoas.stream().filter(p -> p.isEntrou()).count());
		mav.addObject("pessoasDisponiveis", pessoas);

		List<ControleMotorista> motoristas = controleMotoristaMediator.findDisponiveis();
		mav.addObject("totalMotoristasNaCasa", motoristas.stream().filter(m -> m.isFluxoEntrada()).count());
		mav.addObject("controleMotoristas", motoristas);
		return mav;
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
