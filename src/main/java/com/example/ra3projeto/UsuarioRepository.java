package com.example.ra3projeto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private static final String ARQUIVO = "usuarios.dat";

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
            // System.out.println("Arquivo incompatível. Deletando usuarios.dat...");
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

    // Verificar se CPF já existe
    public static boolean cpfJaExiste(String cpf) {
        List<Usuario> usuarios = carregarLista();
        return usuarios.stream().anyMatch(u -> u.getCpf().equals(cpf));
    }

    // Verificar se telefone já existe
    public static boolean telefoneJaExiste(String telefone) {
        List<Usuario> usuarios = carregarLista();
        return usuarios.stream().anyMatch(u -> u.getTelefone().equals(telefone));
    }

    // Verificar se CPF já existe (exceto para um usuário específico - para edição)
    public static boolean cpfJaExiste(String cpf, Usuario usuarioAtual) {
        List<Usuario> usuarios = carregarLista();
        return usuarios.stream().anyMatch(u -> u.getCpf().equals(cpf) && !u.equals(usuarioAtual));
    }

    // Verificar se telefone já existe (exceto para um usuário específico - para edição)
    public static boolean telefoneJaExiste(String telefone, Usuario usuarioAtual) {
        List<Usuario> usuarios = carregarLista();
        return usuarios.stream().anyMatch(u -> u.getTelefone().equals(telefone) && !u.equals(usuarioAtual));
    }
}
