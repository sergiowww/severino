package br.mp.mpt.prt8.severino.mediator;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.UsuarioRepository;
import br.mp.mpt.prt8.severino.entity.Usuario;
import br.mp.mpt.prt8.severino.utils.LdapStringUtil;

/**
 * Mediador de operações.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class UsuarioMediator extends AbstractExampleMediator<Usuario, String> {

	@Autowired
	private UsuarioRepository usuarioRepository;

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
	 * Gravar usuário e retornar.
	 * 
	 * @param auth
	 * @return
	 */
	public void save(Authentication auth) {
		if (usuarioHolder.getUsuario() == null) {
			String login = auth.getName();
			Usuario usuario = usuarioRepository.findOne(login);
			LdapUserDetails details = (LdapUserDetails) auth.getPrincipal();
			String nomeUsuario = getNomeUsuario(login, details.getDn());
			if (usuario == null) {
				usuario = new Usuario();
				usuario.setId(login);
				usuario.setNome(nomeUsuario);
				usuario = usuarioRepository.save(usuario);
			}

			if (!Objects.equals(nomeUsuario, usuario.getNome())) {
				usuario.setNome(nomeUsuario);
				usuario = usuarioRepository.save(usuario);
			}
			usuarioHolder.setUsuario(usuario);
		}
	}

	private String getNomeUsuario(String login, String dn) {
		if (StringUtils.isEmpty(dn)) {
			return login;
		}
		String nomeUsuario = LdapStringUtil.extrairNomeUsuario(dn);
		if (StringUtils.isEmpty(nomeUsuario)) {
			return login;
		}
		return nomeUsuario;
	}

}
