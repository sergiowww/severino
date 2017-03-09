package br.mp.mpt.prt8.severino.mediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Objects;

import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.Search;

import br.mp.mpt.prt8.severino.entity.Endereco;
import br.mp.mpt.prt8.severino.entity.Estado;
import br.mp.mpt.prt8.severino.entity.Visitante;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Teste para o visitante.
 * 
 * @author sergio.eoliveira
 *
 */
public class VisitanteMediatorTest extends AbstractSeverinoTests {

	private static final String DOCUMENTO1 = "1234";
	private static final String NOME_VISITANTE1 = "João do Caminhão";
	@Autowired
	private VisitanteMediator visitanteMediator;

	@Before
	public void setUp() {
		Visitante visitante1 = new Visitante();
		visitante1.setDocumento(DOCUMENTO1);
		visitante1.setNome(NOME_VISITANTE1);
		visitante1.setOrgaoEmissor("SSP");
		visitante1.setUf(Estado.DF);
		visitante1.setTelefone("(61) 99601-3799");

		Visitante visitante2 = new Visitante();
		visitante2.setDocumento("45621");
		visitante2.setNome("Manuel de Portugal");
		visitante2.setOrgaoEmissor("SSP");
		visitante2.setUf(Estado.SP);

		visitanteMediator.save(visitante1);
		visitanteMediator.save(visitante2);
	}

	@Test
	public void testSalvarComEndereco() throws Exception {
		Visitante visitante = new Visitante();
		String documentoTeste = "123456789";
		visitante.setDocumento(documentoTeste);
		visitante.setEmail("sergiowww@gmail.com");
		visitante.setNome("Sergio Eduardo");
		visitante.setOrgaoEmissor("SSP");
		visitante.setProfissao("Analista de TI");
		visitante.setTelefone("(61) 99601-3799");
		visitante.setTelefoneAlternativo("(91) 3199-3760");
		visitante.setUf(Estado.DF);

		Endereco endereco = new Endereco();
		endereco.setBairro("Reduto");
		endereco.setCep("70000-210");
		endereco.setComplemento("Apto 1504");
		endereco.setLogradouro("Tiradentes");
		endereco.setMunicipio("Belém");
		endereco.setNumero("539");
		endereco.setReferencia("Esquina");
		endereco.setUf(Estado.PA);
		visitante.setEndereco(endereco);

		visitanteMediator.save(visitante);
		entityManager.flush();
		entityManager.clear();
		Visitante visitanteGravado = visitanteMediator.findByDocumento(documentoTeste);
		assertVisitante(visitanteGravado, visitante);

		String referencia = "Outro lugar";
		visitanteGravado.getEndereco().setReferencia(referencia);
		visitanteGravado.setTelefone("(61) 99601-3799");
		visitanteGravado.setTelefoneAlternativo("(91) 3199-3760");
		visitanteGravado.getEndereco().setCep("70000-210");
		visitanteGravado.getEndereco().setId(null);
		entityManager.clear();
		visitanteMediator.save(visitanteGravado);
		entityManager.flush();
		entityManager.clear();
		visitanteGravado = visitanteMediator.findByDocumento(documentoTeste);
		assertEquals(referencia, visitanteGravado.getEndereco().getReferencia());

	}

	@Test
	public void testAlterarEndereco() throws Exception {
		Visitante visitante = new Visitante();
		String documentoTeste = "12345678440";
		visitante.setDocumento(documentoTeste);
		visitante.setNome("Sergio Eduardo2");
		visitante.setOrgaoEmissor("SSP");
		visitante.setUf(Estado.DF);

		Endereco endereco = new Endereco();
		endereco.setBairro("Reduto");
		endereco.setCep("70000-210");
		endereco.setComplemento("Apto 1504");
		endereco.setLogradouro("Tiradentes");
		endereco.setMunicipio("Belém");
		endereco.setNumero("539");
		endereco.setReferencia("Esquina");
		endereco.setUf(Estado.PA);
		visitante.setEndereco(endereco);

		visitanteMediator.save(visitante);
		entityManager.flush();
		entityManager.clear();

		Visitante visitanteGravado = visitanteMediator.findByDocumento(documentoTeste);
		assertNotNull(visitanteGravado.getEndereco());
		visitanteGravado.getEndereco().setBairro("São Brás");
		visitanteGravado.getEndereco().setCep("70000-210");
		visitanteMediator.save(visitanteGravado);
		entityManager.flush();
		entityManager.clear();
		visitanteGravado = visitanteMediator.findByDocumento(documentoTeste);
		assertNotNull(visitanteGravado.getEndereco());
		assertEquals("São Brás", visitanteGravado.getEndereco().getBairro());

		visitanteGravado.setEndereco(new Endereco());
		visitanteMediator.save(visitanteGravado);
		entityManager.flush();
		entityManager.clear();
		visitanteGravado = visitanteMediator.findByDocumento(documentoTeste);
		assertNull(visitanteGravado.getEndereco());
		assertEquals(0, entityManager.createQuery("from Endereco", Endereco.class).getResultList().size());

	}

	@Test
	public void testSalvarComEnderecoVazio() throws Exception {
		Visitante visitante = new Visitante();
		String documentoTeste = "12345678910";
		visitante.setDocumento(documentoTeste);
		visitante.setNome("Sergio Eduardo");
		visitante.setOrgaoEmissor("SSP");
		visitante.setUf(Estado.DF);
		visitante.setEndereco(new Endereco());
		visitanteMediator.save(visitante);
		entityManager.flush();
		entityManager.clear();
		Visitante visitanteGravado = visitanteMediator.findByDocumento(documentoTeste);
		assertNull(visitanteGravado.getEndereco());
	}

	private void assertVisitante(Visitante visitanteGravado, Visitante visitante) {
		assertNotSame(visitanteGravado, visitante);
		assertEquals(visitante.getTelefone(), visitanteGravado.getTelefone());
		assertEquals(visitante.getTelefoneAlternativo(), visitanteGravado.getTelefoneAlternativo());
		assertEquals(visitante.getDadosResumo(), visitante.getDadosResumo());
		assertNotNull(visitanteGravado.getEndereco());
		assertNotSame(visitanteGravado.getEndereco(), visitante.getEndereco());
		assertEquals(Objects.toString(visitanteGravado.getEndereco()), Objects.toString(visitante.getEndereco()));
		assertEquals(visitanteGravado.getEndereco().getReferencia(), visitanteGravado.getEndereco().getReferencia());
	}

	@Test
	public void testFind() {
		DataTablesInput dataTablesInput = new DataTablesInput();
		dataTablesInput.setStart(0);
		dataTablesInput.setLength(4);
		dataTablesInput.setSearch(new Search("joão", false));
		Page<Visitante> resultado = visitanteMediator.find(dataTablesInput);
		assertEquals(1, resultado.getTotalElements());
		assertEquals(NOME_VISITANTE1, resultado.getContent().get(0).getNome());
	}

	@Test(expected = NegocioException.class)
	public void testSaveVisitanteComMesmoDocumento() {
		Visitante visitante = new Visitante();
		visitante.setDocumento(DOCUMENTO1);
		visitante.setNome("Outro Cidadão");
		visitante.setOrgaoEmissor("SSP");
		visitante.setUf(Estado.AC);
		visitanteMediator.save(visitante);
	}

	@Test(expected = NegocioException.class)
	public void testAlterarParaUmDocumentoExistente() throws Exception {
		TypedQuery<Visitante> query = entityManager.createQuery("from " + Visitante.class.getSimpleName(), Visitante.class);
		List<Visitante> resultado = query.getResultList();
		Visitante visitante1 = resultado.stream().filter(v -> v.getDocumento().equals(DOCUMENTO1)).findFirst().get();
		Visitante visitante2 = resultado.stream().filter(v -> !v.getDocumento().equals(DOCUMENTO1)).findFirst().get();
		visitante2.setDocumento(visitante1.getDocumento());
		visitanteMediator.save(visitante2);
	}

	@Test
	public void testFindByDocumento() throws Exception {
		Visitante visitante = visitanteMediator.findByDocumento(DOCUMENTO1);
		assertNotNull(visitante);
		assertEquals(DOCUMENTO1, visitante.getDocumento());
		assertEquals("61996013799", visitante.getTelefone());
	}

}
