/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aula06;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author jpbc
 */
public class PrimosThreadPool {

    public static void main(String[] args) {
        long starTimeMillis = System.currentTimeMillis();

        PrimosCountAction contaPrimos = new PrimosCountAction(0, 200000);

        ForkJoinPool pool = new ForkJoinPool();
        System.out.println(pool.getParallelism());

        pool.invoke(contaPrimos);

        System.out.println("RESULTADO: " + contaPrimos.getResult());
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (endTimeMillis-starTimeMillis));

    }
}
