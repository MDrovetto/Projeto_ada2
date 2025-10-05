package com.adatech.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Cupom implements Serializable {

    // Recomendado para controle de versão durante a desserialização
    private static final long serialVersionUID = 1L;

    private int id;
    private String codigo;
    private BigDecimal valorFixo;
    private BigDecimal percentual;
    private LocalDate validade;
    private boolean usado;
    private BigDecimal valorMinimo;

    // Construtor principal
    public Cupom(int id, String codigo, BigDecimal valorFixo, BigDecimal percentual, LocalDate validade, BigDecimal valorMinimo) {
        // Delega a validação para os setters para garantir a consistência
        this.id = id;

        // Chamamos setCodigo() para validar o código
        setCodigo(codigo);

        setValorFixo(valorFixo);
        setPercentual(percentual);
        setValidade(validade);
        setValorMinimo(valorMinimo);
        this.usado = false; // Valor inicial padrão

        // Regra Crítica no Construtor
        if (this.valorFixo.compareTo(BigDecimal.ZERO) > 0 && this.percentual.compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalArgumentException("ERRO CRÍTICO: Um cupom não pode ter desconto fixo e percentual ao mesmo tempo.");
        }
    }

    // -------------------------------------------------------------------
    // GETTERS (Acessores)
    // -------------------------------------------------------------------

    public int getId() { return id; }
    public String getCodigo() { return codigo; }
    public BigDecimal getValorFixo() { return valorFixo; }
    public BigDecimal getPercentual() { return percentual; }
    public LocalDate getValidade() { return validade; }
    public boolean isUsado() { return usado; }
    public BigDecimal getValorMinimo() { return valorMinimo; }

    // -------------------------------------------------------------------
    // SETTERS (Modificadores) - Com Tratamento de Exceção
    // -------------------------------------------------------------------

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

        // 0 <= percentual <= 1.0 (ou 100%)
        if (perc.compareTo(BigDecimal.ZERO) < 0 || perc.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("O percentual de desconto deve estar entre 0.0 e 1.0 (0% a 100%).");
        }
        this.percentual = perc;
    }

    public void setValidade(LocalDate validade) {
        // Validade pode ser nula (cupom sem expiração)
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

    // -------------------------------------------------------------------
    // REGRAS DE NEGÓCIO E OUTROS
    // -------------------------------------------------------------------

    public boolean isExpirado() {
        // Verifica se a data de validade é anterior a hoje
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