package com.uevora.sdv.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table
public class StockDiario {
    @Id
    @SequenceGenerator(
            name = "stock_diario_sequence",
            sequenceName = "stock_diario_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "stock_diario_sequence"
    )
    private Long id;
    private LocalDate data;
    private int vacinas;

    @ManyToOne
    @JoinColumn(name = "centroID")
    @JsonIgnore
    private Centro centro;

    private String centroName;

    public StockDiario() {
    }

    public StockDiario(LocalDate data, int vacinas, Centro centro) {
        this.data = data;
        this.vacinas = vacinas;
        this.centro = centro;
        this.centroName = centro.getNome();
    }

    public StockDiario(LocalDate data, int vacinas, String centro) {
        this.centroName = centro;
        this.data = data;
        this.vacinas = vacinas;
    }

    public StockDiario(Long id, LocalDate data, int vacinas, Centro centro) {
        this.id = id;
        this.data = data;
        this.vacinas = vacinas;
        this.centroName = centro.getNome();
        this.centro = centro;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getVacinas() {
        return vacinas;
    }

    public void setVacinas(int vacinas) {
        this.vacinas = vacinas;
    }

    public Centro getCentro() {
        return centro;
    }

    public String getCentroName() {
        return centroName;
    }

    public void setCentroName(String centroName) {
        this.centroName = centroName;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    @Override
    public String toString() {
        return "StockDiario{" +
                "id=" + id +
                ", data=" + data +
                ", vacinas=" + vacinas +
                ", centroName='" + centroName + '\'' +
                '}';
    }
}

