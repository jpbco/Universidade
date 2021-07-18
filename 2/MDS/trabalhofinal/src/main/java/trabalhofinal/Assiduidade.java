package trabalhofinal;

import utilizador.*;
import java.util.ArrayList;

public class Assiduidade {
	private Utilizador user;
	private ArrayList<Presenca> presencas;

	public Assiduidade(Utilizador user, ArrayList<Presenca> presencas) {
		this.user = user;
		this.presencas = presencas;
	}

	public Utilizador getUser() {
		return user;
	}

	public void setUser(Utilizador user) {
		this.user = user;
	}

	public void setPresencas(ArrayList<Presenca> presencas) {
		this.presencas = presencas;
	}

	public ArrayList<Presenca> getPresencas() {
		return presencas;
	}

	public void addPresencas(Presenca presenca) {
		this.presencas.add(presenca);
	}

	@Override
	public String toString() {
		return "Assiduidade [user=" + user.toString() + ", presencas=" + getPresencas().toString() + "]";
	}

}