/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab1;

import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author jpbc
 */
public class NotasDeSO2 {

    LinkedList<Classificacao> lista;
    
    public NotasDeSO2() {
        this.lista = new LinkedList<Classificacao>();
    }

    public NotasDeSO2(LinkedList<Classificacao> lista) {
        this.lista = lista;
    }
    
    
    
    
    public void adicionaClassif(Classificacao c){
        
    }
    
    public LinkedList<Classificacao> getListaOrdenadaDeClassif(){
            LinkedList<Classificacao> l = this.getLista();
            Collections.sort(l);

        return l;
    }

    public LinkedList<Classificacao> getLista() {
        return lista;
    }

    @Override
    public String toString() {
        
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    public static void main(String[] args) {
        NotasDeSO2 notas = new NotasDeSO2();
        for(int i = 0; i <10; i++){
            notas.adicionaClassif(new Classificacao(i, i, "ola", "adeus"));
        }
        
        System.out.println(notas.toString());
    }
}
