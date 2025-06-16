package com.example.ra3projeto;

import java.io.Serial;
import java.io.Serializable;
import java.util.regex.Pattern;

public class Veiculo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // Padrões regex para validação
    private static final Pattern ANO_PATTERN = Pattern.compile("^(19|20)\\d{2}$");
    private static final Pattern VALOR_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$");

    private String marca;
    private String modelo;
    private String cor;
    private int ano;
    private double valorDiaria;
    private boolean disponivel;
    private Proprietario proprietario; // Relacionamento com Proprietario

    public Veiculo(String marca, String modelo) {
        this.marca = marca;
        this.modelo = modelo;
        this.cor = "Preto";
        this.ano = 2025;
        this.valorDiaria = 100.0;
        this.disponivel = true;
    }

    public Veiculo(String marca, String modelo, String cor, int ano, double valorDiaria) {
        setMarca(marca);
        setModelo(modelo);
        setCor(cor);
        setAno(ano);
        setValorDiaria(valorDiaria);
        this.disponivel = true;
    }

    // Construtor com Proprietario
    public Veiculo(String marca, String modelo, String cor, int ano, double valorDiaria, Proprietario proprietario) {
        setMarca(marca);
        setModelo(modelo);
        setCor(cor);
        setAno(ano);
        setValorDiaria(valorDiaria);
        this.proprietario = proprietario;
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

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setMarca(String marca) {
        validarMarca(marca);
        this.marca = marca.trim();
    }

    public void setModelo(String modelo) {
        validarModelo(modelo);
        this.modelo = modelo.trim();
    }

    public void setCor(String cor) {
        validarCor(cor);
        this.cor = cor.trim();
    }

    public void setAno(int ano) {
        validarAno(ano);
        this.ano = ano;
    }

    public void setValorDiaria(double valorDiaria) {
        validarValorDiaria(valorDiaria);
        this.valorDiaria = valorDiaria;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    @Override
    public String toString() {
        String proprietarioInfo = (proprietario != null) ? " | Proprietário: " + proprietario.getNome() : " | Sem proprietário";
        return marca + " " + modelo + " (" + ano + ") - R$" + String.format("%.2f", valorDiaria) + "/dia" + proprietarioInfo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Veiculo veiculo = (Veiculo) obj;
        return marca.equals(veiculo.marca) && modelo.equals(veiculo.modelo);
    }

    // Métodos de validação privados
    private void validarMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("Marca não pode ser vazia ou nula");
        }
        if (marca.trim().length() < 2) {
            throw new IllegalArgumentException("Marca deve ter pelo menos 2 caracteres");
        }
    }

    private void validarModelo(String modelo) {
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("Modelo não pode ser vazio ou nulo");
        }
        if (modelo.trim().length() < 2) {
            throw new IllegalArgumentException("Modelo deve ter pelo menos 2 caracteres");
        }
    }

    private void validarCor(String cor) {
        if (cor == null || cor.trim().isEmpty()) {
            throw new IllegalArgumentException("Cor não pode ser vazia ou nula");
        }
        if (cor.trim().length() < 3) {
            throw new IllegalArgumentException("Cor deve ter pelo menos 3 caracteres");
        }
    }

    private void validarAno(int ano) {
        int anoAtual = java.time.Year.now().getValue();
        if (ano < 1950) {
            throw new IllegalArgumentException("Ano deve ser maior que 1950");
        }
        if (ano > anoAtual + 1) {
            throw new IllegalArgumentException("Ano não pode ser maior que " + (anoAtual + 1));
        }
    }

    private void validarValorDiaria(double valorDiaria) {
        if (valorDiaria <= 0) {
            throw new IllegalArgumentException("Valor da diária deve ser maior que zero");
        }
        if (valorDiaria > 10000) {
            throw new IllegalArgumentException("Valor da diária não pode ser maior que R$ 10.000");
        }
    }

    // Métodos de validação estáticos para uso em tempo real
    public static boolean isMarcaValida(String marca) {
        return marca != null && !marca.trim().isEmpty() && marca.trim().length() >= 2;
    }

    public static boolean isModeloValido(String modelo) {
        return modelo != null && !modelo.trim().isEmpty() && modelo.trim().length() >= 2;
    }

    public static boolean isCorValida(String cor) {
        return cor != null && !cor.trim().isEmpty() && cor.trim().length() >= 3;
    }

    public static boolean isAnoValido(String anoText) {
        if (anoText == null || anoText.trim().isEmpty()) return false;
        try {
            int ano = Integer.parseInt(anoText.trim());
            int anoAtual = java.time.Year.now().getValue();
            return ano >= 1950 && ano <= anoAtual + 1;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValorDiariaValido(String valorText) {
        if (valorText == null || valorText.trim().isEmpty()) return false;
        try {
            double valor = Double.parseDouble(valorText.trim().replace(",", "."));
            return valor > 0 && valor <= 10000;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Método para formatar valor monetário
    public static String formatarValor(String valor) {
        if (valor == null) return "";
        String valorLimpo = valor.replaceAll("[^0-9,.]", "");
        
        // Substitui vírgula por ponto para validação
        valorLimpo = valorLimpo.replace(",", ".");
        
        try {
            double valorNumerico = Double.parseDouble(valorLimpo);
            return String.format("%.2f", valorNumerico);
        } catch (NumberFormatException e) {
            return valorLimpo;
        }
    }
}