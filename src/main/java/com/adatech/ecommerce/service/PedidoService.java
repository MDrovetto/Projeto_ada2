package main.java.com.adatech.ecommerce.service;

import java.util.List;

/**
 * Interface para os serviços relacionados a Pedidos.
 * Define as operações de negócio para a entidade Pedido.
 * TODO:
 *  - Definir os métodos:
 *    - criarPedido(String cpfCliente): Pedido
 *    - adicionarItem(int pedidoId, int produtoId, int quantidade, double precoVenda): boolean
 *    - removerItem(int pedidoId, int produtoId): boolean
 *    - alterarQuantidadeItem(int pedidoId, int produtoId, int novaQuantidade): boolean
 *    - finalizarPedido(int pedidoId): boolean
 *    - pagarPedido(int pedidoId): boolean
 *    - entregarPedido(int pedidoId): boolean
 *    - listarPedidos(): List<Pedido>
 *    - buscarPedidoPorId(int id): Pedido
 */
public interface PedidoService {
    Pedido criarPedido(String cpfCliente);
    boolean adicionarItem(int pedidoId, int produtoId, int quantidade, double precoVenda);
    boolean removerItem(int pedidoId, int produtoId);
    boolean alterarQuantidadeItem(int pedidoId, int produtoId, int novaQuantidade);
    boolean finalizarPedido(int pedidoId);
    boolean pagarPedido(int pedidoId);
    boolean entregarPedido(int pedidoId);
    List<Pedido> listarPedidos();
    Pedido buscarPedidoPorId(int id);
}

