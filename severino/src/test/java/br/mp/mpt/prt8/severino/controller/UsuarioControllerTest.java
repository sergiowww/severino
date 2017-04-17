package br.mp.mpt.prt8.severino.controller;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.mp.mpt.prt8.severino.mediator.UsuarioMediator;

/**
 * Teste do controlador do usuário.
 * 
 * @author sergio.eoliveira
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class UsuarioControllerTest {
	@Mock
	private UsuarioMediator usuarioMediatorMock;

	@InjectMocks
	private UsuarioController usuarioController;

	@Test
	public void testGetMediatorBean() {
		assertSame(usuarioController.getMediatorBean(), usuarioMediatorMock);
	}

}
