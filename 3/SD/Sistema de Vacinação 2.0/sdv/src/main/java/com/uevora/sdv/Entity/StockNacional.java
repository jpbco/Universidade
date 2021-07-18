package com.uevora.sdv.Entity;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table
public class StockNacional {
    @Id
    @SequenceGenerator(
            name = "stock_nacional_sequence",
            sequenceName = "stock_nacional_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "stock_nacional_sequence"
    )
    private Long id;
    private LocalDate data;
    private int vacinas;

    public StockNacional() {
    }

    public StockNacional(LocalDate data, int vacinas) {
        this.data = data;
        this.vacinas = vacinas;
    }

    public StockNacional(Long id, LocalDate data, int vacinas) {
        this.id = id;
        this.data = data;
        this.vacinas = vacinas;
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

    @Override
    public String toString() {
        return "StockNacional{" +
                "id=" + id +
                ", data=" + data +
                ", vacinas=" + vacinas +
                '}';
    }
}

