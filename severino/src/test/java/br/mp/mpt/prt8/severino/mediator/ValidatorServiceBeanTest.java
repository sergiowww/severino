package br.mp.mpt.prt8.severino.mediator;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.mp.mpt.prt8.severino.entity.Viagem;
import br.mp.mpt.prt8.severino.utils.NegocioException;
import br.mp.mpt.prt8.severino.validators.CadastrarViagem;

/**
 * Teste do validador.
 * 
 * @author sergio.eoliveira
 *
 */
public class ValidatorServiceBeanTest extends AbstractSeverinoTests {
	@Autowired
	private ValidatorServiceBean<Viagem> validatorServiceBean;

	/**
	 * Provocar um erro de validação.
	 */
	@Test(expected = NegocioException.class)
	public void test() {
		Viagem viagem = new Viagem();
		validatorServiceBean.validate(viagem, CadastrarViagem.class);
	}

}
