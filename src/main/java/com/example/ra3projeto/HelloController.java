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
        
        // Configurar validação em tempo real
        configurarValidacaoVeiculo();
        configurarValidacaoUsuario();
        configurarValidacaoProprietario();
        configurarValidacaoReserva();
    }

    private void configurarValidacaoVeiculo() {
        // Validação da marca
        marcaField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Veiculo.isMarcaValida(newValue)) {
                marcaField.setStyle("-fx-border-color: none;");
            } else {
                marcaField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
        });

        // Validação do modelo
        modeloField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Veiculo.isModeloValido(newValue)) {
                modeloField.setStyle("-fx-border-color: none;");
            } else {
                modeloField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
        });

        // Validação da cor
        corField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Veiculo.isCorValida(newValue)) {
                corField.setStyle("-fx-border-color: none;");
            } else {
                corField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
        });

        // Validação do ano
        anoField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Permitir apenas números
            if (!newValue.matches("\\d*")) {
                anoField.setText(oldValue);
                return;
            }
            
            if (Veiculo.isAnoValido(newValue)) {
                anoField.setStyle("-fx-border-color: none;");
            } else {
                anoField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
        });

        // Validação e formatação do valor da diária
        valorDiariaField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Permitir apenas números, vírgula e ponto
            if (!newValue.matches("[0-9]*[,.]?[0-9]*")) {
                valorDiariaField.setText(oldValue);
                return;
            }
            
            if (Veiculo.isValorDiariaValido(newValue)) {
                valorDiariaField.setStyle("-fx-border-color: none;");
            } else {
                valorDiariaField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
        });
    }

    @FXML
    private void salvarVeiculo() {
        String marca = marcaField.getText();
        String modelo = modeloField.getText();
        String cor = corField.getText();
        String anoText = anoField.getText();
        String valorText = valorDiariaField.getText();

        // Validar todos os campos antes de salvar
        if (!Veiculo.isMarcaValida(marca)) {
            mostrarAlerta("Marca inválida", "Marca deve ter pelo menos 2 caracteres");
            return;
        }
        
        if (!Veiculo.isModeloValido(modelo)) {
            mostrarAlerta("Modelo inválido", "Modelo deve ter pelo menos 2 caracteres");
            return;
        }
        
        if (!Veiculo.isCorValida(cor)) {
            mostrarAlerta("Cor inválida", "Cor deve ter pelo menos 3 caracteres");
            return;
        }
        
        if (!Veiculo.isAnoValido(anoText)) {
            mostrarAlerta("Ano inválido", "Ano deve estar entre 1950 e " + (java.time.Year.now().getValue() + 1));
            return;
        }
        
        if (!Veiculo.isValorDiariaValido(valorText)) {
            mostrarAlerta("Valor inválido", "Valor da diária deve ser maior que zero e menor que R$ 10.000");
            return;
        }

        try {
            int ano = Integer.parseInt(anoText);
            double valor = Double.parseDouble(valorText.replace(",", "."));
            
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
            mostrarSucesso("Veículo salvo com sucesso!");
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao salvar veículo: " + e.getMessage());
        }
    }
    
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    
    private void mostrarSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
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

    private void configurarValidacaoUsuario() {
        // Validação do nome do usuário
        nomeUsuarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Usuario.isNomeValido(newValue)) {
                nomeUsuarioField.setStyle("-fx-border-color: none;");
            } else {
                nomeUsuarioField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
        });

        // Validação do email do usuário
        emailUsuarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Usuario.isEmailValido(newValue)) {
                emailUsuarioField.setStyle("-fx-border-color: none;");
            } else {
                emailUsuarioField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
        });

        // Validação do telefone do usuário
        telefoneUsuarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Permitir apenas números, parênteses, espaços e hífen
            if (!newValue.matches("[0-9()\\s-]*")) {
                telefoneUsuarioField.setText(oldValue);
                return;
            }
            
            if (Usuario.isTelefoneValido(newValue)) {
                telefoneUsuarioField.setStyle("-fx-border-color: none;");
            } else {
                telefoneUsuarioField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
        });

        // Validação do CPF do usuário
        cpfUsuarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Permitir apenas números, pontos e hífen
            if (!newValue.matches("[0-9.-]*")) {
                cpfUsuarioField.setText(oldValue);
                return;
            }
            
            if (Usuario.isCpfValido(newValue)) {
                cpfUsuarioField.setStyle("-fx-border-color: none;");
            } else {
                cpfUsuarioField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
        });
    }

    private void configurarValidacaoProprietario() {
        // Validação do nome do proprietário
        nomeProprietarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Proprietario.isNomeValido(newValue)) {
                nomeProprietarioField.setStyle("-fx-border-color: none;");
            } else {
                nomeProprietarioField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
        });

        // Validação do email do proprietário
        emailProprietarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Proprietario.isEmailValido(newValue)) {
                emailProprietarioField.setStyle("-fx-border-color: none;");
            } else {
                emailProprietarioField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
        });

        // Validação do telefone do proprietário
        telefoneProprietarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Permitir apenas números, parênteses, espaços e hífen
            if (!newValue.matches("[0-9()\\s-]*")) {
                telefoneProprietarioField.setText(oldValue);
                return;
            }
            
            if (Proprietario.isTelefoneValido(newValue)) {
                telefoneProprietarioField.setStyle("-fx-border-color: none;");
            } else {
                telefoneProprietarioField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
        });

        // Validação do CPF do proprietário
        cpfProprietarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Permitir apenas números, pontos e hífen
            if (!newValue.matches("[0-9.-]*")) {
                cpfProprietarioField.setText(oldValue);
                return;
            }
            
            if (Proprietario.isCpfValido(newValue)) {
                cpfProprietarioField.setStyle("-fx-border-color: none;");
            } else {
                cpfProprietarioField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
        });

        // Validação do endereço
        enderecoField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Proprietario.isEnderecoValido(newValue)) {
                enderecoField.setStyle("-fx-border-color: none;");
            } else {
                enderecoField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
        });
    }

    private void configurarValidacaoReserva() {
        // Validação do código da reserva
        codigoReservaField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Reserva.isCodigoValido(newValue)) {
                codigoReservaField.setStyle("-fx-border-color: none;");
            } else {
                codigoReservaField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
        });

        // Validação do CPF do usuário na reserva
        cpfUsuarioReservaField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Permitir apenas números, pontos e hífen
            if (!newValue.matches("[0-9.-]*")) {
                cpfUsuarioReservaField.setText(oldValue);
                return;
            }
            
            if (Reserva.isCpfUsuarioValido(newValue)) {
                cpfUsuarioReservaField.setStyle("-fx-border-color: none;");
            } else {
                cpfUsuarioReservaField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
        });

        // Validação da informação do veículo
        veiculoInfoField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Reserva.isVeiculoInfoValido(newValue)) {
                veiculoInfoField.setStyle("-fx-border-color: none;");
            } else {
                veiculoInfoField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
        });

        // Validação do valor total
        valorTotalField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Permitir apenas números, vírgula e ponto
            if (!newValue.matches("[0-9]*[,.]?[0-9]*")) {
                valorTotalField.setText(oldValue);
                return;
            }
            
            if (Reserva.isValorTotalValido(newValue)) {
                valorTotalField.setStyle("-fx-border-color: none;");
            } else {
                valorTotalField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
        });
    }
}