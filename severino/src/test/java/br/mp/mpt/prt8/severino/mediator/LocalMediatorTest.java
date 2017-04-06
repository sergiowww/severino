package br.mp.mpt.prt8.severino.mediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.Search;

import br.mp.mpt.prt8.severino.dao.LocalRepository;
import br.mp.mpt.prt8.severino.dao.OrganizacaoRepository;
import br.mp.mpt.prt8.severino.entity.Local;

/**
 * Classe de teste do local.
 * 
 * @author sergio.eoliveira
 *
 */
public class LocalMediatorTest extends AbstractSeverinoTests {
	@Autowired
	private LocalMediator localMediator;

	@Autowired
	private LocalRepository localRepository;

	@Autowired
	private OrganizacaoRepository organizacaoRepository;

	@Test
	public void testFindByDn() {
		test1();
		test2();
		test3();
	}

	private void test3() {
		Local local = localMediator.findByDn("CN=Anny Claudia Silva Neves,OU=Usuarios,OU=Sede,OU=PRT06,OU=MPT,DC=mpt,DC=intra");
		assertEquals("Sede", local.getNome());
		assertNotNull(local.getOrganizacao());
		assertEquals("PRT06", local.getOrganizacao().getNome());
		List<Local> registros = localRepository.findAll();
		assertEquals(2, registros.size());
		Local loc0 = registros.get(0);
		Local loc1 = registros.get(1);
		assertNotEquals(loc0.getOrganizacao(), loc1.getOrganizacao());
	}

	private void test2() {
		Local local = localMediator.findByDn("cn=André Luis Lima Saldanha,ou=Usuarios,ou=Sede,ou=PRT08,ou=MPT,dc=mpt,dc=intra");
		assertEquals("Sede", local.getNome());
		assertNotNull(local.getOrganizacao());
		assertEquals("PRT08", local.getOrganizacao().getNome());

		assertRegistros();
	}

	private void assertRegistros() {
		assertTrue(localRepository.findAll().stream().anyMatch(p -> "Sede".equals(p.getNome())));
		assertTrue(organizacaoRepository.findAll().stream().anyMatch(p -> "PRT08".equals(p.getNome())));
	}

	private void test1() {
		Local local = localMediator.findByDn("cn=Sergio Eduardo Dantas de Oliveira,ou=Usuarios,ou=Sede,ou=PRT08,ou=MPT,dc=mpt,dc=intra");
		assertEquals("Sede", local.getNome());
		assertNotNull(local.getOrganizacao());
		assertEquals("PRT08", local.getOrganizacao().getNome());

		assertRegistros();
	}

	@Test
	public void testFind() throws Exception {
		assertNotNull(localMediator.findByDn("cn=Sergio Eduardo Dantas de Oliveira,ou=Usuarios,ou=Sede,ou=PRT08,ou=MPT,dc=mpt,dc=intra"));
		DataTablesInput dataTablesInput = new DataTablesInput();
		dataTablesInput.setStart(0);
		dataTablesInput.setLength(4);
		dataTablesInput.setSearch(new Search(null, false));
		Page<Local> resultado = localMediator.find(dataTablesInput);
		assertEquals(1, resultado.getTotalElements());
	}
}
