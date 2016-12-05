package br.mp.mpt.prt8.severino.mediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.mp.mpt.prt8.severino.entity.AcessoGaragem;
import br.mp.mpt.prt8.severino.entity.ControleMotorista;
import br.mp.mpt.prt8.severino.entity.FonteDisponibilidade;
import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.entity.Passageiro;
import br.mp.mpt.prt8.severino.entity.Usuario;
import br.mp.mpt.prt8.severino.entity.Veiculo;
import br.mp.mpt.prt8.severino.entity.Viagem;
import br.mp.mpt.prt8.severino.valueobject.PessoaDisponibilidade;

/**
 * Teste para a disponibilidade da pessoa agrupada.
 * 
 * @author sergio.eoliveira
 *
 */
@Sql({ "/testFindUltimaDisponibilidade.sql", "/testUltimosPassageiros.sql" })
public class PessoaDisponibilidadeMediatorTest extends AbstractSeverinoTests {
	private static final DateFormat DTF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	@Autowired
	private PessoaDisponibilidadeMediator pessoaDisponibilidadeMediator;

	@Test
	public void testFindUltimaDisponibilidade() throws Exception {
		List<PessoaDisponibilidade> ultimas = pessoaDisponibilidadeMediator.findUltimaDisponibilidade(DTF.parse("2016-01-01 00:00"), DTF.parse("2017-01-01 00:00"));
		assertEquals(5, ultimas.size());

		checkPassageiro("2016-11-14 13:06", "2016-11-14 15:29", "Cintia Nazare Pantoja Leao", false, FonteDisponibilidade.ACESSO_GARAGEM, ultimas);
		checkPassageiro("2016-11-16 09:35", "2016-11-16 08:35", "Carla Afonso de Novoa Melo", true, FonteDisponibilidade.VIAGEM, ultimas);
		checkPassageiro("2016-11-16 08:54", null, "Faustino Bartolomeu Alves Pimenta", true, FonteDisponibilidade.ACESSO_GARAGEM, ultimas);
		checkPassageiro(null, "2016-11-16 09:31", "Hideraldo Luiz de Sousa Machado", false, FonteDisponibilidade.VIAGEM, ultimas);
		checkPassageiro(null, "2016-11-17 12:11", "Loris Rocha Pereira Junior", false, FonteDisponibilidade.VIAGEM, ultimas);
	}

	@After
	public void cleanUp() {
		deleteFromTables(Passageiro.class, Viagem.class, ControleMotorista.class, AcessoGaragem.class, Veiculo.class, Usuario.class, Motorista.class);
	}

	private void checkPassageiro(String entrada, String saida, String nomePassageiro, boolean entrou, FonteDisponibilidade fonte, List<PessoaDisponibilidade> ultimas) {
		Optional<PessoaDisponibilidade> optional = ultimas.stream().filter(p -> p.getNome().equals(nomePassageiro)).findFirst();
		assertTrue(optional.isPresent());
		PessoaDisponibilidade pessoaDisponibilidade = optional.get();
		assertEquals(entrou, pessoaDisponibilidade.isEntrou());
		assertEquals(fonte, pessoaDisponibilidade.getFonte());
		if (entrada == null) {
			assertNull(pessoaDisponibilidade.getEntrada());
		} else {
			assertEquals(entrada, DTF.format(pessoaDisponibilidade.getEntrada()));
		}

		if (saida == null) {
			assertNull(pessoaDisponibilidade.getSaida());
		} else {
			assertEquals(saida, DTF.format(pessoaDisponibilidade.getSaida()));
		}
		if (entrou) {
			assertEquals(pessoaDisponibilidade.getEntrada(), pessoaDisponibilidade.getDataEvento());
		} else {
			assertEquals(pessoaDisponibilidade.getSaida(), pessoaDisponibilidade.getDataEvento());
		}
		assertNotNull(Objects.toString(pessoaDisponibilidade));
		assertNotNull(pessoaDisponibilidade.getId());
	}

}
