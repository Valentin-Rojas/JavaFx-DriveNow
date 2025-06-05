package com.example.ra3projeto;

import java.io.Serial;
import java.io.Serializable;

public class Proprietario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String endereco;

    public Proprietario(String nome, String email, String telefone, String cpf, String endereco) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return nome + " - " + email + " (" + endereco + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Proprietario proprietario = (Proprietario) obj;
        return cpf.equals(proprietario.cpf);
    }
}
