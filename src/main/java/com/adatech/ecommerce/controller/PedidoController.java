package com.adatech.ecommerce.controller;

import com.adatech.ecommerce.service.PedidoService;
import com.adatech.ecommerce.service.PedidoServiceImpl;
import com.adatech.ecommerce.model.Pedido;

import java.util.List;

/**
 * Controller para gerenciar as operações relacionadas a Pedidos.
 * Faz a ponte entre a View e o Service.
 * TODO:
 *  - No construtor, instanciar o PedidoService.
 *  - Criar métodos para cada uma das operações de negócio definidas no PedidoService.
 *  - Os métodos devem chamar as operações correspondentes no PedidoService e retornar
 *    os resultados para a View.
 */
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController() {
        this.pedidoService = new PedidoServiceImpl();
    }

    public Pedido criarPedido(String cpfCliente) {
        return pedidoService.criarPedido(cpfCliente);
    }

    public boolean adicionarItem(int pedidoId, int produtoId, int quantidade, double precoVenda) {
        return pedidoService.adicionarItem(pedidoId, produtoId, quantidade, precoVenda);
    }

    public boolean removerItem(int pedidoId, int produtoId) {
        return pedidoService.removerItem(pedidoId, produtoId);
    }

    public boolean alterarQuantidadeItem(int pedidoId, int produtoId, int novaQuantidade) {
        return pedidoService.alterarQuantidadeItem(pedidoId, produtoId, novaQuantidade);
    }

    public boolean finalizarPedido(int pedidoId) {
        return pedidoService.finalizarPedido(pedidoId);
    }

    public boolean pagarPedido(int pedidoId) {
        return pedidoService.pagarPedido(pedidoId);
    }

    public boolean entregarPedido(int pedidoId) {
        return pedidoService.entregarPedido(pedidoId);
    }

    public List<Pedido> listarPedidos() {
        return pedidoService.listarPedidos();
    }

    public Pedido buscarPedidoPorId(int id) {
        return pedidoService.buscarPedidoPorId(id);
    }
}

