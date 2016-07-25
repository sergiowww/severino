package br.mp.mpt.prt8.severino.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.mp.mpt.prt8.severino.entity.Estado;
import br.mp.mpt.prt8.severino.entity.Visita;
import br.mp.mpt.prt8.severino.mediator.AbstractMediator;
import br.mp.mpt.prt8.severino.mediator.SetorMediator;
import br.mp.mpt.prt8.severino.mediator.VisitaMediator;
import br.mp.mpt.prt8.severino.utils.Roles;
import br.mp.mpt.prt8.severino.validatorgroups.CadastrarVisita;
import br.mp.mpt.prt8.severino.valueobject.PessoaLdap;

/**
 * Controlador de operações da entidade. <br>
 * 
 * @author sergio.eoliveira
 *
 */
@Controller
@RequestMapping("/visita")
public class VisitaController extends AbstractFullCrudController<Visita, Integer> {

	@Autowired
	private VisitaMediator visitaMediator;

	@Autowired
	private SetorMediator setorMediator;

	@Override
	protected void addCollections(ModelAndView mav) {
		mav.addObject("setores", setorMediator.findAll());
		mav.addObject("ufs", Arrays.asList(Estado.values()));
		mav.addObject("visitantesSemBaixa", visitaMediator.findVisitasSemBaixa());
	}

	@Override
	protected AbstractMediator<Visita, Integer> getMediatorBean() {
		return visitaMediator;
	}

	@Override
	protected String redirectAposGravar(Visita entity) {
		return "redirect:/visita/" + entity.getId();
	}

	/**
	 * Listar pessoas por parte do nome.
	 * 
	 * @param nome
	 * @return
	 */
	@GetMapping(value = "/listarPessoasPorParteNome", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<PessoaLdap> findResponsaveisByNome(@RequestParam("term") String nome) {
		return visitaMediator.findResponsaveisByNome(nome);
	}

	@PostMapping("")
	@PreAuthorize(Roles.PADRAO)
	public ModelAndView salvar(@Validated(CadastrarVisita.class) Visita entity, BindingResult result, RedirectAttributes redirectAttributes) {
		return super.salvar(entity, result, redirectAttributes);
	}

}
