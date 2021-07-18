public class RegistoAtivacao {
	RegistoAtivacao control_link;
	int[] args;
	int[] vars;
	Integer return_adress;

	public RegistoAtivacao(RegistoAtivacao control_link, int size_args, int size_vars, Integer return_adress) {
		this.control_link = control_link;
		this.args = new int[size_args];
		this.vars = new int[size_vars];
		this.return_adress = return_adress;
	}
}
