package com.example.ra3projeto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaRepository {
    private static final String ARQUIVO = "reservas.ser";

    public static void salvarLista(List<Reserva> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Reserva> carregarLista() {
        File file = new File(ARQUIVO);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Reserva>) ois.readObject();
        } catch (InvalidClassException e) {
            System.out.println("Arquivo incompatível. Deletando reservas.ser...");
            file.delete();
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void adicionarReserva(Reserva novaReserva) {
        List<Reserva> reservas = carregarLista();
        reservas.add(novaReserva);
        salvarLista(reservas);
    }
}
