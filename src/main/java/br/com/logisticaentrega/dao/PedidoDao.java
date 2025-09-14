package br.com.logisticaentrega.dao;

import br.com.logisticaentrega.model.Cliente;
import br.com.logisticaentrega.model.Pedido;
import br.com.logisticaentrega.model.StatusPedido;
import br.com.logisticaentrega.service.ServiceCRUD;
import br.com.logisticaentrega.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoDao{

    public void insert(Cliente cliente, double volume_m3, double peso_kg, String statusPedido) throws SQLException{
        String sql = """
                INSERT INTO pedido (cliente_id, data_pedido, volume_m3, peso_kg, status_pedido)
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

            System.out.println("Pedido cadastrado com sucesso!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Pedido> select(){
        String sql = """
                select pedido.id as pedido_id, cliente.nome as cliente_nome, cliente.id as cliente_id, pedido.data_pedido, pedido.volume_m3, pedido.volume_m3, pedido.peso_kg, pedido.status_pedido
                from pedido
                LEFT JOIN cliente ON pedido.cliente_id = cliente.id;
                """;

        List<Pedido> pedidos = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Integer id = rs.getInt("pedido_id");
                String cliente_nome = rs.getString("cliente_nome");
                Integer cliente_id = rs.getInt("cliente_id");
                LocalDateTime data_pedido = rs.getObject("data_pedido", LocalDateTime.class);
                double volume_m3 = rs.getDouble("volume_m3");
                double peso_kg = rs.getDouble("peso_kg");
                String status_pedido = rs.getString("status_pedido");

                var cliente = new Cliente(cliente_id, cliente_nome);

                var pedido = new Pedido(id, cliente, data_pedido, volume_m3, peso_kg, status_pedido);
                pedidos.add(pedido);

            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return pedidos;
    }
}
