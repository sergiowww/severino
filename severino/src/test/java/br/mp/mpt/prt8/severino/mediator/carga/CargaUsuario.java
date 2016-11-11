package br.mp.mpt.prt8.severino.mediator.carga;

import org.springframework.beans.factory.annotation.Autowired;

import br.mp.mpt.prt8.severino.entity.Usuario;
import br.mp.mpt.prt8.severino.mediator.UsuarioHolder;
import br.mp.mpt.prt8.severino.mediator.UsuarioMediator;

/**
 * 
 * @author sergio.eoliveira
 *
 */
public class CargaUsuario implements ICarga{
	@Autowired
	private UsuarioHolder usuarioHolder;

	@Autowired
	private UsuarioMediator usuarioMediator;

	@Override
	public void carga() {
		Usuario usuario = new Usuario();
		usuario.setId("sergio.eoliveira");
		usuario.setNome("Sergio Eduardo");
		usuarioHolder.setUsuario(usuario);
		usuarioMediator.save(usuario);
	}
}
