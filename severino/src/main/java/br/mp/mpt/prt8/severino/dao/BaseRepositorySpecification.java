package br.mp.mpt.prt8.severino.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Especificação básica para todos os repositórios.
 * 
 * @author sergio.eoliveira
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface BaseRepositorySpecification<T, ID extends Serializable>
		extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
	// nothing.
}
