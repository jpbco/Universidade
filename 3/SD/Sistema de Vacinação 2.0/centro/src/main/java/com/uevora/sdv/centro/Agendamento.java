package com.uevora.sdv.centro;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Agendamento {

    private Long id;


    private boolean vacinado;

    private String cc;
    private String name;
    private int idade;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;
    private Centro centro;

    private String centroName;

    public Agendamento() {
    }

    public Agendamento(String name, int idade, String email, String cc, LocalDate data, String centroName) {
        this.centroName = centroName;
        this.cc = cc;
        this.name = name;
        this.idade = idade;
        this.email = email;
        this.data = data;
        this.vacinado = false;
    }

    public Agendamento(String name, int idade, String email, String cc, LocalDate data, Centro centro) {
        this.centro = centro;
        this.centroName = centro.getNome();
        this.cc = cc;
        this.name = name;
        this.idade = idade;
        this.email = email;
        this.data = data;
        this.vacinado = false;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isVacinado() {
        return vacinado;
    }

    public void setVacinado(boolean vacinado) {
        this.vacinado = vacinado;
    }

    public String getCentroName() {
        return centroName;
    }

    public void setCentroName(String centroName) {
        this.centroName = centroName;
    }

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    @Override
    public String toString() {
        return "Agendamento{" +
                "id=" + id +
                ", vacinado=" + vacinado +
                ", cc='" + cc + '\'' +
                ", name='" + name + '\'' +
                ", idade=" + idade +
                ", email='" + email + '\'' +
                ", data=" + data +

                ", centroName='" + centroName + '\'' +
                '}';
    }

}