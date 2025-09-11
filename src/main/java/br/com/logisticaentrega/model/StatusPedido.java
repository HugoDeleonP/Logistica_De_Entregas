package br.com.logisticaentrega.model;

public enum StatusPedido {
    PENDENTE("PENDENTE"),
    ENTREGUE("ENTREGUE"),
    CANCELADO("CANCELADO");

    private final String ENUM_CHOOSE;

    StatusPedido(String ENUM_CHOOSE){
        this.ENUM_CHOOSE = ENUM_CHOOSE;
    }

    @Override
    public String toString() {
        return ENUM_CHOOSE;
    }
}
