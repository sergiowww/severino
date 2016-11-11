package br.mp.mpt.prt8.severino.mediator.carga;

import org.springframework.beans.factory.annotation.Autowired;

import br.mp.mpt.prt8.severino.entity.Setor;
import br.mp.mpt.prt8.severino.mediator.SetorMediator;

/**
 * Carga de setores.
 * 
 * @author sergio.eoliveira
 *
 */
public class CargaSetor implements ICarga {

	@Autowired
	private SetorMediator setorMediator;

	private Setor setor1;
	private Setor setor2;

	@Override
	public void carga() {
		this.setor1 = new Setor();
		setor1.setAndar((short) 1);
		setor1.setNome("Setor 1");
		setor1.setSala("001");
		setorMediator.save(setor1);

		this.setor2 = new Setor();
		setor2.setAndar((short) 1);
		setor2.setNome("Setor 2");
		setor2.setSala("001");
		setorMediator.save(setor2);
	}

	/**
	 * @return the setor1
	 */
	public Setor getSetor1() {
		return setor1;
	}

	/**
	 * @return the setor2
	 */
	public Setor getSetor2() {
		return setor2;
	}

}
