public class Locals extends FunctionCallInstruction

{
	int numero_argumentos;
	int numero_variaveis;

	public Locals(int numero_argumentos, int numero_variaveis) {
		this.numero_argumentos = numero_argumentos;
		this.numero_variaveis = numero_variaveis;
	}

	public void function(TISC machine) {

		RegistoAtivacao temp = new RegistoAtivacao(null, numero_argumentos, numero_variaveis, null);

		if (!machine.memoria_execucao.empty()) {
			temp.control_link = machine.memoria_execucao.peek();
		}

		for (int i = 0; i < machine.buffer_argumentos.size(); i++) {
			temp.args[i] = machine.buffer_argumentos.get(i);
		}
		machine.buffer_argumentos.clear();
		machine.enviroment_pointer = temp;
		machine.memoria_execucao.push(temp);
		machine.PC++;
	}

	public String toString() {
		return "LOCALS " + numero_argumentos + " " + numero_variaveis;
	}
}
