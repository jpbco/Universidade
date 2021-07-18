package trabalhofinal;


public class Aula {

	private String data;
	private String hora;
	private String disciplina;

	public Aula(String data, String hora, String disciplina) {
		this.data = data;
		this.hora = hora;
		this.disciplina = disciplina;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	
	@Override
	public String toString() {
		return "Aula:" + data + " " + hora + " " + disciplina;
	}


}
