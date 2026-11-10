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
