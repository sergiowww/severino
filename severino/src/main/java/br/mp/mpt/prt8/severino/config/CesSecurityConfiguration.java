package br.mp.mpt.prt8.severino.config;

import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
		//@formatter:off
		auth.ldapAuthentication()
			.groupSearchBase(ldapProperties.getProperty("groupSearchBase"))
			.groupRoleAttribute(ldapProperties.getProperty("groupRoleAttribute"))
			.groupSearchFilter(ldapProperties.getProperty("groupSearchFilter"))
			.rolePrefix("")
			.userSearchBase(ldapProperties.getProperty("userSearchBase"))
			.userSearchFilter(ldapProperties.getProperty("userSearchFilter"))
			.contextSource(this.ldapContextSource)
		;
		//@formatter:on
	}

}
