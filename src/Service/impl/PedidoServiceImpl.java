package Service.impl;

import model.*;
import Repository.impl.ClienteRepository;
import Repository.impl.PedidoRepository;
import Repository.impl.ProdutoRepository;
import Service.PedidoService;
import notification.NotificationService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final NotificationService notificationService;

    // INJEÇÃO POR CONSTRUTOR ✅
    public PedidoServiceImpl(PedidoRepository pedidoRepository,
                             ClienteRepository clienteRepository,
                             ProdutoRepository produtoRepository,
                             NotificationService notificationService) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.notificationService = notificationService;
    }

    @Override
    public Pedido criarPedido(Long clienteId) {
        Cliente cliente = clienteRepository.buscarPorId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + clienteId));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatus(StatusPedido.ABERTO);

        return pedidoRepository.salvar(pedido);
    }

    @Override
    public void adicionarItem(Long pedidoId, Long produtoId, Integer quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        Pedido pedido = pedidoRepository.buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado com ID: " + pedidoId));

        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new IllegalStateException("Não é possível adicionar itens a um pedido " + pedido.getStatus());
        }

        Produto produto = produtoRepository.buscarPorId(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + produtoId));

        if (produto.getQuantidadeEstoque() < quantidade) {
            throw new IllegalStateException("Quantidade em estoque insuficiente. Disponível: " + produto.getQuantidadeEstoque());
        }

        ItemPedido item = new ItemPedido();
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(produto.getPreco());

        pedido.adicionarItem(item);
        pedidoRepository.salvar(pedido);
    }

    @Override
    public void removerItem(Long pedidoId, Long produtoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado com ID: " + pedidoId));

        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new IllegalStateException("Não é possível remover itens de um pedido " + pedido.getStatus());
        }

        pedido.removerItem(produtoId);
        pedidoRepository.salvar(pedido);
    }

    @Override
    public BigDecimal calcularTotal(Long pedidoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado com ID: " + pedidoId));

        return pedido.calcularTotal();
    }

    @Override
    public void finalizarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado com ID: " + pedidoId));

        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new IllegalStateException("Pedido já está " + pedido.getStatus());
        }

        if (pedido.getItens().isEmpty()) {
            throw new IllegalStateException("Não é possível finalizar um pedido sem itens");
        }

        // Atualizar estoque
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = item.getProduto();
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - item.getQuantidade());
            produtoRepository.salvar(produto);
        }

        pedido.setStatus(StatusPedido.FINALIZADO);
        pedido.setDataFinalizacao(LocalDateTime.now());
        pedidoRepository.salvar(pedido);

        // Notificar ✅ SEM System.err.println
        notificationService.enviarNotificacao("Pedido #" + pedidoId + " finalizado com sucesso! Total: R$ " + pedido.calcularTotal());
    }

    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.buscarPorId(id);
    }

    @Override
    public List<Pedido> listarTodos() {
        return pedidoRepository.listarTodos();
    }
}
