package br.mp.mpt.prt8.severino.mediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.Search;
import org.springframework.test.context.ContextConfiguration;

import br.mp.mpt.prt8.severino.entity.Setor;
import br.mp.mpt.prt8.severino.mediator.carga.CargaUsuario;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Testes do setor.
 * 
 * @author sergio.eoliveira
 *
 */
@ContextConfiguration(classes = CargaUsuario.class)
public class SetorMediatorTest extends AbstractSeverinoTests {

	private static final String NOME_SETOR1 = "SETOR 1";
	private static final String NOME_SETOR2 = "setor 2";

	@Autowired
	private SetorMediator setorMediator;

	@Autowired
	private UsuarioHolder usuarioHolder;

	@Before
	public void setUp() {
		Setor setor1 = new Setor();
		setor1.setAndar((short) 1);
		setor1.setNome(NOME_SETOR1);
		setor1.setSala("123");
		setor1.setLocal(usuarioHolder.getLocal());
		setorMediator.save(setor1);

		Setor setor2 = new Setor();
		setor2.setAndar((short) 1);
		setor2.setNome(NOME_SETOR2);
		setor2.setSala("142");
		setor2.setLocal(usuarioHolder.getLocal());
		setorMediator.save(setor2);
	}

	@Test
	public void testFind() {
		DataTablesInput dataTablesInput = new DataTablesInput();
		dataTablesInput.setStart(0);
		dataTablesInput.setLength(4);
		dataTablesInput.setSearch(new Search(NOME_SETOR2, false));
		dataTablesInput.addColumn("local.titulo", true, true, null);
		Page<Setor> resultado = setorMediator.find(dataTablesInput);
		assertEquals(1, resultado.getTotalElements());
		dataTablesInput.setSearch(new Search(null, false));
		resultado = setorMediator.find(dataTablesInput);
		assertEquals(2, resultado.getTotalElements());
	}

	@Test
	public void testFindAll() throws Exception {
		List<Setor> resultado = setorMediator.findAll();
		assertEquals(2, resultado.size());
		assertTrue(resultado.stream().filter(s -> s.getNome().equals(NOME_SETOR1)).findFirst().isPresent());
		assertTrue(resultado.stream().filter(s -> s.getNome().equals(NOME_SETOR2)).findFirst().isPresent());
	}

	@Test(expected = NegocioException.class)
	public void testSaveMesmoNome() throws Exception {
		Setor setor1 = new Setor();
		setor1.setAndar((short) 1);
		setor1.setNome(NOME_SETOR1);
		setor1.setSala("123");
		setorMediator.save(setor1);
	}

	@Test(expected = NegocioException.class)
	public void testAlterarParaMesmoNome() throws Exception {

		List<Setor> resultado = setorMediator.findAll();
		assertEquals(2, resultado.size());
		Setor setor1 = resultado.stream().filter(s -> s.getNome().equals(NOME_SETOR1)).findFirst().get();
		Setor setor2 = resultado.stream().filter(s -> s.getNome().equals(NOME_SETOR2)).findFirst().get();

		setor1.setNome(setor2.getNome());
		setor1.setSala(setor2.getSala());
		setorMediator.save(setor1);
	}

	@Test(expected = NegocioException.class)
	public void testAlterarMesmoNomeSetorSemSala() throws Exception {
		List<Setor> resultado = setorMediator.findAll();
		assertEquals(2, resultado.size());
		Setor setor1 = resultado.stream().filter(s -> s.getNome().equals(NOME_SETOR1)).findFirst().get();
		Setor setor2 = resultado.stream().filter(s -> s.getNome().equals(NOME_SETOR2)).findFirst().get();

		setor1.setSala(null);
		setor2.setSala(null);
		try {

			setorMediator.save(setor1);
			setorMediator.save(setor2);
		} catch (NegocioException e) {
			fail("Não era pra ter dado erro");
		}

		setor1.setNome(setor2.getNome());
		setor1.setSala(setor2.getSala());
		setorMediator.save(setor1);

	}

}
