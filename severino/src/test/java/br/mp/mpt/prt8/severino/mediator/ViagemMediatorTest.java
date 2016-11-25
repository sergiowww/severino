package br.mp.mpt.prt8.severino.mediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.parameter.SearchParameter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import br.mp.mpt.prt8.severino.dao.ControleMotoristaRepository;
import br.mp.mpt.prt8.severino.dao.ViagemRepository;
import br.mp.mpt.prt8.severino.entity.Cargo;
import br.mp.mpt.prt8.severino.entity.ControleMotorista;
import br.mp.mpt.prt8.severino.entity.Fluxo;
import br.mp.mpt.prt8.severino.entity.FonteDisponibilidade;
import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.entity.Passageiro;
import br.mp.mpt.prt8.severino.entity.Usuario;
import br.mp.mpt.prt8.severino.entity.Veiculo;
import br.mp.mpt.prt8.severino.entity.Viagem;
import br.mp.mpt.prt8.severino.mediator.carga.CargaMotorista;
import br.mp.mpt.prt8.severino.mediator.carga.CargaUsuario;
import br.mp.mpt.prt8.severino.utils.Constantes;
import br.mp.mpt.prt8.severino.utils.DateUtils;
import br.mp.mpt.prt8.severino.utils.NegocioException;
import br.mp.mpt.prt8.severino.validators.CadastrarViagem;
import br.mp.mpt.prt8.severino.valueobject.PessoaDisponibilidade;

@ContextConfiguration(classes = { CargaUsuario.class, CargaMotorista.class })
public class ViagemMediatorTest extends AbstractSeverinoTests {

	private static final String PASSAGEIRO3_NOME = "Mais um passageiro";

	private static final String PASSAGEIRO2_NOME = "José do egito";

	private static final String PASSAGEIRO1_NOME = "Sergio Eduardo";

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyy");
	private static final DateFormat DTF = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Constantes.DEFAULT_LOCALE);

	@Autowired
	private ViagemMediator viagemMediator;

	@Autowired
	private UsuarioHolder usuarioHolder;

	@Autowired
	private CargaMotorista cargaMotorista;

	@Autowired
	private VeiculoMediator veiculoMediator;

	@Autowired
	private ViagemRepository viagemRepository;

	@Autowired
	private SmartValidator smartValidator;

	@Autowired
	private ControleMotoristaRepository controleRepository;

	@Test
	public void testApagar() throws Exception {
		Viagem viagem = new Viagem();
		viagem.setAnotacao("Teste");
		viagem.setGravarVeiculo(false);
		viagem.setMotorista(cargaMotorista.getMotorista());
		Viagem viagemGravada = viagemMediator.save(viagem);

		assertEquals(1, controleRepository.count());
		Integer id = viagemGravada.getId();

		viagemMediator.apagar(id);
		entityManager.flush();
		entityManager.clear();

		assertEquals(0, controleRepository.count());
		assertFalse(viagemRepository.exists(id));

	}

	/**
	 * Apagar uma visita como outro usuário (não pode)
	 * 
	 * @throws Exception
	 */
	@Test(expected = NegocioException.class)
	public void testApagarComoOutroUsuario() throws Exception {
		Viagem viagem = new Viagem();
		viagem.setAnotacao("Teste");
		viagem.setGravarVeiculo(false);
		viagem.setMotorista(cargaMotorista.getMotorista());
		Viagem viagemGravada = viagemMediator.save(viagem);

		Integer id = viagemGravada.getId();
		Usuario usuario = new Usuario();
		usuario.setId("outrousuario");
		usuario.setNome("Usuarioque tenta apagar o registro");
		usuarioHolder.setUsuario(usuario);
		try {
			viagemMediator.apagar(id);
			fail("Não deveria ter chegado aqui");
		} catch (NegocioException e) {
			assertEquals("Este registro só pode ser removido no mesmo dia pelo usuário criador!", e.getMessage());
			throw e;
		}

	}

	@Test(expected = NegocioException.class)
	public void testSalvarComMotoristaIndisponivel() throws Exception {
		Viagem viagem1 = new Viagem();
		Motorista motorista = cargaMotorista.getMotorista();
		viagem1.setMotorista(motorista);
		salvarRegistro(viagem1);

		Viagem viagem2 = new Viagem();
		viagem2.setMotorista(motorista);
		try {
			salvarRegistro(viagem2);
		} catch (NegocioException e) {
			assertEquals("O motorista selecionado não está disponível mais!", e.getMessage());
			throw e;
		}

	}

	private void assertControle(Viagem viagem, Fluxo fluxoExpected, long totalRegistrosExpected) {
		assertEquals(totalRegistrosExpected, controleRepository.count());
		Motorista motorista = viagem.getMotorista();
		Integer idMotorista = motorista.getId();
		ControleMotoristaMediator controleMediator = applicationContext.getBean(ControleMotoristaMediator.class);
		ControleMotorista controle = controleMediator.findUltimoControle(idMotorista);
		ControleMotoristaMediator controleMotoristaMediator = applicationContext.getBean(ControleMotoristaMediator.class);
		List<Motorista> resultado = controleMotoristaMediator.findMotoristasDisponiveis(null);
		if (fluxoExpected.equals(Fluxo.SAIDA)) {
			assertFalse(resultado.contains(motorista));
		} else {
			assertTrue(resultado.contains(motorista));
		}
		assertEquals(fluxoExpected, controle.getFluxo());
		assertEquals(idMotorista, controle.getMotorista().getId());
	}

	@Test
	public void testSalvarViagemComPassageiros() throws Exception {
		Viagem viagem = new Viagem();
		String anotacao = "Teste";
		viagem.setAnotacao(anotacao);
		viagem.setGravarVeiculo(false);
		viagem.setMotorista(cargaMotorista.getMotorista());

		Passageiro passageiro1 = new Passageiro();
		passageiro1.setNome(PASSAGEIRO1_NOME);
		passageiro1.setMatricula("1231256-4");
		viagem.addPassageiro(passageiro1);

		Passageiro passageiro2 = new Passageiro();
		passageiro2.setNome(PASSAGEIRO2_NOME);
		passageiro2.setMatricula("145485-4");

		viagem.addPassageiro(passageiro2);

		Viagem viagemGravada = salvarRegistro(viagem);
		assertControle(viagemGravada, Fluxo.SAIDA, 1);
		assertNotNull(viagemGravada.getSaida());
		assertEquals(DATE_FORMAT.format(viagemGravada.getSaida()), DATE_FORMAT.format(new Date()));
		assertNotNull(viagemGravada.getDataHoraCadastro());
		assertEquals(DATE_FORMAT.format(viagemGravada.getDataHoraCadastro()), DATE_FORMAT.format(new Date()));
		assertEquals(anotacao, viagemGravada.getAnotacao());
		assertNotNull(viagemGravada.getUsuario());
		List<Passageiro> passageiros = viagemGravada.getPassageiros();
		assertNotNull(passageiros);
		assertEquals(2, passageiros.size());
		assertPassageiro(passageiro1, passageiros);
		assertPassageiro(passageiro2, passageiros);
		testAlterarViagemAdd1Passageiro(viagemGravada);

	}

	private void validarVisita(Viagem viagem) {
		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(viagem, "viagem", true, 256);
		smartValidator.validate(viagem, errors, CadastrarViagem.class);
		assertFalse("Validação falhou..." + errors.toString(), errors.hasErrors());
	}

	private Viagem salvarRegistro(Viagem viagem) {
		validarVisita(viagem);
		Viagem viagemGravada = viagemMediator.save(viagem);
		entityManager.flush();
		entityManager.clear();
		viagemGravada = viagemMediator.findOne(Optional.of(viagemGravada.getId()));
		return viagemGravada;
	}

	private void testAlterarViagemAdd1Passageiro(Viagem viagem) {
		List<Passageiro> passageiros = viagem.getPassageiros();
		Passageiro passageiro3SemMatricula = new Passageiro();
		passageiro3SemMatricula.setNome(PASSAGEIRO3_NOME);
		viagem.addPassageiro(passageiro3SemMatricula);
		String anotacao = "Alterei a anotação";
		viagem.setAnotacao(anotacao);

		Viagem viagemGravada = salvarRegistro(viagem);
		assertControle(viagemGravada, Fluxo.SAIDA, 1);
		assertEquals(anotacao, viagemGravada.getAnotacao());

		List<Passageiro> passageirosGravados = viagemGravada.getPassageiros();
		assertNotNull(passageirosGravados);
		assertEquals(3, passageirosGravados.size());
		assertPassageiro(passageiros.get(0), passageirosGravados);
		assertPassageiro(passageiros.get(1), passageirosGravados);
		assertPassageiro(passageiro3SemMatricula, passageirosGravados);

		testAlterarViagemRemover1Passageiro(viagemGravada);
	}

	private void testAlterarViagemRemover1Passageiro(Viagem viagem) {
		List<Passageiro> passageiros = viagem.getPassageiros();

		viagem.getPassageiros().removeIf(p -> p.getNome().equals(PASSAGEIRO1_NOME));

		Viagem viagemGravada = salvarRegistro(viagem);
		assertControle(viagemGravada, Fluxo.SAIDA, 1);

		List<Passageiro> passageirosGravados = viagemGravada.getPassageiros();
		assertEquals(2, passageirosGravados.size());
		assertPassageiro(passageiros.stream().filter(p -> p.getNome().equals(PASSAGEIRO2_NOME)).findFirst().get(), passageirosGravados);
		assertPassageiro(passageiros.stream().filter(p -> p.getNome().equals(PASSAGEIRO3_NOME)).findFirst().get(), passageirosGravados);

	}

	private void assertPassageiro(Passageiro passageiro1, List<Passageiro> passageiros) {
		assertTrue(passageiros.stream().filter(p -> ObjectUtils.nullSafeEquals(p.getNome(), passageiro1.getNome()) && ObjectUtils.nullSafeEquals(p.getMatricula(), passageiro1.getMatricula()))
				.findFirst().isPresent());
	}

	@Test
	public void testSalvarViagem() {
		Viagem viagem = new Viagem();
		viagem.setAnotacao("Teste");
		viagem.setGravarVeiculo(false);
		viagem.setMotorista(cargaMotorista.getMotorista());
		Viagem viagemGravada = salvarRegistro(viagem);

		assertNotNull(viagemGravada.getSaida());
		assertEquals(DATE_FORMAT.format(viagemGravada.getSaida()), DATE_FORMAT.format(new Date()));
		assertNotNull(viagemGravada.getDataHoraCadastro());
		assertEquals(DATE_FORMAT.format(viagemGravada.getDataHoraCadastro()), DATE_FORMAT.format(new Date()));
		assertControle(viagemGravada, Fluxo.SAIDA, 1);
		assertNotNull(viagemGravada.getUsuario());

	}

	@Test
	public void testSalvarDuasVezes() throws Exception {
		Viagem viagem = new Viagem();
		viagem.setMotorista(cargaMotorista.getMotorista());
		Viagem viagemGravada = salvarRegistro(viagem);
		Date saida = viagemGravada.getSaida();
		assertNotNull(saida);
		assertControle(viagemGravada, Fluxo.SAIDA, 1);
		viagemGravada.getPassageiros().size();
		entityManager.flush();
		entityManager.clear();
		viagemGravada.getControleSaida().setId(null);

		viagemGravada = salvarRegistro(viagemGravada);
		assertEquals(saida, viagemGravada.getSaida());
		assertControle(viagemGravada, Fluxo.SAIDA, 1);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testSalvarComDataFutura() throws Exception {
		Viagem viagem = new Viagem();
		viagem.setMotorista(cargaMotorista.getMotorista());
		LocalDateTime dataFutura = LocalDateTime.now().plusHours(4);
		viagem.setSaida(Date.from(dataFutura.toInstant(ZoneOffset.UTC)));
		try {
			viagemMediator.save(viagem);
			entityManager.flush();
			entityManager.clear();
		} catch (ConstraintViolationException e) {
			String message = e.getConstraintViolations().stream().map(c -> c.getMessage()).collect(Collectors.joining());
			assertEquals("Não pode ser uma data futura", message);
			throw e;
		}
	}

	@Test
	public void testSalvarDuasVezesSemRetorno() throws Exception {
		Viagem viagem = new Viagem();
		viagem.setMotorista(cargaMotorista.getMotorista());
		Viagem viagemGravada = salvarRegistro(viagem);
		Date saida = viagemGravada.getSaida();
		assertNotNull(saida);
		assertNull(viagemGravada.getRetorno());
		assertControle(viagemGravada, Fluxo.SAIDA, 1);
		viagemGravada.getPassageiros().size();
		entityManager.flush();
		entityManager.clear();
		viagemGravada.getControleSaida().setId(null);

		viagemGravada.setRetorno(null);
		viagemGravada = salvarRegistro(viagemGravada);
		assertEquals(saida, viagemGravada.getSaida());
		assertControle(viagemGravada, Fluxo.SAIDA, 1);
	}

	@Test
	public void testBaixarERemoverBaixa() throws Exception {
		Viagem viagem = new Viagem();
		viagem.setGravarVeiculo(false);
		viagem.setMotorista(cargaMotorista.getMotorista());
		Viagem viagemGravada = salvarRegistro(viagem);
		assertControle(viagemGravada, Fluxo.SAIDA, 1);

		viagemGravada.setRegistrarSaida(true);
		viagemGravada = salvarRegistro(viagemGravada);
		assertControle(viagemGravada, Fluxo.ENTRADA, 2);
		assertNotNull(viagemGravada.getRetorno());
		assertNotNull(viagemGravada.getSaida());

		viagemGravada.setRetorno(null);

		viagemGravada = salvarRegistro(viagemGravada);
		assertControle(viagemGravada, Fluxo.SAIDA, 1);
		assertNull(viagemGravada.getRetorno());
		assertNotNull(viagemGravada.getSaida());
	}

	@Test
	public void testSalvarComEntradaInformada() throws Exception {
		Viagem viagem = new Viagem();
		viagem.setMotorista(cargaMotorista.getMotorista());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date saida = sdf.parse("05/10/2016 10:40");
		viagem.setSaida(saida);
		Viagem viagemGravada = salvarRegistro(viagem);
		assertEquals(saida, viagemGravada.getSaida());
		assertControle(viagemGravada, Fluxo.SAIDA, 1);
	}

	@Test
	public void testBaixarViagem() throws Exception {
		Viagem viagem = new Viagem();
		viagem.setAnotacao("Teste");
		viagem.setGravarVeiculo(false);
		viagem.setMotorista(cargaMotorista.getMotorista());
		Viagem viagemGravada = salvarRegistro(viagem);
		assertControle(viagemGravada, Fluxo.SAIDA, 1);
		List<Viagem> viagensSemBaixa = viagemMediator.findViagensSemBaixa();
		assertEquals(1, viagensSemBaixa.size());
		assertTrue(viagensSemBaixa.contains(viagemGravada));
		assertNull(viagemGravada.getRetorno());
		assertNotNull(viagemGravada.getSaida());

		viagemGravada.setRegistrarSaida(true);
		viagemGravada = salvarRegistro(viagemGravada);
		assertControle(viagemGravada, Fluxo.ENTRADA, 2);
		assertNotNull(viagemGravada.getRetorno());
		assertNotNull(viagemGravada.getSaida());
		assertEquals(DATE_FORMAT.format(viagemGravada.getRetorno()), DATE_FORMAT.format(new Date()));
		viagensSemBaixa = viagemMediator.findViagensSemBaixa();
		assertEquals(0, viagensSemBaixa.size());
	}

	@Test
	public void testCadastrarComViaturaNula() throws Exception {
		Viagem viagem = new Viagem();
		viagem.setMotorista(cargaMotorista.getMotorista());
		Veiculo veiculoVazio = new Veiculo();
		viagem.setViatura(veiculoVazio);
		Viagem viagemGravada = salvarRegistro(viagem);
		assertNull(viagemGravada.getViatura());
	}

	@Test
	public void testCadastrarComViaturaCadastrada() throws Exception {
		Veiculo veiculo = new Veiculo();
		String cor = "Azul";
		String id = "das1234";
		String modelo = "Pálio";
		String marca = "Fiat";
		veiculo.setCor(cor);
		veiculo.setId(id);
		veiculo.setModelo(modelo);
		veiculo.setMarca(marca);
		veiculo.setViaturaMp(true);

		veiculoMediator.save(veiculo);
		entityManager.flush();
		entityManager.clear();

		Viagem viagem = new Viagem();
		viagem.setMotorista(cargaMotorista.getMotorista());
		viagem.setViatura(veiculo);
		Viagem viagemGravada = salvarRegistro(viagem);
		assertControle(viagemGravada, Fluxo.SAIDA, 1);
		assertNotNull(viagemGravada.getViatura());
		assertEquals(id, viagemGravada.getViatura().getId());
		assertEquals(cor, viagemGravada.getViatura().getCor());
		assertEquals(modelo, viagemGravada.getViatura().getModelo());
		assertEquals(marca, viagemGravada.getViatura().getMarca());

	}

	@Test(expected = NegocioException.class)
	public void testSalvarComVeiculoComum() throws Exception {
		Veiculo veiculo = new Veiculo();
		String cor = "Azul";
		String id = "das1234";
		String modelo = "Pálio";
		String marca = "Fiat";
		veiculo.setCor(cor);
		veiculo.setId(id);
		veiculo.setModelo(modelo);
		veiculo.setMarca(marca);
		veiculo.setViaturaMp(false);

		veiculoMediator.save(veiculo);
		entityManager.flush();
		entityManager.clear();

		Viagem viagem = new Viagem();
		viagem.setMotorista(cargaMotorista.getMotorista());
		viagem.setViatura(veiculo);
		try {

			salvarRegistro(viagem);
		} catch (NegocioException e) {
			assertEquals("O veículo informado não está cadastrado como viatura!", e.getMessage());
			throw e;
		}
	}

	@Test
	public void testCadastrarComViaturaNaoCadastrada() throws Exception {
		String placa = "JDS5300";
		String marca = "Nissan";
		String modelo = "Livina";
		String cor = "Prata";

		Viagem viagem = new Viagem();
		viagem.setMotorista(cargaMotorista.getMotorista());
		Veiculo viatura = new Veiculo();
		viatura.setId(placa);
		viatura.setMarca(marca);
		viatura.setModelo(modelo);
		viatura.setCor(cor);
		viagem.setGravarVeiculo(true);
		viagem.setViatura(viatura);

		assertFalse(viatura.getViaturaMp());

		viagem.setViatura(viatura);
		Viagem viagemGravada = salvarRegistro(viagem);
		assertControle(viagemGravada, Fluxo.SAIDA, 1);
		assertNotNull(viagemGravada.getViatura());
		assertTrue(viagemGravada.getViatura().getViaturaMp());
		assertEquals(placa, viagemGravada.getViatura().getId());
		assertEquals(cor, viagemGravada.getViatura().getCor());
		assertEquals(marca, viagemGravada.getViatura().getMarca());
		assertEquals(modelo, viagemGravada.getViatura().getModelo());
		assertNull(viagemGravada.getViatura().getMotorista());

	}

	@Test
	public void testAlterarViagemSemPassageiros() throws Exception {
		Viagem viagem = new Viagem();
		viagem.setMotorista(cargaMotorista.getMotorista());
		Viagem viagemGravada = salvarRegistro(viagem);
		assertControle(viagemGravada, Fluxo.SAIDA, 1);
		entityManager.detach(viagemGravada);
		viagemGravada.setPassageiros(null);

		viagemGravada = salvarRegistro(viagemGravada);
		assertControle(viagemGravada, Fluxo.SAIDA, 1);
		assertNotNull(viagemGravada.getPassageiros());
		assertTrue(viagemGravada.getPassageiros().isEmpty());

		testFind(viagem);
	}

	private void testFind(Viagem viagem) {
		DataTablesInput dataTablesInput = new DataTablesInput();
		dataTablesInput.setStart(0);
		dataTablesInput.setLength(4);
		dataTablesInput.setSearch(new SearchParameter(viagem.getMotorista().getNome(), false));
		Page<Viagem> page = viagemMediator.find(dataTablesInput);
		assertEquals(1, page.getTotalElements());
	}

	@Test
	public void testFindUltimaDisponibilidade() throws Exception {
		// gravar viagem 1 com dois passageiros
		Date saidaViagem1 = DateUtils.toDate(LocalDateTime.now().minusHours(10));
		Date retornoViagem1 = DateUtils.toDate(LocalDateTime.now().minusMinutes(1));
		Viagem viagem1 = new Viagem();
		viagem1.setMotorista(cargaMotorista.getMotorista());
		viagem1.setGravarVeiculo(false);
		viagem1.setSaida(saidaViagem1);
		viagem1.setRetorno(retornoViagem1);

		Passageiro passageiro1 = new Passageiro();
		String nomePassageiro1 = "Passageiro 1";
		passageiro1.setNome(nomePassageiro1);
		passageiro1.setMatricula("12");
		viagem1.addPassageiro(passageiro1);

		Passageiro passageiro2 = new Passageiro();
		String nomePassageiro2 = "Passageiro 2";
		passageiro2.setNome(nomePassageiro2);
		passageiro2.setMatricula("13");

		viagem1.addPassageiro(passageiro2);

		viagem1 = salvarRegistro(viagem1);
		assertEquals(2, viagem1.getPassageiros().size());

		// gravar viagem 3 com 1 passageiro
		Motorista motorista2 = new Motorista();
		motorista2.setNome("Outro Motora");
		motorista2.setMatricula("1");
		motorista2.setCargo(Cargo.MOTORISTA);
		entityManager.persist(motorista2);

		Date saidaViagem3 = DateUtils.toDate(LocalDateTime.now().minusHours(8));
		Date retornoViagem3 = DateUtils.toDate(LocalDateTime.now().minusHours(6));
		Viagem viagem3 = new Viagem();
		viagem3.setMotorista(motorista2);
		viagem3.setGravarVeiculo(false);
		viagem3.setSaida(saidaViagem3);
		viagem3.setRetorno(retornoViagem3);

		Passageiro passageiro3 = new Passageiro();
		String nomePassageiro3 = "Passageiro 3";
		passageiro3.setNome(nomePassageiro3);
		passageiro3.setMatricula("3");
		viagem3.addPassageiro(passageiro3);

		viagem3 = salvarRegistro(viagem3);
		assertEquals(1, viagem3.getPassageiros().size());

		// gravar viagem 2 com o mesmo passageiro da viagem 3
		Viagem viagem2 = new Viagem();
		Date saidaViagem2 = DateUtils.toDate(LocalDateTime.now().minusHours(5));
		viagem2.setMotorista(motorista2);
		viagem2.setGravarVeiculo(false);
		viagem2.setSaida(saidaViagem2);

		passageiro3 = new Passageiro();
		passageiro3.setNome(nomePassageiro3);
		passageiro3.setMatricula("3");
		viagem2.addPassageiro(passageiro3);
		viagem2 = salvarRegistro(viagem2);
		assertEquals(1, viagem2.getPassageiros().size());

		Date inicio = DateUtils.toDate(LocalDateTime.now().minusMonths(10));
		Date fim = DateUtils.toDate(LocalDateTime.now().plusMonths(10));
		List<PessoaDisponibilidade> ultimas = viagemMediator.findUltimaDisponibilidade(inicio, fim);
		assertEquals(3, ultimas.size());
		checkPassageiro(saidaViagem1, retornoViagem1, nomePassageiro1, true, ultimas);
		checkPassageiro(saidaViagem1, retornoViagem1, nomePassageiro2, true, ultimas);
		checkPassageiro(saidaViagem2, null, nomePassageiro3, false, ultimas);

	}

	private void checkPassageiro(Date saida, Date entrada, String nomePassageiro, boolean entrou, List<PessoaDisponibilidade> ultimas) {
		Optional<PessoaDisponibilidade> optional = ultimas.stream().filter(p -> p.getNome().equals(nomePassageiro)).findFirst();
		assertTrue(optional.isPresent());
		PessoaDisponibilidade pessoaDisponibilidade = optional.get();
		assertEquals(FonteDisponibilidade.VIAGEM, pessoaDisponibilidade.getFonte());
		assertEquals(entrou, pessoaDisponibilidade.isEntrou());
		assertEquals(DTF.format(saida), DTF.format(pessoaDisponibilidade.getSaida()));
		if (entrada == null) {
			assertNull(pessoaDisponibilidade.getEntrada());
		} else {
			assertEquals(DTF.format(entrada), DTF.format(pessoaDisponibilidade.getEntrada()));
		}
	}
}
