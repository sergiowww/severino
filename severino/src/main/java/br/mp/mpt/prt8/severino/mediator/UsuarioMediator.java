package br.mp.mpt.prt8.severino.mediator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.UsuarioRepository;
import br.mp.mpt.prt8.severino.entity.Usuario;
import br.mp.mpt.prt8.severino.utils.StringUtilApp;

/**
 * Mediador de opera��es.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class UsuarioMediator extends AbstractExampleMediator<Usuario, String> {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private LocalMediator localMediator;

	@Override
	protected BaseRepositorySpecification<Usuario, String> repositoryBean() {
		return usuarioRepository;
	}

	@Override
	protected Usuario getExampleForSearching(String searchValue) {
		Usuario probe = new Usuario();
		probe.setId(searchValue);
		probe.setNome(searchValue);
		return probe;
	}

	/**
	 * Gravar usu�rio e retornar.
	 * 
	 * @param auth
	 * @return
	 */
	public void save(Authentication auth) {
		if (usuarioHolder.getUsuario() == null) {
			String login = auth.getName();
			Usuario usuario = usuarioRepository.findOne(login);
			LdapUserDetails details = (LdapUserDetails) auth.getPrincipal();

			String dn = details.getDn();
			if (StringUtils.isEmpty(dn)) {
				throw new IllegalArgumentException("O valor do DN � obrigat�rio, porque cargas d�gua n�o veio preenchido?!");
			}
			String nomeUsuario = getNomeUsuario(login, dn);
			if (usuario == null) {
				usuario = new Usuario();
				usuario.setId(login);
			}
			usuario.setNome(nomeUsuario);
			usuario.setLocal(localMediator.findByDn(dn));
			usuario = usuarioRepository.save(usuario);

			usuarioHolder.setUsuario(usuario);
		}
	}

	private String getNomeUsuario(String login, String dn) {
		String nomeUsuario = StringUtilApp.extrairNomeUsuario(dn);
		if (StringUtils.isEmpty(nomeUsuario)) {
			return login;
		}
		return nomeUsuario;
	}

}
