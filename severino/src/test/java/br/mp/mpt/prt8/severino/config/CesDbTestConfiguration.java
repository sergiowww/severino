package br.mp.mpt.prt8.severino.config;

import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.dialect.H2Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

public class CesDbTestConfiguration extends CesDbConfiguration {

	private DataSource dataSource;

	@Bean
	@Override
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = super.entityManagerFactory();
		Map<String, Object> persistenceProperties = entityManagerFactory.getJpaPropertyMap();
		persistenceProperties.put("hibernate.dialect", H2Dialect.class.getName());
		persistenceProperties.put("hibernate.show_sql", "true");
		persistenceProperties.put("javax.persistence.schema-generation.database.action", "create");
		return entityManagerFactory;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}

	@Bean
	@Override
	protected DataSource dataSource() {
		if (dataSource == null) {
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			dataSource = builder.setType(EmbeddedDatabaseType.H2).build();
		}
		return dataSource;
	}
}
