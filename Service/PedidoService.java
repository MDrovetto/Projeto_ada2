package Service;

import model.Pedido;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PedidoService {
    Pedido criarPedido(Long clienteId);
    void adicionarItem(Long pedidoId, Long produtoId, Integer quantidade);
    void removerItem(Long pedidoId, Long produtoId);
    BigDecimal calcularTotal(Long pedidoId);
    void finalizarPedido(Long pedidoId);
    Optional<Pedido> buscarPorId(Long id);
    List<Pedido> listarTodos();
}
