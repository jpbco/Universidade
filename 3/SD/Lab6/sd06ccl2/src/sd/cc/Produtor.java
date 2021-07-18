package sd.cc;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class Produtor extends Thread {

    LinkedList<String> produtos;
    private static final int MAX_AVAILABLE = 1;
    private final Semaphore available = new Semaphore(MAX_AVAILABLE , true);
    
    public Produtor(LinkedList<String> produtos) {
        super();
        this.produtos = produtos;
    }

    public void run() {
        String name = Thread.currentThread().getName();
        try {
            System.out.println("# P " + name + " ponto 0");

            for (int i = 0; i < 4; i++) {
                synchronized (produtos) {
                    available.acquire();
                    if (produtos.size() < 10) {   // ciclo de producao
                        String x = "p_" + name + "_" + i;
                        produtos.add(x);
                        
                        System.out.println("\t\t PRODUTOR " + name + " gera " + x.toString());
                    }
                    produtos.notify();
                    available.release();
                }
            }
            System.out.println("\t# P " + name + " ponto 1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
