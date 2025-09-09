package com.adatech.ecommerce.service;

import com.adatech.ecommerce.model.Pedido;
import java.util.List;

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

