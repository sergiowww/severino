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

import br.mp.mpt.prt8.severino.entity.Veiculo;
import br.mp.mpt.prt8.severino.mediator.MotoristaMediator;
import br.mp.mpt.prt8.severino.mediator.VeiculoMediator;

/**
 * Teste do controlador de veículos.
 * 
 * @author sergio.eoliveira
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VeiculoControllerTest {
	@Mock
	private VeiculoMediator veiculoMediatorMock;

	@Mock
	private MotoristaMediator motoristaMediatorMock;

	@InjectMocks
	private VeiculoController veiculoController;

	@Test
	public void testGetMediatorBean() {
		assertSame(veiculoController.getMediatorBean(), veiculoMediatorMock);
	}

	@Test
	public void testAddCollectionsModelAndViewVeiculo() {
		ModelAndView mav = Mockito.mock(ModelAndView.class);
		Veiculo entity = new Veiculo();
		veiculoController.addCollections(mav, entity);
		Mockito.verify(motoristaMediatorMock, Mockito.only()).findAll();
	}

	@Test
	public void testSalvarVeiculoBindingResultRedirectAttributes() {
		Veiculo entity = new Veiculo();
		BindingResult bindingResultMock = Mockito.mock(BindingResult.class);
		RedirectAttributes redirectAttributesMock = Mockito.mock(RedirectAttributes.class);
		veiculoController.salvar(entity, bindingResultMock, redirectAttributesMock);
		Mockito.verify(veiculoMediatorMock, Mockito.only()).save(entity);
	}

	@Test
	public void testFindByPlaca() {
		veiculoController.findByPlaca("qualquer");
		String parametro = "qualquer";
		Mockito.verify(veiculoMediatorMock, Mockito.only()).findByPlaca(parametro);
	}

}
