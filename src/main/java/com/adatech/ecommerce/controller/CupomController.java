package com.adatech.ecommerce.controller;

import com.adatech.ecommerce.exception.RecursoNaoEncontradoException;
import com.adatech.ecommerce.exception.RegraDeNegocioException;
import com.adatech.ecommerce.model.Cupom;
import com.adatech.ecommerce.service.CupomService;
import java.math.BigDecimal;
import java.util.List;

public class CupomController {

    private final CupomService cupomService;

    public CupomController(CupomService cupomService) {
        this.cupomService = cupomService;
    }
    public Cupom cadastrarCupom(Cupom cupom) {
        try {
            return cupomService.cadastrarCupom(cupom);
        } catch (RegraDeNegocioException | IllegalArgumentException ex) {
            System.err.println("Erro ao cadastrar cupom: " + ex.getMessage());
            return null;
        } catch (Exception ex) {
            System.err.println("Erro inesperado. Detalhe: " + ex.getMessage());
            return null;
        }
    }

    public BigDecimal tentarAplicarDesconto(String codigoCupom, BigDecimal valorPedido) {
        try {
            return cupomService.aplicarDesconto(codigoCupom, valorPedido);
        } catch (RecursoNaoEncontradoException | RegraDeNegocioException ex) {
            System.err.println("Erro ao aplicar desconto: " + ex.getMessage());
            return valorPedido;
        }
    }

    public List<Cupom> listarCupons() {
        try {
            return cupomService.listarCupons();
        } catch (Exception ex) {
            System.err.println("Erro ao listar cupons. Detalhe: " + ex.getMessage());
            return List.of();
        }
    }
}