package br.mp.mpt.prt8.severino.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import br.mp.mpt.prt8.severino.entity.Empresa;
import br.mp.mpt.prt8.severino.mediator.EmpresaMediator;

/**
 * Controlador de operações para a entidade empresa.
 * 
 * @author sergio.eoliveira
 *
 */
@Controller
@RequestMapping("/empresa")
public class EmpresaController extends AbstractFullCrudController<Empresa, Integer> {

	@Autowired
	private EmpresaMediator empresaMediator;

	@Override
	protected EmpresaMediator getMediatorBean() {
		return empresaMediator;
	}

	/**
	 * Listar empresas por parte do nome.
	 * 
	 * @return
	 */
	@GetMapping(value = "/listarPorNome", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody List<Empresa> listarPorNome(@RequestParam("term") String nomeEmpresa) {
		return getMediatorBean().findByParteNome(nomeEmpresa);
	}
}
