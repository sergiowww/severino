package br.mp.mpt.prt8.severino.mediator;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.mp.mpt.prt8.severino.dao.MotoristaRepository;
import br.mp.mpt.prt8.severino.entity.Cargo;
import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Teste do mediator de motorista.
 * 
 * @author sergio.eoliveira
 *
 */
public class MotoristaMediatorTest extends AbstractSeverinoTests {

	@Autowired
	private MotoristaMediator motoristaMediator;

	@Autowired
	private MotoristaRepository motoristaRepository;

	@Test(expected = NegocioException.class)
	public void testCheckMotorista() {
		Motorista motorista = new Motorista();
		motorista.setCargo(Cargo.PROCURADOR);
		motorista.setMatricula("123456-4");
		motorista.setNome("João do Caminhão");
		motoristaMediator.save(motorista);

		try {
			motoristaMediator.checkMotorista(motorista);

		} catch (NegocioException e) {
			assertEquals("O motorista selecionado não é um técnico de transporte!", e.getMessage());
			throw e;
		}

	}

	@Test
	public void testSalvarEAlterar() throws Exception {
		Motorista motorista = new Motorista();
		motorista.setCargo(Cargo.PROCURADOR);
		motorista.setMatricula("123456-4");
		motorista.setNome("João do Caminhão");
		motoristaMediator.save(motorista);

		entityManager.flush();
		entityManager.clear();
		List<Motorista> todos = motoristaRepository.findAll();
		assertEquals(1, todos.size());
		Motorista motoristaGravado = todos.get(0);

		assertEquals("123456-4", motoristaGravado.getMatricula());
		assertEquals("João do Caminhão", motoristaGravado.getNome());
		assertEquals(Cargo.PROCURADOR, motoristaGravado.getCargo());
		String nomeAlterado = motoristaGravado.getNome() + "1";
		motoristaGravado.setNome(nomeAlterado);
		motoristaMediator.save(motoristaGravado);

		entityManager.flush();
		entityManager.clear();
		todos = motoristaRepository.findAll();
		assertEquals(1, todos.size());
		motoristaGravado = todos.get(0);
		assertEquals(nomeAlterado, motoristaGravado.getNome());

	}

	@Test(expected = NegocioException.class)
	public void testCheckMatricula() throws Exception {
		Motorista motorista1 = new Motorista();
		motorista1.setCargo(Cargo.PROCURADOR);
		motorista1.setMatricula("123456-4");
		motorista1.setNome("João do Caminhão");
		motoristaMediator.save(motorista1);

		Motorista motorista2 = new Motorista();
		motorista2.setNome("outro nome");
		motorista2.setMatricula(motorista1.getMatricula());
		motorista2.setCargo(Cargo.SERVIDOR);
		try {
			motoristaMediator.save(motorista2);
		} catch (NegocioException e) {
			assertEquals("Já existe um motorista com a matrícula 123456-4 cadastrado", e.getMessage());
			throw e;
		}
	}

}
