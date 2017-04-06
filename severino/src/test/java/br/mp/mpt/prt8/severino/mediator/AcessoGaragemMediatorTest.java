package br.mp.mpt.prt8.severino.mediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.groups.Default;

import org.hibernate.proxy.HibernateProxy;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.Search;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import br.mp.mpt.prt8.severino.dao.AcessoGaragemRepository;
import br.mp.mpt.prt8.severino.entity.AcessoGaragem;
import br.mp.mpt.prt8.severino.entity.ControleMotorista;
import br.mp.mpt.prt8.severino.entity.Empresa;
import br.mp.mpt.prt8.severino.entity.Estado;
import br.mp.mpt.prt8.severino.entity.FonteDisponibilidade;
import br.mp.mpt.prt8.severino.entity.Local;
import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.entity.Organizacao;
import br.mp.mpt.prt8.severino.entity.Passageiro;
import br.mp.mpt.prt8.severino.entity.Usuario;
import br.mp.mpt.prt8.severino.entity.Veiculo;
import br.mp.mpt.prt8.severino.entity.Viagem;
import br.mp.mpt.prt8.severino.entity.Visita;
import br.mp.mpt.prt8.severino.entity.Visitante;
import br.mp.mpt.prt8.severino.mediator.carga.CargaMotorista;
import br.mp.mpt.prt8.severino.mediator.carga.CargaSetor;
import br.mp.mpt.prt8.severino.mediator.carga.CargaUsuario;
import br.mp.mpt.prt8.severino.utils.Constantes;
import br.mp.mpt.prt8.severino.utils.NegocioException;
import br.mp.mpt.prt8.severino.validators.CadastrarVeiculo;
import br.mp.mpt.prt8.severino.validators.SelecionarVisita;
import br.mp.mpt.prt8.severino.valueobject.PessoaDisponibilidade;

/**
 * Teste para o acesso garagem.
 * 
 * @author sergio.eoliveira
 *
 */
@ContextConfiguration(classes = { CargaUsuario.class, CargaMotorista.class, CargaSetor.class })
public class AcessoGaragemMediatorTest extends AbstractSeverinoTests {
	private static final String PLACA_VEICULO = "HFG4577";
	private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.SHORT, Constantes.DEFAULT_LOCALE);
	private static final DateFormat DATE_TIME_FORMAT = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Constantes.DEFAULT_LOCALE);
	private static final DateFormat DTF = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Autowired
	private AcessoGaragemMediator acessoGaragemMediator;

	@Autowired
	private CargaMotorista cargaMotorista;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private AcessoGaragemRepository acessoGaragemRepository;

	@Autowired
	private VisitaMediator visitaMediator;

	@Autowired
	private CargaSetor cargaSetor;

	@Autowired
	private UsuarioHolder usuarioHolder;

	@Autowired
	private SmartValidator smartValidator;

	@Test(expected = NegocioException.class)
	public void testSalvarVisitaComVeiculoDeServidorOuMembro() throws Exception {
		Visita visita = salvarVisita(null);
		Veiculo veiculo = getVeiculo();
		veiculo.setMotorista(cargaMotorista.getMotorista());
		entityManager.persist(veiculo);
		entityManager.flush();
		entityManager.clear();

		AcessoGaragem acessoGaragem = new AcessoGaragem();
		acessoGaragem.setAnotacao("Teste");

		acessoGaragem.setVeiculo(veiculo);
		acessoGaragem.setUsuarioVisitante(true);
		acessoGaragem.setVisita(visita);
		try {
			salvarAcessoGaragem(acessoGaragem);
		} catch (NegocioException e) {
			assertEquals("O veículo placa " + veiculo.getId() + " é uma viatura ou pertence a um servidor ou membro!", e.getMessage());
			throw e;
		}
	}

	@Test(expected = NegocioException.class)
	public void testSalvarVisitaComVeiculoViatura() throws Exception {
		Visita visita = salvarVisita(null);
		Veiculo veiculo = getVeiculo();
		veiculo.setViaturaMp(true);
		entityManager.persist(veiculo);
		entityManager.flush();
		entityManager.clear();

		AcessoGaragem acessoGaragem = new AcessoGaragem();
		acessoGaragem.setAnotacao("Teste");

		acessoGaragem.setVeiculo(veiculo);
		acessoGaragem.setUsuarioVisitante(true);
		acessoGaragem.setVisita(visita);
		try {
			salvarAcessoGaragem(acessoGaragem);
		} catch (NegocioException e) {
			assertEquals("O veículo placa " + veiculo.getId() + " é uma viatura ou pertence a um servidor ou membro!", e.getMessage());
			throw e;
		}
	}

	@Test(expected = NegocioException.class)
	public void testAlterarVisitaComVisitaAntiga() throws Exception {
		Visita visita = salvarVisita(null);

		AcessoGaragem acessoGaragem = new AcessoGaragem();
		acessoGaragem.setAnotacao("Teste");
		acessoGaragem.setVeiculo(getVeiculo());
		acessoGaragem.setUsuarioVisitante(true);
		acessoGaragem.setVisita(visita);
		try {
			salvarAcessoGaragem(acessoGaragem);
		} catch (NegocioException e) {
			fail("Ocasionou exceção no ponto errado " + e.getMessage());
		}

		Visita visitaPersisted = visitaMediator.findOne(Optional.of(visita.getId()));
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -2);
		visitaPersisted.setEntrada(calendar.getTime());
		entityManager.merge(visitaPersisted);
		entityManager.flush();
		entityManager.clear();
		assertEquals(1, acessoGaragemRepository.count());
		AcessoGaragem acessoGaragemPersistido = acessoGaragemRepository.findAll().get(0);

		acessoGaragemPersistido.setEntrada(calendar.getTime());
		try {
			salvarAcessoGaragem(acessoGaragemPersistido);
		} catch (NegocioException e) {
			fail("Ocasionou exceção no ponto errado " + e.getMessage());
		}
		assertEquals(1, acessoGaragemRepository.count());
		acessoGaragemPersistido = acessoGaragemRepository.findAll().get(0);

		acessoGaragemPersistido.setEntrada(getDataEntradaHoje());
		try {
			salvarAcessoGaragem(acessoGaragemPersistido);
		} catch (NegocioException e) {
			assertEquals("A visita informada não foi cadastrada hoje!", e.getMessage());
			throw e;
		}
	}

	@Test(expected = NegocioException.class)
	public void testSalvarComVisitaAntiga() throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -2);
		Visita visita = salvarVisita(calendar.getTime());

		AcessoGaragem acessoGaragem = new AcessoGaragem();
		acessoGaragem.setAnotacao("Teste");
		acessoGaragem.setVeiculo(getVeiculo());
		acessoGaragem.setUsuarioVisitante(true);
		acessoGaragem.setVisita(visita);
		try {
			salvarAcessoGaragem(acessoGaragem);
		} catch (NegocioException e) {
			assertEquals("A visita informada não foi cadastrada hoje!", e.getMessage());
			throw e;
		}
	}

	@Test(expected = NegocioException.class)
	public void testSalvarComVisitaJaAssociada() throws Exception {
		Visita visita = salvarVisita(null);

		AcessoGaragem acessoGaragem = new AcessoGaragem();
		acessoGaragem.setAnotacao("Teste");
		acessoGaragem.setVeiculo(getVeiculo());
		acessoGaragem.setUsuarioVisitante(true);
		acessoGaragem.setVisita(visita);
		try {
			salvarAcessoGaragem(acessoGaragem);
		} catch (NegocioException e) {
			fail("Não era pra dar exceção: " + e.getMessage());
		}

		assertEquals(1, acessoGaragemRepository.count());

		AcessoGaragem acessoGaragem2 = new AcessoGaragem();
		acessoGaragem2.setVeiculo(getVeiculo());
		acessoGaragem2.setUsuarioVisitante(true);
		acessoGaragem2.setVisita(visita);
		try {
			salvarAcessoGaragem(acessoGaragem2);
		} catch (NegocioException e) {
			assertEquals("Já existe um acesso associado a visita selecionada!", e.getMessage());
			throw e;
		}
	}

	@Test(expected = NegocioException.class)
	public void testSalvarMembroServidorComIntervaloConflitante() throws Exception {
		Veiculo veiculo = getVeiculo();
		veiculo.setMotorista(cargaMotorista.getMotorista());
		entityManager.persist(veiculo);
		entityManager.flush();
		entityManager.clear();

		AcessoGaragem acessoGaragem = new AcessoGaragem();

		acessoGaragem.setVeiculo(veiculo);
		acessoGaragem.setUsuarioVisitante(false);

		Calendar cEntrada = Calendar.getInstance();
		cEntrada.set(2015, 10, 10);
		cEntrada.set(Calendar.HOUR, 10);
		cEntrada.set(Calendar.MINUTE, 0);

		Date entrada = cEntrada.getTime();
		acessoGaragem.setEntrada(entrada);

		Calendar cSaida = Calendar.getInstance();
		cSaida.set(2015, 10, 10);
		cSaida.set(Calendar.HOUR, 12);
		cSaida.set(Calendar.MINUTE, 0);
		Date saida = cSaida.getTime();
		acessoGaragem.setSaida(saida);
		try {
			salvarAcessoGaragem(acessoGaragem);
		} catch (NegocioException e) {
			fail("Não era pra ter dado exceção aqui " + e.getMessage());
		}
		assertNotNull(acessoGaragem.getId());

		AcessoGaragem acessoGaragem2 = new AcessoGaragem();

		acessoGaragem2.setVeiculo(veiculo);
		acessoGaragem2.setUsuarioVisitante(false);

		cEntrada.set(Calendar.MINUTE, 20);
		acessoGaragem2.setEntrada(cEntrada.getTime());
		cSaida.add(Calendar.MINUTE, 20);
		acessoGaragem2.setSaida(cSaida.getTime());
		try {
			salvarAcessoGaragem(acessoGaragem2);
		} catch (NegocioException e) {
			assertEquals("O acesso a garagem do veículo placa " + veiculo.getId() + " possui intervalo de entrada e saída conflitante com a(s) acessos(s) " + acessoGaragem.getId(), e.getMessage());
			throw e;

		}
	}

	@Test
	public void testSalvarComVisita() throws Exception {
		Visita visita = salvarVisita(null);

		AcessoGaragem acessoGaragem = new AcessoGaragem();
		acessoGaragem.setAnotacao("Teste");
		acessoGaragem.setVeiculo(getVeiculo());
		acessoGaragem.setUsuarioVisitante(true);
		acessoGaragem.setVisita(visita);
		salvarAcessoGaragem(acessoGaragem);

		assertEquals(1, acessoGaragemRepository.count());
		AcessoGaragem acessoGaragemPersistido = acessoGaragemRepository.findAll().get(0);
		assertNull(acessoGaragemPersistido.getMotorista());
		assertEquals(visita, acessoGaragemPersistido.getVisita());
		assertNotNull(acessoGaragemPersistido.getVeiculo());
		assertEquals(PLACA_VEICULO, acessoGaragemPersistido.getVeiculo().getId());
	}

	private Veiculo getVeiculo() {
		Veiculo veiculo = new Veiculo();
		veiculo.setCor("Prata");
		veiculo.setId(PLACA_VEICULO);
		veiculo.setMarca("Toyota");
		veiculo.setModelo("Hilux");
		veiculo.setLocal(usuarioHolder.getLocal());
		return veiculo;
	}

	private Visita salvarVisita(Date dataEntrada) {
		Visita visita = new Visita();
		Empresa empresa = new Empresa();
		empresa.setNome("EMPRESA 1");
		visita.setEmpresa(empresa);
		visita.setSetor(cargaSetor.getSetor1());
		Visitante visitante = new Visitante();
		visita.setVisitante(visitante);
		visita.setEntrada(dataEntrada);
		visitante.setDocumento("123456");
		visitante.setNome("José do Egito");
		visitante.setOrgaoEmissor("SSP-DF");
		visitante.setUf(Estado.DF);
		visitaMediator.save(visita);
		entityManager.flush();
		entityManager.clear();
		return visita;
	}

	@Test(expected = NegocioException.class)
	public void testSalvarServidorMembroComVeiculoSemDono() throws Exception {
		Veiculo veiculo = getVeiculo();
		entityManager.persist(veiculo);
		entityManager.flush();
		entityManager.clear();

		AcessoGaragem acessoGaragem = new AcessoGaragem();
		Date entrada = getDataEntradaHoje();
		acessoGaragem.setEntrada(entrada);
		acessoGaragem.setMotorista(cargaMotorista.getMotorista());
		acessoGaragem.setVeiculo(veiculo);

		try {
			salvarAcessoGaragem(acessoGaragem);
		} catch (NegocioException e) {
			assertEquals("Este veículo não possui dono!", e.getMessage());
			throw e;
		}
	}

	@Test(expected = NegocioException.class)
	public void testSalvarServidorMembroComVeiculoInexistente() throws Exception {
		Veiculo veiculo = getVeiculo();
		AcessoGaragem acessoGaragem = new AcessoGaragem();
		Date entrada = getDataEntradaHoje();
		acessoGaragem.setEntrada(entrada);
		acessoGaragem.setMotorista(cargaMotorista.getMotorista());
		acessoGaragem.setVeiculo(veiculo);

		try {
			salvarAcessoGaragem(acessoGaragem);
		} catch (NegocioException e) {
			assertEquals("Não foi encontrado veículo com a placa " + veiculo.getId(), e.getMessage());
			throw e;
		}
	}

	@Test(expected = NegocioException.class)
	public void testSalvarServidorMembroDuasVezes() throws Exception {
		Veiculo veiculo = getVeiculo();
		Motorista motorista = cargaMotorista.getMotorista();
		veiculo.setMotorista(motorista);
		entityManager.persist(veiculo);
		entityManager.flush();
		entityManager.clear();

		AcessoGaragem acessoGaragem = new AcessoGaragem();
		Date entrada = getDataEntradaHoje();
		acessoGaragem.setEntrada(entrada);
		acessoGaragem.setMotorista(motorista);
		acessoGaragem.setVeiculo(veiculo);

		try {
			salvarAcessoGaragem(acessoGaragem);
		} catch (NegocioException e) {
			fail("Ocasionou exceção no ponto errado " + e.getMessage());
		}

		AcessoGaragem acessoGaragem2 = new AcessoGaragem();
		acessoGaragem2.setEntrada(entrada);
		acessoGaragem2.setMotorista(motorista);
		acessoGaragem2.setVeiculo(veiculo);
		try {
			salvarAcessoGaragem(acessoGaragem2);
		} catch (NegocioException e) {
			assertEquals("O servidor ou membro selecionado (" + motorista.getId() + ") já entrou na garagem e não saiu!", e.getMessage());
			throw e;
		}
	}

	@Test
	public void testSalvarServidorMembro() throws Exception {

		Veiculo veiculo = new Veiculo();
		veiculo.setCor("Prata");
		String placa = "JDS5300";
		veiculo.setId(placa);
		String marca = "Nissan";
		veiculo.setMarca(marca);
		veiculo.setModelo("Livina");
		Motorista motorista = cargaMotorista.getMotorista();
		veiculo.setMotorista(motorista);
		veiculo.setLocal(usuarioHolder.getLocal());
		entityManager.persist(veiculo);
		entityManager.flush();
		entityManager.clear();

		AcessoGaragem acessoGaragem = new AcessoGaragem();
		Date entrada = getDataEntradaHoje();
		acessoGaragem.setEntrada(entrada);
		acessoGaragem.setMotorista(motorista);
		String anotacao = "Teste";
		acessoGaragem.setAnotacao(anotacao);
		veiculo.setMarca("Este Valor Não Será Persistido");
		acessoGaragem.setVeiculo(veiculo);

		salvarAcessoGaragem(acessoGaragem);
		assertEquals(1, acessoGaragemRepository.count());
		AcessoGaragem acessoGaragemPersistido = acessoGaragemRepository.findAll().get(0);
		assertEquals(anotacao, acessoGaragemPersistido.getAnotacao());
		assertNotNull(acessoGaragemPersistido.getDataHoraCadastro());
		assertEquals(DATE_FORMAT.format(new Date()), DATE_FORMAT.format(acessoGaragemPersistido.getDataHoraCadastro()));
		assertEquals(DATE_TIME_FORMAT.format(entrada), DATE_TIME_FORMAT.format(acessoGaragemPersistido.getEntrada()));
		assertNotNull(acessoGaragemPersistido.getUsuario());
		assertEquals(motorista, acessoGaragemPersistido.getMotorista());
		assertEquals(motorista.getNome(), acessoGaragemPersistido.getMotorista().getNome());
		assertNotNull(acessoGaragemPersistido.getVeiculo());
		assertEquals(placa, acessoGaragemPersistido.getVeiculo().getId());
		assertEquals(marca, acessoGaragemPersistido.getVeiculo().getMarca());
		assertNull(acessoGaragemPersistido.getVisita());
		assertNull(acessoGaragemPersistido.getSaida());

		List<AcessoGaragem> resultado = acessoGaragemMediator.findAllSemBaixa();
		assertEquals(1, resultado.size());

		acessoGaragemPersistido.setRegistrarSaida(true);
		salvarAcessoGaragem(acessoGaragemPersistido);
		acessoGaragemPersistido = acessoGaragemRepository.findAll().get(0);
		assertNotNull(acessoGaragemPersistido.getSaida());
		assertEquals(DATE_TIME_FORMAT.format(new Date()), DATE_TIME_FORMAT.format(acessoGaragemPersistido.getSaida()));

		DataTablesInput dataTablesInput = new DataTablesInput();
		dataTablesInput.setStart(0);
		dataTablesInput.setLength(4);
		dataTablesInput.setSearch(new Search(motorista.getNome(), false));
		Page<AcessoGaragem> page = acessoGaragemMediator.find(dataTablesInput);
		assertEquals(1, page.getTotalElements());
		dataTablesInput.setSearch(new Search(null, false));
		page = acessoGaragemMediator.find(dataTablesInput);
		assertEquals(1, page.getTotalElements());
	}

	private Date getDataEntradaHoje() throws ParseException {
		return DATE_TIME_FORMAT.parse(DATE_TIME_FORMAT.format(new Date()));
	}

	private void salvarAcessoGaragem(AcessoGaragem acessoGaragem) {
		Veiculo veiculo = acessoGaragem.getVeiculo();
		if (veiculo instanceof HibernateProxy) {
			acessoGaragem.setVeiculo((Veiculo) ((HibernateProxy) veiculo).getHibernateLazyInitializer().getImplementation());
		}
		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(acessoGaragem, "acessoGaragem", true, 256);
		if (acessoGaragem.isUsuarioVisitante()) {
			smartValidator.validate(acessoGaragem, errors, Default.class, CadastrarVeiculo.class, SelecionarVisita.class);
		} else {
			smartValidator.validate(acessoGaragem, errors);
		}
		assertFalse("Validação falhou..." + errors.toString(), errors.hasErrors());
		assertNotNull(acessoGaragemMediator.save(acessoGaragem));
		entityManager.flush();
		entityManager.clear();
	}

	@Test
	@Sql("/testFindUltimaDisponibilidade.sql")
	public void testFindUltimaDisponibilidade() throws Exception {
		List<PessoaDisponibilidade> ultimas = acessoGaragemMediator.findUltimaDisponibilidade(DTF.parse("2016-01-01 00:00"), DTF.parse("2017-01-01 00:00"));
		assertEquals(3, ultimas.size());

		checkPassageiro("2016-11-14 13:06", "2016-11-14 15:29", "Cintia Nazare Pantoja Leao", false, ultimas);
		checkPassageiro("2016-11-16 08:07", null, "Carla Afonso de Novoa Melo", true, ultimas);
		checkPassageiro("2016-11-16 08:54", null, "Faustino Bartolomeu Alves Pimenta", true, ultimas);
		deleteFromTables(Passageiro.class, Viagem.class, ControleMotorista.class, AcessoGaragem.class, Veiculo.class, Usuario.class, Motorista.class, Local.class, Organizacao.class);

	}

	private void checkPassageiro(String entrada, String saida, String nomePassageiro, boolean entrou, List<PessoaDisponibilidade> ultimas) {
		Optional<PessoaDisponibilidade> optional = ultimas.stream().filter(p -> p.getNome().equals(nomePassageiro)).findFirst();
		assertTrue(optional.isPresent());
		PessoaDisponibilidade pessoaDisponibilidade = optional.get();
		assertEquals(FonteDisponibilidade.ACESSO_GARAGEM, pessoaDisponibilidade.getFonte());
		assertEquals(entrou, pessoaDisponibilidade.isEntrou());
		assertEquals(entrada, DTF.format(pessoaDisponibilidade.getEntrada()));
		if (saida == null) {
			assertNull(pessoaDisponibilidade.getSaida());
		} else {
			assertEquals(saida, DTF.format(pessoaDisponibilidade.getSaida()));
		}
	}

}
