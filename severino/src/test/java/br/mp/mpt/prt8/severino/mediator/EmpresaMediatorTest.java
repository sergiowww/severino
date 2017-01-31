package br.mp.mpt.prt8.severino.mediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.Search;

import br.mp.mpt.prt8.severino.entity.Empresa;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Testes da empresa.
 * 
 * @author sergio.eoliveira
 *
 */
public class EmpresaMediatorTest extends AbstractSeverinoTests {

	private static final String NOME_EMPRESA2 = "Teste 11";
	private static final String NOME_EMPRESA1 = "Teste 12";
	@Autowired
	private EmpresaMediator empresaMediator;

	@Before
	public void setUp() {
		Empresa empresa1 = new Empresa();
		empresa1.setNome(NOME_EMPRESA1);
		Empresa empresa2 = new Empresa();
		empresa2.setNome(NOME_EMPRESA2);
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
		dataTablesInput.setSearch(new Search("Teste 1", false));
		Page<Empresa> resultado = empresaMediator.find(dataTablesInput);
		assertEquals(2, resultado.getTotalElements());
	}

	@Test
	public void testFindAll() throws Exception {
		DataTablesInput dataTablesInput = new DataTablesInput();
		dataTablesInput.setStart(0);
		dataTablesInput.setLength(4);
		Page<Empresa> resultado = empresaMediator.find(dataTablesInput);
		assertEquals(2, resultado.getTotalElements());
	}

	@Test(expected = NegocioException.class)
	public void testSaveEmpresaDuplicada() throws Exception {
		Empresa empresaDuplicada = new Empresa();
		empresaDuplicada.setNome(NOME_EMPRESA1);
		empresaMediator.save(empresaDuplicada);
	}

	@Test(expected = NegocioException.class)
	public void testAlterarParaUmNomeQueJaExiste() throws Exception {
		List<Empresa> empresas = findAll();
		Empresa empresa1 = empresas.stream().filter(e -> e.getNome().equals(NOME_EMPRESA1)).findFirst().get();
		Empresa empresa2 = empresas.stream().filter(e -> e.getNome().equals(NOME_EMPRESA2)).findFirst().get();

		empresa1.setNome(empresa2.getNome());
		empresaMediator.save(empresa1);
	}

	private List<Empresa> findAll() {
		TypedQuery<Empresa> query = entityManager.createQuery("from " + Empresa.class.getSimpleName(), Empresa.class);
		return query.getResultList();
	}

	@Test
	public void testFindOne() throws Exception {
		List<Empresa> empresas = findAll();
		assertNotNull(empresaMediator.findOne(empresas.stream().filter(e -> e.getNome().equals(NOME_EMPRESA1)).map(e -> e.getId()).findFirst()));
		assertNull(empresaMediator.findOne(empresas.stream().filter(e -> e.getNome().equals("nome que não existe")).map(e -> e.getId()).findFirst()));
	}

	@Test
	public void testApagar() throws Exception {
		List<Empresa> empresas = findAll();
		Empresa empresa1 = empresas.stream().filter(e -> e.getNome().equals(NOME_EMPRESA1)).findFirst().get();
		empresaMediator.apagar(empresa1.getId());
		entityManager.flush();
		entityManager.clear();

		assertEquals(1, (empresas = findAll()).size());
		assertFalse(empresas.stream().filter(e -> e.getNome().equals(NOME_EMPRESA1)).findFirst().isPresent());
	}

}
