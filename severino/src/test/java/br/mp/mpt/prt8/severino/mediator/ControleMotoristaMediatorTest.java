package br.mp.mpt.prt8.severino.mediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.Search;
import org.springframework.test.context.ContextConfiguration;

import br.mp.mpt.prt8.severino.dao.ControleMotoristaRepository;
import br.mp.mpt.prt8.severino.dao.ViagemRepository;
import br.mp.mpt.prt8.severino.entity.Cargo;
import br.mp.mpt.prt8.severino.entity.ControleMotorista;
import br.mp.mpt.prt8.severino.entity.Fluxo;
import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.entity.Viagem;
import br.mp.mpt.prt8.severino.mediator.carga.CargaUsuario;
import br.mp.mpt.prt8.severino.utils.Constantes;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Teste para o mediator de controle de motoristas.
 * 
 * @author sergio.eoliveira
 *
 */
@ContextConfiguration(classes = CargaUsuario.class)
public class ControleMotoristaMediatorTest extends AbstractSeverinoTests {
	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static final DateFormat TIME_FORMAT = DateFormat.getTimeInstance(DateFormat.SHORT, Constantes.DEFAULT_LOCALE);
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	private static final DateFormat PARCIAL = new SimpleDateFormat("dd/MM/yyyy HH");

	@Autowired
	private ControleMotoristaMediator controleMotoristaMediator;

	@Autowired
	private MotoristaMediator motoristaMediator;

	@Autowired
	private ControleMotoristaRepository controleMotoristaRepository;

	@Autowired
	private ViagemRepository viagemRepository;

	@Test
	public void testDisponibilidadeMotorista() throws Exception {

		Motorista motorista1 = saveMotorista(1);
		Motorista motorista2 = saveMotorista(2);
		Motorista motorista3 = saveMotorista(3);
		Motorista motorista4 = saveMotorista(4);

		// motorista1 entrou, saiu e entrou
		registrarControle(Fluxo.ENTRADA, "05/10/2016 08:10", motorista1);
		registrarControle(Fluxo.SAIDA, "05/10/2016 09:10", motorista1);
		ControleMotorista controleExpected1 = registrarControle(Fluxo.ENTRADA, "05/10/2016 09:30", motorista1);

		// motorista2 entrou
		ControleMotorista controleExpected2 = registrarControle(Fluxo.ENTRADA, "05/10/2016 08:30", motorista2);

		// motorista3 entrou e saiu
		registrarControle(Fluxo.ENTRADA, "05/10/2016 08:05", motorista3);
		ControleMotorista controleExpected3 = registrarControle(Fluxo.SAIDA, "05/10/2016 08:21", motorista3);

		List<ControleMotorista> controles = controleMotoristaMediator.findDisponiveis();
		assertNotNull(controles);

		List<Motorista> disponiveis = controles.stream().map(m -> m.getMotorista()).collect(Collectors.toList());

		assertEquals(4, disponiveis.size());
		assertTrue(disponiveis.containsAll(Arrays.asList(motorista1, motorista2, motorista3, motorista4)));
		ControleMotorista controleActual1 = controles.get(controles.indexOf(controleExpected1));
		ControleMotorista controleActual2 = controles.get(controles.indexOf(controleExpected2));
		ControleMotorista controleActual3 = controles.get(controles.indexOf(controleExpected3));
		ControleMotorista controleActual4 = controles.stream().filter(cm -> cm.getMotorista().equals(motorista4)).findFirst().get();

		assertEquals(motorista1, controleActual1.getMotorista());
		assertEquals(controleExpected1.getDataHora(), controleActual1.getDataHora());
		assertEquals(controleExpected1.getFluxo(), controleActual1.getFluxo());

		assertEquals(motorista2, controleActual2.getMotorista());
		assertEquals(controleExpected2.getDataHora(), controleActual2.getDataHora());
		assertEquals(controleExpected2.getFluxo(), controleActual2.getFluxo());

		assertEquals(motorista3, controleActual3.getMotorista());
		assertEquals(controleExpected3.getDataHora(), controleActual3.getDataHora());
		assertEquals(controleExpected3.getFluxo(), controleActual3.getFluxo());

		assertEquals(motorista4, controleActual4.getMotorista());
		assertNull(controleActual4.getDataHora());
		assertNull(controleActual4.getFluxo());

		List<Motorista> motoristasDisponiveis = controleMotoristaMediator.findMotoristasDisponiveis(null);
		assertEquals(2, motoristasDisponiveis.size());
		assertTrue(motoristasDisponiveis.contains(motorista1));
		assertTrue(motoristasDisponiveis.contains(motorista2));

		motoristasDisponiveis = controleMotoristaMediator.findMotoristasDisponiveis(motorista4.getId());
		assertEquals(3, motoristasDisponiveis.size());
		assertTrue(motoristasDisponiveis.contains(motorista1));
		assertTrue(motoristasDisponiveis.contains(motorista2));
		assertTrue(motoristasDisponiveis.contains(motorista4));

		motoristasDisponiveis = controleMotoristaMediator.findMotoristasDisponiveis(motorista1.getId());
		assertEquals(2, motoristasDisponiveis.size());
		assertTrue(motoristasDisponiveis.contains(motorista1));
		assertTrue(motoristasDisponiveis.contains(motorista2));
	}

	private ControleMotorista registrarControle(Fluxo fluxo, String dataHora, Motorista motorista) throws ParseException {
		ControleMotorista controle = new ControleMotorista();
		controle.setDataHora(SDF.parse(dataHora));
		controle.setMotorista(motorista);
		controle.setFluxo(fluxo);
		return controleMotoristaMediator.save(controle);
	}

	private Motorista saveMotorista(int index) {

		Motorista motorista = new Motorista();
		motorista.setNome("motorista" + index);
		motorista.setMatricula("123-" + index);
		motorista.setCargo(Cargo.MOTORISTA);
		return motoristaMediator.save(motorista);
	}

	@Test
	public void testRegistrarPontoApenas() throws Exception {
		Motorista motorista = saveMotorista(1);

		registrarPrimeiroPonto(motorista);

		assertEquals(1, controleMotoristaRepository.count());

		controleMotoristaMediator.atualizarDisponibilidade(motorista.getId(), null);
		entityManager.flush();
		entityManager.clear();

		assertEquals(2, controleMotoristaRepository.count());
		ControleMotorista controleGravado = controleMotoristaMediator.findUltimoControle(motorista.getId());
		assertEquals(Fluxo.SAIDA, controleGravado.getFluxo());
		assertNotNull(controleGravado.getDataHora());
		assertEquals(motorista, controleGravado.getMotorista());
		assertEquals(DATE_FORMAT.format(new Date()), DATE_FORMAT.format(controleGravado.getDataHora()));

		testFind(motorista);
	}

	@Test(expected = NegocioException.class)
	public void testRegistrarForaDeOrdem2() throws Exception {
		Motorista motorista1 = saveMotorista(1);
		try {
			registrarControle(Fluxo.ENTRADA, "05/10/2016 08:10", motorista1);
			registrarControle(Fluxo.SAIDA, "05/10/2016 09:10", motorista1);
		} catch (NegocioException e) {
			fail("Deu exceção onde não deveria..." + e.getMessage());
		}
		try {
			registrarControle(Fluxo.SAIDA, "05/10/2016 08:12", motorista1);
		} catch (NegocioException e) {
			assertEquals("O registro de ponto 05/10/2016 09:10 posterior a este é uma SAIDA", e.getMessage());
			throw e;
		}
	}

	@Test(expected = NegocioException.class)
	public void testRegistrarDuplicado() throws Exception {
		Motorista motorista1 = saveMotorista(1);
		try {
			registrarControle(Fluxo.SAIDA, "05/10/2016 08:12", motorista1);
		} catch (NegocioException e) {
			fail("Deu exceção onde não deveria..." + e.getMessage());
		}
		try {
			registrarControle(Fluxo.ENTRADA, "05/10/2016 08:12", motorista1);
		} catch (NegocioException e) {
			assertEquals("Não é possível registrar uma data e hora que tenha sido registrada antes!", e.getMessage());
			throw e;
		}
	}

	@Test
	public void testRegistroEAlteracao() throws Exception {
		Motorista motorista1 = saveMotorista(1);
		ControleMotorista controle = registrarControle(Fluxo.ENTRADA, "05/10/2016 08:10", motorista1);
		entityManager.flush();
		entityManager.clear();
		assertNotNull(controle.getId());

		controle = controleMotoristaRepository.findOne(controle.getId());
		assertEquals(Fluxo.ENTRADA, controle.getFluxo());
		assertEquals(motorista1, controle.getMotorista());
		assertEquals("05/10/2016 08:10", SDF.format(controle.getDataHora()));
		controle.setFluxo(Fluxo.SAIDA);
		controleMotoristaMediator.save(controle);
		entityManager.flush();
		entityManager.clear();

		controle = controleMotoristaRepository.findOne(controle.getId());

		assertEquals(Fluxo.SAIDA, controle.getFluxo());
		assertEquals(motorista1, controle.getMotorista());
		assertEquals("05/10/2016 08:10", SDF.format(controle.getDataHora()));

		controle.setDataHora(SDF.parse("05/10/2016 08:12"));

		controleMotoristaMediator.save(controle);
		entityManager.flush();
		entityManager.clear();

		controle = controleMotoristaRepository.findOne(controle.getId());
		assertEquals(Fluxo.SAIDA, controle.getFluxo());
		assertEquals(motorista1, controle.getMotorista());
		assertEquals("05/10/2016 08:12", SDF.format(controle.getDataHora()));
	}

	@Test(expected = NegocioException.class)
	public void testRegistrarForaDeOrdem() throws Exception {
		Motorista motorista1 = saveMotorista(1);
		try {
			registrarControle(Fluxo.ENTRADA, "05/10/2016 08:10", motorista1);

		} catch (NegocioException e) {
			fail("Deu exceção onde não deveria..." + e.getMessage());
		}
		try {
			registrarControle(Fluxo.ENTRADA, "05/10/2016 09:10", motorista1);
		} catch (NegocioException e) {
			assertEquals("O ponto anterior 05/10/2016 08:10 é uma ENTRADA", e.getMessage());
			throw e;
		}
	}

	/**
	 * Registrar um horário anterior ao último registrado
	 * 
	 * @throws Exception
	 */
	@Test(expected = NegocioException.class)
	public void testRegistrarPontoAntesDoUltimoPonto() throws Exception {
		LocalTime agoraMenos5minutos = LocalTime.now().minusMinutes(5);

		Date horario1 = localTimeToDate(agoraMenos5minutos);

		Motorista motorista = saveMotorista(1);
		controleMotoristaMediator.atualizarDisponibilidade(motorista.getId(), horario1);
		assertEquals(1, controleMotoristaRepository.count());
		entityManager.flush();
		entityManager.clear();

		ControleMotorista controleGravado = controleMotoristaMediator.findUltimoControle(motorista.getId());
		assertEquals(Fluxo.ENTRADA, controleGravado.getFluxo());
		assertNotNull(controleGravado.getDataHora());
		assertEquals(TIME_FORMAT.format(horario1), TIME_FORMAT.format(controleGravado.getDataHora()));
		assertEquals(motorista, controleGravado.getMotorista());
		assertEquals(DATE_FORMAT.format(new Date()), DATE_FORMAT.format(controleGravado.getDataHora()));

		LocalTime agoraMenos10minutos = LocalTime.now().minusMinutes(10);
		Date horario2 = localTimeToDate(agoraMenos10minutos);
		try {
			controleMotoristaMediator.atualizarDisponibilidade(motorista.getId(), horario2);
		} catch (NegocioException e) {
			assertEquals("Não é possível inserir um horário antes do último horário registrado: " + SDF.format(horario1), e.getMessage());
			throw e;
		}
	}

	private Date localTimeToDate(LocalTime agoraMenos5minutos) {
		return Date.from(agoraMenos5minutos.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant());
	}

	private void testFind(Motorista motorista) {
		DataTablesInput dataTablesInput = new DataTablesInput();
		dataTablesInput.setStart(0);
		dataTablesInput.setLength(4);
		dataTablesInput.setSearch(new Search(motorista.getNome(), false));
		Page<ControleMotorista> page = controleMotoristaMediator.find(dataTablesInput);
		assertEquals(2, page.getTotalElements());
	}

	@Test
	public void testAtualizarDisponibilidade() throws Exception {

		Motorista motorista = saveMotorista(1);

		registrarPrimeiroPonto(motorista);

		saiuViagem(motorista);

		retornouViagem(motorista);
	}

	private void registrarPrimeiroPonto(Motorista motorista) throws ParseException {
		assertEquals(0, controleMotoristaRepository.count());
		// primeiro ponto deste motorista.
		LocalTime agoraMenos5minutos = LocalTime.now().minusMinutes(5);
		Date horario = localTimeToDate(agoraMenos5minutos);
		controleMotoristaMediator.atualizarDisponibilidade(motorista.getId(), horario);
		entityManager.flush();
		entityManager.clear();

		assertEquals(1, controleMotoristaRepository.count());
		ControleMotorista controleGravado = controleMotoristaMediator.findUltimoControle(motorista.getId());
		assertEquals(Fluxo.ENTRADA, controleGravado.getFluxo());
		assertNotNull(controleGravado.getDataHora());
		assertEquals(motorista, controleGravado.getMotorista());
		assertEquals(DATE_FORMAT.format(new Date()), DATE_FORMAT.format(controleGravado.getDataHora()));
		assertEquals(TIME_FORMAT.format(horario), TIME_FORMAT.format(controleGravado.getDataHora()));
	}

	private void saiuViagem(Motorista motorista) {
		// Vai sair com a viatura, tem que aparecer um ponto
		Viagem viagem = new Viagem();
		viagem.setAnotacao("Teste");
		viagem.setGravarVeiculo(false);
		ViagemMediator viagemMediator = applicationContext.getBean(ViagemMediator.class);
		viagem.setMotorista(motorista);
		viagem = viagemMediator.save(viagem);
		entityManager.flush();
		entityManager.clear();

		Viagem viagemGravada = viagemRepository.findOne(viagem.getId());
		assertNull(viagemGravada.getRetorno());

		assertEquals(2, controleMotoristaRepository.count());
		ControleMotorista controleGravado = controleMotoristaMediator.findUltimoControle(motorista.getId());
		assertEquals(Fluxo.SAIDA, controleGravado.getFluxo());
		assertNotNull(controleGravado.getDataHora());
		assertEquals(motorista, controleGravado.getMotorista());
		assertEquals(PARCIAL.format(new Date()), PARCIAL.format(controleGravado.getDataHora()));
	}

	private void retornouViagem(Motorista motorista) {
		controleMotoristaMediator.atualizarDisponibilidade(motorista.getId(), null);

		assertEquals(3, controleMotoristaRepository.count());
		ControleMotorista controleGravado = controleMotoristaMediator.findUltimoControle(motorista.getId());
		assertEquals(Fluxo.ENTRADA, controleGravado.getFluxo());
		assertNotNull(controleGravado.getDataHora());
		assertEquals(motorista, controleGravado.getMotorista());
		assertEquals(PARCIAL.format(new Date()), PARCIAL.format(controleGravado.getDataHora()));

		List<Viagem> viagens = viagemRepository.findAll();
		assertEquals(1, viagens.size());
		Viagem viagemGravada = viagens.get(0);
		assertNotNull(viagemGravada.getRetorno());
		assertEquals(PARCIAL.format(new Date()), PARCIAL.format(viagemGravada.getRetorno()));

	}

}
