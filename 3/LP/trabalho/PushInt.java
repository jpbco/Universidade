public class PushInt extends IntegerManipulationInstruction {
	int valor;

	public PushInt(int valor) {
		this.valor = valor;
	}

	public void function(TISC machine) {
		machine.pilha_avaliacao.push(valor);
		machine.PC++;
	}

	public String toString() {
		return "PUSH_INT " + valor;
	}
}
