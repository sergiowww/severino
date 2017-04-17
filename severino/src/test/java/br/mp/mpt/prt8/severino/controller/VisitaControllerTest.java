package br.mp.mpt.prt8.severino.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.mp.mpt.prt8.severino.entity.Endereco;
import br.mp.mpt.prt8.severino.entity.Visita;
import br.mp.mpt.prt8.severino.entity.Visitante;
import br.mp.mpt.prt8.severino.mediator.SetorMediator;
import br.mp.mpt.prt8.severino.mediator.VisitaMediator;
import br.mp.mpt.prt8.severino.validators.CadastrarVisita;

/**
 * Teste para o controlador da visita.
 * 
 * @author sergio.eoliveira
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VisitaControllerTest {
	@InjectMocks
	private VisitaController visitaController;

	@Mock
	private VisitaMediator visitaMediatorMock;

	@Mock
	private SetorMediator setorMediatorMock;

	@Mock
	private SmartValidator smartValidatorMock;

	@Mock
	private RedirectAttributes redirectAttributesMock;

	@Mock
	private BindingResult bindingResultMock;

	@Test
	public void testAddCollectionsModelAndViewVisita() {
		Visita visita = new Visita();
		ModelAndView mav = Mockito.mock(ModelAndView.class);
		visitaController.addCollections(mav, visita);
		Mockito.verify(setorMediatorMock, Mockito.only()).findAll();
		Mockito.verify(visitaMediatorMock, Mockito.only()).findVisitasSemBaixa();
	}

	@Test
	public void testGetMediatorBean() {
		assertSame(visitaController.getMediatorBean(), visitaMediatorMock);
	}

	@Test
	public void testGetNewEntity() {
		Visita visita = visitaController.getNewEntity();
		assertNotNull(visita.getVisitante());
		assertNotNull(visita.getVisitante().getTokenFoto());
	}

	@Test
	public void testRedirectAposGravarVisita() {
		Visita visita = new Visita();
		visita.setId(324);
		assertEquals("redirect:/visita/324", visitaController.redirectAposGravar(visita));
	}

	@Test
	public void testDetalharNull() {
		Optional<Integer> id = Optional.of(45);
		Mockito.when(visitaMediatorMock.findOne(id)).thenReturn(null);
		assertNotNull(visitaController.detalhar(id));
		Mockito.verify(visitaMediatorMock, Mockito.atLeastOnce()).findOne(id);
		Mockito.verify(visitaMediatorMock, Mockito.never()).findVisitasSemBaixa();
	}

	@Test
	public void testDetalhar() {
		Optional<Integer> id = Optional.of(45);
		Visita visita = new Visita();
		Mockito.when(visitaMediatorMock.findOne(id)).thenReturn(visita);
		assertNotNull(visitaController.detalhar(id));
		Mockito.verify(visitaMediatorMock, Mockito.atLeastOnce()).findOne(id);
		Mockito.verify(visitaMediatorMock, Mockito.atLeastOnce()).findVisitasSemBaixa();
	}

	@Test
	public void testSalvarVisitaBindingResultRedirectAttributes() {
		Visita visita = new Visita();
		visita.setVisitante(new Visitante());
		visitaController.salvar(visita, bindingResultMock, redirectAttributesMock);
		Mockito.verify(smartValidatorMock, Mockito.only()).validate(visita, bindingResultMock, CadastrarVisita.class);
		Mockito.verify(visitaMediatorMock, Mockito.only()).save(visita);
	}

	@Test
	public void testSalvarComEnderecoNotNull() throws Exception {
		Visita visita = new Visita();
		visita.setVisitante(new Visitante());
		Endereco endereco = new Endereco();
		endereco.setBairro("bairro");
		visita.getVisitante().setEndereco(endereco);

		visitaController.salvar(visita, bindingResultMock, redirectAttributesMock);
		Mockito.verify(smartValidatorMock, Mockito.only()).validate(visita, bindingResultMock, CadastrarVisita.class);
		Mockito.verify(visitaMediatorMock, Mockito.only()).save(visita);
	}

	@Test
	public void testSalvarComEndereco() throws Exception {
		Visita visita = new Visita();
		visita.setVisitante(new Visitante());
		visita.getVisitante().setEndereco(new Endereco());
		visitaController.salvar(visita, bindingResultMock, redirectAttributesMock);
		Mockito.verify(smartValidatorMock, Mockito.only()).validate(visita, bindingResultMock, CadastrarVisita.class);
		Mockito.verify(visitaMediatorMock, Mockito.only()).save(visita);
	}

}
