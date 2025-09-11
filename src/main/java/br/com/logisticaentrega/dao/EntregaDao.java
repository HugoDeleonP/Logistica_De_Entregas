package br.com.logisticaentrega.dao;

import br.com.logisticaentrega.model.Entrega;
import br.com.logisticaentrega.model.Motorista;
import br.com.logisticaentrega.model.Pedido;
import br.com.logisticaentrega.model.StatusEntrega;
import br.com.logisticaentrega.service.ServiceCRUD;
import br.com.logisticaentrega.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EntregaDao{

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
                SELECT id, pedido_id, motorista_id, data_saida, data_entrega, status_entrega
                FROM historicoEntrega;
                """;

        List<Entrega> entregas = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            ServiceCRUD crud = new ServiceCRUD();

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Integer id = rs.getInt("id");
                Integer pedido_id = rs.getInt("pedido_id");
                Integer motorista_id = rs.getInt("motorista_id");
                LocalDateTime data_saida = rs.getObject("data_saida", LocalDateTime.class);
                LocalDateTime data_entrega = rs.getObject("data_entrega", LocalDateTime.class);
                String status = rs.getString("status_entrega");

                Pedido pedido = crud.listagemId_pedido(pedido_id);
                Motorista motorista = crud.listagemId_motorista(motorista_id);

                var entrega = new Entrega(id, pedido, motorista, data_saida, data_entrega, status);
                entregas.add(entrega);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return entregas;
    }
}
