package Controller;

import model.Pedido;
import Service.PedidoService;
import Service.impl.PedidoServiceImpl;
import Repository.impl.ClienteRepository;
import Repository.impl.PedidoRepository;
import Repository.impl.ProdutoRepository;
import notification.NotificationService;
import java.math.BigDecimal;
import java.util.List;

public class PedidoController {
    private final PedidoService pedidoService;

    // OPÇÃO 1 ✅ - Instanciando PedidoServiceImpl passando as dependências
    public PedidoController(PedidoRepository pedidoRepository,
                            ClienteRepository clienteRepository,
                            ProdutoRepository produtoRepository,
                            NotificationService notificationService) {
        this.pedidoService = new PedidoServiceImpl(
                pedidoRepository,
                clienteRepository,
                produtoRepository,
                notificationService
        );
    }

    public Pedido criarPedido(Long clienteId) {
        return pedidoService.criarPedido(clienteId);
    }

    public void adicionarItemPedido(Long pedidoId, Long produtoId, Integer quantidade) {
        pedidoService.adicionarItem(pedidoId, produtoId, quantidade);
    }

    public void removerItemPedido(Long pedidoId, Long produtoId) {
        pedidoService.removerItem(pedidoId, produtoId);
    }

    public BigDecimal calcularTotalPedido(Long pedidoId) {
        return pedidoService.calcularTotal(pedidoId);
    }

    public void finalizarPedido(Long pedidoId) {
        pedidoService.finalizarPedido(pedidoId);
    }

    public Pedido buscarPedidoPorId(Long id) {
        return pedidoService.buscarPorId(id).orElse(null);
    }

    public List<Pedido> listarPedidos() {
        return pedidoService.listarTodos();
    }
}
