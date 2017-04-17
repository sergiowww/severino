package br.mp.mpt.prt8.severino.controller;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.mp.mpt.prt8.severino.entity.ControleMotorista;
import br.mp.mpt.prt8.severino.mediator.ControleMotoristaMediator;
import br.mp.mpt.prt8.severino.mediator.MotoristaMediator;

/**
 * Teste do controle do motorista.
 * 
 * @author sergio.eoliveira
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ControleMotoristaControllerTest {
	@Mock
	private MotoristaMediator motoristaMediatorMock;

	@Mock
	private ControleMotoristaMediator controleMotoristaMediatorMock;

	@InjectMocks
	private ControleMotoristaController controleMotoristaController;

	@Test
	public void testGetMediatorBean() {
		assertSame(controleMotoristaMediatorMock, controleMotoristaController.getMediatorBean());
	}

	@Test
	public void testAddCollections() throws Exception {
		ModelAndView mav = Mockito.mock(ModelAndView.class);
		controleMotoristaController.addCollections(mav, null);

		Mockito.verify(motoristaMediatorMock, Mockito.only()).findAllMotoristasTecnicos();
	}

	@Test
	public void testSalvar() throws Exception {
		ControleMotorista entity = new ControleMotorista();
		BindingResult bindingResultMock = Mockito.mock(BindingResult.class);
		RedirectAttributes redirectAttributesMock = Mockito.mock(RedirectAttributes.class);
		controleMotoristaController.salvar(entity, bindingResultMock, redirectAttributesMock);
		Mockito.verify(controleMotoristaMediatorMock, Mockito.only()).save(entity);
	}

}
