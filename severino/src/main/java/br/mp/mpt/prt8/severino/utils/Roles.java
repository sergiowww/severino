package br.mp.mpt.prt8.severino.utils;

/**
 * Perfis da aplicação para promover alteração nos registros.
 * 
 * @author sergio.eoliveira
 *
 */
public interface Roles {

	/**
	 * Nome do grupo no AD.
	 */
	String GRUPO_SEVERINO_ACTIVE_DIRECTORY = "PRT08-DLG-SEC-SEVERINO";
	String PADRAO = "hasRole('" + GRUPO_SEVERINO_ACTIVE_DIRECTORY + "')";

}
