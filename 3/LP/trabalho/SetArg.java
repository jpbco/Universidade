public class SetArg extends FunctionCallInstruction {
	int argumento;

	public SetArg(int argumento) {
		this.argumento = argumento;
	}

	public void function(TISC machine) {
		machine.buffer_argumentos.add(machine.pilha_avaliacao.pop());
		machine.PC++;
	}

	public String toString() {
		return "SET_ARG " + argumento;
	}
}
