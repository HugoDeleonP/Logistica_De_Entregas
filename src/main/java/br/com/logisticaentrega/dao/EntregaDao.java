package br.com.logisticaentrega.dao;

import br.com.logisticaentrega.model.*;
import br.com.logisticaentrega.service.ServiceCRUD;
import br.com.logisticaentrega.util.Conexao;
import br.com.logisticaentrega.view.Viewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EntregaDao{

    Viewer uiView = new Viewer();

    public void insert(Pedido pedido, Motorista motorista, LocalDateTime data_saida, LocalDateTime data_entrega, String statusEntrega) throws SQLException{

        String sql = """
                INSERT INTO entrega (pedido_id, motorista_id, data_saida, data_entrega, status_entrega)
                VALUES (?, ?, ?, ?, ?);
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getId());
            stmt.setInt(2, motorista.getId());
            stmt.setObject(3, data_saida);
            stmt.setObject(4, data_entrega);
            stmt.setString(5, statusEntrega);

            stmt.executeUpdate();
            System.out.println("Entrega cadastrada com sucesso!");

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public List<Entrega> select(){
        String sql = """
                select entrega.id as entrega_id, entrega.pedido_id, motorista.nome as motorista_nome, motorista.id as motorista_id, entrega.data_saida, entrega.data_entrega, entrega.status_entrega
                from entrega
                LEFT JOIN motorista ON entrega.motorista_id = motorista.id;
                """;

        List<Entrega> entregas = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Integer id = rs.getInt("entrega_id");
                Integer pedido_id = rs.getInt("pedido_id");
                String motorista_nome = rs.getString("motorista_nome");
                Integer motorista_id = rs.getInt("motorista_id");
                LocalDateTime data_saida = rs.getObject("data_saida", LocalDateTime.class);
                LocalDateTime data_entrega = rs.getObject("data_entrega", LocalDateTime.class);
                String status = rs.getString("status_entrega");

                Pedido pedido = new Pedido(pedido_id);
                Motorista motorista = new Motorista(motorista_id, motorista_nome);

                var entrega = new Entrega(id, pedido, motorista, data_saida, data_entrega, status);
                entregas.add(entrega);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return entregas;
    }

    public List<Entrega> selectCliente_motorista() throws SQLException{
        String sql = """
                select entrega.id as entrega_id,
                cliente.nome as cliente_nome,
                cliente.id as cliente_id,
                motorista.nome as motorista_nome,
                motorista.id as motorista_id,
                pedido.id as pedido_id,
                entrega.data_saida,
                entrega.data_entrega,
                entrega.status_entrega
                from entrega
                LEFT JOIN motorista ON entrega.motorista_id = motorista.id
                LEFT JOIN pedido ON entrega.pedido_id = pedido.id
                	LEFT JOIN cliente on pedido.cliente_id = cliente.id;
                """;

        List<Entrega> entregas = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Integer entrega_id = rs.getInt("entrega_id");
                String cliente_nome = rs.getString("cliente_nome");
                String motorista_nome = rs.getString("motorista_nome");
                LocalDateTime dataSaida = rs.getObject("data_saida", LocalDateTime.class);
                LocalDateTime dataEntrega = rs.getObject("data_entrega", LocalDateTime.class);
                String status_entrega = rs.getString("status_entrega");

                Integer cliente_id = rs.getInt("cliente_id");
                Integer motorista_id = rs.getInt("motorista_id");
                Integer pedido_id = rs.getInt("pedido_id");

                Cliente cliente = new Cliente(cliente_id, cliente_nome);
                Motorista motorista = new Motorista(motorista_id, motorista_nome);
                Pedido pedido = new Pedido(pedido_id, cliente);

                Entrega entrega = new Entrega(entrega_id, pedido, motorista, dataSaida, dataEntrega, status_entrega);

                entregas.add(entrega);
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

        return entregas;
    }

    public void updateStatus(String status_entrega, Integer id) throws SQLException{
        String sql = """
                UPDATE entrega
                SET status_entrega = ?
                WHERE id = ?;
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, status_entrega);
            stmt.setInt(2, id);

            stmt.executeUpdate();

            System.out.println("Status de entrega atualizado com sucesso!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateDataEntrega(LocalDateTime data_entrega, Integer id) throws SQLException{
        String sql = """
                UPDATE entrega
                SET data_entrega = ?
                WHERE id = ?;
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setObject(1, data_entrega);
            stmt.setInt(2, id);

            stmt.executeUpdate();

            System.out.println("Data de entrega atualizada com sucesso!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(Integer id) throws SQLException{
        String sql = """
                DELETE FROM entrega
                WHERE id = ?;
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void totalEntregaByMotorista() throws SQLException{
        String sql = """
                select motorista.nome as nome_motorista, count(entrega.id) as quantidade_entregas
                from entrega
                LEFT JOIN motorista ON entrega.motorista_id = motorista.id
                GROUP BY motorista.nome;
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                String nome_motorista = rs.getString("nome_motorista");
                int quantidade_entregas = rs.getInt("quantidade_entregas");

                System.out.println(uiView.attributeString_toString("Nome do motorista", nome_motorista));
                System.out.println(uiView.attributeInt_toString("Quantidade de entregas", quantidade_entregas) + "\n");


            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void totalEntregaAtrasadaByCidade() throws  SQLException{
        String sql = """
                select cliente.cidade as cliente_cidade, count(entrega.id) as quantidade_entrega
                FROM entrega
                LEFT JOIN pedido ON entrega.pedido_id = pedido.id
                    LEFT JOIN cliente ON pedido.cliente_id = cliente.id
                GROUP BY cliente_cidade;
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                String cliente_cidade = rs.getString("cliente_cidade");
                int quantidade_entrega = rs.getInt("quantidade_entrega");

                System.out.println(uiView.attributeString_toString("Cidade", cliente_cidade));
                System.out.println(uiView.attributeInt_toString("Quantidade de entregas", quantidade_entrega) + "\n");

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}
