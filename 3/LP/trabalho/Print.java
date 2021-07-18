public class Print extends ExitInstruction {

	public void function(TISC machine) {
		System.out.print(machine.pilha_avaliacao.pop());
		machine.PC++;
	}

	public String toString() {
		return "PRINT";
	}

}
