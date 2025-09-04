package br.com.logisticaentrega.model;

import java.time.LocalDate;

public class HistoricoEntrega {

    private Integer id;
    private Entrega entrega;
    private LocalDate data_evento;
    private String descricao;

    public HistoricoEntrega(Integer id, Entrega entrega, LocalDate data_evento, String descricao) {
        this.id = id;
        this.entrega = entrega;
        this.data_evento = data_evento;
        this.descricao = descricao;
    }

    public HistoricoEntrega(Entrega entrega, LocalDate data_evento, String descricao) {
        this.entrega = entrega;
        this.data_evento = data_evento;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public LocalDate getData_evento() {
        return data_evento;
    }

    public void setData_evento(LocalDate data_evento) {
        this.data_evento = data_evento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
