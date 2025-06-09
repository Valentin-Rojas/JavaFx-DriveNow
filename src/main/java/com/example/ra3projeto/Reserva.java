package com.example.ra3projeto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class Reserva implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // Padrões regex para validação
    private static final Pattern CPF_PATTERN = 
        Pattern.compile("^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$");

    private String codigoReserva;
    private String cpfUsuario;
    private String veiculoInfo; // marca + modelo
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private double valorTotal;
    private String status; // "Ativa", "Finalizada", "Cancelada"

    public Reserva(String codigoReserva, String cpfUsuario, String veiculoInfo, LocalDate dataInicio, LocalDate dataFim, double valorTotal) {
        this.codigoReserva = codigoReserva;
        this.cpfUsuario = cpfUsuario;
        this.veiculoInfo = veiculoInfo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorTotal = valorTotal;
        this.status = "Ativa";
    }

    public String getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    public void setCpfUsuario(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }

    public String getVeiculoInfo() {
        return veiculoInfo;
    }

    public void setVeiculoInfo(String veiculoInfo) {
        this.veiculoInfo = veiculoInfo;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return codigoReserva + " - " + veiculoInfo + " (" + status + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reserva reserva = (Reserva) obj;
        return codigoReserva.equals(reserva.codigoReserva);
    }

    // Métodos de validação estáticos para uso em tempo real
    public static boolean isCodigoValido(String codigo) {
        return codigo != null && !codigo.trim().isEmpty() && codigo.trim().length() >= 3;
    }

    public static boolean isCpfUsuarioValido(String cpfUsuario) {
        if (cpfUsuario == null || cpfUsuario.trim().isEmpty()) return false;
        return CPF_PATTERN.matcher(cpfUsuario.trim()).matches();
    }

    public static boolean isVeiculoInfoValido(String veiculoInfo) {
        return veiculoInfo != null && !veiculoInfo.trim().isEmpty() && veiculoInfo.trim().length() >= 3;
    }

    public static boolean isValorTotalValido(String valorText) {
        if (valorText == null || valorText.trim().isEmpty()) return false;
        try {
            double valor = Double.parseDouble(valorText.trim().replace(",", "."));
            return valor >= 0 && valor <= 100000;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
