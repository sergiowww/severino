package br.mp.mpt.prt8.severino.controller;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import br.mp.mpt.prt8.severino.mediator.DeployInfoMediator;

/**
 * Teste do controlador de login.
 * 
 * @author sergio.eoliveira
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {
	@Mock
	private DeployInfoMediator deploInfoMediator;

	@Mock
	private HttpServletRequest requestMock;

	@InjectMocks
	private LoginController loginController;

	@Test
	public void testLogin() {
		Map<String, String[]> parameterMap = new HashMap<>();
		Mockito.when(requestMock.getParameterMap()).thenReturn(parameterMap);
		ModelAndView model = loginController.login(requestMock);
		assertEquals(1, model.getModelMap().size());
	}

	@Test
	public void testLoginFalha() {
		Map<String, String[]> parameterMap = new HashMap<>();
		parameterMap.put("error", new String[] { "true" });
		Mockito.when(requestMock.getParameterMap()).thenReturn(parameterMap);
		ModelAndView model = loginController.login(requestMock);
		assertEquals(2, model.getModelMap().size());
		assertEquals(1, model.getModelMap().values().stream().filter(p -> "Usuário ou senha inválidos!".equals(p)).count());
	}

	@Test
	public void testLogout() {
		Map<String, String[]> parameterMap = new HashMap<>();
		parameterMap.put("logout", new String[] { "true" });
		Mockito.when(requestMock.getParameterMap()).thenReturn(parameterMap);
		ModelAndView model = loginController.login(requestMock);
		assertEquals(2, model.getModelMap().size());
		assertEquals(1, model.getModelMap().values().stream().filter(p -> "Sessão finalizada, efetue login para entrar novamente".equals(p)).count());
	}

}
