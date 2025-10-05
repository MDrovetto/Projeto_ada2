package com.adatech.ecommerce.model;

import java.io.Serializable; // Adicionamos a interface por clareza

public enum StatusPedido implements Serializable {

    // Enums não precisam de serialVersionUID se não tiverem campos transient

    ABERTO("Pedido Aberto"),
    AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
    PAGO("Pago"),
    FINALIZADO("Finalizado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}