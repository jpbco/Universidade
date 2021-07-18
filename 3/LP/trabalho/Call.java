public class Call extends FunctionCallInstruction {

	int distancia; 
	String etiqueta;

	public Call(int distancia, String etiqueta) {
		this.distancia = distancia;
		this.etiqueta = etiqueta;
	}

	public void function(TISC machine) {
		machine.enviroment_pointer.return_adress = machine.PC + 1;
		machine.PC = machine.etiquetas.get(etiqueta);
	}

	public String toString() {
		return "CALL " + distancia + " " + etiqueta;
	}
}
