package com.adatech.ecommerce.model;

import java.math.BigDecimal;

public class ItemVenda {
    private Produto produto;
    private int quantidade;
    private BigDecimal precoVenda;

    public ItemVenda(Produto produto, int quantidade, BigDecimal precoVenda) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoVenda = precoVenda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public BigDecimal calcularSubtotal() {
        return precoVenda.multiply(BigDecimal.valueOf(quantidade));
    }

    @Override
    public String toString() {
        return "ItemVenda{" +
                "produto=" + produto +
                ", quantidade=" + quantidade +
                ", precoVenda=" + precoVenda +
                ", subtotal=" + calcularSubtotal() +
                '}';
    }

    private class Produto {
    }
}