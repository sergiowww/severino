package br.mp.mpt.prt8.severino.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import br.mp.mpt.prt8.severino.mediator.ControleMotoristaMediator;
import br.mp.mpt.prt8.severino.mediator.PessoaDisponibilidadeMediator;
import br.mp.mpt.prt8.severino.mediator.UsuarioMediator;

/**
 * Teste do home controller.
 * 
 * @author sergio.eoliveira
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {
	@InjectMocks
	private HomeController homeController;

	@Mock
	private UsuarioMediator usuarioMediatorMock;

	@Mock
	private PessoaDisponibilidadeMediator pessoaDisponibilidadeMediatorMock;

	@Mock
	private ControleMotoristaMediator controleMotoristaMediatorMock;

	@Test
	public void testInicio() {
		Authentication authenticationMock = Mockito.mock(Authentication.class);
		homeController.inicio(authenticationMock);

		Mockito.verify(pessoaDisponibilidadeMediatorMock, Mockito.only()).findUltimaDisponibilidade(Mockito.anyObject(), Mockito.anyObject());
		Mockito.verify(controleMotoristaMediatorMock, Mockito.only()).findDisponiveis();
		Mockito.verify(usuarioMediatorMock, Mockito.only()).save(authenticationMock);
	}

	@Test
	public void testAcessoNegado() {
		assertNotNull(homeController.acessoNegado());
	}

}
