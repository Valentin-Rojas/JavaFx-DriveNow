package com.example.ra3projeto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private static final String ARQUIVO = "usuarios.ser";

    public static void salvarLista(List<Usuario> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Usuario> carregarLista() {
        File file = new File(ARQUIVO);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Usuario>) ois.readObject();
        } catch (InvalidClassException e) {
            System.out.println("Arquivo incompatível. Deletando usuarios.ser...");
            file.delete();
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void adicionarUsuario(Usuario novoUsuario) {
        List<Usuario> usuarios = carregarLista();
        usuarios.add(novoUsuario);
        salvarLista(usuarios);
    }
}
