package com.uevora.sdv.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Centro {
    @Id
    @SequenceGenerator(
            name = "centro_sequence",
            sequenceName = "centro_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "centro_sequence"
    )

    private Long id;
    private String nome;
    private int capacidade_diaria;

    @OneToMany(mappedBy = "centro")
    private List<Agendamento> agendamentos;

    @OneToMany(mappedBy = "centro")
    private List<StockDiario> stock;

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


    public List<StockDiario> getStock() {
        return stock;
    }

    public void setStock(List<StockDiario> stock) {
        this.stock = stock;
    }

    public List<Agendamento> getAgendamentos() {
        List<Agendamento> notvacinados = agendamentos;
        notvacinados.removeIf( obj -> obj.isVacinado());
        return notvacinados;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }

    @Override
    public String toString() {
        return "Centro{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", capacidade_diaria=" + capacidade_diaria +
                ", agendamentos=" + agendamentos +
                ", stock=" + stock +
                '}';
    }
}
