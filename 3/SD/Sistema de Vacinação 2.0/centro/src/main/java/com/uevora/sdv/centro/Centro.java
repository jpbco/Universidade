package com.uevora.sdv.centro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Centro {
    private Long id;
    private String nome;
    private int capacidade_diaria;

    public Centro() {
    }

    public Centro(String nome, int capacidade_diaria) {
        this.nome = nome;
        this.capacidade_diaria = capacidade_diaria;
    }

    public Centro(Long id, String nome, int capacidade_diaria) {
        this.id = id;
        this.nome = nome;
        this.capacidade_diaria = capacidade_diaria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapacidade_diaria() {
        return capacidade_diaria;
    }

    public void setCapacidade_diaria(int capacidade_diaria) {
        this.capacidade_diaria = capacidade_diaria;
    }


    @Override
    public String toString() {
        return "Centro{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", capacidade_diaria=" + capacidade_diaria +
                '}';
    }
}