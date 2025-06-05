package com.example.ra3projeto;

import java.io.Serial;
import java.io.Serializable;

public class Veiculo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String marca;
    private String modelo;
    private String cor;
    private int ano;
    private double valorDiaria;
    private boolean disponivel;

    public Veiculo(String marca, String modelo) {
        this.marca = marca;
        this.modelo = modelo;
        this.cor = "";
        this.ano = 2024;
        this.valorDiaria = 100.0;
        this.disponivel = true;
    }

    public Veiculo(String marca, String modelo, String cor, int ano, double valorDiaria) {
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.ano = ano;
        this.valorDiaria = valorDiaria;
        this.disponivel = true;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getCor() {
        return cor;
    }

    public int getAno() {
        return ano;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return marca + " " + modelo + " (" + ano + ") - R$" + valorDiaria + "/dia";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Veiculo veiculo = (Veiculo) obj;
        return marca.equals(veiculo.marca) && modelo.equals(veiculo.modelo);
    }
}