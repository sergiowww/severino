package br.mp.mpt.prt8.severino.mediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;

import br.mp.mpt.prt8.severino.dao.ViagemRepository;
import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.entity.Viagem;
import br.mp.mpt.prt8.severino.mediator.ViagemMediatorMockTest.MockContextClasses;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Teste do mediator de viagem com opções de mock para cobertura de testes.
 * 
 * @author sergio.eoliveira
 *
 */
@ContextConfiguration(classes = MockContextClasses.class)
public class ViagemMediatorMockTest extends AbstractSeverinoTests {
	private static final String EXCEPTION_MESSAGE = "essa validação foi \"mockada\" com sucesso!";

	@Autowired
	private ViagemMediator viagemMediator;

	@Autowired
	@Qualifier("viagemRepositoryMock")
	private ViagemRepository viagemRepositoryMock;

	@Autowired
	private ValidatorServiceBean<Viagem> validatorServiceBeanMock;

	@Test(expected = NegocioException.class)
	@SuppressWarnings("unchecked")
	public void testRealizarBaixaComFalhaValidacao() throws Exception {
		ConstraintViolation<Viagem> constraintViolation = Mockito.mock(ConstraintViolation.class);
		Set<ConstraintViolation<Viagem>> violacoes = new HashSet<>(Arrays.asList(constraintViolation));
		Mockito.when(validatorServiceBeanMock.getValidationConstraints(Mockito.any(), Mockito.any())).thenReturn(violacoes);
		Motorista motorista = new Motorista();
		Date dataHoraRetorno = new Date();
		Viagem viagem = new Viagem();
		Mockito.when(viagemRepositoryMock.findByControleRetornoIsNullAndMotorista(Mockito.eq(motorista))).thenReturn(viagem);
		Mockito.when(validatorServiceBeanMock.constraintsToMessage(Mockito.eq(violacoes))).thenReturn(EXCEPTION_MESSAGE);
		try {
			viagemMediator.realizarBaixa(motorista, dataHoraRetorno);
			fail("Não devia ter chegado aqui");
		} catch (NegocioException e) {
			assertEquals(EXCEPTION_MESSAGE, e.getMessage());
			throw e;
		}

	}

	public static class MockContextClasses {
		@Bean
		@Primary
		@SuppressWarnings("unchecked")
		public ValidatorServiceBean<Viagem> validatorBean() {
			return (ValidatorServiceBean<Viagem>) Mockito.mock(ValidatorServiceBean.class);
		}

		@Bean("viagemRepositoryMock")
		@Primary
		public ViagemRepository viagemRepository() {
			return Mockito.mock(ViagemRepository.class);
		}
	}

}
