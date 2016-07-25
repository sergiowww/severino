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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.ContainerCriteria;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Repository;

import br.mp.mpt.prt8.severino.utils.LdapStringUtil;
import br.mp.mpt.prt8.severino.valueobject.PessoaLdap;

/**
 * Consultas no LDAP.
 * 
 * @author sergio.eoliveira
 *
 */
@Repository
public class LdapRepository {
	private static final String KEY_ATRIBUTO_DEPARTAMENTO = "atributoDepartamento";
	private static final String KEY_ATRIBUTO_NOME_PESSOA = "atributoNomePessoa";
	private static final String KEY_USER_SEARCH_BASE = "userSearchBase";
	private static final Logger LOG = LoggerFactory.getLogger(LdapRepository.class);

	@Autowired
	private LdapTemplate ldapTemplate;

	@Resource(name = "ldapProperties")
	private Properties ldapProperties;

	public List<PessoaLdap> findByNomeLike(String parteNome) {
		LOG.info(String.format("Consultando LDAP por \"%s\" ...", parteNome));
		String base = ldapProperties.getProperty(KEY_USER_SEARCH_BASE);
		String atributoNome = ldapProperties.getProperty(KEY_ATRIBUTO_NOME_PESSOA);
		String atributoDepartamento = ldapProperties.getProperty(KEY_ATRIBUTO_DEPARTAMENTO);
		String cn = ldapProperties.getProperty("groupRoleAttribute");
		ContainerCriteria criteria = LdapQueryBuilder.query().base(base).attributes(atributoNome, atributoDepartamento)
				.where("objectClass").is("person").and(cn).whitespaceWildcardsLike(parteNome);
		return ldapTemplate.search(criteria, new PessoaLdapAttributeMapper(atributoNome, atributoDepartamento));
	}

	/**
	 * Mapeador de atributos do LDAP.
	 * 
	 * @author sergio.eoliveira
	 *
	 */
	private final class PessoaLdapAttributeMapper implements AttributesMapper<PessoaLdap> {

		private final String atributoNome, atributoDepartamento;

		public PessoaLdapAttributeMapper(String atributoNome, String atributoDepartamento) {
			super();
			this.atributoNome = atributoNome;
			this.atributoDepartamento = atributoDepartamento;
		}

		@Override
		public PessoaLdap mapFromAttributes(Attributes attributes) throws NamingException {
			Attribute attributeDepartamento = attributes.get(atributoDepartamento);
			String departamento = "";
			if (attributeDepartamento != null) {
				departamento = Objects.toString(attributeDepartamento.get());
			}
			String nome = LdapStringUtil.extrairNomeUsuario(Objects.toString(attributes.get(atributoNome).get()));
			return new PessoaLdap(nome, departamento);
		}
	}
}
