package br.mp.mpt.prt8.severino.mediator.carga;

import org.springframework.beans.factory.annotation.Autowired;

import br.mp.mpt.prt8.severino.entity.Cargo;
import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.mediator.MotoristaMediator;

/**
 * Carga de dados para o motorista.
 * 
 * @author sergio.eoliveira
 *
 */
public class CargaMotorista implements ICarga {

	@Autowired
	private MotoristaMediator motoristaMediator;
	private Motorista motorista;
	
	@Override
	public void carga() {
		Motorista motorista = new Motorista();
		motorista.setCargo(Cargo.MOTORISTA);
		motorista.setMatricula("123456-4");
		motorista.setNome("João do Caminhão");
		this.motorista = motoristaMediator.save(motorista);		
	}

	/**
	 * @return the motorista
	 */
	public Motorista getMotorista() {
		return motorista;
	}

	
}
