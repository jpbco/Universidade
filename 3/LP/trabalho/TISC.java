import java.util.LinkedList;
import java.util.Stack;
import java.util.Hashtable;

// Conjunto de Instrucoes Maquina Simples
public class TISC {
	LinkedList<Instruction> memoria_instrucoes;
	Stack<Integer> pilha_avaliacao;
	Stack<RegistoAtivacao> memoria_execucao;
	Hashtable<String, Integer> etiquetas;
	RegistoAtivacao enviroment_pointer;
	LinkedList<Integer> buffer_argumentos;
	int PC;

	public TISC() {
		memoria_instrucoes = new LinkedList<Instruction>();
		etiquetas = new Hashtable<String, Integer>();
		pilha_avaliacao = new Stack<Integer>();
		memoria_execucao = new Stack<RegistoAtivacao>();
		buffer_argumentos = new LinkedList<Integer>();
	}

	/** Executa o programa TISC carregado na maquina. */
	public void function() {
		// printMemoria(); // <--- 1º parte do trabalho
		PC = etiquetas.get("program");
		while (true) {
			memoria_instrucoes.get(PC).function(this);
			if (memoria_execucao.empty()) {
				break;
			}
		}
	}

	public void printMemoria() {
		System.out.println("Instruções em Memória:");
		System.out.println("--------------------------------------------------");
		for (Instruction a : memoria_instrucoes) {
			System.out.println(a.toString());
		}
		System.out.println("--------------------------------------------------\n");
	}

}
