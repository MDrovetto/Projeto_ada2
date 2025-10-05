package com.adatech.ecommerce.model;

import java.io.Serializable; // 1. Torna a classe serializável
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pedido implements Serializable {

    // Recomendado para controle de versão
    private static final long serialVersionUID = 1L;

    private int id;
    private Cliente cliente;
    private LocalDate dataCriacao;
    private List<ItemVenda> itens;

    private BigDecimal valorBruto;
    private BigDecimal valorDesconto;

    private StatusPedido status;
    private Cupom cupomAplicado;

    // Construtor Básico
    public Pedido(int id, Cliente cliente, LocalDate dataCriacao) {
        // Validações no construtor
        setCliente(cliente);
        setDataCriacao(dataCriacao); // Valida se a data é nula (se necessário)

        this.id = id;
        this.itens = new ArrayList<>();
        this.valorBruto = BigDecimal.ZERO;
        this.valorDesconto = BigDecimal.ZERO;
        this.status = StatusPedido.ABERTO;
        this.cupomAplicado = null;
    }

    // Construtor Completo
    public Pedido(int id, Cliente cliente, LocalDate dataCriacao,
                  List<ItemVenda> itens, BigDecimal valorBruto, BigDecimal valorDesconto, StatusPedido status) {
        // Delega a validação aqui também
        setCliente(cliente);
        setDataCriacao(dataCriacao);
        setStatus(status);

        this.id = id;
        this.itens = itens != null ? new ArrayList<>(itens) : new ArrayList<>();

        // Os valores devem ser definidos pelos setters para validação, mas aqui estamos assumindo que vêm de uma fonte confiável (desserialização)
        this.valorBruto = valorBruto != null ? valorBruto : BigDecimal.ZERO;
        this.valorDesconto = valorDesconto != null ? valorDesconto : BigDecimal.ZERO;
        this.cupomAplicado = null; // O cupom deve ser setado separadamente, se necessário.
    }

    // ----------------------------------------------------------------------
    // SETTERS (Com Validação de Entidade)
    // ----------------------------------------------------------------------

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

    // Sobrescreve o setStatus para evitar que o status seja nulo
    public void setStatus(StatusPedido status) {
        if (status == null) {
            throw new IllegalArgumentException("O status do pedido não pode ser nulo.");
        }
        this.status = status;
    }

    // ----------------------------------------------------------------------
    // MÉTODOS DE AÇÃO (Com Regras de Negócio Básicas)
    // ----------------------------------------------------------------------


    public void adicionarItem(ItemVenda item) {
        if (item == null) {
            throw new IllegalArgumentException("Não é possível adicionar um item nulo.");
        }
        if (this.status != StatusPedido.ABERTO) {
            // Esta validação pode ser duplicada no Service, mas ajuda a manter o Model defensivo
            throw new IllegalArgumentException("Só é possível adicionar itens a um pedido em status ABERTO.");
        }

        itens.add(item);
        calcularValorTotal(); // Recalcula e anula desconto
    }


    public void removerItem(ItemVenda item) {
        if (item == null) {
            return; // Ignora se tentar remover item nulo
        }
        if (this.status != StatusPedido.ABERTO) {
            throw new IllegalArgumentException("Só é possível remover itens de um pedido em status ABERTO.");
        }

        itens.remove(item);
        calcularValorTotal(); // Recalcula e anula desconto
    }


    public void aplicarCupom(Cupom cupom, BigDecimal novoValorTotal) {
        if (cupom == null) {
            throw new IllegalArgumentException("O cupom aplicado não pode ser nulo.");
        }
        if (novoValorTotal == null || novoValorTotal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O novo valor total após o desconto deve ser positivo.");
        }

        // A validação se o valorBruto - valorDesconto é o novoValorTotal deve ser feita no Service
        this.valorDesconto = this.valorBruto.subtract(novoValorTotal);
        this.cupomAplicado = cupom;
    }

    // ----------------------------------------------------------------------
    // GETTERS E MÉTODOS DE CÁLCULO (Restante do Código)
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


    public void calcularValorTotal() {
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