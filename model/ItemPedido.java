package model;

    import java.math.BigDecimal;
    import java.util.Objects;

    public class ItemPedido {
        private Long id;
        private Produto produto;
        private Integer quantidade;
        private BigDecimal precoUnitario;

        public ItemPedido() {}

        public ItemPedido(Produto produto, Integer quantidade, BigDecimal precoUnitario) {
            this.produto = produto;
            this.quantidade = quantidade;
            this.precoUnitario = precoUnitario;
        }

        // Getters e Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Produto getProduto() { return produto; }
        public void setProduto(Produto produto) { this.produto = produto; }
        public Integer getQuantidade() { return quantidade; }
        public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
        public BigDecimal getPrecoUnitario() { return precoUnitario; }
        public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }

        public BigDecimal getSubTotal() {
            return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
        }

        @Override
        public String toString() {
            return String.format("ItemPedido [produto=%s, quantidade=%d, precoUnitario=R$ %.2f, subTotal=R$ %.2f]",
                    produto.getNome(), quantidade, precoUnitario, getSubTotal());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ItemPedido that = (ItemPedido) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
