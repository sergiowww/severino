package br.mp.mpt.prt8.severino.mediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import br.mp.mpt.prt8.severino.dao.VisitaRepository;
import br.mp.mpt.prt8.severino.dao.VisitanteRepository;
import br.mp.mpt.prt8.severino.entity.Empresa;
import br.mp.mpt.prt8.severino.entity.Estado;
import br.mp.mpt.prt8.severino.entity.Setor;
import br.mp.mpt.prt8.severino.entity.Usuario;
import br.mp.mpt.prt8.severino.entity.Visita;
import br.mp.mpt.prt8.severino.entity.Visitante;
import br.mp.mpt.prt8.severino.utils.NegocioException;
import br.mp.mpt.prt8.severino.validatorgroups.CadastrarVisita;

/**
 * Casos de teste para a visita.
 * 
 * @author sergio.eoliveira
 *
 */
public class VisitaMediatorTest extends AbstractSeverinoTests {
	private static final Estado VISITANTE_ESTADO = Estado.DF;
	private static final String VISITANTE_ORGAO = "SSP-DF";
	private static final String VISITANTE_DOCUMENTO = "123456";
	private static final String VISITANTE_NOME = "José do Egito";
	private static final String NOME_SETOR_2 = "Setor 2";
	private static final String NOME_SETOR_1 = "Setor 1";
	private static final String NOME_EMPRESA = "EMPRESA 1";

	@Autowired
	private VisitaMediator visitaMediator;

	@Autowired
	private SetorMediator setorMediator;

	@Autowired
	private UsuarioHolder usuarioHolder;

	@Autowired
	private UsuarioMediator usuarioMediator;

	@Autowired
	private SmartValidator smartValidator;

	@Autowired
	private VisitaRepository visitaRepository;

	@Autowired
	private EmpresaMediator empresaMediator;

	@Autowired
	private VisitanteMediator visitanteMediator;

	@Autowired
	private VisitanteRepository visitanteRepository;

	private Setor setor2;

	private Setor setor1;

	@Before
	public void cargaUsuario() {
		Usuario usuario = new Usuario();
		usuario.setId("sergio.eoliveira");
		usuario.setNome("Sergio Eduardo");
		usuarioHolder.setUsuario(usuario);
		usuarioMediator.save(usuario);
	}

	@Before
	public void cargaSetores() {
		List<Setor> registros = setorMediator.findAll();
		if (registros.isEmpty()) {
			this.setor1 = new Setor();
			setor1.setAndar((short) 1);
			setor1.setNome(NOME_SETOR_1);
			setor1.setSala("001");
			setorMediator.save(setor1);

			this.setor2 = new Setor();
			setor2.setAndar((short) 1);
			setor2.setNome(NOME_SETOR_2);
			setor2.setSala("001");
			setorMediator.save(setor2);
		} else {
			this.setor1 = registros.stream().filter(s -> s.getNome().equals(NOME_SETOR_1)).findFirst().get();
			this.setor2 = registros.stream().filter(s -> s.getNome().equals(NOME_SETOR_2)).findFirst().get();
		}
	}

	/**
	 * Nova empresa; Novo visitante; sem informar data de entrada; apagar em
	 * seguida.
	 */
	@Test
	public void testCenario1() {
		Visita visita = new Visita();
		Empresa empresa = new Empresa();
		empresa.setNome(NOME_EMPRESA);
		visita.setEmpresa(empresa);
		visita.setSetor(setor1);
		Visitante visitante = new Visitante();
		visita.setVisitante(visitante);
		visitante.setDocumento(VISITANTE_DOCUMENTO);
		visitante.setNome(VISITANTE_NOME);
		visitante.setOrgaoEmissor(VISITANTE_ORGAO);
		visitante.setUf(VISITANTE_ESTADO);
		validarVisita(visita);
		visitaMediator.save(visita);
		Visita visitaCheckar = getFirst();
		assertVisita(null, visitaCheckar);
		assertEmpresa(null, visitaCheckar);

		assertEquals(1, visitaMediator.findVisitasSemBaixa().size());

		Integer id = visitaCheckar.getId();
		Integer idVisitante = visita.getVisitante().getId();
		visitaMediator.apagar(id);
		entityManager.flush();
		entityManager.clear();
		assertFalse(visitaRepository.exists(id));
		assertTrue(visitanteRepository.exists(idVisitante));
	}

	/**
	 * Apagar uma visita como outro usuário (não pode)
	 * 
	 * @throws Exception
	 */
	@Test(expected = NegocioException.class)
	public void testApagarComoOutroUsuario() throws Exception {
		Integer indice = 1;
		Visita visita = new Visita();
		Empresa empresa = new Empresa();
		empresa.setNome(indice + NOME_EMPRESA);
		visita.setEmpresa(empresa);
		visita.setSetor(setor2);
		Visitante visitante = new Visitante();
		visitante.setDocumento(indice + VISITANTE_DOCUMENTO);
		visitante.setNome(indice + VISITANTE_NOME);
		visitante.setOrgaoEmissor(indice + VISITANTE_ORGAO);
		visitante.setUf(VISITANTE_ESTADO);
		visita.setVisitante(visitante);
		Date entrada = getDataEntradaValida();
		visita.setEntrada(entrada);
		validarVisita(visita);

		visitaMediator.save(visita);

		Integer id = visita.getId();
		Usuario usuario = new Usuario();
		usuario.setId("outrousuario");
		usuario.setNome("Usuarioque tenta apagar o registro");
		usuarioHolder.setUsuario(usuario);
		try {
			visitaMediator.apagar(id);
			fail("Não deveria ter chegado aqui");
		} catch (NegocioException e) {
			assertEquals("A visita só pode ser removida no mesmo dia e pelo mesmo usuário criador.", e.getMessage());
			throw e;
		}

	}

	/**
	 * Empresa existente; Visitante existente; informado data de entrada; apagar
	 * como outro usuário.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCenario2() throws Exception {
		Integer indice = 1;
		Visita visita = new Visita();
		Empresa empresa = new Empresa();
		empresa.setNome(indice + NOME_EMPRESA);

		visita.setEmpresa(empresaMediator.save(empresa));
		assertNotNull(visita.getEmpresa().getId());
		visita.setSetor(setor2);
		Visitante visitante = new Visitante();
		visitante.setDocumento(indice + VISITANTE_DOCUMENTO);
		visitante.setNome(indice + VISITANTE_NOME);
		visitante.setOrgaoEmissor(indice + VISITANTE_ORGAO);
		visitante.setUf(VISITANTE_ESTADO);
		visita.setVisitante(visitanteMediator.save(visitante));
		Date entrada = getDataEntradaValida();
		visita.setEntrada(entrada);
		assertNotNull(visita.getVisitante().getId());
		validarVisita(visita);

		visitaMediator.save(visita);

		Visita visitaCheckar = getFirst();
		assertVisita(indice, visitaCheckar);
		assertEmpresa(indice, visitaCheckar);
		assertEquals(entrada, visitaCheckar.getEntrada());
	}

	/**
	 * Empresa somente com id; Visitante existente; informado data de entrada.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCenario4() throws Exception {
		Integer indice = 3;
		Visita visita = new Visita();
		Empresa empresa = new Empresa();
		empresa.setNome(indice + NOME_EMPRESA);

		Empresa empresaPersisted = empresaMediator.save(empresa);
		Empresa empresaAssociada = new Empresa();
		empresaAssociada.setId(empresaPersisted.getId());
		visita.setEmpresa(empresaAssociada);
		assertNotNull(visita.getEmpresa().getId());
		visita.setSetor(setor2);
		Visitante visitante = new Visitante();
		visitante.setDocumento(indice + VISITANTE_DOCUMENTO);
		visitante.setNome(indice + VISITANTE_NOME);
		visitante.setOrgaoEmissor(indice + VISITANTE_ORGAO);
		visitante.setUf(VISITANTE_ESTADO);
		visita.setVisitante(visitanteMediator.save(visitante));
		Date entrada = getDataEntradaValida();
		visita.setEntrada(entrada);
		assertNotNull(visita.getVisitante().getId());
		validarVisita(visita);

		visitaMediator.save(visita);

		Visita visitaCheckar = getFirst();
		assertVisita(indice, visitaCheckar);
		assertEmpresa(indice, visitaCheckar);
		assertEquals(entrada, visitaCheckar.getEntrada());
	}

	/**
	 * Testar visitante que entrou no prédio e não saiu ainda.
	 * 
	 * @throws Exception
	 */
	@Test(expected = NegocioException.class)
	public void testCenario6() throws Exception {
		Integer indice = 5;
		Visita visita = new Visita();
		visita.setSetor(setor2);
		Visitante visitante = new Visitante();
		visitante.setDocumento(indice + VISITANTE_DOCUMENTO);
		visitante.setNome(indice + VISITANTE_NOME);
		visitante.setOrgaoEmissor(indice + VISITANTE_ORGAO);
		visitante.setUf(VISITANTE_ESTADO);
		visita.setVisitante(visitante);
		Date entrada = getDataEntradaValida();
		visita.setEntrada(entrada);
		validarVisita(visita);

		visitaMediator.save(visita);

		Visita visitaCheckar = getFirst();
		assertVisita(indice, visitaCheckar);
		assertNull(visitaCheckar.getEmpresa());
		assertEquals(entrada, visitaCheckar.getEntrada());
		assertNull(visitaCheckar.getSaida());
		entityManager.detach(visitaCheckar);
		visitaCheckar.setId(null);

		try {
			visitaMediator.save(visitaCheckar);
			fail("Não deveria ter dado certo");
		} catch (NegocioException e) {
			assertEquals("O visitante com documento " + indice + VISITANTE_DOCUMENTO + " já entrou no prédio e não saiu!", e.getMessage());
			throw e;
		}
	}

	/**
	 * Testar visitante com um horário em que ele já esteve no prédio.
	 * 
	 * @throws Exception
	 */
	@Test(expected = NegocioException.class)
	public void testCenario7() throws Exception {
		Integer indice = 6;
		Visita visita = new Visita();
		visita.setSetor(setor2);
		Visitante visitante = new Visitante();
		visitante.setDocumento(indice + VISITANTE_DOCUMENTO);
		visitante.setNome(indice + VISITANTE_NOME);
		visitante.setOrgaoEmissor(indice + VISITANTE_ORGAO);
		visitante.setUf(VISITANTE_ESTADO);
		visita.setVisitante(visitante);

		Calendar cEntrada = Calendar.getInstance();
		cEntrada.set(2015, 10, 10);
		cEntrada.set(Calendar.HOUR, 10);
		cEntrada.set(Calendar.MINUTE, 0);

		Date entrada = cEntrada.getTime();
		visita.setEntrada(entrada);

		Calendar cSaida = Calendar.getInstance();
		cSaida.set(2015, 10, 10);
		cSaida.set(Calendar.HOUR, 12);
		cSaida.set(Calendar.MINUTE, 0);
		Date saida = cSaida.getTime();
		visita.setSaida(saida);

		validarVisita(visita);

		visitaMediator.save(visita);

		Visita visitaCheckar = getFirst();
		assertVisita(indice, visitaCheckar);
		assertNull(visitaCheckar.getEmpresa());
		assertEquals(entrada, visitaCheckar.getEntrada());
		assertEquals(saida, visitaCheckar.getSaida());
		entityManager.detach(visitaCheckar);
		visitaCheckar.setId(null);

		cEntrada.set(Calendar.MINUTE, 20);
		visitaCheckar.setEntrada(cEntrada.getTime());
		cSaida.add(Calendar.MINUTE, 20);
		visitaCheckar.setSaida(cSaida.getTime());

		try {
			visitaMediator.save(visitaCheckar);
			fail("Não deveria ter dado certo");
		} catch (NegocioException e) {
			assertEquals("O visitante com documento " + indice + VISITANTE_DOCUMENTO + " possui intervalo de entrada e saída conflitante com a(s) visita(s) " + visita.getId(), e.getMessage());
			throw e;
		}
	}

	/**
	 * Sem empresa; Visitante existente; informado data de entrada; registrando
	 * saída apos cadastro.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCenario5() throws Exception {
		Integer indice = 4;
		Visita visita = new Visita();
		visita.setSetor(setor2);
		Visitante visitante = new Visitante();
		visitante.setDocumento(indice + VISITANTE_DOCUMENTO);
		visitante.setNome(indice + VISITANTE_NOME);
		visitante.setOrgaoEmissor(indice + VISITANTE_ORGAO);
		visitante.setUf(VISITANTE_ESTADO);
		visita.setVisitante(visitanteMediator.save(visitante));
		Date entrada = getDataEntradaValida();
		visita.setEntrada(entrada);
		assertNotNull(visita.getVisitante().getId());
		validarVisita(visita);

		visitaMediator.save(visita);

		Visita visitaCheckar = getFirst();
		assertVisita(indice, visitaCheckar);
		assertNull(visitaCheckar.getEmpresa());
		assertEquals(entrada, visitaCheckar.getEntrada());
		assertNull(visitaCheckar.getSaida());

		visitaCheckar.setRegistrarSaida(true);
		visitaMediator.save(visitaCheckar);
		visitaCheckar = getFirst();
		assertNotNull(visitaCheckar.getSaida());
	}

	private Date getDataEntradaValida() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, -1);
		Date entrada = c.getTime();
		return entrada;
	}

	/**
	 * Empresa vazia (nenhum dado preenchido); Novo visitante; sem informar data
	 * de entrada.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCenario3() throws Exception {
		Integer indice = 2;
		Visita visita = new Visita();
		Empresa empresa = new Empresa();
		empresa.setNome("");

		visita.setEmpresa(empresa);
		assertNull(visita.getEmpresa().getId());
		visita.setSetor(setor2);
		Visitante visitante = new Visitante();
		visitante.setDocumento(indice + VISITANTE_DOCUMENTO);
		visitante.setNome(indice + VISITANTE_NOME);
		visitante.setOrgaoEmissor(indice + VISITANTE_ORGAO);
		visitante.setUf(VISITANTE_ESTADO);
		visita.setVisitante(visitante);
		assertNull(visita.getVisitante().getId());
		validarVisita(visita);
		visitaMediator.save(visita);
		Visita visitaCheckar = getFirst();
		assertVisita(indice, visitaCheckar);
		assertNull(visitaCheckar.getEmpresa());
	}

	private void assertVisita(Integer indice, Visita visitaCheckar) {
		String indiceString = Objects.toString(indice, "");
		assertNotNull(visitaCheckar.getVisitante());
		assertEquals(indiceString + VISITANTE_DOCUMENTO, visitaCheckar.getVisitante().getDocumento());
		assertNotNull(visitaCheckar.getVisitante().getId());
		assertEquals(indiceString + VISITANTE_NOME, visitaCheckar.getVisitante().getNome());
		assertEquals(indiceString + VISITANTE_ORGAO, visitaCheckar.getVisitante().getOrgaoEmissor());
		assertEquals(VISITANTE_ESTADO, visitaCheckar.getVisitante().getUf());
		assertNotNull(visitaCheckar.getEntrada());
		assertNotNull(visitaCheckar.getUsuario());
		assertEquals(usuarioHolder.getUsuario().getId(), visitaCheckar.getUsuario().getId());
	}

	private Visita getFirst() {
		entityManager.clear();
		List<Visita> registros = visitaRepository.findAll();
		Visita visitaCheckar = registros.stream().findFirst().get();
		assertFalse(registros.isEmpty());
		return visitaCheckar;
	}

	private void assertEmpresa(Integer indice, Visita visitaCheckar) {
		String indiceString = Objects.toString(indice, "");
		assertNotNull(visitaCheckar.getEmpresa());
		assertEquals(indiceString + NOME_EMPRESA, visitaCheckar.getEmpresa().getNome());
	}

	private void validarVisita(Visita visita) {
		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(visita, "visita", true, 256);
		smartValidator.validate(visita, errors, CadastrarVisita.class);
		assertFalse("Validação falhou..." + errors.toString(), errors.hasErrors());
	}

}
