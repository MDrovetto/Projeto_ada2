package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Pedido;
import java.util.List;

public interface PedidoRepository extends CrudRepository<Pedido, Integer> {
    List<Pedido>buscarPorCliente(String cpfCliente);
}

