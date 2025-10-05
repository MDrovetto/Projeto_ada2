// CupomService.java (Interface)
package com.adatech.ecommerce.service;
import com.adatech.ecommerce.model.Cupom;
import java.math.BigDecimal;
import java.util.List;

public interface CupomService {
    Cupom cadastrarCupom(Cupom cupom);
    Cupom buscarPorCodigo(String codigo);
    List<Cupom> listarCupons();
    BigDecimal aplicarDesconto(String codigoCupom, BigDecimal valorPedido);
    void marcarComoUsado(int id);
}