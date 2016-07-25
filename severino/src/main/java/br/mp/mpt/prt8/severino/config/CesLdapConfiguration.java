package br.mp.mpt.prt8.severino.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * Configurações do LDAP.
 * 
 * @author sergio.eoliveira
 *
 */
@ImportResource("classpath:ldap.xml")
@Configuration
public class CesLdapConfiguration {
	@Autowired
	@Qualifier("ldapContextSource")
	private LdapContextSource ldapContextSource;

	@Bean
	public LdapTemplate ldapTemplate() {
		return new LdapTemplate(ldapContextSource);
	}
}
