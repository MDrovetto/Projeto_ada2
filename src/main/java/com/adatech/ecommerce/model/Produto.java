package com.adatech.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int estoque;

    public Produto(int id, String nome, String descricao, BigDecimal preco, int estoque) {
        this.id = id;
        setNome(nome);
        setDescricao(descricao);
        setPreco(preco);
        setEstoque(estoque);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao == null) {
            throw new IllegalArgumentException("A descrição do produto não pode ser nula.");
        }
        this.descricao = descricao.trim();
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        Objects.requireNonNull(preco, "O preço do produto não pode ser nulo.");

        if (preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço do produto não pode ser negativo.");
        }
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        if (estoque < 0) {
            throw new IllegalArgumentException("O estoque do produto não pode ser negativo.");
        }
        this.estoque = estoque;
    }

    @Override
    public String toString() {
        return "Produto {" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", estoque=" + estoque +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}