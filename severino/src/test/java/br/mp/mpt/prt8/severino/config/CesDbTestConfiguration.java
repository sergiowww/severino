package br.mp.mpt.prt8.severino.config;

import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.dialect.HSQLDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

public class CesDbTestConfiguration extends CesDbConfiguration {

	@Bean
	@Override
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = super.entityManagerFactory();
		Map<String, Object> persistenceProperties = entityManagerFactory.getJpaPropertyMap();
		persistenceProperties.put("hibernate.dialect", HSQLDialect.class.getName());
		persistenceProperties.put("hibernate.show_sql", "true");
		persistenceProperties.put("javax.persistence.schema-generation.database.action", "create");
		return entityManagerFactory;
	}

	@Bean
	@Override
	protected DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder.setType(EmbeddedDatabaseType.HSQL).build();
	}
}
