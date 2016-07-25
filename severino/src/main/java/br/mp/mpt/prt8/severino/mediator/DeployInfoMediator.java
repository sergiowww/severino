package br.mp.mpt.prt8.severino.mediator;

import java.util.Calendar;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Mediator para recuperar informações sobre o deploy da aplicação.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class DeployInfoMediator {
	private static final Logger LOG = LoggerFactory.getLogger(DeployInfoMediator.class);

	@Autowired
	@Qualifier("manifest")
	private Manifest manifest;

	private Date dataHoraPublicacao;

	/**
	 * Retorna a data e a hora da publicação do sistema.
	 * 
	 * @return
	 */
	public Date getDataHoraPublicacao() {
		if (dataHoraPublicacao == null) {
			try {
				Attributes mainAttributes = manifest.getMainAttributes();
				Set<Entry<Object, Object>> entradas = mainAttributes.entrySet();
				for (Entry<Object, Object> entry : entradas) {
					LOG.info(String.format("Manifest Entry: %s => %s", entry.getKey(), entry.getValue()));
				}
				String timestampString = Objects.toString(mainAttributes.getValue("Implementation-Build"),
						"2016-07-11T10:08:00Z");

				Calendar calendar = DatatypeConverter.parseDateTime(timestampString);
				calendar.setTimeZone(TimeZone.getDefault());
				dataHoraPublicacao = calendar.getTime();
			} catch (IllegalArgumentException e) {
				dataHoraPublicacao = Calendar.getInstance().getTime();
			}
		}
		return dataHoraPublicacao;

	}
}
