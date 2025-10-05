package com.adatech.ecommerce.model;

import java.io.Serializable; // 1. Torna a classe serializável
import java.math.BigDecimal;
import java.util.Objects; // Importação útil para checagem de nulidade

public class ItemVenda implements Serializable {

    // Recomendado para controle de versão
    private static final long serialVersionUID = 1L;

    private Produto produto;
    private int quantidade;
    private BigDecimal precoVenda;

    public ItemVenda(Produto produto, int quantidade, BigDecimal precoVenda) {
        // Delega a validação para os setters para garantir que o objeto seja válido desde a criação
        setProduto(produto);
        setQuantidade(quantidade);
        setPrecoVenda(precoVenda);
    }

    public Produto getProduto() {
        return produto;
    }

    // Validação: O produto não pode ser nulo
    public void setProduto(Produto produto) {
        // Se o produto for nulo, não é um ItemVenda válido.
        if (produto == null) {
            throw new IllegalArgumentException("O produto de um item de venda não pode ser nulo.");
        }
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    // Validação: A quantidade deve ser positiva
    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) {
            // Um item de venda deve ter pelo menos 1 unidade
            throw new IllegalArgumentException("A quantidade de um item de venda deve ser maior que zero.");
        }
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    // Validação: O preço de venda deve ser positivo
    public void setPrecoVenda(BigDecimal precoVenda) {
        // 1. Checagem de nulo (precoVenda não pode ser nulo)
        Objects.requireNonNull(precoVenda, "O preço de venda não pode ser nulo.");

        // 2. Checagem de valor (não pode ser zero ou negativo)
        if (precoVenda.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O preço de venda deve ser maior que zero.");
        }
        this.precoVenda = precoVenda;
    }

    public BigDecimal calcularSubtotal() {
        // Garante que o cálculo use os valores internos, que já são validados
        return precoVenda.multiply(BigDecimal.valueOf(quantidade));
    }

    @Override
    public String toString() {
        return "ItemVenda{" +
                "produto=" + (produto != null ? produto.getNome() : "N/A") + // Exibição mais amigável
                ", quantidade=" + quantidade +
                ", precoVenda=" + precoVenda +
                ", subtotal=" + calcularSubtotal() +
                '}';
    }
}