package br.mp.mpt.prt8.severino.mediator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.Search;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

import br.mp.mpt.prt8.severino.entity.Local;
import br.mp.mpt.prt8.severino.entity.Organizacao;
import br.mp.mpt.prt8.severino.entity.Usuario;

/**
 * Testes para o usuário.
 * 
 * @author sergio.eoliveira
 *
 */
public class UsuarioMediatorTest extends AbstractSeverinoTests {

	private static final String USUARIO_NOME = "Sergio Eduardo";
	private static final String USUARIO_LOGIN = "sergio.eoliveira";

	@Autowired
	private UsuarioMediator usuarioMediator;

	private Authentication authenticationMock;
	private LdapUserDetails userDetailsMock;

	private Authentication configureMocks(String nome, String login) {
		return configureMocksDn(login, "cn=" + nome + ",ou=Usuarios,ou=Sede,ou=PRT08,ou=MPT,dc=mpt,dc=intra");
	}

	private Authentication configureMocksDn(String login, String dn) {
		if (authenticationMock == null && userDetailsMock == null) {
			this.authenticationMock = Mockito.mock(Authentication.class);
			this.userDetailsMock = Mockito.mock(LdapUserDetails.class);
			Mockito.when(userDetailsMock.getDn()).thenReturn(dn);
			Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsMock);
			Mockito.when(authenticationMock.getName()).thenReturn(login);
		}
		return authenticationMock;
	}

	@Test
	public void testSaveSemNome() {
		usuarioMediator.save(configureMocks("", USUARIO_LOGIN));

		entityManager.flush();
		verificarComportamentoPadrao();

		DataTablesInput dataTablesInput = new DataTablesInput();
		dataTablesInput.setStart(0);
		dataTablesInput.setLength(4);
		dataTablesInput.setSearch(new Search(USUARIO_LOGIN, false));
		Page<Usuario> resultado = usuarioMediator.find(dataTablesInput);
		assertEquals(1, resultado.getTotalElements());
		Usuario usuario = resultado.getContent().get(0);
		assertEquals(USUARIO_LOGIN, usuario.getNome());
		assertEquals(USUARIO_LOGIN, usuario.getId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveSemDn() throws Exception {
		try {
			usuarioMediator.save(configureMocksDn(USUARIO_LOGIN, ""));
		} catch (IllegalArgumentException e) {
			assertEquals("O valor do DN é obrigatório, porque cargas dágua não veio preenchido?!", e.getMessage());
			throw e;
		}

		entityManager.flush();
		verificarComportamentoPadrao();

		DataTablesInput dataTablesInput = new DataTablesInput();
		dataTablesInput.setStart(0);
		dataTablesInput.setLength(4);
		dataTablesInput.setSearch(new Search(USUARIO_LOGIN, false));
		Page<Usuario> resultado = usuarioMediator.find(dataTablesInput);
		assertEquals(1, resultado.getTotalElements());
		Usuario usuario = resultado.getContent().get(0);
		assertEquals(USUARIO_LOGIN, usuario.getNome());
		assertEquals(USUARIO_LOGIN, usuario.getId());
	}

	@Test
	public void testHolderJaPreenchido() throws Exception {
		usuarioMediator.save(configureMocks(USUARIO_NOME, USUARIO_LOGIN));

		entityManager.flush();
		verificarComportamentoPadrao();

		usuarioMediator.save(configureMocks(USUARIO_NOME, USUARIO_LOGIN));
		Mockito.verifyZeroInteractions(authenticationMock, userDetailsMock);
	}

	@Test
	public void testSave() {
		usuarioMediator.save(configureMocks(USUARIO_NOME, USUARIO_LOGIN));

		entityManager.flush();
		verificarComportamentoPadrao();

		DataTablesInput dataTablesInput = new DataTablesInput();
		dataTablesInput.setStart(0);
		dataTablesInput.setLength(4);
		dataTablesInput.setSearch(new Search(USUARIO_LOGIN, false));
		Page<Usuario> resultado = usuarioMediator.find(dataTablesInput);
		assertEquals(1, resultado.getTotalElements());
		Usuario usuario = resultado.getContent().get(0);
		assertEquals(USUARIO_NOME, usuario.getNome());
		assertEquals(USUARIO_LOGIN, usuario.getId());
	}

	private void verificarComportamentoPadrao() {
		Mockito.verify(authenticationMock, Mockito.times(1)).getName();
		Mockito.verify(authenticationMock, Mockito.times(1)).getPrincipal();
		Mockito.verify(userDetailsMock, Mockito.times(1)).getDn();
		Mockito.reset(authenticationMock, userDetailsMock);
	}

	@Test
	public void testUpdate() throws Exception {
		Usuario usuario = new Usuario();
		usuario.setId(USUARIO_LOGIN);
		usuario.setNome(USUARIO_NOME);
		usuario.setLocal(getLocal());
		usuarioMediator.save(usuario);
		entityManager.flush();

		usuarioMediator.save(configureMocks(USUARIO_NOME, USUARIO_LOGIN));

		verificarComportamentoPadrao();

	}

	private Local getLocal() {
		Local locTeste = new Local("teste");
		Organizacao organizacao = new Organizacao("teste2");
		entityManager.persist(organizacao);
		locTeste.setOrganizacao(organizacao);
		entityManager.persist(locTeste);
		return locTeste;
	}

	@Test
	public void testUpdateNome() throws Exception {
		Usuario usuario = new Usuario();
		usuario.setId(USUARIO_LOGIN);
		usuario.setNome("Casei mudei de nome");
		usuario.setLocal(getLocal());
		usuarioMediator.save(usuario);
		entityManager.flush();

		usuarioMediator.save(configureMocks(USUARIO_NOME, USUARIO_LOGIN));

		verificarComportamentoPadrao();

	}
}
