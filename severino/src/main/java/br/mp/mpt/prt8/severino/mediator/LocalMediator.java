package br.mp.mpt.prt8.severino.mediator;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.LocalRepository;
import br.mp.mpt.prt8.severino.dao.OrganizacaoRepository;
import br.mp.mpt.prt8.severino.entity.Local;
import br.mp.mpt.prt8.severino.entity.Organizacao;
import br.mp.mpt.prt8.severino.utils.Constantes;
import br.mp.mpt.prt8.severino.utils.StringUtilApp;

/**
 * Mediator para o local.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class LocalMediator extends AbstractExampleMediator<Local, Integer> {
	private static final int PRIMEIRO_NIVEL_ORGANIZACAO = 1;
	private static final int SEGUNDO_NIVEL_ORGANIZACAO = 2;
	/**
	 * Localizar valor após a string <b>ou</b>=Sede
	 */
	private static final Pattern PATTERN_OU = Pattern.compile("(?<=(ou\\=))([^,]*)", Pattern.CASE_INSENSITIVE);

	@Autowired
	private LocalRepository localRepository;

	@Autowired
	private OrganizacaoRepository organizacaoRepository;

	@Resource(name = "ldapProperties")
	private Properties ldapProperties;

	@Override
	protected BaseRepositorySpecification<Local, Integer> repositoryBean() {
		return localRepository;
	}

	@Override
	protected Local getExampleForSearching(String searchValue) {
		return new Local(searchValue);
	}

	/**
	 * Buscar o local, extraindo-o do DN <br>
	 * Exemplos:
	 * 
	 * <pre>
	 * cn=Sergio Eduardo Dantas de Oliveira,ou=Usuarios,ou=Sede,<b>ou=PRT08</b>,<b><i>ou=MPT</i></b>,dc=mpt,dc=intra
	 * cn=André Luis Lima Saldanha,ou=Usuarios,<b>ou=Sede</b>,<b>ou=PRT08</b>,<b>ou=MPT</b>,dc=mpt,dc=intra
	 * </pre>
	 * 
	 * 
	 * <ul>
	 * <li>A parte que contém ou=PRT08 será a organização
	 * {@link br.mp.mpt.prt8.severino.entity.Organizacao}</li>
	 * <li>A parte que contém "ou=Sede" será o {@link Local}</li>
	 * <li>A parte que contém "ou=MPT" deverá constar no ldap.xml na entrada
	 * "userSearchBase" para marcar o início da árvore, a partir desta entrada
	 * que será buscado o primeiro nível - organização e o segundo nível -
	 * local.</li>
	 * </ul>
	 * 
	 * @param dn
	 * @return
	 */
	@Transactional
	public Local findByDn(String dn) {
		String base = ldapProperties.getProperty(Constantes.KEY_USER_SEARCH_BASE);
		List<String> entradas = Arrays.asList(StringUtils.commaDelimitedListToStringArray(dn));
		String baseEntry = entradas.stream().filter(p -> base.equalsIgnoreCase(p)).findFirst().get();
		int indiceBase = entradas.indexOf(baseEntry);
		String organizacaoNome = entradas.get(indiceBase - PRIMEIRO_NIVEL_ORGANIZACAO);
		String localNome = entradas.get(indiceBase - SEGUNDO_NIVEL_ORGANIZACAO);

		Organizacao organizacao = getOrganizacao(organizacaoNome);

		organizacaoRepository.save(organizacao);

		Local local = getLocal(localNome, organizacao);
		local.setOrganizacao(organizacao);
		localRepository.save(local);
		return local;
	}

	private Organizacao getOrganizacao(String organizacaoNome) {
		organizacaoNome = StringUtilApp.extrairPadrao(organizacaoNome, PATTERN_OU);
		Organizacao organizacao = organizacaoRepository.findByNomeIgnoreCase(organizacaoNome);
		if (organizacao == null) {
			organizacao = new Organizacao();
			organizacao.setNome(organizacaoNome);
		}
		return organizacao;
	}

	private Local getLocal(String localNome, Organizacao organizacao) {
		localNome = StringUtilApp.extrairPadrao(localNome, PATTERN_OU);
		Local local = localRepository.findByNomeIgnoreCaseAndOrganizacao(localNome, organizacao);
		if (local == null) {
			local = new Local(localNome);
		}
		return local;
	}

}
