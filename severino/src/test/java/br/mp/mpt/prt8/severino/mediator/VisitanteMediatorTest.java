package br.mp.mpt.prt8.severino.mediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.parameter.SearchParameter;

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

		Visitante visitante2 = new Visitante();
		visitante2.setDocumento("45621");
		visitante2.setNome("Manuel de Portugal");
		visitante2.setOrgaoEmissor("SSP");
		visitante2.setUf(Estado.SP);

		visitanteMediator.save(visitante1);
		visitanteMediator.save(visitante2);
	}

	@Test
	public void testFind() {
		DataTablesInput dataTablesInput = new DataTablesInput();
		dataTablesInput.setStart(0);
		dataTablesInput.setLength(4);
		dataTablesInput.setSearch(new SearchParameter("joão", false));
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
	}

}
