package br.mp.mpt.prt8.severino.controller;

import static org.junit.Assert.assertSame;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.mp.mpt.prt8.severino.dao.LdapRepository;

/**
 * Teste do controlador da consulta ldap.
 * 
 * @author sergio.eoliveira
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LdapConsultaControllerTest {
	@InjectMocks
	private LdapConsultaController ldapConsultaController;

	@Mock
	private LdapRepository ldapRepositoryMock;

	@Test
	public void testFindResponsaveisByNome() {
		String nome = "termo";
		ldapConsultaController.findResponsaveisByNome(nome);
		Mockito.verify(ldapRepositoryMock, Mockito.only()).findByNomeLike(nome);
	}

	@Test
	public void testFindResponsaveisByNomeVazio() {
		String nome = "";
		assertSame(Collections.emptyList(), ldapConsultaController.findResponsaveisByNome(nome));
		Mockito.verify(ldapRepositoryMock, Mockito.never()).findByNomeLike(nome);
	}

}
