# 🚗 DriveNow - JavaFX

<div align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/JavaFX-007396?style=for-the-badge&logo=java&logoColor=white" alt="JavaFX">
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white" alt="Maven">
  <img src="https://img.shields.io/badge/POO-Object%20Oriented-blue?style=for-the-badge" alt="OOP">
</div>

## 📋 Sobre o Projeto

O **DriveNow JavaFX** é uma aplicação desktop desenvolvida como projeto acadêmico para a disciplina de **Programação Orientada a Objetos (POO)**. O sistema implementa um **gerenciador simples de aluguel de veículos** com interface gráfica intuitiva, demonstrando conceitos fundamentais da POO através de um CRUD completo.

### 🎯 Objetivos Acadêmicos

- Aplicar os **4 pilares da POO**: Encapsulamento, Herança, Polimorfismo e Abstração
- Desenvolver interface gráfica com **JavaFX** e layout responsivo
- Implementar **persistência de dados** com serialização de objetos
- Aplicar **validações em tempo real** nos formulários
- Demonstrar **relacionamentos entre classes** (Usuario ↔ Reserva ↔ Veiculo ↔ Proprietario)
- Gerenciar **operações CRUD** (Create, Read, Update, Delete)

### 🌟 Funcionalidades Implementadas

#### 👤 **Gestão de Usuários**
- Cadastro com validação de nome, email, telefone e CPF
- Listagem e visualização de todos os usuários
- Edição de dados de usuários existentes
- Exclusão de usuários com confirmação

#### 🏢 **Gestão de Proprietários**
- Cadastro com dados pessoais e endereço
- Validação de CPF e telefone únicos
- Relacionamento com veículos cadastrados
- Operações completas de CRUD

#### 🚙 **Gestão de Veículos**
- Cadastro com marca, modelo, cor, ano e valor da diária
- Validação de campos obrigatórios e formatos
- **Relacionamento obrigatório** com proprietário
- Controle de disponibilidade automático

#### 📅 **Sistema de Reservas**
- Código de reserva único (geração automática disponível)
- Seleção de usuário e proprietário
- **Filtro inteligente**: veículos aparecem apenas do proprietário selecionado
- Cálculo automático do valor total (dias × valor diário)
- Controle de período e status da reserva

#### 💾 **Persistência de Dados**
- Arquivos binários (.dat) para cada entidade
- Serialização automática de objetos Java
- Carregamento na inicialização da aplicação
- Atualização em tempo real dos relacionamentos

## 🏗️ Arquitetura Real do Projeto

### 📦 Estrutura Simplificada

```
src/main/java/com/example/ra3projeto/
├── Main.java                    # Classe principal com toda a interface
├── Veiculo.java                # Entidade Veículo + validações
├── Usuario.java                # Entidade Usuário + validações  
├── Proprietario.java           # Entidade Proprietário + validações
├── Reserva.java                # Entidade Reserva + validações
├── VeiculoRepository.java      # Persistência de veículos
├── UsuarioRepository.java      # Persistência de usuários
├── ProprietarioRepository.java # Persistência de proprietários
├── ReservaRepository.java      # Persistência de reservas
└── resources/
    └── styles.css              # Estilos CSS personalizados
```

### 🎨 **Arquitetura de Interface**
- **Uma única classe Main** contendo toda a lógica da interface
- **TabPane** com 8 abas organizadas por funcionalidade:
  - Cadastro de Veículos / Gerenciar Veículos  
  - Cadastro de Usuários / Gerenciar Usuários
  - Cadastro de Proprietários / Gerenciar Proprietários
  - Cadastro de Reservas / Gerenciar Reservas

### 🎨 **Conceitos de POO Demonstrados**

#### 📚 **Encapsulamento**
```java
public class Veiculo implements Serializable {
    private String marca;           // Atributos privados
    private String modelo;
    private double valorDiaria;
    private Proprietario proprietario;
    
    public void setMarca(String marca) {
        validarMarca(marca);        // Validação no setter
        this.marca = marca.trim();
    }
    
    private void validarMarca(String marca) {  // Métodos de validação privados
        if (marca == null || marca.trim().length() < 2) {
            throw new IllegalArgumentException("Marca deve ter pelo menos 2 caracteres");
        }
    }
}
```

#### � **Relacionamentos e Composição**
```java
public class Reserva implements Serializable {
    private Usuario usuario;        // Composição: Reserva "tem um" Usuario
    private Veiculo veiculo;       // Composição: Reserva "tem um" Veiculo
    
    // Construtor que estabelece relacionamentos
    public Reserva(String codigo, Usuario usuario, Veiculo veiculo, LocalDate inicio, LocalDate fim, int dias) {
        this.usuario = usuario;
        this.veiculo = veiculo;
        this.valorTotal = dias * veiculo.getValorDiaria(); // Uso do relacionamento
    }
}
```

#### 🔄 **Polimorfismo com Serializable**
```java
// Todas as entidades implementam a mesma interface
public class Usuario implements Serializable { ... }
public class Veiculo implements Serializable { ... }
public class Proprietario implements Serializable { ... }
public class Reserva implements Serializable { ... }

// Polimorfismo nos repositórios
public static void salvarLista(List<? extends Serializable> lista) {
    // Mesmo método para diferentes tipos de objetos
}
```

#### 🏭 **Abstração através de Interfaces**
```java
// Interface comum para persistência
public interface Serializable {
    // Métodos abstratos implementados por todas as entidades
}

// Validações estáticas abstraindo complexidade
public static boolean isCpfValido(String cpf);
public static boolean isEmailValido(String email);
public static boolean isAnoValido(String ano);
```

## 🛠️ Tecnologias Utilizadas

### ⚙️ **Core Technologies**
- **Java 21.0.7+**: Linguagem de programação principal
- **JavaFX**: Framework para interface gráfica desktop
- **Maven**: Gerenciamento de dependências e build
- **CSS**: Estilização customizada da interface

### 📚 **Principais Dependências (pom.xml)**
```xml
<dependencies>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>21.0.7</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>21.0.7</version>
    </dependency>
    <dependency>
        <groupId>org.kordamp.ikonli</groupId>
        <artifactId>ikonli-javafx</artifactId>
    </dependency>
    <dependency>
        <groupId>org.kordamp.bootstrapfx</groupId>
        <artifactId>bootstrapfx-core</artifactId>
    </dependency>
</dependencies>
```

## 🚀 Como Executar o Projeto

### 📋 Pré-requisitos

- **Java JDK 21.0.7+** instalado
- **Maven 3.6+** instalado  
- **IDE** recomendada: IntelliJ IDEA, Eclipse ou VS Code

### 💻 Passos para Execução

1. **Clone o repositório**
```bash
git clone https://github.com/Valentin-Rojas/JavaFx-DriveNow.git
cd JavaFx-DriveNow
```

2. **Compile o projeto**
```bash
mvn clean compile
```

3. **Execute a aplicação**
```bash
mvn javafx:run
```

### 🔧 Configuração Alternativa

#### Executar diretamente pela classe Main
```bash
# Compile primeiro
mvn compile

# Execute a classe principal
java -cp target/classes com.example.ra3projeto.Main
```

## 📁 Estrutura de Arquivos Gerados

O sistema gera automaticamente arquivos de dados na **raiz do projeto**:

```
📁 JavaFx-DriveNow/
├── usuarios.dat          # Dados serializados dos usuários
├── proprietarios.dat     # Dados serializados dos proprietários  
├── veiculos.dat         # Dados serializados dos veículos
├── reservas.dat         # Dados serializados das reservas
├── src/main/java/...    # Código fonte
└── pom.xml              # Configurações Maven
```

## 🎨 Interface do Usuário

### 🖥️ **Layout da Aplicação**
- **Janela principal**: 1300x700 pixels
- **TabPane** com 8 abas organizadas em pares (Cadastro/Gerenciar)
- **Design responsivo** com ScrollPane em todas as abas
- **Formulários validados** em tempo real
- **Botões com ícones** e cores diferenciadas por ação

### 🎭 **Recursos da Interface**

#### ✅ **Validação em Tempo Real**
```java
// Exemplo: Validação de CPF em tempo real
cpfField.textProperty().addListener((observable, oldValue, newValue) -> {
    if (Usuario.isCpfValido(newValue)) {
        cpfField.setStyle("-fx-border-color: none;");
    } else {
        cpfField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
    }
});
```

#### 🔗 **Relacionamentos Inteligentes**
- ComboBox de proprietários carrega automaticamente
- Seleção de proprietário filtra veículos disponíveis  
- Cálculo automático do valor total das reservas
- Atualização em cascata dos relacionamentos

#### 🎨 **Temas e Estilos CSS**
```css
.button {
    -fx-background-color: #4CAF50;
    -fx-text-fill: white;
    -fx-font-weight: bold;
    -fx-background-radius: 5px;
}

.button.danger { -fx-background-color: #f44336; }
.button.warning { -fx-background-color: #ffc107; }
.button.success { -fx-background-color: #28a745; }
```

## 🧪 Exemplos de Código Implementados

### 👤 **Cadastro de Usuário com Validação**
```java
private void salvarUsuario() {
    String nome = nomeUsuarioField.getText();
    String email = emailUsuarioField.getText();
    String telefone = telefoneUsuarioField.getText();
    String cpf = cpfUsuarioField.getText();

    // Validações antes de salvar
    if (!Usuario.isNomeValido(nome)) {
        mostrarAlerta("Nome inválido", "Nome deve ter pelo menos 2 caracteres");
        return;
    }
    
    if (!Usuario.isEmailValido(email)) {
        mostrarAlerta("Email inválido", "Digite um email válido");
        return;
    }

    // Criar e salvar usuario
    Usuario novoUsuario = new Usuario(nome, email, telefone, cpf);
    UsuarioRepository.adicionarUsuario(novoUsuario);
    mostrarSucesso("Usuário salvo com sucesso!");
}
```

### 🚗 **Cadastro de Veículo com Relacionamento**
```java
private void salvarVeiculo() {
    String marca = marcaField.getText();
    String modelo = modeloField.getText();
    String cor = corField.getText();
    int ano = Integer.parseInt(anoField.getText());
    double valorDiaria = Double.parseDouble(valorDiariaField.getText().replace(",", "."));
    Proprietario proprietarioSelecionado = proprietarioVeiculoCombo.getValue();

    // Criar veiculo com relacionamento
    Veiculo novoVeiculo = new Veiculo(marca, modelo, cor, ano, valorDiaria, proprietarioSelecionado);
    VeiculoRepository.adicionarVeiculo(novoVeiculo);
    
    // Atualizar interface
    carregarLista();
    limparCamposVeiculo();
}
```

### 📅 **Sistema de Reservas com Cálculo Automático**
```java
private void salvarReserva() {
    Usuario usuarioSelecionado = usuarioReservaCombo.getValue();
    Veiculo veiculoSelecionado = veiculoReservaCombo.getValue();
    int dias = Integer.parseInt(diasAluguelField.getText());
    
    // Construtor calcula valor automaticamente
    LocalDate hoje = LocalDate.now();
    LocalDate dataFim = hoje.plusDays(dias);
    
    Reserva novaReserva = new Reserva(codigo, usuarioSelecionado, veiculoSelecionado, 
                                     hoje, dataFim, dias);
    // Valor total = dias * veiculo.getValorDiaria() - calculado internamente
    
    ReservaRepository.adicionarReserva(novaReserva);
}
```

### � **Relacionamento Inteligente entre Proprietário e Veículos**
```java
// Configurar filtro de veículos por proprietário nas reservas
proprietarioReservaCombo.setOnAction(e -> {
    Proprietario proprietarioSelecionado = proprietarioReservaCombo.getValue();
    if (proprietarioSelecionado != null) {
        // Filtrar apenas veículos deste proprietário
        List<Veiculo> veiculosDoProprietario = VeiculoRepository
            .carregarVeiculosPorProprietario(proprietarioSelecionado);
        ObservableList<Veiculo> veiculosItems = FXCollections
            .observableArrayList(veiculosDoProprietario);
        veiculoReservaCombo.setItems(veiculosItems);
        veiculoReservaCombo.setValue(null); // Limpar seleção anterior
    }
});
```

## 📚 Conceitos Acadêmicos Aplicados

### 🔍 **Programação Orientada a Objetos**

#### **1. Encapsulamento**
- ✅ Atributos privados com getters/setters
- ✅ Validação de dados nos métodos modificadores
- ✅ Ocultação da implementação interna

#### **2. Relacionamentos/Composição**
- ✅ Reserva "tem um" Usuario e "tem um" Veiculo  
- ✅ Veiculo "pertence a" Proprietario
- ✅ Atualização em cascata dos relacionamentos

#### **3. Polimorfismo**
- ✅ Implementação da interface Serializable
- ✅ Métodos toString() personalizados para cada classe
- ✅ Validações estáticas uniformes

#### **4. Abstração**
- ✅ Métodos estáticos para validações complexas
- ✅ Interfaces de repositório para persistência
- ✅ Encapsulamento da lógica de negócio

### 🎯 **Padrões e Boas Práticas Implementadas**

#### **📦 Repository Pattern**
```java
public class VeiculoRepository {
    private static final String ARQUIVO = "veiculos.dat";
    
    public static void salvarLista(List<Veiculo> lista) { ... }
    public static List<Veiculo> carregarLista() { ... }
    public static void adicionarVeiculo(Veiculo novoVeiculo) { ... }
    public static List<Veiculo> carregarVeiculosPorProprietario(Proprietario proprietario) { ... }
}
```

#### **🔒 Validação Centralizada**
```java
public class Usuario implements Serializable {
    // Validações estáticas reutilizáveis
    public static boolean isNomeValido(String nome) {
        return nome != null && !nome.trim().isEmpty() && nome.trim().length() >= 2;
    }
    
    public static boolean isEmailValido(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean isCpfValido(String cpf) {
        return cpf != null && CPF_PATTERN.matcher(cpf).matches();
    }
}
```

#### **⚡ Atualização Reativa da Interface**
```java
// Validação em tempo real com listeners
nomeField.textProperty().addListener((observable, oldValue, newValue) -> {
    if (Usuario.isNomeValido(newValue)) {
        nomeField.setStyle("-fx-border-color: none;");
    } else {
        nomeField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
    }
});
```

## 📈 Funcionalidades Destacadas

### ⚡ **Características Técnicas do Sistema**

- **Interface única**: Todo o sistema em uma classe Main.java de ~1800 linhas
- **8 Abas organizadas**: Cadastro e Gerenciamento para cada entidade
- **Validação em tempo real**: Bordas vermelhas para campos inválidos
- **Relacionamentos inteligentes**: ComboBoxes que se atualizam automaticamente
- **Persistência simples**: Arquivos .dat com serialização Java nativa
- **Operações CRUD completas**: Create, Read, Update, Delete para todas as entidades

### � **Funcionalidades Especiais**

#### **🎲 Gerador de Código de Reserva**
```java
public static String gerarCodigoReserva() {
    return "RES" + System.currentTimeMillis();
}
```

#### **💰 Cálculo Automático de Valores**
```java
// Nas reservas: valor = dias × valor_diário_do_veículo
public Reserva(String codigo, Usuario usuario, Veiculo veiculo, LocalDate inicio, LocalDate fim, int dias) {
    this.valorTotal = dias * veiculo.getValorDiaria(); // Cálculo automático
}
```

#### **🔄 Atualização em Cascata**
```java
// Quando um proprietário é editado, todos os seus veículos são atualizados automaticamente
// Quando um veículo é editado, todas as reservas que o usam são atualizadas
// Relacionamentos sempre consistentes
```

## 🎓 Valor Acadêmico do Projeto

### 📊 **Complexidade Apropriada para POO**
- **Classes bem definidas**: 4 entidades principais + repositórios
- **Relacionamentos reais**: Proprietário → Veículo → Reserva ← Usuário  
- **Validações consistentes**: Métodos estáticos reutilizáveis
- **Interface responsiva**: JavaFX com CSS personalizado
- **Persistência funcional**: Serialização de objetos complexos

### 🎯 **Conceitos de POO Claramente Demonstrados**
1. **Encapsulamento**: Atributos privados, getters/setters com validação
2. **Composição**: Reserva composta por Usuario + Veiculo
3. **Polimorfismo**: Interface Serializable, métodos toString personalizados
4. **Abstração**: Validações estáticas, repositories abstraem persistência

### 🏆 **Diferenciais do Projeto**
- ✅ **Sistema funcional completo** - não é apenas um exemplo teórico
- ✅ **Interface gráfica profissional** - demonstra conhecimento além da POO
- ✅ **Relacionamentos complexos** - vai além de exemplos simples de livros
- ✅ **Validações robustas** - CPF, email, telefone com regex
- ✅ **Persistência real** - dados salvos entre execuções
- ✅ **Código limpo e organizado** - fácil de entender e manter

## 🚀 Possíveis Expansões Futuras

### � **Melhorias Técnicas**
- [ ] Migração para banco de dados (H2, SQLite)
- [ ] Implementação de testes unitários (JUnit)
- [ ] Separação em arquitetura MVC completa
- [ ] API REST para integração externa
- [ ] Sistema de logs e auditoria

### 🌟 **Novas Funcionalidades**
- [ ] Sistema de autenticação e perfis de usuário
- [ ] Relatórios gráficos com JavaFX Charts
- [ ] Exportação para PDF/Excel
- [ ] Sistema de notificações e lembretes
- [ ] Integração com APIs de mapas
- [ ] Dashboard com estatísticas em tempo real

## 👥 Informações do Projeto

### 🎓 **Desenvolvedores**
- **Nome**: Andryus Zolet, Eduardo Ferreira, Nicolas Medeiros e Valentin Rojas
- **GitHub**: [@Andryus Zolet](https://github.com/AndryusZolet), [@Nicolas Medeiros](https://github.com/Nicolas-Medeiros), [@Valentin-Rojas](https://github.com/Valentin-Rojas)
- **Disciplina**: Programação Orientada a Objetos
- **Projeto**: Sistema de Gestão de Aluguel de Veículos em JavaFX

### 🤝 **Projeto Relacionado**
Este projeto JavaFX é uma versão desktop baseada no projeto web **DriveNow**, desenvolvido em equipe:
- **Repositório Web**: [AndryusZolet/DriveNow](https://github.com/AndryusZolet/DriveNow)
- **Tecnologias Web**: PHP, MySQL, HTML, CSS, JavaScript
- **Equipe Web**: Andryus Zolet, Eduardo Ferreira, Leonardo Henrique, Nicolas Medeiros e Valentin Rojas

### 📜 **Licença Acadêmica**
Este projeto foi desenvolvido exclusivamente para fins educacionais como parte do aprendizado de **Programação Orientada a Objetos**. O código é aberto para consulta e aprendizado.

---

<div align="center">
  <h3>🎯 Projeto Acadêmico de POO - JavaFX DriveNow</h3>
  <p>
    <strong>Demonstrando conceitos fundamentais da Programação Orientada a Objetos<br>
    através de um sistema real e funcional de gestão de aluguel de veículos</strong>
  </p>
  
  ⭐ **Este projeto demonstra aplicação prática dos 4 pilares da POO** ⭐
</div>

---

## 📊 Estatísticas do Projeto

![Status](https://img.shields.io/badge/Status-Concluído-brightgreen?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-17+-blue?style=for-the-badge)
![JavaFX](https://img.shields.io/badge/JavaFX-17.0.2-orange?style=for-the-badge)
![POO](https://img.shields.io/badge/POO-4%20Pilares-purple?style=for-the-badge)

**Projeto Acadêmico Desenvolvido no 4 período**: - Primeiro semestre de 2025 (2025/1)
