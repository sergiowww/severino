package br.mp.mpt.prt8.severino.mediator;

import java.util.Arrays;
import java.util.Map;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import br.mp.mpt.prt8.severino.config.CesDbTestConfiguration;
import br.mp.mpt.prt8.severino.mediator.carga.ICarga;
import br.mp.mpt.prt8.severino.utils.Constantes;

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

	@BeforeClass
	public static void configureTimeZone() {
		TimeZone.setDefault(Constantes.DEFAULT_TIMEZONE);
	}

	@Before
	public void setUpCarga() {
		Map<String, ICarga> beansMap = applicationContext.getBeansOfType(ICarga.class);
		beansMap.values().forEach(ICarga::carga);
	}

	/**
	 * Apagar os dados das tabelas.
	 * 
	 * @param entityClasses
	 */
	protected void deleteFromTables(Class<?>... entityClasses) {
		String[] tables = Arrays.stream(entityClasses).map(cls -> cls.getAnnotation(Table.class).name()).toArray(String[]::new);
		deleteFromTables(tables);
	}

}
