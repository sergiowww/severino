package br.mp.mpt.prt8.severino.controller;

import static org.junit.Assert.assertNotNull;
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

import br.mp.mpt.prt8.severino.entity.Local;
import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.mediator.LocalMediator;
import br.mp.mpt.prt8.severino.mediator.MotoristaMediator;
import br.mp.mpt.prt8.severino.mediator.UsuarioHolder;

/**
 * Teste da classe de motorista.
 * 
 * @author sergio.eoliveira
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MotoristaControllerTest {

	@Mock
	private MotoristaMediator motoristaMediatorMock;

	@Mock
	private UsuarioHolder usuarioHolder;

	@Mock
	private LocalMediator localMediatorMock;

	@InjectMocks
	private MotoristaController motoristaController;

	@Test
	public void testGetMediatorBean() {
		assertSame(motoristaMediatorMock, motoristaController.getMediatorBean());
	}

	@Test
	public void testAddCollections() throws Exception {
		ModelAndView mav = Mockito.mock(ModelAndView.class);
		motoristaController.addCollections(mav, null);

		Mockito.verify(localMediatorMock, Mockito.only()).findAll();
	}

	@Test
	public void testSalvar() throws Exception {
		Motorista entity = new Motorista();
		BindingResult bindingResultMock = Mockito.mock(BindingResult.class);
		RedirectAttributes redirectAttributesMock = Mockito.mock(RedirectAttributes.class);
		motoristaController.salvar(entity, bindingResultMock, redirectAttributesMock);
		Mockito.verify(motoristaMediatorMock, Mockito.only()).save(entity);
	}

	@Test
	public void testGetNewEntity() throws Exception {
		Local local = new Local();
		Mockito.when(usuarioHolder.getLocal()).thenReturn(local);
		Motorista newEntity = motoristaController.getNewEntity();
		assertNotNull(newEntity);
		assertSame(local, newEntity.getLocal());
	}
}
