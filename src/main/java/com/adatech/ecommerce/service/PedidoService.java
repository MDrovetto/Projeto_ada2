package com.adatech.ecommerce.service;

import com.adatech.ecommerce.model.Pedido;

import java.math.BigDecimal;
import java.util.List;

public interface PedidoService {

    Pedido criarPedido(String cpfCliente);

    boolean adicionarItem(int pedidoId, int produtoId, int quantidade, BigDecimal precoVenda);

    boolean removerItem(int pedidoId, int produtoId);

    boolean alterarQuantidadeItem(int pedidoId, int produtoId, int novaQuantidade);

    /**
     * NOVO MÉTODO: Tenta aplicar um cupom de desconto ao pedido especificado.
     * Deve validar se o pedido está em status 'ABERTO' e se o cupom é válido.
     *
     * @param pedidoId O ID do pedido.
     * @param codigoCupom O código do cupom a ser aplicado.
     * @return true se o cupom foi aplicado com sucesso.
     * @throws com.adatech.ecommerce.exception.RecursoNaoEncontradoException Se o pedido ou cupom não for encontrado.
     * @throws com.adatech.ecommerce.exception.RegraDeNegocioException Se o cupom estiver expirado, usado ou se as regras de negócio forem violadas.
     */
    boolean aplicarCupom(int pedidoId, String codigoCupom);

    boolean finalizarPedido(int pedidoId);

    boolean pagarPedido(int pedidoId);

    boolean entregarPedido(int pedidoId);

    List<Pedido> listarPedidos();

    Pedido buscarPedidoPorId(int id);
}