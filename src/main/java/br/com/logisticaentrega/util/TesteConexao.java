package br.com.logisticaentrega.util;

import com.sun.security.jgss.GSSUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String args[]){
        try(Connection conn = Conexao.conectar()){
            if (conn != null){
                System.out.println("Conexão realizada com sucesso!");
            }
            else {
                System.out.println("Conexão falhou");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
