package com.adatech.ecommerce.service;

import com.adatech.ecommerce.model.Pedido;

import java.math.BigDecimal;
import java.util.List;

public interface PedidoService {

    Pedido criarPedido(String cpfCliente);

    boolean adicionarItem(int pedidoId, int produtoId, int quantidade, BigDecimal precoVenda);

    boolean removerItem(int pedidoId, int produtoId);

    boolean alterarQuantidadeItem(int pedidoId, int produtoId, int novaQuantidade);

    boolean aplicarCupom(int pedidoId, String codigoCupom);

    boolean finalizarPedido(int pedidoId);

    boolean pagarPedido(int pedidoId);

    boolean entregarPedido(int pedidoId);

    List<Pedido> listarPedidos();

    Pedido buscarPedidoPorId(int id);

    List<Pedido> buscarPedidosPorCliente(String cpf);
}