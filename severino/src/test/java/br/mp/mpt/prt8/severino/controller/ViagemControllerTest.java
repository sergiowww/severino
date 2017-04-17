package br.mp.mpt.prt8.severino.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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

import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.entity.Passageiro;
import br.mp.mpt.prt8.severino.entity.Viagem;
import br.mp.mpt.prt8.severino.mediator.ControleMotoristaMediator;
import br.mp.mpt.prt8.severino.mediator.VeiculoMediator;
import br.mp.mpt.prt8.severino.mediator.ViagemMediator;
import br.mp.mpt.prt8.severino.utils.NegocioException;
import br.mp.mpt.prt8.severino.validators.CadastrarVeiculo;
import br.mp.mpt.prt8.severino.validators.CadastrarViagem;

/**
 * Teste do controlador de viagem.
 * 
 * @author sergio.eoliveira
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ViagemControllerTest {
	@Mock
	private ViagemMediator viagemMediatorMock;

	@Mock
	private VeiculoMediator veiculoMediatorMock;

	@Mock
	private SmartValidator smartValidatorMock;

	@Mock
	private ControleMotoristaMediator controleMotoristaMediatorMock;

	@InjectMocks
	private ViagemController viagemController;

	@Mock
	private RedirectAttributes redirectAttributesMock;

	@Mock
	private BindingResult bindingResultMock;

	@Test
	public void testGetMediatorBean() {
		assertSame(viagemController.getMediatorBean(), viagemMediatorMock);
	}

	@Test
	public void testAddCollectionsModelAndViewViagem() {
		ModelAndView mav = Mockito.mock(ModelAndView.class);
		Viagem viagem = new Viagem();
		viagemController.addCollections(mav, viagem);

		Mockito.verify(controleMotoristaMediatorMock).findMotoristasDisponiveis(null);
		Mockito.verify(controleMotoristaMediatorMock).findDisponiveis();
		Mockito.verify(veiculoMediatorMock).findViaturas();
		Mockito.verify(viagemMediatorMock).findViagensSemBaixa();
	}

	@Test
	public void testAddCollectionsComMotorista() {
		ModelAndView mav = Mockito.mock(ModelAndView.class);
		Viagem viagem = new Viagem();
		viagem.setMotorista(new Motorista(45));
		viagemController.addCollections(mav, viagem);

		Mockito.verify(controleMotoristaMediatorMock).findMotoristasDisponiveis(45);
		Mockito.verify(controleMotoristaMediatorMock).findDisponiveis();
		Mockito.verify(veiculoMediatorMock).findViaturas();
		Mockito.verify(viagemMediatorMock).findViagensSemBaixa();
	}

	@Test
	public void testRedirectAposGravarViagem() {
		Viagem viagem = new Viagem();
		viagem.setId(48);
		assertEquals("redirect:/viagem/48", viagemController.redirectAposGravar(viagem));
	}

	@Test
	public void testSalvarViagemBindingResultRedirectAttributes() {
		Viagem viagem = new Viagem();
		viagemController.salvar(viagem, bindingResultMock, redirectAttributesMock);
		Mockito.verify(viagemMediatorMock, Mockito.only()).save(viagem);
		Mockito.verify(smartValidatorMock, Mockito.only()).validate(viagem, bindingResultMock, CadastrarViagem.class);
	}

	@Test
	public void testSalvarViagemGravarVeiculoTrue() {
		Viagem viagem = new Viagem();
		viagem.setGravarVeiculo(true);
		viagem.addPassageiro(new Passageiro());
		viagemController.salvar(viagem, bindingResultMock, redirectAttributesMock);
		Mockito.verify(viagemMediatorMock, Mockito.only()).save(viagem);
		Mockito.verify(smartValidatorMock, Mockito.only()).validate(viagem, bindingResultMock, CadastrarViagem.class, CadastrarVeiculo.class);
	}

	@Test
	public void testAtualizarDisponibilidadeFalhaValidacao() throws Exception {
		Mockito.when(bindingResultMock.hasErrors()).thenReturn(true);
		ViagemController.DisponibilidadeMotorista disponibilidadeMotorista = new ViagemController.DisponibilidadeMotorista();
		disponibilidadeMotorista.setIdMotorista(49);
		disponibilidadeMotorista.setIdViagem(488);
		disponibilidadeMotorista.setHorario(new Date());
		assertEquals("redirect:/viagem/488", viagemController.atualizarDisponibilidade(disponibilidadeMotorista, redirectAttributesMock, bindingResultMock));
		Mockito.verify(controleMotoristaMediatorMock, Mockito.never()).atualizarDisponibilidade(49, null);
	}

	@Test
	public void testAtualizarDisponibilidade() {
		Mockito.when(bindingResultMock.hasErrors()).thenReturn(false);
		ViagemController.DisponibilidadeMotorista disponibilidadeMotorista = new ViagemController.DisponibilidadeMotorista();
		disponibilidadeMotorista.setIdMotorista(49);
		disponibilidadeMotorista.setIdViagem(488);
		assertEquals("redirect:/viagem/488", viagemController.atualizarDisponibilidade(disponibilidadeMotorista, redirectAttributesMock, bindingResultMock));
		Mockito.verify(controleMotoristaMediatorMock, Mockito.only()).atualizarDisponibilidade(49, null);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAtualizarDisponibilidadeConstraintException() {
		Mockito.when(bindingResultMock.hasErrors()).thenReturn(false);
		Set<ConstraintViolation<?>> violation = new HashSet<>();
		ConstraintViolation<String> constraintViolation = Mockito.mock(ConstraintViolation.class);
		String mensagemErro = "Mensagem de erro gerada";
		Mockito.when(constraintViolation.getMessage()).thenReturn(mensagemErro);
		violation.add(constraintViolation);
		Mockito.doThrow(new ConstraintViolationException(violation)).when(controleMotoristaMediatorMock).atualizarDisponibilidade(49, null);
		ViagemController.DisponibilidadeMotorista disponibilidadeMotorista = new ViagemController.DisponibilidadeMotorista();
		disponibilidadeMotorista.setIdMotorista(49);
		viagemController.atualizarDisponibilidade(disponibilidadeMotorista, redirectAttributesMock, bindingResultMock);
		Mockito.verify(controleMotoristaMediatorMock, Mockito.only()).atualizarDisponibilidade(49, null);
		Mockito.verify(redirectAttributesMock, Mockito.only()).addFlashAttribute(AbstractFullCrudController.KEY_ERROR, mensagemErro);
	}

	@Test
	public void testAtualizarDisponibilidadeNegocioException() {
		Mockito.when(bindingResultMock.hasErrors()).thenReturn(false);
		String mensagemErro = "Mensagem de erro de negocio";
		Mockito.doThrow(new NegocioException(mensagemErro)).when(controleMotoristaMediatorMock).atualizarDisponibilidade(49, null);
		ViagemController.DisponibilidadeMotorista disponibilidadeMotorista = new ViagemController.DisponibilidadeMotorista();
		disponibilidadeMotorista.setIdMotorista(49);
		viagemController.atualizarDisponibilidade(disponibilidadeMotorista, redirectAttributesMock, bindingResultMock);
		Mockito.verify(controleMotoristaMediatorMock, Mockito.only()).atualizarDisponibilidade(49, null);
		Mockito.verify(redirectAttributesMock, Mockito.only()).addFlashAttribute(AbstractFullCrudController.KEY_ERROR, mensagemErro);
	}

}
