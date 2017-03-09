package br.mp.mpt.prt8.severino.config;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import br.mp.mpt.prt8.severino.utils.Constantes;
import br.mp.mpt.prt8.severino.viewhelpers.CesMappingExceptionResolver;

/**
 * Configuração de acesso ao view.
 * 
 * @author sergio.eoliveira
 *
 */
@Configuration
@EnableWebMvc
@ImportResource("classpath:deploy-info.xml")
public class CesViewConfiguration extends WebMvcConfigurerAdapter {
	@Autowired
	private EntityManagerFactory emf;

	@Bean
	public ViewResolver configureViewResolver() {
		InternalResourceViewResolver viewResolve = new InternalResourceViewResolver();
		viewResolve.setPrefix("/WEB-INF/templates/");
		viewResolve.setSuffix(".jsp");
		viewResolve.setViewClass(JstlView.class);
		return viewResolve;
	}

	@Bean
	@Primary
	public SmartValidator validator() {
		return new LocalValidatorFactoryBean();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		registry.addResourceHandler("/img/**").addResourceLocations("/img/");
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(Constantes.DEFAULT_LOCALE, Constantes.DEFAULT_TIMEZONE);
	}

	@PostConstruct
	public void initLocaleSettings() {
		Locale.setDefault(Constantes.DEFAULT_LOCALE);
		TimeZone.setDefault(Constantes.DEFAULT_TIMEZONE);
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("messages");
		return source;
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(new CesMappingExceptionResolver());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		OpenEntityManagerInViewInterceptor interceptor = new OpenEntityManagerInViewInterceptor();
		interceptor.setEntityManagerFactory(emf);
		registry.addWebRequestInterceptor(interceptor);
	}
}
