package br.com.logisticaentrega.dao;

import br.com.logisticaentrega.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MotoristaDao{

    public void insert(String nome, String cnh, String veiculo, String cidade_base) throws SQLException{
        String sql = """
                    INSERT INTO motorista (nome, cnh, veiculo, cidade_base)
                    VALUES (?, ?, ?, ?);
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, nome);
            stmt.setString(2, cnh);
            stmt.setString(3, veiculo);
            stmt.setString(4, cidade_base);

            stmt.executeUpdate();

            System.out.println("Motorista cadastrado com sucesso!");

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
