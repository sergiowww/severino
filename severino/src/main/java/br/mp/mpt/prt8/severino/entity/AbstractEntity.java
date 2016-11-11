package br.mp.mpt.prt8.severino.entity;

import java.io.Serializable;

/**
 * Interface base para as entidades.
 * 
 * @author sergio.eoliveira
 *
 * @param <ID>
 */
public abstract class AbstractEntity<ID> implements Serializable {
	private static final long serialVersionUID = -2658306733109848510L;

	/**
	 * Identificador do objeto.
	 * 
	 * @return
	 */
	public abstract ID getId();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		Class<?> testClass = getClass();
		while (testClass.getSuperclass() != AbstractEntity.class) {
			testClass = testClass.getSuperclass();
		}
		int result = testClass.hashCode();
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!getClass().isAssignableFrom(obj.getClass()) && !obj.getClass().isAssignableFrom(getClass())) {
			return false;
		}
		AbstractEntity<ID> other = (AbstractEntity<ID>) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

}
