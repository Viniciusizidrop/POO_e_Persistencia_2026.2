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
