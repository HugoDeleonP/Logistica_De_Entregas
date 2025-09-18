package br.com.logisticaentrega.dao;

import br.com.logisticaentrega.model.Cliente;
import br.com.logisticaentrega.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    public void insert(String nome, String cpf_cnpj, String endereco, String cidade, String estado) throws SQLException {
        String sql = """
                    INSERT INTO cliente (nome, cpf_cnpj, endereco, cidade, estado)
                    VALUES (?, ?, ?, ?, ?);
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, nome);
            stmt.setString(2, cpf_cnpj);
            stmt.setString(3, endereco);
            stmt.setString(4, cidade);
            stmt.setString(5, estado);

            stmt.executeUpdate();

            System.out.println("Cliente cadastrado com sucesso!");
        }
        catch (SQLException e){
            System.out.println("Cliente cadastrado sem sucesso.");
            e.printStackTrace();
        }
    }

    public List<Cliente> select() throws SQLException{
        String sql = """
                    SELECT id, nome, cpf_cnpj, endereco, cidade, estado
                    FROM cliente;
                """;

        List<Cliente> clientes = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf_cnpj = rs.getString("cpf_cnpj");
                String endereco = rs.getString("endereco");
                String cidade = rs.getString("cidade");
                String estado = rs.getString("estado");

                var cliente = new Cliente(id, nome, cpf_cnpj, endereco, cidade, estado);
                clientes.add(cliente);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return clientes;
    }

    public void delete(Cliente cliente) throws SQLException{
        String sql = """
                DELETE FROM cliente
                WHERE id = ?;
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, cliente.getId());

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
