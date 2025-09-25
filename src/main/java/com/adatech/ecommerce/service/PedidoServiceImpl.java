package com.adatech.ecommerce.service;

import com.adatech.ecommerce.model.*;
import com.adatech.ecommerce.repository.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final NotificationService notificationService; // A ser criado

    public PedidoServiceImpl() {
        this.pedidoRepository = new PedidoRepositoryImpl();
        this.clienteRepository = new ClienteRepositoryImpl();
        this.produtoRepository = new ProdutoRepositoryImpl();
        this.notificationService = new EmailNotificationServiceImpl(); // A ser criado
    }

    @Override
    public Pedido criarPedido(String cpfCliente) {
        Cliente cliente = clienteRepository.buscarPorCpf(cpfCliente);
        if (cliente == null) {
            System.err.println("Erro: Cliente não encontrado para criar o pedido.");
            return null;
        }
        Pedido novoPedido = new Pedido(0, cliente, LocalDate.now());
        return pedidoRepository.salvar(novoPedido);
    }

    @Override
    public boolean adicionarItem(int pedidoId, int produtoId, int quantidade, double precoVenda) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);
        Produto produto = produtoRepository.buscarPorId(produtoId);

        if (pedido == null || produto == null) {
            System.err.println("Erro: Pedido ou Produto não encontrado.");
            return false;
        }

        // Valida se o status do pedido é "ABERTO" para permitir a adição do item.
        if (pedido.getStatus() != StatusPedido.ABERTO) {
            System.err.println("Erro: Não é possível adicionar itens a um pedido que não está em status ABERTO.");
            return false;
        }

        BigDecimal precoVendaBigDecimal = BigDecimal.valueOf(precoVenda);
        ItemVenda item = new ItemVenda(produto, quantidade, precoVendaBigDecimal);
        pedido.adicionarItem(item);
        pedidoRepository.salvar(pedido);
        return true;
    }

    @Override
    public boolean removerItem(int pedidoId, int produtoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId); // instância temporariamente um pedido para ver se existe na lista de pedidos
        if (pedido == null) {
            return false;
        }

        if (pedido.getStatus() != StatusPedido.ABERTO) {
            return false;
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
        return false;
    }

    @Override
    public boolean alterarQuantidadeItem(int pedidoId, int produtoId, int novaQuantidade) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);

        if (pedido == null || novaQuantidade <= 0) {
            System.err.println("Erro: Pedido não encontrado ou quantidade inválida.");
            return false;
        }

        if (pedido.getStatus() != StatusPedido.ABERTO) {
            System.err.println("Erro: Não é possível alterar itens de um pedido que não está ABERTO.");
            return false;
        }

        ItemVenda itemParaAlterar = null;
        for (ItemVenda item : pedido.getItens()) {
            if (item.getProduto().getId() == produtoId) {
                itemParaAlterar = item;
                break;
            }
        }

        if (itemParaAlterar != null) {
            pedido.removerItem(itemParaAlterar);
            BigDecimal precoVendaOriginal = itemParaAlterar.getPrecoVenda();
            ItemVenda novoItem = new ItemVenda(itemParaAlterar.getProduto(), novaQuantidade, precoVendaOriginal);

            pedido.adicionarItem(novoItem);
            pedidoRepository.salvar(pedido);
            System.out.println("Quantidade do item alterada com sucesso.");
            return true;
        }

        System.err.println("Erro: Item não encontrado no pedido.");
        return false;

    }

    @Override
    public boolean finalizarPedido(int pedidoId) {
        // TODO: Implementar a lógica para finalizar o pedido
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);
        if (pedido == null) {
            return false;
        }

        if (pedido.getItens().isEmpty() || pedido.getValorTotal().compareTo(BigDecimal.ZERO) <= 0) {
            System.err.println("Erro: Não é possível finalizar um pedido vazio.");
            return false;
        }

        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedidoRepository.salvar(pedido);
        notificationService.enviarNotificacao(pedido.getCliente(), "Seu pedido foi finalizado com sucesso!");
        return true;
    }

    @Override
    public boolean pagarPedido(int pedidoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);
        if (pedido == null) {
            return false;
        }

        if (pedido.getStatus() != StatusPedido.AGUARDANDO_PAGAMENTO) {
            System.err.println("Erro: O pedido não está aguardando pagamento.");
            return false;
        }

        pedido.setStatus(StatusPedido.PAGO);
        pedidoRepository.salvar(pedido);
        notificationService.enviarNotificacao(pedido.getCliente(), "O pagamento do seu pedido foi confirmado!");
        return true;
    }

    @Override
    public boolean entregarPedido(int pedidoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);
        if (pedido == null) {
            return false;
        }

        if (pedido.getStatus() != StatusPedido.PAGO) {
            System.err.println("Erro: O pedido não está pago para ser entregue.");
            return false;
        }

        pedido.setStatus(StatusPedido.FINALIZADO);
        pedidoRepository.salvar(pedido);
        notificationService.enviarNotificacao(pedido.getCliente(), "Seu pedido foi entregue!");
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

