package br.mp.mpt.prt8.severino.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.mp.mpt.prt8.severino.dao.LdapRepository;
import br.mp.mpt.prt8.severino.valueobject.PessoaLdap;

/**
 * Controlador de consultas ao LDAP.
 * 
 * @author sergio.eoliveira
 *
 */
@Controller
@RequestMapping("/ldap")
public class LdapConsultaController {

	@Autowired
	private LdapRepository ldapRepository;

	/**
	 * Listar pessoas por parte do nome.
	 * 
	 * @param nome
	 * @return
	 */
	@GetMapping(value = "/listarPessoasPorParteNome", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<PessoaLdap> findResponsaveisByNome(@RequestParam("term") String nome) {
		if (StringUtils.isEmpty(nome)) {
			return Collections.emptyList();
		}
		return ldapRepository.findByNomeLike(nome);
	}
}
