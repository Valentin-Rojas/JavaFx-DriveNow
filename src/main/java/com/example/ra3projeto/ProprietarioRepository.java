package com.example.ra3projeto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioRepository {
    private static final String ARQUIVO = "proprietarios.ser";

    public static void salvarLista(List<Proprietario> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Proprietario> carregarLista() {
        File file = new File(ARQUIVO);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Proprietario>) ois.readObject();
        } catch (InvalidClassException e) {
            System.out.println("Arquivo incompatível. Deletando proprietarios.ser...");
            file.delete();
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void adicionarProprietario(Proprietario novoProprietario) {
        List<Proprietario> proprietarios = carregarLista();
        proprietarios.add(novoProprietario);
        salvarLista(proprietarios);
    }
}
