package br.com.logisticaentrega.view;

import br.com.logisticaentrega.model.StatusEntrega;
import br.com.logisticaentrega.model.StatusPedido;

import javax.swing.text.DateFormatter;
import javax.swing.text.View;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class Viewer {

    Scanner input;

    public Viewer(){
        input = new Scanner(System.in);
    }

    public int mainMenu(){

        System.out.println("=========================| Logística de entregas |=========================");
        System.out.println(" 1 - Cadastrar Cliente");
        System.out.println(" 2 - Cadastrar Motorista");
        System.out.println(" 3 - Criar Pedido");
        System.out.println(" 4 - Atribuir Pedido a Motorista (Gerar Entrega)");
        System.out.println(" 5 - Registrar Evento de Entrega (Histórico)");
        System.out.println(" 6 - Atualizar Status da Entrega");
        System.out.println(" 7 - Listar Todas as Entregas com Cliente e Motorista");
        System.out.println(" 8 - Relatório: Total de Entregas por Motorista");
        System.out.println(" 9 - Relatório: Clientes com Maior Volume Entregue");
        System.out.println(" 10 - Relatório: Pedidos Pendentes por Estado");
        System.out.println(" 11 - Relatório: Entregas Atrasadas por Cidade");
        System.out.println(" 12 - Buscar Pedido por CPF/CNPJ do Cliente");
        System.out.println(" 13 - Cancelar Pedido");
        System.out.println(" 14 - Excluir Entrega (com validação)");
        System.out.println(" 15 - Excluir Cliente (com verificação de dependência)");
        System.out.println(" 16 - Excluir Motorista (com verificação de dependência)");
        System.out.println(" \n0 - Sair");
        System.out.println("===========================================================================");

        System.out.println("\n Digite o que deseja fazer, conforme a legenda acima:");
        int escolha = input.nextInt();
        input.nextLine();

        return escolha;
    }

    public String readNome(String titulo, String entidade){
        System.out.printf("=========================| %s |=========================\n", titulo);
        System.out.printf(" Digite o nome do %s: \n", entidade);
        return input.nextLine();
    }

    public String readCpf_Cnpj(String titulo){
        System.out.printf("=========================| %s |=========================\n", titulo);
        System.out.println(" Digite o CPF/CNPJ: ");
        return input.nextLine();
    }

    public String readEndereco(String titulo){
        System.out.printf("=========================| %s |=========================\n", titulo);
        System.out.println(" Digite o Endereço: ");
        return input.nextLine();
    }

    public String readCidade(String titulo){
        System.out.printf("=========================| %s |=========================\n", titulo);
        System.out.println(" Digite a cidade: ");
        return input.nextLine();
    }

    public String readEstado(String titulo){
        System.out.printf("=========================| %s |=========================\n", titulo);
        System.out.println(" Digite o estado: ");
        return input.nextLine();
    }

    public String readCnh(String titulo){
        System.out.printf("=========================| %s |=========================\n", titulo);
        System.out.println(" Digite o CNH: ");
        return input.nextLine();
    }

    public String readVeiculo(String titulo){
        System.out.printf("=========================| %s |=========================\n", titulo);
        System.out.println(" Digite o Veículo: ");
        return input.nextLine();
    }

    public Integer readId(String titulo, String entidade){
        System.out.printf("=========================| %s |=========================\n", titulo);
        System.out.printf(" Digite o ID d%s: ", entidade);
        Integer id = input.nextInt();
        input.nextLine();

        return id;
    }

    public double readVolume(String titulo){
        System.out.printf("=========================| %s |=========================\n", titulo);
        System.out.println(" Digite o Volume (m³): ");
        double volume = input.nextDouble();
        input.nextLine();

        return volume;
    }

    public double readPeso(String titulo){
        System.out.printf("=========================| %s |=========================\n", titulo);
        System.out.println(" Digite o Peso (Kg): ");
        double peso = input.nextDouble();
        input.nextLine();

        return peso;
    }

    public StatusPedido readStatusPedido(String titulo){
        System.out.printf("=========================| %s |=========================\n", titulo);
        System.out.println(" 1- PENDENTE");
        System.out.println(" 2- ENTREGUE");
        System.out.println(" 3- CANCELADO");
        int escolha = input.nextInt();
        input.nextLine();

        switch (escolha){
            case 1 ->{
                return StatusPedido.PENDENTE;
            }

            case 2 ->{
                return StatusPedido.ENTREGUE;
            }

            case 3 ->{
                return StatusPedido.CANCELADO;
            }

            default ->{
                return null;
            }
        }
    }

    public LocalDateTime readDateTime(String titulo, String entidade){
        System.out.printf("=========================| %s |=========================\n", titulo);
        System.out.printf(" Digite a data de %s (YYYY-MM-DD): \n", entidade);
        String dateString = input.nextLine();
        System.out.printf(" Digite o horário de %s (hh:mm:ss): \n", entidade);
        String timeString = input.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

        String dateTimeString = dateString + "T" + timeString;

        return LocalDateTime.parse(dateTimeString);
    }

    public LocalDate readDate(String titulo, String entidade){
        System.out.printf("=========================| %s |=========================\n", titulo);
        System.out.printf(" Digite a data de %s (YYYY-MM-DD): \n", entidade);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = input.nextLine();

        return LocalDate.parse(dateString);
    }

    public StatusEntrega readStatusEntrega(String titulo){
        System.out.printf("=========================| %s |=========================\n", titulo);
        System.out.println(" 1- EM_ROTA");
        System.out.println(" 2- ENTREGUE");
        System.out.println(" 3- ATRASADA");
        int escolha = input.nextInt();
        input.nextLine();

        switch (escolha){
            case 1 ->{
                return StatusEntrega.EM_ROTA;
            }

            case 2 ->{
                return StatusEntrega.ENTREGUE;
            }

            case 3 ->{
                return StatusEntrega.ATRASADA;
            }

            default ->{
                return null;
            }
        }
    }

    public String readDescricao(String titulo, String entidade){
        System.out.printf("=========================| %s |=========================\n", titulo);
        System.out.printf(" Digite a descrição de %s: \n", entidade);
        return input.nextLine();
    }
}
