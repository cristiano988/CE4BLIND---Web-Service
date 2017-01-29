package utils.agents;

import utils.models.Medicine;

public interface Agent {
	
	public boolean exists(); //metodo para pesquisar o produto desejado
	public Medicine getMedicine(); //metodo para uma vez que temos os dados, construir e devolver o medicamento
	
}
