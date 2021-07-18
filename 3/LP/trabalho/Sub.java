public class Sub extends ArithmeticInstruction {

	public void function(TISC machine) {
		int a = machine.pilha_avaliacao.pop();
		int b = machine.pilha_avaliacao.pop();
		machine.pilha_avaliacao.push(b - a);
		machine.PC++;
	}

	public String toString() {
		return "SUB";
	}

}
