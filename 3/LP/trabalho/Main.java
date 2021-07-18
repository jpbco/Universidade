// Programa principal para a implementacao da maquina abstracta TISC.

public class Main {
  public static void main(String args[]) throws Exception {
    parser aParser = new parser();
    TISC maquina;

    // carrega o programa TISC
    maquina = (TISC) aParser.parse().value;

    // e function-o
    if (maquina != null)
      // System.out.println(maquina.toString());
      maquina.function();
  }
}
