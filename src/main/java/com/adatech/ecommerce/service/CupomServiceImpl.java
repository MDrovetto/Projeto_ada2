package com.adatech.ecommerce.service;

import com.adatech.ecommerce.exception.RecursoNaoEncontradoException;
import com.adatech.ecommerce.exception.RegraDeNegocioException;
import com.adatech.ecommerce.model.Cupom;
import com.adatech.ecommerce.repository.CupomRepository;
import java.math.BigDecimal;
import java.util.List;

public class CupomServiceImpl implements CupomService {

    private final CupomRepository cupomRepository;

    // Injeção de Dependência por Construtor
    public CupomServiceImpl(CupomRepository cupomRepository) {
        this.cupomRepository = cupomRepository;
    }

    public Cupom cadastrarCupom(Cupom cupom) {
        // Regra de Negócio: Não cadastrar cupom com código duplicado (verificação adicional no service)
        Cupom existente = cupomRepository.buscarPorCodigo(cupom.getCodigo());
        if (existente != null) {
            throw new RegraDeNegocioException("Código de cupom '" + cupom.getCodigo() + "' já cadastrado.");
        }
        return cupomRepository.salvar(cupom);
    }

    public Cupom buscarPorCodigo(String codigo) {
        Cupom cupom = cupomRepository.buscarPorCodigo(codigo);
        if (cupom == null) {
            throw new RecursoNaoEncontradoException("Cupom com código '" + codigo + "' não encontrado.");
        }
        return cupom;
    }

    public List<Cupom> listarCupons() {
        return cupomRepository.listarTodos();
    }

    /**
     * Regra Principal: Aplica o desconto ao valor do pedido, validando todas as regras.
     */
    public BigDecimal aplicarDesconto(String codigoCupom, BigDecimal valorPedido) {

        Cupom cupom = buscarPorCodigo(codigoCupom); // Lança RecursoNaoEncontradoException se não achar

        if (cupom.isExpirado()) {
            throw new RegraDeNegocioException("O cupom " + codigoCupom + " está expirado.");
        }

        if (cupom.isUsado()) {
            throw new RegraDeNegocioException("O cupom " + codigoCupom + " já foi utilizado.");
        }

        if (valorPedido.compareTo(cupom.getValorMinimo()) < 0) {
            throw new RegraDeNegocioException(
                    String.format("Valor mínimo do pedido não atingido. É necessário R$%.2f.", cupom.getValorMinimo())
            );
        }

        // Cálculo do desconto
        if (cupom.getValorFixo().compareTo(BigDecimal.ZERO) > 0) {
            return valorPedido.subtract(cupom.getValorFixo());
        } else if (cupom.getPercentual().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal desconto = valorPedido.multiply(cupom.getPercentual());
            return valorPedido.subtract(desconto);
        } else {
            // Caso não tenha valor fixo nem percentual (cupom inválido)
            throw new RegraDeNegocioException("Cupom inválido: Não possui valor de desconto definido.");
        }
    }

    // Método para marcar como usado (chamado após a finalização da compra)
    public void marcarComoUsado(int id) {
        Cupom cupom = cupomRepository.buscarPorId(id);
        if (cupom == null) {
            throw new RecursoNaoEncontradoException("Cupom ID " + id + " não encontrado.");
        }
        if (cupom.isUsado()) {
            throw new RegraDeNegocioException("Cupom ID " + id + " já foi utilizado.");
        }
        cupom.setUsado(true);
        cupomRepository.salvar(cupom);
    }
}