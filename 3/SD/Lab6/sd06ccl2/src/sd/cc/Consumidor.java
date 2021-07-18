package sd.cc;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class Consumidor extends Thread {

    LinkedList<String> produtos;
    private static final int MAX_AVAILABLE = 2;
    private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);

    public Consumidor(LinkedList<String> produtos) {
        super();
        this.produtos = produtos;
    }

    public void run() {
        String name = Thread.currentThread().getName();
        try {
            System.out.println("# C " + name + " ponto 0");

            for (int i = 0; i < 3; i++) { // loop de consumo
                synchronized (produtos) {
                    available.acquire();
                    if (produtos.isEmpty()) {
                        System.out.println("\t " + name + " WAIT");
                        produtos.wait(1200);
                        if (produtos.isEmpty()) {
                            System.out.println("\t " + name + " DESISTO");
                            continue;
                        }
                        System.out.println("\t " + name + " WAIT is OVER");
                    }
                    // *************************************
                    // NÃO ALTERAR ESTE BLOCO **************
                    String p = produtos.getFirst();
                    Thread.sleep(1000); // espera propositada para simular ambiente de risco
                    produtos.removeFirst();
                    // *************************************

                    System.out.println("CONSUMIDOR " + name + " consome " + p);
                    available.release();
                }
            }

            System.out.println("\t# C " + name + " ponto 1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
