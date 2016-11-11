package br.mp.mpt.prt8.severino.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Teste equals e hashcode genérico.
 * 
 * @author sergio.eoliveira
 *
 */
public class AbstractEntityTest {

	private static class SubControleMot extends ControleMotorista {

		private static final long serialVersionUID = -6695960501179600915L;

	}

	@Test
	public void testEquality() {

		SubControleMot obj1 = new SubControleMot();
		obj1.setId(1);

		ControleMotorista obj3 = new ControleMotorista();
		obj3.setId(1);

		Motorista obj2 = new Motorista();
		obj2.setId(1);

		Motorista obj4 = new Motorista();
		obj4.setId(2);

		assertTrue(obj1.equals(obj3));
		assertEquals(obj1.hashCode(), obj3.hashCode());

		assertTrue(obj1.equals(obj1));
		assertEquals(obj1.hashCode(), obj1.hashCode());

		assertTrue(obj3.equals(obj1));
		assertEquals(obj3.hashCode(), obj1.hashCode());

		assertFalse(obj2.equals(obj3));
		assertNotEquals(obj2.hashCode(), obj3.hashCode());

		assertFalse(obj3.equals(obj2));

		assertFalse(obj4.equals(obj2));
		assertNotEquals(obj4.hashCode(), obj2.hashCode());

		Motorista motoristaTransient1 = new Motorista();

		assertFalse(motoristaTransient1.equals(obj4));
		assertNotEquals(motoristaTransient1.hashCode(), obj4.hashCode());

		Motorista motoristaTransient2 = new Motorista();
		Motorista motoristaTransient3 = new Motorista();
		assertTrue(motoristaTransient2.equals(motoristaTransient3));

		assertEquals(motoristaTransient1.hashCode(), motoristaTransient2.hashCode());

	}

}
