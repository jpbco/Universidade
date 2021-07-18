/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aula06;

/**
 *
 * @author jpbc
 */
public class PrimosThread extends Thread {

    int inicio;
    int fim;

    public PrimosThread() {
        super();
        this.inicio = 0;
        this.fim = 10;
    }

    public PrimosThread(int inicio, int fim) {
        super();
        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    public void run() {
        Primos p = new Primos(inicio, fim);
        p.go();
    }

    public static void main(String[] args) throws InterruptedException {

        long starTimeMillis = System.currentTimeMillis();

        var p1 = new PrimosThread(0, 50000);
        var p2 = new PrimosThread(50000, 100000);
        var p3 = new PrimosThread(100000, 150000);
        var p4 = new PrimosThread(150000, 200000);

        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p4.join();
        long endTimeMillis = System.currentTimeMillis();

        System.out.println("Time elapsed: " + (endTimeMillis - starTimeMillis));
    }
}
