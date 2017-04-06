package br.mp.mpt.prt8.severino.mediator.carga;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.mp.mpt.prt8.severino.entity.Local;
import br.mp.mpt.prt8.severino.entity.Organizacao;
import br.mp.mpt.prt8.severino.entity.Usuario;
import br.mp.mpt.prt8.severino.mediator.UsuarioHolder;

/**
 * 
 * @author sergio.eoliveira
 *
 */
public class CargaUsuario implements ICarga {
	@Autowired
	private UsuarioHolder usuarioHolder;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void carga() {
		Usuario usuario = new Usuario();
		usuario.setId("sergio.eoliveira");
		usuario.setNome("Sergio Eduardo");
		usuario.setLocal(getLocal());
		usuarioHolder.setUsuario(usuario);
		entityManager.persist(usuario);
	}

	private Local getLocal() {
		Local local = entityManager.find(Local.class, 1);
		if (local == null) {
			local = new Local("teste");
			Organizacao organizacao = new Organizacao("teste2");
			entityManager.persist(organizacao);
			local.setOrganizacao(organizacao);
			entityManager.persist(local);
		}
		return local;
	}
}
