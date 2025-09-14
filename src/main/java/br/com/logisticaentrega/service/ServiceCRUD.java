package br.com.logisticaentrega.service;

import br.com.logisticaentrega.dao.ClienteDao;
import br.com.logisticaentrega.dao.EntregaDao;
import br.com.logisticaentrega.dao.MotoristaDao;
import br.com.logisticaentrega.dao.PedidoDao;
import br.com.logisticaentrega.model.*;
import br.com.logisticaentrega.view.Viewer;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceCRUD {
    List<Cliente> clientes;
    List<Entrega> entregas;
    List<HistoricoEntrega> historicoEntregas;
    List<Motorista> motoristas;
    List<Pedido> pedidos;

    Viewer uiView;
    ServiceRelatorio relatorio = new ServiceRelatorio();

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
                cadastrarPedido();
            }

            case 4 ->{
                cadastrarEntrega();
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

        listagemCliente();
        Integer id = uiView.readId("Cadastrar pedido", "cliente");
        var cliente = listagemId_cliente(id);
        double volume = uiView.readVolume("Cadastrar pedido");
        double peso = uiView.readPeso("Cadastrar pedido");
        StatusPedido statusEnum = uiView.readStatusPedido("Cadastrar pedido");
        String status = statusEnum.toString();

        try{
            pedidoData.insert(cliente, volume, peso, status);
        } catch (SQLException e){
            System.out.println("Conexão com banco de dados não realizada");
            e.printStackTrace();
        }


    }

    private void listagemCliente(){
        var clienteData = new ClienteDao();
        var clientes = clienteData.select();

        for (Cliente clienteUnit: clientes){
            System.out.println(clienteUnit);
        }
    }

    private void listagemPedido(){
        var pedidoData = new PedidoDao();
        var pedidos = pedidoData.select();

        for (Pedido pedidoUnit: pedidos){
            System.out.println(pedidoUnit);
        }
    }

    private void listagemMotorista(){
        var motoristaData = new MotoristaDao();
        var motoristas = motoristaData.select();

        for(Motorista motoristaUnit: motoristas){
            System.out.println(motoristaUnit);
        }
    }

    private Cliente listagemId_cliente(Integer id){
        var clienteData = new ClienteDao();
        var clientes = clienteData.select();

        for (Cliente clienteUnit: clientes){
            if(clienteUnit.getId() == id){
                return clienteUnit;
            }
        }

        return null;
    }
    private Pedido listagemId_pedido(Integer id){
        var pedidoData = new PedidoDao();
        var pedidos = pedidoData.select();

        for (Pedido pedidoUnit: pedidos){
            if(pedidoUnit.getId() == id){
                return pedidoUnit;
            }
        }

        return null;
    }

    private Motorista listagemId_motorista(Integer id){
        var motoristaData = new MotoristaDao();
        var motoristas = motoristaData.select();

        for(Motorista motoristaUnit: motoristas){
            if(motoristaUnit.getId() == id){
                return motoristaUnit;
            }
        }

        return null;
    }

    private void cadastrarEntrega(){
        var entregaData = new EntregaDao();
        listagemPedido();
        Integer pedido_id = uiView.readId("Cadastrar entrega", "pedido");
        listagemMotorista();
        Integer motorista_id = uiView.readId("Cadastrar entrega", "motorista");
        LocalDateTime data_saida = uiView.readDateTime("Cadastrar entrega", "saída");
        LocalDateTime data_entrega = uiView.readDateTime("Cadastrar entrega", "entrega");
        StatusEntrega statusEnum = uiView.readStatusEntrega("Cadastrar entrega");
        String status = statusEnum.toString();

        Pedido pedido = listagemId_pedido(pedido_id);
        Motorista motorista = listagemId_motorista(motorista_id);
        try {
            entregaData.insert(pedido, motorista, data_saida, data_entrega, status);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
