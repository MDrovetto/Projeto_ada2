package com.adatech.ecommerce.service;

import com.adatech.ecommerce.exception.PedidoVazioException;
import com.adatech.ecommerce.exception.RegraDeNegocioException;
import com.adatech.ecommerce.exception.RecursoNaoEncontradoException;
import com.adatech.ecommerce.model.*;
import com.adatech.ecommerce.repository.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PedidoServiceImpl implements PedidoService {

    // Dependências injetadas
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final NotificationService notificationService;

    // 1. INJEÇÃO POR CONSTRUTOR
    public PedidoServiceImpl(
            PedidoRepository pedidoRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository,
            NotificationService notificationService) {

        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.notificationService = notificationService;
    }

    @Override
    public Pedido criarPedido(String cpfCliente) {
        Cliente cliente = clienteRepository.buscarPorCpf(cpfCliente);

        // 2. EXCEÇÃO DE DOMÍNIO
        if (cliente == null) {
            throw new RecursoNaoEncontradoException("Cliente com CPF '" + cpfCliente + "' não encontrado para criar o pedido.");
        }

        Pedido novoPedido = new Pedido(0, cliente, LocalDate.now());
        return pedidoRepository.salvar(novoPedido);
    }

    @Override
    public boolean adicionarItem(int pedidoId, int produtoId, int quantidade, double precoVenda) {

        // Validação de entrada
        if (quantidade <= 0) {
            throw new RegraDeNegocioException("A quantidade do item deve ser positiva.");
        }
        if (precoVenda <= 0) {
            throw new RegraDeNegocioException("O preço de venda deve ser positivo.");
        }

        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);
        Produto produto = produtoRepository.buscarPorId(produtoId);

        // 2. EXCEÇÕES DE DOMÍNIO
        if (pedido == null) {
            throw new RecursoNaoEncontradoException("Pedido ID " + pedidoId + " não encontrado.");
        }
        if (produto == null) {
            throw new RecursoNaoEncontradoException("Produto ID " + produtoId + " não encontrado.");
        }

        // Validação de status
        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new RegraDeNegocioException("Não é possível adicionar itens a um pedido com status '" + pedido.getStatus() + "'.");
        }

        BigDecimal precoVendaBigDecimal = BigDecimal.valueOf(precoVenda);
        ItemVenda item = new ItemVenda(produto, quantidade, precoVendaBigDecimal);

        // Note: Se o produto já existir, o método 'adicionarItem' do Pedido deve ser inteligente o suficiente para atualizar a quantidade.
        pedido.adicionarItem(item);
        pedidoRepository.salvar(pedido);
        return true;
    }

    @Override
    public boolean removerItem(int pedidoId, int produtoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);

        if (pedido == null) {
            throw new RecursoNaoEncontradoException("Pedido ID " + pedidoId + " não encontrado.");
        }

        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new RegraDeNegocioException("Não é possível remover itens de um pedido com status '" + pedido.getStatus() + "'.");
        }

        ItemVenda itemParaRemover = null;
        for (ItemVenda item : pedido.getItens()) {
            if (item.getProduto().getId() == produtoId) {
                itemParaRemover = item;
                break;
            }
        }

        if (itemParaRemover != null) {
            pedido.removerItem(itemParaRemover);
            pedidoRepository.salvar(pedido);
            return true;
        }

        // 2. EXCEÇÃO DE DOMÍNIO
        throw new RecursoNaoEncontradoException("Item com Produto ID " + produtoId + " não encontrado no pedido.");
    }

    @Override
    public boolean alterarQuantidadeItem(int pedidoId, int produtoId, int novaQuantidade) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);

        if (pedido == null) {
            throw new RecursoNaoEncontradoException("Pedido ID " + pedidoId + " não encontrado.");
        }
        if (novaQuantidade <= 0) {
            throw new RegraDeNegocioException("A nova quantidade deve ser maior que zero.");
        }

        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new RegraDeNegocioException("Não é possível alterar itens de um pedido com status '" + pedido.getStatus() + "'.");
        }

        ItemVenda itemParaAlterar = null;
        for (ItemVenda item : pedido.getItens()) {
            if (item.getProduto().getId() == produtoId) {
                itemParaAlterar = item;
                break;
            }
        }

        if (itemParaAlterar != null) {
            // Remove o item antigo
            pedido.removerItem(itemParaAlterar);

            // Adiciona o novo item com a nova quantidade (mantendo o preço de venda original)
            BigDecimal precoVendaOriginal = itemParaAlterar.getPrecoVenda();
            ItemVenda novoItem = new ItemVenda(itemParaAlterar.getProduto(), novaQuantidade, precoVendaOriginal);
            pedido.adicionarItem(novoItem);

            pedidoRepository.salvar(pedido);
            return true;
        }

        throw new RecursoNaoEncontradoException("Item com Produto ID " + produtoId + " não encontrado no pedido.");
    }

    @Override
    public boolean finalizarPedido(int pedidoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);

        if (pedido == null) {
            throw new RecursoNaoEncontradoException("Pedido ID " + pedidoId + " não encontrado.");
        }

        if (pedido.getItens().isEmpty()) {
            throw new PedidoVazioException("Não é possível finalizar um pedido que não contém itens.");
        }

        // Se a validação do valor total for necessária
        if (pedido.getValorTotal() == null || pedido.getValorTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("O valor total do pedido é zero ou negativo. Verifique os itens.");
        }

        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedidoRepository.salvar(pedido);

        // Assumindo que o serviço de notificação trata suas próprias exceções internamente
        notificationService.enviarNotificacao(pedido.getCliente(), "Seu pedido foi finalizado e está aguardando pagamento! ID: " + pedido.getId());
        return true;
    }

    @Override
    public boolean pagarPedido(int pedidoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);

        if (pedido == null) {
            throw new RecursoNaoEncontradoException("Pedido ID " + pedidoId + " não encontrado.");
        }

        if (pedido.getStatus() != StatusPedido.AGUARDANDO_PAGAMENTO) {
            throw new RegraDeNegocioException("O pedido não pode ser pago pois o status atual é '" + pedido.getStatus() + "'. Deve estar AGUARDANDO_PAGAMENTO.");
        }

        pedido.setStatus(StatusPedido.PAGO);
        pedidoRepository.salvar(pedido);

        notificationService.enviarNotificacao(pedido.getCliente(), "O pagamento do seu pedido foi confirmado! ID: " + pedido.getId());
        return true;
    }

    @Override
    public boolean entregarPedido(int pedidoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);

        if (pedido == null) {
            throw new RecursoNaoEncontradoException("Pedido ID " + pedidoId + " não encontrado.");
        }

        if (pedido.getStatus() != StatusPedido.PAGO) {
            throw new RegraDeNegocioException("O pedido não pode ser entregue pois o status atual é '" + pedido.getStatus() + "'. Deve estar PAGO.");
        }

        pedido.setStatus(StatusPedido.FINALIZADO);
        pedidoRepository.salvar(pedido);

        notificationService.enviarNotificacao(pedido.getCliente(), "Seu pedido foi entregue! Obrigado pela compra. ID: " + pedido.getId());
        return true;
    }

    @Override
    public List<Pedido> listarPedidos() {
        return pedidoRepository.listarTodos();
    }

    @Override
    public Pedido buscarPedidoPorId(int id) {
        return pedidoRepository.buscarPorId(id);
    }
}