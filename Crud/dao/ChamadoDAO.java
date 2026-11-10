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
