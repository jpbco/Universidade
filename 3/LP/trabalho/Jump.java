public class Jump extends JumpInstruction {
	String etiqueta;

	public Jump(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public void function(TISC machine) {
		machine.PC = machine.etiquetas.get(etiqueta);
	}

	public String toString() {
		return "JUMP" + etiqueta;
	}
}
