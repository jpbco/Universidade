public class PrintStr extends ExitInstruction {
	String string;

	public PrintStr(String string) {
		this.string = string;
	}

	public void function(TISC machine) {
		System.out.print(string);
		machine.PC++;
	}

	public String toString() {
		return "PRINT_STRING " + string;
	}

}
