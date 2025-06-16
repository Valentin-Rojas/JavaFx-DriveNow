package com.example.ra3projeto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoRepository {
    private static final String ARQUIVO = "veiculos.dat";

    public static void salvarLista(List<Veiculo> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Veiculo> carregarLista() {
        File file = new File(ARQUIVO);
        // System.out.println("Existe veiculos.dat? " + file.exists());

        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Veiculo>) ois.readObject();
        } catch (InvalidClassException e) {
            // System.out.println("Arquivo incompatível. Deletando veiculos.dat...");
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

    // Filtrar veículos por proprietário
    public static List<Veiculo> carregarVeiculosPorProprietario(Proprietario proprietario) {
        List<Veiculo> todosVeiculos = carregarLista();
        return todosVeiculos.stream()
                .filter(v -> v.getProprietario() != null && 
                           v.getProprietario().getCpf().equals(proprietario.getCpf()))
                .collect(java.util.stream.Collectors.toList());
    }
}