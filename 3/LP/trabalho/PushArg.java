public class PushArg extends ArgAcessInstruction {
	int distancia;
	int valor;

	public PushArg(int distancia, int valor) {
		this.distancia = distancia;
		this.valor = valor;
	}

	public void function(TISC machine) {

		RegistoAtivacao current = machine.enviroment_pointer;

		for (int i = 0; i < distancia; i++) {
			current = current.control_link;
		}

		machine.pilha_avaliacao.push(current.args[valor - 1]);
		machine.PC++;
	}

	public String toString() {
		return "PUSH_ARG " + distancia + " " + valor;
	}

}
