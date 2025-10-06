package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Cupom;
import java.util.List;

public interface CupomRepository {
    Cupom salvar(Cupom cupom);
    Cupom buscarPorCodigo(String codigo);
    Cupom buscarPorId(int id);
    List<Cupom> listarTodos();
}