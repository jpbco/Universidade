public class PushVar extends VarAcessInstruction {
	int distancia;
	int variavel;

	public PushVar(int distancia, int variavel) {
		this.distancia = distancia;
		this.variavel = variavel;
	}

	public void function(TISC machine) {
		RegistoAtivacao current = machine.enviroment_pointer;
		for (int i = 0; i < distancia; i++) {
			current = current.control_link;
		}
	
		machine.pilha_avaliacao.push(current.vars[variavel - 1]);
		machine.PC++;
	}

	public String toString() {
		return "PUSH_VAR " + distancia + " " + variavel;
	}
}
