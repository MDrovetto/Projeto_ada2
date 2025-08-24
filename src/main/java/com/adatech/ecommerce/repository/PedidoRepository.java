package com.adatech.ecommerce.repository;

/**
 * Interface específica para o repositório de Pedidos.
 * Herda as operações básicas de CrudRepository.
 * TODO:
 *  - Adicionar, se necessário, métodos específicos para busca de pedidos,
 *    como buscarPorCliente(String cpfCliente).
 */
public interface PedidoRepository extends CrudRepository<Pedido, Integer> {
}

