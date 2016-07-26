package br.mp.mpt.prt8.severino.viewhelpers;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * Mapeamento de exceções esperadas.
 * 
 * @author sergio.eoliveira
 *
 */
public class CesMappingExceptionResolver extends SimpleMappingExceptionResolver {
	private static final String ERRO_403 = "forward:/403";
	private static final String ERRO_500 = "erro/500";
	private static final Logger LOG = LoggerFactory.getLogger(CesMappingExceptionResolver.class);
	private static final String CONTRAINT_VIOLATION_ERROR_VIEW = "erro/constraint-violation";

	public CesMappingExceptionResolver() {
		setDefaultErrorView(ERRO_500);
		addStatusCode(ERRO_403, 403);
		setWarnLogCategory("error");
		Properties mappings = new Properties();
		mappings.put(AccessDeniedException.class.getName(), ERRO_403);
		setExceptionMappings(mappings);
	}

	@Override
	protected void logException(Exception ex, HttpServletRequest request) {
		LOG.error(ex.getMessage(), ex);
	}

	@Override
	protected String determineViewName(Exception ex, HttpServletRequest request) {

		Throwable cause = ex;
		do {

			if (cause instanceof ConstraintViolationException) {
				return CONTRAINT_VIOLATION_ERROR_VIEW;
			}
			cause = cause.getCause();
		} while (cause != null && cause.getCause() != cause);

		return super.determineViewName(ex, request);
	}

}
