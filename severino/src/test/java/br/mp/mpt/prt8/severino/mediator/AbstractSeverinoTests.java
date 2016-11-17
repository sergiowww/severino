package br.mp.mpt.prt8.severino.mediator;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import br.mp.mpt.prt8.severino.config.CesDbTestConfiguration;
import br.mp.mpt.prt8.severino.mediator.carga.ICarga;

/**
 * Classe base de teste.
 * 
 * @author sergio.eoliveira
 *
 */
@ContextConfiguration(classes = CesDbTestConfiguration.class)
@WebAppConfiguration
public abstract class AbstractSeverinoTests extends AbstractTransactionalJUnit4SpringContextTests {

	@PersistenceContext
	protected EntityManager entityManager;

	@Before
	public void setUpCarga() {
		Map<String, ICarga> beansMap = applicationContext.getBeansOfType(ICarga.class);
		for (ICarga carga : beansMap.values()) {
			carga.carga();
		}
	}

}
