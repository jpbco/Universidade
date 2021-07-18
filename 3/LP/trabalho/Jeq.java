public class Jeq extends JumpInstruction {

	String etiqueta;

	public Jeq(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public void function(TISC machine) {
		int a = machine.pilha_avaliacao.pop();
		int b = machine.pilha_avaliacao.pop();

		if (b == a) {
			machine.PC = machine.etiquetas.get(etiqueta);
		} else {
			machine.PC++;
		}
	}

	public String toString() {
		return "JEQ " + etiqueta;
	}

}
