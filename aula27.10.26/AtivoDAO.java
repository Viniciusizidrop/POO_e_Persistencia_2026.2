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

