package utilizador;

public class Utilizador {
	
	private String nome, tipo, id;
	
	public Utilizador(String nome, String tipo, String id) {
		this.nome = nome;
		this.tipo = tipo;
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return tipo;
	}

	public void setEmail(String tipo) {
		this.tipo = tipo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Utilizador [nome=" + nome + ", tipo=" + tipo + ", id=" + id + "]";
	}

}
