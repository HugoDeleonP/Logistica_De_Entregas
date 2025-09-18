package br.com.logisticaentrega.dao;

import br.com.logisticaentrega.model.Cliente;
import br.com.logisticaentrega.model.Pedido;
import br.com.logisticaentrega.model.StatusPedido;
import br.com.logisticaentrega.service.ServiceCRUD;
import br.com.logisticaentrega.util.Conexao;
import br.com.logisticaentrega.view.Viewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoDao{

    Viewer uiView = new Viewer();

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

    public List<Pedido> select() throws SQLException{
        String sql = """
                select pedido.id as pedido_id,
                cliente.nome as cliente_nome,
                cliente.id as cliente_id,
                pedido.data_pedido,
                pedido.volume_m3,
                pedido.peso_kg,
                pedido.status_pedido
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

    public void updateStatus(String status_pedido, Integer id) throws SQLException{
        String sql = """
                UPDATE pedido SET status_pedido = ?
                WHERE id = ?;
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, status_pedido);
            stmt.setInt(2, id);

            stmt.executeUpdate();

            System.out.println("Status do pedido atualizado com sucesso!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Pedido> search_CpfCnpj(String termoPesquisa) throws SQLException{
        String sql = """
                select pedido.id as pedido_id,
                cliente.id as cliente_id,
                cliente.cpf_cnpj as cliente_cpf_cnpj,
                cliente.nome as cliente_nome,
                pedido.data_pedido,
                pedido.volume_m3,
                pedido.peso_kg,
                pedido.status_pedido
                from pedido
                LEFT JOIN cliente ON pedido.cliente_id = cliente.id
                WHERE cliente.cpf_cnpj like ?;
                """;

        List<Pedido> pedidos = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, "%" + termoPesquisa + "%");

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                Integer cliente_id = rs.getInt("cliente_id");
                String cliente_nome = rs.getString("cliente_nome");
                String cliente_cpf_cnpj = rs.getString("cliente_cpf_cnpj");

                Integer pedido_id = rs.getInt("pedido_id");
                LocalDateTime data_pedido = rs.getObject("data_pedido", LocalDateTime.class);
                double volume_m3 = rs.getDouble("volume_m3");
                double peso_kg = rs.getDouble("peso_kg");
                String status_pedido = rs.getString("status_pedido");

                Cliente cliente = new Cliente(cliente_id, cliente_cpf_cnpj, cliente_nome);
                Pedido pedido = new Pedido(pedido_id, cliente, data_pedido, volume_m3, peso_kg, status_pedido);

                pedidos.add(pedido);
            }

            System.out.println("CPF/CNPJ encontrado com sucesso!");

        } catch (SQLException e){
            e.printStackTrace();
        }

        return pedidos;
    }

    public void maiorVolumeByCliente() throws SQLException{
        String sql = """
                select cliente.nome as nome_cliente, max(pedido.volume_m3) as maior_volume
                FROM pedido
                LEFT JOIN cliente ON pedido.cliente_id = cliente.id
                GROUP BY nome_cliente;
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                String nome_cliente = rs.getString("nome_cliente");
                double maior_volume = rs.getDouble("maior_volume");

                System.out.println(uiView.attributeString_toString("Nome do cliente", nome_cliente));
                System.out.println(uiView.attributeDouble_toString("Maior volume", maior_volume) + "\n");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void pedidoPendenteByEstado() throws SQLException{
        String sql = """
                select cliente.estado as estado, count(pedido.id) as quantidade_pedidos
                FROM pedido
                LEFT JOIN cliente ON pedido.cliente_id = cliente.id
                GROUP BY estado;
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                String estado = rs.getString("estado");
                int quantidade_pedidos = rs.getInt("quantidade_pedidos");

                System.out.println(uiView.attributeString_toString("Estado", estado));
                System.out.println(uiView.attributeInt_toString("Maior volume", quantidade_pedidos) + "\n");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
