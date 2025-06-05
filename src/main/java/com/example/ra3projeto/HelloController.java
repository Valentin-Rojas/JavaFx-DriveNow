package com.example.ra3projeto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

public class HelloController {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabCadastroVeiculos;

    @FXML
    private Tab tabEditarVeiculos;

    // Referências das abas dos outros CRUDs
    @FXML
    private Tab tabCadastroUsuarios;

    @FXML
    private Tab tabGerenciarUsuarios;

    @FXML
    private Tab tabCadastroProprietarios;

    @FXML
    private Tab tabGerenciarProprietarios;

    @FXML
    private Tab tabCadastroReservas;

    @FXML
    private Tab tabGerenciarReservas;

    @FXML
    private TextField marcaField;

    @FXML
    private TextField modeloField;

    @FXML
    private TextField corField;

    @FXML
    private TextField anoField;

    @FXML
    private TextField valorDiariaField;

    @FXML
    private ListView<Veiculo> listaVeiculos;

    // Campos para Usuario
    @FXML
    private TextField nomeUsuarioField;

    @FXML
    private TextField emailUsuarioField;

    @FXML
    private TextField telefoneUsuarioField;

    @FXML
    private TextField cpfUsuarioField;

    @FXML
    private ListView<Usuario> listaUsuarios;

    // Campos para Proprietario
    @FXML
    private TextField nomeProprietarioField;

    @FXML
    private TextField emailProprietarioField;

    @FXML
    private TextField telefoneProprietarioField;

    @FXML
    private TextField cpfProprietarioField;

    @FXML
    private TextField enderecoField;

    @FXML
    private ListView<Proprietario> listaProprietarios;

    // Campos para Reserva
    @FXML
    private TextField codigoReservaField;

    @FXML
    private TextField cpfUsuarioReservaField;

    @FXML
    private TextField veiculoInfoField;

    @FXML
    private TextField valorTotalField;

    @FXML
    private ListView<Reserva> listaReservas;

    private Veiculo veiculoEditando = null;
    private Usuario usuarioEditando = null;
    private Proprietario proprietarioEditando = null;
    private Reserva reservaEditando = null;

    @FXML
    public void initialize() {
        carregarLista();
        carregarListaUsuarios();
        carregarListaProprietarios();
        carregarListaReservas();
    }

    @FXML
    private void salvarVeiculo() {
        String marca = marcaField.getText();
        String modelo = modeloField.getText();
        String cor = corField.getText();
        String anoText = anoField.getText();
        String valorText = valorDiariaField.getText();

        if (!marca.isEmpty() && !modelo.isEmpty()) {
            try {
                int ano = anoText.isEmpty() ? 2024 : Integer.parseInt(anoText);
                double valor = valorText.isEmpty() ? 100.0 : Double.parseDouble(valorText);
                
                List<Veiculo> veiculos = VeiculoRepository.carregarLista();

                if (veiculoEditando != null) {
                    for (Veiculo v : veiculos) {
                        if (v.equals(veiculoEditando)) {
                            v.setMarca(marca);
                            v.setModelo(modelo);
                            v.setCor(cor);
                            v.setAno(ano);
                            v.setValorDiaria(valor);
                            break;
                        }
                    }
                    veiculoEditando = null;
                    tabPane.getSelectionModel().select(tabEditarVeiculos);
                } else {
                    Veiculo novo = new Veiculo(marca, modelo, cor, ano, valor);
                    veiculos.add(novo);
                }

                VeiculoRepository.salvarLista(veiculos);
                limparCamposVeiculo();
                carregarLista();
            } catch (NumberFormatException e) {
                System.out.println("Ano e valor devem ser números válidos.");
            }
        } else {
            System.out.println("Preencha pelo menos marca e modelo.");
        }
    }

    private void limparCamposVeiculo() {
        marcaField.clear();
        modeloField.clear();
        corField.clear();
        anoField.clear();
        valorDiariaField.clear();
    }

    private void carregarLista() {
        ObservableList<Veiculo> veiculos = FXCollections.observableArrayList(
                VeiculoRepository.carregarLista()
        );
        listaVeiculos.setItems(veiculos);
        configurarListaComBotao();
    }

    private void configurarListaComBotao() {
        listaVeiculos.setCellFactory(lv -> new ListCell<Veiculo>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnRemover = new Button("Remover");
            private final HBox botoes = new HBox(5, btnEditar, btnRemover);
            private final BorderPane conteudo = new BorderPane();

            {
                btnEditar.setOnAction(e -> {
                    Veiculo veiculo = getItem();
                    if (veiculo != null) {
                        editarVeiculo(veiculo);
                    }
                });

                btnRemover.setOnAction(e -> {
                    Veiculo veiculo = getItem();
                    if (veiculo != null) {
                        List<Veiculo> lista = VeiculoRepository.carregarLista();
                        lista.remove(veiculo);
                        VeiculoRepository.salvarLista(lista);
                        carregarLista();
                    }
                });

                botoes.setSpacing(5);
                conteudo.setRight(botoes);
            }

            @Override
            protected void updateItem(Veiculo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    conteudo.setLeft(new Label(item.toString()));
                    setGraphic(conteudo);
                }
            }
        });
    }

    private void editarVeiculo(Veiculo veiculo) {
        marcaField.setText(veiculo.getMarca());
        modeloField.setText(veiculo.getModelo());
        corField.setText(veiculo.getCor());
        anoField.setText(String.valueOf(veiculo.getAno()));
        valorDiariaField.setText(String.valueOf(veiculo.getValorDiaria()));
        veiculoEditando = veiculo;
        tabPane.getSelectionModel().select(tabCadastroVeiculos);
        System.out.println("Editando: " + veiculo);
    }

    // ===== MÉTODOS PARA USUARIO =====
    @FXML
    private void salvarUsuario() {
        String nome = nomeUsuarioField.getText();
        String email = emailUsuarioField.getText();
        String telefone = telefoneUsuarioField.getText();
        String cpf = cpfUsuarioField.getText();

        if (!nome.isEmpty() && !email.isEmpty() && !cpf.isEmpty()) {
            List<Usuario> usuarios = UsuarioRepository.carregarLista();

            if (usuarioEditando != null) {
                for (Usuario u : usuarios) {
                    if (u.equals(usuarioEditando)) {
                        u.setNome(nome);
                        u.setEmail(email);
                        u.setTelefone(telefone);
                        u.setCpf(cpf);
                        break;
                    }
                }
                usuarioEditando = null;
                tabPane.getSelectionModel().select(tabGerenciarUsuarios);
            } else {
                Usuario novo = new Usuario(nome, email, telefone, cpf);
                usuarios.add(novo);
            }

            UsuarioRepository.salvarLista(usuarios);
            limparCamposUsuario();
            carregarListaUsuarios();
        } else {
            System.out.println("Preencha nome, email e CPF.");
        }
    }

    private void limparCamposUsuario() {
        nomeUsuarioField.clear();
        emailUsuarioField.clear();
        telefoneUsuarioField.clear();
        cpfUsuarioField.clear();
    }

    private void carregarListaUsuarios() {
        if (listaUsuarios != null) {
            ObservableList<Usuario> usuarios = FXCollections.observableArrayList(
                    UsuarioRepository.carregarLista()
            );
            listaUsuarios.setItems(usuarios);
            configurarListaUsuariosComBotao();
        }
    }

    private void configurarListaUsuariosComBotao() {
        listaUsuarios.setCellFactory(lv -> new ListCell<Usuario>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnRemover = new Button("Remover");
            private final HBox botoes = new HBox(5, btnEditar, btnRemover);
            private final BorderPane conteudo = new BorderPane();

            {
                btnEditar.setOnAction(e -> {
                    Usuario usuario = getItem();
                    if (usuario != null) {
                        editarUsuario(usuario);
                    }
                });

                btnRemover.setOnAction(e -> {
                    Usuario usuario = getItem();
                    if (usuario != null) {
                        List<Usuario> lista = UsuarioRepository.carregarLista();
                        lista.remove(usuario);
                        UsuarioRepository.salvarLista(lista);
                        carregarListaUsuarios();
                    }
                });

                botoes.setSpacing(5);
                conteudo.setRight(botoes);
            }

            @Override
            protected void updateItem(Usuario item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    conteudo.setLeft(new Label(item.toString()));
                    setGraphic(conteudo);
                }
            }
        });
    }

    private void editarUsuario(Usuario usuario) {
        nomeUsuarioField.setText(usuario.getNome());
        emailUsuarioField.setText(usuario.getEmail());
        telefoneUsuarioField.setText(usuario.getTelefone());
        cpfUsuarioField.setText(usuario.getCpf());
        usuarioEditando = usuario;
        tabPane.getSelectionModel().select(tabCadastroUsuarios);
        System.out.println("Editando: " + usuario);
    }

    // ===== MÉTODOS PARA PROPRIETARIO =====
    @FXML
    private void salvarProprietario() {
        String nome = nomeProprietarioField.getText();
        String email = emailProprietarioField.getText();
        String telefone = telefoneProprietarioField.getText();
        String cpf = cpfProprietarioField.getText();
        String endereco = enderecoField.getText();

        if (!nome.isEmpty() && !email.isEmpty() && !cpf.isEmpty()) {
            List<Proprietario> proprietarios = ProprietarioRepository.carregarLista();

            if (proprietarioEditando != null) {
                for (Proprietario p : proprietarios) {
                    if (p.equals(proprietarioEditando)) {
                        p.setNome(nome);
                        p.setEmail(email);
                        p.setTelefone(telefone);
                        p.setCpf(cpf);
                        p.setEndereco(endereco);
                        break;
                    }
                }
                proprietarioEditando = null;
                tabPane.getSelectionModel().select(tabGerenciarProprietarios);
            } else {
                Proprietario novo = new Proprietario(nome, email, telefone, cpf, endereco);
                proprietarios.add(novo);
            }

            ProprietarioRepository.salvarLista(proprietarios);
            limparCamposProprietario();
            carregarListaProprietarios();
        } else {
            System.out.println("Preencha nome, email e CPF.");
        }
    }

    private void limparCamposProprietario() {
        nomeProprietarioField.clear();
        emailProprietarioField.clear();
        telefoneProprietarioField.clear();
        cpfProprietarioField.clear();
        enderecoField.clear();
    }

    private void carregarListaProprietarios() {
        if (listaProprietarios != null) {
            ObservableList<Proprietario> proprietarios = FXCollections.observableArrayList(
                    ProprietarioRepository.carregarLista()
            );
            listaProprietarios.setItems(proprietarios);
            configurarListaProprietariosComBotao();
        }
    }

    private void configurarListaProprietariosComBotao() {
        listaProprietarios.setCellFactory(lv -> new ListCell<Proprietario>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnRemover = new Button("Remover");
            private final HBox botoes = new HBox(5, btnEditar, btnRemover);
            private final BorderPane conteudo = new BorderPane();

            {
                btnEditar.setOnAction(e -> {
                    Proprietario proprietario = getItem();
                    if (proprietario != null) {
                        editarProprietario(proprietario);
                    }
                });

                btnRemover.setOnAction(e -> {
                    Proprietario proprietario = getItem();
                    if (proprietario != null) {
                        List<Proprietario> lista = ProprietarioRepository.carregarLista();
                        lista.remove(proprietario);
                        ProprietarioRepository.salvarLista(lista);
                        carregarListaProprietarios();
                    }
                });

                botoes.setSpacing(5);
                conteudo.setRight(botoes);
            }

            @Override
            protected void updateItem(Proprietario item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    conteudo.setLeft(new Label(item.toString()));
                    setGraphic(conteudo);
                }
            }
        });
    }

    private void editarProprietario(Proprietario proprietario) {
        nomeProprietarioField.setText(proprietario.getNome());
        emailProprietarioField.setText(proprietario.getEmail());
        telefoneProprietarioField.setText(proprietario.getTelefone());
        cpfProprietarioField.setText(proprietario.getCpf());
        enderecoField.setText(proprietario.getEndereco());
        proprietarioEditando = proprietario;
        tabPane.getSelectionModel().select(tabCadastroProprietarios);
        System.out.println("Editando: " + proprietario);
    }

    // ===== MÉTODOS PARA RESERVA =====
    @FXML
    private void salvarReserva() {
        String codigo = codigoReservaField.getText();
        String cpfUsuario = cpfUsuarioReservaField.getText();
        String veiculoInfo = veiculoInfoField.getText();
        String valorText = valorTotalField.getText();

        if (!codigo.isEmpty() && !cpfUsuario.isEmpty() && !veiculoInfo.isEmpty()) {
            try {
                double valor = valorText.isEmpty() ? 0.0 : Double.parseDouble(valorText);
                List<Reserva> reservas = ReservaRepository.carregarLista();

                if (reservaEditando != null) {
                    for (Reserva r : reservas) {
                        if (r.equals(reservaEditando)) {
                            r.setCodigoReserva(codigo);
                            r.setCpfUsuario(cpfUsuario);
                            r.setVeiculoInfo(veiculoInfo);
                            r.setValorTotal(valor);
                            break;
                        }
                    }
                    reservaEditando = null;
                    tabPane.getSelectionModel().select(tabGerenciarReservas);
                } else {
                    Reserva nova = new Reserva(codigo, cpfUsuario, veiculoInfo, 
                                             java.time.LocalDate.now(), 
                                             java.time.LocalDate.now().plusDays(1), 
                                             valor);
                    reservas.add(nova);
                }

                ReservaRepository.salvarLista(reservas);
                limparCamposReserva();
                carregarListaReservas();
            } catch (NumberFormatException e) {
                System.out.println("Valor total deve ser um número válido.");
            }
        } else {
            System.out.println("Preencha código, CPF do usuário e veículo.");
        }
    }

    private void limparCamposReserva() {
        codigoReservaField.clear();
        cpfUsuarioReservaField.clear();
        veiculoInfoField.clear();
        valorTotalField.clear();
    }

    private void carregarListaReservas() {
        if (listaReservas != null) {
            ObservableList<Reserva> reservas = FXCollections.observableArrayList(
                    ReservaRepository.carregarLista()
            );
            listaReservas.setItems(reservas);
            configurarListaReservasComBotao();
        }
    }

    private void configurarListaReservasComBotao() {
        listaReservas.setCellFactory(lv -> new ListCell<Reserva>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnRemover = new Button("Remover");
            private final HBox botoes = new HBox(5, btnEditar, btnRemover);
            private final BorderPane conteudo = new BorderPane();

            {
                btnEditar.setOnAction(e -> {
                    Reserva reserva = getItem();
                    if (reserva != null) {
                        editarReserva(reserva);
                    }
                });

                btnRemover.setOnAction(e -> {
                    Reserva reserva = getItem();
                    if (reserva != null) {
                        List<Reserva> lista = ReservaRepository.carregarLista();
                        lista.remove(reserva);
                        ReservaRepository.salvarLista(lista);
                        carregarListaReservas();
                    }
                });

                botoes.setSpacing(5);
                conteudo.setRight(botoes);
            }

            @Override
            protected void updateItem(Reserva item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    conteudo.setLeft(new Label(item.toString()));
                    setGraphic(conteudo);
                }
            }
        });
    }

    private void editarReserva(Reserva reserva) {
        codigoReservaField.setText(reserva.getCodigoReserva());
        cpfUsuarioReservaField.setText(reserva.getCpfUsuario());
        veiculoInfoField.setText(reserva.getVeiculoInfo());
        valorTotalField.setText(String.valueOf(reserva.getValorTotal()));
        reservaEditando = reserva;
        tabPane.getSelectionModel().select(tabCadastroReservas);
        System.out.println("Editando: " + reserva);
    }
}