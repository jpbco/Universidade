public class StoreArg extends ArgAcessInstruction {
	int distancia;
	int argumento;

	public StoreArg(int distancia, int argumento) {
		this.distancia = distancia;
		this.argumento = argumento;
	}

	public void function(TISC machine) {

		int temp = machine.pilha_avaliacao.pop();
		RegistoAtivacao current = machine.enviroment_pointer;
		for (int i = 0; i < distancia; i++) {
			current = current.control_link;
		}

		current.args[argumento - 1] = temp;
		machine.PC++;
	}

	public String toString() {
		return "STORE_ARG " + distancia + " " + argumento;
	}

}
