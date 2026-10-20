import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ExemploConexao{
    public static void main(string[] args){
        string url = "jdbc:sqlite:lib/conexao.db";

        System.out.println("Tentando conectar ao SQLite. . .");
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Conexão com SQLite estabelecida com sucesso");
                String sql = "CREATE TABLE IF NOT EXISTS test_ativos (" + 
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "codigo_patrimonio TEXT NOT NULL," +
                            "modelo TEXT NOT NULL" +
                             ");";
                            
            try(Statement stmt = conn.createStatement()){
                stmt.execute(sql);
                System.out.println("Tabela 'test_ativos' verificada/criada com sucesso!" + e.getMenssage());
            }                 
            }
        } catch(SQLException e){
            System.out.println("Erro ao conectar ou operar no banco de dados: " + e.getMenssage());
            e.printStackTrace();
        }

    }
    
}