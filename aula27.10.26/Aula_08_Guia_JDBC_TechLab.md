# Guia de Laboratório --- Aula 08

## Introdução ao JDBC e Conexão com Banco de Dados no TechLab Inventory

## 1. Introdução e Objetivos da Aprendizagem

Seja bem-vindo à oitava sessão prática do nosso laboratório de
Engenharia de Software. Até a avaliação parcial (AV1), concentramos
nossos esforços no domínio das estruturas fundamentais da Orientação a
Objetos: encapsulamento do estado interno, invocação de métodos de
negócio e manipulação de instâncias alocadas temporariamente na memória
volátil (*Heap*) da Java Virtual Machine (JVM).

Sistemas corporativos como o **TechLab Inventory & Service Desk** exigem
que as informações sobre infraestrutura, servidores, estações de
trabalho e chamados operacionais permaneçam salvas de forma íntegra e
definitiva. O encerramento do processo da JVM ou um reinício do servidor
não podem resultar na perda dos dados cadastrados.

Nesta aula, migraremos da alocação volátil em memória principal para a
persistência física em banco de dados relacional, utilizando a API
**JDBC (Java Database Connectivity)**.

### Diferença crítica: Heap volátil vs. tabela relacional

-   **Memória Heap:** possui ciclo de vida atrelado ao processo da JVM.
    Quando a aplicação é finalizada, objetos não salvos em meio físico
    deixam de estar disponíveis.
-   **Tabela relacional:** armazena o estado dos objetos em disco de
    forma permanente. O mapeamento de atributos para colunas e linhas
    permite que os dados sobrevivam ao término da aplicação.

### Objetivos pedagógicos

1.  **Entender a arquitetura JDBC:** conhecer drivers e interfaces da
    API `java.sql` e como o Java SE se comunica com bancos relacionais.
2.  **Garantir segurança com `PreparedStatement`:** utilizar
    placeholders (`?`) para reduzir o risco de SQL Injection e permitir
    a reutilização de consultas preparadas.
3.  **Gerenciar recursos com `try-with-resources`:** fechar conexões e
    objetos de execução automaticamente, evitando vazamentos de conexão
    (*connection leaks*).
4.  **Implementar o padrão DAO (*Data Access Object*):** criar a camada
    de persistência do TechLab Inventory, isolando o SQL e os
    mapeamentos do modelo de domínio.

## 2. Tutorial explicativo: conceitos fundamentais da persistência com JDBC

### 2.1 Arquitetura JDBC do Java SE

A API `java.sql` padroniza o acesso a bancos relacionais,
independentemente do fornecedor (SQLite, MySQL, PostgreSQL ou Oracle).

  -----------------------------------------------------------------------
  Componente              Responsabilidade        Analogia
  ----------------------- ----------------------- -----------------------
  `DriverManager`         Gerencia os drivers     Telefonista que
                          JDBC e estabelece       localiza a central e
                          conexões a partir de    inicia a chamada.
                          uma URL.                

  `Connection`            Representa uma sessão   Linha telefônica
                          de comunicação com o    dedicada.
                          banco de dados.         

  `Statement`             Envia instruções SQL    Mensageiro que entrega
                          estáticas, sem          uma ordem textual.
                          parâmetros.             

  `PreparedStatement`     Permite consultas       Formulário padronizado
                          parametrizadas com      com campos delimitados.
                          placeholders (`?`).     

  `ResultSet`             Representa os           Relatório com os dados
                          resultados de uma       recuperados.
                          consulta, permitindo    
                          ler as linhas           
                          sequencialmente.        
  -----------------------------------------------------------------------

### 2.2 Segurança e desempenho: `PreparedStatement` vs. `Statement`

Usar `Statement` com concatenação de strings, por exemplo:

``` java
"WHERE patrimonio = '" + entrada + "'"
```

pode permitir **SQL Injection**, pois uma entrada maliciosa pode alterar
a consulta. Além disso, a instrução pode precisar ser analisada e
compilada a cada execução.

O `PreparedStatement` permite separar a estrutura SQL dos valores
recebidos.

  -----------------------------------------------------------------------
  Critério                `Statement`             `PreparedStatement`
  ----------------------- ----------------------- -----------------------
  Segurança               Pode ser vulnerável     Usa parâmetros (`?`)
                          quando há concatenação  tratados como valores.
                          de entradas.            

  Preparação SQL          A consulta pode ser     A estrutura da consulta
                          analisada a cada        é preparada para
                          execução.               execução.

  Desempenho em loops     Pode repetir o custo de Pode reutilizar a
                          análise e compilação.   consulta alterando os
                                                  parâmetros.

  Legibilidade            Pode exigir             Mantém a SQL organizada
                          concatenações e         e os valores separados.
                          tratamento de aspas.    
  -----------------------------------------------------------------------

### 2.3 Gerenciamento seguro: `try-with-resources` e `AutoCloseable`

Conexões, instruções e resultados consomem recursos do sistema. Se não
forem fechados, podem ocorrer vazamentos de recursos (*connection
leaks*).

No Java 7 ou superior, objetos que implementam `AutoCloseable`,
declarados no cabeçalho do `try`, são fechados automaticamente ao final
do bloco.

``` java
try (Connection conn = ConexaoBanco.obterConexao();
     PreparedStatement stmt = conn.prepareStatement(sql);
     ResultSet rs = stmt.executeQuery()) {

    while (rs.next()) {
        // Processamento dos dados...
    }
} // rs, stmt e conn são fechados automaticamente.
```

### 2.4 Mapeamento objeto-relacional básico

  ------------------------------------------------------------------------------------------------------------------
  Classe Java         Tabela         Atributo              Coluna SQL                  Restrição
  ------------------- -------------- --------------------- --------------------------- -----------------------------
  `AtivoTI`           `ativos`       `id`                  `id INTEGER`                `PRIMARY KEY AUTOINCREMENT`

  `AtivoTI`           `ativos`       `patrimonio`          `patrimonio TEXT`           `NOT NULL`, `UNIQUE`

  `AtivoTI`           `ativos`       `nome`                `nome TEXT`                 `NOT NULL`

  `AtivoTI`           `ativos`       `categoria`           `categoria TEXT`            `NOT NULL`

  `Servidor`          `ativos`       `detalheEspecifico`   `detalhe_especifico TEXT`   Pode ser nulo
  (especialização)                                                                     

  `EstacaoTrabalho`   `ativos`       `detalheEspecifico`   `detalhe_especifico TEXT`   Pode ser nulo
  (especialização)                                                                     

  `ChamadoSuporte`    `chamados`     `id`                  `id INTEGER`                `PRIMARY KEY AUTOINCREMENT`

  `ChamadoSuporte`    `chamados`     `descricao`           `descricao TEXT`            `NOT NULL`

  `ChamadoSuporte`    `chamados`     `ativoId`             `ativo_id INTEGER`          Chave estrangeira para
                                                                                       `ativos(id)`
  ------------------------------------------------------------------------------------------------------------------

## 3. Requisitos da atividade prática: TechLab Inventory (Aula 08)

Os componentes de infraestrutura, modelagem e acesso a dados devem ser
construídos individualmente ou em dupla.

### 3.1 Infraestrutura de conexão (`ConexaoBanco.java`)

Criar uma classe utilitária de conexão desacoplada com os métodos:

``` java
public static Connection obterConexao() throws SQLException
public static void inicializarBanco()
```

`inicializarBanco()` deve criar automaticamente o esquema na primeira
execução, evitando erros de tabela inexistente.

### 3.2 Modelagem do banco de dados (DDL)

``` sql
-- Criação do esquema relacional no SQLite / MySQL
CREATE TABLE IF NOT EXISTS ativos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    patrimonio TEXT NOT NULL UNIQUE,
    nome TEXT NOT NULL,
    categoria TEXT NOT NULL,
    detalhe_especifico TEXT
);

CREATE TABLE IF NOT EXISTS chamados (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    descricao TEXT NOT NULL,
    ativo_id INTEGER NOT NULL,
    FOREIGN KEY (ativo_id) REFERENCES ativos(id) ON DELETE CASCADE
);
```

### 3.3 Camada de acesso a dados (`AtivoDAO.java`)

Implementar a classe DAO responsável pela persistência e recuperação dos
ativos de TI, respeitando as assinaturas:

``` java
public void salvar(AtivoTI ativo) throws SQLException
public List<AtivoTI> listarTodos() throws SQLException
```

Requisitos: - Usar `try-with-resources` e `PreparedStatement`. -
Repassar exceções SQL com `throws SQLException` para que a camada
solicitante possa tratá-las.

## 4. Código consolidado e compilável (Java SE)

### 4.1 Utilitário de conexão: `ConexaoBanco.java`

``` java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBanco {

    private static final String URL = "jdbc:sqlite:techlab.db";

    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void inicializarBanco() {
        String ddlAtivos = "CREATE TABLE IF NOT EXISTS ativos ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "patrimonio TEXT NOT NULL UNIQUE, "
            + "nome TEXT NOT NULL, "
            + "categoria TEXT NOT NULL, "
            + "detalhe_especifico TEXT);";

        String ddlChamados = "CREATE TABLE IF NOT EXISTS chamados ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "descricao TEXT NOT NULL, "
            + "ativo_id INTEGER NOT NULL, "
            + "FOREIGN KEY (ativo_id) REFERENCES ativos(id));";

        try (Connection conexao = obterConexao();
             Statement stmt = conexao.createStatement()) {

            stmt.execute(ddlAtivos);
            stmt.execute(ddlChamados);
            System.out.println("[INFO] Base de dados e tabelas verificadas/inicializadas com sucesso.");

        } catch (SQLException e) {
            System.err.println("[ERRO CRÍTICO] Falha ao inicializar o banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao inicializar o banco de dados.", e);
        }
    }
}
```

### 4.2 Modelo de domínio: `AtivoTI.java`

``` java
public class AtivoTI {

    private int id;
    private String patrimonio;
    private String nome;
    private String categoria;

    public AtivoTI(String patrimonio, String nome, String categoria) {
        this.patrimonio = patrimonio;
        this.nome = nome;
        this.categoria = categoria;
    }

    public AtivoTI(int id, String patrimonio, String nome, String categoria) {
        this(patrimonio, nome, categoria);
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getPatrimonio() {
        return this.patrimonio;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCategoria() {
        return this.categoria;
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %-3d | Patrimônio: %-10s | Nome: %-30s | Categoria: %-15s",
            this.id, this.patrimonio, this.nome, this.categoria
        );
    }
}
```

### 4.3 Data Access Object: `AtivoDAO.java`

``` java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtivoDAO {

    public void salvar(AtivoTI ativo) throws SQLException {
        String sql = "INSERT INTO ativos (patrimonio, nome, categoria) VALUES (?, ?, ?)";

        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, ativo.getPatrimonio());
            stmt.setString(2, ativo.getNome());
            stmt.setString(3, ativo.getCategoria());

            stmt.executeUpdate();
            System.out.println("[LOG] Ativo cadastrado com sucesso: " + ativo.getNome());

        } catch (SQLException e) {
            System.err.println("[ERRO DAO] Falha ao inserir ativo (" + ativo.getPatrimonio() + "): " + e.getMessage());
            throw e;
        }
    }

    public List<AtivoTI> listarTodos() throws SQLException {
        List<AtivoTI> listaAtivos = new ArrayList<>();
        String sql = "SELECT id, patrimonio, nome, categoria FROM ativos ORDER BY id ASC";

        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String patrimonio = rs.getString("patrimonio");
                String nome = rs.getString("nome");
                String categoria = rs.getString("categoria");

                AtivoTI ativo = new AtivoTI(id, patrimonio, nome, categoria);
                listaAtivos.add(ativo);
            }

        } catch (SQLException e) {
            System.err.println("[ERRO DAO] Falha ao listar ativos: " + e.getMessage());
            throw e;
        }

        return listaAtivos;
    }
}
```

### 4.4 Classe de execução: `Main.java`

``` java
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("=== TECHLAB INVENTORY & SERVICE DESK - AULA 08 ===");
        System.out.println("============================================\n");

        // 1. Garante que o banco e as tabelas estejam criados
        ConexaoBanco.inicializarBanco();

        AtivoDAO ativoDAO = new AtivoDAO();

        // 2. Instanciação de novos ativos
        AtivoTI servidor = new AtivoTI(
            "SRV-001", "Servidor de Banco de Dados MySQL", "Servidor"
        );
        AtivoTI workstation = new AtivoTI(
            "EST-042", "Estação de Trabalho Dev-Pro", "EstacaoTrabalho"
        );

        // 3. Persistência dos objetos via DAO com tratamento de exceções
        System.out.println("\n--- REGISTRANDO ATIVOS NO BANCO DE DADOS ---");

        try {
            ativoDAO.salvar(servidor);
            ativoDAO.salvar(workstation);
        } catch (SQLException e) {
            System.out.println("[AVISO] Registro não efetuado (possível patrimônio duplicado): " + e.getMessage());
        }

        // 4. Consulta e listagem de registros salvos
        System.out.println("\n--- RELATÓRIO DE ATIVOS PERSISTIDOS ---");

        try {
            List<AtivoTI> ativos = ativoDAO.listarTodos();

            if (ativos.isEmpty()) {
                System.out.println("Nenhum ativo cadastrado até o momento.");
            } else {
                for (AtivoTI ativo : ativos) {
                    System.out.println(ativo);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório de ativos: " + e.getMessage());
        }

        System.out.println("\n============================================");
        System.out.println("=== EXECUÇÃO FINALIZADA COM SUCESSO ===");
        System.out.println("============================================");
    }
}
```

## 5. Roteiro de execução no terminal e erros frequentes

### 5.1 Compilação e configuração do classpath (`javac` e `java`)

Para compilar e executar pelo terminal, inclua o driver JDBC (`.jar`) no
parâmetro `-cp`.

**Windows (Prompt de Comando / PowerShell):**

``` powershell
# Compilação
javac -cp ".;sqlite-jdbc.jar" ConexaoBanco.java AtivoTI.java AtivoDAO.java Main.java

# Execução
java -cp ".;sqlite-jdbc.jar" Main
```

**Linux / macOS (Terminal Bash / Zsh):**

``` bash
# Compilação
javac -cp ".:sqlite-jdbc.jar" ConexaoBanco.java AtivoTI.java AtivoDAO.java Main.java

# Execução
java -cp ".:sqlite-jdbc.jar" Main
```

### 5.2 Checklist anti-reprovação: erros frequentes

1.  **`ClassNotFoundException` / driver ausente no classpath:** o `.jar`
    do driver (por exemplo, `sqlite-jdbc.jar`) não foi incluído em `-cp`
    durante a execução.
2.  **`SQLException: No suitable driver found` / URL inválida:** pode
    ocorrer por erro no prefixo da URL JDBC, como `jdbc:sqllite:` ou
    ausência de `jdbc:sqlite:`.
3.  **Connection leak / vazamento de conexões:** ocorre quando conexões,
    instruções ou resultados não são fechados.
4.  **Omissão do tratamento de exceções / *silent catch*:** blocos
    `catch` vazios ou que apenas escondem a falha mascaram problemas e
    impedem a aplicação de reagir corretamente.

## 6. Exercícios de fixação e desafios

### 6.1 Exercício: buscar ativo por código de patrimônio

Implemente em `AtivoDAO` o método:

``` java
public AtivoTI buscarPorPatrimonio(String patrimonio) throws SQLException
```

**Roteiro de implementação:**

1.  Escreva a consulta parametrizada:

    ``` sql
    SELECT id, patrimonio, nome, categoria
    FROM ativos
    WHERE patrimonio = ?
    ```

2.  Abra a conexão e prepare a instrução com `PreparedStatement`.

3.  Vincule o parâmetro com `stmt.setString(1, patrimonio)`.

4.  Execute a consulta e use `if (rs.next())` para instanciar e retornar
    `AtivoTI`.

5.  Se o registro não for encontrado, retorne `null`.

### 6.2 Desafio avançado: persistência e vinculação de chamados

Crie `ChamadoDAO` e implemente:

``` java
public void salvar(ChamadoSuporte chamado) throws SQLException
```

**Roteiro de implementação:**

1.  Elabore a consulta:

    ``` sql
    INSERT INTO chamados (descricao, ativo_id) VALUES (?, ?)
    ```

2.  Obtenha o ID do ativo associado com `chamado.getAtivo().getId()`.

3.  Garanta que a integridade referencial com a tabela `ativos` seja
    validada pelo banco.

## 7. Workflow Git completo do laboratório

### 7.1 Rotina de início da aula

Antes de codificar, sincronize o repositório local com o servidor:

``` bash
git pull origin main
```

### 7.2 Rotina de encerramento e entrega obrigatória

Ao concluir os exercícios, confira os arquivos alterados e envie a
atividade:

``` bash
git status
git add .
git commit -m "feat: implementacao da conexao JDBC e AtivoDAO - Aula 08"
git push origin main
```

## Síntese final

> A transição do armazenamento temporário em memória RAM para a
> persistência definitiva em banco de dados relacional é um marco na
> formação de um arquiteto de software. Código de produção exige o uso
> rigoroso de `PreparedStatement` contra ataques maliciosos, fechamento
> automático de recursos com `try-with-resources` e cuidado na
> propagação das exceções de banco de dados.

## Checklist de entrega

-   [ ] A classe `ConexaoBanco` estabelece a conexão corretamente e
    possui `inicializarBanco()`.
-   [ ] O script DDL cria as tabelas `ativos` e `chamados` sem erros.
-   [ ] Os atributos das classes do modelo (`AtivoTI` etc.) são privados
    e respeitam o encapsulamento.
-   [ ] A classe `AtivoDAO` utiliza `PreparedStatement` e
    `try-with-resources` em todos os métodos.
-   [ ] As exceções `SQLException` são registradas e tratadas, sem
    blocos `catch` silenciosos.
-   [ ] A aplicação compila e executa pelo terminal com o driver
    incluído em `-cp`.
-   [ ] O repositório Git foi atualizado com `pull`, `add`, `commit` e
    `push`.
