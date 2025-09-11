package br.com.logisticaentrega.model;

public enum StatusEntrega {
    EM_ROTA("EM_ROTA"),
    ENTREGUE("ENTREGUE"),
    ATRASADA("ATRASADA");

    private final String ENUM_CHOOSE;

    StatusEntrega(String ENUM_CHOOSE){
        this.ENUM_CHOOSE = ENUM_CHOOSE;
    }

    @Override
    public String toString() {
        return ENUM_CHOOSE;
    }
}
