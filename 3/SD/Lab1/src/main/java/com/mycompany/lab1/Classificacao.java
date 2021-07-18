/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab1;

/**
 *
 * @author jpbc
 */
public class Classificacao implements Comparable<Classificacao>{
  // 
    private int numero;
    private int nota;
    private String descricao;
    private String observaçoes;

    public Classificacao(int numero, int nota, String descricao, String observaçoes) {
        this.numero = numero;
        this.nota = nota;
        this.descricao = descricao;
        this.observaçoes = observaçoes;
    }
    
    
    @Override
    public int compareTo(Classificacao t) {
        if(this.getNota()< t.getNota())
            return -1;
        if(this.getNota()>t.getNota())
            return 1;
        else 
            return 0;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getNota() {
        return nota;
    }

    public int getNumero() {
        return numero;
    }

    public String getObservaçoes() {
        return observaçoes;
    }
    
    
}
