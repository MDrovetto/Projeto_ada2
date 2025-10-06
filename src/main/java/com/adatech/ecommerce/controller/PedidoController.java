package com.adatech.ecommerce.controller;

import com.adatech.ecommerce.model.Pedido;
import com.adatech.ecommerce.repository.*; // Interfaces de Repositório
import com.adatech.ecommerce.service.PedidoService;
import com.adatech.ecommerce.exception.RecursoNaoEncontradoException;
import com.adatech.ecommerce.exception.RegraDeNegocioException;

import java.math.BigDecimal;
import java.util.List;

public class PedidoController {

    private final PedidoService pedidoService;

    // O Controller deve receber o serviço, sem saber como ele foi criado.
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService; // A dependência é INJETADA
    }

    // ----------------------------------------------------------------------
    // MÉTODOS COM TRATAMENTO DE EXCEÇÃO
    // ----------------------------------------------------------------------

    public Pedido criarPedido(String cpfCliente) {
        System.out.println("\n--- Criar Pedido ---");
        try {
            // A leitura é garantida como String (CPF) e a validação de CPF é feita no Service.
            return pedidoService.criarPedido(cpfCliente);
        } catch (RecursoNaoEncontradoException ex) {
            // Exceção lançada se o cliente não for encontrado
            System.err.println("Erro: " + ex.getMessage());
            return null;
        } catch (Exception ex) {
            System.err.println("Erro inesperado ao criar pedido. Detalhe: " + ex.getMessage());
            return null;
        }
    }

    public boolean adicionarItem(int pedidoId, int produtoId, int quantidade, BigDecimal precoVenda) {
        System.out.println("\n--- Adicionar Item ---");
        try {
            // As validações de ID, quantidade e preço (como números positivos)
            // estão sendo tratadas dentro do PedidoServiceImpl.
            return pedidoService.adicionarItem(pedidoId, produtoId, quantidade, precoVenda);
        } catch (RecursoNaoEncontradoException | RegraDeNegocioException ex) {
            // Captura erros de não encontrar Pedido/Produto ou violação de status (RegraDeNegocio)
            System.err.println("Erro ao adicionar item: " + ex.getMessage());
            return false;
        } catch (Exception ex) {
            System.err.println("Erro inesperado. Detalhe: " + ex.getMessage());
            return false;
        }
    }

    public boolean removerItem(int pedidoId, int produtoId) {
        System.out.println("\n--- Remover Item ---");
        try {
            return pedidoService.removerItem(pedidoId, produtoId);
        } catch (RecursoNaoEncontradoException | RegraDeNegocioException ex) {
            System.err.println("Erro ao remover item: " + ex.getMessage());
            return false;
        } catch (Exception ex) {
            System.err.println("Erro inesperado. Detalhe: " + ex.getMessage());
            return false;
        }
    }

    public boolean alterarQuantidadeItem(int pedidoId, int produtoId, int novaQuantidade) {
        System.out.println("\n--- Alterar Quantidade ---");
        try {
            return pedidoService.alterarQuantidadeItem(pedidoId, produtoId, novaQuantidade);
        } catch (RecursoNaoEncontradoException | RegraDeNegocioException ex) {
            System.err.println("Erro ao alterar quantidade: " + ex.getMessage());
            return false;
        } catch (Exception ex) {
            System.err.println("Erro inesperado. Detalhe: " + ex.getMessage());
            return false;
        }
    }

    public boolean finalizarPedido(int pedidoId) {
        System.out.println("\n--- Finalizar Pedido ---");
        try {
            return pedidoService.finalizarPedido(pedidoId);
        } catch (RegraDeNegocioException ex) {
            // Inclui PedidoVazioException, que é filha de RegraDeNegocioException
            System.err.println("Não foi possível finalizar o pedido: " + ex.getMessage());
            return false;
        } catch (RecursoNaoEncontradoException ex) {
            System.err.println("Erro: " + ex.getMessage());
            return false;
        } catch (Exception ex) {
            System.err.println("Erro inesperado. Detalhe: " + ex.getMessage());
            return false;
        }
    }

    public boolean pagarPedido(int pedidoId) {
        System.out.println("\n--- Pagar Pedido ---");
        try {
            return pedidoService.pagarPedido(pedidoId);
        } catch (RegraDeNegocioException ex) {
            System.err.println("Erro ao pagar pedido: " + ex.getMessage());
            return false;
        } catch (RecursoNaoEncontradoException ex) {
            System.err.println("Erro: " + ex.getMessage());
            return false;
        } catch (Exception ex) {
            System.err.println("Erro inesperado. Detalhe: " + ex.getMessage());
            return false;
        }
    }

    public boolean entregarPedido(int pedidoId) {
        System.out.println("\n--- Entregar Pedido ---");
        try {
            return pedidoService.entregarPedido(pedidoId);
        } catch (RegraDeNegocioException ex) {
            System.err.println("Erro ao entregar pedido: " + ex.getMessage());
            return false;
        } catch (RecursoNaoEncontradoException ex) {
            System.err.println("Erro: " + ex.getMessage());
            return false;
        } catch (Exception ex) {
            System.err.println("Erro inesperado. Detalhe: " + ex.getMessage());
            return false;
        }
    }

    public List<Pedido> listarPedidos() {
        // A listagem geralmente não lança exceções de domínio, mas é bom ter o catch
        try {
            return pedidoService.listarPedidos();
        } catch (Exception ex) {
            System.err.println("Erro ao listar pedidos. Detalhe: " + ex.getMessage());
            return List.of(); // Retorna uma lista vazia em caso de falha
        }
    }

    public Pedido buscarPedidoPorId(int id) {
        // A busca é uma operação de leitura, mas pode falhar
        try {
            return pedidoService.buscarPedidoPorId(id);
        } catch (Exception ex) {
            System.err.println("Erro ao buscar pedido. Detalhe: " + ex.getMessage());
            return null;
        }
    }
}