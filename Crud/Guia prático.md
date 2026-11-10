# Guia Prático de Laboratório — Aula 09

## CRUD Completo, Relacionamentos com Chave Estrangeira e Persistência do Service Desk

## 1. Introdução e Objetivos

Na Aula 08, a aplicação integradora TechLab Inventory & Service Desk avançou no desenvolvimento da camada de persistência com o uso do Java Database Connectivity (JDBC), abordando exclusivamente as operações de inserção (`INSERT`) e listagem simples (`SELECT`) de dados na entidade de ativos de TI.

Nesta Aula 09, fechamos o ciclo de vida dos dados completando o CRUD (Create, Read, Update, Delete) na classe `AtivoDAO.java`, adicionando os recursos de atualização (`UPDATE`) e remoção (`DELETE`) de registros com total controle de execução.

Além de consolidar o CRUD básico, o sistema expande seu escopo para o domínio da persistência relacional com a criação da entidade `ChamadoDAO.java`. Essa camada introduz o mapeamento de relacionamentos entre entidades atreladas via Chaves Estrangeiras (`FOREIGN KEY`), permitindo associar os chamados de suporte técnico (`ChamadoSuporte`) aos seus respectivos ativos (`AtivoTI`).

### Objetivos de Aprendizagem

- Implementar as operações de atualização (`UPDATE`) e remoção (`DELETE`) de registros com segurança utilizando `PreparedStatement`.
- Validar o impacto de comandos de alteração no banco de dados relacional através do retorno inteiro do método `executeUpdate()`.
- Modelar e persistir relacionamentos com Chave Estrangeira (`FOREIGN KEY`) entre entidades (`ChamadoSuporte` e `AtivoTI`), compreendendo a regra de integridade referencial.
- Compreender o uso de consultas SQL com agrupamento/junção (`JOIN`) e a reconstituição de grafos de objetos em memória.
- Configurar o mecanismo de ativação de Chaves Estrangeiras no SQLite (`PRAGMA foreign_keys = ON;`) e tratar exceções de restrição relacional em Java.
- Consolidar o fluxo completo de persistência JDBC, modularização em pacotes (`model`, `dao`) e gerenciamento adequado de recursos.

## 2. Tutorial Explicativo (Conceitos Fundamentais)

### 2.1. Operações SQL UPDATE e DELETE com PreparedStatement

Para modificar ou remover dados existentes no banco de dados relacional, utilizam-se as instruções SQL `UPDATE` e `DELETE`.

Sintaxe UPDATE:

```
UPDATE tabela
SET coluna1 = ?, coluna2 = ?
WHERE id = ?;
```

Sintaxe DELETE:

```
DELETE FROM tabela
WHERE id = ?;
```

É indispensável parametrizar todos os valores dinâmicos das instruções SQL utilizando `PreparedStatement` (representados pelos marcadores de posição `?`).

A parametrização previne ataques de SQL Injection e garante a conversão correta dos tipos de dados entre o Java SE e a base relacional.

### 2.2. Validação com o Retorno de executeUpdate()

Diferente do método `executeQuery()` — utilizado exclusivamente para consultas (`SELECT`) e que retorna um `ResultSet` —, as instruções de alteração e manipulação (`INSERT`, `UPDATE`, `DELETE`) devem ser executadas com o método `executeUpdate()`.

O método `executeUpdate()` retorna um número inteiro (`int`) correspondente ao número exato de linhas afetadas pela operação na tabela.

- Se `linhasAfetadas > 0`, a alteração ou exclusão foi realizada com sucesso.
- Se `linhasAfetadas == 0`, significa que nenhum registro atendeu à condição especificada na cláusula `WHERE` (por exemplo, tentativa de atualizar ou excluir um ID inexistente no banco).

### 2.3. Integridade Referencial e Chave Estrangeira (FOREIGN KEY)

A Chave Estrangeira (`FOREIGN KEY`) é um campo em uma tabela (tabela filha) que se refere à Chave Primária (`PRIMARY KEY`) de outra tabela (tabela pai).

Ela assegura a Integridade Referencial:

1. O banco de dados impede que um registro filho seja inserido apontando para um registro pai inexistente.
2. O banco impede a exclusão acidental de um registro pai enquanto existirem registros filhos associados a ele.

Nota Técnica sobre o SQLite:

Por padrão e razões de compatibilidade histórica com versões antigas, o SQLite desativa a verificação de Chaves Estrangeiras a cada nova conexão estabelecida.

Portanto, no ecossistema Java com SQLite, é obrigatório executar o comando SQL abaixo imediatamente após abrir a `Connection`:

```
PRAGMA foreign_keys = ON;
```

Isso garante que o motor do banco imponha as restrições de integridade referencial durante a execução da aplicação.

### 2.4. Mapeamento do Relacionamento "Tem-Um" e Reconstituição de Objetos (SQL JOIN)

Em Orientação a Objetos, a relação entre entidades é representada por associação direta ("Tem-Um"): a classe `ChamadoSuporte` contém uma referência de atributo para um objeto `AtivoTI`.

No banco de dados relacional, essa associação é traduzida armazenando o identificador do ativo (`ativo_id`) na tabela de chamados (`chamados`).

Exemplo de relacionamento:

```
[Tabela: ativos]                  [Tabela: chamados]

+----+----------------------+    +----+--------------------------------+----------+
| id | nome                 |    | id | descricao                      | ativo_id |
+----+----------------------+    +----+--------------------------------+----------+
| 1  | Notebook Latitude    | <--| 1  | Troca de teclado defeituoso    | 1        |
+----+----------------------+    +----+--------------------------------+----------+
```

Ao gravar um chamado (`ChamadoDAO.salvar`), extrai-se a chave primária do objeto associado via:

```
chamado.getAtivo().getId();
```

Contudo, ao ler os dados de volta do banco, há duas estratégias principais para recompor o objeto Java em memória:

1\. Stub/Referência Leve

Cria-se o objeto `ChamadoSuporte` e instancia-se um `AtivoTI` apenas preenchendo o seu ID:

```
ativo.setId(rs.getInt("ativo_id"));
```

Essa abordagem é performática para cenários onde apenas o ID do pai é necessário.

2\. Consulta com JOIN (Reconstituição Completa)

Executa-se uma instrução `INNER JOIN` ou `LEFT JOIN` unindo as tabelas `chamados` e `ativos`.

O `ResultSet` resultante conterá os dados de ambas as tabelas na mesma linha, permitindo instanciar o `AtivoTI` completo (com nome, patrimônio etc.) e atribuí-lo diretamente ao `ChamadoSuporte`.

## 3. Requisitos da Atividade Prática (TechLab Inventory - Aula 09)

| Componente / Classe         | Requisito / Método Mapeado                                                                                                                                                                                                               |
| --------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `model/AtivoTI.java`        | Representar a entidade de domínio do ativo contendo `id`, `nome`, `patrimonio` e `valor`, com construtores, getters e setters.                                                                                                           |
| `model/ChamadoSuporte.java` | Representar a entidade de chamado contendo `id`, `descricao`, `status` e a associação `AtivoTI ativo`.                                                                                                                                   |
| `dao/AtivoDAO.java`         | Implementar `inserir`, `listar`, `atualizar(AtivoTI ativo)` e `excluir(int id)` utilizando `PreparedStatement` e validando linhas afetadas com `executeUpdate()`.                                                                        |
| `dao/ChamadoDAO.java`       | Criar a nova classe DAO contendo `salvar(ChamadoSuporte chamado)` e `listarPorAtivo(int ativoId)` mapeando a Chave Estrangeira `ativo_id`.                                                                                               |
| `Main.java`                 | Executar o fluxo interativo: ativar PRAGMA de FK, criar tabelas, cadastrar ativo, abrir chamado vinculado, atualizar ativo, listar chamados filtrados, demonstrar a restrição de FK na exclusão e finalizar com a limpeza dos registros. |

## 4. Código Consolidado e Compilável

Abaixo encontra-se o código-fonte Java SE completo e limpo, organizado na estrutura profissional de pacotes (`model`, `dao` e a classe principal no pacote default).

### 4.1. Classes de Modelo

#### Arquivo: `model/AtivoTI.java`

```java
package model;

public class AtivoTI {
    private int id;
    private String nome;
    private String patrimonio;
    private double valor;

    public AtivoTI() {
    }

    public AtivoTI(int id, String nome, String patrimonio, double valor) {
        this.id = id;
        this.nome = nome;
        this.patrimonio = patrimonio;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "AtivoTI [id=" + id + ", nome=" + nome
            + ", patrimonio=" + patrimonio + ", valor=" + valor + "]";
    }
}
```

#### Arquivo: `model/ChamadoSuporte.java`

```java
package model;

public class ChamadoSuporte {
    private int id;
    private String descricao;
    private String status;
    private AtivoTI ativo;

    public ChamadoSuporte() {
    }

    public ChamadoSuporte(int id, String descricao, String status, AtivoTI ativo) {
        this.id = id;
        this.descricao = descricao;
        this.status = status;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AtivoTI getAtivo() {
        return ativo;
    }

    public void setAtivo(AtivoTI ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "ChamadoSuporte [id=" + id + ", descricao=" + descricao
            + ", status=" + status
            + ", ativo=" + (ativo != null ? ativo.getNome() : "null") + "]";
    }
}
```

### 4.2. Classe DAO de Ativos

#### Arquivo: `dao/AtivoDAO.java`

```java
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.AtivoTI;

public class AtivoDAO {
    private Connection conexao;

    public AtivoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(AtivoTI ativo) throws SQLException {
        String sql = "INSERT INTO ativos (nome, patrimonio, valor) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, ativo.getNome());
            stmt.setString(2, ativo.getPatrimonio());
            stmt.setDouble(3, ativo.getValor());
            stmt.executeUpdate();
        }
    }

    public List<AtivoTI> listar() throws SQLException {
        List<AtivoTI> lista = new ArrayList<>();

        String sql = "SELECT id, nome, patrimonio, valor FROM ativos";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AtivoTI ativo = new AtivoTI(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("patrimonio"),
                    rs.getDouble("valor")
                );

                lista.add(ativo);
            }
        }

        return lista;
    }

    public boolean atualizar(AtivoTI ativo) throws SQLException {
        String sql = "UPDATE ativos SET nome = ?, patrimonio = ?, valor = ? WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, ativo.getNome());
            stmt.setString(2, ativo.getPatrimonio());
            stmt.setDouble(3, ativo.getValor());
            stmt.setInt(4, ativo.getId());

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM ativos WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        }
    }
}
```

### 4.3. Classe DAO de Chamados

#### Arquivo: `dao/ChamadoDAO.java`

```java
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.AtivoTI;
import model.ChamadoSuporte;

public class ChamadoDAO {
    private Connection conexao;

    public ChamadoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void salvar(ChamadoSuporte chamado) throws SQLException {
        String sql = "INSERT INTO chamados (descricao, status, ativo_id) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, chamado.getDescricao());
            stmt.setString(2, chamado.getStatus());
            stmt.setInt(3, chamado.getAtivo().getId());

            stmt.executeUpdate();
        }
    }

    public List<ChamadoSuporte> listarPorAtivo(int ativoId) throws SQLException {
        List<ChamadoSuporte> lista = new ArrayList<>();

        String sql = "SELECT id, descricao, status, ativo_id FROM chamados WHERE ativo_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, ativoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    // Instanciação em formato 'stub' leve contendo a referência do ID do Ativo
                    AtivoTI ativoStub = new AtivoTI();
                    ativoStub.setId(rs.getInt("ativo_id"));

                    ChamadoSuporte chamado = new ChamadoSuporte(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getString("status"),
                        ativoStub
                    );

                    lista.add(chamado);
                }
            }
        }

        return lista;
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM chamados WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        }
    }
}
```

### 4.4. Classe Principal de Execução

#### Arquivo: `Main.java`

```java
import dao.AtivoDAO;
import dao.ChamadoDAO;
import model.AtivoTI;
import model.ChamadoSuporte;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {

    private static final String URL_BANCO = "jdbc:sqlite:techlab.db";

    public static void main(String[] args) {

        try (Connection conexao = DriverManager.getConnection(URL_BANCO)) {

            System.out.println("[INFO] Conexão estabelecida com sucesso.");

            // Habilita obrigatoriamente a verificação de Foreign Keys no SQLite
            try (Statement stmt = conexao.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }

            // Inicializa as tabelas no banco de dados
            inicializarTabelas(conexao);

            AtivoDAO ativoDAO = new AtivoDAO(conexao);
            ChamadoDAO chamadoDAO = new ChamadoDAO(conexao);

            // 1. Inserir Ativo
            AtivoTI novoAtivo = new AtivoTI(
                0,
                "Notebook Dell Latitude",
                "PAT-2026-001",
                4500.00
            );

            ativoDAO.inserir(novoAtivo);

            System.out.println("[OK] Ativo cadastrado com sucesso.");

            // Recuperar o ID do ativo recém-inserido
            List<AtivoTI> ativos = ativoDAO.listar();

            AtivoTI ativoSalvo = ativos.get(ativos.size() - 1);

            System.out.println("[INFO] ID do ativo salvo no banco: " + ativoSalvo.getId());

            // 2. Abrir Chamado Vinculado ao Ativo (FK)
            ChamadoSuporte novoChamado = new ChamadoSuporte(
                0,
                "Troca de teclado defeituoso",
                "ABERTO",
                ativoSalvo
            );

            chamadoDAO.salvar(novoChamado);

            System.out.println("[OK] Chamado vinculado ao ativo salvo com sucesso.");

            // 3. Atualizar Dados do Ativo (UPDATE)
            ativoSalvo.setNome("Notebook Dell Latitude 5420");
            ativoSalvo.setValor(4800.00);

            boolean atualizado = ativoDAO.atualizar(ativoSalvo);

            System.out.println("[OK] Ativo atualizado no banco? " + atualizado);

            // 4. Listar Chamados do Ativo (Consulta Filtrada por FK)
            List<ChamadoSuporte> chamadosDoAtivo =
                chamadoDAO.listarPorAtivo(ativoSalvo.getId());

            System.out.println("\n--- Chamados Vinculados ao Ativo ID "
                + ativoSalvo.getId() + " ---");

            for (ChamadoSuporte c : chamadosDoAtivo) {
                System.out.println(" -> ID Chamado: " + c.getId()
                    + " | Descrição: " + c.getDescricao()
                    + " | Status: " + c.getStatus());
            }

            System.out.println("---------------------------------------------------\n");

            // 5. Demonstrar Restrição de Integridade Referencial ao Tentar Excluir o Ativo
            System.out.println("[TESTE] Tentando excluir o Ativo ID "
                + ativoSalvo.getId() + " que possui chamado ativo...");

            try {
                ativoDAO.excluir(ativoSalvo.getId());

                System.out.println("[ALERTA] Exclusão realizada (Inesperado se PRAGMA FK estiver ativo).");

            } catch (SQLException e) {

                System.err.println("[SUCESSO NO TESTE DE INTEGRIDADE] A exclusão foi bloqueada pelo banco!");

                System.err.println(" -> Mensagem de Erro do Engine SQL: " + e.getMessage());
            }

            // 6. Limpeza Correta: Excluir Primeiro o Registro Filho (Chamado) e Depois o Pai (Ativo)
            System.out.println("\n[LIMPEZA] Removendo o chamado filho primeiro...");

            List<ChamadoSuporte> chamadosParaExcluir =
                chamadoDAO.listarPorAtivo(ativoSalvo.getId());

            for (ChamadoSuporte c : chamadosParaExcluir) {
                chamadoDAO.excluir(c.getId());
            }

            System.out.println("[OK] Chamados excluídos com sucesso.");

            System.out.println("[LIMPEZA] Removendo o ativo pai...");

            boolean excluido = ativoDAO.excluir(ativoSalvo.getId());

            System.out.println("[OK] Ativo pai excluído com sucesso? " + excluido);

        } catch (SQLException e) {

            System.err.println("[ERRO CRÍTICO] Falha na operação JDBC: " + e.getMessage());

            e.printStackTrace();
        }
    }

    private static void inicializarTabelas(Connection conexao) throws SQLException {

        String sqlAtivos = "CREATE TABLE IF NOT EXISTS ativos (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome TEXT NOT NULL, " +
            "patrimonio TEXT NOT NULL, " +
            "valor REAL NOT NULL);";

        String sqlChamados = "CREATE TABLE IF NOT EXISTS chamados (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "descricao TEXT NOT NULL, " +
            "status TEXT NOT NULL, " +
            "ativo_id INTEGER NOT NULL, " +
            "FOREIGN KEY (ativo_id) REFERENCES ativos(id));";

        try (Statement stmt = conexao.createStatement()) {
            stmt.execute(sqlAtivos);
            stmt.execute(sqlChamados);
        }
    }
}
```

## 5. Roteiro de Execução no Terminal e Erros Frequentes

### 5.1. Comandos de Compilação e Execução

Certifique-se de que o driver JDBC do SQLite (`sqlite-jdbc.jar`) esteja na raiz do projeto ou dentro de uma pasta de bibliotecas (ex.: `lib/`).

Windows (separador de classpath `;`):

```
# Compilação dos modelos, DAOs e classe principal
javac -cp ".;sqlite-jdbc.jar" model/*.java dao/*.java Main.java

# Execução da aplicação
java -cp ".;sqlite-jdbc.jar" Main
```

Linux e macOS (separador de classpath `:`):

```
# Compilação dos modelos, DAOs e classe principal
javac -cp ".:sqlite-jdbc.jar" model/*.java dao/*.java Main.java

# Execução da aplicação
java -cp ".:sqlite-jdbc.jar" Main
```

### 5.2. Tratamento de Exceções e Diagnóstico de Erros

O uso do JDBC exige o tratamento rigoroso de `SQLException` e exceções do ambiente de execução.

Principais Falhas e Soluções:

1\. `java.lang.ClassNotFoundException: org.sqlite.JDBC`

- Causa: O arquivo `.jar` do driver JDBC não foi localizado no classpath do sistema durante a execução.
- Solução: Verifique o nome e o caminho exato do driver na opção `-cp` do comando `java` (ex.: `.;lib/sqlite-jdbc-3.42.0.0.jar`).

2\. `SQLException: no such table: ativos` (Tabela não encontrada)

- Causa: Execução de instruções DML (`SELECT`, `INSERT`, `UPDATE`, `DELETE`) em um banco de dados recém-criado antes da execução dos comandos DDL (`CREATE TABLE`).
- Solução: Garantir que a rotina `inicializarTabelas()` seja executada imediatamente após abrir a conexão.

3\. `SQLException: FOREIGN KEY constraint failed`

- Causa: Ocorre em dois cenários:
  - Tentativa de inserir um `ChamadoSuporte` apontando para um `ativo_id` que não existe na tabela `ativos`.
  - Tentativa de excluir um `AtivoTI` que possui um ou mais chamados vinculados a ele na tabela `chamados`.
- Solução: Excluir os registros filhos associados antes de remover o registro pai, ou configurar remoção em cascata (`ON DELETE CASCADE`) na tabela SQL.

4\. `SQLException: database is locked`

- Causa: O SQLite bloqueia o arquivo do banco de dados se outra conexão mantiver uma transação não finalizada ou um `Statement`/`ResultSet` aberto.
- Solução: Certifique-se de usar a estrutura `try-with-resources` em todos os `PreparedStatement` e `ResultSet` para assegurar o fechamento automático das alocações.

## 6. Exercícios de Fixação e Desafios

### Exercício 1 (Nível Básico - Validação)

Modifique o trecho de exclusão de ativo em `Main.java` para tratar a tentativa de remover um ID inexistente no banco.

Caso o método `ativoDAO.excluir(id)` retorne `false` (isto é, 0 linhas afetadas pelo `executeUpdate()`), exiba o seguinte alerta no console:

```
Aviso: Nenhum ativo foi localizado com o ID informado. Exclusão não efetuada.
```

### Exercício 2 (Nível Intermediário - Consulta Completa com SQL JOIN)

Implemente o método `listarTodosComAtivo()` na classe `ChamadoDAO.java`.

O método deve realizar uma consulta SQL utilizando `INNER JOIN` para retornar a lista de chamados com seus respectivos objetos `AtivoTI` totalmente preenchidos em memória (sem utilizar referências stub).

Exemplo de instrução SQL a ser utilizada:

```
SELECT c.id AS chamado_id, c.descricao, c.status,
       a.id AS ativo_id, a.nome, a.patrimonio, a.valor
FROM chamados c
INNER JOIN ativos a ON c.ativo_id = a.id;
```

### Exercício 3 (Nível Avançado - Regra de Integridade Prévia)

Antes de disparar o comando SQL `DELETE` dentro do método `excluir(int id)` na classe `AtivoDAO`, adicione uma verificação programática que execute uma consulta na tabela `chamados` verificando se existem registros vinculados ao ID do ativo.

\- Se a contagem for maior que zero, aborte a remoção e lance uma exceção de regra de negócio (`SQLException` customizada ou exceção de runtime) informando:

```
Operação Negada: O ativo selecionado possui chamados vinculados e não pode ser removido sem antes tratar os chamados associados.
```

## 7. Workflow Git Completo

Ao concluir os testes da atividade prática no terminal, execute a sequência de comandos abaixo para versionar e enviar suas implementações para o repositório remoto no GitHub.

1\. Verificar os arquivos alterados e criados no projeto

```
git status
```

2\. Adicionar as pastas e arquivos Java ao staging area

```
git add .
```

3\. Criar o commit padronizado segundo as boas práticas de mensagens

```
git commit -m "feat: implementa CRUD completo em AtivoDAO e persistencia relacional em ChamadoDAO"
```

4\. Sincronizar as alterações com o repositório remoto na nuvem

```
git push origin main
```

Conteúdo convertido do PDF original (14 páginas), mantendo a organização, os conceitos, os códigos e os exercícios apresentados no material.