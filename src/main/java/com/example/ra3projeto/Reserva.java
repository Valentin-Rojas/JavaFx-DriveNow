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
    private int diasAluguel; // Número de dias do aluguel
    private double valorTotal;
    private String status; // "Ativa", "Finalizada", "Cancelada"
    private Usuario usuario; // Relacionamento com Usuario
    private Veiculo veiculo; // Relacionamento com Veiculo

    public Reserva(String codigoReserva, String cpfUsuario, String veiculoInfo, LocalDate dataInicio, LocalDate dataFim, double valorTotal) {
        this.codigoReserva = codigoReserva;
        this.cpfUsuario = cpfUsuario;
        this.veiculoInfo = veiculoInfo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.diasAluguel = calcularDias(dataInicio, dataFim);
        this.valorTotal = valorTotal;
        this.status = "Ativa";
    }

    // Construtor com relacionamentos
    public Reserva(String codigoReserva, Usuario usuario, Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim, int diasAluguel) {
        this.codigoReserva = codigoReserva;
        this.usuario = usuario;
        this.veiculo = veiculo;
        this.cpfUsuario = usuario.getCpf(); // Manter compatibilidade
        this.veiculoInfo = veiculo.getMarca() + " " + veiculo.getModelo(); // Manter compatibilidade
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.diasAluguel = diasAluguel;
        this.valorTotal = diasAluguel * veiculo.getValorDiaria(); // Cálculo automático
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

    public int getDiasAluguel() {
        return diasAluguel;
    }

    public void setDiasAluguel(int diasAluguel) {
        this.diasAluguel = diasAluguel;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    @Override
    public String toString() {
        String usuarioInfo = (usuario != null) ? usuario.getNome() : cpfUsuario;
        String veiculoInfo = (veiculo != null) ? veiculo.getMarca() + " " + veiculo.getModelo() : this.veiculoInfo;
        return codigoReserva + " | " + usuarioInfo + " | " + veiculoInfo + " | " + diasAluguel + " dias | R$" + String.format("%.2f", valorTotal) + " (" + status + ")";
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

    // Método estático para validar dias de aluguel
    public static boolean isDiasAluguelValido(String diasText) {
        if (diasText == null || diasText.trim().isEmpty()) return false;
        try {
            int dias = Integer.parseInt(diasText.trim());
            return dias > 0 && dias <= 365; // Máximo 1 ano
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Método para calcular dias entre datas
    private int calcularDias(LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio == null || dataFim == null) return 1;
        long dias = java.time.temporal.ChronoUnit.DAYS.between(dataInicio, dataFim);
        return dias > 0 ? (int) dias : 1; // Mínimo 1 dia
    }

    // Método estático para gerar código de reserva automaticamente
    public static String gerarCodigoReserva() {
        java.time.LocalDateTime agora = java.time.LocalDateTime.now();
        return "RES" + agora.format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    // Método para recalcular valor total baseado nos dias e valor diário do veículo
    public void recalcularValorTotal() {
        if (veiculo != null && diasAluguel > 0) {
            this.valorTotal = diasAluguel * veiculo.getValorDiaria();
        }
    }
}
