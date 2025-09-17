package br.com.logisticaentrega.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Entrega {

    private Integer id;
    private Pedido pedido;
    private Motorista motorista;
    private LocalDateTime data_saida;
    private LocalDateTime data_entrega;
    private String status_entrega;

    public Entrega(Integer id, Pedido pedido, Motorista motorista, LocalDateTime data_saida, LocalDateTime data_entrega, String status_entrega) {
        this.id = id;
        this.pedido = pedido;
        this.motorista = motorista;
        this.data_saida = data_saida;
        this.data_entrega = data_entrega;
        this.status_entrega = status_entrega;
    }

    public Entrega(Pedido pedido, Motorista motorista, LocalDateTime data_saida, LocalDateTime data_entrega, String status_entrega) {
        this.pedido = pedido;
        this.motorista = motorista;
        this.data_saida = data_saida;
        this.data_entrega = data_entrega;
        this.status_entrega = status_entrega;
    }

    public Entrega(Motorista motorista, int quantidadeEntrega){
        this.motorista = motorista;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public LocalDateTime getData_saida() {
        return data_saida;
    }

    public void setData_saida(LocalDateTime data_saida) {
        this.data_saida = data_saida;
    }

    public LocalDateTime getData_entrega() {
        return data_entrega;
    }

    public void setData_entrega(LocalDateTime data_entrega) {
        this.data_entrega = data_entrega;
    }

    public String getStatus_entrega() {
        return status_entrega;
    }

    public void setStatus_entrega(String status_entrega) {
        this.status_entrega = status_entrega;
    }

    @Override
    public String toString() {
        return "=========================| Entrega |=========================\n" +
                "ID: " + this.id +
                "\nPedido: " + this.pedido.getId() +
                "\nMotorista: " + this.motorista.getNome() +
                "\nData de saída: " + this.data_saida +
                "\nData de entrega: " + this.data_entrega +
                "\nStatus da entrega: " + this.status_entrega + "\n";
    }
}
