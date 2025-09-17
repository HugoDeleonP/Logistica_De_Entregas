package br.com.logisticaentrega.service;

import br.com.logisticaentrega.dao.*;
import br.com.logisticaentrega.model.*;
import br.com.logisticaentrega.view.Viewer;

import java.sql.SQLException;
import java.time.LocalDate;
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
                cadastrarEventoHistorico();
            }

            case 6 ->{
                atualizarStatusEntrega();
            }

            case 7 ->{
                listarCliente_Motorista();
                //Listar Todas as Entregas com Cliente e Motorista
            }

            case 12 ->{
                buscarPedidoCpfCnpj();
            }

            case 13 ->{
                //cancelarPedido();
            }

            case 14 ->{
                //excluirEntrega();
            }

            case 15 ->{
                //excluirCliente();
            }

            case 16 ->{
                //excluirMotorista();
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
        Integer id = uiView.readId("Cadastrar pedido", "o cliente");
        var cliente = listagemId_cliente(id);
        double volume = uiView.readVolume("Cadastrar pedido");
        double peso = uiView.readPeso("Cadastrar pedido");
        StatusPedido statusEnum = StatusPedido.PENDENTE;
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
        clientes = clienteData.select();

        for (Cliente clienteUnit: clientes){
            System.out.println(clienteUnit);
        }
    }

    private void listagemPedido(){
        var pedidoData = new PedidoDao();
        pedidos = pedidoData.select();

        for (Pedido pedidoUnit: pedidos){
            System.out.println(pedidoUnit);
        }
    }

    private void listagemMotorista(){
        var motoristaData = new MotoristaDao();
        motoristas = motoristaData.select();

        for(Motorista motoristaUnit: motoristas){
            System.out.println(motoristaUnit);
        }
    }

    private void listagemEntrega(){
        var entregaData = new EntregaDao();
        entregas = entregaData.select();

        for(Entrega entregaUnit: entregas){
            System.out.println(entregaUnit);
        }
    }


    private Cliente listagemId_cliente(Integer id){
        var clienteData = new ClienteDao();
        clientes = clienteData.select();

        for (Cliente clienteUnit: clientes){
            if(clienteUnit.getId() == id){
                return clienteUnit;
            }
        }

        return null;
    }
    private Pedido listagemId_pedido(Integer id){
        var pedidoData = new PedidoDao();
        pedidos = pedidoData.select();

        for (Pedido pedidoUnit: pedidos){
            if(pedidoUnit.getId() == id){
                return pedidoUnit;
            }
        }

        return null;
    }

    private Motorista listagemId_motorista(Integer id){
        var motoristaData = new MotoristaDao();
        motoristas = motoristaData.select();

        for(Motorista motoristaUnit: motoristas){
            if(motoristaUnit.getId() == id){
                return motoristaUnit;
            }
        }

        return null;
    }

    private Entrega listagemId_entrega(Integer id){
        var entregaDao = new EntregaDao();
        entregas = entregaDao.select();

        for(Entrega entregaUnit: entregas){
            if(entregaUnit.getId() == id){
                return entregaUnit;
            }
        }

        return null;
    }

    private void cadastrarEntrega(){
        var entregaData = new EntregaDao();
        var pedidoData = new PedidoDao();

        LocalDateTime data_entrega;

        listagemPedido();
        Integer pedido_id = uiView.readId("Cadastrar entrega", "o pedido");
        listagemMotorista();
        Integer motorista_id = uiView.readId("Cadastrar entrega", "o motorista");
        LocalDateTime data_saida = uiView.readDateTime("Cadastrar entrega", "saída");

        int answer = uiView.verifyDataEntrega("Cadastrar entrega");
        Boolean verify = routerVerifyEntrega(answer);

        String status_entrega;

        if(Boolean.TRUE.equals(verify)){
            data_entrega = uiView.readDateTime("Cadastrar entrega", "entrega");
            status_entrega = StatusEntrega.ENTREGUE.toString();
        }
        else{
            status_entrega = StatusEntrega.EM_ROTA.toString();
            data_entrega = null;
        }

        Pedido pedido = listagemId_pedido(pedido_id);
        Motorista motorista = listagemId_motorista(motorista_id);
        try {
            entregaData.insert(pedido, motorista, data_saida, data_entrega, status_entrega);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    private Boolean routerVerifyEntrega(int answer){
        switch (answer){

            case 1 ->{
                return true;
            }

            case 2 ->{
                return false;
            }

            default ->{
                return null;
            }
        }
    }

    private void cadastrarEventoHistorico(){
        var historicoEntregaData = new HistoricoEntregaDao();
        LocalDateTime data_evento;

        listagemEntrega();
        Integer entrega_id = uiView.readId("Cadastrar histórico de entrega", "a entrega");
        String descricao = uiView.readDescricao("Cadastrar histórico de entrega", "evento da entrega");
        Entrega entrega = listagemId_entrega(entrega_id);
        if(entrega.getData_entrega() != null){
            data_evento = entrega.getData_entrega();
        }
        else{
            uiView.errorDataEntrega_null();
            return;
        }


        try{
            historicoEntregaData.insert(entrega, LocalDate.from(data_evento), descricao);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    private void atualizarStatusEntrega(){
        var entregaData = new EntregaDao();
        LocalDateTime data_entrega;
        String statusEntrega;


        listagemEntrega();
        Integer entrega_id = uiView.readId("Atualizar status de entrega", "a entrega");
        Entrega entrega = listagemId_entrega(entrega_id);

        int answer = uiView.verifyDataEntrega("Atualizar status de entrega");
        Boolean verify = routerVerifyEntrega(answer);

        if(Boolean.TRUE.equals(verify)){
            data_entrega = uiView.readDateTime("Cadastrar entrega", "entrega");
            statusEntrega = StatusEntrega.ENTREGUE.toString();
            String statusPedido = StatusPedido.ENTREGUE.toString();
            var pedidoData = new PedidoDao();
            try {
                pedidoData.updateStatus(statusPedido, entrega.getPedido().getId());
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        else{
            uiView.warnEntregaAtrasada();
            data_entrega = null;
            statusEntrega = StatusEntrega.ATRASADA.toString();
        }


        try{
            entregaData.updateDataEntrega(data_entrega, entrega_id);
            entregaData.updateStatus(statusEntrega, entrega_id);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }


    private void listarCliente_Motorista(){
        var entregaData = new EntregaDao();
        entregas = entregaData.selectCliente_motorista();

        for(Entrega entrega: entregas){
            Cliente cliente = entrega.getPedido().getCliente();
            Motorista motorista = entrega.getMotorista();

            System.out.println(uiView.clienteMotorista_toString(entrega, cliente, motorista));

        }
    }

    private void buscarPedidoCpfCnpj(){
        PedidoDao pedidoData = new PedidoDao();
        String cnpj_cpf = uiView.search("Pesquisar pedido por CPF/CNPJ", "o pedido", "CPF/CNPJ do cliente");

        pedidos = pedidoData.search_CpfCnpj(cnpj_cpf);

        if(!pedidos.isEmpty()){

            for(Pedido pedido: pedidos){
                Cliente cliente = pedido.getCliente();
                System.out.println(uiView.searchPedido_cpfCnpj_toString(pedido, cliente));
            }
        }
        else{
            uiView.warnListEmpty("pesquisa de pedido");
        }
    }
}
