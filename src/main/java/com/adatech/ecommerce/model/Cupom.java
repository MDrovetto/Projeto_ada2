package com.adatech.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Cupom implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String codigo;
    private BigDecimal valorFixo;
    private BigDecimal percentual;
    private LocalDate validade;
    private boolean usado;
    private BigDecimal valorMinimo;

    public Cupom(int id, String codigo, BigDecimal valorFixo, BigDecimal percentual, LocalDate validade, BigDecimal valorMinimo) {
        this.id = id;

        setCodigo(codigo);

        setValorFixo(valorFixo);
        setPercentual(percentual);
        setValidade(validade);
        setValorMinimo(valorMinimo);
        this.usado = false;

        if (this.valorFixo.compareTo(BigDecimal.ZERO) > 0 && this.percentual.compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalArgumentException("ERRO CRÍTICO: Um cupom não pode ter desconto fixo e percentual ao mesmo tempo.");
        }
    }
    public int getId() { return id; }
    public String getCodigo() { return codigo; }
    public BigDecimal getValorFixo() { return valorFixo; }
    public BigDecimal getPercentual() { return percentual; }
    public boolean isUsado() { return usado; }
    public BigDecimal getValorMinimo() { return valorMinimo; }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID do cupom não pode ser negativo.");
        }
        this.id = id;
    }

    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("O código do cupom não pode ser vazio.");
        }
        this.codigo = codigo.trim().toUpperCase();
    }

    public void setValorFixo(BigDecimal valorFixo) {
        BigDecimal fixo = Objects.requireNonNullElse(valorFixo, BigDecimal.ZERO);
        if (fixo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor fixo de desconto não pode ser negativo.");
        }
        this.valorFixo = fixo;
    }

    public void setPercentual(BigDecimal percentual) {
        BigDecimal perc = Objects.requireNonNullElse(percentual, BigDecimal.ZERO);

        if (perc.compareTo(BigDecimal.ZERO) < 0 || perc.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("O percentual de desconto deve estar entre 0.0 e 1.0 (0% a 100%).");
        }
        this.percentual = perc;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    public void setValorMinimo(BigDecimal valorMinimo) {
        BigDecimal minimo = Objects.requireNonNullElse(valorMinimo, BigDecimal.ZERO);
        if (minimo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor mínimo do pedido não pode ser negativo.");
        }
        this.valorMinimo = minimo;
    }


    public boolean isExpirado() {
        return validade != null && validade.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        String tipoDesconto = valorFixo.compareTo(BigDecimal.ZERO) > 0 ?
                "R$" + valorFixo : (percentual.multiply(new BigDecimal("100")) + "%");

        return String.format("ID: %d | Código: %s | Desconto: %s | Validade: %s | Mínimo: R$%.2f | Usado: %s",
                id, codigo, tipoDesconto, validade, valorMinimo, usado ? "Sim" : "Não");
    }
}