package br.mp.mpt.prt8.severino.dao;

import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.ContainerCriteria;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Repository;

import br.mp.mpt.prt8.severino.mediator.UsuarioHolder;
import br.mp.mpt.prt8.severino.utils.Constantes;
import br.mp.mpt.prt8.severino.valueobject.PessoaLdap;

/**
 * Consultas no LDAP.
 * 
 * @author sergio.eoliveira
 *
 */
@Repository
public class LdapRepository {
	private static final Logger LOG = LoggerFactory.getLogger(LdapRepository.class);
	private static final String KEY_ATRIBUTOS_TRAZER = "attributesToShow";

	@Autowired
	private LdapTemplate ldapTemplate;

	@Autowired
	private UsuarioHolder usuarioHolder;

	@Resource(name = "ldapProperties")
	private Properties ldapProperties;

	public List<PessoaLdap> findByNomeLike(String parteNome) {
		LOG.info(String.format("Consultando LDAP por \"%s\" ...", parteNome));
		String ldapName = usuarioHolder.getLocal().getOrganizacao().getLdapName();
		String base = ldapName + ldapProperties.getProperty(Constantes.KEY_USER_SEARCH_BASE);
		String atributos = ldapProperties.getProperty(KEY_ATRIBUTOS_TRAZER);

		String[] attr = atributos.split(",");

		String cn = ldapProperties.getProperty("groupRoleAttribute");
		ContainerCriteria criteria = LdapQueryBuilder.query().base(base).attributes(attr).where("objectClass").is("person").and(cn).whitespaceWildcardsLike(parteNome);
		return ldapTemplate.search(criteria, new PessoaLdapAttributeMapper(attr));
	}

	/**
	 * Mapeador de atributos do LDAP.
	 * 
	 * @author sergio.eoliveira
	 *
	 */
	private final class PessoaLdapAttributeMapper implements AttributesMapper<PessoaLdap> {

		private final String[] atributos;

		public PessoaLdapAttributeMapper(String[] atributos) {
			super();
			this.atributos = atributos;

		}

		@Override
		public PessoaLdap mapFromAttributes(Attributes attributes) throws NamingException {
			PessoaLdap pessoaLdap = new PessoaLdap();
			BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(pessoaLdap);
			for (String atributo : atributos) {
				Attribute attribute = attributes.get(atributo);
				String value = "";
				if (attribute != null) {
					value = Objects.toString(attribute.get());
				}
				wrapper.setPropertyValue(atributo, value);

			}
			return pessoaLdap;
		}
	}
}
