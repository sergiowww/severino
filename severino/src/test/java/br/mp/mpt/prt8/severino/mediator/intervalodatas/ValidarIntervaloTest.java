package br.mp.mpt.prt8.severino.mediator.intervalodatas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.Test;

import br.mp.mpt.prt8.severino.entity.Visita;
import br.mp.mpt.prt8.severino.utils.DateUtils;

/**
 * Validar intervalos de datas.
 * 
 * @author sergio.eoliveira
 *
 */
public class ValidarIntervaloTest {

	@Test
	public void testIsDataEntradaValida() {
		Visita visita = new Visita();
		assertFalse(ValidarIntervalo.isDataEntradaValida(visita));
		visita.setId(4);
		assertTrue(ValidarIntervalo.isDataEntradaValida(visita));
		visita.setEntrada(new Date());
		assertFalse(ValidarIntervalo.isDataEntradaValida(visita));
	}

	@Test
	public void testIsIntervaloEntradaSaidaValido() throws Exception {
		Visita visita = new Visita();
		visita.setEntrada(new Date());
		assertFalse(ValidarIntervalo.isIntervaloEntradaSaidaValido(visita));

		visita.setEntrada(DateUtils.toDate(LocalDateTime.now().minusHours(1)));
		visita.setSaida(DateUtils.toDate(LocalDateTime.now()));
		assertFalse(ValidarIntervalo.isIntervaloEntradaSaidaValido(visita));

		visita.setEntrada(DateUtils.toDate(LocalDateTime.now()));
		visita.setSaida(DateUtils.toDate(LocalDateTime.now().minusHours(1)));
		assertTrue(ValidarIntervalo.isIntervaloEntradaSaidaValido(visita));

		visita.setEntrada(null);
		visita.setSaida(null);
		assertFalse(ValidarIntervalo.isIntervaloEntradaSaidaValido(visita));

		visita.setEntrada(null);
		visita.setSaida(DateUtils.toDate(LocalDateTime.now()));
		assertFalse(ValidarIntervalo.isIntervaloEntradaSaidaValido(visita));

	}

}
