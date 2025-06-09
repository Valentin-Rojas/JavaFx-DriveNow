package com.example.ra3projeto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoRepository {
    private static final String ARQUIVO = "veiculos.ser";

    public static void salvarLista(List<Veiculo> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Veiculo> carregarLista() {
        File file = new File(ARQUIVO);
        // System.out.println("Existe veiculos.ser? " + file.exists());

        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Veiculo>) ois.readObject();
        } catch (InvalidClassException e) {
            // System.out.println("Arquivo incompatível. Deletando veiculos.ser...");
            file.delete();
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void adicionarVeiculo(Veiculo novoVeiculo) {
        List<Veiculo> veiculos = carregarLista();
        veiculos.add(novoVeiculo);
        salvarLista(veiculos);
    }
}