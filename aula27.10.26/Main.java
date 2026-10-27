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
