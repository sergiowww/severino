package br.mp.mpt.prt8.severino.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.validation.groups.Default;

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

import br.mp.mpt.prt8.severino.entity.AcessoGaragem;
import br.mp.mpt.prt8.severino.entity.Visita;
import br.mp.mpt.prt8.severino.mediator.AcessoGaragemMediator;
import br.mp.mpt.prt8.severino.mediator.VeiculoMediator;
import br.mp.mpt.prt8.severino.mediator.VisitaMediator;
import br.mp.mpt.prt8.severino.validators.CadastrarVeiculo;
import br.mp.mpt.prt8.severino.validators.SelecionarVisita;

/**
 * Teste do controlador de acesso a garagem.
 * 
 * @author sergio.eoliveira
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AcessoGaragemControllerTest {

	@Mock
	private AcessoGaragemMediator acessoGaragemMediator;

	@Mock
	private VisitaMediator visitaMediator;

	@Mock
	private VeiculoMediator veiculoMediator;

	@Mock
	private SmartValidator smartValidator;

	@InjectMocks
	private AcessoGaragemController acessoGaragemController;

	@Test
	public void testMediatorBean() {
		assertNotNull(acessoGaragemController.getMediatorBean());
	}

	@Test
	public void testRedirectAposGravar() throws Exception {
		AcessoGaragem entity = new AcessoGaragem();
		entity.setId(45);
		assertEquals("redirect:/acessoGaragem/45", acessoGaragemController.redirectAposGravar(entity));
	}

	@Test
	public void testAddCollections() throws Exception {
		AcessoGaragem entity = new AcessoGaragem();
		ModelAndView mav = Mockito.mock(ModelAndView.class);
		acessoGaragemController.addCollections(mav, entity);

		Mockito.verify(veiculoMediator, Mockito.only()).findAllServidoresMembros();
		Mockito.verify(acessoGaragemMediator, Mockito.only()).findAllSemBaixa();
		Mockito.verify(visitaMediator, Mockito.only()).findAllRegistradasHoje(null);
	}

	@Test
	public void testSalvar() throws Exception {
		AcessoGaragem acessoGaragem = new AcessoGaragem();
		BindingResult bindingResultMock = Mockito.mock(BindingResult.class);
		RedirectAttributes redirectAttributesMock = Mockito.mock(RedirectAttributes.class);
		acessoGaragemController.salvar(acessoGaragem, bindingResultMock, redirectAttributesMock);
		Mockito.verify(smartValidator, Mockito.only()).validate(acessoGaragem, bindingResultMock);
		Mockito.verify(acessoGaragemMediator, Mockito.only()).save(acessoGaragem);
	}

	@Test
	public void testSalvarVisitante() throws Exception {
		AcessoGaragem acessoGaragem = new AcessoGaragem();
		acessoGaragem.setVisita(new Visita());
		BindingResult bindingResultMock = Mockito.mock(BindingResult.class);
		RedirectAttributes redirectAttributesMock = Mockito.mock(RedirectAttributes.class);
		acessoGaragemController.salvar(acessoGaragem, bindingResultMock, redirectAttributesMock);
		Mockito.verify(smartValidator, Mockito.only()).validate(acessoGaragem, bindingResultMock, Default.class, CadastrarVeiculo.class, SelecionarVisita.class);
		Mockito.verify(acessoGaragemMediator, Mockito.only()).save(acessoGaragem);
	}
}
