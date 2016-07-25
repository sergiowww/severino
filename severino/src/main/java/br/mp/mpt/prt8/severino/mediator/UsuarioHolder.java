package br.mp.mpt.prt8.severino.mediator;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import br.mp.mpt.prt8.severino.entity.Usuario;

/**
 * Armazena o usuário corrente da aplicação.
 * 
 * @author sergio.eoliveira
 *
 */
@Component
@SessionScope
public class UsuarioHolder implements Serializable {
	private static final long serialVersionUID = 7273394785937559357L;
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
