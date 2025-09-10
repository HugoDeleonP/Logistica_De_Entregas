package br.com.logisticaentrega.dao;

import br.com.logisticaentrega.model.Cliente;
import br.com.logisticaentrega.model.StatusPedido;
import br.com.logisticaentrega.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PedidoDao{

    public void insert(Cliente cliente, double volume_m3, double peso_kg, StatusPedido statusPedido){
        String sql = """
                INSERT INTO FROM pedido VALUES(cliente_id, data_pedido, volume_m3, peso_kg, status_pedido)
                VALUES (?, ?, ?, ?, ?);
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            LocalDateTime data_pedido = LocalDateTime.now();

            stmt.setInt(1, cliente.getId());
            stmt.setObject(2, data_pedido);
            stmt.setDouble(3, volume_m3);
            stmt.setDouble(4, peso_kg);
            stmt.setObject(5, statusPedido);

            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
