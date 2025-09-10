package br.com.logisticaentrega.service;

import br.com.logisticaentrega.dao.ClienteDao;
import br.com.logisticaentrega.dao.MotoristaDao;
import br.com.logisticaentrega.dao.PedidoDao;
import br.com.logisticaentrega.model.*;
import br.com.logisticaentrega.view.Viewer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceCRUD {
    List<Cliente> clientes;
    List<Entrega> entregas;
    List<HistoricoEntrega> historicoEntregas;
    List<Motorista> motoristas;
    List<Pedido> pedidos;

    Viewer uiView;

    public ServiceCRUD(){
        clientes = new ArrayList<>();
        entregas = new ArrayList<>();
        historicoEntregas = new ArrayList<>();
        motoristas = new ArrayList<>();
        pedidos = new ArrayList<>();

        uiView = new Viewer();
    }

    public void mainRouter(int opcao){

        switch (opcao){

            case 1 ->{
                cadastrarCliente();
            }

            case 2 ->{
                cadastrarMotorista();
            }

            case 3 ->{
                //cadastrarPedido();
            }

            case 4 ->{
                //cadastrarEntrega();
            }

            case 5 ->{
                //cadastrarEventoHistorico();
            }

            case 6 ->{
                //atualizarStatusEntrega();
            }

            case 7 ->{
                //listarCliente_Motorista();
            }
        }

    }

    private void cadastrarCliente(){
        var clienteData = new ClienteDao();

        String nome = uiView.readNome("Cadastrar cliente", "cliente");
        String cpf_cnpj = uiView.readCpf_Cnpj("Cadastrar cliente");
        String endereco = uiView.readEndereco("Cadastrar cliente");
        String cidade = uiView.readCidade("Cadastrar cliente");
        String estado = uiView.readEstado("Cadastrar cliente");

        try{
            clienteData.insert(nome, cpf_cnpj, endereco, cidade, estado);
        } catch (SQLException e) {
            System.out.println("Conexão com banco de dados não realizada");
            e.printStackTrace();
        }
    }

    private void cadastrarMotorista(){
        var motoristaData = new MotoristaDao();

        String nome = uiView.readNome("Cadastrar motorista", "motorista");
        String cnh = uiView.readCnh("Cadastrar motorista");
        String veiculo = uiView.readVeiculo("Cadastrar motorista");
        String cidade_base = uiView.readCidade("Cadastrar motorista");

        try{
            motoristaData.insert(nome, cnh, veiculo, cidade_base);
        } catch (SQLException e){
            System.out.println("Conexão com banco de dados não realizada");
            e.printStackTrace();
        }


    }

    private void cadastrarPedido(){
        var pedidoData = new PedidoDao();
        listagem();
        Integer id = uiView.readId("Cadastrar pedido", "cliente");
        double volume = uiView.readVolume("Cadastrar pedido");
        double peso = uiView.readPeso("Cadastrar pedido");
        StatusPedido status = null;

        do{
             status = uiView.readStatus("Cadastrar pedido");
        } while(status != null);

        pedidoData.insert( volume, peso, status);

    }

    private void listagem(){
        var clienteData = new ClienteDao();
        var clientes = clienteData.select();
        for (Cliente cliente: clientes){
            System.out.println(cliente);
        }
    }

}
