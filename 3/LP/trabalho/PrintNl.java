public class PrintNl extends ExitInstruction {

	public void function(TISC machine) {
		System.out.println();
		machine.PC++;
	}

	public String toString() {
		return "PRINT_NL";
	}

}
