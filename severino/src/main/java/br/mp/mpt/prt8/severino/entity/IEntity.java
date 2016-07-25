package br.mp.mpt.prt8.severino.entity;

import java.io.Serializable;

/**
 * Interface base para as entidades.
 * 
 * @author sergio.eoliveira
 *
 * @param <ID>
 */
public interface IEntity<ID> extends Serializable{
	/**
	 * Identificador do objeto.
	 * 
	 * @return
	 */
	ID getId();
}
