package com.example.ra3projeto;

import java.io.Serial;
import java.io.Serializable;
import java.util.regex.Pattern;

public class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // Padrões regex para validação
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern TELEFONE_PATTERN = 
        Pattern.compile("^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$");
    private static final Pattern CPF_PATTERN = 
        Pattern.compile("^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$");

    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    public Usuario(String nome, String email, String telefone, String cpf) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
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

    @Override
    public String toString() {
        return nome + " - " + email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return cpf.equals(usuario.cpf);
    }

    // Métodos de validação estáticos para uso em tempo real
    public static boolean isNomeValido(String nome) {
        return nome != null && !nome.trim().isEmpty() && nome.trim().length() >= 2;
    }

    public static boolean isEmailValido(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isTelefoneValido(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) return false;
        return TELEFONE_PATTERN.matcher(telefone.trim()).matches();
    }

    public static boolean isCpfValido(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) return false;
        if (!CPF_PATTERN.matcher(cpf.trim()).matches()) return false;
        
        String cpfClean = cpf.replaceAll("[.-]", "");
        return isValidCpfDigits(cpfClean);
    }

    private static boolean isValidCpfDigits(String cpf) {
        if (cpf.length() != 11) return false;
        
        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) return false;
        
        // Calcula primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int primeiro = (soma * 10) % 11;
        if (primeiro == 10) primeiro = 0;
        
        // Calcula segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int segundo = (soma * 10) % 11;
        if (segundo == 10) segundo = 0;
        
        return primeiro == Character.getNumericValue(cpf.charAt(9)) && segundo == Character.getNumericValue(cpf.charAt(10));
    }
}
