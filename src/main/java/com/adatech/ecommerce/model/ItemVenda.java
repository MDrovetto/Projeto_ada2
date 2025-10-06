package com.adatech.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ItemVenda implements Serializable {

    private static final long serialVersionUID = 1L;

    private Produto produto;
    private int quantidade;
    private BigDecimal precoVenda;

    public ItemVenda(Produto produto, int quantidade, BigDecimal precoVenda) {
        setProduto(produto);
        setQuantidade(quantidade);
        setPrecoVenda(precoVenda);
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("O produto de um item de venda não pode ser nulo.");
        }
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade de um item de venda deve ser maior que zero.");
        }
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        Objects.requireNonNull(precoVenda, "O preço de venda não pode ser nulo.");

        if (precoVenda.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O preço de venda deve ser maior que zero.");
        }
        this.precoVenda = precoVenda;
    }

    public BigDecimal calcularSubtotal() {
        return precoVenda.multiply(BigDecimal.valueOf(quantidade));
    }

    @Override
    public String toString() {
        return "ItemVenda{" +
                "produto=" + (produto != null ? produto.getNome() : "N/A") +
                ", quantidade=" + quantidade +
                ", precoVenda=" + precoVenda +
                ", subtotal=" + calcularSubtotal() +
                '}';
    }
}