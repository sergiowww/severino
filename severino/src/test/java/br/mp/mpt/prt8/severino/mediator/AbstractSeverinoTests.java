package br.mp.mpt.prt8.severino.mediator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Classe base de teste.
 * 
 * @author sergio.eoliveira
 *
 */
@ContextConfiguration(classes = { CesDbTestConfiguration.class })
@WebAppConfiguration
public abstract class AbstractSeverinoTests extends AbstractTransactionalJUnit4SpringContextTests {

	@PersistenceContext
	protected EntityManager entityManager;

}
