package br.mp.mpt.prt8.severino.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

/**
 * Configurações de acesso ao banco e jpa.
 * 
 * @author sergio.eoliveira
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = "br.mp.mpt.prt8.severino.dao")
@EnableTransactionManagement
@ComponentScan(basePackages = "br.mp.mpt.prt8.severino")
public class CesDbConfiguration {

	private static final String JPA_PU_NAME = "ces";

	private DataSource dataSource() {
		JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
		jndiDataSourceLookup.setResourceRef(true);
		return jndiDataSourceLookup.getDataSource("java:comp/env/jdbc/ces");
	}

	@Bean
	public AbstractPlatformTransactionManager transactionManager() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setPersistenceUnitName(JPA_PU_NAME);
		return jpaTransactionManager;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setDataSource(dataSource());
		bean.setPersistenceUnitName(JPA_PU_NAME);
		return bean;
	}

}
