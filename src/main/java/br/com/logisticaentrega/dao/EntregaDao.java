package br.com.logisticaentrega.dao;

import br.com.logisticaentrega.model.Motorista;
import br.com.logisticaentrega.model.Pedido;
import br.com.logisticaentrega.model.StatusEntrega;
import br.com.logisticaentrega.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

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
}
