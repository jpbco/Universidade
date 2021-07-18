public class Jlt extends JumpInstruction {
	String etiqueta;

	public Jlt(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public void function(TISC machine) {
		int a = machine.pilha_avaliacao.pop();
		int b = machine.pilha_avaliacao.pop();

		if (b < a) {
			machine.PC = machine.etiquetas.get(etiqueta);
		} else {
			machine.PC++;
		}
	}

	public String toString() {
		return "JLT " + etiqueta;
	}

}
