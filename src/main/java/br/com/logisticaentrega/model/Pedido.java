package br.com.logisticaentrega.model;

import java.time.LocalDateTime;
import java.lang.Enum;

public class Pedido {

    private Integer id;
    private Cliente cliente;
    private LocalDateTime data_pedido;
    private double volume_m3;
    private double peso_kg;
    private StatusPedido status_pedido;

    public Pedido(Integer id, Cliente cliente, LocalDateTime data_pedido, double volume_m3, double peso_kg, StatusPedido status_pedido) {
        this.id = id;
        this.cliente = cliente;
        this.data_pedido = data_pedido;
        this.volume_m3 = volume_m3;
        this.peso_kg = peso_kg;
        this.status_pedido = status_pedido;
    }

    public Pedido(Cliente cliente, LocalDateTime data_pedido, double volume_m3, double peso_kg, StatusPedido status_pedido) {
        this.cliente = cliente;
        this.data_pedido = data_pedido;
        this.volume_m3 = volume_m3;
        this.peso_kg = peso_kg;
        this.status_pedido = status_pedido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getData_pedido() {
        return data_pedido;
    }

    public void setData_pedido(LocalDateTime data_pedido) {
        this.data_pedido = data_pedido;
    }

    public double getVolume_m3() {
        return volume_m3;
    }

    public void setVolume_m3(double volume_m3) {
        this.volume_m3 = volume_m3;
    }

    public double getPeso_kg() {
        return peso_kg;
    }

    public void setPeso_kg(double peso_kg) {
        this.peso_kg = peso_kg;
    }

    public StatusPedido getStatus_pedido() {
        return status_pedido;
    }

    public void setStatus_pedido(StatusPedido status_pedido) {
        this.status_pedido = status_pedido;
    }
}
