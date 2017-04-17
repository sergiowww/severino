package br.mp.mpt.prt8.severino.controller;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.mp.mpt.prt8.severino.mediator.SetorMediator;

/**
 * Teste do setor.
 * 
 * @author sergio.eoliveira
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SetorControllerTest {
	@Mock
	private SetorMediator setorMediatorMock;

	@InjectMocks
	private SetorController setorController;

	@Test
	public void testGetMediatorBean() {
		assertSame(setorController.getMediatorBean(), setorMediatorMock);
	}

}
