package br.mp.mpt.prt8.severino.mediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.parameter.SearchParameter;
import org.springframework.test.context.ContextConfiguration;

import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.entity.Veiculo;
import br.mp.mpt.prt8.severino.mediator.carga.CargaMotorista;

/**
 * Teste do veículo.
 * 
 * @author sergio.eoliveira
 *
 */
@ContextConfiguration(classes = { CargaMotorista.class })
public class VeiculoMediatorTest extends AbstractSeverinoTests {
	@Autowired
	private CargaMotorista cargaMotorista;

	@Autowired
	private VeiculoMediator veiculoMediator;

	private Veiculo veiculo3;

	private Veiculo veiculo2;

	private Veiculo veiculo1;

	@Before
	public void setUp() {
		Veiculo veiculo1 = new Veiculo();
		veiculo1.setId("JDS5300");
		veiculo1.setCor("Prata");
		veiculo1.setModelo("Livina");
		veiculo1.setMarca("Nissan");
		veiculo1.setMotorista(cargaMotorista.getMotorista());
		this.veiculo1 = veiculoMediator.save(veiculo1);

		Veiculo veiculo2 = new Veiculo();
		veiculo2.setId("AQT1234");
		veiculo2.setCor("Cinza");
		veiculo2.setModelo("Hilux");
		veiculo2.setMarca("Toyota");
		veiculo2.setViaturaMp(true);
		this.veiculo2 = veiculoMediator.save(veiculo2);

		Veiculo veiculo3 = new Veiculo();
		veiculo3.setId("AQT4577");
		veiculo3.setCor("Branca");
		veiculo3.setModelo("Frontier");
		veiculo3.setMarca("Nissan");
		veiculo3.setViaturaMp(true);
		veiculo3.setMotorista(new Motorista());
		this.veiculo3 = veiculoMediator.save(veiculo3);
	}
	
	@Test
	public void testFind() throws Exception {
		DataTablesInput dataTablesInput = new DataTablesInput();
		dataTablesInput.setStart(0);
		dataTablesInput.setLength(4);
		dataTablesInput.setSearch(new SearchParameter("Nissan", false));
		Page<Veiculo> page = veiculoMediator.find(dataTablesInput);
		assertEquals(2, page.getTotalElements());
	}

	@Test
	public void testListarViaturas() throws Exception {
		List<Veiculo> viaturas = veiculoMediator.findViaturas();
		assertEquals(2, viaturas.size());

		Veiculo veiculo3Actual = viaturas.stream().filter(p -> p.getId().equals(veiculo3.getId())).findFirst().get();
		assertEquals(veiculo3.getDescricaoCompleta(), veiculo3Actual.getDescricaoCompleta());

		Veiculo veiculo2Actual = viaturas.stream().filter(p -> p.getId().equals(veiculo2.getId())).findFirst().get();
		assertEquals(veiculo2.getDescricaoCompleta(), veiculo2Actual.getDescricaoCompleta());

	}

	@Test
	public void testIsViatura() {
		assertFalse(veiculoMediator.isViatura(veiculo1));
		assertTrue(veiculoMediator.isViatura(veiculo2));
		assertTrue(veiculoMediator.isViatura(veiculo3));
		Veiculo veiculoTransient = new Veiculo();
		veiculoTransient.setId("placa nao cadastrada");
		assertTrue(veiculoMediator.isViatura(veiculoTransient));
	}

}
