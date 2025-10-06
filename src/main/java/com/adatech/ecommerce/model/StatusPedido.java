package com.adatech.ecommerce.model;

import java.io.Serializable;

public enum StatusPedido implements Serializable {

    ABERTO("Pedido Aberto"),
    AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
    PAGO("Pago"),
    FINALIZADO("Finalizado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}