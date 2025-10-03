package com.adatech.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private int id;
    private Cliente cliente;
    private LocalDate dataCriacao;
    private List<ItemVenda> itens;

    // NOVO: Valor total BRUTO (soma dos itens SEM desconto de cupom)
    private BigDecimal valorBruto;

    // NOVO: Valor do desconto total aplicado pelo cupom
    private BigDecimal valorDesconto;

    private StatusPedido status;
    private Cupom cupomAplicado;

    // Construtor Básico
    public Pedido(int id, Cliente cliente, LocalDate dataCriacao) {
        this.id = id;
        this.cliente = cliente;
        this.dataCriacao = dataCriacao;
        this.itens = new ArrayList<>();
        this.valorBruto = BigDecimal.ZERO;
        this.valorDesconto = BigDecimal.ZERO; // Inicializa o desconto como zero
        this.status = StatusPedido.ABERTO;
        this.cupomAplicado = null;
    }

    // Construtor Completo
    public Pedido(int id, Cliente cliente, LocalDate dataCriacao,
                  List<ItemVenda> itens, BigDecimal valorBruto, BigDecimal valorDesconto, StatusPedido status) {
        this.id = id;
        this.cliente = cliente;
        this.dataCriacao = dataCriacao;
        this.itens = itens != null ? new ArrayList<>(itens) : new ArrayList<>();
        this.valorBruto = valorBruto != null ? valorBruto : BigDecimal.ZERO;
        this.valorDesconto = valorDesconto != null ? valorDesconto : BigDecimal.ZERO;
        this.status = status != null ? status : StatusPedido.ABERTO;
        this.cupomAplicado = null;
    }

    // ----------------------------------------------------------------------
    // GETTERS E SETTERS ESPECÍFICOS PARA O CUPOM
    // ----------------------------------------------------------------------

    public String getCupomCodigo() {
        return cupomAplicado != null ? cupomAplicado.getCodigo() : null;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public Cupom getCupomAplicado() {
        return cupomAplicado;
    }

    /**
     * MÉTODO DE REGISTRO DO CUPOM:
     * Atualiza o valor do desconto e o cupom aplicado.
     */
    public void aplicarCupom(Cupom cupom, BigDecimal novoValorTotal) {
        // O valor do desconto é a diferença entre o valorBruto e o novoValorTotal
        this.valorDesconto = this.valorBruto.subtract(novoValorTotal);
        this.cupomAplicado = cupom;
    }

    // ----------------------------------------------------------------------
    // MÉTODOS DE CÁLCULO
    // ----------------------------------------------------------------------

    public void calcularValorTotal() {
        // Calcula o valor bruto (subtotal)
        this.valorBruto = BigDecimal.ZERO;
        for (ItemVenda item : itens) {
            this.valorBruto = this.valorBruto.add(item.calcularSubtotal());
        }

        // Se a lista de itens muda, invalidamos o cupom
        if (this.valorDesconto.compareTo(BigDecimal.ZERO) > 0) {
            this.valorDesconto = BigDecimal.ZERO;
            this.cupomAplicado = null;
        }
    }

    /**
     *  Retorna o valor final do pedido após o desconto.
     */
    public BigDecimal getTotalComDesconto() {
        return this.valorBruto.subtract(this.valorDesconto);
    }

    // Sobrescreve o getter para valorTotal para retornar o valor com desconto
    public BigDecimal getValorTotal() {
        return getTotalComDesconto();
    }

    // ----------------------------------------------------------------------
    // MÉTODOS DE ITENS E GETTERS/SETTERS PADRÃO
    // ----------------------------------------------------------------------

    public void adicionarItem(ItemVenda item) {
        if (item != null) {
            itens.add(item);
            calcularValorTotal();
        }
    }

    public void removerItem(ItemVenda item) {
        if (item != null) {
            itens.remove(item);
            calcularValorTotal();
        }
    }

    public void removerItem(int index) {
        if (index >= 0 && index < itens.size()) {
            itens.remove(index);
            calcularValorTotal();
        }
    }

    // GETTERS E SETTERS PADRÃO (alguns ajustados para usar valorBruto)

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }
    public List<ItemVenda> getItens() { return new ArrayList<>(itens); }
    public void setItens(List<ItemVenda> itens) { this.itens = itens != null ? new ArrayList<>(itens) : new ArrayList<>(); calcularValorTotal(); }
    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status != null ? status : StatusPedido.ABERTO; }
    public BigDecimal getValorBruto() { return valorBruto; }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", cliente=" + cliente.getNome() +
                ", valorBruto=" + valorBruto +
                ", desconto=" + valorDesconto +
                ", valorTotal=" + getTotalComDesconto() +
                ", status=" + status +
                (cupomAplicado != null ? ", cupomAplicado=" + cupomAplicado.getCodigo() : "") +
                '}';
    }
}