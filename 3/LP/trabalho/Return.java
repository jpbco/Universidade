public class Return extends FunctionCallInstruction {

	public void function(TISC machine) {

		machine.memoria_execucao.pop();
		if (!machine.memoria_execucao.empty()) {
			machine.enviroment_pointer = machine.memoria_execucao.peek();
			machine.PC = machine.enviroment_pointer.return_adress;
		}
	}

	public String toString() {
		return "RETURN";
	}

}
