package trabalhofinal;

import utilizador.Utilizador;

public class Presenca {

	private Utilizador utilizador;
	private boolean falta;
	private boolean justificada;

	public Presenca(Utilizador utilizador) {
		this.utilizador = utilizador;
		this.falta = true;
		this.justificada = false;
	}

	public void setUtilizador(Utilizador utilizador) {
		this.utilizador = utilizador;
	}

	public void setFalta(boolean falta) {
		this.falta = falta;
	}

	public void setJustificada(boolean justificada) {
		this.justificada = justificada;
	}
	
	public Utilizador getUtilizador() {
		return utilizador;
	}


	public boolean isFalta() {
		return falta;
	}

	public boolean isJustificada() {
		return justificada;
	}

	@Override
	public String toString() {
		return "Presenca [utilizador=" + utilizador.toString() + ", falta=" + falta + ", justificada=" + justificada
				+ "]";
	}

}
