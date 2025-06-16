package com.example.ra3projeto;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {
    
    // Componentes da interface
    private TabPane tabPane;
    
    // Campos para Veículos
    private TextField marcaField;
    private TextField modeloField;
    private TextField corField;
    private TextField anoField;
    private TextField valorDiariaField;
    private ComboBox<Proprietario> proprietarioVeiculoCombo;
    private ListView<Veiculo> listaVeiculos;
    
    // Campos para Usuários
    private TextField nomeUsuarioField;
    private TextField emailUsuarioField;
    private TextField telefoneUsuarioField;
    private TextField cpfUsuarioField;
    private ListView<Usuario> listaUsuarios;
    
    // Campos para Proprietários
    private TextField nomeProprietarioField;
    private TextField emailProprietarioField;
    private TextField telefoneProprietarioField;
    private TextField cpfProprietarioField;
    private TextField enderecoField;
    private ListView<Proprietario> listaProprietarios;
    
    // Campos para Reservas
    private TextField codigoReservaField;
    private TextField cpfUsuarioReservaField;
    private TextField veiculoInfoField;
    private TextField diasAluguelField;
    private TextField valorTotalField;
    private ComboBox<Usuario> usuarioReservaCombo;
    private ComboBox<Proprietario> proprietarioReservaCombo;
    private ComboBox<Veiculo> veiculoReservaCombo;
    private ListView<Reserva> listaReservas;
    
    // Objetos de edição
    private Veiculo veiculoEditando = null;
    private Usuario usuarioEditando = null;
    private Proprietario proprietarioEditando = null;
    private Reserva reservaEditando = null;

    @Override
    public void start(Stage stage) {
        // Criar o container principal
        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(20);
        root.getStyleClass().add("root");
        
        // Criar TabPane
        tabPane = new TabPane();
        tabPane.setPrefSize(900, 700);
        
        // Criar as abas
        createVeiculosTab();
        createUsuariosTab();
        createProprietariosTab();
        createReservasTab();
        
        // Adicionar TabPane ao container principal
        root.getChildren().add(tabPane);
        
        // Aplicar estilos CSS
        Scene scene = new Scene(root, 1300, 700);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        
        // Configurar janela
        stage.setTitle("DriveNow!");
        stage.setScene(scene);
        stage.show();
        
        // Inicializar dados
        initialize();
    }
    
    private void createVeiculosTab() {
        // Aba de Cadastro de Veículos
        Tab tabCadastroVeiculos = new Tab("Cadastro Veículos");
        tabCadastroVeiculos.setClosable(false);
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(25);
        vbox.setPadding(new Insets(40));
        
        Label titleLabel = new Label("🚗 Cadastro de Veículos");
        titleLabel.getStyleClass().add("title-label");
        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPrefWidth(650);
        gridPane.getStyleClass().add("grid-pane");
        
        // Configurar colunas
        ColumnConstraints col1 = new ColumnConstraints(130);
        col1.setHgrow(Priority.NEVER);
        ColumnConstraints col2 = new ColumnConstraints(280);
        col2.setHgrow(Priority.ALWAYS);
        ColumnConstraints col3 = new ColumnConstraints(220);
        col3.setHgrow(Priority.NEVER);
        gridPane.getColumnConstraints().addAll(col1, col2, col3);
        
        // Campos do formulário
        int row = 0;
        
        // Marca
        Label marcaLabel = new Label("Marca:");
        marcaLabel.getStyleClass().add("form-label");
        marcaField = new TextField();
        marcaField.setPromptText("Digite a marca do veículo...");
        marcaField.getStyleClass().add("text-field");
        Label marcaHelper = new Label("(min. 2 caracteres)");
        marcaHelper.getStyleClass().add("helper-label");
        gridPane.add(marcaLabel, 0, row);
        gridPane.add(marcaField, 1, row);
        gridPane.add(marcaHelper, 2, row);
        row++;
        
        // Modelo
        Label modeloLabel = new Label("Modelo:");
        modeloLabel.getStyleClass().add("form-label");
        modeloField = new TextField();
        modeloField.setPromptText("Digite o modelo do veículo...");
        modeloField.getStyleClass().add("text-field");
        Label modeloHelper = new Label("(min. 2 caracteres)");
        modeloHelper.getStyleClass().add("helper-label");
        gridPane.add(modeloLabel, 0, row);
        gridPane.add(modeloField, 1, row);
        gridPane.add(modeloHelper, 2, row);
        row++;
        
        // Cor
        Label corLabel = new Label("Cor:");
        corLabel.getStyleClass().add("form-label");
        corField = new TextField();
        corField.setPromptText("Digite a cor do veículo...");
        corField.getStyleClass().add("text-field");
        Label corHelper = new Label("(min. 3 caracteres)");
        corHelper.getStyleClass().add("helper-label");
        gridPane.add(corLabel, 0, row);
        gridPane.add(corField, 1, row);
        gridPane.add(corHelper, 2, row);
        row++;
        
        // Ano
        Label anoLabel = new Label("Ano:");
        anoLabel.getStyleClass().add("form-label");
        anoField = new TextField();
        anoField.setPromptText("Digite o ano (ex: 2024)");
        anoField.getStyleClass().add("text-field");
        Label anoHelper = new Label("(1950 a 2026)");
        anoHelper.getStyleClass().add("helper-label");
        gridPane.add(anoLabel, 0, row);
        gridPane.add(anoField, 1, row);
        gridPane.add(anoHelper, 2, row);
        row++;
        
        // Valor Diária
        Label valorLabel = new Label("Valor/Dia:");
        valorLabel.getStyleClass().add("form-label");
        valorDiariaField = new TextField();
        valorDiariaField.setPromptText("Digite o valor (ex: 120.50)");
        valorDiariaField.getStyleClass().add("text-field");
        Label valorHelper = new Label("(R$ 0,01 a R$ 10.000)");
        valorHelper.getStyleClass().add("helper-label");
        gridPane.add(valorLabel, 0, row);
        gridPane.add(valorDiariaField, 1, row);
        gridPane.add(valorHelper, 2, row);
        row++;
        
        // Proprietário
        Label proprietarioLabel = new Label("Proprietário:");
        proprietarioLabel.getStyleClass().add("form-label");
        proprietarioVeiculoCombo = new ComboBox<>();
        proprietarioVeiculoCombo.setPromptText("Selecione o proprietário...");
        proprietarioVeiculoCombo.getStyleClass().add("choice-box");
        proprietarioVeiculoCombo.setPrefWidth(280);
        Label proprietarioHelper = new Label("(obrigatório)");
        proprietarioHelper.getStyleClass().add("helper-label");
        gridPane.add(proprietarioLabel, 0, row);
        gridPane.add(proprietarioVeiculoCombo, 1, row);
        gridPane.add(proprietarioHelper, 2, row);
        row++;
        
        Button salvarButton = new Button("💾 Salvar Veículo");
        salvarButton.getStyleClass().add("button");
        salvarButton.setPrefWidth(250);
        salvarButton.setOnAction(e -> salvarVeiculo());
        
        vbox.getChildren().addAll(titleLabel, gridPane, salvarButton);
        scrollPane.setContent(vbox);
        tabCadastroVeiculos.setContent(scrollPane);
        
        // Aba de Gerenciar Veículos
        Tab tabGerenciarVeiculos = new Tab("Gerenciar Veículos");
        tabGerenciarVeiculos.setClosable(false);
        
        ScrollPane scrollPane2 = new ScrollPane();
        scrollPane2.setFitToWidth(true);
        scrollPane2.setFitToHeight(true);
        
        VBox vbox2 = new VBox();
        vbox2.setAlignment(Pos.TOP_CENTER);
        vbox2.setSpacing(25);
        vbox2.setPadding(new Insets(40));
        
        Label titleLabel2 = new Label("🔧 Gerenciar Veículos");
        titleLabel2.getStyleClass().add("title-label");
        
        Label subtitleLabel2 = new Label("Selecione um veículo da lista para editar ou remover");
        subtitleLabel2.getStyleClass().add("subtitle-label");
        
        listaVeiculos = new ListView<>();
        listaVeiculos.setPrefSize(750, 350);
        listaVeiculos.getStyleClass().add("list-view");
        
        // Botões de ação
        HBox botoesVeiculos = new HBox();
        botoesVeiculos.setAlignment(Pos.CENTER);
        botoesVeiculos.setSpacing(15);
        
        Button editarVeiculoBtn = new Button("✏️ Editar");
        editarVeiculoBtn.getStyleClass().addAll("button", "warning");
        editarVeiculoBtn.setPrefWidth(120);
        editarVeiculoBtn.setOnAction(e -> editarVeiculo());
        
        Button excluirVeiculoBtn = new Button("🗑️ Excluir");
        excluirVeiculoBtn.getStyleClass().addAll("button", "danger");
        excluirVeiculoBtn.setPrefWidth(120);
        excluirVeiculoBtn.setOnAction(e -> excluirVeiculo());
        
        botoesVeiculos.getChildren().addAll(editarVeiculoBtn, excluirVeiculoBtn);
        
        vbox2.getChildren().addAll(titleLabel2, subtitleLabel2, listaVeiculos, botoesVeiculos);
        scrollPane2.setContent(vbox2);  
        tabGerenciarVeiculos.setContent(scrollPane2);
        
        tabPane.getTabs().addAll(tabCadastroVeiculos, tabGerenciarVeiculos);
    }
    
    private void createUsuariosTab() {
        // Aba de Cadastro de Usuários
        Tab tabCadastroUsuarios = new Tab("Cadastro Usuários");
        tabCadastroUsuarios.setClosable(false);
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(25);
        vbox.setPadding(new Insets(40));
        
        Label titleLabel = new Label("👤 Cadastro de Usuários");
        titleLabel.getStyleClass().add("title-label");
        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPrefWidth(650);
        gridPane.getStyleClass().add("grid-pane");
        
        // Configurar colunas
        ColumnConstraints col1 = new ColumnConstraints(130);
        col1.setHgrow(Priority.NEVER);
        ColumnConstraints col2 = new ColumnConstraints(280);
        col2.setHgrow(Priority.ALWAYS);
        ColumnConstraints col3 = new ColumnConstraints(220);
        col3.setHgrow(Priority.NEVER);
        gridPane.getColumnConstraints().addAll(col1, col2, col3);
        
        int row = 0;
        
        // Nome
        Label nomeLabel = new Label("Nome:");
        nomeLabel.getStyleClass().add("form-label");
        nomeUsuarioField = new TextField();
        nomeUsuarioField.setPromptText("Digite o nome completo...");
        nomeUsuarioField.getStyleClass().add("text-field");
        Label nomeHelper = new Label("(min. 2 caracteres)");
        nomeHelper.getStyleClass().add("helper-label");
        gridPane.add(nomeLabel, 0, row);
        gridPane.add(nomeUsuarioField, 1, row);
        gridPane.add(nomeHelper, 2, row);
        row++;
        
        // Email
        Label emailLabel = new Label("Email:");
        emailLabel.getStyleClass().add("form-label");
        emailUsuarioField = new TextField();
        emailUsuarioField.setPromptText("Digite o email...");
        emailUsuarioField.getStyleClass().add("text-field");
        Label emailHelper = new Label("(formato: email@dominio.com)");
        emailHelper.getStyleClass().add("helper-label");
        gridPane.add(emailLabel, 0, row);
        gridPane.add(emailUsuarioField, 1, row);
        gridPane.add(emailHelper, 2, row);
        row++;
        
        // Telefone
        Label telefoneLabel = new Label("Telefone:");
        telefoneLabel.getStyleClass().add("form-label");
        telefoneUsuarioField = new TextField();
        telefoneUsuarioField.setPromptText("Digite o telefone...");
        telefoneUsuarioField.getStyleClass().add("text-field");
        Label telefoneHelper = new Label("(formato: (11) 99999-9999)");
        telefoneHelper.getStyleClass().add("helper-label");
        gridPane.add(telefoneLabel, 0, row);
        gridPane.add(telefoneUsuarioField, 1, row);
        gridPane.add(telefoneHelper, 2, row);
        row++;
        
        // CPF
        Label cpfLabel = new Label("CPF:");
        cpfLabel.getStyleClass().add("form-label");
        cpfUsuarioField = new TextField();
        cpfUsuarioField.setPromptText("Digite o CPF...");
        cpfUsuarioField.getStyleClass().add("text-field");
        Label cpfHelper = new Label("(formato: 000.000.000-00)");
        cpfHelper.getStyleClass().add("helper-label");
        gridPane.add(cpfLabel, 0, row);
        gridPane.add(cpfUsuarioField, 1, row);
        gridPane.add(cpfHelper, 2, row);
        
        Button salvarButton = new Button("👤 Salvar Usuário");
        salvarButton.getStyleClass().addAll("button", "success");
        salvarButton.setPrefWidth(250);
        salvarButton.setOnAction(e -> salvarUsuario());
        
        vbox.getChildren().addAll(titleLabel, gridPane, salvarButton);
        scrollPane.setContent(vbox);
        tabCadastroUsuarios.setContent(scrollPane);
        
        // Aba de Gerenciar Usuários
        Tab tabGerenciarUsuarios = new Tab("Gerenciar Usuários");
        tabGerenciarUsuarios.setClosable(false);
        
        ScrollPane scrollPane2 = new ScrollPane();
        scrollPane2.setFitToWidth(true);
        scrollPane2.setFitToHeight(true);
        
        VBox vbox2 = new VBox();
        vbox2.setAlignment(Pos.TOP_CENTER);
        vbox2.setSpacing(25);
        vbox2.setPadding(new Insets(40));
        
        Label titleLabel2 = new Label("👥 Gerenciar Usuários");
        titleLabel2.getStyleClass().add("title-label");
        
        Label subtitleLabel2 = new Label("Selecione um usuário da lista para editar ou remover");
        subtitleLabel2.getStyleClass().add("subtitle-label");
        
        listaUsuarios = new ListView<>();
        listaUsuarios.setPrefSize(750, 350);
        listaUsuarios.getStyleClass().add("list-view");
        
        // Botões de ação
        HBox botoesUsuarios = new HBox();
        botoesUsuarios.setAlignment(Pos.CENTER);
        botoesUsuarios.setSpacing(15);
        
        Button editarUsuarioBtn = new Button("✏️ Editar");
        editarUsuarioBtn.getStyleClass().addAll("button", "warning");
        editarUsuarioBtn.setPrefWidth(120);
        editarUsuarioBtn.setOnAction(e -> editarUsuario());
        
        Button excluirUsuarioBtn = new Button("🗑️ Excluir");
        excluirUsuarioBtn.getStyleClass().addAll("button", "danger");
        excluirUsuarioBtn.setPrefWidth(120);
        excluirUsuarioBtn.setOnAction(e -> excluirUsuario());
        
        botoesUsuarios.getChildren().addAll(editarUsuarioBtn, excluirUsuarioBtn);
        
        vbox2.getChildren().addAll(titleLabel2, subtitleLabel2, listaUsuarios, botoesUsuarios);
        scrollPane2.setContent(vbox2);
        tabGerenciarUsuarios.setContent(scrollPane2);
        
        tabPane.getTabs().addAll(tabCadastroUsuarios, tabGerenciarUsuarios);
    }
    
    private void createProprietariosTab() {
        // Aba de Cadastro de Proprietários
        Tab tabCadastroProprietarios = new Tab("Cadastro Proprietários");
        tabCadastroProprietarios.setClosable(false);
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(25);
        vbox.setPadding(new Insets(40));
        
        Label titleLabel = new Label("🏢 Cadastro de Proprietários");
        titleLabel.getStyleClass().add("title-label");
        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPrefWidth(650);
        gridPane.getStyleClass().add("grid-pane");
        
        // Configurar colunas
        ColumnConstraints col1 = new ColumnConstraints(130);
        col1.setHgrow(Priority.NEVER);
        ColumnConstraints col2 = new ColumnConstraints(280);
        col2.setHgrow(Priority.ALWAYS);
        ColumnConstraints col3 = new ColumnConstraints(220);
        col3.setHgrow(Priority.NEVER);
        gridPane.getColumnConstraints().addAll(col1, col2, col3);
        
        int row = 0;
        
        // Nome
        Label nomeLabel = new Label("Nome:");
        nomeLabel.getStyleClass().add("form-label");
        nomeProprietarioField = new TextField();
        nomeProprietarioField.setPromptText("Digite o nome completo...");
        nomeProprietarioField.getStyleClass().add("text-field");
        Label nomeHelper = new Label("(min. 2 caracteres)");
        nomeHelper.getStyleClass().add("helper-label");
        gridPane.add(nomeLabel, 0, row);
        gridPane.add(nomeProprietarioField, 1, row);
        gridPane.add(nomeHelper, 2, row);
        row++;
        
        // Email
        Label emailLabel = new Label("Email:");
        emailLabel.getStyleClass().add("form-label");
        emailProprietarioField = new TextField();
        emailProprietarioField.setPromptText("Digite o email...");
        emailProprietarioField.getStyleClass().add("text-field");
        Label emailHelper = new Label("(formato: email@dominio.com)");
        emailHelper.getStyleClass().add("helper-label");
        gridPane.add(emailLabel, 0, row);
        gridPane.add(emailProprietarioField, 1, row);
        gridPane.add(emailHelper, 2, row);
        row++;
        
        // Telefone
        Label telefoneLabel = new Label("Telefone:");
        telefoneLabel.getStyleClass().add("form-label");
        telefoneProprietarioField = new TextField();
        telefoneProprietarioField.setPromptText("Digite o telefone...");
        telefoneProprietarioField.getStyleClass().add("text-field");
        Label telefoneHelper = new Label("(formato: (11) 99999-9999)");
        telefoneHelper.getStyleClass().add("helper-label");
        gridPane.add(telefoneLabel, 0, row);
        gridPane.add(telefoneProprietarioField, 1, row);
        gridPane.add(telefoneHelper, 2, row);
        row++;
        
        // CPF
        Label cpfLabel = new Label("CPF:");
        cpfLabel.getStyleClass().add("form-label");
        cpfProprietarioField = new TextField();
        cpfProprietarioField.setPromptText("Digite o CPF...");
        cpfProprietarioField.getStyleClass().add("text-field");
        Label cpfHelper = new Label("(formato: 000.000.000-00)");
        cpfHelper.getStyleClass().add("helper-label");
        gridPane.add(cpfLabel, 0, row);
        gridPane.add(cpfProprietarioField, 1, row);
        gridPane.add(cpfHelper, 2, row);
        row++;
        
        // Endereço
        Label enderecoLabel = new Label("Endereço:");
        enderecoLabel.getStyleClass().add("form-label");
        enderecoField = new TextField();
        enderecoField.setPromptText("Digite o endereço completo...");
        enderecoField.getStyleClass().add("text-field");
        Label enderecoHelper = new Label("(min. 5 caracteres)");
        enderecoHelper.getStyleClass().add("helper-label");
        gridPane.add(enderecoLabel, 0, row);
        gridPane.add(enderecoField, 1, row);
        gridPane.add(enderecoHelper, 2, row);
        
        Button salvarButton = new Button("🏢 Salvar Proprietário");
        salvarButton.getStyleClass().addAll("button", "warning");
        salvarButton.setPrefWidth(250);
        salvarButton.setOnAction(e -> salvarProprietario());
        
        vbox.getChildren().addAll(titleLabel, gridPane, salvarButton);
        scrollPane.setContent(vbox);
        tabCadastroProprietarios.setContent(scrollPane);
        
        // Aba de Gerenciar Proprietários
        Tab tabGerenciarProprietarios = new Tab("Gerenciar Proprietários");
        tabGerenciarProprietarios.setClosable(false);
        
        ScrollPane scrollPane2 = new ScrollPane();
        scrollPane2.setFitToWidth(true);
        scrollPane2.setFitToHeight(true);
        
        VBox vbox2 = new VBox();
        vbox2.setAlignment(Pos.TOP_CENTER);
        vbox2.setSpacing(25);
        vbox2.setPadding(new Insets(40));
        
        Label titleLabel2 = new Label("🏪 Gerenciar Proprietários");
        titleLabel2.getStyleClass().add("title-label");
        
        Label subtitleLabel2 = new Label("Selecione um proprietário da lista para editar ou remover");
        subtitleLabel2.getStyleClass().add("subtitle-label");
        
        listaProprietarios = new ListView<>();
        listaProprietarios.setPrefSize(750, 350);
        listaProprietarios.getStyleClass().add("list-view");
        
        // Botões de ação
        HBox botoesProprietarios = new HBox();
        botoesProprietarios.setAlignment(Pos.CENTER);
        botoesProprietarios.setSpacing(15);
        
        Button editarProprietarioBtn = new Button("✏️ Editar");
        editarProprietarioBtn.getStyleClass().addAll("button", "warning");
        editarProprietarioBtn.setPrefWidth(120);
        editarProprietarioBtn.setOnAction(e -> editarProprietario());
        
        Button excluirProprietarioBtn = new Button("🗑️ Excluir");
        excluirProprietarioBtn.getStyleClass().addAll("button", "danger");
        excluirProprietarioBtn.setPrefWidth(120);
        excluirProprietarioBtn.setOnAction(e -> excluirProprietario());
        
        botoesProprietarios.getChildren().addAll(editarProprietarioBtn, excluirProprietarioBtn);
        
        vbox2.getChildren().addAll(titleLabel2, subtitleLabel2, listaProprietarios, botoesProprietarios);
        scrollPane2.setContent(vbox2);
        tabGerenciarProprietarios.setContent(scrollPane2);
        
        tabPane.getTabs().addAll(tabCadastroProprietarios, tabGerenciarProprietarios);
    }
    
    private void createReservasTab() {
        // Aba de Cadastro de Reservas
        Tab tabCadastroReservas = new Tab("Cadastro Reservas");
        tabCadastroReservas.setClosable(false);
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(25);
        vbox.setPadding(new Insets(40));
        
        Label titleLabel = new Label("📋 Cadastro de Reservas");
        titleLabel.getStyleClass().add("title-label");
        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPrefWidth(650);
        gridPane.getStyleClass().add("grid-pane");
        
        // Configurar colunas
        ColumnConstraints col1 = new ColumnConstraints(130);
        col1.setHgrow(Priority.NEVER);
        ColumnConstraints col2 = new ColumnConstraints(280);
        col2.setHgrow(Priority.ALWAYS);
        ColumnConstraints col3 = new ColumnConstraints(220);
        col3.setHgrow(Priority.NEVER);
        gridPane.getColumnConstraints().addAll(col1, col2, col3);
        
        int row = 0;
        
        // Código
        Label codigoLabel = new Label("Código:");
        codigoLabel.getStyleClass().add("form-label");
        
        HBox codigoBox = new HBox();
        codigoBox.setSpacing(10);
        codigoBox.setAlignment(Pos.CENTER_LEFT);
        
        codigoReservaField = new TextField();
        codigoReservaField.setPromptText("Digite o código da reserva...");
        codigoReservaField.getStyleClass().add("text-field");
        codigoReservaField.setPrefWidth(200);
        
        Button gerarCodigoBtn = new Button("🎲 Gerar");
        gerarCodigoBtn.getStyleClass().addAll("button", "primary");
        gerarCodigoBtn.setPrefWidth(70);
        gerarCodigoBtn.setOnAction(e -> codigoReservaField.setText(Reserva.gerarCodigoReserva()));
        
        codigoBox.getChildren().addAll(codigoReservaField, gerarCodigoBtn);
        
        Label codigoHelper = new Label("(min. 3 caracteres ou clique Gerar)");
        codigoHelper.getStyleClass().add("helper-label");
        gridPane.add(codigoLabel, 0, row);
        gridPane.add(codigoBox, 1, row);
        gridPane.add(codigoHelper, 2, row);
        row++;
        
        // Usuário
        Label usuarioLabel = new Label("Usuário:");
        usuarioLabel.getStyleClass().add("form-label");
        usuarioReservaCombo = new ComboBox<>();
        usuarioReservaCombo.setPromptText("Selecione o usuário...");
        usuarioReservaCombo.getStyleClass().add("choice-box");
        usuarioReservaCombo.setPrefWidth(280);
        Label usuarioHelper = new Label("(obrigatório)");
        usuarioHelper.getStyleClass().add("helper-label");
        gridPane.add(usuarioLabel, 0, row);
        gridPane.add(usuarioReservaCombo, 1, row);
        gridPane.add(usuarioHelper, 2, row);
        row++;
        
        // Proprietário
        Label proprietarioReservaLabel = new Label("Proprietário:");
        proprietarioReservaLabel.getStyleClass().add("form-label");
        proprietarioReservaCombo = new ComboBox<>();
        proprietarioReservaCombo.setPromptText("Selecione o proprietário...");
        proprietarioReservaCombo.getStyleClass().add("choice-box");
        proprietarioReservaCombo.setPrefWidth(280);
        Label proprietarioReservaHelper = new Label("(obrigatório)");
        proprietarioReservaHelper.getStyleClass().add("helper-label");
        gridPane.add(proprietarioReservaLabel, 0, row);
        gridPane.add(proprietarioReservaCombo, 1, row);
        gridPane.add(proprietarioReservaHelper, 2, row);
        row++;
        
        // Veículo
        Label veiculoLabel = new Label("Veículo:");
        veiculoLabel.getStyleClass().add("form-label");
        veiculoReservaCombo = new ComboBox<>();
        veiculoReservaCombo.setPromptText("Selecione o veículo...");
        veiculoReservaCombo.getStyleClass().add("choice-box");
        veiculoReservaCombo.setPrefWidth(280);
        Label veiculoHelper = new Label("(obrigatório)");
        veiculoHelper.getStyleClass().add("helper-label");
        gridPane.add(veiculoLabel, 0, row);
        gridPane.add(veiculoReservaCombo, 1, row);
        gridPane.add(veiculoHelper, 2, row);
        row++;
        
        // Dias de Aluguel
        Label diasLabel = new Label("Dias:");
        diasLabel.getStyleClass().add("form-label");
        diasAluguelField = new TextField();
        diasAluguelField.setPromptText("Digite o número de dias...");
        diasAluguelField.getStyleClass().add("text-field");
        Label diasHelper = new Label("(mínimo 1 dia)");
        diasHelper.getStyleClass().add("helper-label");
        gridPane.add(diasLabel, 0, row);
        gridPane.add(diasAluguelField, 1, row);
        gridPane.add(diasHelper, 2, row);
        row++;
        
        // Valor Total
        Label valorLabel = new Label("Valor Total:");
        valorLabel.getStyleClass().add("form-label");
        valorTotalField = new TextField();
        valorTotalField.setPromptText("Calculado automaticamente...");
        valorTotalField.getStyleClass().add("text-field");
        valorTotalField.setEditable(false); // Campo somente leitura
        Label valorHelper = new Label("(dias × valor diário)");
        valorHelper.getStyleClass().add("helper-label");
        gridPane.add(valorLabel, 0, row);
        gridPane.add(valorTotalField, 1, row);
        gridPane.add(valorHelper, 2, row);
        
        Button salvarButton = new Button("📋 Salvar Reserva");
        salvarButton.getStyleClass().addAll("button", "danger");
        salvarButton.setPrefWidth(250);
        salvarButton.setOnAction(e -> salvarReserva());
        
        vbox.getChildren().addAll(titleLabel, gridPane, salvarButton);
        scrollPane.setContent(vbox);
        tabCadastroReservas.setContent(scrollPane);
        
        // Aba de Gerenciar Reservas
        Tab tabGerenciarReservas = new Tab("Gerenciar Reservas");
        tabGerenciarReservas.setClosable(false);
        
        ScrollPane scrollPane2 = new ScrollPane();
        scrollPane2.setFitToWidth(true);
        scrollPane2.setFitToHeight(true);
        
        VBox vbox2 = new VBox();
        vbox2.setAlignment(Pos.TOP_CENTER);
        vbox2.setSpacing(25);
        vbox2.setPadding(new Insets(40));
        
        Label titleLabel2 = new Label("📊 Gerenciar Reservas");
        titleLabel2.getStyleClass().add("title-label");
        
        Label subtitleLabel2 = new Label("Selecione uma reserva da lista para editar ou remover");
        subtitleLabel2.getStyleClass().add("subtitle-label");
        
        listaReservas = new ListView<>();
        listaReservas.setPrefSize(750, 350);
        listaReservas.getStyleClass().add("list-view");
        
        // Botões de ação
        HBox botoesReservas = new HBox();
        botoesReservas.setAlignment(Pos.CENTER);
        botoesReservas.setSpacing(15);
        
        Button editarReservaBtn = new Button("✏️ Editar");
        editarReservaBtn.getStyleClass().addAll("button", "warning");
        editarReservaBtn.setPrefWidth(120);
        editarReservaBtn.setOnAction(e -> editarReserva());
        
        Button excluirReservaBtn = new Button("🗑️ Excluir");
        excluirReservaBtn.getStyleClass().addAll("button", "danger");
        excluirReservaBtn.setPrefWidth(120);
        excluirReservaBtn.setOnAction(e -> excluirReserva());
        
        botoesReservas.getChildren().addAll(editarReservaBtn, excluirReservaBtn);
        
        vbox2.getChildren().addAll(titleLabel2, subtitleLabel2, listaReservas, botoesReservas);
        scrollPane2.setContent(vbox2);
        tabGerenciarReservas.setContent(scrollPane2);
        
        tabPane.getTabs().addAll(tabCadastroReservas, tabGerenciarReservas);
    }

    // Métodos de inicialização e ações
    public void initialize() {
        carregarLista();
        carregarListaUsuarios();
        carregarListaProprietarios();
        carregarListaReservas();
        
        // Carregar ComboBoxes
        carregarComboBoxes();
        configurarRelacionamentos();
        
        // Configurar validação em tempo real
        configurarValidacaoVeiculo();
        configurarValidacaoUsuario();
        configurarValidacaoProprietario();
        configurarValidacaoReserva();
    }

    private void carregarComboBoxes() {
        // Carregar proprietários no ComboBox de veículos
        List<Proprietario> proprietarios = ProprietarioRepository.carregarLista();
        ObservableList<Proprietario> proprietariosItems = FXCollections.observableArrayList(proprietarios);
        proprietarioVeiculoCombo.setItems(proprietariosItems);
        
        // Carregar usuários no ComboBox de reservas
        List<Usuario> usuarios = UsuarioRepository.carregarLista();
        ObservableList<Usuario> usuariosItems = FXCollections.observableArrayList(usuarios);
        usuarioReservaCombo.setItems(usuariosItems);
        
        // Carregar proprietários no ComboBox de reservas
        proprietarioReservaCombo.setItems(proprietariosItems);
    }

    private void configurarRelacionamentos() {
        // Configurar filtro de veículos por proprietário nas reservas
        proprietarioReservaCombo.setOnAction(e -> {
            Proprietario proprietarioSelecionado = proprietarioReservaCombo.getValue();
            if (proprietarioSelecionado != null) {
                List<Veiculo> veiculosDoProprietario = VeiculoRepository.carregarVeiculosPorProprietario(proprietarioSelecionado);
                ObservableList<Veiculo> veiculosItems = FXCollections.observableArrayList(veiculosDoProprietario);
                veiculoReservaCombo.setItems(veiculosItems);
                veiculoReservaCombo.setValue(null); // Limpar seleção anterior
            } else {
                veiculoReservaCombo.getItems().clear();
            }
        });
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

    private void salvarVeiculo() {
        String marca = marcaField.getText();
        String modelo = modeloField.getText();
        String cor = corField.getText();
        String anoText = anoField.getText();
        String valorText = valorDiariaField.getText();
        Proprietario proprietarioSelecionado = proprietarioVeiculoCombo.getValue();

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
            mostrarAlerta("Ano inválido", "Ano deve estar entre 1950 e 2026");
            return;
        }

        if (!Veiculo.isValorDiariaValido(valorText)) {
            mostrarAlerta("Valor inválido", "Valor da diária deve estar entre R$ 0,01 e R$ 10.000,00");
            return;
        }

        if (proprietarioSelecionado == null) {
            mostrarAlerta("Proprietário obrigatório", "Por favor, selecione um proprietário para o veículo");
            return;
        }

        try {
            int ano = Integer.parseInt(anoText);
            double valorDiaria = Double.parseDouble(valorText.replace(",", "."));

            if (veiculoEditando == null) {
                // Criar novo veículo
                Veiculo novoVeiculo = new Veiculo(marca, modelo, cor, ano, valorDiaria, proprietarioSelecionado);
                VeiculoRepository.adicionarVeiculo(novoVeiculo);
                mostrarSucesso("Veículo salvo com sucesso!");
            } else {
                // Editar veículo existente - buscar e substituir no arquivo
                List<Veiculo> veiculos = VeiculoRepository.carregarLista();
                Veiculo veiculoAtualizado = null;
                
                // Encontrar o veículo na lista e substituir os dados
                for (int i = 0; i < veiculos.size(); i++) {
                    Veiculo v = veiculos.get(i);
                    if (v.getMarca().equals(veiculoEditando.getMarca()) && 
                        v.getModelo().equals(veiculoEditando.getModelo()) && 
                        v.getCor().equals(veiculoEditando.getCor()) && 
                        v.getAno() == veiculoEditando.getAno()) {
                        // Criar novo veículo com dados atualizados
                        veiculoAtualizado = new Veiculo(marca, modelo, cor, ano, valorDiaria, proprietarioSelecionado);
                        veiculos.set(i, veiculoAtualizado);
                        break;
                    }
                }
                
                // Salvar a lista modificada no arquivo
                VeiculoRepository.salvarLista(veiculos);
                
                // Atualizar relacionamentos em reservas se o veículo foi encontrado
                if (veiculoAtualizado != null) {
                    atualizarRelacionamentosVeiculo(veiculoEditando, veiculoAtualizado);
                }
                
                mostrarSucesso("Veículo atualizado com sucesso!");
                veiculoEditando = null;
            }

            limparCamposVeiculo();
            carregarLista();
            carregarListaReservas(); // Recarregar reservas para mostrar mudanças

        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de formato", "Verifique os valores inseridos");
        }
    }

    private void salvarUsuario() {
        String nome = nomeUsuarioField.getText();
        String email = emailUsuarioField.getText();
        String telefone = telefoneUsuarioField.getText();
        String cpf = cpfUsuarioField.getText();

        // Validar todos os campos antes de salvar
        if (!Usuario.isNomeValido(nome)) {
            mostrarAlerta("Nome inválido", "Nome deve ter pelo menos 2 caracteres");
            return;
        }

        if (!Usuario.isEmailValido(email)) {
            mostrarAlerta("Email inválido", "Email deve ter formato válido");
            return;
        }

        if (!Usuario.isTelefoneValido(telefone)) {
            mostrarAlerta("Telefone inválido", "Telefone deve ter formato válido");
            return;
        }

        if (!Usuario.isCpfValido(cpf)) {
            mostrarAlerta("CPF inválido", "CPF deve ter formato válido");
            return;
        }

        // Validações de duplicação
        if (usuarioEditando == null) {
            // Novo usuário - verificar se CPF ou telefone já existem
            if (UsuarioRepository.cpfJaExiste(cpf)) {
                mostrarAlerta("CPF já cadastrado", "Este CPF já está cadastrado para outro usuário");
                return;
            }
            if (UsuarioRepository.telefoneJaExiste(telefone)) {
                mostrarAlerta("Telefone já cadastrado", "Este telefone já está cadastrado para outro usuário");
                return;
            }
        } else {
            // Editando usuário - verificar se CPF ou telefone já existem em outros usuários
            if (UsuarioRepository.cpfJaExiste(cpf, usuarioEditando)) {
                mostrarAlerta("CPF já cadastrado", "Este CPF já está cadastrado para outro usuário");
                return;
            }
            if (UsuarioRepository.telefoneJaExiste(telefone, usuarioEditando)) {
                mostrarAlerta("Telefone já cadastrado", "Este telefone já está cadastrado para outro usuário");
                return;
            }
        }

        try {
            if (usuarioEditando == null) {
                // Criar novo usuário
                Usuario novoUsuario = new Usuario(nome, email, telefone, cpf);
                UsuarioRepository.adicionarUsuario(novoUsuario);
                mostrarSucesso("Usuário salvo com sucesso!");
            } else {
                // Editar usuário existente - buscar e substituir no arquivo
                List<Usuario> usuarios = UsuarioRepository.carregarLista();
                Usuario usuarioAtualizado = null;
                
                // Encontrar o usuário na lista e substituir os dados
                for (int i = 0; i < usuarios.size(); i++) {
                    Usuario u = usuarios.get(i);
                    if (u.getNome().equals(usuarioEditando.getNome()) && 
                        u.getEmail().equals(usuarioEditando.getEmail()) && 
                        u.getCpf().equals(usuarioEditando.getCpf())) {
                        // Criar novo usuário com dados atualizados
                        usuarioAtualizado = new Usuario(nome, email, telefone, cpf);
                        usuarios.set(i, usuarioAtualizado);
                        break;
                    }
                }
                
                // Salvar a lista modificada no arquivo
                UsuarioRepository.salvarLista(usuarios);
                
                // Atualizar relacionamentos em reservas se o usuário foi encontrado
                if (usuarioAtualizado != null) {
                    atualizarRelacionamentosUsuario(usuarioEditando, usuarioAtualizado);
                }
                
                mostrarSucesso("Usuário atualizado com sucesso!");
                usuarioEditando = null;
            }

            limparCamposUsuario();
            carregarListaUsuarios();
            carregarListaReservas(); // Recarregar reservas para mostrar mudanças

        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao salvar usuário: " + e.getMessage());
        }
    }

    private void salvarProprietario() {
        String nome = nomeProprietarioField.getText();
        String email = emailProprietarioField.getText();
        String telefone = telefoneProprietarioField.getText();
        String cpf = cpfProprietarioField.getText();
        String endereco = enderecoField.getText();

        // Validar todos os campos antes de salvar
        if (!Proprietario.isNomeValido(nome)) {
            mostrarAlerta("Nome inválido", "Nome deve ter pelo menos 2 caracteres");
            return;
        }

        if (!Proprietario.isEmailValido(email)) {
            mostrarAlerta("Email inválido", "Email deve ter formato válido");
            return;
        }

        if (!Proprietario.isTelefoneValido(telefone)) {
            mostrarAlerta("Telefone inválido", "Telefone deve ter formato válido");
            return;
        }

        if (!Proprietario.isCpfValido(cpf)) {
            mostrarAlerta("CPF inválido", "CPF deve ter formato válido");
            return;
        }

        if (!Proprietario.isEnderecoValido(endereco)) {
            mostrarAlerta("Endereço inválido", "Endereço deve ter pelo menos 5 caracteres");
            return;
        }

        // Validações de duplicação
        if (proprietarioEditando == null) {
            // Novo proprietário - verificar se CPF ou telefone já existem
            if (ProprietarioRepository.cpfJaExiste(cpf)) {
                mostrarAlerta("CPF já cadastrado", "Este CPF já está cadastrado para outro proprietário");
                return;
            }
            if (ProprietarioRepository.telefoneJaExiste(telefone)) {
                mostrarAlerta("Telefone já cadastrado", "Este telefone já está cadastrado para outro proprietário");
                return;
            }
        } else {
            // Editando proprietário - verificar se CPF ou telefone já existem em outros proprietários
            if (ProprietarioRepository.cpfJaExiste(cpf, proprietarioEditando)) {
                mostrarAlerta("CPF já cadastrado", "Este CPF já está cadastrado para outro proprietário");
                return;
            }
            if (ProprietarioRepository.telefoneJaExiste(telefone, proprietarioEditando)) {
                mostrarAlerta("Telefone já cadastrado", "Este telefone já está cadastrado para outro proprietário");
                return;
            }
        }

        try {
            if (proprietarioEditando == null) {
                // Criar novo proprietário
                Proprietario novoProprietario = new Proprietario(nome, email, telefone, cpf, endereco);
                ProprietarioRepository.adicionarProprietario(novoProprietario);
                mostrarSucesso("Proprietário salvo com sucesso!");
            } else {
                // Editar proprietário existente - buscar e substituir no arquivo
                List<Proprietario> proprietarios = ProprietarioRepository.carregarLista();
                
                // Encontrar o proprietário na lista e substituir os dados
                for (int i = 0; i < proprietarios.size(); i++) {
                    Proprietario p = proprietarios.get(i);
                    if (p.getNome().equals(proprietarioEditando.getNome()) && 
                        p.getEmail().equals(proprietarioEditando.getEmail()) && 
                        p.getCpf().equals(proprietarioEditando.getCpf())) {
                        // Criar novo proprietário com dados atualizados
                        Proprietario proprietarioAtualizado = new Proprietario(nome, email, telefone, cpf, endereco);
                        proprietarios.set(i, proprietarioAtualizado);
                        
                        // Atualizar relacionamentos em veículos e reservas
                        atualizarRelacionamentosProprietario(proprietarioEditando, proprietarioAtualizado);
                        break;
                    }
                }
                
                // Salvar a lista modificada no arquivo
                ProprietarioRepository.salvarLista(proprietarios);
                mostrarSucesso("Proprietário atualizado com sucesso!");
                proprietarioEditando = null;
            }

            limparCamposProprietario();
            carregarListaProprietarios();
            carregarLista(); // Recarregar veículos para mostrar mudanças
            carregarListaReservas(); // Recarregar reservas para mostrar mudanças

        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao salvar proprietário: " + e.getMessage());
        }
    }

    private void salvarReserva() {
        String codigo = codigoReservaField.getText();
        String diasText = diasAluguelField.getText();
        String valorText = valorTotalField.getText();
        Usuario usuarioSelecionado = usuarioReservaCombo.getValue();
        Veiculo veiculoSelecionado = veiculoReservaCombo.getValue();

        // Validar todos os campos antes de salvar
        if (!Reserva.isCodigoValido(codigo)) {
            mostrarAlerta("Código inválido", "Código deve ter pelo menos 3 caracteres");
            return;
        }

        if (usuarioSelecionado == null) {
            mostrarAlerta("Usuário obrigatório", "Por favor, selecione um usuário");
            return;
        }

        if (veiculoSelecionado == null) {
            mostrarAlerta("Veículo obrigatório", "Por favor, selecione um veículo");
            return;
        }

        // Validar dias de aluguel
        if (diasText == null || diasText.trim().isEmpty()) {
            mostrarAlerta("Dias obrigatório", "Por favor, digite o número de dias");
            return;
        }

        int dias;
        try {
            dias = Integer.parseInt(diasText.trim());
            if (dias <= 0) {
                mostrarAlerta("Dias inválido", "Número de dias deve ser maior que zero");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Dias inválido", "Por favor, digite um número válido para os dias");
            return;
        }

        if (!Reserva.isValorTotalValido(valorText)) {
            mostrarAlerta("Valor inválido", "Valor total deve estar entre R$ 0,00 e R$ 100.000,00");
            return;
        }

        try {
            if (reservaEditando == null) {
                // Criar nova reserva com relacionamentos - usar construtor que calcula valor automaticamente
                java.time.LocalDate hoje = java.time.LocalDate.now();
                java.time.LocalDate dataFim = hoje.plusDays(dias);
                Reserva novaReserva = new Reserva(codigo, usuarioSelecionado, veiculoSelecionado, hoje, dataFim, dias);
                ReservaRepository.adicionarReserva(novaReserva);
                mostrarSucesso("Reserva salva com sucesso!");
            } else {
                // Editar reserva existente - buscar e substituir no arquivo
                List<Reserva> reservas = ReservaRepository.carregarLista();
                
                // Encontrar a reserva na lista e substituir os dados
                for (int i = 0; i < reservas.size(); i++) {
                    Reserva r = reservas.get(i);
                    if (r.getCodigoReserva().equals(reservaEditando.getCodigoReserva()) && 
                        r.getCpfUsuario().equals(reservaEditando.getCpfUsuario())) {
                        // Criar nova reserva com dados atualizados
                        java.time.LocalDate dataInicio = r.getDataInicio();
                        java.time.LocalDate dataFim = dataInicio.plusDays(dias);
                        Reserva reservaAtualizada = new Reserva(codigo, usuarioSelecionado, veiculoSelecionado, 
                                                             dataInicio, dataFim, dias);
                        reservas.set(i, reservaAtualizada);
                        break;
                    }
                }
                
                // Salvar a lista modificada no arquivo
                ReservaRepository.salvarLista(reservas);
                mostrarSucesso("Reserva atualizada com sucesso!");
                reservaEditando = null;
            }

            limparCamposReserva();
            carregarListaReservas();

        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de formato", "Verifique o valor inserido");
        }
    }

    // Método para calcular valor total automaticamente
    private void calcularValorTotal() {
        String diasText = diasAluguelField.getText();
        Veiculo veiculoSelecionado = veiculoReservaCombo.getValue();
        
        if (diasText != null && !diasText.trim().isEmpty() && veiculoSelecionado != null) {
            try {
                int dias = Integer.parseInt(diasText.trim());
                if (dias > 0) {
                    double valorTotal = dias * veiculoSelecionado.getValorDiaria();
                    valorTotalField.setText(String.format("%.2f", valorTotal));
                } else {
                    valorTotalField.clear();
                }
            } catch (NumberFormatException e) {
                valorTotalField.clear();
            }
        } else {
            valorTotalField.clear();
        }
    }

    // Métodos auxiliares
    private void carregarLista() {
        List<Veiculo> veiculos = VeiculoRepository.carregarLista();
        ObservableList<Veiculo> items = FXCollections.observableArrayList(veiculos);
        listaVeiculos.setItems(items);
    }

    private void carregarListaUsuarios() {
        List<Usuario> usuarios = UsuarioRepository.carregarLista();
        ObservableList<Usuario> items = FXCollections.observableArrayList(usuarios);
        listaUsuarios.setItems(items);
        
        // Atualizar ComboBox de reservas
        usuarioReservaCombo.setItems(items);
    }

    private void carregarListaProprietarios() {
        List<Proprietario> proprietarios = ProprietarioRepository.carregarLista();
        ObservableList<Proprietario> items = FXCollections.observableArrayList(proprietarios);
        listaProprietarios.setItems(items);
        
        // Atualizar ComboBoxes
        proprietarioVeiculoCombo.setItems(items);
        proprietarioReservaCombo.setItems(items);
    }

    private void carregarListaReservas() {
        List<Reserva> reservas = ReservaRepository.carregarLista();
        ObservableList<Reserva> items = FXCollections.observableArrayList(reservas);
        listaReservas.setItems(items);
    }

    private void limparCamposVeiculo() {
        marcaField.clear();
        modeloField.clear();
        corField.clear();
        anoField.clear();
        valorDiariaField.clear();
        proprietarioVeiculoCombo.setValue(null);
    }

    private void limparCamposUsuario() {
        nomeUsuarioField.clear();
        emailUsuarioField.clear();
        telefoneUsuarioField.clear();
        cpfUsuarioField.clear();
    }

    private void limparCamposProprietario() {
        nomeProprietarioField.clear();
        emailProprietarioField.clear();
        telefoneProprietarioField.clear();
        cpfProprietarioField.clear();
        enderecoField.clear();
    }

    private void limparCamposReserva() {
        codigoReservaField.clear();
        diasAluguelField.clear();
        valorTotalField.clear();
        usuarioReservaCombo.setValue(null);
        proprietarioReservaCombo.setValue(null);
        veiculoReservaCombo.setValue(null);
    }

    private void configurarValidacaoUsuario() {
        // Implementar validação para usuários
        nomeUsuarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Usuario.isNomeValido(newValue)) {
                nomeUsuarioField.setStyle("-fx-border-color: none;");
            } else {
                nomeUsuarioField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
        });

        emailUsuarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Usuario.isEmailValido(newValue)) {
                emailUsuarioField.setStyle("-fx-border-color: none;");
            } else {
                emailUsuarioField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
        });

        telefoneUsuarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Usuario.isTelefoneValido(newValue)) {
                telefoneUsuarioField.setStyle("-fx-border-color: none;");
            } else {
                telefoneUsuarioField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
        });

        cpfUsuarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Usuario.isCpfValido(newValue)) {
                cpfUsuarioField.setStyle("-fx-border-color: none;");
            } else {
                cpfUsuarioField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
        });
    }

    private void configurarValidacaoProprietario() {
        // Implementar validação para proprietários
        nomeProprietarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Proprietario.isNomeValido(newValue)) {
                nomeProprietarioField.setStyle("-fx-border-color: none;");
            } else {
                nomeProprietarioField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
        });

        emailProprietarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Proprietario.isEmailValido(newValue)) {
                emailProprietarioField.setStyle("-fx-border-color: none;");
            } else {
                emailProprietarioField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
        });

        telefoneProprietarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Proprietario.isTelefoneValido(newValue)) {
                telefoneProprietarioField.setStyle("-fx-border-color: none;");
            } else {
                telefoneProprietarioField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
        });

        cpfProprietarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Proprietario.isCpfValido(newValue)) {
                cpfProprietarioField.setStyle("-fx-border-color: none;");
            } else {
                cpfProprietarioField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
        });

        enderecoField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Proprietario.isEnderecoValido(newValue)) {
                enderecoField.setStyle("-fx-border-color: none;");
            } else {
                enderecoField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
        });
    }

    private void configurarValidacaoReserva() {
        // Implementar validação para reservas
        codigoReservaField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Reserva.isCodigoValido(newValue)) {
                codigoReservaField.setStyle("-fx-border-color: none;");
            } else {
                codigoReservaField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
        });

        // Validação e formatação do campo de dias
        diasAluguelField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Permitir apenas números
            if (!newValue.matches("\\d*")) {
                diasAluguelField.setText(oldValue);
                return;
            }
            
            // Validar dias
            if (Reserva.isDiasAluguelValido(newValue)) {
                diasAluguelField.setStyle("-fx-border-color: none;");
            } else {
                diasAluguelField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
            
            // Recalcular valor total quando dias mudam
            calcularValorTotal();
        });

        // Listener para recalcular valor quando veículo for selecionado
        veiculoReservaCombo.setOnAction(e -> calcularValorTotal());

        valorTotalField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Permitir apenas números, vírgula e ponto
            if (!newValue.matches("[0-9]*[,.]?[0-9]*")) {
                valorTotalField.setText(oldValue);
                return;
            }

            if (Reserva.isValorTotalValido(newValue)) {
                valorTotalField.setStyle("-fx-border-color: none;");
            } else {
                valorTotalField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
        });
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

    // Métodos de edição e exclusão
    private void editarVeiculo() {
        Veiculo selecionado = listaVeiculos.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Seleção inválida", "Por favor, selecione um veículo para editar.");
            return;
        }
        
        // Preencher os campos com os dados do veículo selecionado
        marcaField.setText(selecionado.getMarca());
        modeloField.setText(selecionado.getModelo());
        corField.setText(selecionado.getCor());
        anoField.setText(String.valueOf(selecionado.getAno()));
        valorDiariaField.setText(String.valueOf(selecionado.getValorDiaria()));
        proprietarioVeiculoCombo.setValue(selecionado.getProprietario());
        
        // Definir como editando
        veiculoEditando = selecionado;
        
        // Mudar para a aba de cadastro
        tabPane.getSelectionModel().select(0); // Primeira aba (Cadastro Veículos)
        
        mostrarSucesso("Veículo carregado para edição. Modifique os campos e clique em 'Salvar Veículo'.");
    }
    
    private void excluirVeiculo() {
        Veiculo selecionado = listaVeiculos.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Seleção inválida", "Por favor, selecione um veículo para excluir.");
            return;
        }
        
        // Confirmação de exclusão
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Excluir Veículo");
        confirmacao.setContentText("Deseja realmente excluir o veículo: " + selecionado.toString() + "?");
        
        confirmacao.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                List<Veiculo> veiculos = VeiculoRepository.carregarLista();
                veiculos.remove(selecionado);
                VeiculoRepository.salvarLista(veiculos);
                carregarLista();
                mostrarSucesso("Veículo excluído com sucesso!");
            }
        });
    }
    
    private void editarUsuario() {
        Usuario selecionado = listaUsuarios.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Seleção inválida", "Por favor, selecione um usuário para editar.");
            return;
        }
        
        // Preencher os campos com os dados do usuário selecionado
        nomeUsuarioField.setText(selecionado.getNome());
        emailUsuarioField.setText(selecionado.getEmail());
        telefoneUsuarioField.setText(selecionado.getTelefone());
        cpfUsuarioField.setText(selecionado.getCpf());
        
        // Definir como editando
        usuarioEditando = selecionado;
        
        // Mudar para a aba de cadastro de usuários
        tabPane.getSelectionModel().select(2); // Terceira aba (Cadastro Usuários)
        
        mostrarSucesso("Usuário carregado para edição. Modifique os campos e clique em 'Salvar Usuário'.");
    }
    
    private void excluirUsuario() {
        Usuario selecionado = listaUsuarios.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Seleção inválida", "Por favor, selecione um usuário para excluir.");
            return;
        }
        
        // Confirmação de exclusão
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Excluir Usuário");
        confirmacao.setContentText("Deseja realmente excluir o usuário: " + selecionado.toString() + "?");
        
        confirmacao.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                List<Usuario> usuarios = UsuarioRepository.carregarLista();
                usuarios.remove(selecionado);
                UsuarioRepository.salvarLista(usuarios);
                carregarListaUsuarios();
                mostrarSucesso("Usuário excluído com sucesso!");
            }
        });
    }
    
    private void editarProprietario() {
        Proprietario selecionado = listaProprietarios.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Seleção inválida", "Por favor, selecione um proprietário para editar.");
            return;
        }
        
        // Preencher os campos com os dados do proprietário selecionado
        nomeProprietarioField.setText(selecionado.getNome());
        emailProprietarioField.setText(selecionado.getEmail());
        telefoneProprietarioField.setText(selecionado.getTelefone());
        cpfProprietarioField.setText(selecionado.getCpf());
        enderecoField.setText(selecionado.getEndereco());
        
        // Definir como editando
        proprietarioEditando = selecionado;
        
        // Mudar para a aba de cadastro de proprietários
        tabPane.getSelectionModel().select(4); // Quinta aba (Cadastro Proprietários)
        
        mostrarSucesso("Proprietário carregado para edição. Modifique os campos e clique em 'Salvar Proprietário'.");
    }
    
    private void excluirProprietario() {
        Proprietario selecionado = listaProprietarios.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Seleção inválida", "Por favor, selecione um proprietário para excluir.");
            return;
        }
        
        // Confirmação de exclusão
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Excluir Proprietário");
        confirmacao.setContentText("Deseja realmente excluir o proprietário: " + selecionado.toString() + "?");
        
        confirmacao.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                List<Proprietario> proprietarios = ProprietarioRepository.carregarLista();
                proprietarios.remove(selecionado);
                ProprietarioRepository.salvarLista(proprietarios);
                carregarListaProprietarios();
                mostrarSucesso("Proprietário excluído com sucesso!");
            }
        });
    }
    
    private void editarReserva() {
        Reserva selecionada = listaReservas.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Seleção inválida", "Por favor, selecione uma reserva para editar.");
            return;
        }
        
        // Preencher os campos com os dados da reserva selecionada
        codigoReservaField.setText(selecionada.getCodigoReserva());
        diasAluguelField.setText(String.valueOf(selecionada.getDiasAluguel()));
        valorTotalField.setText(String.format("%.2f", selecionada.getValorTotal()));
        
        // Preencher ComboBoxes se houver relacionamentos
        if (selecionada.getUsuario() != null) {
            usuarioReservaCombo.setValue(selecionada.getUsuario());
        }
        if (selecionada.getVeiculo() != null) {
            veiculoReservaCombo.setValue(selecionada.getVeiculo());
            // Preencher proprietário baseado no veículo
            if (selecionada.getVeiculo().getProprietario() != null) {
                proprietarioReservaCombo.setValue(selecionada.getVeiculo().getProprietario());
                // Recarregar veículos do proprietário
                List<Veiculo> veiculosDoProprietario = VeiculoRepository.carregarVeiculosPorProprietario(selecionada.getVeiculo().getProprietario());
                ObservableList<Veiculo> veiculosItems = FXCollections.observableArrayList(veiculosDoProprietario);
                veiculoReservaCombo.setItems(veiculosItems);
                veiculoReservaCombo.setValue(selecionada.getVeiculo());
            }
        }
        
        // Definir como editando
        reservaEditando = selecionada;
        
        // Mudar para a aba de cadastro de reservas
        tabPane.getSelectionModel().select(6); // Sétima aba (Cadastro Reservas)
        
        mostrarSucesso("Reserva carregada para edição. Modifique os campos e clique em 'Salvar Reserva'.");
    }
    
    private void excluirReserva() {
        Reserva selecionada = listaReservas.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Seleção inválida", "Por favor, selecione uma reserva para excluir.");
            return;
        }
        
        // Confirmação de exclusão
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Excluir Reserva");
        confirmacao.setContentText("Deseja realmente excluir a reserva: " + selecionada.toString() + "?");
        
        confirmacao.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                List<Reserva> reservas = ReservaRepository.carregarLista();
                reservas.remove(selecionada);
                ReservaRepository.salvarLista(reservas);
                carregarListaReservas();
                mostrarSucesso("Reserva excluída com sucesso!");
            }
        });
    }

    // Método para atualizar relacionamentos quando proprietário é editado
    private void atualizarRelacionamentosProprietario(Proprietario proprietarioAntigo, Proprietario proprietarioNovo) {
        // Atualizar veículos que pertencem a este proprietário
        List<Veiculo> veiculos = VeiculoRepository.carregarLista();
        boolean veiculosAtualizados = false;
        
        for (int i = 0; i < veiculos.size(); i++) {
            Veiculo veiculo = veiculos.get(i);
            if (veiculo.getProprietario() != null && 
                veiculo.getProprietario().getCpf().equals(proprietarioAntigo.getCpf())) {
                // Criar novo veículo com proprietário atualizado
                Veiculo veiculoAtualizado = new Veiculo(
                    veiculo.getMarca(), 
                    veiculo.getModelo(), 
                    veiculo.getCor(), 
                    veiculo.getAno(), 
                    veiculo.getValorDiaria(), 
                    proprietarioNovo
                );
                veiculos.set(i, veiculoAtualizado);
                veiculosAtualizados = true;
            }
        }
        
        // Salvar veículos atualizados se houver mudanças
        if (veiculosAtualizados) {
            VeiculoRepository.salvarLista(veiculos);
            // Recarregar listas na interface para refletir mudanças
            Platform.runLater(() -> {
                carregarLista(); // Recarregar lista de veículos
                carregarListaProprietarios(); // Recarregar ComboBoxes
            });
        }
        
        // Atualizar reservas que usam veículos deste proprietário
        List<Reserva> reservas = ReservaRepository.carregarLista();
        boolean reservasAtualizadas = false;
        
        for (int i = 0; i < reservas.size(); i++) {
            Reserva reserva = reservas.get(i);
            if (reserva.getVeiculo() != null && 
                reserva.getVeiculo().getProprietario() != null &&
                reserva.getVeiculo().getProprietario().getCpf().equals(proprietarioAntigo.getCpf())) {
                // Criar novo veículo atualizado para a reserva
                Veiculo veiculoAtualizado = new Veiculo(
                    reserva.getVeiculo().getMarca(),
                    reserva.getVeiculo().getModelo(),
                    reserva.getVeiculo().getCor(),
                    reserva.getVeiculo().getAno(),
                    reserva.getVeiculo().getValorDiaria(),
                    proprietarioNovo
                );
                
                // Criar nova reserva com veículo atualizado
                Reserva reservaAtualizada = new Reserva(
                    reserva.getCodigoReserva(),
                    reserva.getUsuario(),
                    veiculoAtualizado,
                    reserva.getDataInicio(),
                    reserva.getDataFim(),
                    reserva.getDiasAluguel()
                );
                reservas.set(i, reservaAtualizada);
                reservasAtualizadas = true;
            }
        }
        
        // Salvar reservas atualizadas se houver mudanças
        if (reservasAtualizadas) {
            ReservaRepository.salvarLista(reservas);
            // Recarregar listas na interface para refletir mudanças
            Platform.runLater(() -> {
                carregarListaReservas(); // Recarregar lista de reservas
            });
        }
    }

    // Método para atualizar relacionamentos quando usuário é editado
    private void atualizarRelacionamentosUsuario(Usuario usuarioAntigo, Usuario usuarioNovo) {
        List<Reserva> reservas = ReservaRepository.carregarLista();
        boolean reservasAtualizadas = false;
        
        for (int i = 0; i < reservas.size(); i++) {
            Reserva reserva = reservas.get(i);
            if (reserva.getUsuario() != null && 
                reserva.getUsuario().getCpf().equals(usuarioAntigo.getCpf())) {
                // Criar nova reserva com usuário atualizado
                Reserva reservaAtualizada = new Reserva(
                    reserva.getCodigoReserva(),
                    usuarioNovo,
                    reserva.getVeiculo(),
                    reserva.getDataInicio(),
                    reserva.getDataFim(),
                    reserva.getDiasAluguel()
                );
                reservas.set(i, reservaAtualizada);
                reservasAtualizadas = true;
            }
        }
        
        // Salvar reservas atualizadas se houver mudanças
        if (reservasAtualizadas) {
            ReservaRepository.salvarLista(reservas);
            // Recarregar listas na interface para refletir mudanças
            Platform.runLater(() -> {
                carregarListaReservas(); // Recarregar lista de reservas
            });
        }
    }

    // Método para atualizar relacionamentos quando veículo é editado
    private void atualizarRelacionamentosVeiculo(Veiculo veiculoAntigo, Veiculo veiculoNovo) {
        List<Reserva> reservas = ReservaRepository.carregarLista();
        boolean reservasAtualizadas = false;
        
        for (int i = 0; i < reservas.size(); i++) {
            Reserva reserva = reservas.get(i);
            if (reserva.getVeiculo() != null && 
                reserva.getVeiculo().getMarca().equals(veiculoAntigo.getMarca()) &&
                reserva.getVeiculo().getModelo().equals(veiculoAntigo.getModelo()) &&
                reserva.getVeiculo().getAno() == veiculoAntigo.getAno()) {
                // Criar nova reserva com veículo atualizado
                Reserva reservaAtualizada = new Reserva(
                    reserva.getCodigoReserva(),
                    reserva.getUsuario(),
                    veiculoNovo,
                    reserva.getDataInicio(),
                    reserva.getDataFim(),
                    reserva.getDiasAluguel()
                );
                reservas.set(i, reservaAtualizada);
                reservasAtualizadas = true;
            }
        }
        
        // Salvar reservas atualizadas se houver mudanças
        if (reservasAtualizadas) {
            ReservaRepository.salvarLista(reservas);
            // Recarregar listas na interface para refletir mudanças
            Platform.runLater(() -> {
                carregarListaReservas(); // Recarregar lista de reservas
            });
        }
    }

    public static void main(String[] args) {
        launch();
    }
}