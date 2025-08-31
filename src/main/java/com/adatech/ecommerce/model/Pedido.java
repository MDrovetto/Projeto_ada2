package main.java.com.adatech.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

        private int id;
        private Cliente cliente;
        private LocalDate dataCriacao;
        private List<ItemVenda> itens;
        private BigDecimal valorTotal;
        private StatusPedido status;

        public Pedido(int id, Cliente cliente, LocalDate dataCriacao) {
            this.id = id;
            this.cliente = cliente;
            this.dataCriacao = dataCriacao;
            this.itens = new ArrayList<>();
            this.valorTotal = BigDecimal.ZERO;
            this.status = StatusPedido.ABERTO;
        }

        public Pedido(int id, Cliente cliente, LocalDate dataCriacao,
                      List<ItemVenda> itens, BigDecimal valorTotal, StatusPedido status) {
            this.id = id;
            this.cliente = cliente;
            this.dataCriacao = dataCriacao;
            this.itens = itens != null ? new ArrayList<>(itens) : new ArrayList<>();
            this.valorTotal = valorTotal != null ? valorTotal : BigDecimal.ZERO;
            this.status = status != null ? status : StatusPedido.ABERTO;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Cliente getCliente() {
            return cliente;
        }

        public void setCliente(Cliente cliente) {
            this.cliente = cliente;
        }

        public LocalDate getDataCriacao() {
            return dataCriacao;
        }

        public void setDataCriacao(LocalDate dataCriacao) {
            this.dataCriacao = dataCriacao;
        }

        public List<ItemVenda> getItens() {
            return new ArrayList<>(itens);
        }

        public void setItens(List<ItemVenda> itens) {
            this.itens = itens != null ? new ArrayList<>(itens) : new ArrayList<>();
            calcularValorTotal();
        }

        public BigDecimal getValorTotal() {
            return valorTotal;
        }

        public void setValorTotal(BigDecimal valorTotal) {
            this.valorTotal = valorTotal != null ? valorTotal : BigDecimal.ZERO;
        }

        public StatusPedido getStatus() {
            return status;
        }

        public void setStatus(StatusPedido status) {
            this.status = status != null ? status : StatusPedido.ABERTO;
        }

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

        public void calcularValorTotal() {
            this.valorTotal = BigDecimal.ZERO;
            for (ItemVenda item : itens) {
                this.valorTotal = this.valorTotal.add(item.calcularSubtotal());
            }
        }

        @Override
        public String toString() {
            return "Pedido{" +
                    "id=" + id +
                    ", cliente=" + cliente +
                    ", dataCriacao=" + dataCriacao +
                    ", itens=" + itens +
                    ", valorTotal=" + valorTotal +
                    ", status=" + status +
                    '}';
        }

}
