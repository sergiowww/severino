package br.mp.mpt.prt8.severino;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mockito.internal.util.io.IOUtil;

/**
 * Utilitários de teste.
 * 
 * @author sergio.eoliveira
 *
 */
public class TestUtil {
	private TestUtil() {
		super();
	}

	public static void executarScript(EntityManager entityManager, String sqlFile) {
		Collection<String> linhas = IOUtil.readLines(TestUtil.class.getResourceAsStream("/" + sqlFile));
		for (String sql : linhas) {
			Query query = entityManager.createNativeQuery(sql);
			query.executeUpdate();

		}
		entityManager.flush();
		entityManager.clear();
	}
}
