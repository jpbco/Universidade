/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab1;

import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author jpbc
 */
public class Main {

    public static void Ex1() {

        System.out.println("System java version: " + System.getProperty("java.version"));
        System.out.println("System java vendor: " + System.getProperty("java.vendor"));
        System.out.println("System classpath: " + System.getProperty("java.class.path"));
        System.out.println("Path separator:" + System.getProperty("file.separator"));
        System.out.println("User name:" + System.getProperty("user.name"));
        System.out.println("File enconding:" + System.getProperty("file.enconding"));
    }

    public static void Ex2() throws IOException {
        byte[] b = new byte[128];
        int lidos = System.in.read(b);
        String s = new String(b, 0, lidos - 1); // ou -2 no windows
        System.out.println("lido: " + lidos);
        System.out.println("s: " + s + "\n\n");
        int valor = Integer.parseInt(s.substring(0, lidos - 1)) + 1;
        System.out.println("valor: " + valor);
    }

        private static void insertionSort(String s, LinkedList<String> ord) {
            int pos=0;
            while(pos< ord.size() && ord.get(pos).compareTo(s)<0){
                pos++;
            }
            ord.add(pos,s);
 
    }
    public static void main(String[] args) throws IOException {

        LinkedList<String> lista = new LinkedList<String>();
        for (String arg : args) {
            insertionSort(arg, lista);
            System.out.println("Adicionado: " + arg);
        }
        System.out.println(lista);

    }


}
