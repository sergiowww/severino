package br.mp.mpt.prt8.severino.config;

import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;

/**
 * Configurações de segurança e acesso.
 * 
 * @author sergio.eoliveira
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CesSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String ROLE_PREFIX = "";

	@Autowired
	@Qualifier("ldapContextSource")
	private LdapContextSource ldapContextSource;

	@Resource(name = "ldapProperties")
	private Properties ldapProperties;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http.authorizeRequests()
			.antMatchers("/css/**", "/js/**", "/img/**", "/resources/**")
			.permitAll()
			.anyRequest()
			.authenticated()
		.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
			.defaultSuccessUrl("/", true)
			.failureUrl("/login?error")
			.permitAll()
		.and()
			.logout().permitAll()
		.and()
			.exceptionHandling().accessDeniedPage("/403")
		.and()
			.csrf().disable();
		
		//@formatter:on
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		DefaultLdapAuthoritiesPopulator defaultAuthoritiesPopulator = new DefaultLdapAuthoritiesPopulator(this.ldapContextSource, ldapProperties.getProperty("groupSearchBase"));
		defaultAuthoritiesPopulator.setGroupRoleAttribute(ldapProperties.getProperty("groupRoleAttribute"));
		defaultAuthoritiesPopulator.setGroupSearchFilter(ldapProperties.getProperty("groupSearchFilter"));
		defaultAuthoritiesPopulator.setRolePrefix(ROLE_PREFIX);
		defaultAuthoritiesPopulator.setSearchSubtree(true);
		//@formatter:off
		auth.ldapAuthentication()
			.rolePrefix(ROLE_PREFIX)
			.userSearchBase(ldapProperties.getProperty("userSearchBase"))
			.userSearchFilter(ldapProperties.getProperty("userSearchFilter"))
			.ldapAuthoritiesPopulator(defaultAuthoritiesPopulator)
			.contextSource(this.ldapContextSource)
		;
		//@formatter:on
	}

	@Bean
	public GrantedAuthorityDefaults grantedAuthorityDefaults() {
		return new GrantedAuthorityDefaults(ROLE_PREFIX);
	}

}
