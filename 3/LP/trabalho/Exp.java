public class Exp extends ArithmeticInstruction {

	public void function(TISC machine) {
		int a = machine.pilha_avaliacao.pop();
		int b = machine.pilha_avaliacao.pop();
		machine.pilha_avaliacao.push(((int) Math.pow(b, a)));
		machine.PC++;
	}

	public String toString() {
		return "EXP";
	}
}
