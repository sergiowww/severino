package br.mp.mpt.prt8.severino.mediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.mp.mpt.prt8.severino.dao.ViagemRepository;
import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.entity.Viagem;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Teste do mediator de viagem com opções de mock para cobertura de testes.
 * 
 * @author sergio.eoliveira
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ViagemMediatorMockTest {
	private static final String EXCEPTION_MESSAGE = "essa validação foi \"mockada\" com sucesso!";

	@InjectMocks
	private ViagemMediator viagemMediator;

	@Mock
	private ViagemRepository viagemRepositoryMock;

	@Mock
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

}
