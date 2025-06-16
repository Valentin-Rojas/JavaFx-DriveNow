package com.example.ra3projeto;

import java.io.Serial;
import java.io.Serializable;
import java.util.regex.Pattern;

public class Proprietario implements Serializable {
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
    private String endereco;

    public Proprietario(String nome, String email, String telefone, String cpf, String endereco) {
        setNome(nome);
        setEmail(email);
        setTelefone(telefone);
        setCpf(cpf);
        setEndereco(endereco);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        validarNome(nome);
        this.nome = nome.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validarEmail(email);
        this.email = email.trim().toLowerCase();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        validarTelefone(telefone);
        this.telefone = telefone.trim();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        validarCpf(cpf);
        this.cpf = cpf.trim();
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        validarEndereco(endereco);
        this.endereco = endereco.trim();
    }

    @Override
    public String toString() {
        return nome + " - " + email + " | CPF: " + cpf + " | Tel: " + telefone + " | " + endereco;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Proprietario proprietario = (Proprietario) obj;
        return cpf.equals(proprietario.cpf);
    }

    // Métodos de validação
    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio ou nulo");
        }
        if (nome.trim().length() < 2) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 2 caracteres");
        }
    }

    private void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio ou nulo");
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException("Email inválido. Use o formato: exemplo@dominio.com");
        }
    }

    private void validarTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio ou nulo");
        }
        String telefoneClean = telefone.replaceAll("[\\s()-]", "");
        if (!TELEFONE_PATTERN.matcher(telefone.trim()).matches()) {
            throw new IllegalArgumentException("Telefone inválido. Use o formato: (11) 99999-9999 ou 11999999999");
        }
    }

    private void validarCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio ou nulo");
        }
        String cpfClean = cpf.replaceAll("[.-]", "");
        if (!CPF_PATTERN.matcher(cpf.trim()).matches()) {
            throw new IllegalArgumentException("CPF inválido. Use o formato: 000.000.000-00 ou 00000000000");
        }
        if (!isValidCpf(cpfClean)) {
            throw new IllegalArgumentException("CPF inválido (dígitos verificadores incorretos)");
        }
    }

    private void validarEndereco(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço não pode ser vazio ou nulo");
        }
        if (endereco.trim().length() < 5) {
            throw new IllegalArgumentException("Endereço deve ter pelo menos 5 caracteres");
        }
    }

    // Validação completa do CPF com dígitos verificadores
    private boolean isValidCpf(String cpf) {
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
        return isValidCpfStatic(cpfClean);
    }

    public static boolean isEnderecoValido(String endereco) {
        return endereco != null && !endereco.trim().isEmpty() && endereco.trim().length() >= 5;
    }

    private static boolean isValidCpfStatic(String cpf) {
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
