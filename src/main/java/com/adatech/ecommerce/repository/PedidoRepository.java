package main.java.com.adatech.ecommerce.repository;

import main.java.com.adatech.ecommerce.model.Pedido;

/**
 * Interface específica para o repositório de Pedidos.
 * Herda as operações básicas de CrudRepository.
 * TODO:
 *  - Adicionar, se necessário, métodos específicos para busca de pedidos,
 *    como buscarPorCliente(String cpfCliente).
 */
public interface PedidoRepository extends CrudRepository<Pedido, Integer> {
}

