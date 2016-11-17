package br.mp.mpt.prt8.severino.mediator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mp.mpt.prt8.severino.valueobject.PessoaDisponibilidade;

/**
 * Mediator para verificar a disponibilidade de um servidor da casa.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class PessoaDisponibilidadeMediator {
	@Autowired
	private AcessoGaragemMediator acessoGaragemMediator;

	@Autowired
	private ViagemMediator viagemMediator;

	/**
	 * Buscar a união das disponibilidades em cada local.
	 * 
	 * @return
	 */
	public List<PessoaDisponibilidade> findUltimaDisponibilidade() {
		List<PessoaDisponibilidade> ultimoViagem = viagemMediator.findUltimaDisponibilidade();
		List<PessoaDisponibilidade> ultimoGaragem = acessoGaragemMediator.findUltimaDisponibilidade();
		List<PessoaDisponibilidade> uniao = new ArrayList<PessoaDisponibilidade>(ultimoGaragem);
		uniao.addAll(ultimoViagem);
		Collections.sort(uniao);
		PessoaDisponibilidade anterior = null;
		for (int i = uniao.size() - 1; i >= 0; i--) {
			PessoaDisponibilidade pessoa = uniao.get(i);
			if (anterior == null) {
				anterior = pessoa;
			}
			if (pessoa != anterior && pessoa.getNome().equals(anterior.getNome())) {
				uniao.remove(i);
			}
			anterior = pessoa;
		}
		return uniao;
	}

}
