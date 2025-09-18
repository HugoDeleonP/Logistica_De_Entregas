package br.com.logisticaentrega.service;

import br.com.logisticaentrega.dao.*;
import br.com.logisticaentrega.model.*;
import br.com.logisticaentrega.view.Viewer;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

            case 8 ->{
                relatorio.totalEntregaByMotorista();
            }

            case 9 ->{
                relatorio.maiorVolumeByCliente();
            }

            case 10 ->{
                relatorio.pedidoPendenteByEstado();
            }

            case 11 ->{
                relatorio.totalEntregaAtrasadaByCidade();
            }

            case 12 ->{
                buscarPedidoCpfCnpj();
            }

            case 13 ->{
                cancelarPedido();
            }

            case 14 ->{
                excluirEntrega();

                // verifica se está atrasada -> não exclui as atrasadas
            }

            case 15 ->{
                excluirCliente();

                // verifica se tem pedido pendente atrelado
            }

            case 16 ->{
                excluirMotorista();

                // verifica se há entrega atrasada atrelada
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
        String statusEnum = StatusPedido.PENDENTE.toString();

        try{
            pedidoData.insert(cliente, volume, peso, statusEnum);
        } catch (SQLException e){
            System.out.println("Conexão com banco de dados não realizada");
            e.printStackTrace();
        }


    }

    private void listagemCliente(){
        var clienteData = new ClienteDao();
        try{
            clientes = clienteData.select();
        }catch (SQLException e){
            e.printStackTrace();
        }


        for (Cliente clienteUnit: clientes){
            System.out.println(clienteUnit);
        }
    }

    private void listagemPedido(){
        var pedidoData = new PedidoDao();
        try{
            pedidos = pedidoData.select();
        } catch (SQLException e){
            e.printStackTrace();
        }


        for (Pedido pedidoUnit: pedidos){
            System.out.println(pedidoUnit);
        }
    }

    private void listagemMotorista(){
        var motoristaData = new MotoristaDao();
        try{
            motoristas = motoristaData.select();
        }catch (SQLException e){
            e.printStackTrace();
        }


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
        try{
            clientes = clienteData.select();
        }catch (SQLException e){
            e.printStackTrace();
        }

        for (Cliente clienteUnit: clientes){
            if(clienteUnit.getId() == id){
                return clienteUnit;
            }
        }

        return null;
    }
    private Pedido listagemId_pedido(Integer id){
        var pedidoData = new PedidoDao();
        try{
            pedidos = pedidoData.select();
        } catch (SQLException e){
            e.printStackTrace();
        }


        for (Pedido pedidoUnit: pedidos){
            if(pedidoUnit.getId() == id){
                return pedidoUnit;
            }
        }

        return null;
    }

    private Motorista listagemId_motorista(Integer id){
        var motoristaData = new MotoristaDao();

        try {
            motoristas = motoristaData.select();
        }catch (SQLException e){
            e.printStackTrace();
        }

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
        try{
            entregas = entregaData.selectCliente_motorista();
        }catch (SQLException e){
            e.printStackTrace();
        }

        for(Entrega entrega: entregas){
            Cliente cliente = entrega.getPedido().getCliente();
            Motorista motorista = entrega.getMotorista();

            System.out.println(uiView.clienteMotorista_toString(entrega, cliente, motorista));

        }
    }

    private void buscarPedidoCpfCnpj(){
        PedidoDao pedidoData = new PedidoDao();
        String cnpj_cpf = uiView.search("Pesquisar pedido por CPF/CNPJ", "o pedido", "CPF/CNPJ do cliente");

        try{
            pedidos = pedidoData.search_CpfCnpj(cnpj_cpf);
        } catch (SQLException e){
            e.printStackTrace();
        }


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

    private void cancelarPedido(){
        var pedidoData = new PedidoDao();

        listagemPedido();
        Integer pedido_id = uiView.readId("Cancelar pedido", "a entrega");
        Pedido pedido = listagemId_pedido(pedido_id);

        if(!Objects.equals(pedido.getStatus_pedido(), StatusPedido.ENTREGUE.toString())){
            try{
                pedidoData.updateStatus(StatusPedido.CANCELADO.toString(), pedido.getId());
            } catch (SQLException e){
                e.printStackTrace();
            }
        } else{
            uiView.warnCancelamentoPedido("o cancelamento do pedido", "o pedido já está entregue");
        }

    }

    private void excluirEntrega(){
        // verifica se está atrasada -> não exclui as atrasadas
        EntregaDao entregaData = new EntregaDao();
        listagemEntrega();

        Integer entrega_id = uiView.readId("Excluir entrega", "a entrega");
        Entrega entrega = listagemId_entrega(entrega_id);
        String statusAtrasada = StatusEntrega.ATRASADA.toString();

        if(!entrega.getStatus_entrega().equals(statusAtrasada)){
            try{
                entregaData.delete(entrega_id);
                uiView.sucessOperation("A entrega", "deletada");
            }catch (SQLException e){
                e.printStackTrace();
            }
        } else{
            uiView.warnDependencia("a entrega " + entrega.getId(), "o status de atraso");
        }

    }

    private Cliente validacaoExclusaoCliente(Integer cliente_id){
        PedidoDao pedidoData = new PedidoDao();

        try{
            pedidos = pedidoData.select();
        } catch (SQLException e){
            e.printStackTrace();
        }

        for(Pedido pedido: pedidos){
            var cliente = pedido.getCliente();
            String pedidoPendente = StatusPedido.PENDENTE.toString();
            if(cliente.getId() == cliente_id){
                if(!pedido.getStatus_pedido().equals(pedidoPendente)){
                    return cliente;
                }
                else{
                    uiView.warnDependencia("o pedido " + pedido.getId(), "o status de pendência");
                }
            }
        }

        return null;
    }

    private void excluirCliente(){
        // verifica se tem pedido pendente atrelado
        ClienteDao clienteData = new ClienteDao();
        listagemCliente();
        Integer cliente_id = uiView.readId("Excluir cliente", "o cliente");
        Cliente cliente = validacaoExclusaoCliente(cliente_id);

        try{
            clienteData.delete(cliente);
            uiView.sucessOperation("O cliente", "deletado");
        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    private Motorista validacaoExclusaoMotorista(Integer motorista_id){
        EntregaDao entregaData = new EntregaDao();
        entregas = entregaData.select();

        for(Entrega entrega: entregas){
            Motorista motorista = entrega.getMotorista();
            String entregaAtrasada = StatusEntrega.ATRASADA.toString();

            if(Objects.equals(motorista.getId(), motorista_id)){
                if(!entrega.getStatus_entrega().equals(entregaAtrasada)){
                    return motorista;
                }
                else{
                    uiView.warnDependencia("a entrega " + entrega.getId(), "o status de atraso");
                }
            }
            else {
                uiView.warnListEmpty("exclusão");
            }
        }

        return null;
    }

    private void excluirMotorista(){
        // verifica se há entrega atrasada atrelada
        MotoristaDao motoristaData = new MotoristaDao();
        listagemMotorista();
        Integer motorista_id = uiView.readId("Excluir motorista", "o motorista");
        Motorista motorista = validacaoExclusaoMotorista(motorista_id);


        try{
            motoristaData.delete(motorista);
            uiView.sucessOperation("O cliente", "deletado");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
