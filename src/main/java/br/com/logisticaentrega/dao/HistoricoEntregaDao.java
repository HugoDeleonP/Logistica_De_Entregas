package br.com.logisticaentrega.dao;

import br.com.logisticaentrega.model.Entrega;
import br.com.logisticaentrega.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class HistoricoEntregaDao {

    public void insert(Entrega entrega, LocalDate data_evento, String descricao) throws SQLException {
        String sql = """
                INSERT INTO historicoEntrega (entrega_id, data_evento, descricao)
                VALUES (?, ?, ?);
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, entrega.getId());
            stmt.setObject(2, data_evento);
            stmt.setString(3, descricao);

            stmt.executeUpdate();
            System.out.println("Histórico de entrega cadastrado com sucesso!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


}
