package br.com.logisticaentrega.service;

import br.com.logisticaentrega.dao.EntregaDao;
import br.com.logisticaentrega.dao.PedidoDao;
import br.com.logisticaentrega.view.Viewer;
import java.sql.SQLException;

public class ServiceRelatorio {

    Viewer uiView;

    public ServiceRelatorio(){
        uiView = new Viewer();
    }

    public void totalEntregaByMotorista(){
        EntregaDao entregaData = new EntregaDao();

        System.out.println(uiView.reportTitle_toString("Total de entrega por motorista"));

        try{
            entregaData.totalEntregaByMotorista();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void maiorVolumeByCliente(){
        PedidoDao pedidoData = new PedidoDao();

        System.out.println(uiView.reportTitle_toString("Maior volume de pedido por cliente"));

        try {
            pedidoData.maiorVolumeByCliente();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void pedidoPendenteByEstado(){
        PedidoDao pedidoData = new PedidoDao();

        System.out.println(uiView.reportTitle_toString("Quantidade de pedidos pendentes por estado"));

        try {
            pedidoData.pedidoPendenteByEstado();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void totalEntregaAtrasadaByCidade(){
        EntregaDao entregaData = new EntregaDao();

        System.out.println(uiView.reportTitle_toString("Total de entregas atrasadas por cidade"));

        try {
            entregaData.totalEntregaAtrasadaByCidade();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }


}
