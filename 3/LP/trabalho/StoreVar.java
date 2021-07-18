public class StoreVar extends VarAcessInstruction {
	int distancia;
	int variavel;

	public StoreVar(int distancia, int variavel) {
		this.distancia = distancia;
		this.variavel = variavel;
	}

	public void function(TISC machine) {
		int temp = machine.pilha_avaliacao.pop();
		RegistoAtivacao current = machine.enviroment_pointer;

		for (int i = 0; i < distancia; i++) {
			current = current.control_link;
		}
		current.vars[variavel - 1] = temp;
		machine.PC++;
	}

	public String toString() {
		return "STORE_VAR " + distancia + " " + variavel;
	}

}
