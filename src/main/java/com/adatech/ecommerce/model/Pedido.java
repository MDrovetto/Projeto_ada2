package com.adatech.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private Cliente cliente;
    private LocalDate dataCriacao;
    private List<ItemVenda> itens;

    private BigDecimal valorBruto;
    private BigDecimal valorDesconto;

    private StatusPedido status;
    private Cupom cupomAplicado;

    public Pedido(int id, Cliente cliente, LocalDate dataCriacao) {
        setCliente(cliente);
        setDataCriacao(dataCriacao);

        this.id = id;
        this.itens = new ArrayList<>();
        this.valorBruto = BigDecimal.ZERO;
        this.valorDesconto = BigDecimal.ZERO;
        this.status = StatusPedido.ABERTO;
        this.cupomAplicado = null;
    }

    public Pedido(int id, Cliente cliente, LocalDate dataCriacao,
                  List<ItemVenda> itens, BigDecimal valorBruto, BigDecimal valorDesconto, StatusPedido status) {
        setCliente(cliente);
        setDataCriacao(dataCriacao);
        setStatus(status);

        this.id = id;
        this.itens = itens != null ? new ArrayList<>(itens) : new ArrayList<>();
        this.valorBruto = valorBruto != null ? valorBruto : BigDecimal.ZERO;
        this.valorDesconto = valorDesconto != null ? valorDesconto : BigDecimal.ZERO;
        this.cupomAplicado = null;
    }

    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("O Pedido deve estar associado a um Cliente válido.");
        }
        this.cliente = cliente;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        if (dataCriacao == null) {
            throw new IllegalArgumentException("A data de criação do pedido não pode ser nula.");
        }
        this.dataCriacao = dataCriacao;
    }

    public void setStatus(StatusPedido status) {
        if (status == null) {
            throw new IllegalArgumentException("O status do pedido não pode ser nulo.");
        }
        this.status = status;
    }

    public void adicionarItem(ItemVenda item) {
        if (item == null) {
            throw new IllegalArgumentException("Não é possível adicionar um item nulo.");
        }
        if (this.status != StatusPedido.ABERTO) {
            throw new IllegalArgumentException("Só é possível adicionar itens a um pedido em status ABERTO.");
        }
        itens.add(item);
        calcularValorTotal();
    }
    public void removerItem(ItemVenda item) {
        if (item == null) {
            return;
        }
        if (this.status != StatusPedido.ABERTO) {
            throw new IllegalArgumentException("Só é possível remover itens de um pedido em status ABERTO.");
        }

        itens.remove(item);
        calcularValorTotal();
    }

    public void aplicarCupom(Cupom cupom, BigDecimal novoValorTotal) {
        if (cupom == null) {
            throw new IllegalArgumentException("O cupom aplicado não pode ser nulo.");
        }
        if (novoValorTotal == null || novoValorTotal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O novo valor total após o desconto deve ser positivo.");
        }

        this.valorDesconto = this.valorBruto.subtract(novoValorTotal);
        this.cupomAplicado = cupom;
    }

    public String getCupomCodigo() {
        return cupomAplicado != null ? cupomAplicado.getCodigo() : null;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public Cupom getCupomAplicado() {
        return cupomAplicado;
    }

    public void calcularValorTotal() {
        this.valorBruto = BigDecimal.ZERO;
        for (ItemVenda item : itens) {
            this.valorBruto = this.valorBruto.add(item.calcularSubtotal());
        }
        if (this.valorDesconto.compareTo(BigDecimal.ZERO) > 0) {
            this.valorDesconto = BigDecimal.ZERO;
            this.cupomAplicado = null;
        }
    }

    public BigDecimal getTotalComDesconto() {
        return this.valorBruto.subtract(this.valorDesconto);
    }

    public BigDecimal getValorTotal() {
        return getTotalComDesconto();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Cliente getCliente() { return cliente; }
    public LocalDate getDataCriacao() { return dataCriacao; }
    public List<ItemVenda> getItens() { return new ArrayList<>(itens); }
    public void setItens(List<ItemVenda> itens) { this.itens = itens != null ? new ArrayList<>(itens) : new ArrayList<>(); calcularValorTotal(); }
    public StatusPedido getStatus() { return status; }
    public BigDecimal getValorBruto() { return valorBruto; }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", cliente=" + (cliente != null ? cliente.getNome() : "N/A") +
                ", valorBruto=" + valorBruto +
                ", desconto=" + valorDesconto +
                ", valorTotal=" + getTotalComDesconto() +
                ", status=" + status +
                (cupomAplicado != null ? ", cupomAplicado=" + cupomAplicado.getCodigo() : "") +
                '}';
    }
}