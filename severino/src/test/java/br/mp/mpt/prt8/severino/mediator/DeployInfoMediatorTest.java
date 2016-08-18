package br.mp.mpt.prt8.severino.mediator;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.mp.mpt.prt8.severino.utils.Constantes;

/**
 * Teste do deploy info.
 * 
 * @author sergio.eoliveira
 *
 */
public class DeployInfoMediatorTest extends AbstractSeverinoTests {

	@Autowired
	private DeployInfoMediator deployInfoMediator;

	@Test
	public void testGetDataHoraPublicacao() {
		Date data = deployInfoMediator.getDataHoraPublicacao();
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Constantes.DEFAULT_LOCALE);
		assertEquals("11/07/2016", dateFormat.format(data));
		data = deployInfoMediator.getDataHoraPublicacao();
		assertEquals("11/07/2016", dateFormat.format(data));
	}

}
