package br.mp.mpt.prt8.severino.mediator;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.parameter.SearchParameter;

import br.mp.mpt.prt8.severino.entity.Empresa;

/**
 * Testes da empresa.
 * 
 * @author sergio.eoliveira
 *
 */
public class EmpresaMediatorTest extends AbstractSeverinoTests {

	@Autowired
	private EmpresaMediator empresaMediator;

	@Before
	public void setUp() {
		Empresa empresa1 = new Empresa();
		empresa1.setNome("Teste 12");
		Empresa empresa2 = new Empresa();
		empresa2.setNome("Teste 11");
		empresaMediator.save(empresa1);
		empresaMediator.save(empresa2);
	}

	@Test
	public void testFindByParteNome() {

		List<Empresa> resultado = empresaMediator.findByParteNome("Teste 1");
		assertEquals(2, resultado.size());
		assertEquals(2, resultado.stream().filter(e -> e.getNome().contains("Teste")).count());
	}

	@Test
	public void testFind() {
		DataTablesInput dataTablesInput = new DataTablesInput();
		dataTablesInput.setStart(0);
		dataTablesInput.setLength(4);
		dataTablesInput.setSearch(new SearchParameter("Teste", false));
		Page<Empresa> resultado = empresaMediator.find(dataTablesInput);
		assertEquals(2, resultado.getTotalElements());
	}

}
