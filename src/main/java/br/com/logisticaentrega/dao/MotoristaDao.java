package br.com.logisticaentrega.dao;

import br.com.logisticaentrega.model.Motorista;
import br.com.logisticaentrega.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Motorista> select(){
        String sql = """
                SELECT id, nome, cnh, veiculo, cidade_base
                FROM motorista;
                """;
        List<Motorista> motoristas = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Integer id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cnh = rs.getString("cnh");
                String veiculo = rs.getString("veiculo");
                String cidade_base = rs.getString("cidade_base");

                var motorista = new Motorista(id, nome, cnh, veiculo, cidade_base);
                motoristas.add(motorista);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return motoristas;
    }

    public void delete(Motorista motorista){
        String sql = """
                DELETE FROM motorista
                WHERE id = ?;
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, motorista.getId());

            System.out.println("Motorista deletado com sucesso!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
