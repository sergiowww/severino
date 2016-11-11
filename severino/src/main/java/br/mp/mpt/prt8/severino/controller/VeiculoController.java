package br.mp.mpt.prt8.severino.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
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

import com.fasterxml.jackson.annotation.JsonView;

import br.mp.mpt.prt8.severino.entity.Veiculo;
import br.mp.mpt.prt8.severino.mediator.AbstractMediator;
import br.mp.mpt.prt8.severino.mediator.MotoristaMediator;
import br.mp.mpt.prt8.severino.mediator.VeiculoMediator;
import br.mp.mpt.prt8.severino.utils.Roles;
import br.mp.mpt.prt8.severino.validators.CadastrarVeiculo;

/**
 * Controlador de operações da entidade.
 * 
 * @author sergio.eoliveira
 *
 */
@Controller
@RequestMapping("/veiculo")
public class VeiculoController extends AbstractFullCrudController<Veiculo, String> {

	@Autowired
	private VeiculoMediator veiculoMediator;

	@Autowired
	private MotoristaMediator motoristaMediator;

	@Override
	protected AbstractMediator<Veiculo, String> getMediatorBean() {
		return veiculoMediator;
	}

	@Override
	protected void addCollections(ModelAndView mav, Veiculo entity) {
		mav.addObject("motoristas", motoristaMediator.findAll());
	}

	@PostMapping("")
	@PreAuthorize(Roles.PADRAO)
	@Override
	public ModelAndView salvar(@Validated(CadastrarVeiculo.class) Veiculo entity, BindingResult result, RedirectAttributes redirectAttributes) {
		return super.salvar(entity, result, redirectAttributes);
	}

	/**
	 * Buscar dados do veículo pelo número da placa.
	 * 
	 * @param placa
	 * @return
	 */
	@GetMapping(value = "/buscarPorPlaca", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@JsonView(DataTablesOutput.View.class)
	public Veiculo findByPlaca(@RequestParam("placa") String placa) {
		return veiculoMediator.findByPlaca(placa);
	}
}
