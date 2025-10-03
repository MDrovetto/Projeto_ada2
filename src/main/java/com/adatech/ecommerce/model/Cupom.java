package com.adatech.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Cupom {

    private int id;
    private String codigo;
    private BigDecimal valorFixo;
    private BigDecimal percentual; // Representa 0.0 a 1.0 (ex: 0.10 para 10%)
    private LocalDate validade;
    private boolean usado;
    private BigDecimal valorMinimo; // Valor mínimo do pedido para aplicar o cupom

    // Construtor completo (ajuste conforme a necessidade do seu projeto)
    public Cupom(int id, String codigo, BigDecimal valorFixo, BigDecimal percentual, LocalDate validade, BigDecimal valorMinimo) {
        this.id = id;
        this.codigo = codigo;
        this.valorFixo = valorFixo != null ? valorFixo : BigDecimal.ZERO;
        this.percentual = percentual != null ? percentual : BigDecimal.ZERO;
        this.validade = validade;
        this.usado = false;
        this.valorMinimo = valorMinimo != null ? valorMinimo : BigDecimal.ZERO;

        // Regra básica: Garante que apenas um tipo de desconto (fixo ou percentual) seja aplicado
        if (this.valorFixo.compareTo(BigDecimal.ZERO) > 0 && this.percentual.compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalArgumentException("Um cupom não pode ter desconto fixo e percentual ao mesmo tempo.");
        }
    }

    // Getters
    public int getId() { return id; }
    public String getCodigo() { return codigo; }
    public BigDecimal getValorFixo() { return valorFixo; }
    public BigDecimal getPercentual() { return percentual; }
    public LocalDate getValidade() { return validade; }
    public boolean isUsado() { return usado; }
    public BigDecimal getValorMinimo() { return valorMinimo; }

    // Setters (apenas para campos que podem ser alterados, como o status 'usado')
    public void setUsado(boolean usado) { this.usado = usado; }
    public void setId(int id) { this.id = id; }

    // Método de Regra de Negócio: Verifica se o cupom está expirado (Regra básica no Model)
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